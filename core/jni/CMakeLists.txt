cmake_minimum_required(VERSION 3.5)
project(jimgui
		VERSION 0.1
		LANGUAGES C CXX)

set(CMAKE_CXX_STANDARD 11)

find_package(Java REQUIRED)
find_package(JNI REQUIRED)

if (WIN32)
	set(USE_WINSDK_HEADERS TRUE)
	set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -O3")

	set(IMGUI_IMPL impl/imgui_impl_dx11.cpp dx11_impl.cpp)
	set(TARGET_LINK_LIBS d3dcompiler d3d11)
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
	set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wno-format-security -O3")

	if (APPLE)
		include_directories(/System/Library/Frameworks)
		find_library(COCOA_LIBRARY Cocoa)
		find_library(GLUT_LIBRARY GLUT)
		find_library(OpenGL_LIBRARY OpenGL)
		mark_as_advanced(COCOA_LIBRARY GLUT_LIBRARY OpenGL_LIBRARY)
		set(EXTRA_LIBS ${COCOA_LIBRARY} ${GLUT_LIBRARY} ${OpenGL_LIBRARY})
		list(APPEND TARGET_LINK_LIBS ${EXTRA_LIBS})
	endif ()
endif ()

if (JNI_FOUND)
	message(STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
	message(STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
endif ()

include_directories(${JNI_INCLUDE_DIRS} imgui javah impl config)

link_directories(imgui impl)

set(ALL_LIBRARIES
		imgui
		imgui/imgui.cpp
		imgui/imgui_draw.cpp
		imgui/imgui_demo.cpp
		${IMGUI_IMPL}
		javah
		generated_imgui.cpp
		generated_imgui_io.cpp
		generated_imgui_font.cpp
		generated_imgui_font_atlas.cpp
		generated_imgui_draw_list.cpp
		generated_imgui_style.cpp
		hand_written_bindings.cpp
		overloads_helper.cpp
		cpp_interop.cpp)

if ("${CMAKE_SIZEOF_VOID_P}" EQUAL "8")
	message(STATUS "64 bit architecture")
	add_library(jimgui SHARED ${ALL_LIBRARIES})
	target_link_libraries(jimgui ${TARGET_LINK_LIBS})
else ()
	message(STATUS "32 bit architecture")
	add_library(jimgui32 SHARED ${ALL_LIBRARIES})
	target_link_libraries(jimgui32 ${TARGET_LINK_LIBS})
endif ()
