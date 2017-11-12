//
// Created by Gwynn on 17/11/3.
//
#include <jni.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include "audio.h"
#include "../utils.h"

#define writeint(buf, base, val) do { buf[base + 3] = ((val) >> 24) & 0xff; \
buf[base + 2]=((val) >> 16) & 0xff; \
buf[base + 1]=((val) >> 8) & 0xff; \
buf[base] = (val) & 0xff; \
} while(0)

JavaVM *g_JVM = NULL;

/*-----------         map java method             -----------*/

int startRecord(JNIEnv *env, jobject object, jstring path) {
    const char *pathStr = env->GetStringUTFChars(path, 0);
    int result = initRecorder(pathStr);

    if (pathStr != 0) {
        env->ReleaseStringUTFChars(path, pathStr);
    }

    return result;
}

int writeFrame(JNIEnv *env, jobject object, jobject byteBuffer, jint len) {

}

void stopRecord(JNIEnv *env, jobject object) {

}


int openOpusFile(JNIEnv *env, jobject object, jstring path) {

}

int seekOpusFile(JNIEnv *env, jobject object, jfloat position) {

}

int isOpusFile(JNIEnv *env, jobject object, jstring path) {

}

void closeOpusFile(JNIEnv *env, jobject object) {

}

void readOpusFile(JNIEnv *env, jobject object, jobject buffer, jint capacity, jintArray args) {

}

long getTotalPcmDuration(JNIEnv *env, jobject object) {

}

jbyteArray getWaveform(JNIEnv *env, jobject object, jstring path) {

}

jbyteArray getWaveform2(JNIEnv *env, jobject object, jshortArray array, jint length) {

}

opus_int32 coding_rate = 16000;
ogg_int32_t _packetId;
OpusEncoder *_encoder = 0;
uint8_t *_packet = 0;
ogg_stream_state os;
FILE *_fileOs = 0;
oe_enc_opt inopt;
OpusHeader header;
opus_int32 min_bytes;
int max_frame_bytes;
ogg_packet op;
ogg_page og;
opus_int64 bytes_written;
opus_int64 pages_out;
opus_int64 total_samples;
ogg_int64_t enc_granulepos;
ogg_int64_t last_granulepos;
int size_segments;
int last_segments;

/*-----------         implements audio.h             -----------*/

static int write_uint32(Packet *p, ogg_uint32_t val) {
    if (p->pos > p->maxlen - 4) {
        return 0;
    }
    p->data[p->pos  ] = (val    ) & 0xFF;
    p->data[p->pos+1] = (val>> 8) & 0xFF;
    p->data[p->pos+2] = (val>>16) & 0xFF;
    p->data[p->pos+3] = (val>>24) & 0xFF;
    p->pos += 4;
    return 1;
}

static int write_uint16(Packet *p, ogg_uint16_t val) {
    if (p->pos > p->maxlen-2) {
        return 0;
    }
    p->data[p->pos  ] = (val    ) & 0xFF;
    p->data[p->pos+1] = (val>> 8) & 0xFF;
    p->pos += 2;
    return 1;
}

static int write_chars(Packet *p, const unsigned char *str, int nb_chars)
{
    int i;
    if (p->pos>p->maxlen-nb_chars)
        return 0;
    for (i=0;i<nb_chars;i++)
        p->data[p->pos++] = str[i];
    return 1;
}

static int read_uint32(ROPacket *p, ogg_uint32_t *val)
{
    if (p->pos>p->maxlen-4)
        return 0;
    *val =  (ogg_uint32_t)p->data[p->pos  ];
    *val |= (ogg_uint32_t)p->data[p->pos+1]<< 8;
    *val |= (ogg_uint32_t)p->data[p->pos+2]<<16;
    *val |= (ogg_uint32_t)p->data[p->pos+3]<<24;
    p->pos += 4;
    return 1;
}

static int read_uint16(ROPacket *p, ogg_uint16_t *val)
{
    if (p->pos>p->maxlen-2)
        return 0;
    *val =  (ogg_uint16_t)p->data[p->pos  ];
    *val |= (ogg_uint16_t)p->data[p->pos+1]<<8;
    p->pos += 2;
    return 1;
}

static int read_chars(ROPacket *p, unsigned char *str, int nb_chars)
{
    int i;
    if (p->pos>p->maxlen-nb_chars)
        return 0;
    for (i=0;i<nb_chars;i++)
        str[i] = p->data[p->pos++];
    return 1;
}

