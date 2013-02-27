LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := libqrencode

LOCAL_CFLAGS := -DHAVE_CONFIG_H=1
				
LOCAL_SRC_FILES := \
                 qrencode.c \
                 qrinput.c \
                 bitstream.c \
                 qrspec.c \
                 rscode.c \
                 split.c \
                 mask.c \
                 mqrspec.c \
                 mmask.c

LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/. \

include $(BUILD_STATIC_LIBRARY)
