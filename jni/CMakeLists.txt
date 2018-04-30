cmake_minimum_required(VERSION 3.10)
project(libimgui
        VERSION 0.1
        LANGUAGES C CXX)

set(CMAKE_CXX_STANDARD 11)

find_package(Java REQUIRED)
find_package(JNI REQUIRED)

if (JNI_FOUND)
    message(STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
    message(STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
endif ()

set(IMGUI_CPP imgui/imgui.cpp imgui/imgui_impl_glfw_gl3.cpp)

include_directories(${JNI_INCLUDE_DIRS} imgui javah)

add_library(libimgui SHARED ${IMGUI_CPP} jimgui.cpp)

target_link_libraries(libimgui)
