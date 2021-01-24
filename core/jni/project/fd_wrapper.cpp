//
// Created by ice1000 on 2020/12/16.
//

#include <CustomFont.cpp>
#include <ImGuiFileDialog.h>
#include <org_ice1000_jimgui_JImFileDialog.h>
#include "basics.hpp"

using IGFD::FileDialog;

extern "C" {
JNIEXPORT void JNICALL
Java_org_ice1000_jimgui_JImFileDialog_loadIcons(JNIEnv *, jclass, jfloat fontSize) {
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
    jfloat maxSizeX, jfloat maxSizeY,
    jlong nativeObjectPtr
) {
  auto ret = PTR_J2C(FileDialog, nativeObjectPtr)->Display(
      *PTR_J2C(std::string, stringPtr), flags, ImVec2(minSizeX, minSizeY),
      ImVec2(maxSizeX, maxSizeY));
  return (ret ? JNI_TRUE : JNI_FALSE);
}

JNIEXPORT jboolean JNICALL
Java_org_ice1000_jimgui_JImFileDialog_fileDialogP(
    JNIEnv *, jclass,
    jlong stringPtr, jint flags,
    jfloat minSizeX, jfloat minSizeY,
    jfloat maxSizeX, jfloat maxSizeY,
    jlong nativeObjectPtr
) {
  return JavaCritical_org_ice1000_jimgui_JImFileDialog_fileDialogP(
      stringPtr, flags, minSizeX, minSizeY, maxSizeX, maxSizeY, nativeObjectPtr);
}

JNIEXPORT jlong JNICALL
JavaCritical_org_ice1000_jimgui_JImFileDialog_selectedFiles(jlong nativeObjectPtr) {
  auto &&map = PTR_J2C(FileDialog, nativeObjectPtr)->GetSelection();
  auto *vec = new std::vector<std::string>(map.size());
  vec->clear();
  for (auto &&selection : map) vec->push_back(selection.second);
  return PTR_C2J(vec);
}

JNIEXPORT jlong JNICALL
Java_org_ice1000_jimgui_JImFileDialog_selectedFiles(JNIEnv *, jclass, jlong nativeObjectPtr) {
  return JavaCritical_org_ice1000_jimgui_JImFileDialog_selectedFiles(nativeObjectPtr);
}

JNIEXPORT jlong JNICALL JavaCritical_org_ice1000_jimgui_JImFileDialog_currentPath0(jlong nativeObjectPtr) {
  return PTR_C2J(new std::string(PTR_J2C(FileDialog, nativeObjectPtr)->GetCurrentPath()));
}

JNIEXPORT jlong JNICALL JavaCritical_org_ice1000_jimgui_JImFileDialog_currentFileName0(jlong nativeObjectPtr) {
  return PTR_C2J(new std::string(PTR_J2C(FileDialog, nativeObjectPtr)->GetCurrentFileName()));
}

JNIEXPORT jlong JNICALL JavaCritical_org_ice1000_jimgui_JImFileDialog_filePathName0(jlong nativeObjectPtr) {
  return PTR_C2J(new std::string(PTR_J2C(FileDialog, nativeObjectPtr)->GetFilePathName()));
}

#define GET_STRING_FUNCTION(name) \
JNIEXPORT jlong JNICALL Java_org_ice1000_jimgui_JImFileDialog_ ## name(JNIEnv *env, jclass, jlong nativeObjectPtr) { \
  return JavaCritical_org_ice1000_jimgui_JImFileDialog_ ## name(nativeObjectPtr); \
}

GET_STRING_FUNCTION(currentPath0)
GET_STRING_FUNCTION(currentFileName0)
GET_STRING_FUNCTION(filePathName0)

#undef GET_STRING_FUNCTION
}
