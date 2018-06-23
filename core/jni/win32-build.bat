@mkdir win32-build
@cd src
@set JDK_PATH=C:\Program Files\Java\jdk1.8.0_102
@set LD_OPTION=-lglfw3 -lgdi32 -lopengl32
@set INCLUDE_OPTION=-I . -I .\javah  -I .\impl -I .\imgui -I .\config -I "%JDK_PATH%\include" -I "%JDK_PATH%\include\win32"
@gcc -c %INCLUDE_OPTION% .\impl\gl3w.c -o ..\win32-build\gl3w.o
@g++ -std=c++11 -shared -static -fPIC -s -O2 %INCLUDE_OPTION% ..\win32-build\gl3w.o .\generated*.cpp .\hand_written_bindings.cpp .\overloads_helper.cpp .\cpp_interop.cpp .\glfw_impl.cpp .\imgui\*.cpp .\impl\*.cpp -o ..\win32-build\jimgui-glfw.dll %LD_OPTION%
@del /F /Q ..\win32-build\*.o