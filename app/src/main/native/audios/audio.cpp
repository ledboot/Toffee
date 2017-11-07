//
// Created by Gwynn on 17/11/3.
//
#include <jni.h>
#include <stdlib.h>
#include "audio.h"
#include "../utils.h"

extern "C"{
    #include <opus.h>
}

JavaVM *g_JVM = NULL;

/*-----------         map java method             -----------*/

int startRecord(JNIEnv *env, jobject object, jstring path) {
    const char *pathStr = env->GetStringUTFChars(path,0);
    int result = initRecorder(pathStr);

    if(pathStr != 0){
        env->ReleaseStringUTFChars(path,pathStr);
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

int initRecorder(const char *path){
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
    header.preskip = (int)(inopt.skip * (48000.0 / coding_rate));
    inopt.extraout = (int)(header.preskip * (rate / 48000.0));

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
    op.packet = (unsigned char *)inopt.comments;
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

void cleanupRecorder(){

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