cmake_minimum_required(VERSION 3.5)
project(jimgui
		VERSION 0.1
		LANGUAGES C CXX)

set(CMAKE_CXX_STANDARD 11)

find_package(Java REQUIRED)
find_package(JNI REQUIRED)
find_package(OpenGL REQUIRED)
find_package(PkgConfig REQUIRED)
find_package(glfw3 REQUIRED)
#find_package(gl3w QUIET)

pkg_search_module(GLFW REQUIRED glfw3)

if (JNI_FOUND)
	message(STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
	message(STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
endif ()

if (glfw3_FOUND)
	message(STATUS "GLFW_INCLUDE_DIRS=${GLFW_INCLUDE_DIRS}")
endif ()

#set(IMGUI_IMPL impl/imgui_impl_glfw_gl3.cpp)
set(IMGUI_IMPL impl/imgui_impl_glfw_gl2.cpp)

include_directories(${JNI_INCLUDE_DIRS} ${GLFW_INCLUDE_DIRS} imgui javah impl)

link_directories(imgui impl)

add_library(jimgui SHARED
		imgui
		imgui/imgui.cpp
		imgui/imgui_demo.cpp
		imgui/imgui_draw.cpp
		${IMGUI_IMPL}
		javah
		jimgui.cpp)

target_link_libraries(jimgui ${GLFW_STATIC_LIBRARIES})
