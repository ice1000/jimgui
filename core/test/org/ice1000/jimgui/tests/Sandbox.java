package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.*;
import org.ice1000.jimgui.cpp.DeallocatableObjectManager;
import org.ice1000.jimgui.flag.JImDirection;
import org.ice1000.jimgui.flag.JImFontAtlasFlags;
import org.ice1000.jimgui.flag.JImMouseIndexes;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.ice1000.jimgui.tests.JImGuiTest.useAlternativeJniLibAndCheckHeadless;
import static org.junit.Assert.assertNotNull;

public class Sandbox {
	private static String ini = "";
	@BeforeClass
	public static void setup() {
		useAlternativeJniLibAndCheckHeadless();
	}

	@Test
	public void testSandbox() {
		main();
	}

	@SuppressWarnings("AccessStaticViaInstance")
	public static void main(@NotNull String @NotNull ... args) {
		JniLoader.load();
		AtomicInteger count = new AtomicInteger();
		NativeFloat aFloat = new NativeFloat();
		NativeFloat aFloat2 = new NativeFloat();
		NativeInt anInt = new NativeInt();
		NativeInt anInt2 = new NativeInt();
		long start = System.currentTimeMillis();
		DeallocatableObjectManager manager = new DeallocatableObjectManager();
		manager.add(aFloat);
		manager.add(aFloat2);
		manager.add(anInt);
		manager.add(anInt2);
		System.out.println(aFloat.accessValue());
		System.out.println(anInt.accessValue());
		aFloat.modifyValue(666);
		System.out.println(aFloat.accessValue());
		System.out.println(anInt.accessValue());
		anInt.modifyValue(666);
		System.out.println(aFloat.accessValue());
		System.out.println(anInt.accessValue());
		byte[] buffer = new byte[500];
		JImGuiUtil.runWithinPer(9000, 10, imGui -> {
			JImFont font = imGui.getFont();
			font.setFallbackChar('*');
			if (imGui.inputText("WTF", buffer)) ;
			if (imGui.beginMainMenuBar()) {
				if (imGui.beginMenu("Main", true)) {
					imGui.menuItem("Copy", "Ctrl+C");
					imGui.menuItem("Paste", "Ctrl+V");
					imGui.menuItem("Open");
					imGui.endMenu();
				}
				if (imGui.beginMenu("Styles")) {
					if (imGui.menuItem("Dark")) imGui.styleColorsDark();
					if (imGui.menuItem("Classic")) imGui.styleColorsClassic();
					if (imGui.menuItem("Light")) imGui.styleColorsLight();
					imGui.endMenu();
				}
				imGui.endMainMenuBar();
			}
			imGui.dragFloat("Wtf", aFloat);
			imGui.text("Float = " + aFloat.accessValue());
			imGui.dragFloatRange2("Wtf", aFloat, aFloat2);
			imGui.text("Float2 = " + aFloat2.accessValue());
			imGui.dragInt("Wtf", anInt);
			imGui.text("Int = " + anInt.accessValue());
			imGui.dragIntRange2("Wtf", anInt, anInt2);
			imGui.text("Int2 = " + anInt2.accessValue());
			float bizarreValue = (System.currentTimeMillis() - start) / 2000f;
			imGui.getStyle().setWindowBorderSize(bizarreValue);
			JImVec4 background = imGui.getBackground();
			assertNotNull(background);
			// System.out.print(background);
			imGui.colorEdit3("Background", background);
			// System.out.println(background);
			// use `getIO` in production environment
			JImGuiIO io = imGui.findIO();
			assertNotNull(io);
			imGui.text("framerate: " + io.getFramerate());
			if (io.isKeyCtrl()) {
				imGui.text("[Ctrl]");
				imGui.sameLine();
			}
			if (io.isKeyAlt()) {
				imGui.text("[Alt]");
				imGui.sameLine();
			}
			if (io.isKeySuper()) {
				imGui.text("[Super]");
				imGui.sameLine();
			}
			if (io.isKeyShift()) {
				imGui.text("[Shift]");
				imGui.sameLine();
			}
			imGui.plotHistogram("Wtf", new float[]{1, 2, 3, 4}, "What?");
			imGui.newLine();
			font.setFontSize(bizarreValue + 13);
			JImFontAtlas containerAtlas = font.getContainerAtlas();
			containerAtlas.setFlags(containerAtlas.getFlags() | JImFontAtlasFlags.NoMouseCursors);
			imGui.text(String.valueOf(font.getFontSize()));
			imGui.text(font.getDebugName());
			String inputString = io.getInputString();
			imGui.text("Input characters (len: " + inputString.length() + "): " + inputString);
			if (io.mouseClickedAt(JImMouseIndexes.Left)) imGui.text("Left is down.");
			if (io.mouseClickedAt(JImMouseIndexes.Right)) imGui.text("Right is down.");
			imGui.text("MousePos: [" + io.getMousePosX() + ", " + io.getMousePosY() + "]");
			imGui.text("MouseDelta: [" + io.getMouseDeltaX() + ", " + io.getMouseDeltaY() + "]");
			if (imGui.button("Click me!")) count.getAndIncrement();
			imGui.sameLine();
			imGui.text("Click count: " + count);
			imGui.bulletText("fps: " + io.getFramerate());
			imGui.text("Boy\u2642next\u26a8door\n就是邻\u26a2家男\u26a3孩");
			imGui.textWrapped("Boy\u2642next\u26a8door deep dark fantasy oh yes sir billy harrington van darkholm");
			imGui.textDisabled("Boy\u2642next\u26a8door\n就是邻\u26a2家男\u26a3孩");
			imGui.labelText("Boy\u2642next\u26a8door", "就是邻\u26a2家男\u26a3孩");
			imGui.smallButton("Boy\u2642next\u26a8door\n就是邻\u26a2家男\u26a3孩");
			imGui.newLine();
			imGui.newLine();
			if (io.isWantSaveIniSettings()) {
				ini = imGui.saveIniSettingsToMemory();
				io.setWantSaveIniSettings(false);
			}
			imGui.text(ini);
			imGui.newLine();
			try (MutableJImVec4 red = JImVec4.fromAWT(java.awt.Color.RED);
			     MutableJImVec4 yellow = JImVec4.fromAWT(java.awt.Color.YELLOW);
			     MutableJImVec4 green = JImVec4.fromJFX(javafx.scene.paint.Color.GREEN)) {
				imGui.textColored(red, "Woa!");
				imGui.textColored(imGui.getStyle().getColor(JImStyleColors.TextSelectedBg), "Woa!");
				imGui.separator();
				imGui.textColored(green, "Woa!");
				imGui.spacing();
				imGui.bullet();
				imGui.textColored(yellow, "Woa!");
				imGui.arrowButton("Woa!", JImDirection.Down);
			}
		});
		manager.deallocateAll();
	}
}
