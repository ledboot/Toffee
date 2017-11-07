//
// Created by Gwynn on 17/11/6.
//

#ifndef AUDIO_H
#define AUDIO_H

#include <ogg/os_types.h>
#include <ogg/ogg.h>
#include <stdio.h>

/* ----          field                ---- */

const opus_int32 bitrate = 16000;
const opus_int32 rate = 16000;
const opus_int32 frame_size = 960;
const int with_cvbr = 1;
const int max_ogg_delay = 0;
const int comment_padding = 512;

/* ----          struct                ---- */

typedef struct {
    int version;
    int channels; /* Number of channels: 1..255 */
    int preskip;
    ogg_uint32_t input_sample_rate;
    int gain; /* in dB S7.8 should be zero whenever possible */
    int channel_mapping;
    /* The rest is only used if channel_mapping != 0 */
    int nb_streams;
    int nb_coupled;
    unsigned char stream_map[255];
} OpusHeader;


typedef struct {
    unsigned char *data;
    int maxlen;
    int pos;
} Packet;

typedef struct {
    const unsigned char *data;
    int maxlen;
    int pos;
} ROPacket;

typedef struct {
    void *readdata;
    opus_int64 total_samples_per_channel;
    int rawmode;
    int channels;
    long rate;
    int gain;
    int samplesize;
    int endianness;
    char *infilename;
    int ignorelength;
    int skip;
    int extraout;
    char *comments;
    int comments_length;
    int copy_comments;
} oe_enc_opt;

/* ----          method                ---- */

static int write_uint32(Packet *p, ogg_uint32_t val);

static int write_uint16(Packet *p, ogg_uint16_t val);

static int write_chars(Packet *p, const unsigned char *str, int nb_chars);

static int read_uint32(ROPacket *p, ogg_uint32_t *val);

static int read_uint16(ROPacket *p, ogg_uint16_t *val);

static int read_chars(ROPacket *p, unsigned char *str, int nb_chars);

static void comment_init(char **comments, int *length, const char *vendor_string);

static void comment_pad(char **comments, int* length, int amount);

static int writeOggPage(ogg_page *page, FILE *os);

int opus_header_to_packet(const OpusHeader *h, unsigned char *packet, int len);

int initRecorder(const char *path);

void cleanupRecorder();

void cleanupPlayer();


#endif //AUDIO_H
