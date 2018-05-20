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
	return reinterpret_cast<jlong> (ImGui::GetFont());
}

JNIEXPORT void JNICALL Java_org_ice1000_jimgui_JImGui_pushID(JNIEnv *env, jclass, jbyteArray _id) {
	__JNI__FUNCTION__INIT__
	__get(Byte, id);
	ImGui::PushID(reinterpret_cast<Ptr<const char>> (id), reinterpret_cast<Ptr<const char>> (id + __len(id)));
	__release(Byte, id);
	__JNI__FUNCTION__CLEAN__
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImGui_getID(JNIEnv *env, jclass, jbyteArray _id) -> jint {
	__JNI__FUNCTION__INIT__
	__get(Byte, id);
	auto res = ImGui::GetID(reinterpret_cast<Ptr<const char>> (id), reinterpret_cast<Ptr<const char>> (id + __len(id)));
	__release(Byte, id);
	__JNI__FUNCTION__CLEAN__
	return res;
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImGui_getWindowDrawListNativeObjectPtr(JNIEnv *, jclass) -> jlong {
	return reinterpret_cast<jlong> (ImGui::GetWindowDrawList());
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImGui_getOverlayDrawListNativeObjectPtr(JNIEnv *, jclass) -> jlong {
	return reinterpret_cast<jlong> (ImGui::GetOverlayDrawList());
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImStyle_getColor0(JNIEnv *, jclass, jint index) -> jlong {
	return reinterpret_cast<jlong> (&ImGui::GetStyle().Colors[index]);
}

JNIEXPORT void JNICALL Java_org_ice1000_jimgui_JImFont_setDisplayOffset(JNIEnv *, jobject, jfloat newX, jfloat newY) {
	ImGui::GetFont()->DisplayOffset.x = newX;
	ImGui::GetFont()->DisplayOffset.y = newY;
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImFont_getDebugName0(JNIEnv *env, jclass, jlong nativeObjectPtr) -> jbyteArray {
	__JNI__FUNCTION__INIT__
	auto *font = reinterpret_cast<Ptr<ImFont>> (nativeObjectPtr);
	auto debugName = font->GetDebugName();
	auto len = static_cast<jsize> (strlen(debugName));
	auto *data = reinterpret_cast<Ptr<const jbyte>> (debugName);
	__init(Byte, data, len);
	__JNI__FUNCTION__CLEAN__
	return _data;
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImFont_getContainerFontAtlas(JNIEnv *, jclass, jlong nativeObjectPtr) -> jlong {
	auto *font = reinterpret_cast<Ptr<ImFont>> (nativeObjectPtr);
	return reinterpret_cast<jlong> (font->ContainerAtlas);
}

#define JImIOMouseArrayAccessor(property) \
JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImGuiIO_getMouse ## property ## X(JNIEnv *, jobject, jint index) -> jfloat { \
  return ImGui::GetIO().Mouse ## property [index].x; \
} \
JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImGuiIO_getMouse ## property ## Y(JNIEnv *, jobject, jint index) -> jfloat { \
  return ImGui::GetIO().Mouse ## property [index].y; \
}

JImIOMouseArrayAccessor(ClickedPos)
JImIOMouseArrayAccessor(DragMaxDistanceAbs)

#undef JImIOMouseArrayAccessor

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImGuiIO_getInputString0(JNIEnv *env, jclass) -> jbyteArray {
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
	return reinterpret_cast<jlong> (ImGui::GetIO().Fonts);
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImVec4_fromHSV0(JNIEnv *, jclass, jfloat h, jfloat s, jfloat v, jfloat a) -> jlong {
	float r, g, b;
	ImGui::ColorConvertHSVtoRGB(h, s, v, r, g, b);
	return reinterpret_cast<jlong> (new ImVec4(r, g, b, a));
}

JNIEXPORT void JNICALL Java_org_ice1000_jimgui_JImGui_plotLines(JNIEnv *env, jclass,
		jbyteArray _label, jfloatArray _values, jint valuesOffset, jbyteArray _overlayText, jfloat scaleMin, jfloat scaleMax, jfloat graphWidth, jfloat graphHeight) {
	__JNI__FUNCTION__INIT__
	__get(Byte, label);
	__get(Float, values);
	__get(Byte, overlayText);
	ImGui::PlotLines(
			reinterpret_cast<Ptr<const char>> (label),
			values,
			__len(values),
			valuesOffset,
			reinterpret_cast<Ptr<const char>> (overlayText),
			scaleMin,
			scaleMax,
			ImVec2(graphWidth, graphHeight)
	);
	__release(Byte, label);
	__release(Float, values);
	__release(Byte, overlayText);
	__JNI__FUNCTION__CLEAN__
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImGuiIO_getFontDefault0(JNIEnv *, jclass) -> jlong {
	return reinterpret_cast<jlong> (ImGui::GetIO().FontDefault);
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImVec4_allocateNativeObjects0(JNIEnv *, jclass) -> jlong {
	return reinterpret_cast<jlong>(new ImVec4());
}

JNIEXPORT void JNICALL Java_org_ice1000_jimgui_JImVec4_deallocateNativeObjects(JNIEnv *, jclass, jlong nativeObjectPtr) {
	delete reinterpret_cast<Ptr<ImVec4>> (nativeObjectPtr);
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImVec4_allocateNativeObjects(
		JNIEnv *, jclass, jfloat x, jfloat y, jfloat z, jfloat w) -> jlong {
	return reinterpret_cast<jlong> (new ImVec4(x, y, z, w));
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImVec4_getX(JNIEnv *, jclass, jlong nativeObjectPtr) -> jfloat {
	return reinterpret_cast<Ptr<ImVec4>> (nativeObjectPtr)->x;
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImVec4_getY(JNIEnv *, jclass, jlong nativeObjectPtr) -> jfloat {
	return reinterpret_cast<Ptr<ImVec4>> (nativeObjectPtr)->y;
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImVec4_getZ(JNIEnv *, jclass, jlong nativeObjectPtr) -> jfloat {
	return reinterpret_cast<Ptr<ImVec4>> (nativeObjectPtr)->z;
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImVec4_toU32(JNIEnv *, jclass, jlong nativeObjectPtr) -> jint {
	return ImGui::ColorConvertFloat4ToU32(* reinterpret_cast<Ptr<ImVec4>> (nativeObjectPtr));
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImVec4_fromImU32(JNIEnv *, jclass, jint u32) -> jlong {
	return reinterpret_cast<jlong> (new ImVec4(ImGui::ColorConvertU32ToFloat4(static_cast<ImU32> (u32))));
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImVec4_getW(JNIEnv *, jclass, jlong nativeObjectPtr) -> jfloat {
	return reinterpret_cast<Ptr<ImVec4>> (nativeObjectPtr)->w;
}

JNIEXPORT void JNICALL Java_org_ice1000_jimgui_MutableJImVec4_setX(JNIEnv *, jclass, jlong nativeObjectPtr, jfloat newValue) {
	reinterpret_cast<Ptr<ImVec4>> (nativeObjectPtr)->x = newValue;
}

JNIEXPORT void JNICALL Java_org_ice1000_jimgui_MutableJImVec4_setY(JNIEnv *, jclass, jlong nativeObjectPtr, jfloat newValue) {
	reinterpret_cast<Ptr<ImVec4>> (nativeObjectPtr)->y = newValue;
}

JNIEXPORT void JNICALL Java_org_ice1000_jimgui_MutableJImVec4_setZ(JNIEnv *, jclass, jlong nativeObjectPtr, jfloat newValue) {
	reinterpret_cast<Ptr<ImVec4>> (nativeObjectPtr)->z = newValue;
}

JNIEXPORT void JNICALL Java_org_ice1000_jimgui_MutableJImVec4_setW(JNIEnv *, jclass, jlong nativeObjectPtr, jfloat newValue) {
	reinterpret_cast<Ptr<ImVec4>> (nativeObjectPtr)->w = newValue;
}

JNIEXPORT void JNICALL Java_org_ice1000_jimgui_JImGui_loadIniSettingsFromMemory(JNIEnv *env, jclass, jbyteArray _data) {
	__JNI__FUNCTION__INIT__
	__get(Byte, data)
	const auto *ini_data = reinterpret_cast<Ptr<const char>>(data);
	auto ini_size = static_cast<size_t>(__len(data));
	ImGui::LoadIniSettingsFromMemory(ini_data, ini_size);
	__abort(Byte, data)
	__JNI__FUNCTION__CLEAN__
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImGui_saveIniSettingsToMemory0(JNIEnv *env, jclass) -> jbyteArray {
	__JNI__FUNCTION__INIT__
	auto ini_data = ImGui::SaveIniSettingsToMemory();
	auto len = static_cast<jsize> (strlen(ini_data));
	auto *data = reinterpret_cast<Ptr<const jbyte>> (ini_data);
	__init(Byte, data, len);
	__JNI__FUNCTION__CLEAN__
	return _data;
}

JNIEXPORT auto JNICALL Java_org_ice1000_jimgui_JImGui_getClipboardText0(JNIEnv *env, jclass) -> jbyteArray {
	__JNI__FUNCTION__INIT__
	auto ini_data = ImGui::GetClipboardText();
	auto len = static_cast<jsize> (strlen(ini_data));
	auto *data = reinterpret_cast<Ptr<const jbyte>> (ini_data);
	__init(Byte, data, len);
	__JNI__FUNCTION__CLEAN__
	return _data;
}

#pragma clang diagnostic pop