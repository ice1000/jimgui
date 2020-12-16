//
// Created by ice1000 on 2020/12/16.
//

#include <CustomFont.cpp>
#include <ImGuiFileDialog.h>
#include <org_ice1000_jimgui_JImFileDialog.h>
#include "basics.hpp"

using igfd::ImGuiFileDialog;

extern "C" {
JNIEXPORT void JNICALL Java_org_ice1000_jimgui_JImFileDialog_loadIcons(JNIEnv *, jclass, jfloat fontSize) {
  static const ImWchar icons_ranges[] = {ICON_MIN_IGFD, ICON_MAX_IGFD, 0};
  ImFontConfig icons_config;
  icons_config.MergeMode = true;
  icons_config.PixelSnapH = true;
  ImGui::GetIO().Fonts->AddFontFromMemoryCompressedBase85TTF(
      FONT_ICON_BUFFER_NAME_IGFD, fontSize, &icons_config, icons_ranges);
}

JNIEXPORT jboolean JNICALL
JavaCritical_org_ice1000_jimgui_JImFileDialog_fileDialogP(
    jlong stringPtr, jint flags,
    jfloat minSizeX, jfloat minSizeY,
    jfloat maxSizeX, jfloat maxSizeY
) {
  auto ret = ImGuiFileDialog::Instance()->FileDialog(
      *PTR_J2C(std::string, stringPtr), flags, ImVec2(minSizeX, minSizeY),
      ImVec2(maxSizeX, maxSizeY));
  return (ret ? JNI_TRUE : JNI_FALSE);
}

JNIEXPORT jboolean JNICALL
Java_org_ice1000_jimgui_JImFileDialog_fileDialogP(
    JNIEnv *, jclass,
    jlong stringPtr, jint flags,
    jfloat minSizeX, jfloat minSizeY,
    jfloat maxSizeX, jfloat maxSizeY
) {
  return JavaCritical_org_ice1000_jimgui_JImFileDialog_fileDialogP(
      stringPtr, flags, minSizeX, minSizeY, maxSizeX, maxSizeY);
}

JNIEXPORT jlong JNICALL JavaCritical_org_ice1000_jimgui_JImFileDialog_currentPath0() {
  return PTR_C2J(new std::string(ImGuiFileDialog::Instance()->GetCurrentPath()));
}

JNIEXPORT jlong JNICALL JavaCritical_org_ice1000_jimgui_JImFileDialog_currentFileName0() {
  return PTR_C2J(new std::string(ImGuiFileDialog::Instance()->GetCurrentFileName()));
}

JNIEXPORT jlong JNICALL JavaCritical_org_ice1000_jimgui_JImFileDialog_filePathName0() {
  return PTR_C2J(new std::string(ImGuiFileDialog::Instance()->GetFilePathName()));
}

#define GET_STRING_FUNCTION(name) \
JNIEXPORT jlong JNICALL Java_org_ice1000_jimgui_JImFileDialog_ ## name(JNIEnv *env, jclass) { \
  return JavaCritical_org_ice1000_jimgui_JImFileDialog_ ## name(); \
}

GET_STRING_FUNCTION(currentPath0)
GET_STRING_FUNCTION(currentFileName0)
GET_STRING_FUNCTION(filePathName0)

#undef GET_STRING_FUNCTION
}
