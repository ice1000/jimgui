///
/// Created by ice1000 on 18-5-14.
///

#include "basics.hpp"

#include <string>
#include <org_ice1000_jimgui_NativeBool.h>
#include <org_ice1000_jimgui_NativeString.h>
#include <org_ice1000_jimgui_NativeInt.h>
#include <org_ice1000_jimgui_NativeFloat.h>
#include <org_ice1000_jimgui_NativeDouble.h>
#include <org_ice1000_jimgui_NativeLong.h>
#include <org_ice1000_jimgui_NativeShort.h>

#ifndef WIN32
#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"
#endif

extern "C" {

#define NativeImpls(JavaName, JavaType, CppType, InitValue) \
JNIEXPORT JavaType JNICALL \
Java_org_ice1000_jimgui_Native ## JavaName ## _accessValue(JNIEnv *, jclass, jlong nativeObjectPtr) { \
  auto *nativeObject = PTR_J2C(CppType, nativeObjectPtr); \
  return static_cast<JavaType> (*nativeObject); \
} \
JNIEXPORT void JNICALL \
Java_org_ice1000_jimgui_Native ## JavaName ## _modifyValue(JNIEnv *, jclass, jlong nativeObjectPtr, JavaType newValue) { \
  auto *nativeObject = PTR_J2C(CppType, nativeObjectPtr); \
  *nativeObject = static_cast<CppType> (newValue); \
} \
JNIEXPORT void JNICALL \
Java_org_ice1000_jimgui_Native ## JavaName ## _increaseValue(JNIEnv *, jclass, jlong nativeObjectPtr, JavaType increment) { \
  auto *nativeObject = PTR_J2C(CppType, nativeObjectPtr); \
  *nativeObject += static_cast<CppType> (increment); \
} \
JNIEXPORT jlong JNICALL \
Java_org_ice1000_jimgui_Native ## JavaName ## _allocateNativeObject(JNIEnv *, jclass) { \
  return PTR_C2J(new CppType(InitValue)); \
} \
JNIEXPORT void JNICALL \
Java_org_ice1000_jimgui_Native ## JavaName ## _deallocateNativeObject0(JNIEnv *, jclass, jlong nativeObjectPtr) { \
  auto *nativeObject = PTR_J2C(CppType, nativeObjectPtr); \
  delete nativeObject; \
} \
JNIEXPORT JavaType JNICALL \
JavaCritical_org_ice1000_jimgui_Native ## JavaName ## _accessValue(jlong nativeObjectPtr) { \
  auto *nativeObject = PTR_J2C(CppType, nativeObjectPtr); \
  return static_cast<JavaType> (*nativeObject); \
} \
JNIEXPORT void JNICALL \
JavaCritical_org_ice1000_jimgui_Native ## JavaName ## _modifyValue(jlong nativeObjectPtr, JavaType newValue) { \
  auto *nativeObject = PTR_J2C(CppType, nativeObjectPtr); \
  *nativeObject = static_cast<CppType> (newValue); \
} \
JNIEXPORT void JNICALL \
JavaCritical_org_ice1000_jimgui_Native ## JavaName ## _increaseValue(jlong nativeObjectPtr, JavaType increment) { \
  auto *nativeObject = PTR_J2C(CppType, nativeObjectPtr); \
  *nativeObject += static_cast<CppType> (increment); \
} \
JNIEXPORT jlong JNICALL \
JavaCritical_org_ice1000_jimgui_Native ## JavaName ## _allocateNativeObject() { \
  return PTR_C2J(new CppType(InitValue)); \
} \
JNIEXPORT void JNICALL \
JavaCritical_org_ice1000_jimgui_Native ## JavaName ## _deallocateNativeObject0(jlong nativeObjectPtr) { \
  auto *nativeObject = PTR_J2C(CppType, nativeObjectPtr); \
  delete nativeObject; \
}

NativeImpls(Int, jint, int, 0)
NativeImpls(Float, jfloat, float, 0)
NativeImpls(Double, jdouble, double, 0)
NativeImpls(Short, jshort, short, 0)
NativeImpls(Long, jlong, long, 0)

