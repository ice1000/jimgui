rm -rf imgui
rm -rf impl

export GITHUB=https://raw.githubusercontent.com/ocornut/imgui/master
export TO_IMGUI=--directory-prefix=imgui
export TO_IMPL=--directory-prefix=impl

mkdir imgui
mkdir impl

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
wget ${GITHUB}/examples/opengl2_example/imgui_impl_glfw_gl2.h ${TO_IMPL}
wget ${GITHUB}/examples/opengl2_example/imgui_impl_glfw_gl2.cpp ${TO_IMPL}
