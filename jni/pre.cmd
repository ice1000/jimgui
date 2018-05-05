set GITHUB=https://raw.githubusercontent.com/ocornut/imgui/master
set COV_GITHUB=https://raw.githubusercontent.com/covscript/covscript-imgui/master
set ICE_GITHUB=https://raw.githubusercontent.com/ice1000/algo4j/master
set TO_IMGUI=--directory-prefix=imgui
set TO_IMPL=--directory-prefix=impl

mkdir imgui
mkdir impl
mkdir impl/GL

wget %GITHUB%/imgui.cpp %TO_IMGUI%
wget %GITHUB%/imgui.h %TO_IMGUI%
wget %GITHUB%/imgui_draw.cpp %TO_IMGUI%
wget %GITHUB%/imgui_internal.h %TO_IMGUI%
wget %GITHUB%/imconfig.h %TO_IMGUI%
wget %GITHUB%/stb_rect_pack.h %TO_IMGUI%
wget %GITHUB%/stb_textedit.h %TO_IMGUI%
wget %GITHUB%/stb_truetype.h %TO_IMGUI%
wget %GITHUB%/examples/opengl3_example/imgui_impl_glfw_gl3.h %TO_IMPL%
wget %GITHUB%/examples/opengl3_example/imgui_impl_glfw_gl3.cpp %TO_IMPL%
wget %COV_GITHUB%/include/GL/gl3w.h %TO_IMPL%/GL
wget %COV_GITHUB%/include/GL/glcorearb.h %TO_IMPL%/GL
wget %COV_GITHUB%/src/gl3w.c %TO_IMPL%
