LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := libeio

LOCAL_CFLAGS := -DDEBUG \

LOCAL_SRC_FILES := \
	eio.c \
	demo.c \

LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/.
	
include $(BUILD_STATIC_LIBRARY)
