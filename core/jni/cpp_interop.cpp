///
/// Created by ice1000 on 18-5-14.
///

#include <org_ice1000_jimgui_cpp_NativeBool.h>
#include <org_ice1000_jimgui_cpp_NativeInt.h>
#include <org_ice1000_jimgui_cpp_NativeFloat.h>

#define NativeImpls(JavaName, JavaType, CppType, ExtraCodes) \
JavaType Java_org_ice1000_jimgui_cpp_Native ## JavaName ## _accessValue(JNIEnv *, jclass, jlong nativeObjectPtr) { \
  auto nativeObject = reinterpret_cast<CppType *> (nativeObjectPtr); \
  return static_cast<JavaType> (*nativeObject ExtraCodes); \
} \
jlong Java_org_ice1000_jimgui_cpp_Native ## JavaName ## _allocateNativeObject(JNIEnv *, jclass) { \
  return reinterpret_cast<jlong> (new (CppType)); \
} \
void Java_org_ice1000_jimgui_cpp_Native ## JavaName ## _deallocateNativeObject0(JNIEnv *, jclass, jlong nativeObjectPtr) { \
  auto nativeObject = reinterpret_cast<CppType *> (nativeObjectPtr); \
  delete nativeObject; \
}

NativeImpls(Bool, jboolean, bool, ? JNI_TRUE : JNI_FALSE)
NativeImpls(Int, jint, int,)
NativeImpls(Float, jfloat, float,)

#undef NativeImpls

//jboolean Java_org_ice1000_jimgui_cpp_NativeBool_accessValue(JNIEnv *, jclass, jlong nativeObjectPtr) {
//	auto nativeObject = reinterpret_cast<bool *> (nativeObjectPtr);
//	return static_cast<jboolean> (*nativeObject ? JNI_TRUE : JNI_FALSE);
//}


