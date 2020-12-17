package org.ice1000.jimgui.util;

/**
 * @author ice1000
 */
@SuppressWarnings("WeakerAccess")
public interface JniLoader {
  String OsName = System.getProperty("os.name");
  String ArchitectureName = System.getProperty("os.arch");
  boolean X86 = "x86".equals(ArchitectureName);
  boolean Linux = "Linux".equals(OsName);
  // I want to use osName.startsWith("Windows 9")...
  boolean Windows95 = "Windows 95".equals(OsName);
  boolean Windows98 = "Windows 98".equals(OsName);
  boolean Windows200X = OsName.startsWith("Windows 200");
  boolean WindowsXP = "Windows XP".equals(OsName);
  boolean WindowsVista = "Windows Vista".equals(OsName);
  boolean Windows7 = "Windows 7".equals(OsName) || OsName.startsWith("Windows Server 2008");
  boolean Windows8 = "Windows 8".equals(OsName) ||
      "Windows 8.1".equals(OsName) ||
      OsName.startsWith("Windows Server 2012");
  boolean Windows10 = "Windows 10".equals(OsName) ||
      OsName.startsWith("Windows Server 2019") ||
      OsName.startsWith("Windows Server 2016");
  boolean OSX = "Mac OS X".equals(OsName);
  static void load() {
    if (SharedState.isLoaded) return;
    // Supported OS
    if (Linux) loadLib(X86 ? "libjimgui32.so" : "libjimgui.so");
    else if (WindowsVista || WindowsXP) {
      loadLib(X86 ? "jimgui32.dll" : "jimgui.dll");
      loadLib(X86 ? "jimgui32-dx9.dll" : "jimgui-dx9.dll");
    } else if (Windows7 || Windows8 || Windows10) {
      loadLib(X86 ? "jimgui32.dll" : "jimgui.dll");
      loadLib(X86 ? "jimgui32-dx11.dll" : "jimgui-dx11.dll");
    } else if (OSX) loadLib("libjimgui.dylib");
      // Unsupported OS
    else if (Windows98 || Windows95 || Windows200X)
      throw new UnsupportedOperationException("Windows 98/95/2000/2003 are not supported and won't be supported.");
    else throw new UnsupportedOperationException("Unknown OS " +
          OsName +
          ", please submit issue to https://github.com/ice1000/jimgui/issues");
    SharedState.isLoaded = true;
  }
  static void loadLib(String libraryName) {
    NativeUtil.loadLibraryFromJar(libraryName, NativeUtil.class);
  }
}
