/*
 * node2android.cpp
 *
 *  Created on: 2013-2-16
 *      Author: huangzhentao
 */
#include <stdio.h>
#include <stdarg.h>
#include "node2android.h"

Invoker::Invoker(JNIEnv* env, const char* classname, const char* method, const char* sig, bool isStatic)
	: mClass(0), mMethodId(0)
{
	mClass = env->FindClass(classname);
	if (mClass != 0)
	{
		if (isStatic)
		{
			mMethodId = env->GetStaticMethodID(mClass, method, sig);
		}
		else
		{
			mMethodId = env->GetMethodID(mClass, method, sig);
		}

		mStatic = isStatic;
	}
}

void Invoker::runVoid(JNIEnv* env, ...)
{
	va_list ap, ap2;
	if (isReady())
	{
		va_start(ap, env);
		if (mStatic)
		{
			env->CallStaticVoidMethodV(mClass, mMethodId, ap);
		}
		else
		{
			jobject obj = va_arg(ap, jobject);
			va_copy(ap2, ap);
			env->CallVoidMethodV(obj, mMethodId, ap2);
			va_end(ap2);
		}
		va_end(ap);
	}
}

jint Invoker::runInt(JNIEnv* env, ...)
{
	va_list ap, ap2;
	jint result;
	if (isReady())
	{
		va_start(ap, env);
		if (mStatic)
		{
			result = env->CallStaticIntMethodV(mClass, mMethodId, ap);
		}
		else
		{
			jobject obj = va_arg(ap, jobject);
			va_copy(ap2, ap);
			result = env->CallIntMethodV(obj, mMethodId, ap2);
			va_end(ap2);
		}
		va_end(ap);
	}

	return result;
}

jobject Invoker::runObject(JNIEnv* env, ...)
{
	va_list ap, ap2;
	jobject result;
	if (isReady())
	{
		va_start(ap, env);
		if (mStatic)
		{
			result = env->CallStaticObjectMethodV(mClass, mMethodId, ap);
		}
		else
		{
			jobject obj = va_arg(ap, jobject);
			va_copy(ap2, ap);
			result = env->CallObjectMethodV(obj, mMethodId, ap2);
			va_end(ap2);
		}
		va_end(ap);
	}
	return result;
}

bool Invoker::isReady()
{
	return (mClass != 0 && mMethodId != 0);
}


