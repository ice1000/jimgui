///
/// Created by ice1000 on 18-5-14.
///

#include "basics.hpp"

#include <org_ice1000_jimgui_NativeBool.h>
#include <org_ice1000_jimgui_NativeInt.h>
#include <org_ice1000_jimgui_NativeFloat.h>
#include <org_ice1000_jimgui_NativeDouble.h>
#include <org_ice1000_jimgui_NativeLong.h>
#include <org_ice1000_jimgui_NativeShort.h>

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"

#ifdef __cplusplus
extern "C" {
#endif

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
NativeImpls(Double, jdouble, float, 0)

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

#ifdef __cplusplus
}
#endif

#pragma clang diagnostic pop