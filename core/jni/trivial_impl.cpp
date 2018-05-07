///
/// Created by ice1000 on 18-5-7.
///

#include <imgui.h>

#include <org_ice1000_jimgui_JImVec4.h>
#include <org_ice1000_jimgui_MutableJImVec4.h>
#include <org_ice1000_jimgui_JImStyleVar.h>
#include <org_ice1000_jimgui_JImGuiIO.h>

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"
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
	return reinterpret_cast<jlong>(new ImVec4(x, y, z, w));
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

jint Java_org_ice1000_jimgui_JImStyleVar_getAlpha(JNIEnv *, jclass) {
	return ImGuiStyleVar_Alpha;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getWindowPadding(JNIEnv *, jclass) {
	return ImGuiStyleVar_WindowPadding;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getWindowRounding(JNIEnv *, jclass) {
	return ImGuiStyleVar_WindowRounding;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getWindowBorderSize(JNIEnv *, jclass) {
	return ImGuiStyleVar_WindowBorderSize;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getWindowMinSize(JNIEnv *, jclass) {
	return ImGuiStyleVar_WindowMinSize;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getWindowTitleAlign(JNIEnv *, jclass) {
	return ImGuiStyleVar_WindowTitleAlign;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getChildRounding(JNIEnv *, jclass) {
	return ImGuiStyleVar_ChildRounding;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getChildBorderSize(JNIEnv *, jclass) {
	return ImGuiStyleVar_ChildBorderSize;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getPopupRounding(JNIEnv *, jclass) {
	return ImGuiStyleVar_PopupRounding;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getPopupBorderSize(JNIEnv *, jclass) {
	return ImGuiStyleVar_PopupBorderSize;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getFramePadding(JNIEnv *, jclass) {
	return ImGuiStyleVar_FramePadding;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getFrameRounding(JNIEnv *, jclass) {
	return ImGuiStyleVar_FrameRounding;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getFrameBorderSize(JNIEnv *, jclass) {
	return ImGuiStyleVar_FrameBorderSize;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getItemSpacing(JNIEnv *, jclass) {
	return ImGuiStyleVar_ItemSpacing;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getItemInnerSpacing(JNIEnv *, jclass) {
	return ImGuiStyleVar_ItemInnerSpacing;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getIndentSpacing(JNIEnv *, jclass) {
	return ImGuiStyleVar_IndentSpacing;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getScrollbarSize(JNIEnv *, jclass) {
	return ImGuiStyleVar_ScrollbarSize;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getScrollbarRounding(JNIEnv *, jclass) {
	return ImGuiStyleVar_ScrollbarRounding;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getGrabMinSize(JNIEnv *, jclass) {
	return ImGuiStyleVar_GrabMinSize;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getGrabRounding(JNIEnv *, jclass) {
	return ImGuiStyleVar_GrabRounding;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getButtonTextAlign(JNIEnv *, jclass) {
	return ImGuiStyleVar_ButtonTextAlign;
}

jint Java_org_ice1000_jimgui_JImStyleVar_getCOUNT(JNIEnv *, jclass) {
	return ImGuiStyleVar_COUNT;
}

#pragma clang diagnostic pop