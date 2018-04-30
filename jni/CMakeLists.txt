cmake_minimum_required(VERSION 3.10)
project(jimgui
        VERSION 0.1
        LANGUAGES C CXX)

set(CMAKE_CXX_STANDARD 11)

find_package(Java REQUIRED)
find_package(JNI REQUIRED)
find_package(OpenGL REQUIRED)
find_package(glfw3 REQUIRED)
#find_package(gl3w QUIET)

if (JNI_FOUND)
    message(STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
    message(STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
endif ()

#set(IMGUI_IMPL impl/imgui_impl_glfw_gl3.cpp)
set(IMGUI_IMPL impl/imgui_impl_glfw_gl2.cpp)

include_directories(${JNI_INCLUDE_DIRS} imgui javah impl)

add_library(jimgui SHARED imgui javah ${IMGUI_CPP} jimgui.cpp)

target_link_libraries(jimgui)
