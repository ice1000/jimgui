#!/usr/bin/env bash
rm -rf imgui
mkdir imgui
wget https://raw.githubusercontent.com/ocornut/imgui/master/imgui.cpp --directory-prefix=imgui
wget https://raw.githubusercontent.com/ocornut/imgui/master/imgui.h --directory-prefix=imgui
wget https://raw.githubusercontent.com/ocornut/imgui/master/imgui_demo.cpp --directory-prefix=imgui
wget https://raw.githubusercontent.com/ocornut/imgui/master/imgui_draw.cpp --directory-prefix=imgui
wget https://raw.githubusercontent.com/ocornut/imgui/master/imgui_internal.h --directory-prefix=imgui
wget https://raw.githubusercontent.com/ocornut/imgui/master/imconfig.h --directory-prefix=imgui
wget https://raw.githubusercontent.com/ocornut/imgui/master/stb_rect_pack.h --directory-prefix=imgui
wget https://raw.githubusercontent.com/ocornut/imgui/master/stb_textedit.h --directory-prefix=imgui
wget https://raw.githubusercontent.com/ocornut/imgui/master/stb_truetype.h --directory-prefix=imgui
# wget https://raw.githubusercontent.com/ocornut/imgui/master/examples/opengl3_example/imgui_impl_glfw_gl3.h --directory-prefix=imgui
# wget https://raw.githubusercontent.com/ocornut/imgui/master/examples/opengl3_example/imgui_impl_glfw_gl3.cpp --directory-prefix=imgui
wget https://raw.githubusercontent.com/ocornut/imgui/master/examples/opengl2_example/imgui_impl_glfw_gl2.h --directory-prefix=imgui
wget https://raw.githubusercontent.com/ocornut/imgui/master/examples/opengl2_example/imgui_impl_glfw_gl2.cpp --directory-prefix=imgui
