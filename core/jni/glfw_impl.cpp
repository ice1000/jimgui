///
/// Created by ice1000 on 18-4-30.
///

#include <imgui.h>
#include <imgui_impl_opengl3.h>
#include <imgui_impl_glfw.h>
#include <stdio.h>
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
auto loadTexture(Ptr<void> imageData, Ptr<GLuint> tex, int x, int y) -> bool {
	// NPOT check
	// if ((x & (x - 1)) != 0 || (y & (y - 1)) != 0)
	// 	fprintf(stderr, "WARNING: texture %s is not power-of-2 dimensions\n", fileName);
	glGenTextures(1, tex);
	glBindTexture(GL_TEXTURE_2D, *tex);
	glPixelStorei(GL_UNPACK_ROW_LENGTH, 0);
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, x, y, 0, GL_RGBA, GL_UNSIGNED_BYTE, imageData);
	glGenerateMipmap(GL_TEXTURE_2D);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImTextureID_createTextureFromFile(Ptr<JNIEnv> env,
                                                           jclass,
                                                           jbyteArray _fileName) -> jlongArray {
	__JNI__FUNCTION__INIT__
	__get(Byte, fileName)
	GLuint texture = 0;
	int width, height, channels;
	int forceChannels = 4;
	auto *imageData = stbi_load(STR_J2C(fileName), &width, &height, &channels, forceChannels);
	__release(Byte, fileName)
	if (!imageData) texture = 0;
	else loadTexture(imageData, &texture, width, height);
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
JavaCritical_org_ice1000_jimgui_JImTextureID_createGlfwTextureFromBytes(jint,
                                                                        Ptr<jbyte> rawData,
                                                                        jint width,
                                                                        jint height) -> jlong {
	GLuint texture = 0;
	loadTexture(rawData, &texture, width, height);
	return texture;
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_allocateNativeObjects(
		JNIEnv *env, jclass, jint width, jint height, jlong fontAtlas, jbyteArray _title) -> jlong {
	glfwSetErrorCallback(glfw_error_callback);
	if (!glfwInit()) return 0L;
	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
#if __APPLE__
	glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
#endif
	__JNI__FUNCTION__INIT__
	__get(Byte, title)
	Ptr<GLFWwindow> window = glfwCreateWindow(width, height, STR_J2C(title), nullptr, nullptr);
	__release(Byte, title)
	__JNI__FUNCTION__CLEAN__
	glfwMakeContextCurrent(window);
	// Enable vsync
	glfwSwapInterval(1);
	gl3wInit();

	// Setup Dear ImGui binding
	IMGUI_CHECKVERSION();
	ImGui::CreateContext(PTR_J2C(ImFontAtlas, fontAtlas));
	ImGuiIO &io = ImGui::GetIO();
	(void) io;
	// Enable Keyboard Controls
	io.ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard;
	ImGui_ImplGlfw_InitForOpenGL(window, true);
	ImGui_ImplOpenGL3_Init();
	return PTR_C2J(window);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_deallocateNativeObjects(jlong nativeObjectPtr) {
	ImGui_ImplOpenGL3_Shutdown();
	ImGui_ImplGlfw_Shutdown();
	ImGui::DestroyContext();
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_deallocateGuiFramework(jlong nativeObjectPtr) {
	auto *window = PTR_J2C(GLFWwindow, nativeObjectPtr);
	glfwDestroyWindow(window);
	glfwTerminate();
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_windowShouldClose(jlong nativeObjectPtr) -> jboolean {
	return static_cast<jboolean>(glfwWindowShouldClose(PTR_J2C(GLFWwindow, nativeObjectPtr)) ? JNI_TRUE : JNI_FALSE);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_initNewFrame(jlong) {
	glfwPollEvents();
	ImGui_ImplOpenGL3_NewFrame();
	ImGui_ImplGlfw_NewFrame();
	ImGui::NewFrame();
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_render(jlong nativeObjectPtr, jlong colorPtr) {
	auto *window = PTR_J2C(GLFWwindow, nativeObjectPtr);
	auto *clear_color = PTR_J2C(ImVec4, colorPtr);
	ImGui::Render();
	int display_w, display_h;
	glfwMakeContextCurrent(window);
	glfwGetFramebufferSize(window, &display_w, &display_h);
	glViewport(0, 0, display_w, display_h);
	glClearColor(clear_color->x, clear_color->y, clear_color->z, clear_color->w);
	glClear(GL_COLOR_BUFFER_BIT);
	ImGui_ImplOpenGL3_RenderDrawData(ImGui::GetDrawData());

	glfwMakeContextCurrent(window);
	glfwSwapBuffers(window);
}

#pragma clang diagnostic pop
