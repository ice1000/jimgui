cmake_minimum_required(VERSION 3.14)
project(jimgui
    VERSION 114.514
    LANGUAGES C CXX)

set(CMAKE_CXX_STANDARD 11)
set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${CMAKE_SOURCE_DIR}/cmake-modules/")

find_package(Java REQUIRED)
find_package(JNI REQUIRED)

if (WIN32)
  set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} /Ox -source-charset:utf-8")
  set(DX_COMMON project/impl_header.cpp project/win32_impl.cpp)
  set(DX11_IMPL ${DX_COMMON} impl/imgui_impl_dx11.cpp project/dx11_impl.cpp)
  list(APPEND TARGET_LINK_LIBS_DX11 d3d11 d3dcompiler dxgi)
  find_package(DirectX REQUIRED)

  if (DIRECTX_FOUND)
    message(STATUS "DIRECTX_INCLUDE_DIRS=${DIRECTX_INCLUDE_DIRS}")
    message(STATUS "DIRECTX_D3DX9_LIBRARIES=${DIRECTX_D3DX9_LIBRARIES}")
    message(STATUS "DXSDK_DIRS=${DXSDK_DIRS}")
  endif ()
  include_directories(${DIRECTX_INCLUDE_DIRS})
  include_directories("${DXSDK_DIRS}\\Include")

  set(DX9_IMPL ${DX_COMMON} impl/imgui_impl_dx9.cpp project/dx9_impl.cpp)
  set(TARGET_LINK_LIBS_DX9 d3d9)
  list(APPEND TARGET_LINK_LIBS_DX9 ${DIRECTX_D3DX9_LIBRARIES})
  set(IMGUI_IMPL impl/imgui_impl_win32.cpp)

  if (MSVC)
    add_definitions(-D_USE_MATH_DEFINES)
    add_definitions(-DMSVC)
  endif ()
else ()
  find_package(OpenGL REQUIRED)
  find_package(PkgConfig REQUIRED)

  set(IMGUI_IMPL project/impl_header.cpp impl/imgui_impl_glfw.cpp impl/imgui_impl_opengl3.cpp impl/gl3w.c project/glfw_impl.cpp)
  include_directories(${GLFW_INCLUDE_DIRS})

  set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wno-format-security -O3")

  if (APPLE)
    include_directories(/System/Library/Frameworks)
    find_library(COCOA_LIBRARY Cocoa)
    find_library(GLUT_LIBRARY GLUT)
    find_library(OpenGL_LIBRARY OpenGL)

    mark_as_advanced(COCOA_LIBRARY GLUT_LIBRARY OpenGL_LIBRARY)

    # Compiled universal library to support both x86_64 and arm64
    set(CMAKE_OSX_ARCHITECTURES "arm64;x86_64")

    # Statically linking glfw
    set(OLD_BUILD_SHARED_LIBS "${BUILD_SHARED_LIBS}")
    set(BUILD_SHARED_LIBS "OFF")
    set(GLFW_NATIVE_API 1)
    add_subdirectory(glfw)
    set(BUILD_SHARED_LIBS "${OLD_BUILD_SHARED_LIBS}")
    list(APPEND TARGET_LINK_LIBS glfw)

    # Fix _CVDisplayLinkRelease
    set(CMAKE_SHARED_LINK_FLAGS "${CMAKE_SHARED_LINK_FLAGS} -framework IOKit -framework CoreVideo -framework CoreFoundation")
    set(EXTRA_LIBS ${COCOA_LIBRARY} ${GLUT_LIBRARY} ${OpenGL_LIBRARY})
    list(APPEND TARGET_LINK_LIBS ${EXTRA_LIBS})
  else()
    find_package(glfw3 REQUIRED)
    pkg_search_module(GLFW REQUIRED glfw3)

    if (glfw3_FOUND)
      message(STATUS "GLFW_INCLUDE_DIRS=${GLFW_INCLUDE_DIRS}")
    endif ()
    set(TARGET_LINK_LIBS ${GLFW_STATIC_LIBRARIES})
  endif ()
endif ()

