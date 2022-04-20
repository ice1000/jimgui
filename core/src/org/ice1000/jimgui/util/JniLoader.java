package org.ice1000.jimgui.util;

import java.util.Locale;

/**
 * @author ice1000
 */
@SuppressWarnings("WeakerAccess")
public interface JniLoader {
  String OsName = System.getProperty("os.name");
  String ArchitectureName = System.getProperty("os.arch");
  boolean X86 = "x86".equals(ArchitectureName);
  static void load() {
    if (SharedState.isLoaded) return;

    if (OS.Current == OS.Unknown) {
      throw new UnsupportedOperationException("Unknown operating system '" +
          System.getProperty("os.name") +
          "', please submit issue to https://github.com/ice1000/jimgui/issues");
    }
    if (Arch.Current == Arch.Unknown) {
      throw new UnsupportedOperationException("Unknown CPU architecture '" +
          System.getProperty("os.arch") +
          "', please submit issue to https://github.com/ice1000/jimgui/issues");
    }

    if (OS.Current == OS.Linux) {
      switch (Arch.Current) {
        case X86:
          loadLib("libjimgui32.so");
          break;
        case X86_64:
          loadLib("libjimgui.so");
          break;
        default:
          throw new UnsupportedOperationException("Unsupported platform 'linux-" +
              System.getProperty("os.arch") +
              "', please submit issue to https://github.com/ice1000/jimgui/issues");
      }
    } else if (OS.Current == OS.Windows) {
      String osName = System.getProperty("os.name", "");
      if ("Windows Vista".equals(osName) || "Windows XP".equals(osName)) { // Windows Vista or Windows XP
        loadDX9();
      } else if ("Windows 7".equals(osName) ||
          osName.startsWith("Windows Server 2008") ||
          "Windows 8".equals(osName) ||
          "Windows 8.1".equals(osName) ||
          osName.startsWith("Windows Server 2012") ||
          "Windows 10".equals(osName) ||
          osName.startsWith("Windows Server 2019") ||
          osName.startsWith("Windows Server 2016")) { // Windows 7 or Latter
        loadDX11();
      } else if ("Windows 95".equals(osName) || "Windows 98".equals(osName) || osName.startsWith("Windows 200")) {
        // Unsupported
        throw new UnsupportedOperationException("Windows 98/95/2000/2003 are not supported and won't be supported.");
      }
    } else if (OS.Current == OS.MacOS) {
      switch (Arch.Current) {
        case X86_64:
        case AArch64:
          loadLib("libjimgui.dylib");
          break;
        default:
          throw new UnsupportedOperationException("Unsupported platform 'macos-" +
              System.getProperty("os.arch") +
              "', please submit issue to https://github.com/ice1000/jimgui/issues");
      }
    }
    SharedState.isLoaded = true;
  }
  static void loadDX11() {
    switch (Arch.Current) {
      case X86:
        loadLib("jimgui32.dll");
        loadLib("jimgui32-dx11.dll");
        break;
      case X86_64:
        loadLib("jimgui.dll");
        loadLib("jimgui-dx11.dll");
        break;
      default:
        throw new UnsupportedOperationException();
    }
  }
  static void loadDX9() {
    switch (Arch.Current) {
      case X86:
        loadLib("jimgui32.dll");
        loadLib("jimgui32-dx9.dll");
        break;
      case X86_64:
        loadLib("jimgui.dll");
        loadLib("jimgui-dx9.dll");
        break;
      default:
        throw new UnsupportedOperationException();
    }
  }
  static void loadLib(String libraryName) {
    NativeUtil.loadLibraryFromJar(libraryName, NativeUtil.class);
  }
  enum OS {
    Windows, Linux, MacOS, Unknown;
    public static final OS Current = detectOS();
    static OS detectOS() {
      String osName = System.getProperty("os.name", "").toLowerCase(Locale.ROOT).trim();
      String jvmName = System.getProperty("java.vm.name", "").toLowerCase(Locale.ROOT).trim();

      if (osName.startsWith("windows")) {
        return Windows;
      }
      if (osName.startsWith("mac")) {
        return MacOS;
      }

      if (osName.startsWith("linux") || osName.equals("gnu")) {
        if (jvmName.equals("dalvik")) {
          // return "android";
          return null;
        }
        return Linux;
      }

      return Unknown;
    }
  }

  enum Arch {
    X86, X86_64, ARM32, AArch64, LoongArch64, Unknown;
    public static final Arch Current = detectArch();
    private static Arch detectArch() {
      String arch = System.getProperty("os.arch", "").toLowerCase(Locale.ROOT).trim();
      switch (arch) {
        case "x86":
        case "i386":
        case "i486":
        case "i586":
        case "i686":
          return X86;
        case "x64":
        case "x86-64":
        case "x86_64":
        case "amd64":
          return X86_64;
        case "loongarch64":
          return LoongArch64;
      }

      if (arch.startsWith("aarch64") || arch.startsWith("armv8") || arch.startsWith("arm64")) {
        return AArch64;
      } else if (arch.startsWith("arm")) {
        return ARM32;
      }

      return Unknown;
    }
  }
}
