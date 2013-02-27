LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := libev

LOCAL_CFLAGS := -DDEBUG \

LOCAL_SRC_FILES := \
	ev.c \
	event.c


LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/.
	
include $(BUILD_STATIC_LIBRARY)
