package org.ice1000.jimgui.features;

import org.ice1000.jimgui.NativeString;
import org.ice1000.jimgui.util.JniLoader;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.ice1000.jimgui.util.JImGuiUtil.EMPTY_BYTES;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

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
}
