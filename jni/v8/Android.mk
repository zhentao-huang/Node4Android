LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE	:= libv8

LOCAL_CFLAGS := -DV8_ANDROID_LOG_STDOUT \
				-DENABLE_DEBUGGER_SUPPORT \
                -DNDEBUG \
				-DOBJECT_PRINT \
				-DENABLE_DISASSEMBLER

LOCAL_SRC_FILES := \
	src/accessors.cc \
	src/allocation.cc \
	src/api.cc \
	src/assembler.cc \
	src/ast.cc \
	src/bignum.cc \
	src/bignum-dtoa.cc \
	src/bootstrapper.cc \
	src/builtins.cc \
	src/cached-powers.cc \
	src/checks.cc \
	src/circular-queue.cc \
	src/code-stubs.cc \
	src/codegen.cc \
	src/compilation-cache.cc \
	src/compiler.cc \
	src/contexts.cc \
	src/conversions.cc \
	src/counters.cc \
	src/cpu-profiler.cc \
	src/d8-debug.cc \
	src/d8-posix.cc \
	src/d8.cc \
	src/data-flow.cc \
	src/dateparser.cc \
	src/date.cc \
	src/debug-agent.cc \
	src/debug.cc \
	src/deoptimizer.cc \
	src/disassembler.cc \
	src/diy-fp.cc \
	src/dtoa.cc \
	src/elements.cc \
	src/elements-kind.cc \
	src/execution.cc \
	src/factory.cc \
	src/flags.cc \
	src/frames.cc \
	src/full-codegen.cc \
	src/func-name-inferrer.cc \
	src/gdb-jit.cc \
	src/global-handles.cc \
	src/fast-dtoa.cc \
	src/fixed-dtoa.cc \
	src/handles.cc \
	src/heap-profiler.cc \
	src/heap.cc \
	src/hydrogen.cc \
	src/hydrogen-instructions.cc \
	src/ic.cc \
	src/incremental-marking.cc \
	src/inspector.cc \
	src/interface.cc \
	src/interpreter-irregexp.cc \
	src/isolate.cc \
	src/jsregexp.cc \
	src/lithium-allocator.cc \
	src/lithium.cc \
	src/liveedit.cc \
	src/liveobjectlist.cc \
	src/log-utils.cc \
	src/log.cc \
	src/mark-compact.cc \
	src/messages.cc \
	src/mksnapshot.cc \
	src/objects-debug.cc \
	src/objects-printer.cc \
	src/objects-visiting.cc \
	src/objects.cc \
	src/once.cc \
	src/parser.cc \
	src/platform-linux.cc \
	src/platform-posix.cc \
	src/preparser.cc \
	src/preparse-data.cc \
	src/preparser-api.cc \
	src/prettyprinter.cc \
	src/profile-generator.cc \
	src/property.cc \
	src/regexp-macro-assembler-irregexp.cc \
	src/regexp-macro-assembler-tracer.cc \
	src/regexp-macro-assembler.cc \
	src/regexp-stack.cc \
	src/rewriter.cc \
	src/runtime-profiler.cc \
	src/runtime.cc \
	src/safepoint-table.cc \
	src/scanner-character-streams.cc \
	src/scanner.cc \
	src/scopeinfo.cc \
	src/scopes.cc \
	src/serialize.cc \
	src/snapshot-common.cc \
	src/snapshot-empty.cc \
	src/spaces.cc \
	src/store-buffer.cc \
	src/string-search.cc \
	src/string-stream.cc \
	src/strtod.cc \
	src/stub-cache.cc \
	src/token.cc \
	src/type-info.cc \
	src/unicode.cc \
	src/utils.cc \
	src/v8-counters.cc \
	src/v8.cc \
	src/v8conversions.cc \
	src/v8dll-main.cc \
	src/v8threads.cc \
	src/v8utils.cc \
	src/variables.cc \
	src/version.cc \
	src/zone.cc \
	src/extensions/gc-extension.cc \
	src/extensions/externalize-string-extension.cc \
	src/arm/builtins-arm.cc \
	src/arm/code-stubs-arm.cc \
	src/arm/codegen-arm.cc \
	src/arm/constants-arm.cc \
	src/arm/cpu-arm.cc \
	src/arm/debug-arm.cc \
	src/arm/deoptimizer-arm.cc \
	src/arm/disasm-arm.cc \
	src/arm/frames-arm.cc \
	src/arm/full-codegen-arm.cc \
	src/arm/ic-arm.cc \
	src/arm/lithium-arm.cc \
	src/arm/lithium-codegen-arm.cc \
	src/arm/lithium-gap-resolver-arm.cc \
	src/arm/macro-assembler-arm.cc \
	src/arm/regexp-macro-assembler-arm.cc \
	src/arm/stub-cache-arm.cc \
	src/arm/assembler-arm.cc \
	out/native/obj/gen/experimental-libraries.cc \
	out/native/obj/gen/libraries.cc \
#	out/native/obj/gen/resources.cc \
#src/hashmap.cc \
#out/native/obj/gen/d8-js.cc \


LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/src/. \
	$(LOCAL_PATH)/src/arm/. \
	$(LOCAL_PATH)/include/. \

LOCAL_CPP_EXTENSION := .cc

LOCAL_SHARED_LIBRARIES := libstlport.so

LOCAL_LDLIBS := -llog -landroid

include $(BUILD_STATIC_LIBRARY)
