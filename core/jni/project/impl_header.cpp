//
// Created by ice10 on 12/18/2020.
//

#include <impl_header.h>
#include <org_ice1000_jimgui_JImGui.h>

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_getPlatformWindowSizeX(
    JNIEnv *,
    jclass,
    jlong nativeObjectPtr) -> float {
  return JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowSizeX(nativeObjectPtr);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_getPlatformWindowSizeY(
    JNIEnv *,
    jclass,
    jlong nativeObjectPtr) -> float {
  return JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowSizeY(nativeObjectPtr);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_getPlatformWindowPosX(
    JNIEnv *,
    jclass,
    jlong nativeObjectPtr) -> float {
  return JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowPosX(nativeObjectPtr);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_getPlatformWindowPosY(
    JNIEnv *,
    jclass,
    jlong nativeObjectPtr) -> float {
  return JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowPosY(nativeObjectPtr);
}

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

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_glfw_GlfwUtil_createWindowPointer0(
    JNIEnv *env,
    jclass,
    jint w,
    jint h,
    jbyteArray _title,
    jlong anotherWindow) -> jlong {
  __get(Byte, title)
  auto ret = JavaCritical_org_ice1000_jimgui_glfw_GlfwUtil_createWindowPointer0(w, h, title, anotherWindow);
  __release(Byte, title)
  return ret;
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_initBeforeMainLoop(JNIEnv *, jclass, jlong nativeObjectPtr) {
  JavaCritical_org_ice1000_jimgui_JImGui_initBeforeMainLoop(nativeObjectPtr);
}
