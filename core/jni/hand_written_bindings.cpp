///
/// Created by ice1000 on 18-5-7.
///

#include <imgui.h>
#include <string.h>
#include "basics.hpp"

#include <org_ice1000_jimgui_JImVec4.h>
#include <org_ice1000_jimgui_MutableJImVec4.h>
#include <org_ice1000_jimgui_JImStyleVar.h>
#include <org_ice1000_jimgui_JImGuiIO.h>
#include <org_ice1000_jimgui_JImGuiFont.h>
#include <org_ice1000_jimgui_JImGui.h>

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"

void Java_org_ice1000_jimgui_JImGuiFont_setDisplayOffsetX(JNIEnv *, jobject, jfloat newValue) {
	ImGui::GetFont()->DisplayOffset.x = newValue;
}

void Java_org_ice1000_jimgui_JImGuiFont_setDisplayOffsetY(JNIEnv *, jobject, jfloat newValue) {
	ImGui::GetFont()->DisplayOffset.y = newValue;
}

void Java_org_ice1000_jimgui_JImGuiFont_setDisplayOffset(JNIEnv *, jobject, jfloat newX, jfloat newY) {
	ImGui::GetFont()->DisplayOffset.x = newX;
	ImGui::GetFont()->DisplayOffset.y = newY;
}

jfloat Java_org_ice1000_jimgui_JImGuiFont_getDisplayOffsetX(JNIEnv *, jobject) {
	return ImGui::GetFont()->DisplayOffset.x;
}

jfloat Java_org_ice1000_jimgui_JImGuiFont_getDisplayOffsetY(JNIEnv *, jobject) {
	return ImGui::GetFont()->DisplayOffset.y;
}

jbyteArray Java_org_ice1000_jimgui_JImGuiFont_getDebugName0(JNIEnv *env, jclass) {
	__JNI__FUNCTION__INIT__
	auto debugName = ImGui::GetFont()->GetDebugName();
	auto len = static_cast<jsize> (strlen(debugName));
	auto *data = reinterpret_cast<const jbyte *> (debugName);
	__init(Byte, data, len);
	__JNI__FUNCTION__CLEAN__
	return _data;
}

jint Java_org_ice1000_jimgui_JImGuiFont_getFontAtlasFlags(JNIEnv *, jobject) {
	return ImGui::GetFont()->ContainerAtlas->Flags;
}

void Java_org_ice1000_jimgui_JImGuiFont_setFontAtlasFlags(JNIEnv *, jobject, jint newValue) {
	ImGui::GetFont()->ContainerAtlas->Flags = newValue;
}

jfloat Java_org_ice1000_jimgui_JImGuiIO_getMousePosX(JNIEnv *, jobject) {
	return ImGui::GetMousePos().x;
}

jfloat Java_org_ice1000_jimgui_JImGuiIO_getMousePosY(JNIEnv *, jobject) {
	return ImGui::GetMousePos().y;
}

jlong Java_org_ice1000_jimgui_JImVec4_allocateNativeObjects__(JNIEnv *, jclass) {
	return reinterpret_cast<jlong>(new ImVec4());
}

void Java_org_ice1000_jimgui_JImVec4_deallocateNativeObjects(JNIEnv *, jclass, jlong nativeObjectPtr) {
	delete reinterpret_cast<ImVec4 *> (nativeObjectPtr);
}

jlong Java_org_ice1000_jimgui_JImVec4_allocateNativeObjects__FFFF(
		JNIEnv *, jclass, jfloat x, jfloat y, jfloat z, jfloat w) {
	return reinterpret_cast<jlong> (new ImVec4(x, y, z, w));
}

jfloat Java_org_ice1000_jimgui_JImVec4_getX(JNIEnv *, jclass, jlong nativeObjectPtr) {
	return reinterpret_cast<ImVec4 *> (nativeObjectPtr)->x;
}

jfloat Java_org_ice1000_jimgui_JImVec4_getY(JNIEnv *, jclass, jlong nativeObjectPtr) {
	return reinterpret_cast<ImVec4 *> (nativeObjectPtr)->y;
}

jfloat Java_org_ice1000_jimgui_JImVec4_getZ(JNIEnv *, jclass, jlong nativeObjectPtr) {
	return reinterpret_cast<ImVec4 *> (nativeObjectPtr)->z;
}

