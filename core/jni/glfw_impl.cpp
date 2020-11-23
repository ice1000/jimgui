///
/// Created by ice1000 on 18-4-30.
///

#include <imgui.h>
#include <imgui_impl_opengl3.h>
#include <imgui_impl_glfw.h>
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

#include <basics.hpp>
#include <impl_header.h>

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"

#if __APPLE__
	// GL 3.2 + GLSL 150
	#define GLSL_VERSION "#version 150"
	#define OPENGL_MAJOR_VERSION 3
	#define OPENGL_MINOR_VERSION 2
#else
	// GL 3.0 + GLSL 130
	#define GLSL_VERSION "#version 130"
	#define OPENGL_MAJOR_VERSION 3
	#define OPENGL_MINOR_VERSION 0
#endif

static void glfw_error_callback(int error, Ptr<const char> description) {
	fprintf(stderr, "JImGui Error %d: %s\n", error, description);
}

void setupGlfw() {
	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, OPENGL_MAJOR_VERSION);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, OPENGL_MINOR_VERSION);
	if (OPENGL_MAJOR_VERSION >= 3 && OPENGL_MINOR_VERSION >= 2)
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);  // 3.2+ only
}

// See https://github.com/capnramses/antons_opengl_tutorials_book/blob/master/09_texture_mapping/main.cpp
void initTexture(Ptr<void> imageData, Ptr<GLuint> tex, int x, int y) {
	// NPOT check
	// if ((x & (x - 1)) != 0 || (y & (y - 1)) != 0)
	// 	fprintf(stderr, "WARNING: texture %s is not power-of-2 dimensions\n", fileName);
	glGenTextures(1, tex);
	glBindTexture(GL_TEXTURE_2D, *tex);
	// glPixelStorei(GL_UNPACK_ROW_LENGTH, 0);
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, x, y, 0, GL_RGBA, GL_UNSIGNED_BYTE, imageData);
	glGenerateMipmap(GL_TEXTURE_2D);
	// glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
	// glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	// glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	// glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
}

namespace jimgui {
	void setWindowBounds(Ptr<GLFWwindow> window, const ImVec4& bounds) {
		glfwSetWindowPos(window, static_cast<int>(bounds.x), static_cast<int>(bounds.y));
		glfwSetWindowSize(window, static_cast<int>(bounds.z), static_cast<int>(bounds.w));
	}