static void comment_pad(char **comments, int* length, int amount) {
    if (amount > 0) {
        char *p = *comments;
        // Make sure there is at least amount worth of padding free, and round up to the maximum that fits in the current ogg segments
        int newlen = (*length + amount + 255) / 255 * 255 - 1;
        p = (char *)realloc(p, newlen);
        for (int i = *length; i < newlen; i++) {
            p[i] = 0;
        }
        *comments = p;
        *length = newlen;
    }
}

static int writeOggPage(ogg_page *page, FILE *os) {
    int written = fwrite(page->header, sizeof(unsigned char), page->header_len, os);
    written += fwrite(page->body, sizeof(unsigned char), page->body_len, os);
    return written;
}

static void comment_init(char **comments, int *length, const char *vendor_string) {
    // The 'vendor' field should be the actual encoding library used
    int vendor_length = strlen(vendor_string);
    int user_comment_list_length = 0;
    int len = 8 + 4 + vendor_length + 4;
    char *p = (char *)malloc(len);
    memcpy(p, "OpusTags", 8);
    writeint(p, 8, vendor_length);
    memcpy(p + 12, vendor_string, vendor_length);
    writeint(p, 12 + vendor_length, user_comment_list_length);
    *length = len;
    *comments = p;
}


int opus_header_to_packet(const OpusHeader *h, unsigned char *packet, int len) {
    int i;
    Packet p;
    unsigned char ch;
    
    p.data = packet;
    p.maxlen = len;
    p.pos = 0;
    if (len < 19) {
        return 0;
    }
    if (!write_chars(&p, (const unsigned char *)"OpusHead", 8)) {
        return 0;
    }

    ch = 1;
    if (!write_chars(&p, &ch, 1)) {
        return 0;
    }
    
    ch = h->channels;
    if (!write_chars(&p, &ch, 1)) {
        return 0;
    }
    
    if (!write_uint16(&p, h->preskip)) {
        return 0;
    }
    
    if (!write_uint32(&p, h->input_sample_rate)) {
        return 0;
    }
    
    if (!write_uint16(&p, h->gain)) {
        return 0;
    }
    
    ch = h->channel_mapping;
    if (!write_chars(&p, &ch, 1)) {
        return 0;
    }
    
    if (h->channel_mapping != 0) {
        ch = h->nb_streams;
        if (!write_chars(&p, &ch, 1)) {
            return 0;
        }
        
        ch = h->nb_coupled;
        if (!write_chars(&p, &ch, 1)) {
            return 0;
        }
        
        /* Multi-stream support */
        for (i = 0; i < h->channels; i++) {
            if (!write_chars(&p, &h->stream_map[i], 1)) {
                return 0;
            }
        }
    }
    
    return p.pos;
}

