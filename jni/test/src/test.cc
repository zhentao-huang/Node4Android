#include <errno.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>

#include <unistd.h>

#include <v8.h>
#include "utils.h"

#include "test.h"

using namespace v8;

namespace test {

Persistent<Context> Test::test_context_;

Handle<Value> Test::RunTestScript() {
  HandleScope handle_scope;
  Context::Scope test_scope(test_context_);

  // Run the test script in the test context
  int source_index = NativesCollection<TEST>::GetIndex("test");
  v8::internal::Vector<const char> test_source =
      NativesCollection<test::TEST>::GetRawScriptSource(source_index);
  v8::internal::Vector<const char> test_source_name =
      NativesCollection<test::TEST>::GetScriptName(source_index);
  Handle<String> source = String::New(test_source.start(),
      test_source.length());
  Handle<String> name = String::New(test_source_name.start(),
      test_source_name.length());
  Handle<Script> script = Script::Compile(source, name);
  Handle<Value> result = script->Run();
  return handle_scope.Close(result);
}

Handle<Value> Test::Version(const Arguments& args) {
  return String::New(V8::GetVersion());
}

Handle<ObjectTemplate> Test::CreateGlobalTemplate() {
  Handle<ObjectTemplate> global_template = ObjectTemplate::New();
  global_template->Set(String::New("version"), FunctionTemplate::New(Version));
  return global_template;
}

void Test::Initialize() {
  HandleScope handle_scope;
  Handle<ObjectTemplate> global_template = CreateGlobalTemplate();
  test_context_ = Context::New(NULL, global_template);
}

int Test::Main(int argc, char* argv[]) {
  Initialize();
}

void Test::Exit(int exit_code) {
  // Use _exit instead of exit to avoid races between isolate
  // threads and static destructors.
  fflush(stdout);
  fflush(stderr);
  _exit(exit_code);
}

}  // namespace test

int main(int argc, char* argv[]) {
	test::Test::Main(argc, argv);
	test::Test::Exit(0);
}
