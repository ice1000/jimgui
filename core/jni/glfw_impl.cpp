///
/// Created by ice1000 on 18-4-30.
///

#include <imgui.h>
#include <imgui_impl_glfw_gl3.h>
#include "impl/GL/glcorearb.h" // avoid conflicting with system include
#include <GL/gl3w.h>
#include <GLFW/glfw3.h>

#define STB_IMAGE_IMPLEMENTATION
#define STBI_NO_LINEAR
#define STBI_NO_HDR
#define STBI_NO_GIF

#include <stb_image.h>

#include <org_ice1000_jimgui_JImGui.h>
#include <org_ice1000_jimgui_JImTextureID.h>

#include "basics.hpp"
#include "impl_header.h"

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"

static void glfw_error_callback(int error, Ptr<const char> description) {
	fprintf(stderr, "ImGui Error %d: %s\n", error, description);
}

// See https://github.com/capnramses/antons_opengl_tutorials_book/blob/master/09_texture_mapping/main.cpp
auto loadTexture(Ptr<const char> fileName, Ptr<GLuint> tex, int &x, int &y, int &n, int forceChannels = 4) -> bool {
	auto *imageData = stbi_load(fileName, &x, &y, &n, forceChannels);
	if (!imageData) return false;
	// NPOT check
	// if ((x & (x - 1)) != 0 || (y & (y - 1)) != 0)
	// 	fprintf(stderr, "WARNING: texture %s is not power-of-2 dimensions\n", fileName);
	glGenTextures(1, tex);
	glBindTexture(GL_TEXTURE_2D, *tex);
	glPixelStorei(GL_UNPACK_ROW_LENGTH, 0);
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, x, y, 0, GL_RGBA, GL_UNSIGNED_BYTE, imageData);
	return true;
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImTextureID_createTextureFromFile(JNIEnv *env, jclass, jbyteArray _fileName) -> jlongArray {
	__JNI__FUNCTION__INIT__
	__get(Byte, fileName)
	GLuint texture = 0;
	int width, height, channels;
	auto success = loadTexture(STR_J2C(fileName), &texture, width, height, channels);
	if (!success) return nullptr;
	__release(Byte, fileName)
#define RET_LEN 3
	auto ret = new jlong[RET_LEN];
	ret[0] = static_cast<jlong> (texture);
	ret[1] = static_cast<jlong> (width);
	ret[2] = static_cast<jlong> (height);
	__init(Long, ret, RET_LEN);
#undef RET_LEN
	delete[] ret;
	__JNI__FUNCTION__CLEAN__
	return _ret;
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_allocateNativeObjects(
		JNIEnv *env, jclass, jint width, jint height, jbyteArray _title) -> jlong {
	glfwSetErrorCallback(glfw_error_callback);
	if (!glfwInit()) return 0L;
	__JNI__FUNCTION__INIT__
	__get(Byte, title);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
#if __APPLE__
	glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
#endif
	Ptr<GLFWwindow> window = glfwCreateWindow(width, height, STR_J2C(title), nullptr, nullptr);
	glfwMakeContextCurrent(window);
	// Enable vsync
	glfwSwapInterval(1);
	gl3wInit();

	// Setup Dear ImGui binding
	IMGUI_CHECKVERSION();
	ImGui::CreateContext();
	ImGuiIO &io = ImGui::GetIO();
	(void) io;
	// Enable Keyboard Controls
	io.ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard;
	ImGui_ImplGlfwGL3_Init(window, true);
	__release(Byte, title);
	__JNI__FUNCTION__CLEAN__
	return PTR_C2J(window);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_deallocateNativeObjects(jlong nativeObjectPtr) {
	auto *window = PTR_J2C(GLFWwindow, nativeObjectPtr);
	ImGui_ImplGlfwGL3_Shutdown();
	ImGui::DestroyContext();
	glfwDestroyWindow(window);
	glfwTerminate();
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_deallocateNativeObjects(JNIEnv *, jclass, jlong nativeObjectPtr) {
	JavaCritical_org_ice1000_jimgui_JImGui_deallocateNativeObjects(nativeObjectPtr);
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_windowShouldClose(jlong nativeObjectPtr) -> jboolean {
	return static_cast<jboolean>(glfwWindowShouldClose(PTR_J2C(GLFWwindow, nativeObjectPtr)) ? JNI_TRUE : JNI_FALSE);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_windowShouldClose(JNIEnv *, jclass, jlong nativeObjectPtr) -> jboolean {
	return JavaCritical_org_ice1000_jimgui_JImGui_windowShouldClose(nativeObjectPtr);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_initNewFrame(jlong) {
	glfwPollEvents();
	ImGui_ImplGlfwGL3_NewFrame();
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_initNewFrame(JNIEnv *, jclass, jlong ptr) {
	JavaCritical_org_ice1000_jimgui_JImGui_initNewFrame(ptr);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_render(jlong nativeObjectPtr, jlong colorPtr) {
	auto *window = PTR_J2C(GLFWwindow, nativeObjectPtr);
	auto *clear_color = PTR_J2C(ImVec4, colorPtr);
	int display_w, display_h;
	glfwGetFramebufferSize(window, &display_w, &display_h);
	glViewport(0, 0, display_w, display_h);
	glClearColor(clear_color->x, clear_color->y, clear_color->z, clear_color->w);
	glClear(GL_COLOR_BUFFER_BIT);
	ImGui::Render();
	ImGui_ImplGlfwGL3_RenderDrawData(ImGui::GetDrawData());
	glfwSwapBuffers(window);
}

JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImGui_render(JNIEnv *, jclass, jlong nativeObjectPtr, jlong colorPtr) {
	JavaCritical_org_ice1000_jimgui_JImGui_render(nativeObjectPtr, colorPtr);
}

#pragma clang diagnostic pop
