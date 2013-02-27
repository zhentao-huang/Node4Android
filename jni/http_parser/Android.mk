LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := libhttp_parser

LOCAL_CFLAGS := -DDEBUG \

LOCAL_SRC_FILES := \
	http_parser.c \
	test.c \

LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/.
	
include $(BUILD_STATIC_LIBRARY)
