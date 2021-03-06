///
/// Created by ice1000 on 18-5-7.
///


#include <imgui.h>
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
#include <org_ice1000_jimgui_JImSortSpecs.h>
#include <string>
#include <ctime>
#include <clocale>

#ifndef WIN32
#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"
#endif

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_setTimeLocale(JNIEnv *env, jclass, jbyteArray _locale) {
  __get(Byte, locale);
  setlocale(LC_TIME, STR_J2C(locale));
  __release(Byte, locale);
}

extern "C" {
JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getFontNativeObjectPtr() -> jlong {
  return PTR_C2J(ImGui::GetFont());
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getStyleNativeObjectPtr() -> jlong {
  return PTR_C2J(&ImGui::GetStyle());
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_pushID(jint id) {
  ImGui::PushID(id);
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getWindowDrawListNativeObjectPtr() -> jlong {
  return PTR_C2J(ImGui::GetWindowDrawList());
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getTableSortSpecsNativeObjectPtr() -> jlong {
  return PTR_C2J(ImGui::TableGetSortSpecs());
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getForegroundDrawListNativeObjectPtr() -> jlong {
  return PTR_C2J(ImGui::GetForegroundDrawList());
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImStyle_getColor0(jlong nativeObjectPtr, jint index) -> jlong {
  return PTR_C2J(&PTR_J2C(ImGuiStyle, nativeObjectPtr)->Colors[index]);
}

JNIEXPORT jlong JNICALL
JavaCritical_org_ice1000_jimgui_JImSortSpecs_columnSortSpecs(jlong nativeObjectPtr, jint index) {
  auto *p = PTR_J2C(ImGuiTableSortSpecs, nativeObjectPtr)->Specs + index;
  return PTR_C2J(p);
}
}

JNIEXPORT jlong JNICALL
Java_org_ice1000_jimgui_JImSortSpecs_columnSortSpecs(JNIEnv *, jclass, jlong nativeObjectPtr, jint index) {
  return JavaCritical_org_ice1000_jimgui_JImSortSpecs_columnSortSpecs(nativeObjectPtr, index);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_getFontNativeObjectPtr(Ptr<JNIEnv>, jclass) -> jlong {
  return JavaCritical_org_ice1000_jimgui_JImGui_getFontNativeObjectPtr();
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_getStyleNativeObjectPtr(Ptr<JNIEnv>, jclass) -> jlong {
  return JavaCritical_org_ice1000_jimgui_JImGui_getStyleNativeObjectPtr();
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_pushID(Ptr<JNIEnv>, jclass, jint id) {
  JavaCritical_org_ice1000_jimgui_JImGui_pushID(id);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_getWindowDrawListNativeObjectPtr(Ptr<JNIEnv>, jclass) -> jlong {
  return JavaCritical_org_ice1000_jimgui_JImGui_getWindowDrawListNativeObjectPtr();
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_getTableSortSpecsNativeObjectPtr(Ptr<JNIEnv>, jclass) -> jlong {
  return JavaCritical_org_ice1000_jimgui_JImGui_getTableSortSpecsNativeObjectPtr();
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_getForegroundDrawListNativeObjectPtr(Ptr<JNIEnv>, jclass) -> jlong {
  return JavaCritical_org_ice1000_jimgui_JImGui_getForegroundDrawListNativeObjectPtr();
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImStyle_getColor0(Ptr<JNIEnv>, jclass, jlong nativeObjectPtr, jint index) -> jlong {
  return JavaCritical_org_ice1000_jimgui_JImStyle_getColor0(nativeObjectPtr, index);
}

#define ALLOCATE_AND_DEALLOCATE(cxxClass, javaClass) \
extern "C" { \
JNIEXPORT auto JNICALL \
JavaCritical_org_ice1000_jimgui_ ## javaClass ## _allocateNativeObject() -> jlong { \
  return PTR_C2J(new cxxClass()); \
} \
JNIEXPORT auto JNICALL \
Java_org_ice1000_jimgui_ ## javaClass ## _allocateNativeObject(JNIEnv *, jclass) -> jlong { \
  return JavaCritical_org_ice1000_jimgui_ ## javaClass ## _allocateNativeObject(); \
} \
JNIEXPORT void JNICALL \
JavaCritical_org_ice1000_jimgui_ ## javaClass ## _deallocateNativeObject(jlong nativeObjectPtr) { \
  delete PTR_J2C(cxxClass, nativeObjectPtr); \
} \
JNIEXPORT void JNICALL \
Java_org_ice1000_jimgui_ ## javaClass ## _deallocateNativeObject(JNIEnv *, jclass, jlong nativeObjectPtr) { \
  JavaCritical_org_ice1000_jimgui_ ## javaClass ## _deallocateNativeObject(nativeObjectPtr); \
} \
}

ALLOCATE_AND_DEALLOCATE(ImGuiStyle, JImStyle)
ALLOCATE_AND_DEALLOCATE(ImFontConfig, JImFontConfig)
ALLOCATE_AND_DEALLOCATE(ImFontAtlas, JImFontAtlas)
ALLOCATE_AND_DEALLOCATE(IGFD::FileDialog, JImFileDialog)
ALLOCATE_AND_DEALLOCATE(tm, NativeTime)

#undef ALLOCATE_AND_DEALLOCATE

// JNIEXPORT void JNICALL
// Java_org_ice1000_jimgui_JImFont_setDisplayOffset(Ptr<JNIEnv>, jobject, jfloat newX, jfloat newY) {
//   ImGui::GetFont()->DisplayOffset.x = newX;
//   ImGui::GetFont()->DisplayOffset.y = newY;
// }

extern "C" {
JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImFontAtlas_addFontFromFileTTF0(
    jint pathLen, Ptr<jbyte> path, jfloat size, jlong config, jlong range, jlong nativeObjectPtr) -> jlong {
  auto *fonts = PTR_J2C(ImFontAtlas, nativeObjectPtr);
  return PTR_C2J(fonts->AddFontFromFileTTF(STR_J2C(path), size, PTR_J2C(ImFontConfig, config), PTR_J2C(
                     const ImWchar, range)));
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImFont_getContainerFontAtlas(jlong nativeObjectPtr) -> jlong {
  auto *font = PTR_J2C(ImFont, nativeObjectPtr);
  return PTR_C2J(font->ContainerAtlas);
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImFont_getConfigData(jlong nativeObjectPtr) -> jlong {
  auto *font = PTR_J2C(ImFont, nativeObjectPtr);
  return PTR_C2J(font->ConfigData);
}
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImFontAtlas_addFontFromFileTTF0(
    Ptr<JNIEnv> env, jclass, jbyteArray _path, jfloat size, jlong config, jlong range, jlong nativeObjectPtr) -> jlong {
  __get(Byte, path)
  auto res = JavaCritical_org_ice1000_jimgui_JImFontAtlas_addFontFromFileTTF0(-1, path, size, config, range,
                                                                              nativeObjectPtr);
  __release(Byte, path)
  return res;
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImFont_getContainerFontAtlas(Ptr<JNIEnv>, jclass, jlong nativeObjectPtr) -> jlong {
  return JavaCritical_org_ice1000_jimgui_JImFont_getContainerFontAtlas(nativeObjectPtr);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImFont_getConfigData(Ptr<JNIEnv>, jclass, jlong nativeObjectPtr) -> jlong {
  return JavaCritical_org_ice1000_jimgui_JImFont_getConfigData(nativeObjectPtr);
}

#define JImIOMouseArrayAccessor(property) \
JNIEXPORT auto JNICALL \
Java_org_ice1000_jimgui_JImGuiIO_getMouse ## property ## X(Ptr<JNIEnv>, jobject, jint index) -> jfloat { \
  return ImGui::GetIO().Mouse ## property [index].x; \
} \
JNIEXPORT auto JNICALL \
Java_org_ice1000_jimgui_JImGuiIO_getMouse ## property ## Y(Ptr<JNIEnv>, jobject, jint index) -> jfloat { \
  return ImGui::GetIO().Mouse ## property [index].y; \
}

JImIOMouseArrayAccessor(ClickedPos)
JImIOMouseArrayAccessor(DragMaxDistanceAbs)

#undef JImIOMouseArrayAccessor

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGuiIO_getInputChars(Ptr<JNIEnv> env, jobject) -> jcharArray {
  ImVector<ImWchar> inputShorts = ImGui::GetIO().InputQueueCharacters;
  jsize len = inputShorts.size();
  auto *inputStr = new jchar[len];
  for (int i = 0; i < len; ++i) {
    inputStr[i] = static_cast<jchar> (inputShorts[i]);
  }
  __init_array(Char, inputStr, len);
  delete[] inputStr;
  return _inputStr;
}

extern "C" {

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGuiIO_getFonts0() -> jlong {
  return PTR_C2J(ImGui::GetIO().Fonts);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGuiIO_getFonts0(Ptr<JNIEnv>, jclass) -> jlong {
  return JavaCritical_org_ice1000_jimgui_JImGuiIO_getFonts0();
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImVec4_fromHSV0(jfloat h, jfloat s, jfloat v, jfloat a) -> jlong {
  float r, g, b;
  ImGui::ColorConvertHSVtoRGB(h, s, v, r, g, b);
  return PTR_C2J(new ImVec4(r, g, b, a));
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImVec4_fromHSV0(Ptr<JNIEnv>, jclass, jfloat h, jfloat s, jfloat v, jfloat a) -> jlong {
  return JavaCritical_org_ice1000_jimgui_JImVec4_fromHSV0(h, s, v, a);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImWidgets_text(jlong stringPtr) {
  ImGui::Text(PTR_J2C(std::string, stringPtr)->c_str());
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImWidgets_text(JNIEnv *, jclass, jlong stringPtr) {
  JavaCritical_org_ice1000_jimgui_JImWidgets_text(stringPtr);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImWidgets_plotLines(
    jint,
    Ptr<jbyte> label,
    jint,
    Ptr<jfloat> values,
    jint valuesOffset,
    jint valuesLength,
    jint,
    Ptr<jbyte> overlayText,
    jfloat scaleMin,
    jfloat scaleMax,
    jfloat graphWidth,
    jfloat graphHeight
) {
  ImGui::PlotLines(
      STR_J2C(label),
      values,
      valuesLength,
      valuesOffset,
      STR_J2C(overlayText),
      scaleMin,
      scaleMax,
      ImVec2(graphWidth, graphHeight));
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImWidgets_plotLines(
    Ptr<JNIEnv> env,
    jclass,
    jbyteArray _label,
    jfloatArray _values,
    jint valuesOffset,
    jint valuesLength,
    jbyteArray _overlayText,
    jfloat scaleMin,
    jfloat scaleMax,
    jfloat graphWidth,
    jfloat graphHeight
) {
  __get(Byte, label);
  __get(Float, values);
  __get(Byte, overlayText);
  JavaCritical_org_ice1000_jimgui_JImWidgets_plotLines(
      -1,
      label,
      -1,
      values,
      valuesOffset,
      valuesLength,
      -1,
      overlayText,
      scaleMin,
      scaleMax,
      graphWidth,
      graphHeight);
  __release(Byte, label);
  __release(Float, values);
  __release(Byte, overlayText);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImWidgets_plotHistogram(
    jint,
    Ptr<jbyte> label,
    jint,
    Ptr<jfloat> values,
    jint valuesOffset,
    jint valuesLength,
    jint,
    Ptr<jbyte> overlayText,
    jfloat scaleMin,
    jfloat scaleMax,
    jfloat graphWidth,
    jfloat graphHeight
) {
  ImGui::PlotHistogram(
      STR_J2C(label),
      values,
      valuesLength,
      valuesOffset,
      STR_J2C(overlayText),
      scaleMin,
      scaleMax,
      ImVec2(graphWidth, graphHeight));
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImWidgets_plotHistogram(
    Ptr<JNIEnv> env,
    jclass,
    jbyteArray _label,
    jfloatArray _values,
    jint valuesOffset,
    jint valuesLength,
    jbyteArray _overlayText,
    jfloat scaleMin,
    jfloat scaleMax,
    jfloat graphWidth,
    jfloat graphHeight
) {
  __get(Byte, label);
  __get(Float, values);
  __get(Byte, overlayText);
  JavaCritical_org_ice1000_jimgui_JImWidgets_plotHistogram(
      -1,
      label,
      -1,
      values,
      valuesOffset,
      valuesLength,
      -1,
      overlayText,
      scaleMin,
      scaleMax,
      graphWidth,
      graphHeight);
  __release(Byte, label);
  __release(Float, values);
  __release(Byte, overlayText);
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImWidgets_inputText(
    jint,
    Ptr<jbyte> label,
    jint,
    Ptr<jbyte> buffer,
    jint bufferLen,
    jint flags
) -> jboolean {
  auto ret = ImGui::InputText(STR_J2C(label), PTR_J2C(char, buffer), static_cast<size_t>(bufferLen), flags);
  return static_cast<jboolean>(ret ? JNI_TRUE : JNI_FALSE);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImWidgets_inputText(
    Ptr<JNIEnv> env,
    jclass,
    jbyteArray _label,
    jbyteArray _buffer,
    jint bufferLen,
    jint flags
) -> jboolean {
  __get(Byte, label)
  __get(Byte, buffer)
  auto ret = JavaCritical_org_ice1000_jimgui_JImWidgets_inputText(-1, label, -1, buffer, bufferLen, flags);
  __release(Byte, label)
  __release(Byte, buffer)
  return ret;
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGuiIO_getFontDefault0(Ptr<JNIEnv>, jclass) -> jlong {
  return PTR_C2J(ImGui::GetIO().FontDefault);
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGuiIO_getFontDefault0() -> jlong {
  return PTR_C2J(ImGui::GetIO().FontDefault);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImVec4_deallocateNativeObjects(Ptr<JNIEnv>, jclass, jlong nativeObjectPtr) {
  delete PTR_J2C(ImVec4, nativeObjectPtr);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImVec4_deallocateNativeObjects(jlong nativeObjectPtr) {
  delete PTR_J2C(ImVec4, nativeObjectPtr);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImVec4_allocateNativeObjects(Ptr<JNIEnv>, jclass, jfloat x, jfloat y, jfloat z,
                                                      jfloat w) -> jlong {
  return PTR_C2J(new ImVec4(x, y, z, w));
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImVec4_allocateNativeObjects(jfloat x, jfloat y, jfloat z, jfloat w) -> jlong {
  return PTR_C2J(new ImVec4(x, y, z, w));
}
}

#define JIMVEC4_GETTER(name, Name) \
extern "C" { \
JNIEXPORT auto JNICALL \
Java_org_ice1000_jimgui_JImVec4_get ## Name(Ptr<JNIEnv>, jclass, jlong nativeObjectPtr) -> jfloat { \
  return PTR_J2C(ImVec4, nativeObjectPtr)->name; \
} \
JNIEXPORT auto JNICALL \
JavaCritical_org_ice1000_jimgui_JImVec4_get ## Name(jlong nativeObjectPtr) -> jfloat { \
  return PTR_J2C(ImVec4, nativeObjectPtr)->name; \
} \
}

JIMVEC4_GETTER(x, X)
JIMVEC4_GETTER(y, Y)
JIMVEC4_GETTER(z, Z)
JIMVEC4_GETTER(w, W)

#undef JIMVEC4_GETTER

extern "C" {
JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImVec4_toU32(jlong nativeObjectPtr) -> jint {
  return ImGui::ColorConvertFloat4ToU32(*PTR_J2C(ImVec4, nativeObjectPtr));
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImVec4_fromImU32(jint u32) -> jlong {
  return PTR_C2J(new ImVec4(ImGui::ColorConvertU32ToFloat4(static_cast<ImU32> (u32))));
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImWidgets_menuItem(
    jint labelLen,
    Ptr<jbyte> label,
    jint shortcutLen,
    Ptr<jbyte> shortcut,
    jboolean selected,
    jboolean enabled
) -> jboolean {
  auto i = ImGui::MenuItem(STR_J2C(label), STR_J2C(shortcut), selected, enabled) ? JNI_TRUE : JNI_FALSE;
  return static_cast<jboolean>(i);
}
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImVec4_toU32(Ptr<JNIEnv>, jclass, jlong nativeObjectPtr) -> jint {
  return JavaCritical_org_ice1000_jimgui_JImVec4_toU32(nativeObjectPtr);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImVec4_fromImU32(Ptr<JNIEnv>, jclass, jint u32) -> jlong {
  return JavaCritical_org_ice1000_jimgui_JImVec4_fromImU32(u32);
}

#define JIMVEC4_SETTER(name, Name) \
extern "C" { \
JNIEXPORT void JNICALL \
Java_org_ice1000_jimgui_MutableJImVec4_set ## Name(Ptr<JNIEnv>, jclass, jlong nativeObjectPtr, jfloat newValue) { \
  PTR_J2C(ImVec4, nativeObjectPtr)->name = newValue; \
} \
JNIEXPORT void JNICALL \
Java_org_ice1000_jimgui_MutableJImVec4_inc ## Name(Ptr<JNIEnv>, jclass, jlong nativeObjectPtr, jfloat increment) { \
  PTR_J2C(ImVec4, nativeObjectPtr)->name += increment; \
} \
JNIEXPORT void JNICALL \
JavaCritical_org_ice1000_jimgui_MutableJImVec4_set ## Name(jlong nativeObjectPtr, jfloat newValue) { \
  PTR_J2C(ImVec4, nativeObjectPtr)->name = newValue; \
} \
JNIEXPORT void JNICALL \
JavaCritical_org_ice1000_jimgui_MutableJImVec4_inc ## Name(jlong nativeObjectPtr, jfloat increment) { \
  PTR_J2C(ImVec4, nativeObjectPtr)->name += increment; \
} \
}

JIMVEC4_SETTER(x, X)
JIMVEC4_SETTER(y, Y)
JIMVEC4_SETTER(z, Z)
JIMVEC4_SETTER(w, W)

#undef JIMVEC4_SETTER

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_loadIniSettingsFromMemory(Ptr<JNIEnv> env, jclass, jbyteArray _data) {
  __get(Byte, data)
  const auto *ini_data = STR_J2C(data);
  auto ini_size = static_cast<size_t>(__len(data));
  ImGui::LoadIniSettingsFromMemory(ini_data, ini_size);
  __abort(Byte, data)
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_saveIniSettingsToMemory0(Ptr<JNIEnv> env, jclass) -> jbyteArray {
  auto ini_data = ImGui::SaveIniSettingsToMemory();
  auto len = static_cast<jsize> (strlen(ini_data));
  auto *data = PTR_J2C(const jbyte, ini_data);
  __init_array(Byte, data, len);
  return _data;
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_getClipboardText0(Ptr<JNIEnv> env, jclass) -> jbyteArray {
  auto ini_data = ImGui::GetClipboardText();
  auto len = static_cast<jsize> (strlen(ini_data));
  auto *data = PTR_J2C(const jbyte, ini_data);
  __init_array(Byte, data, len);
  return _data;
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImWidgets_menuItem(
    Ptr<JNIEnv> env,
    jclass,
    jbyteArray _label,
    jbyteArray _shortcut,
    jboolean selected,
    jboolean enabled
) -> jboolean {
  __get(Byte, label);
  __get(Byte, shortcut);
  auto res = JavaCritical_org_ice1000_jimgui_JImWidgets_menuItem(-1, label, -1, shortcut, selected, enabled);
  __release(Byte, label);
  __release(Byte, shortcut);
  return static_cast<jboolean>(res ? JNI_TRUE : JNI_FALSE);
}

#define XY_ACCESSOR(Property) \
extern "C" { \
JNIEXPORT auto JNICALL \
Java_org_ice1000_jimgui_JImGui_get ## Property ## X(Ptr<JNIEnv>, jclass) -> jfloat { \
  return ImGui::Get ## Property().x; \
} \
JNIEXPORT auto JNICALL \
Java_org_ice1000_jimgui_JImGui_get ## Property ## Y(Ptr<JNIEnv>, jclass) -> jfloat { \
  return ImGui::Get ## Property().y; \
} \
JNIEXPORT auto JNICALL \
JavaCritical_org_ice1000_jimgui_JImGui_get ## Property ## X() -> jfloat { \
  return ImGui::Get ## Property().x; \
} \
JNIEXPORT auto JNICALL \
JavaCritical_org_ice1000_jimgui_JImGui_get ## Property ## Y() -> jfloat { \
  return ImGui::Get ## Property().y; \
} \
}

XY_ACCESSOR(WindowPos)
XY_ACCESSOR(ContentRegionMax)
XY_ACCESSOR(ContentRegionAvail)
XY_ACCESSOR(WindowContentRegionMin)
XY_ACCESSOR(WindowContentRegionMax)
XY_ACCESSOR(FontTexUvWhitePixel)
XY_ACCESSOR(ItemRectMin)
XY_ACCESSOR(ItemRectMax)
XY_ACCESSOR(ItemRectSize)
XY_ACCESSOR(MousePosOnOpeningCurrentPopup)

#undef XY_ACCESSOR

extern "C" {
JNIEXPORT jlong JNICALL
JavaCritical_org_ice1000_jimgui_NativeTime_toAbsoluteSeconds(jlong nativeObjectPtr) {
  return mktime(PTR_J2C(tm, nativeObjectPtr));
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_NativeTime_fromAbsoluteSeconds(jlong nativeObjectPtr, jlong absoluteSeconds) {
  auto *time = PTR_J2C(tm, nativeObjectPtr);
  auto newTime = static_cast<time_t>(absoluteSeconds);
  *time = *localtime(&newTime);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_NativeTime_resetToToday(jlong nativeObjectPtr) {
  ImGui::SetDateToday(PTR_J2C(tm, nativeObjectPtr));
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_NativeTime_reset(jlong nativeObjectPtr) {
  ImGui::SetDateZero(PTR_J2C(tm, nativeObjectPtr));
}
}

JNIEXPORT jlong JNICALL
Java_org_ice1000_jimgui_NativeTime_toAbsoluteSeconds(JNIEnv *, jclass, jlong nativeObjectPtr) {
  return JavaCritical_org_ice1000_jimgui_NativeTime_toAbsoluteSeconds(nativeObjectPtr);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_NativeTime_fromAbsoluteSeconds(JNIEnv *, jclass, jlong nativeObjectPtr, jlong absoluteSeconds) {
  JavaCritical_org_ice1000_jimgui_NativeTime_fromAbsoluteSeconds(nativeObjectPtr, absoluteSeconds);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_NativeTime_resetToToday(JNIEnv *, jclass, jlong nativeObjectPtr) {
  JavaCritical_org_ice1000_jimgui_NativeTime_resetToToday(nativeObjectPtr);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_NativeTime_reset(JNIEnv *, jclass, jlong nativeObjectPtr) {
  JavaCritical_org_ice1000_jimgui_NativeTime_reset(nativeObjectPtr);
}

#ifndef WIN32
#pragma clang diagnostic pop
#endif
