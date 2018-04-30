//
// Created by ice1000 on 18-4-30.
//

#include <imgui.h>
#include <imgui_impl_glfw_gl2.h>
#include <GLFW/glfw3.h>

#include <org_ice1000_jimgui_JImGui.h>

static void glfw_error_callback(int error, const char *description) {
	fprintf(stderr, "Error %d: %s\n", error, description);
}

jlong Java_org_ice1000_jimgui_JImGui_allocateNativeObjects(JNIEnv *, jclass) {
	glfwSetErrorCallback(glfw_error_callback);
	if (!glfwInit())
		return NULL;
	GLFWwindow *window = glfwCreateWindow(1280, 720, "ImGui GLFW+OpenGL2 example", nullptr, nullptr);
	glfwMakeContextCurrent(window);
	glfwSwapInterval(1); // Enable vsync

	// Setup Dear ImGui binding
	IMGUI_CHECKVERSION();
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

