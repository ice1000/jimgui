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
#include <org_ice1000_jimgui_JImGui.h>
#include <org_ice1000_jimgui_JImStyle.h>

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImGui_getFontNativeObjectPtr(JNIEnv *, jclass) -> jlong {
	return PTR_C2J(ImGui::GetFont());
}

JNIEXPORT void JNICALL Java_org_ice1000_jimgui_JImGui_pushID(JNIEnv *env, jobject, jint id) {
	ImGui::PushID(id);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_getWindowDrawListNativeObjectPtr(JNIEnv *, jclass) -> jlong {
	return PTR_C2J(ImGui::GetWindowDrawList());
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_getOverlayDrawListNativeObjectPtr(JNIEnv *, jclass) -> jlong {
	return PTR_C2J(ImGui::GetOverlayDrawList());
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImStyle_getColor0(JNIEnv *, jclass, jint index) -> jlong {
	return PTR_C2J(&ImGui::GetStyle().Colors[index]);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImFont_setDisplayOffset(JNIEnv *, jobject, jfloat newX, jfloat newY) {
	ImGui::GetFont()->DisplayOffset.x = newX;
	ImGui::GetFont()->DisplayOffset.y = newY;
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImFont_getDebugName0(JNIEnv *env, jclass, jlong nativeObjectPtr) -> jbyteArray {
	__JNI__FUNCTION__INIT__
	auto *font = PTR_J2C(ImFont, nativeObjectPtr);
	auto debugName = font->GetDebugName();
	auto len = static_cast<jsize> (strlen(debugName));
	auto *data = PTR_J2C(const jbyte, debugName);
	__init(Byte, data, len);
	__JNI__FUNCTION__CLEAN__
	return _data;
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImFont_getContainerFontAtlas(JNIEnv *, jclass, jlong nativeObjectPtr) -> jlong {
	auto *font = PTR_J2C(ImFont, nativeObjectPtr);
	return PTR_C2J(font->ContainerAtlas);
}

#define JImIOMouseArrayAccessor(property) \
JNIEXPORT auto JNICALL \
Java_org_ice1000_jimgui_JImGuiIO_getMouse ## property ## X(JNIEnv *, jobject, jint index) -> jfloat { \
  return ImGui::GetIO().Mouse ## property [index].x; \
} \
JNIEXPORT auto JNICALL \
Java_org_ice1000_jimgui_JImGuiIO_getMouse ## property ## Y(JNIEnv *, jobject, jint index) -> jfloat { \
  return ImGui::GetIO().Mouse ## property [index].y; \
}

JImIOMouseArrayAccessor(ClickedPos)
JImIOMouseArrayAccessor(DragMaxDistanceAbs)

#undef JImIOMouseArrayAccessor

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGuiIO_getInputString0(JNIEnv *env, jclass) -> jbyteArray {
	__JNI__FUNCTION__INIT__
	auto *inputShorts = ImGui::GetIO().InputCharacters;
	auto *inputStr = new jbyte[17];
	jsize len;
	for (int i = 0;; ++i) {
		if (!inputShorts[i]) {
			len = i;
			break;
		} else inputStr[i] = static_cast<jbyte> (inputShorts[i]);
	}
	__init(Byte, inputStr, len);
	__JNI__FUNCTION__CLEAN__
	delete[] inputStr;
	return _inputStr;
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImGuiIO_getFonts0(JNIEnv *, jclass) -> jlong {
	return PTR_C2J(ImGui::GetIO().Fonts);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImVec4_fromHSV0(JNIEnv *, jclass, jfloat h, jfloat s, jfloat v, jfloat a) -> jlong {
	float r, g, b;
	ImGui::ColorConvertHSVtoRGB(h, s, v, r, g, b);
	return PTR_C2J(new ImVec4(r, g, b, a));
}

JNIEXPORT void JNICALL Java_org_ice1000_jimgui_JImGui_plotLines(
		JNIEnv *env, jclass, jbyteArray _label, jfloatArray _values, jint valuesOffset, jint valuesLength,
		jbyteArray _overlayText, jfloat scaleMin, jfloat scaleMax, jfloat graphWidth, jfloat graphHeight) {
	__JNI__FUNCTION__INIT__
	__get(Byte, label);
	__get(Float, values);
	__get(Byte, overlayText);
	ImGui::PlotLines(STR_J2C(label),
	                 values,
	                 valuesLength,
	                 valuesOffset,
	                 STR_J2C(overlayText),
	                 scaleMin,
	                 scaleMax,
	                 ImVec2(graphWidth, graphHeight));
	__release(Byte, label);
	__release(Float, values);
	__release(Byte, overlayText);
	__JNI__FUNCTION__CLEAN__
}

JNIEXPORT void JNICALL Java_org_ice1000_jimgui_JImGui_plotHistogram(
		JNIEnv *env, jclass, jbyteArray _label, jfloatArray _values, jint valuesOffset, jint valuesLength,
		jbyteArray _overlayText, jfloat scaleMin, jfloat scaleMax, jfloat graphWidth, jfloat graphHeight) {
	__JNI__FUNCTION__INIT__
	__get(Byte, label);
	__get(Float, values);
	__get(Byte, overlayText);
	ImGui::PlotHistogram(STR_J2C(label),
	                     values,
	                     valuesLength,
	                     valuesOffset,
	                     STR_J2C(overlayText),
	                     scaleMin,
	                     scaleMax,
	                     ImVec2(graphWidth, graphHeight));
	__release(Byte, label);
	__release(Float, values);
	__release(Byte, overlayText);
	__JNI__FUNCTION__CLEAN__
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImGuiIO_getFontDefault0(JNIEnv *, jclass) -> jlong {
	return PTR_C2J(ImGui::GetIO().FontDefault);
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImVec4_allocateNativeObjects0(JNIEnv *, jclass) -> jlong {
	return PTR_C2J(new ImVec4());
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImVec4_deallocateNativeObjects(JNIEnv *, jclass, jlong nativeObjectPtr) {
	delete PTR_J2C(ImVec4, nativeObjectPtr);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImVec4_allocateNativeObjects(JNIEnv *, jclass, jfloat x, jfloat y, jfloat z, jfloat w) -> jlong {
	return PTR_C2J(new ImVec4(x, y, z, w));
}

#define JIMVEC4_GETTER(name, Name) \
JNIEXPORT auto JNICALL \
Java_org_ice1000_jimgui_JImVec4_get ## Name(JNIEnv *, jclass, jlong nativeObjectPtr) -> jfloat { \
  return PTR_J2C(ImVec4, nativeObjectPtr)->name; \
}

JIMVEC4_GETTER(x, X)
JIMVEC4_GETTER(y, Y)
JIMVEC4_GETTER(z, Z)
JIMVEC4_GETTER(w, W)

#undef JIMVEC4_GETTER

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImVec4_toU32(JNIEnv *, jclass, jlong nativeObjectPtr) -> jint {
	return ImGui::ColorConvertFloat4ToU32(*PTR_J2C(ImVec4, nativeObjectPtr));
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImVec4_fromImU32(JNIEnv *, jclass, jint u32) -> jlong {
	return PTR_C2J(new ImVec4(ImGui::ColorConvertU32ToFloat4(static_cast<ImU32> (u32))));
}

#define JIMVEC4_SETTER(name, Name) \
JNIEXPORT void JNICALL \
Java_org_ice1000_jimgui_MutableJImVec4_set ## Name(JNIEnv *, jclass, jlong nativeObjectPtr, jfloat newValue) { \
  PTR_J2C(ImVec4, nativeObjectPtr)->name = newValue; \
} \
JNIEXPORT void JNICALL \
Java_org_ice1000_jimgui_MutableJImVec4_inc ## Name(JNIEnv *, jclass, jlong nativeObjectPtr, jfloat increment) { \
  PTR_J2C(ImVec4, nativeObjectPtr)->name += increment; \
}

JIMVEC4_SETTER(x, X)
JIMVEC4_SETTER(y, Y)
JIMVEC4_SETTER(z, Z)
JIMVEC4_SETTER(w, W)

#undef JIMVEC4_SETTER

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_loadIniSettingsFromMemory(JNIEnv *env, jclass, jbyteArray _data) {
	__JNI__FUNCTION__INIT__
	__get(Byte, data)
	const auto *ini_data = STR_J2C(data);
	auto ini_size = static_cast<size_t>(__len(data));
	ImGui::LoadIniSettingsFromMemory(ini_data, ini_size);
	__abort(Byte, data)
	__JNI__FUNCTION__CLEAN__
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_saveIniSettingsToMemory0(JNIEnv *env, jclass) -> jbyteArray {
	__JNI__FUNCTION__INIT__
	auto ini_data = ImGui::SaveIniSettingsToMemory();
	auto len = static_cast<jsize> (strlen(ini_data));
	auto *data = PTR_J2C(const jbyte, ini_data);
	__init(Byte, data, len);
	__JNI__FUNCTION__CLEAN__
	return _data;
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_getClipboardText0(JNIEnv *env, jclass) -> jbyteArray {
	__JNI__FUNCTION__INIT__
	auto ini_data = ImGui::GetClipboardText();
	auto len = static_cast<jsize> (strlen(ini_data));
	auto *data = PTR_J2C(const jbyte, ini_data);
	__init(Byte, data, len);
	__JNI__FUNCTION__CLEAN__
	return _data;
}

JNIEXPORT jboolean JNICALL
Java_org_ice1000_jimgui_JImGui_menuItem(
		JNIEnv *env, jclass, jbyteArray _label, jbyteArray _shortcut, jboolean selected, jboolean enabled) {
	__JNI__FUNCTION__INIT__
	__get(Byte, label);
	__get(Byte, shortcut);
	auto res = ImGui::MenuItem(STR_J2C(label), STR_J2C(shortcut), selected, enabled);
	__release(Byte, label);
	__release(Byte, shortcut);
	__JNI__FUNCTION__CLEAN__
	return static_cast<jboolean>(res ? JNI_TRUE : JNI_FALSE);
}

#pragma clang diagnostic pop
