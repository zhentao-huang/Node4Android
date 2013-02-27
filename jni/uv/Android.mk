LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE	:= libuv

LOCAL_CFLAGS := -DNDEBUG \
				-DHAVE_CONFIG_H \
				-DEV_CONFIG_H="\"ev_config_android.h\"" \
				-DEIO_CONFIG_H="\"eio_config_android.h\""
				-DEV_MULTIPLICITY=0
				
LOCAL_SRC_FILES := \
	src/ares/ares__close_sockets.c \
	src/ares/ares__get_hostent.c \
	src/ares/ares__read_line.c \
	src/ares/ares__timeval.c \
	src/ares/ares_cancel.c \
	src/ares/ares_data.c \
	src/ares/ares_destroy.c \
	src/ares/ares_expand_name.c \
	src/ares/ares_expand_string.c \
	src/ares/ares_fds.c \
	src/ares/ares_free_hostent.c \
	src/ares/ares_free_string.c \
	src/ares/ares_getenv.c \
	src/ares/ares_gethostbyaddr.c \
	src/ares/ares_gethostbyname.c \
	src/ares/ares_getnameinfo.c \
	src/ares/ares_getopt.c \
	src/ares/ares_getsock.c \
	src/ares/ares_init.c \
	src/ares/ares_library_init.c \
	src/ares/ares_llist.c \
	src/ares/ares_mkquery.c \
	src/ares/ares_nowarn.c \
	src/ares/ares_options.c \
	src/ares/ares_parse_a_reply.c \
	src/ares/ares_parse_aaaa_reply.c \
	src/ares/ares_parse_mx_reply.c \
	src/ares/ares_parse_ns_reply.c \
	src/ares/ares_parse_ptr_reply.c \
	src/ares/ares_parse_srv_reply.c \
	src/ares/ares_parse_txt_reply.c \
	src/ares/ares_platform.c \
	src/ares/ares_process.c \
	src/ares/ares_query.c \
	src/ares/ares_search.c \
	src/ares/ares_send.c \
	src/ares/ares_strcasecmp.c \
	src/ares/ares_strdup.c \
	src/ares/ares_strerror.c \
	src/ares/ares_timeout.c \
	src/ares/ares_version.c \
	src/ares/ares_writev.c \
	src/ares/bitncmp.c \
	src/ares/inet_net_pton.c \
	src/ares/inet_ntop.c \
	src/uv-common.c \
	src/cares.c \
	src/fs-poll.c \
	src/unix/async.c \
	src/unix/core.c \
	src/unix/error.c \
	src/unix/fs.c \
	src/unix/loop.c \
	src/unix/loop-watcher.c \
	src/unix/pipe.c \
	src/unix/process.c \
	src/unix/poll.c \
	src/unix/stream.c \
	src/unix/tcp.c \
	src/unix/thread.c \
	src/unix/timer.c \
	src/unix/tty.c \
	src/unix/udp.c \
	src/unix/uv-eio.c \
	src/unix/linux/inotify.c \
	src/unix/linux/syscalls.c \
	src/unix/linux/linux-core.c \
        src/unix/dl.c
#src/unix/linux.c \
	#src/unix/eio/eio.c \
	# src/unix/ev/ev.c \
	# src/unix/ev/event.c \
#src/unix/cares.c \
#src/unix/linux.c \

LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/src/. \
	$(LOCAL_PATH)/src/ares/. \
	$(LOCAL_PATH)/src/ares/config_android/. \
	$(LOCAL_PATH)/src/unix/. \
	$(LOCAL_PATH)/include/. \
	$(LOCAL_PATH)/include/uv-private/. \
	$(LOCAL_PATH)/test/. \
	# $(LOCAL_PATH)/src/unix/ev/. \
	#$(LOCAL_PATH)/src/unix/eio/. \

include $(BUILD_STATIC_LIBRARY)
