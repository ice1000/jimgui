///
/// Created by ice1000 on 18-6-1.
///

#ifndef JIMGUI_IMPL_HEADER_H
#define JIMGUI_IMPL_HEADER_H

#include <jni.h>

#ifdef __cplusplus
extern "C" {
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

#ifdef __cplusplus
}
#endif

#endif //JIMGUI_IMPL_HEADER_H
