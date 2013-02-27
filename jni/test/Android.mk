LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := libtest

LOCAL_CFLAGS := -DDEBUG \

LOCAL_SRC_FILES := \
	src/test.cc \
	gen/test-js.cc


LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/src/. \
	$(LOCAL_PATH)/include/. \
	$(LOCAL_PATH)/../v8/include/. \
	$(LOCAL_PATH)/../v8/src/.

LOCAL_CPP_EXTENSION := .cc

LOCAL_SHARED_LIBRARIES := libstlport.so

LOCAL_LDLIBS := -llog -landroid

include $(BUILD_STATIC_LIBRARY)
