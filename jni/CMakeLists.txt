cmake_minimum_required(VERSION 3.10)
project(libimgui
        VERSION 0.1
        LANGUAGES C CXX)

set(CMAKE_CXX_STANDARD 11)

find_package(Java REQUIRED)
find_package(JNI REQUIRED)
find_package(OpenGL REQUIRED)
find_package(glfw3 REQUIRED)
find_package(gl3w QUIET)

if (JNI_FOUND)
    message(STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
    message(STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
endif ()

if (gl3w_FOUND)
    set(IMGUI_IMPL imgui/imgui_impl_glfw_gl3.cpp)
else ()
    set(IMGUI_IMPL imgui/imgui_impl_glfw_gl2.cpp)
endif ()

include_directories(${JNI_INCLUDE_DIRS} imgui javah)

add_library(libimgui SHARED imgui/imgui.cpp ${IMGUI_CPP} jimgui.cpp)

target_link_libraries(libimgui)
