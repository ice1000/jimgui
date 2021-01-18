cmake_minimum_required(VERSION 3.5)
project(jimgui
		VERSION 0.1
		LANGUAGES C CXX)

set(CMAKE_CXX_STANDARD 11)
find_package(Java REQUIRED)
find_package(JNI REQUIRED)

if (JNI_FOUND)
	message(STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
	message(STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
endif ()

set(GLFW_BUILD_DOCS OFF CACHE BOOL "" FORCE)
set(GLFW_BUILD_TESTS OFF CACHE BOOL "" FORCE)
set(GLFW_BUILD_EXAMPLES OFF CACHE BOOL "" FORCE)
add_subdirectory(glfw)

find_package(OpenGL REQUIRED)
find_package(PkgConfig REQUIRED)
find_package(glfw3 REQUIRED)
# pkg_search_module(GLFW REQUIRED glfw3)
if (glfw3_FOUND)
	message(STATUS "GLFW_INCLUDE_DIRS=${GLFW_INCLUDE_DIRS}")
endif ()
set(IMGUI_IMPL ../impl/imgui_impl_glfw.cpp ../impl/imgui_impl_opengl3.cpp ../impl/gl3w.c ../project/glfw_impl.cpp)
include_directories(${GLFW_INCLUDE_DIRS})

set(TARGET_LINK_LIBS glfw opengl32 pthread gdi32 -static-libgcc -static-libstdc++ -static)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wno-format-security -O3")
add_definitions(-DCUSTOM_IMGUIFILEDIALOG_CONFIG="fd_customization.h")
include_directories(${JNI_INCLUDE_DIRS} .. ../imgui ../javah ../impl ../config glfw/include ../3rdparty/fd)
link_directories(../imgui ../impl)

file(GLOB IMGUI_FILES "../imgui/*")
file(GLOB GENERATED_FILES "../gen/*")
file(GLOB FILE_DIALOG_FILES "../3rdparty/fd/*")
file(GLOB DATE_TIME_CHOOSER_FILES "../3rdparty/dtc/*")

set(ALL_LIBRARIES
		${IMGUI_FILES} ${IMGUI_IMPL}
		${GENERATED_FILES}
		${FILE_DIALOG_FILES}
		${DATE_TIME_CHOOSER_FILES}
		../project/fd_wrapper.cpp
    ../project/hand_written_bindings.cpp
		../project/overloads_helper.cpp
		../project/basics.hpp
		../project/imgui_ext.cpp
		../project/impl_header.h
		../project/cpp_interop.cpp)
if (NOT X86)
	message(STATUS "64 bit architecture")
	add_library(jimgui-glfw SHARED ${ALL_LIBRARIES})
	target_link_libraries(jimgui-glfw ${TARGET_LINK_LIBS})
	# add_executable(main-test ${ALL_LIBRARIES} main-test.cpp)
	# target_link_libraries(main-test ${TARGET_LINK_LIBS})
else ()
	set(CMAKE_C_FLAGS -m32)
	set(CMAKE_CXX_FLAGS -m32)
	message(STATUS "32 bit architecture")
	add_library(jimgui32-glfw SHARED ${ALL_LIBRARIES})
	target_link_libraries(jimgui32-glfw ${TARGET_LINK_LIBS})
endif ()
