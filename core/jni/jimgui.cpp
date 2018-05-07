//
// Created by ice1000 on 18-4-30.
//

#include <imgui.h>
#include <imgui_impl_glfw_gl3.h>
#include "impl/GL/glcorearb.h" // avoid conflicting with system include
#include <GL/gl3w.h>
#include <GLFW/glfw3.h>

#include <org_ice1000_jimgui_JImGui.h>
#include <org_ice1000_jimgui_JImVec4.h>
#include <org_ice1000_jimgui_MutableJImVec4.h>

#include "basics.hpp"

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"

static void glfw_error_callback(int error, const char *description) {
	fprintf(stderr, "ImGui Error %d: %s\n", error, description);
}

auto Java_org_ice1000_jimgui_JImGui_allocateNativeObjects(
		JNIEnv *env, jclass, jint width, jint height, jbyteArray _title) -> jlong {
	__JNI__FUNCTION__INIT__
	__get(Byte, title);
	glfwSetErrorCallback(glfw_error_callback);
	if (!glfwInit())
		return 1;
	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
#if __APPLE__
	glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
#endif
	GLFWwindow *window = glfwCreateWindow(
			width, height,
			reinterpret_cast<const char *> (title), nullptr, nullptr);
	glfwMakeContextCurrent(window);
	// Enable vsync
	glfwSwapInterval(1);
	gl3wInit();

	// Setup Dear ImGui binding
	// IMGUI_CHECKVERSION();
	ImGui::CreateContext();
	ImGuiIO &io = ImGui::GetIO();
	(void) io;
	// Enable Keyboard Controls
	io.ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard;
	ImGui_ImplGlfwGL3_Init(window, true);
	__release(Byte, title);
	__JNI__FUNCTION__CLEAN__
	return reinterpret_cast<jlong> (window);
}

void Java_org_ice1000_jimgui_JImGui_deallocateNativeObjects(JNIEnv *, jclass, jlong nativeObjectPtr) {
	auto *window = reinterpret_cast<GLFWwindow *>(nativeObjectPtr);
	ImGui_ImplGlfwGL3_Shutdown();
	ImGui::DestroyContext();
	glfwDestroyWindow(window);
	glfwTerminate();
}

jboolean Java_org_ice1000_jimgui_JImGui_windowShouldClose(JNIEnv *, jclass, jlong nativeObjectPtr) {
	return static_cast<jboolean>(glfwWindowShouldClose(reinterpret_cast<GLFWwindow *>(nativeObjectPtr)));
}

void Java_org_ice1000_jimgui_JImGui_initNewFrame(JNIEnv *, jobject) {
	glfwPollEvents();
	ImGui_ImplGlfwGL3_NewFrame();
}

void Java_org_ice1000_jimgui_JImGui_render(JNIEnv *, jclass, jlong nativeObjectPtr, jlong colorPtr) {
	auto *window = reinterpret_cast<GLFWwindow *> (nativeObjectPtr);
	auto clear_color = *reinterpret_cast<ImVec4 *> (colorPtr);
	int display_w, display_h;
	glfwGetFramebufferSize(window, &display_w, &display_h);
	glViewport(0, 0, display_w, display_h);
	glClearColor(clear_color.x, clear_color.y, clear_color.z, clear_color.w);
	glClear(GL_COLOR_BUFFER_BIT);
	ImGui::Render();
	ImGui_ImplGlfwGL3_RenderDrawData(ImGui::GetDrawData());
	glfwSwapBuffers(window);
}

void Java_org_ice1000_jimgui_JImGui_textColored(JNIEnv *env, jclass, jlong colorPtr, jbyteArray _text) {
	__JNI__FUNCTION__INIT__
	__get(Byte, text);
	auto color = *reinterpret_cast<ImVec4 *> (colorPtr);
	ImGui::TextColored(color, reinterpret_cast<const char *>(text));
	__release(Byte, text);
	__JNI__FUNCTION__CLEAN__
}

void Java_org_ice1000_jimgui_JImGui_begin(JNIEnv *env, jclass, jbyteArray _name, jint flags) {
	__JNI__FUNCTION__INIT__
	__get(Byte, name)
	ImGui::Begin(reinterpret_cast<const char *>(name), nullptr, flags);
	__release(Byte, name)
	__JNI__FUNCTION__CLEAN__
}

void Java_org_ice1000_jimgui_JImGui_pushID(JNIEnv *env, jclass, jbyteArray _stringID) {
	__JNI__FUNCTION__INIT__
	__get(Byte, stringID)
	ImGui::PushID(reinterpret_cast<const char *>(stringID));
	__release(Byte, stringID)
	__JNI__FUNCTION__CLEAN__
}

jfloat Java_org_ice1000_jimgui_JImGui_getMousePosX(JNIEnv *, jobject) {
	return ImGui::GetMousePos().x;
}

jfloat Java_org_ice1000_jimgui_JImGui_getMousePosY(JNIEnv *, jobject) {
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

#pragma clang diagnostic pop