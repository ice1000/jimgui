//
// Created by ice1000 on 18-4-30.
//

#include <imgui.h>
#include <imgui_impl_glfw_gl2.h>
#include <GLFW/glfw3.h>

#include <org_ice1000_jimgui_JImGui.h>
#include <org_ice1000_jimgui_JImVec4.h>
#include <org_ice1000_jimgui_MutableJImVec4.h>

#include "basics.h"

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"

static void glfw_error_callback(int error, const char *description) {
	fprintf(stderr, "Error %d: %s\n", error, description);
}

auto Java_org_ice1000_jimgui_JImGui_allocateNativeObjects(JNIEnv *, jclass) -> jlong {
	glfwSetErrorCallback(glfw_error_callback);
	if (!glfwInit())
		return NULL;
	GLFWwindow *window = glfwCreateWindow(1280, 720, "ImGui GLFW+OpenGL2 example", nullptr, nullptr);
	glfwMakeContextCurrent(window);
	glfwSwapInterval(1); // Enable vsync

	// Setup Dear ImGui binding
	// IMGUI_CHECKVERSION();
	ImGui::CreateContext();
	ImGuiIO &io = ImGui::GetIO();
	(void) io;
	io.ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard;  // Enable Keyboard Controls
	ImGui_ImplGlfwGL2_Init(window, true);
	return reinterpret_cast<jlong>(window);
}

void Java_org_ice1000_jimgui_JImGui_deallocateNativeObjects(JNIEnv *, jclass, jlong nativeObjectPtr) {
	auto *window = reinterpret_cast<GLFWwindow *>(nativeObjectPtr);
	ImGui_ImplGlfwGL2_Shutdown();
	ImGui::DestroyContext();
	glfwDestroyWindow(window);
	glfwTerminate();
}

void Java_org_ice1000_jimgui_JImGui_demoMainLoop(JNIEnv *, jclass, jlong colorPtr) {
	auto clear_color = reinterpret_cast<ImVec4 *> (colorPtr);

	static bool show_demo_window = false;
	static bool show_another_window = false;

	// Main loop
	// You can read the io.WantCaptureMouse, io.WantCaptureKeyboard flags to tell if dear imgui wants to use your inputs.
	// - When io.WantCaptureMouse is true, do not dispatch mouse input data to your main application.
	// - When io.WantCaptureKeyboard is true, do not dispatch keyboard input data to your main application.
	// Generally you may always pass all inputs to dear imgui, and hide them from your application based on those two flags.

	// 1. Show a simple window.
	// Tip: if we don't call ImGui::Begin()/ImGui::End() the widgets automatically appears in a window called "Debug".
	{
		static float f = 0.0f;
		static int counter = 0;
		ImGui::SliderFloat("float", &f, 0.0f, 1.0f);            // Edit 1 float using a slider from 0.0f to 1.0f
		ImGui::ColorEdit3("clear color", (float *) clear_color); // Edit 3 floats representing a color

		if (ImGui::Button(
				"Button"))                            // Buttons return true when clicked (NB: most widgets return true when edited/activated)
			counter++;
		ImGui::SameLine();
		ImGui::Text("counter = %d", counter);

		ImGui::Text("Application average %.3f ms/frame (%.1f FPS)", 1000.0f / ImGui::GetIO().Framerate,
		            ImGui::GetIO().Framerate);
	}

	// 3. Show the ImGui demo window. Most of the sample code is in ImGui::ShowDemoWindow(). Read its code to learn more about Dear ImGui!
	if (show_demo_window) {
		ImGui::SetNextWindowPos(ImVec2(650, 20),
		                        ImGuiCond_FirstUseEver); // Normally user code doesn't need/want to call this because positions are saved in .ini file anyway. Here we just want to make the demo initial state a bit more friendly!
		ImGui::ShowDemoWindow(&show_demo_window);
	}
}

jboolean Java_org_ice1000_jimgui_JImGui_windowShouldClose(JNIEnv *, jclass, jlong nativeObjectPtr) {
	return static_cast<jboolean>(glfwWindowShouldClose(reinterpret_cast<GLFWwindow *>(nativeObjectPtr)));
}

void Java_org_ice1000_jimgui_JImGui_initNewFrame(JNIEnv *, jobject) {
	glfwPollEvents();
	ImGui_ImplGlfwGL2_NewFrame();
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
	ImGui_ImplGlfwGL2_RenderDrawData(ImGui::GetDrawData());
	glfwSwapBuffers(window);
}

void Java_org_ice1000_jimgui_JImGui_text(JNIEnv *env, jclass, jbyteArray _text) {
	__JNI__FUNCTION__INIT__
	__get(Byte, text);
	ImGui::Text(reinterpret_cast<const char *>(text));
	__release(Byte, text);
	__JNI__FUNCTION__CLEAN__
}

jboolean Java_org_ice1000_jimgui_JImGui_button___3B(JNIEnv *env, jclass, jbyteArray _text) {
	__JNI__FUNCTION__INIT__
	__get(Byte, text);
	auto res = ImGui::Button(reinterpret_cast<const char *>(text));
	__release(Byte, text);
	__JNI__FUNCTION__CLEAN__
	return static_cast<jboolean>(res ? JNI_TRUE : JNI_FALSE);
}

jboolean Java_org_ice1000_jimgui_JImGui_smallButton(JNIEnv *env, jclass, jbyteArray _text) {
	__JNI__FUNCTION__INIT__
	__get(Byte, text);
	auto res = ImGui::SmallButton(reinterpret_cast<const char *>(text));
	__release(Byte, text);
	__JNI__FUNCTION__CLEAN__
	return static_cast<jboolean>(res ? JNI_TRUE : JNI_FALSE);
}

jboolean Java_org_ice1000_jimgui_JImGui_button___3BFF(
		JNIEnv *env, jclass, jbyteArray _text, jfloat width, jfloat height) {
	__JNI__FUNCTION__INIT__
	__get(Byte, text);
	auto res = ImGui::Button(reinterpret_cast<const char *>(text), ImVec2(width, height));
	__release(Byte, text);
	__JNI__FUNCTION__CLEAN__
	return static_cast<jboolean>(res ? JNI_TRUE : JNI_FALSE);
}

void Java_org_ice1000_jimgui_JImGui_sameLine(JNIEnv *, jobject, jfloat pos_x, jfloat spacing_w) {
	ImGui::SameLine(pos_x, spacing_w);
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