LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := libnode

ifeq ($(APP_OPTIM), debug)
	LOCAL_CFLAGS := -g -DDEBUG \
				-DNODE_WANT_INTERNALS -DEV_FORK_ENABLE=0 \
				-DEV_EMBED_ENABLE=0 \
				-DX_STACKSIZE=65536 \
				-DHAVE_FDATASYNC=0 \
				-D__POSIX__=1 \
				-DHAVE_MONOTONIC_CLOCK=1 \
				-DPLATFORM=\"android\" \
				-DARCH=\"arm\" \
				-D_LARGEFILE_SOURCE \
				-D_FILE_OFFSET_BITS=64 \
				-D_FORTIFY_SOURCE=2 \
				-DHAVE_CONFIG_H=1 \
				-DHAVE_OPENSSL=0 
else
	LOCAL_CFLAGS := -DNODE_WANT_INTERNALS -DEV_FORK_ENABLE=0 \
				-DEV_EMBED_ENABLE=0 \
				-DX_STACKSIZE=65536 \
				-DHAVE_FDATASYNC=0 \
				-D__POSIX__=1 \
				-DHAVE_MONOTONIC_CLOCK=1 \
				-DPLATFORM=\"android\" \
				-DARCH=\"arm\" \
				-D_LARGEFILE_SOURCE \
				-D_FILE_OFFSET_BITS=64 \
				-D_FORTIFY_SOURCE=2 \
				-DHAVE_CONFIG_H=1 \
				-DHAVE_OPENSSL=0 
endif
				
LOCAL_SRC_FILES := \
	src/node_crypto.cc \
	src/node_javascript.cc \
	src/node.cc \
	src/cares_wrap.cc \
	src/fs_event_wrap.cc \
	src/handle_wrap.cc \
	src/node_buffer.cc \
	src/node_constants.cc \
	src/node_dtrace.cc \
	src/node_extensions.cc \
	src/node_file.cc \
	src/node_http_parser.cc \
	src/node_io_watcher.cc \
	src/node_os.cc \
	src/node_script.cc \
	src/node_signal_watcher.cc \
	src/node_stat_watcher.cc \
	src/node_string.cc \
	src/node_zlib.cc \
	src/pipe_wrap.cc \
	src/process_wrap.cc \
	src/stream_wrap.cc \
	src/tcp_wrap.cc \
	src/timer_wrap.cc \
	src/tty_wrap.cc \
	src/udp_wrap.cc \
	src/v8_typed_array.cc \
	src/platform_android.cc \
	src/slab_allocator.cc \
	src/stream_wrap.cc \
	src/udp_wrap.cc \
#src/node_stdio.cc \
#src/node_cares.cc \
#src/node_child_process.cc \
#src/node_net.cc \
#src/node_timer.cc \
#src/stdio_wrap.cc \

LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/src/. \
	$(LOCAL_PATH)/../v8/include/. \
	$(LOCAL_PATH)/../http_parser/. \
	$(LOCAL_PATH)/../uv/src/. \
	$(LOCAL_PATH)/../uv/src/ares/. \
	$(LOCAL_PATH)/../uv/src/unix/. \
	$(LOCAL_PATH)/../uv/src/unix/eio/. \
	$(LOCAL_PATH)/../uv/src/unix/ev/. \
	$(LOCAL_PATH)/../uv/include/. \
	$(LOCAL_PATH)/../uv/include/uv-private/. \
	$(LOCAL_PATH)/../openssl/include/. \
	$(LOCAL_PATH)/../libpng/. \
	$(LOCAL_PATH)/../qrencode/. \
	# $(LOCAL_PATH)/../uv/src/ares/config_android/. \

LOCAL_CPP_EXTENSION := .cc

include $(BUILD_STATIC_LIBRARY)