#undef NativeImpls

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_NativeBool_accessValue(jlong nativeObjectPtr) -> jboolean {
  auto nativeObject = PTR_J2C(bool, nativeObjectPtr);
  return static_cast<jboolean>(*nativeObject ? JNI_TRUE : JNI_FALSE);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_NativeBool_modifyValue(jlong nativeObjectPtr, jboolean newValue) {
  auto nativeObject = PTR_J2C(bool, nativeObjectPtr);
  *nativeObject = static_cast<bool>(newValue);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_NativeBool_invertValue(jlong nativeObjectPtr) {
  auto *nativeObject = PTR_J2C(bool, nativeObjectPtr);
  *nativeObject = !*nativeObject;
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_NativeBool_allocateNativeObject() -> jlong {
  return PTR_C2J(new bool(false));
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_NativeBool_deallocateNativeObject0(jlong nativeObjectPtr) {
  auto nativeObject = PTR_J2C(bool, nativeObjectPtr);
  delete nativeObject;
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_NativeBool_accessValue(JNIEnv *, jclass, jlong nativeObjectPtr) -> jboolean {
  return JavaCritical_org_ice1000_jimgui_NativeBool_accessValue(nativeObjectPtr);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_NativeBool_modifyValue(JNIEnv *, jclass, jlong nativeObjectPtr, jboolean newValue) {
  JavaCritical_org_ice1000_jimgui_NativeBool_modifyValue(nativeObjectPtr, newValue);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_NativeBool_invertValue(JNIEnv *, jclass, jlong nativeObjectPtr) {
  JavaCritical_org_ice1000_jimgui_NativeBool_invertValue(nativeObjectPtr);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_NativeBool_allocateNativeObject(JNIEnv *, jclass) -> jlong {
  return JavaCritical_org_ice1000_jimgui_NativeBool_allocateNativeObject();
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_NativeBool_deallocateNativeObject0(JNIEnv *, jclass, jlong nativeObjectPtr) {
  JavaCritical_org_ice1000_jimgui_NativeBool_deallocateNativeObject0(nativeObjectPtr);
}

JNIEXPORT jlong JNICALL
JavaCritical_org_ice1000_jimgui_NativeString_allocateNativeObject() {
  return PTR_C2J(new std::string());
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_NativeString_deallocateNativeObject0(jlong nativeObjectPtr) {
  auto nativeObject = PTR_J2C(std::string, nativeObjectPtr);
  delete nativeObject;
}

JNIEXPORT jbyte JNICALL
JavaCritical_org_ice1000_jimgui_NativeString_byteAt(jlong nativeObjectPtr, jint at) {
  auto nativeObject = PTR_J2C(std::string, nativeObjectPtr);
  return nativeObject->at(at);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_NativeString_setByteAt(jlong nativeObjectPtr, jint at, jbyte newValue) {
  auto nativeObject = PTR_J2C(std::string, nativeObjectPtr);
  nativeObject->at(at) = newValue;
}

JNIEXPORT jint JNICALL
JavaCritical_org_ice1000_jimgui_NativeString_length(jlong nativeObjectPtr) {
  auto nativeObject = PTR_J2C(std::string, nativeObjectPtr);
  return nativeObject->size();
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_NativeString_clear(jlong nativeObjectPtr) {
  auto nativeObject = PTR_J2C(std::string, nativeObjectPtr);
  nativeObject->clear();
}

JNIEXPORT jlong JNICALL
JavaCritical_org_ice1000_jimgui_NativeString_substring(jlong nativeObjectPtr, jint start, jint end) {
  auto nativeObject = PTR_J2C(std::string, nativeObjectPtr);
  auto newStr = new std::string(nativeObject->substr(start, end));
  return PTR_C2J(newStr);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_NativeString_readValues(jlong nativeObjectPtr, jint, jbyte* buf) {
  auto nativeObject = PTR_J2C(std::string, nativeObjectPtr);
  auto size = nativeObject->size();
  for (int i = 0; i < size; ++i) {
    buf[i] = nativeObject->at(i);
  }
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_NativeString_appendChar(jlong nativeObjectPtr, jbyte newChar) {
  auto nativeObject = PTR_J2C(std::string, nativeObjectPtr);
  nativeObject->push_back(newChar);
}

JNIEXPORT jlong JNICALL
Java_org_ice1000_jimgui_NativeString_allocateNativeObject(JNIEnv *, jclass) {
  return JavaCritical_org_ice1000_jimgui_NativeString_allocateNativeObject();
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_NativeString_deallocateNativeObject0(JNIEnv *, jclass, jlong nativeObjectPtr) {
  JavaCritical_org_ice1000_jimgui_NativeString_deallocateNativeObject0(nativeObjectPtr);
}

JNIEXPORT jbyte JNICALL
Java_org_ice1000_jimgui_NativeString_byteAt(JNIEnv *, jclass, jlong nativeObjectPtr, jint at) {
  return JavaCritical_org_ice1000_jimgui_NativeString_byteAt(nativeObjectPtr, at);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_NativeString_setByteAt(JNIEnv *, jclass, jlong nativeObjectPtr, jint at, jbyte newValue) {
  JavaCritical_org_ice1000_jimgui_NativeString_setByteAt(nativeObjectPtr, at, newValue);
}

JNIEXPORT jint JNICALL
Java_org_ice1000_jimgui_NativeString_length(JNIEnv *, jclass, jlong nativeObjectPtr) {
  return JavaCritical_org_ice1000_jimgui_NativeString_length(nativeObjectPtr);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_NativeString_clear(JNIEnv *, jclass, jlong nativeObjectPtr) {
  JavaCritical_org_ice1000_jimgui_NativeString_clear(nativeObjectPtr);
}

JNIEXPORT jlong JNICALL
Java_org_ice1000_jimgui_NativeString_substring(JNIEnv *, jclass, jlong nativeObjectPtr, jint start, jint end) {
  return JavaCritical_org_ice1000_jimgui_NativeString_substring(nativeObjectPtr, start, end);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_NativeString_readValues(JNIEnv *env, jclass, jlong nativeObjectPtr, jbyteArray _buf) {
  __get(Byte, buf)
  JavaCritical_org_ice1000_jimgui_NativeString_readValues(nativeObjectPtr, -1, buf);
  __release(Byte, buf)
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_NativeString_appendChar(JNIEnv *, jclass, jlong nativeObjectPtr, jbyte newChar) {
  JavaCritical_org_ice1000_jimgui_NativeString_appendChar(nativeObjectPtr, newChar);
}

}

#ifndef WIN32
#pragma clang diagnostic pop
#endif