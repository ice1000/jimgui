///
/// Created by ice1000 on 18-6-1.
///

#ifndef JIMGUI_IMPL_HEADER_H
#define JIMGUI_IMPL_HEADER_H

#include <jni.h>
#include <basics.hpp>

#ifdef __cplusplus
extern "C" {
#endif

#ifndef WIN32
#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"
#endif

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_deallocateNativeObjects(jlong nativeObjectPtr);

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_deallocateGuiFramework(jlong nativeObjectPtr);

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_initNewFrame(jlong nativeObjectPtr);

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_windowShouldClose(jlong nativeObjectPtr) -> jboolean;

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_render(jlong nativeObjectPtr, jlong colorPtr);

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImTextureID_createGlfwTextureFromBytes(jint rawDataLen,
                                                                        Ptr<jbyte> rawData,
                                                                        jint width,
                                                                        jint height) -> jlong;

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_deallocateNativeObjects(JNIEnv *, jclass, jlong nativeObjectPtr) {
	JavaCritical_org_ice1000_jimgui_JImGui_deallocateNativeObjects(nativeObjectPtr);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_deallocateGuiFramework(JNIEnv *, jclass, jlong nativeObjectPtr) {
	JavaCritical_org_ice1000_jimgui_JImGui_deallocateGuiFramework(nativeObjectPtr);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_initNewFrame(JNIEnv *, jclass, jlong nativeObjectPtr) {
	JavaCritical_org_ice1000_jimgui_JImGui_initNewFrame(nativeObjectPtr);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_windowShouldClose(JNIEnv *, jclass, jlong nativeObjectPtr) -> jboolean {
	return JavaCritical_org_ice1000_jimgui_JImGui_windowShouldClose(nativeObjectPtr);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_render(JNIEnv *, jclass, jlong ptr, jlong colorPtr) {
	JavaCritical_org_ice1000_jimgui_JImGui_render(ptr, colorPtr);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImTextureID_createGlfwTextureFromBytes(Ptr<JNIEnv> env,
                                                                jclass,
                                                                jbyteArray _rawData,
                                                                jint w,
                                                                jint h) -> jlong {
	__JNI__FUNCTION__INIT__
	__get(Byte, rawData)
	auto ret = JavaCritical_org_ice1000_jimgui_JImTextureID_createGlfwTextureFromBytes(-1, rawData, w, h);
	__release(Byte, rawData)
	__JNI__FUNCTION__CLEAN__
	return ret;
}

#ifdef __cplusplus
}
#endif

#ifndef WIN32
#pragma clang diagnostic pop
#endif

#endif //JIMGUI_IMPL_HEADER_H
