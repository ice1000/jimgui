mkdir win32-build
JAVA_HOME=${JAVA_HOME//\\/\/}
JAVA_HOME=${JAVA_HOME/C:/\/c}
LD_OPTION="-lglfw3 -lgdi32 -lopengl32"
COMPILE_OPTION="-std=c++11 -shared -static -fPIC -s -O2"
SOURCE="./win32-build/gl3w.o ./generated*.cpp ./hand_written_bindings.cpp ./overloads_helper.cpp ./cpp_interop.cpp ./glfw_impl.cpp ./imgui/*.cpp ./impl/*.cpp"
INCLUDE_OPTION="-I . -I ./javah -I win32-build  -I ./impl -I ./imgui -I ./config -I \"$JAVA_HOME/include\" -I \"$JAVA_HOME/include/win32\" -I /usr/include"
echo LD_OPTION=$LD_OPTION
echo COMPILE_OPTION=$COMPILE_OPTION
echo SOURCE=$SOURCE
echo INCLUDE_OPTION=$INCLUDE_OPTION
gcc -c $INCLUDE_OPTION ./impl/gl3w.c -o ./win32-build/gl3w.o
g++ $COMPILE_OPTION $INCLUDE_OPTION $SOURCE -o ./win32-build/jimgui-glfw.dll $LD_OPTION
g++ $COMPILE_OPTION -m32 $INCLUDE_OPTION $SOURCE -o ./win32-build/jimgui32-glfw.dll $LD_OPTION