int initRecorder(const char *path) {
    cleanupRecorder();

    if (!path) {
        return 0;
    }

    _fileOs = fopen(path, "wb");
    if (!_fileOs) {
        return 0;
    }

    inopt.rate = rate;
    inopt.gain = 0;
    inopt.endianness = 0;
    inopt.copy_comments = 0;
    inopt.rawmode = 1;
    inopt.ignorelength = 1;
    inopt.samplesize = 16;
    inopt.channels = 1;
    inopt.skip = 0;

    comment_init(&inopt.comments, &inopt.comments_length, opus_get_version_string());

    if (rate > 24000) {
        coding_rate = 48000;
    } else if (rate > 16000) {
        coding_rate = 24000;
    } else if (rate > 12000) {
        coding_rate = 16000;
    } else if (rate > 8000) {
        coding_rate = 12000;
    } else {
        coding_rate = 8000;
    }

    if (rate != coding_rate) {
        LOGE("Invalid rate");
        return 0;
    }

    header.channels = 1;
    header.channel_mapping = 0;
    header.input_sample_rate = rate;
    header.gain = inopt.gain;
    header.nb_streams = 1;

    int result = OPUS_OK;
    _encoder = opus_encoder_create(coding_rate, 1, OPUS_APPLICATION_AUDIO, &result);
    if (result != OPUS_OK) {
        LOGE("Error cannot create encoder: %s", opus_strerror(result));
        return 0;
    }

    min_bytes = max_frame_bytes = (1275 * 3 + 7) * header.nb_streams;
    _packet = (uint8_t *) malloc(max_frame_bytes);

    result = opus_encoder_ctl(_encoder, OPUS_SET_BITRATE(bitrate));
    if (result != OPUS_OK) {
        LOGE("Error OPUS_SET_BITRATE returned: %s", opus_strerror(result));
        return 0;
    }

#ifdef OPUS_SET_LSB_DEPTH
    result = opus_encoder_ctl(_encoder, OPUS_SET_LSB_DEPTH(max(8, min(24, inopt.samplesize))));
    if (result != OPUS_OK) {
        LOGE("Warning OPUS_SET_LSB_DEPTH returned: %s", opus_strerror(result));
    }
#endif

    opus_int32 lookahead;
    result = opus_encoder_ctl(_encoder, OPUS_GET_LOOKAHEAD(&lookahead));
    if (result != OPUS_OK) {
        LOGE("Error OPUS_GET_LOOKAHEAD returned: %s", opus_strerror(result));
        return 0;
    }

    inopt.skip += lookahead;
    header.preskip = (int) (inopt.skip * (48000.0 / coding_rate));
    inopt.extraout = (int) (header.preskip * (rate / 48000.0));

    if (ogg_stream_init(&os, rand()) == -1) {
        LOGE("Error: stream init failed");
        return 0;
    }

    unsigned char header_data[100];
    int packet_size = opus_header_to_packet(&header, header_data, 100);
    op.packet = header_data;
    op.bytes = packet_size;
    op.b_o_s = 1;
    op.e_o_s = 0;
    op.granulepos = 0;
    op.packetno = 0;
    ogg_stream_packetin(&os, &op);

    while ((result = ogg_stream_flush(&os, &og))) {
        if (!result) {
            break;
        }

        int pageBytesWritten = writeOggPage(&og, _fileOs);
        if (pageBytesWritten != og.header_len + og.body_len) {
            LOGE("Error: failed writing header to output stream");
            return 0;
        }
        bytes_written += pageBytesWritten;
        pages_out++;
    }

    comment_pad(&inopt.comments, &inopt.comments_length, comment_padding);
    op.packet = (unsigned char *) inopt.comments;
    op.bytes = inopt.comments_length;
    op.b_o_s = 0;
    op.e_o_s = 0;
    op.granulepos = 0;
    op.packetno = 1;
    ogg_stream_packetin(&os, &op);

    while ((result = ogg_stream_flush(&os, &og))) {
        if (result == 0) {
            break;
        }

        int writtenPageBytes = writeOggPage(&og, _fileOs);
        if (writtenPageBytes != og.header_len + og.body_len) {
            LOGE("Error: failed writing header to output stream");
            return 0;
        }

        bytes_written += writtenPageBytes;
        pages_out++;
    }

    free(inopt.comments);

    return 1;
}

void cleanupRecorder() {

}


#ifndef NELEM
# define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))
#endif


#define CLASS_HOLDER "com/ledboot/toffee/utils/MediaController"


static JNINativeMethod gMethods[] = {
        // name,                    signature,                funcPtrs
        {"startRecord",         "(Ljava/lang/String;)I",       (void *) startRecord},
        {"writeFrame",          "(Ljava/nio/ByteBuffer;I)I",   (void *) writeFrame},
        {"stopRecord",          "()V",                         (void *) stopRecord},
        {"openOpusFile",        "(Ljava/lang/String;)I",       (void *) openOpusFile},
        {"seekOpusFile",        "(F)I",                        (void *) seekOpusFile},
        {"isOpusFile",          "(Ljava/lang/String;)I",       (void *) isOpusFile},
        {"closeOpusFile",       "()V",                         (void *) closeOpusFile},
        {"readOpusFile",        "(Ljava/nio/ByteBuffer;I[I)V", (void *) readOpusFile},
        {"getTotalPcmDuration", "()J",                         (void *) getTotalPcmDuration},
        {"getWaveform",         "(Ljava/lang/String;)[B",      (void *) getWaveform},
        {"getWaveform2",        "([SI)[B",                     (void *) getWaveform2},
};

int register_jni(JNIEnv *env) {
    jclass mediaControllerClass = env->FindClass(CLASS_HOLDER);
    if (mediaControllerClass == NULL) {
        LOGD("can't find %s", CLASS_HOLDER);
        return -1;
    }
    int ret = env->RegisterNatives(mediaControllerClass, gMethods, NELEM(gMethods));
    if (ret != 0) {
        LOGD("regist method fail!");
        return -1;
    }
    env->DeleteLocalRef(mediaControllerClass);
    return ret;

}

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    g_JVM = vm;
    if ((vm->GetEnv((void **) &env, JNI_VERSION_1_4)) != JNI_OK) {
        return -1;
    }

    register_jni(env);
    return JNI_VERSION_1_4;
}