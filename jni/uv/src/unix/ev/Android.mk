LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := libev

LOCAL_CFLAGS := -DNDEBUG \
	-DEV_SELECT_USE_FD_SET \
        -DHAVE_CONFIG_H \
	-DEV_CONFIG_H="\"config_android.h\"" \

LOCAL_SRC_FILES := \
	ev.c \
	event.c


LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/. \
	$(LOCAL_PATH)/../../../include/uv-private/.
	
include $(BUILD_STATIC_LIBRARY)
