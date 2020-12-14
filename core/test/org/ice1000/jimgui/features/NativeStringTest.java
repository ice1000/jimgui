package org.ice1000.jimgui.features;

import org.ice1000.jimgui.NativeString;
import org.ice1000.jimgui.util.JniLoader;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.ice1000.jimgui.util.JImGuiUtil.EMPTY_BYTES;
import static org.junit.Assert.*;

public class NativeStringTest {
  @BeforeClass public static void setup() {
    JniLoader.load();
  }

  @Test public void sizeIs0() {
    NativeString string = new NativeString();
    assertEquals(0, string.length());
    assertArrayEquals(EMPTY_BYTES, string.toBytes());
    string.deallocateNativeObject();
  }

  @Test public void appendOneChar() {
    NativeString string = new NativeString();
    string.append('A');
    assertEquals(1, string.length());
    assertEquals('A', string.charAt(0));
    assertEquals("A", string.toString());
    string.deallocateNativeObject();
  }

  @Test public void modifyOneChar() {
    NativeString string = new NativeString();
    string.append('A');
    assertEquals(1, string.length());
    assertEquals('A', string.charAt(0));
    string.setCharAt(0, 'B');
    assertEquals('B', string.charAt(0));
    string.deallocateNativeObject();
  }

  @Test public void clearText() {
    NativeString string = new NativeString();
    string.append('A');
    assertEquals(1, string.length());
    assertEquals('A', string.charAt(0));
    string.clear();
    assertTrue(string.isEmpty());
    string.deallocateNativeObject();
  }

  @Test public void appendTwoChars() {
    NativeString string = new NativeString();
    string.append('X');
    string.append('Y');
    assertEquals(2, string.length());
    assertEquals('X', string.charAt(0));
    assertEquals('Y', string.charAt(1));
    assertEquals("XY", string.toString());
    NativeString subSequence01 = string.subSequence(0, 1);
    assertEquals("X", subSequence01.toString());
    NativeString subSequence12 = string.subSequence(1, 2);
    assertEquals("Y", subSequence12.toString());
    subSequence01.deallocateNativeObject();
    subSequence12.deallocateNativeObject();
    string.deallocateNativeObject();
  }
}
