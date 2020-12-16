//
// Created by ice1000 on 2020/12/16.
//

#include <CustomFont.cpp>
#include <ImGuiFileDialog.h>
#include <org_ice1000_jimgui_JImFileDialog.h>
#include "basics.hpp"

JNIEXPORT void JNICALL Java_org_ice1000_jimgui_JImFileDialog_loadIcons(JNIEnv *, jclass) {
  static const ImWchar icons_ranges[] = { ICON_MIN_IGFD, ICON_MAX_IGFD, 0 };
  ImFontConfig icons_config; icons_config.MergeMode = true; icons_config.PixelSnapH = true;
  ImGui::GetIO().Fonts->AddFontFromMemoryCompressedBase85TTF(FONT_ICON_BUFFER_NAME_IGFD, 15.0f, &icons_config, icons_ranges);
}

JNIEXPORT jboolean JNICALL
JavaCritical_org_ice1000_jimgui_JImFileDialog_fileDialog(
    jint keyLen, jbyte *key, jint flags,
    jfloat minSizeX, jfloat minSizeY,
    jfloat maxSizeX, jfloat maxSizeY
) {
  auto ret = igfd::ImGuiFileDialog::Instance()->FileDialog(
      STR_J2C(key), flags, ImVec2(minSizeX, minSizeY),
      ImVec2(maxSizeX, maxSizeY));
  return (ret ? JNI_TRUE : JNI_FALSE);
}

JNIEXPORT jboolean JNICALL
Java_org_ice1000_jimgui_JImFileDialog_fileDialog(
    JNIEnv *env, jclass,
    jbyteArray _key, jint flags,
    jfloat minSizeX, jfloat minSizeY,
    jfloat maxSizeX, jfloat maxSizeY
) {
  __get(Byte, key)
  auto ret = JavaCritical_org_ice1000_jimgui_JImFileDialog_fileDialog(
      -1, key, flags, minSizeX, minSizeY, maxSizeX, maxSizeY);
  __release(Byte, key)
  return ret;
}
