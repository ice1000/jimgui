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
JavaCritical_org_ice1000_jimgui_glfw_GlfwUtil_createWindowPointer0(
    jint width,
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
JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowSize(jlong nativeObjectPtr, float newX, float newY);
JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowPos(jlong nativeObjectPtr, float newX, float newY);

#ifdef __cplusplus
}
#endif

#ifndef WIN32
#pragma clang diagnostic pop
#endif

#endif //JIMGUI_IMPL_HEADER_H
