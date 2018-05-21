///
/// Created by ice1000 on 18-5-14.
///

#include "basics.hpp"

#include <org_ice1000_jimgui_NativeBool.h>
#include <org_ice1000_jimgui_NativeInt.h>
#include <org_ice1000_jimgui_NativeFloat.h>

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"

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
}

NativeImpls(Int, jint, int, 0)
NativeImpls(Float, jfloat, float, 0)
NativeImpls(Double, jdouble, float, 0)

JNIEXPORT jboolean JNICALL
Java_org_ice1000_jimgui_NativeBool_accessValue(JNIEnv *, jclass, jlong nativeObjectPtr) {
	auto nativeObject = PTR_J2C(bool, nativeObjectPtr);
	return static_cast<jboolean>(*nativeObject ? JNI_TRUE : JNI_FALSE);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_NativeBool_modifyValue(JNIEnv *, jclass, jlong nativeObjectPtr, jboolean newValue) {
	auto nativeObject = PTR_J2C(bool, nativeObjectPtr);
	*nativeObject = static_cast<bool>(newValue);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_NativeBool_invertValue(JNIEnv *, jclass, jlong nativeObjectPtr) {
	auto *nativeObject = PTR_J2C(bool, nativeObjectPtr);
	*nativeObject = !*nativeObject;
}

JNIEXPORT jlong JNICALL
Java_org_ice1000_jimgui_NativeBool_allocateNativeObject(JNIEnv *, jclass) {
	return PTR_C2J(new bool(false));
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_NativeBool_deallocateNativeObject0(JNIEnv *, jclass, jlong nativeObjectPtr) {
	auto nativeObject = PTR_J2C(bool, nativeObjectPtr);
	delete nativeObject;
}

#undef NativeImpls

#pragma clang diagnostic pop