///
/// Created by ice1000 on 18-5-7.
///

#include <imgui.h>
#include <cstring>
#include "basics.hpp"

#include <org_ice1000_jimgui_JImVec4.h>
#include <org_ice1000_jimgui_MutableJImVec4.h>
#include <org_ice1000_jimgui_JImGuiIO.h>
#include <org_ice1000_jimgui_JImFont.h>
#include <org_ice1000_jimgui_JImFontAtlas.h>
#include <org_ice1000_jimgui_JImGui.h>
#include <org_ice1000_jimgui_JImStyle.h>

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
JavaCritical_org_ice1000_jimgui_JImGui_getOverlayDrawListNativeObjectPtr() -> jlong;

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImStyle_getColor0(jlong nativeObjectPtr, jint index) -> jlong;

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImStyle_allocateNativeObject() -> jlong;

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImStyle_deallocateNativeObject(jlong nativeObjectPtr);

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImFontAtlas_addFontFromFileTTF0(
		jint pathLen, Ptr<jbyte> path, jfloat size, jlong range, jlong nativeObjectPtr) -> jlong;

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImFont_getContainerFontAtlas(jlong nativeObjectPtr) -> jlong;

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGuiIO_getFonts0() -> jlong;

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImVec4_fromHSV0(jfloat h, jfloat s, jfloat v, jfloat a) -> jlong;

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGuiIO_getFontDefault0() -> jlong;

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImVec4_deallocateNativeObjects(jlong nativeObjectPtr);

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImVec4_allocateNativeObjects(jfloat x, jfloat y, jfloat z, jfloat w) -> jlong;

#define JIMVEC4_GETTER(name, Name) \
JNIEXPORT auto JNICALL \
JavaCritical_org_ice1000_jimgui_JImVec4_get ## Name(jlong nativeObjectPtr) -> jfloat;

JIMVEC4_GETTER(x, X)
JIMVEC4_GETTER(y, Y)
JIMVEC4_GETTER(z, Z)
JIMVEC4_GETTER(w, W)

#undef JIMVEC4_GETTER

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImVec4_toU32(jlong nativeObjectPtr) -> jint;

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImVec4_fromImU32(jint u32) -> jlong;

#define JIMVEC4_SETTER(name, Name) \
JNIEXPORT void JNICALL \
JavaCritical_org_ice1000_jimgui_MutableJImVec4_set ## Name(jlong nativeObjectPtr, jfloat newValue); \
JNIEXPORT void JNICALL \
JavaCritical_org_ice1000_jimgui_MutableJImVec4_inc ## Name(jlong nativeObjectPtr, jfloat increment); \

JIMVEC4_SETTER(x, X)
JIMVEC4_SETTER(y, Y)
JIMVEC4_SETTER(z, Z)
JIMVEC4_SETTER(w, W)

#undef JIMVEC4_SETTER

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_menuItem(jint labelLen,
                                                Ptr<jbyte> label,
                                                jint shortcutLen,
                                                Ptr<jbyte> shortcut,
                                                jboolean selected,
                                                jboolean enabled) -> jboolean;

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_plotLines(jint labelLen,
                                                 Ptr<jbyte> label,
                                                 jint valuesLen,
                                                 Ptr<jfloat> values,
                                                 jint valuesOffset,
                                                 jint valuesLength,
                                                 jint overlayTextLen,
                                                 Ptr<jbyte> overlayText,
                                                 jfloat scaleMin,
                                                 jfloat scaleMax,
                                                 jfloat graphWidth,
                                                 jfloat graphHeight);

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_plotHistogram(jint labelLen,
                                                     Ptr<jbyte> label,
                                                     jint valuesLen,
                                                     Ptr<jfloat> values,
                                                     jint valuesOffset,
                                                     jint valuesLength,
                                                     jint overlayTextLen,
                                                     Ptr<jbyte> overlayText,
                                                     jfloat scaleMin,
                                                     jfloat scaleMax,
                                                     jfloat graphWidth,
                                                     jfloat graphHeight);

#define XY_ACCESSOR(Property) \
JNIEXPORT auto JNICALL \
JavaCritical_org_ice1000_jimgui_JImGui_get ## Property ## X() -> jfloat; \
JNIEXPORT auto JNICALL \
JavaCritical_org_ice1000_jimgui_JImGui_get ## Property ## Y() -> jfloat;

XY_ACCESSOR(WindowPos)
XY_ACCESSOR(ContentRegionMax)
XY_ACCESSOR(WindowContentRegionMin)
XY_ACCESSOR(WindowContentRegionMax)
XY_ACCESSOR(FontTexUvWhitePixel)
XY_ACCESSOR(ItemRectMin)
XY_ACCESSOR(ItemRectMax)
XY_ACCESSOR(ItemRectSize)
XY_ACCESSOR(MousePosOnOpeningCurrentPopup)

#undef XY_ACCESSOR

#ifdef __cplusplus
}
#endif

#ifndef WIN32
#pragma clang diagnostic pop
#endif