	ImVec4 getWindowBounds(Ptr<GLFWwindow> window) {
		int x, y, xPlusW, yPlusH;
		glfwGetWindowFrameSize(window, &x, &y, &xPlusW, &yPlusH);
		return {
				static_cast<float>(x), static_cast<float>(y),
				static_cast<float>(xPlusW - x), static_cast<float>(yPlusH - y)
		};
	}
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_glfw_GlfwUtil_createWindowPointer0(jint width,
                                                                   jint height,
                                                                   Ptr<jbyte> title,
                                                                   jlong anotherWindow) -> jlong {
	setupGlfw();
	auto *share = PTR_J2C(GLFWwindow, anotherWindow);
	auto monitor = share != nullptr ? glfwGetWindowMonitor(share) : nullptr;
	return PTR_C2J(glfwCreateWindow(width, height, STR_J2C(title), monitor, share));
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImTextureID_createTextureFromFile(
	Ptr<JNIEnv> env,
	jclass,
	jbyteArray _fileName
) -> jlongArray {
	__get(Byte, fileName)
	GLuint texture = 0;
	int width, height, channels;
	int forceChannels = 4;
	auto *imageData = stbi_load(STR_J2C(fileName), &width, &height, &channels, forceChannels);
	__release(Byte, fileName)
	if (!imageData) texture = 0;
	else initTexture(imageData, &texture, width, height);
	stbi_image_free(imageData);
#define RET_LEN 3
	auto ret = new jlong[RET_LEN];
	ret[0] = static_cast<jlong> (texture);
	ret[1] = static_cast<jlong> (width);
	ret[2] = static_cast<jlong> (height);
	__init(Long, ret, RET_LEN);
#undef RET_LEN
	delete[] ret;
	return _ret;
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImTextureID_createTextureFromBytes(
	Ptr<JNIEnv> env,
	jclass,
	jbyteArray _rawData,
	jint size
) -> jlongArray {
	__get(Byte, rawData)
	GLuint texture = 0;
	int width, height, channels;
	int forceChannels = 4;
	auto *imageData = stbi_load_from_memory(PTR_J2C(stbi_uc, rawData), size, &width, &height, &channels, forceChannels);
	__release(Byte, rawData)
	if (!imageData) texture = 0;
	else initTexture(imageData, &texture, width, height);
	stbi_image_free(imageData);
#define RET_LEN 3
	auto ret = new jlong[RET_LEN];
	ret[0] = static_cast<jlong> (texture);
	ret[1] = static_cast<jlong> (width);
	ret[2] = static_cast<jlong> (height);
	__init(Long, ret, RET_LEN);
#undef RET_LEN
	delete[] ret;
	return _ret;
}

JNIEXPORT auto JNICALL
Java_org_ice1000_jimgui_JImGui_allocateNativeObjects(
		JNIEnv *env, jclass, jint width, jint height, jlong fontAtlas, jbyteArray _title, jlong anotherWindow) -> jlong {
	auto *share = PTR_J2C(GLFWwindow, anotherWindow);
	glfwSetErrorCallback(glfw_error_callback);
	if (!glfwInit()) return 0L;
	setupGlfw();
#if __APPLE__
	glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);            // Required on Mac
#endif
	__get(Byte, title)
	auto monitor = share != nullptr ? glfwGetWindowMonitor(share) : nullptr;
	Ptr<GLFWwindow> window = glfwCreateWindow(width, height, STR_J2C(title), monitor, share);
	__release(Byte, title)
	if (!window) return 0L;
	glfwMakeContextCurrent(window);
	// Enable vsync
	glfwSwapInterval(1);
	return PTR_C2J(window);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_initBeforeMainLoop(jlong) {
	// Do nothing
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setupImguiSpecificObjects(jlong nativeObjectPtr, jlong fontAtlas) {
	gl3wInit();
	auto *window = PTR_J2C(GLFWwindow, nativeObjectPtr);
	IMGUI_CHECKVERSION();
	ImGui::CreateContext(PTR_J2C(ImFontAtlas, fontAtlas));
	ImGuiIO &io = ImGui::GetIO();
	// Enable Keyboard Controls
	io.ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard;
	ImGui_ImplGlfw_InitForOpenGL(window, true);
	ImGui_ImplOpenGL3_Init(GLSL_VERSION);
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
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	ImGui_ImplOpenGL3_RenderDrawData(ImGui::GetDrawData());

	glfwMakeContextCurrent(window);
	glfwSwapBuffers(window);
}


JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowSizeX(jlong nativeObjectPtr) -> float {
	auto *window = PTR_J2C(GLFWwindow, nativeObjectPtr);
	int w, h;
	glfwGetWindowSize(window, &w, &h);
	return w;
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowSizeY(jlong nativeObjectPtr) -> float {
	auto *window = PTR_J2C(GLFWwindow, nativeObjectPtr);
	int w, h;
	glfwGetWindowSize(window, &w, &h);
	return h;
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowPosX(jlong nativeObjectPtr) -> float {
	auto *window = PTR_J2C(GLFWwindow, nativeObjectPtr);
	int w, h;
	glfwGetWindowPos(window, &w, &h);
	return w;
}

JNIEXPORT auto JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_getPlatformWindowPosY(jlong nativeObjectPtr) -> float {
	auto *window = PTR_J2C(GLFWwindow, nativeObjectPtr);
	int w, h;
	glfwGetWindowPos(window, &w, &h);
	return h;
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowSizeX(jlong nativeObjectPtr, float newValue) {
	auto *window = PTR_J2C(GLFWwindow, nativeObjectPtr);
	int w, h;
	glfwGetWindowSize(window, &w, &h);
	glfwSetWindowSize(window, static_cast<int>(newValue), h);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowSizeY(jlong nativeObjectPtr, float newValue) {
	auto *window = PTR_J2C(GLFWwindow, nativeObjectPtr);
	int w, h;
	glfwGetWindowSize(window, &w, &h);
	glfwSetWindowSize(window, w, static_cast<int>(newValue));
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowPosX(jlong nativeObjectPtr, float newValue) {
	auto *window = PTR_J2C(GLFWwindow, nativeObjectPtr);
	int w, h;
	glfwGetWindowPos(window, &w, &h);
	glfwSetWindowPos(window, static_cast<int>(newValue), h);
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowPosY(jlong nativeObjectPtr, float newValue) {
	auto *window = PTR_J2C(GLFWwindow, nativeObjectPtr);
	int w, h;
	glfwGetWindowPos(window, &w, &h);
	glfwSetWindowPos(window, w, static_cast<int>(newValue));
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowSize(jlong nativeObjectPtr, float newX, float newY) {
	auto *window = PTR_J2C(GLFWwindow, nativeObjectPtr);
	glfwSetWindowSize(window, static_cast<int>(newX), static_cast<int>(newY));
}

JNIEXPORT void JNICALL
JavaCritical_org_ice1000_jimgui_JImGui_setPlatformWindowPos(jlong nativeObjectPtr, float newX, float newY) {
	auto *window = PTR_J2C(GLFWwindow, nativeObjectPtr);
	glfwSetWindowPos(window, static_cast<int>(newX), static_cast<int>(newY));
}

#pragma clang diagnostic pop
