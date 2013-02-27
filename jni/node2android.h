/*
 * node2android.h
 *
 *  Created on: 2013-2-16
 *      Author: huangzhentao
 */

#ifndef NODE2ANDROID_H_
#define NODE2ANDROID_H_

#include <jni.h>

class Invoker
{
public:
	Invoker(JNIEnv* env, const char* classname, const char* method, const char* sig, bool isStatic = false);

	bool isReady();

	void runVoid(JNIEnv* env, ...);

	jint runInt(JNIEnv* env, ...);

	jobject runObject(JNIEnv* env, ...);

private:
    jclass mClass;
    jmethodID mMethodId;
    bool mStatic;
};


#endif /* NODE2ANDROID_H_ */
