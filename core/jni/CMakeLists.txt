cmake_minimum_required(VERSION 3.5)
project(jimgui
		VERSION 0.1
		LANGUAGES C CXX)

set(CMAKE_CXX_STANDARD 11)

find_package(Java REQUIRED)
find_package(JNI REQUIRED)

if (WIN32)
	set(USE_WINSDK_HEADERS TRUE)

	set(IMGUI_IMPL impl/imgui_impl_dx11.cpp dx11_impl.cpp)
	set(TARGET_LINK_LIBS d3dcompiler d3d11 d3dx11 dxguid)
else ()
	find_package(OpenGL REQUIRED)
	find_package(PkgConfig REQUIRED)
	find_package(glfw3 REQUIRED)

	pkg_search_module(GLFW REQUIRED glfw3)

	if (glfw3_FOUND)
		message(STATUS "GLFW_INCLUDE_DIRS=${GLFW_INCLUDE_DIRS}")
	endif ()

	set(IMGUI_IMPL impl/imgui_impl_glfw_gl3.cpp impl/gl3w.c glfw_impl.cpp)
	include_directories(${GLFW_INCLUDE_DIRS})

	set(TARGET_LINK_LIBS ${GLFW_STATIC_LIBRARIES})
endif ()

if (JNI_FOUND)
	message(STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
	message(STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
endif ()

include_directories(${JNI_INCLUDE_DIRS} imgui javah impl)

link_directories(imgui impl)

add_library(jimgui SHARED
		imgui
		imgui/imgui.cpp
		imgui/imgui_draw.cpp
		${IMGUI_IMPL}
		javah
		generated_imgui.cpp
		generated_imgui_io.cpp
		hand_written_bindings.cpp
		overloads_helper.cpp)

target_link_libraries(jimgui ${TARGET_LINK_LIBS})
