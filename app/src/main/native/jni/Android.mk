ROOT := $(call my-dir)

# Build libogg
include $(CLEAR_VARS)
LOCAL_PATH := $(ROOT)/ogg
LOCAL_MODULE := libogg
LOCAL_SRC_FILES := src/bitwise.c src/framing.c
LOCAL_CFLAGS := -I$(LOCAL_PATH)/include -DFIXED_POINT  -DFIXED_POINT -DUSE_KISS_FFT -DEXPORT=""	-D_ANDROID -fno-math-errno
include $(BUILD_STATIC_LIBRARY)

# Build libopusfile
include $(CLEAR_VARS)
LOCAL_PATH := $(ROOT)/opusfile
LOCAL_MODULE := libopusfile
LOCAL_SRC_FILES := src/http.c src/info.c src/internal.c src/opusfile.c src/stream.c src/wincerts.c
LOCAL_CFLAGS := -I$(LOCAL_PATH)/include -I$(ROOT)/ogg/include -I$(ROOT)/opus/celt -I$(ROOT)/opus/include  -I$(ROOT)/opus/silk -DFIXED_POINT -DUSE_KISS_FFT -DEXPORT=""  -UHAVE_CONFIG_H  -D_ANDROID -fno-math-errno	-DPACKAGE_NAME -DPACKAGE_VERSION
include $(BUILD_STATIC_LIBRARY)

# Build libopus
include $(CLEAR_VARS)
LOCAL_PATH := $(ROOT)/opus
LOCAL_MODULE := libopus

LOCAL_SRC_FILES := \
src/opus.c src/opus_decoder.c \
src/opus_encoder.c \
src/opus_multistream.c \
src/opus_multistream_encoder.c \
src/opus_multistream_decoder.c \
src/repacketizer.c \
src/analysis.c \
src/mlp.c \
src/mlp_data.c

LOCAL_SRC_FILES += \
silk/CNG.c \
silk/code_signs.c \
silk/init_decoder.c \
silk/decode_core.c \
silk/decode_frame.c \
silk/decode_parameters.c \
silk/decode_indices.c \
silk/decode_pulses.c \
silk/decoder_set_fs.c \
silk/dec_API.c \
silk/enc_API.c \
silk/encode_indices.c \
silk/encode_pulses.c \
silk/gain_quant.c \
silk/interpolate.c \
silk/LP_variable_cutoff.c \
silk/NLSF_decode.c \
silk/NSQ.c \
silk/NSQ_del_dec.c \
silk/PLC.c \
silk/shell_coder.c \
silk/tables_gain.c \
silk/tables_LTP.c \
silk/tables_NLSF_CB_NB_MB.c \
silk/tables_NLSF_CB_WB.c \
silk/tables_other.c \
silk/tables_pitch_lag.c \
silk/tables_pulses_per_block.c \
silk/VAD.c \
silk/control_audio_bandwidth.c \
silk/quant_LTP_gains.c \
silk/VQ_WMat_EC.c \
silk/HP_variable_cutoff.c \
silk/NLSF_encode.c \
silk/NLSF_VQ.c \
silk/NLSF_unpack.c \
silk/NLSF_del_dec_quant.c \
silk/process_NLSFs.c \
silk/stereo_LR_to_MS.c \
silk/stereo_MS_to_LR.c \
silk/check_control_input.c \
silk/control_SNR.c \
silk/init_encoder.c \
silk/control_codec.c \
silk/A2NLSF.c \
silk/ana_filt_bank_1.c \
silk/biquad_alt.c \
silk/bwexpander_32.c \
silk/bwexpander.c \
silk/debug.c \
silk/decode_pitch.c \
silk/inner_prod_aligned.c \
silk/lin2log.c \
silk/log2lin.c \
silk/LPC_analysis_filter.c \
silk/LPC_inv_pred_gain.c \
silk/table_LSF_cos.c \
silk/NLSF2A.c \
silk/NLSF_stabilize.c \
silk/NLSF_VQ_weights_laroia.c \
silk/pitch_est_tables.c \
silk/resampler.c \
silk/resampler_down2_3.c \
silk/resampler_down2.c \
silk/resampler_private_AR2.c \
silk/resampler_private_down_FIR.c \
silk/resampler_private_IIR_FIR.c \
silk/resampler_private_up2_HQ.c \
silk/resampler_rom.c \
silk/sigm_Q15.c \
silk/sort.c \
silk/sum_sqr_shift.c \
silk/stereo_decode_pred.c \
silk/stereo_encode_pred.c \
silk/stereo_find_predictor.c \
silk/stereo_quant_pred.c \
silk/LPC_fit.c