jfloat Java_org_ice1000_jimgui_JImVec4_getW(JNIEnv *, jclass, jlong nativeObjectPtr) {
	return reinterpret_cast<ImVec4 *> (nativeObjectPtr)->w;
}

void Java_org_ice1000_jimgui_MutableJImVec4_setX(JNIEnv *, jclass, jlong nativeObjectPtr, jfloat newValue) {
	reinterpret_cast<ImVec4 *> (nativeObjectPtr)->x = newValue;
}

void Java_org_ice1000_jimgui_MutableJImVec4_setY(JNIEnv *, jclass, jlong nativeObjectPtr, jfloat newValue) {
	reinterpret_cast<ImVec4 *> (nativeObjectPtr)->y = newValue;
}

void Java_org_ice1000_jimgui_MutableJImVec4_setZ(JNIEnv *, jclass, jlong nativeObjectPtr, jfloat newValue) {
	reinterpret_cast<ImVec4 *> (nativeObjectPtr)->z = newValue;
}

void Java_org_ice1000_jimgui_MutableJImVec4_setW(JNIEnv *, jclass, jlong nativeObjectPtr, jfloat newValue) {
	reinterpret_cast<ImVec4 *> (nativeObjectPtr)->w = newValue;
}

void Java_org_ice1000_jimgui_JImGui_begin(JNIEnv *env, jclass, jbyteArray _name, jint flags) {
	__JNI__FUNCTION__INIT__
	__get(Byte, name)
	ImGui::Begin(reinterpret_cast<const char *>(name), nullptr, flags);
	__abort(Byte, name)
	__JNI__FUNCTION__CLEAN__
}

void Java_org_ice1000_jimgui_JImGui_loadIniSettingsFromMemory(JNIEnv *env, jclass, jbyteArray _data) {
	__JNI__FUNCTION__INIT__
	__get(Byte, data)
	const auto *ini_data = reinterpret_cast<const char *>(data);
	auto ini_size = static_cast<size_t>(__len(data));
	ImGui::LoadIniSettingsFromMemory(ini_data, ini_size);
	__abort(Byte, data)
	__JNI__FUNCTION__CLEAN__
}

auto Java_org_ice1000_jimgui_JImGui_saveIniSettingsToMemory0(JNIEnv *env, jclass) -> jbyteArray {
	__JNI__FUNCTION__INIT__
	auto ini_data = ImGui::SaveIniSettingsToMemory();
	auto len = static_cast<jsize> (strlen(ini_data));
	auto *data = reinterpret_cast<const jbyte *> (ini_data);
	__init(Byte, data, len);
	delete[] ini_data;
	__JNI__FUNCTION__CLEAN__
	return _data;
}

#define JImStyleVarAccessor(variableName) \
jint Java_org_ice1000_jimgui_JImStyleVar_get ## variableName(JNIEnv *, jclass) { \
  return ImGuiStyleVar_ ## variableName; \
}

JImStyleVarAccessor(Alpha)
JImStyleVarAccessor(WindowPadding)
JImStyleVarAccessor(WindowRounding)
JImStyleVarAccessor(WindowBorderSize)
JImStyleVarAccessor(WindowMinSize)
JImStyleVarAccessor(WindowTitleAlign)
JImStyleVarAccessor(ChildRounding)
JImStyleVarAccessor(ChildBorderSize)
JImStyleVarAccessor(PopupRounding)
JImStyleVarAccessor(PopupBorderSize)
JImStyleVarAccessor(FramePadding)
JImStyleVarAccessor(FrameRounding)
JImStyleVarAccessor(FrameBorderSize)
JImStyleVarAccessor(ItemSpacing)
JImStyleVarAccessor(ItemInnerSpacing)
JImStyleVarAccessor(IndentSpacing)
JImStyleVarAccessor(ScrollbarSize)
JImStyleVarAccessor(ScrollbarRounding)
JImStyleVarAccessor(GrabMinSize)
JImStyleVarAccessor(GrabRounding)
JImStyleVarAccessor(ButtonTextAlign)

#undef JImStyleVarAccessor

#pragma clang diagnostic pop