if (JNI_FOUND)
  message(STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
  message(STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
endif ()

add_definitions(-DCUSTOM_IMGUIFILEDIALOG_CONFIG="fd_customization.h")

include_directories(${JNI_INCLUDE_DIRS} project imgui javah impl config 3rdparty/fd 3rdparty/dtc)

file(GLOB IMGUI_FILES "imgui/*")
file(GLOB GENERATED_FILES "gen/*")
file(GLOB FILE_DIALOG_FILES "3rdparty/fd/*")
file(GLOB DATE_TIME_CHOOSER_FILES "3rdparty/dtc/*")

set(ALL_LIBRARIES
    imgui ${IMGUI_FILES} ${IMGUI_IMPL}
    javah gen ${GENERATED_FILES}
    3rdparty/fd ${FILE_DIALOG_FILES}
    3rdparty/dtc ${DATE_TIME_CHOOSER_FILES}
    project/fd_wrapper.cpp
    project/hand_written_bindings.cpp
    project/overloads_helper.cpp
    project/basics.hpp
    project/imgui_ext.cpp
    project/cpp_interop.cpp)

message(STATUS "CMAKE_GENERATOR_PLATFORM=${CMAKE_GENERATOR_PLATFORM}")
message(STATUS "TARGET_LINK_LIBS=${TARGET_LINK_LIBS}")
message(STATUS "TARGET_LINK_LIBS_DX9=${TARGET_LINK_LIBS_DX9}")
message(STATUS "TARGET_LINK_LIBS_DX11=${TARGET_LINK_LIBS_DX11}")

add_library(jimgui SHARED ${ALL_LIBRARIES})
target_link_libraries(jimgui ${TARGET_LINK_LIBS})
if (WIN32)
  add_library(jimgui-dx9 SHARED ${DX9_IMPL})
  add_library(jimgui-dx11 SHARED ${DX11_IMPL})
  target_link_libraries(jimgui-dx9 jimgui ${TARGET_LINK_LIBS_DX9})
  target_link_libraries(jimgui-dx11 jimgui ${TARGET_LINK_LIBS_DX11})
  target_compile_definitions(jimgui-dx11 PRIVATE _WIN32_WINNT=0x0601 WINVER=0x0601)
  target_compile_definitions(jimgui-dx9 PRIVATE _WIN32_WINNT=0x0502 WINVER=0x0502)
  # target_compile_definitions(jimgui PRIVATE _WIN32_WINNT=0x0502 WINVER=0x0502)
endif ()

# Detect Architecture
if(APPLE)
  message(STATUS "Target Platform: macos-(amd64|aarch64)")
elseif (WIN32)
  if (${CMAKE_GENERATOR_PLATFORM} MATCHES "x64")
    set(64BIT true)
  endif ()
  if ("${CMAKE_SIZEOF_VOID_P}" EQUAL "8")
    set(64BIT true)
  endif ()

  if (${64BIT})
    message(STATUS "Target Platform: windows-amd64")
  else()
    message(STATUS "Target Platform: windows-x86")
    set_target_properties(jimgui PROPERTIES OUTPUT_NAME "jimgui32")
    set_target_properties(jimgui-dx9 PROPERTIES OUTPUT_NAME "jimgui32-dx9")
    set_target_properties(jimgui-dx11 PROPERTIES OUTPUT_NAME "jimgui32-dx11")
  endif()

  # TODO: windows-aarch64
else()
  if (${CMAKE_HOST_SYSTEM_PROCESSOR} MATCHES "(x86_64|amd64|x64|ia32e|em64t)")
    message(STATUS "Target Platform: linux-amd64")
  elseif(${CMAKE_HOST_SYSTEM_PROCESSOR} MATCHES "(x86|i386|i486|i586|i686|ia32|x32)")
    message(STATUS "Target Platform: linux-x86")
    set_target_properties(jimgui PROPERTIES OUTPUT_NAME "jimgui32")
  elseif(${CMAKE_HOST_SYSTEM_PROCESSOR} MATCHES "(aarch64|aarch64_be|arm64|armv8|armv8b|armv9|armv9b)")
    message(STATUS "Target Platform: linux-aarch64")
    set_target_properties(jimgui PROPERTIES OUTPUT_NAME "jimgui-aarch64")
  elseif(${CMAKE_HOST_SYSTEM_PROCESSOR} MATCHES "(arm|arm32|armv7)")
    message(STATUS "Target Platform: linux-arm32")
    set_target_properties(jimgui PROPERTIES OUTPUT_NAME "jimgui-arm32")
  elseif(${CMAKE_HOST_SYSTEM_PROCESSOR} MATCHES "(loongarch64)")
    message(STATUS "Target Platform: linux-loongarch64")
    set_target_properties(jimgui PROPERTIES OUTPUT_NAME "jimgui-loongarch64")
  endif()
endif()
