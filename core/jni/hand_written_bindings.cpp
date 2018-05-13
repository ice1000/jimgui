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

jlong Java_org_ice1000_jimgui_JImStyle_getColor0(JNIEnv *, jclass, jint index) {
	return reinterpret_cast<jlong> (&ImGui::GetStyle().Colors[index]);
}

void Java_org_ice1000_jimgui_JImFont_setDisplayOffset(JNIEnv *, jobject, jfloat newX, jfloat newY) {
	ImGui::GetFont()->DisplayOffset.x = newX;
	ImGui::GetFont()->DisplayOffset.y = newY;
}

jbyteArray Java_org_ice1000_jimgui_JImFont_getDebugName0(JNIEnv *env, jclass) {
	__JNI__FUNCTION__INIT__
	auto debugName = ImGui::GetFont()->GetDebugName();
	auto len = static_cast<jsize> (strlen(debugName));
	auto *data = reinterpret_cast<const jbyte *> (debugName);
	__init(Byte, data, len);
	__JNI__FUNCTION__CLEAN__
	return _data;
}

jint Java_org_ice1000_jimgui_JImFont_getFontAtlasFlags(JNIEnv *, jobject) {
	return ImGui::GetFont()->ContainerAtlas->Flags;
}

void Java_org_ice1000_jimgui_JImFont_setFontAtlasFlags(JNIEnv *, jobject, jint newValue) {
	ImGui::GetFont()->ContainerAtlas->Flags = newValue;
}

#define JImIOAccessor(Property) \
jfloat Java_org_ice1000_jimgui_JImGuiIO_get ## Property ## X(JNIEnv *, jobject) { \
  return ImGui::GetIO().Property.x; \
} \
jfloat Java_org_ice1000_jimgui_JImGuiIO_get ## Property ## Y(JNIEnv *, jobject) { \
  return ImGui::GetIO().Property.y; \
}

JImIOAccessor(DisplayFramebufferScale)
JImIOAccessor(DisplayVisibleMin)
JImIOAccessor(DisplayVisibleMax)
JImIOAccessor(DisplaySize)
JImIOAccessor(MousePos)
JImIOAccessor(MouseDelta)
JImIOAccessor(MousePosPrev)

#undef JImIOAccessor

#define JImIOMouseArrayAccessor(property) \
jfloat Java_org_ice1000_jimgui_JImGuiIO_getMouse ## property ## X(JNIEnv *, jobject, jint index) { \
  return ImGui::GetIO().Mouse ## property [index].x; \
} \
jfloat Java_org_ice1000_jimgui_JImGuiIO_getMouse ## property ## Y(JNIEnv *, jobject, jint index) { \
  return ImGui::GetIO().Mouse ## property [index].y; \
}

JImIOMouseArrayAccessor(ClickedPos)
JImIOMouseArrayAccessor(DragMaxDistanceAbs)

#undef JImIOMouseArrayAccessor

jbyteArray Java_org_ice1000_jimgui_JImGuiIO_getInputString0(JNIEnv *env, jobject) {
	__JNI__FUNCTION__INIT__
	auto *inputShorts = ImGui::GetIO().InputCharacters;
	auto *inputStr = new jbyte[17];
	jsize len;
	for (int i = 0;; ++i) {
		if (not inputShorts[i]) {
			len = i;
			break;
		} else inputStr[i] = static_cast<jbyte> (inputShorts[i]);
	}
	__init(Byte, inputStr, len);
	__JNI__FUNCTION__CLEAN__
	delete[] inputStr;
	return _inputStr;
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

#pragma clang diagnostic pop