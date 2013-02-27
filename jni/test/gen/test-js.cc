// Copyright 2011 Google Inc. All Rights Reserved.

// This file was generated from .js source files by SCons.  If you
// want to make changes to this file you should either change the
// javascript source files or the SConstruct script.

#include <v8.h>
#include "utils.h"

#include "test.h"

namespace test {

  static const byte sources[] = { 118, 101, 114, 115, 105, 111, 110, 40, 41, 59, 10, 10, 10 };

  static const char* raw_sources = reinterpret_cast<const char*>(sources);

  template <>
  int NativesCollection<TEST>::GetBuiltinsCount() {
    return 1;
  }

  template <>
  int NativesCollection<TEST>::GetDebuggerCount() {
    return 0;
  }

  template <>
  int NativesCollection<TEST>::GetIndex(const char* name) {
    if (strcmp(name, "test") == 0) return 0;
    return -1;
  }

  template <>
  int NativesCollection<TEST>::GetRawScriptsSize() {
    return 13;
  }

  template <>
  v8::internal::Vector<const char> NativesCollection<TEST>::GetRawScriptSource(int index) {
    if (index == 0) return v8::internal::Vector<const char>(raw_sources + 0, 13);
    return v8::internal::Vector<const char>("", 0);
  }

  template <>
  v8::internal::Vector<const char> NativesCollection<TEST>::GetScriptName(int index) {
    if (index == 0) return v8::internal::Vector<const char>("native test.js", 14);
    return v8::internal::Vector<const char>("", 0);
  }

  template <>
  v8::internal::Vector<const byte> NativesCollection<TEST>::GetScriptsSource() {
    return v8::internal::Vector<const byte>(sources, 13);
  }

  template <>
  void NativesCollection<TEST>::SetRawScriptsSource(v8::internal::Vector<const char> raw_source) {
    ASSERT(13 == raw_source.length());
    raw_sources = raw_source.start();
  }

}  // namespace test
