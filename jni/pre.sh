#!/usr/bin/env bash
rm -rf imgui
rm -rf impl
rm basics.hpp

export GITHUB=https://raw.githubusercontent.com/ocornut/imgui/master
export COV_GITHUB=https://raw.githubusercontent.com/covscript/covscript-imgui/master
export ICE_GITHUB=https://raw.githubusercontent.com/ice1000/algo4j/master
export TO_IMGUI=--directory-prefix=imgui
export TO_IMPL=--directory-prefix=impl

mkdir -p imgui
mkdir -p impl/GL

wget ${GITHUB}/imgui.cpp ${TO_IMGUI}
wget ${GITHUB}/imgui.h ${TO_IMGUI}
wget ${GITHUB}/imgui_draw.cpp ${TO_IMGUI}
wget ${GITHUB}/imgui_demo.cpp ${TO_IMGUI}
wget ${GITHUB}/imgui_internal.h ${TO_IMGUI}
wget ${GITHUB}/imconfig.h ${TO_IMGUI}
wget ${GITHUB}/stb_rect_pack.h ${TO_IMGUI}
wget ${GITHUB}/stb_textedit.h ${TO_IMGUI}
wget ${GITHUB}/stb_truetype.h ${TO_IMGUI}
wget ${GITHUB}/examples/opengl3_example/imgui_impl_glfw_gl3.h ${TO_IMPL}
wget ${GITHUB}/examples/opengl3_example/imgui_impl_glfw_gl3.cpp ${TO_IMPL}
wget ${COV_GITHUB}/include/GL/gl3w.h ${TO_IMPL}/GL
wget ${COV_GITHUB}/include/GL/glcorearb.h ${TO_IMPL}/GL
wget ${COV_GITHUB}/src/gl3w.c ${TO_IMPL}
wget ${ICE_GITHUB}/jni/global/basics.hpp
