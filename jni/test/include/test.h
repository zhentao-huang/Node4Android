#ifndef TEST_H_
#define TEST_H_

#include <v8.h>
#include "utils.h"

typedef uint8_t byte;

using namespace v8;

namespace test {


class Test {
public:
	static int Main(int argc, char* argv[]);
  	static void Exit(int exit_code);

  	static void Initialize();
    static Handle<Value> RunTestScript();

private:	
	static Handle<Value> Version(const Arguments& args);
  
  
	static Persistent<Context> test_context_;
	
  
  	static Handle<ObjectTemplate> CreateGlobalTemplate();
};

enum NativeType {
  CORE, EXPERIMENTAL, TEST
};

template <NativeType type>
class NativesCollection {
 public:
  // Number of built-in scripts.
  static int GetBuiltinsCount();
  // Number of debugger implementation scripts.
  static int GetDebuggerCount();

  // These are used to access built-in scripts.  The debugger implementation
  // scripts have an index in the interval [0, GetDebuggerCount()).  The
  // non-debugger scripts have an index in the interval [GetDebuggerCount(),
  // GetNativesCount()).
  static int GetIndex(const char* name);
  static int GetRawScriptsSize();
  static v8::internal::Vector<const char> GetRawScriptSource(int index);
  static v8::internal::Vector<const char> GetScriptName(int index);
  static v8::internal::Vector<const byte> GetScriptsSource();
  static void SetRawScriptsSource(v8::internal::Vector<const char> raw_source);
};


}  // namespace test


#endif  // TEST_H_
  