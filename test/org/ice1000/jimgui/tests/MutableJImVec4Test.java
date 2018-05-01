package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JniLoader;
import org.ice1000.jimgui.MutableJImVec4;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class MutableJImVec4Test {
	private static MutableJImVec4 vec;
	private static MutableJImVec4 vec2;

	@BeforeClass
	public static void loadJni() {
		JniLoader.jniLibraryPath = Paths.get("jni", "cmake-build-debug", "libjimgui.so").toAbsolutePath().toString();
		JniLoader.load();
		vec = new MutableJImVec4();
		vec2 = new MutableJImVec4(0, 0, 0, 0);
	}

	@Test
	public void setW() {
		assertEquals(0, vec.getW(), 0.0001f);
		float newValue = (float) Math.random();
		vec.setW(newValue);
		assertEquals(newValue, vec.getW(), 0.0001f);
		assertEquals(0, vec2.getW(), 0.0001f);
		vec2.setW(newValue);
		assertEquals(newValue, vec2.getW(), 0.0001f);
	}

	@Test
	public void getX() {
		assertEquals(0, vec.getX(), 0.0001f);
		float newValue = (float) Math.random();
		vec.setX(newValue);
		assertEquals(newValue, vec.getX(), 0.0001f);
		assertEquals(0, vec2.getX(), 0.0001f);
		vec2.setX(newValue);
		assertEquals(newValue, vec2.getX(), 0.0001f);
	}

	@Test
	public void getY() {
		assertEquals(0, vec.getY(), 0.0001f);
		float newValue = (float) Math.random();
		vec.setY(newValue);
		assertEquals(newValue, vec.getY(), 0.0001f);
	}

	@Test
	public void getZ() {
		assertEquals(0, vec.getZ(), 0.0001f);
		float newValue = (float) Math.random();
		vec.setZ(newValue);
		assertEquals(newValue, vec.getZ(), 0.0001f);
	}

	@AfterClass
	public static void deallocate() {
		vec.close();
		vec2.close();
	}
}