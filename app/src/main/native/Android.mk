LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := toffee

LOCAL_SRC_FILES := audios/audio.cpp

LOCAL_C_INCLUDE :=  ${LOCAL_PATH}/oupus/include \
                    ${LOCAL_PATH}/oupus/celt \
                    ${LOCAL_PATH}/oupus/ogg \
                    ${{LOCAL_PATH}/oupus/opusfile  \
                    ${{LOCAL_PATH}/opus/silk \
                    ${{LOCAL_PATH}/opus/silk/fixed \

LOCAL_LDLIBS  := -llog

include $(BUILD_SHARED_LIBRARY)