LOCAL_SRC_FILES += \
silk/fixed/LTP_analysis_filter_FIX.c \
silk/fixed/LTP_scale_ctrl_FIX.c \
silk/fixed/corrMatrix_FIX.c \
silk/fixed/encode_frame_FIX.c \
silk/fixed/find_LPC_FIX.c \
silk/fixed/find_LTP_FIX.c \
silk/fixed/find_pitch_lags_FIX.c \
silk/fixed/find_pred_coefs_FIX.c \
silk/fixed/noise_shape_analysis_FIX.c \
silk/fixed/process_gains_FIX.c \
silk/fixed/regularize_correlations_FIX.c \
silk/fixed/residual_energy16_FIX.c \
silk/fixed/residual_energy_FIX.c \
silk/fixed/warped_autocorrelation_FIX.c \
silk/fixed/apply_sine_window_FIX.c \
silk/fixed/autocorr_FIX.c \
silk/fixed/burg_modified_FIX.c \
silk/fixed/k2a_FIX.c \
silk/fixed/k2a_Q16_FIX.c \
silk/fixed/pitch_analysis_core_FIX.c \
silk/fixed/vector_ops_FIX.c \
silk/fixed/schur64_FIX.c \
silk/fixed/schur_FIX.c

LOCAL_SRC_FILES += \
celt/bands.c \
celt/celt.c \
celt/celt_encoder.c \
celt/celt_decoder.c \
celt/cwrs.c \
celt/entcode.c \
celt/entdec.c \
celt/entenc.c \
celt/kiss_fft.c \
celt/laplace.c \
celt/mathops.c \
celt/mdct.c \
celt/modes.c \
celt/pitch.c \
celt/celt_lpc.c \
celt/quant_bands.c \
celt/rate.c \
celt/vq.c \
celt/arm/armcpu.c \
celt/arm/arm_celt_map.c

LOCAL_C_INCLUDE := $(LOCAL_PATH)/celt $(LOCAL_PATH)/include $(LOCAL_PATH)/silk
LOCAL_CFLAGS := -I$(LOCAL_PATH)/celt -I$(LOCAL_PATH)/include -I$(LOCAL_PATH)/silk -Drestrict='' -D__EMX__ -DOPUS_BUILD -DFIXED_POINT -DUSE_ALLOCA -DHAVE_LRINT -DHAVE_LRINTF -O3 -fno-math-errno
include $(BUILD_STATIC_LIBRARY)


# Build library
include $(CLEAR_VARS)
LOCAL_PATH := $(ROOT)/audios
LOCAL_MODULE := libtoffee
LOCAL_SRC_FILES := audio.cpp


LOCAL_LDLIBS  := -llog -landroid
LOCAL_CFLAGS := -I$(ROOT)/audios -I$(ROOT)/ogg/include -I$(ROOT)/opus/include -I$(ROOT)/opus/celt -I$(ROOT)/opus/silk -I$(ROOT)/opusfile/include -fno-math-errno
LOCAL_STATIC_LIBRARIES := libogg libopus libopusfile
include $(BUILD_SHARED_LIBRARY)
