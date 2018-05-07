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

#define JImStyleVarFunctionDefinition(variableName) \
jint Java_org_ice1000_jimgui_JImStyleVar_get ## variableName(JNIEnv *, jclass) { \
  return ImGuiStyleVar_ ## variableName; \
}

JImStyleVarFunctionDefinition(Alpha)
JImStyleVarFunctionDefinition(WindowPadding)
JImStyleVarFunctionDefinition(WindowRounding)
JImStyleVarFunctionDefinition(WindowBorderSize)
JImStyleVarFunctionDefinition(WindowMinSize)
JImStyleVarFunctionDefinition(WindowTitleAlign)
JImStyleVarFunctionDefinition(ChildRounding)
JImStyleVarFunctionDefinition(ChildBorderSize)
JImStyleVarFunctionDefinition(PopupRounding)
JImStyleVarFunctionDefinition(PopupBorderSize)
JImStyleVarFunctionDefinition(FramePadding)
JImStyleVarFunctionDefinition(FrameRounding)
JImStyleVarFunctionDefinition(FrameBorderSize)
JImStyleVarFunctionDefinition(ItemSpacing)
JImStyleVarFunctionDefinition(ItemInnerSpacing)
JImStyleVarFunctionDefinition(IndentSpacing)
JImStyleVarFunctionDefinition(ScrollbarSize)
JImStyleVarFunctionDefinition(ScrollbarRounding)
JImStyleVarFunctionDefinition(GrabMinSize)
JImStyleVarFunctionDefinition(GrabRounding)
JImStyleVarFunctionDefinition(ButtonTextAlign)
JImStyleVarFunctionDefinition(COUNT)

#undef JImStyleVarFunctionDefinition

#pragma clang diagnostic pop