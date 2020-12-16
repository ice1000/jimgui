cmake_minimum_required(VERSION 3.14)
project(jimgui
		VERSION 0.1
		LANGUAGES C CXX)

set(CMAKE_CXX_STANDARD 11)
set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${CMAKE_SOURCE_DIR}/cmake-modules/")

find_package(Java REQUIRED)
find_package(JNI REQUIRED)

if (WIN32)
	set(USE_WINSDK_HEADERS TRUE)
	set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} /Ox -source-charset:utf-8")
	if (DX11)
		set(IMGUI_IMPL project/win32_impl.cpp impl/imgui_impl_dx11.cpp impl/imgui_impl_win32.cpp project/dx11_impl.cpp)
		list(APPEND TARGET_LINK_LIBS d3d11 d3dcompiler dxgi)
		set(RENDERER_NAME DirectX11)
	else ()
		find_package(DirectX REQUIRED)

		if (DIRECTX_FOUND)
			message(STATUS "DIRECTX_INCLUDE_DIRS=${DIRECTX_INCLUDE_DIRS}")
			message(STATUS "DIRECTX_D3DX9_LIBRARIES=${DIRECTX_D3DX9_LIBRARIES}")
			message(STATUS "DXSDK_DIRS=${DXSDK_DIRS}")
		endif ()
		include_directories(${DIRECTX_INCLUDE_DIRS})
		include_directories("${DXSDK_DIRS}\\Include")

		set(IMGUI_IMPL project/win32_impl.cpp impl/imgui_impl_dx9.cpp impl/imgui_impl_win32.cpp project/dx9_impl.cpp)
		set(TARGET_LINK_LIBS d3d9)
		list(APPEND TARGET_LINK_LIBS ${DIRECTX_D3DX9_LIBRARIES})

	endif ()

	if (MSVC)
		add_definitions(-D_USE_MATH_DEFINES)
		add_definitions(-DMSVC)
	endif ()
else ()
	find_package(OpenGL REQUIRED)
	find_package(PkgConfig REQUIRED)
	find_package(glfw3 REQUIRED)

	pkg_search_module(GLFW REQUIRED glfw3)

	if (glfw3_FOUND)
		message(STATUS "GLFW_INCLUDE_DIRS=${GLFW_INCLUDE_DIRS}")
	endif ()

	set(IMGUI_IMPL impl/imgui_impl_glfw.cpp impl/imgui_impl_opengl3.cpp impl/gl3w.c project/glfw_impl.cpp)
	include_directories(${GLFW_INCLUDE_DIRS})

	set(TARGET_LINK_LIBS ${GLFW_STATIC_LIBRARIES})
	set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wno-format-security -O3")

	if (APPLE)
		include_directories(/System/Library/Frameworks)
		find_library(COCOA_LIBRARY Cocoa)
		find_library(GLUT_LIBRARY GLUT)
		find_library(OpenGL_LIBRARY OpenGL)

		mark_as_advanced(COCOA_LIBRARY GLUT_LIBRARY OpenGL_LIBRARY)

		# Fix _CVDisplayLinkRelease
		set(CMAKE_SHARED_LINK_FLAGS "${CMAKE_SHARED_LINK_FLAGS} -framework IOKit -framework CoreVideo -framework CoreFoundation")
		set(EXTRA_LIBS ${COCOA_LIBRARY} ${GLUT_LIBRARY} ${OpenGL_LIBRARY})
		list(APPEND TARGET_LINK_LIBS ${EXTRA_LIBS})
	endif ()
endif ()

if (JNI_FOUND)
	message(STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
	message(STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
endif ()

include_directories(${JNI_INCLUDE_DIRS} project imgui javah impl config)

link_directories(imgui impl)

set(ALL_LIBRARIES
		imgui
		imgui/imgui.cpp
		imgui/imgui_widgets.cpp
		imgui/imgui_tables.cpp
		imgui/imgui_stdlib.cpp
		imgui/imgui_draw.cpp
		imgui/imgui_demo.cpp
		${IMGUI_IMPL}
		javah
		gen/generated_imgui.cpp
		gen/generated_imgui_io.cpp
		gen/generated_imgui_font.cpp
		gen/generated_imgui_font_atlas.cpp
		gen/generated_imgui_font_config.cpp
		gen/generated_imgui_draw_list.cpp
		gen/generated_imgui_style.cpp
		project/hand_written_bindings.h
		project/hand_written_bindings.cpp
		project/overloads_helper.cpp
		project/basics.hpp
		project/imgui_ext.cpp
		project/impl_header.h
		project/cpp_interop.cpp)

message(STATUS "CMAKE_GENERATOR_PLATFORM=${CMAKE_GENERATOR_PLATFORM}")
message(STATUS "TARGET_LINK_LIBS=${TARGET_LINK_LIBS}")
# -A x64
if (${CMAKE_GENERATOR_PLATFORM} MATCHES "x64")
	set(64BIT true)
endif ()
if ("${CMAKE_SIZEOF_VOID_P}" EQUAL "8")
	set(64BIT true)
endif ()
if (${64BIT})
	message(STATUS "64 bit architecture")
	add_library(jimgui SHARED ${ALL_LIBRARIES})
	target_link_libraries(jimgui ${TARGET_LINK_LIBS})
else ()
	message(STATUS "32 bit architecture")
	add_library(jimgui32 SHARED ${ALL_LIBRARIES})
	target_link_libraries(jimgui32 ${TARGET_LINK_LIBS})
endif ()
