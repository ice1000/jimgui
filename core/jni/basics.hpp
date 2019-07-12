///
/// Created by ice1000
///

#include "jni.h"

#ifndef __JIMGUI_BASICS_HPP__
#define __JIMGUI_BASICS_HPP__

template<typename T>
using Ptr = T *;

template<typename T>
using Com = T const &;

template<typename T>
using Ref = T &;

using RawStr = Ptr<const char>;

#define __release(type, name) \
if (_ ## name != nullptr) env->Release ## type ## ArrayElements(_ ## name, name, JNI_OK);

#define __abort(type, name) \
env->Release ## type ## ArrayElements(_ ## name, name, JNI_ABORT);

#define __get(type, name) \
auto name = _ ## name == nullptr ? nullptr : env->Get ## type ## ArrayElements(_ ## name, nullptr);

#define __new(type, name, len) \
auto _ ## name = env->New ## type ## Array(len);

#define __set(type, name, len) \
env->Set ## type ## ArrayRegion(_ ## name, 0, (len), name);

#define __init(type, name, len) \
auto _ ## name = env->New ## type ## Array(len); \
env->Set ## type ## ArrayRegion(_ ## name, 0, (len), name);

#define __len(name) \
env->GetArrayLength(_ ## name)

#define STR_J2C(value) \
reinterpret_cast<Ptr<const char>> ((value))

#define PTR_C2J(value) \
reinterpret_cast<jlong> ((value))

#define PTR_J2C(type, value) \
reinterpret_cast<Ptr<type>> ((value))

#undef _

#endif // __JIMGUI_BASICS_HPP__
