//
// Created by ice10 on 12/18/2020.
//

#include <impl_header.h>
#include <org_ice1000_jimgui_JImGui.h>

#define DEFINE_PLATFORM_WINDOW_JNI_ACCESSORS(name) \
JNIEXPORT auto JNICALL \
Java_org_ice1000_jimgui_JImGui_getPlatformWindow ## name(JNIEnv *, jclass, jlong nativeObjectPtr) -> float { \
  return JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindow ## name(nativeObjectPtr); \
}

DEFINE_PLATFORM_WINDOW_JNI_ACCESSORS(SizeX)
DEFINE_PLATFORM_WINDOW_JNI_ACCESSORS(SizeY)
DEFINE_PLATFORM_WINDOW_JNI_ACCESSORS(PosX)
DEFINE_PLATFORM_WINDOW_JNI_ACCESSORS(PosY)

#undef DEFINE_PLATFORM_WINDOW_JNI_ACCESSORS

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_setPlatformWindowSize(
    JNIEnv *, jclass, jlong nativeObjectPtr, jfloat newX,
    jfloat newY) {
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

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_setWindowTitle(JNIEnv *env, jclass, jlong nativeObjectPtr, jbyteArray _title) {
  __get(Byte, title);
  JavaCritical_org_ice1000_jimgui_JImGui_setWindowTitle(nativeObjectPtr, -1, title);
  __release(Byte, title);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_setWindowTitlePtr(JNIEnv *env, jclass, jlong nativeObjectPtr, jlong stringPtr) {
  JavaCritical_org_ice1000_jimgui_JImGui_setWindowTitlePtr(nativeObjectPtr, stringPtr);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_glfw_GlfwUtil_createWindowPointer0(
    JNIEnv *env,
    jclass,
    jint w,
    jint h,
    jbyteArray _title,
    jlong anotherWindow) -> jlong {
  __get(Byte, title)
  auto ret = JavaCritical_org_ice1000_jimgui_glfw_GlfwUtil_createWindowPointer0(w, h, -1, title, anotherWindow);
  __release(Byte, title)
  return ret;
}
