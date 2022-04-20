package org.ice1000.jimgui.util;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
          OsName +
          "', please submit issue to https://github.com/ice1000/jimgui/issues");
    }
    if (Arch.Current == Arch.Unknown) {
      throw new UnsupportedOperationException("Unknown CPU architecture '" +
          ArchitectureName +
          "', please submit issue to https://github.com/ice1000/jimgui/issues");
    }

    if (OS.Current == OS.Linux) {
      if (Arch.Current == Arch.X86_64) {
        loadLib("libjimgui.so");
      } else if (Arch.Current == Arch.X86) {
        loadLib("libjimgui32.so");
      } else {
        try {
          loadLib("libjimgui-" + Arch.Current.name().toLowerCase(Locale.ROOT) + ".so");
        } catch (UnsupportedOperationException e) {
          throw new UnsupportedOperationException("Unsupported platform 'linux-" +
              Arch.Current.name().toLowerCase(Locale.ROOT) +
              "', please submit issue to https://github.com/ice1000/jimgui/issues", e);
        }
      }
    } else if (OS.Current == OS.Windows) {
      if ("Windows 95".equals(OsName) || "Windows 98".equals(OsName) || OsName.startsWith("Windows 200")) {
        // Unsupported
        throw new UnsupportedOperationException("Windows 98/95/2000/2003 are not supported and won't be supported.");
      }

      boolean loadDX11 = false;

      if ("Windows 7".equals(OsName) ||
          OsName.startsWith("Windows Server 2008") ||
          "Windows 8".equals(OsName) ||
          "Windows 8.1".equals(OsName) ||
          OsName.startsWith("Windows Server 2012")||
          "Windows 10".equals(OsName) ||
          OsName.startsWith("Windows Server 2016") ||
          OsName.startsWith("Windows Server 2019") ||
          "Windows 11".equals(OsName) ||
          OsName.startsWith("Windows Server 2022")) {
        loadDX11 = true;
      } else {
        String osVersion = System.getProperty("os.version", "");

        Matcher matcher = Pattern.compile("^(?<major>\\d+)(\\.\\d+)*$").matcher(osVersion);
        if (matcher.matches()) {
          int major = Integer.parseInt(matcher.group("major"));
          if (major > 6) {
            loadDX11 = true;
          }
        }
      }

      if (Arch.Current == Arch.X86_64) {
        loadLib("jimgui.dll");
        if (loadDX11) {
          loadLib("jimgui-dx11.dll");
        } else {
          loadLib("jimgui-dx9.dll");
        }
      } else if (Arch.Current == Arch.X86) {
        loadLib("jimgui32.dll");
        if (loadDX11) {
          loadLib("jimgui32-dx11.dll");
        } else {
          loadLib("jimgui32-dx9.dll");
        }
      } else {
        try {
          String base = "jimgui-" + Arch.Current.name().toLowerCase(Locale.ROOT);
          loadLib(base + ".dll");
          if (loadDX11) {
            loadLib(base + "-dx11.dll");
          } else {
            loadLib(base + "-dx9.dll");
          }
        } catch (UnsupportedOperationException e) {
          throw new UnsupportedOperationException("Unsupported platform 'windows-" +
              Arch.Current.name().toLowerCase(Locale.ROOT) +
              "', please submit issue to https://github.com/ice1000/jimgui/issues", e);
        }
      }

    } else if (OS.Current == OS.MacOS) {
      if (Arch.Current == Arch.X86_64 || Arch.Current == Arch.AArch64) {
        loadLib("libjimgui.dylib");
      } else {
        try {
          loadLib("libjimgui-" + Arch.Current.name().toLowerCase(Locale.ROOT) + ".dylib");
        } catch (UnsupportedOperationException e) {
          throw new UnsupportedOperationException("Unsupported platform 'macos-" +
              Arch.Current.name().toLowerCase(Locale.ROOT) +
              "', please submit issue to https://github.com/ice1000/jimgui/issues", e);
        }
      }
    } else {
      try {
        String libraryName;

        if (Arch.Current == Arch.X86_64) {
          libraryName = "jimgui";
        } else if (Arch.Current == Arch.X86) {
          libraryName = "jimgui32";
        } else {
          libraryName = "jimgui-" + Arch.Current.name().toLowerCase(Locale.ROOT);
        }

        loadLib(System.mapLibraryName(libraryName + "-" + OS.Current.name().toLowerCase(Locale.ROOT)));
      } catch (UnsupportedOperationException e) {
        throw new UnsupportedOperationException("Unsupported platform '"
            + OS.Current.name().toLowerCase(Locale.ROOT) + "-" + Arch.Current.name().toLowerCase(Locale.ROOT) +
            "', please submit issue to https://github.com/ice1000/jimgui/issues", e);
      }
    }

    SharedState.isLoaded = true;
  }

  static void loadLib(String libraryName) {
    NativeUtil.loadLibraryFromJar(libraryName, NativeUtil.class);
  }
  enum OS {
    Windows, Linux, MacOS, Android, FreeBSD, DARWIN, IOS, AIX, SOLARIS, Unknown;
    public static final OS Current = detectOS();
    static OS detectOS() {
      String osName = OsName.toLowerCase(Locale.ROOT).trim();
      String jvmName = System.getProperty("java.vm.name", "").toLowerCase(Locale.ROOT).trim();

      if (osName.startsWith("win")) {
        return Windows;
      }
      if (osName.startsWith("mac")) {
        return MacOS;
      }
      if (osName.startsWith("darwin")) {
        if ("robovm".equals(jvmName)) {
          return IOS;
        }
        return DARWIN;
      }

      if (osName.startsWith("linux") || osName.equals("gnu")) {
        if ("dalvik".equals(jvmName)) {
          return Android;
        }
        return Linux;
      }

      if (osName.startsWith("aix")) {
        return AIX;
      }
      if (osName.startsWith("solaris") || osName.startsWith("sunos")) {
        return SOLARIS;
      }
      if (osName.startsWith("freebsd")) {
        return FreeBSD;
      }

      return Unknown;
    }
  }

  enum Arch {
    X86, X86_64,
    IA64,
    ARM32, AArch64,
    PPC, PPCLE, PPC64, PPC64LE,
    S390, S390X,
    SPARC, SPARCV9,
    MIPS, MIPS64, MIPSEL, MIPS64EL,
    RISCV,
    LoongArch64,
    Unknown;
    public static final Arch Current = detectArch();
    private static Arch detectArch() {
      String arch = ArchitectureName.toLowerCase(Locale.ROOT).trim();

      switch (arch) {
        case "x86":
        case "x86-32":
        case "x86_32":
        case "x8632":
        case "i86pc":
        case "i386":
        case "i486":
        case "i586":
        case "i686":
        case "ia32":
        case "x32":
          return X86;
        case "x8664":
        case "x86-64":
        case "x86_64":
        case "amd64":
        case "x64":
        case "ia32e":
        case "em64t":
          return X86_64;
        case "arm":
        case "arm32":
        case "aarch32":
          return ARM32;
        case "arm64":
        case "aarch64":
          return AArch64;
        case "ppc":
        case "powerpc":
        case "ppc32":
        case "powerpc32":
          return PPC;
        case "ppcle":
        case "powerpcle":
        case "ppc32le":
        case "powerpc32le":
          return PPCLE;
        case "ppc64":
        case "powerpc64":
          return PPC64;
        case "ppc64le":
        case "powerpc64le":
          return PPC64LE;
        case "s390":
          return S390;
        case "s390x":
          return S390X;
        case "sparc":
          return SPARC;
        case "sparcv9":
          return SPARCV9;
        case "mips":
        case "mips32":
          return MIPS;
        case "mipsel":
        case "mips32el":
          return MIPSEL;
        case "mips64":
          return MIPS64;
        case "mips64el":
          return MIPS64EL;
        case "loongarch64":
          return LoongArch64;
      }

      if (arch.startsWith("armv7")) {
        return ARM32;
      }
      if (arch.startsWith("armv8") || arch.startsWith("armv9")) {
        return AArch64;
      }

      return Unknown;
    }
  }
}
