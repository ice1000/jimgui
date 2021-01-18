///
/// Created by ice1000 on 18-5-7.
///

#include <imgui.h>
#include <cstring>
#include <ImGuiFileDialog.h>
#include <imguidatechooser.h>
#include "basics.hpp"

#include <org_ice1000_jimgui_JImVec4.h>
#include <org_ice1000_jimgui_MutableJImVec4.h>
#include <org_ice1000_jimgui_JImGuiIO.h>
#include <org_ice1000_jimgui_JImFont.h>
#include <org_ice1000_jimgui_JImFontAtlas.h>
#include <org_ice1000_jimgui_JImFontConfig.h>
#include <org_ice1000_jimgui_JImGui.h>
#include <org_ice1000_jimgui_JImWidgets.h>
#include <org_ice1000_jimgui_JImStyle.h>
#include <org_ice1000_jimgui_JImFileDialog.h>
#include <org_ice1000_jimgui_NativeTime.h>

#ifndef WIN32
#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"
#endif

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getFontNativeObjectPtr() -> jlong;

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getStyleNativeObjectPtr() -> jlong;

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_pushID(jint id);

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getWindowDrawListNativeObjectPtr() -> jlong;

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getForegroundDrawListNativeObjectPtr() -> jlong;

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImStyle_getColor0(jlong nativeObjectPtr, jint index) -> jlong;

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImFontAtlas_addFontFromFileTTF0(
    jint pathLen, Ptr<jbyte> path, jfloat size, jlong config, jlong range, jlong nativeObjectPtr) -> jlong;

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImFont_getContainerFontAtlas(jlong nativeObjectPtr) -> jlong;

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImFont_getConfigData(jlong nativeObjectPtr) -> jlong;

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImVec4_toU32(jlong nativeObjectPtr) -> jint;

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImVec4_fromImU32(jint u32) -> jlong;

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImWidgets_menuItem(
    jint labelLen,
    Ptr<jbyte> label,
    jint shortcutLen,
    Ptr<jbyte> shortcut,
    jboolean selected,
    jboolean enabled
) -> jboolean;

#ifdef __cplusplus
}
#endif

#ifndef WIN32
#pragma clang diagnostic pop
#endif
