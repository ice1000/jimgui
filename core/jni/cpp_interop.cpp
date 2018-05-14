///
/// Created by ice1000 on 18-5-14.
///

#include "basics.hpp"

#include <org_ice1000_jimgui_NativeBool.h>
#include <org_ice1000_jimgui_NativeInt.h>
#include <org_ice1000_jimgui_NativeFloat.h>

#define NativeImpls(JavaName, JavaType, CppType, ExtraCodes, InitValue) \
JavaType Java_org_ice1000_jimgui_Native ## JavaName ## _accessValue(JNIEnv *, jclass, jlong nativeObjectPtr) { \
  auto nativeObject = reinterpret_cast<Ptr<CppType>> (nativeObjectPtr); \
  return static_cast<JavaType> (*nativeObject ExtraCodes); \
} \
void Java_org_ice1000_jimgui_Native ## JavaName ## _modifyValue(JNIEnv *, jclass, jlong nativeObjectPtr, JavaType newValue) { \
  auto nativeObject = reinterpret_cast<Ptr<CppType>> (nativeObjectPtr); \
  *nativeObject = static_cast<CppType> (newValue); \
} \
jlong Java_org_ice1000_jimgui_Native ## JavaName ## _allocateNativeObject(JNIEnv *, jclass) { \
  return reinterpret_cast<jlong> (new CppType(InitValue)); \
} \
void Java_org_ice1000_jimgui_Native ## JavaName ## _deallocateNativeObject0(JNIEnv *, jclass, jlong nativeObjectPtr) { \
  auto nativeObject = reinterpret_cast<Ptr<CppType>> (nativeObjectPtr); \
  delete nativeObject; \
}

NativeImpls(Bool, jboolean, bool, ? JNI_TRUE : JNI_FALSE, false)
NativeImpls(Int, jint, int, , 0)
NativeImpls(Float, jfloat, float, , 0)

#undef NativeImpls
