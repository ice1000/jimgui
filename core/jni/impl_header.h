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

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setupImguiSpecificObjects(jlong nativeObjectPtr, jlong fontAtlas);

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_initBeforeMainLoop(jlong nativeObjectPtr);

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_glfw_GlfwUtil_createWindowPointer0(jint width,
                                                                   jint height,
                                                                   Ptr<jbyte> title,
                                                                   jlong anotherWindow) -> jlong;

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowSizeX(jlong nativeObjectPtr) -> float;
JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowSizeY(jlong nativeObjectPtr) -> float;
JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowPosX(jlong nativeObjectPtr) -> float;
JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowPosY(jlong nativeObjectPtr) -> float;
JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowSizeX(jlong nativeObjectPtr, float newValue);
JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowSizeY(jlong nativeObjectPtr, float newValue);
JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowPosX(jlong nativeObjectPtr, float newValue);
JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowPosY(jlong nativeObjectPtr, float newValue);
JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowSize(jlong nativeObjectPtr, float newX, float newY);
JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowPos(jlong nativeObjectPtr, float newX, float newY);

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_getPlatformWindowSizeX(JNIEnv *,
                                                      jclass,
                                                      jlong nativeObjectPtr) -> float {
	return JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowSizeX(nativeObjectPtr);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_getPlatformWindowSizeY(JNIEnv *,
                                                      jclass,
                                                      jlong nativeObjectPtr) -> float {
	return JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowSizeY(nativeObjectPtr);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_getPlatformWindowPosX(JNIEnv *,
                                                     jclass,
                                                     jlong nativeObjectPtr) -> float {
	return JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowPosX(nativeObjectPtr);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_getPlatformWindowPosY(JNIEnv *,
                                                     jclass,
                                                     jlong nativeObjectPtr) -> float {
	return JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowPosY(nativeObjectPtr);
}


JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_setPlatformWindowSizeX(JNIEnv *, jclass, jlong nativeObjectPtr, jfloat newValue) {
	JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowSizeX(nativeObjectPtr, newValue);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_setPlatformWindowSizeY(JNIEnv *, jclass, jlong nativeObjectPtr, jfloat newValue) {
	JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowSizeY(nativeObjectPtr, newValue);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_setPlatformWindowPosX(JNIEnv *, jclass, jlong nativeObjectPtr, jfloat newValue) {
	JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowPosX(nativeObjectPtr, newValue);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_setPlatformWindowPosY(JNIEnv *, jclass, jlong nativeObjectPtr, jfloat newValue) {
	JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowPosY(nativeObjectPtr, newValue);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_setPlatformWindowSize(JNIEnv *, jclass, jlong nativeObjectPtr, jfloat newX, jfloat newY) {
	JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowSize(nativeObjectPtr, newX, newY);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_setPlatformWindowPos(JNIEnv *, jclass, jlong nativeObjectPtr, jfloat newX, jfloat newY) {
	JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowPos(nativeObjectPtr, newX, newY);
}

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

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_setupImguiSpecificObjects(JNIEnv *, jclass, jlong nativeObjectPtr, jlong fontAtlas) {
	JavaCritical_org_ice1000_jimgui_JImGui_setupImguiSpecificObjects(nativeObjectPtr, fontAtlas);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_glfw_GlfwUtil_createWindowPointer0(JNIEnv *env,
                                                           jclass,
                                                           jint w,
                                                           jint h,
                                                           jbyteArray _title,
                                                           jlong anotherWindow) -> jlong {
	__JNI__FUNCTION__INIT__
	__get(Byte, title)
	auto ret = JavaCritical_org_ice1000_jimgui_glfw_GlfwUtil_createWindowPointer0(w, h, title, anotherWindow);
	__release(Byte, title)
	__JNI__FUNCTION__CLEAN__
	return ret;
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_initBeforeMainLoop(JNIEnv *, jclass, jlong nativeObjectPtr) {
	JavaCritical_org_ice1000_jimgui_JImGui_initBeforeMainLoop(nativeObjectPtr);
}

#ifdef __cplusplus
}
#endif

#ifndef WIN32
#pragma clang diagnostic pop
#endif

#endif //JIMGUI_IMPL_HEADER_H
