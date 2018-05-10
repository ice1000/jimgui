package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JImDir;
import org.ice1000.jimgui.JImVec4;
import org.ice1000.jimgui.MutableJImVec4;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Sandbox {
	public static void main(@NotNull String... args) {
		JniLoader.load();
		AtomicInteger count = new AtomicInteger();
		AtomicReference<String> ini = new AtomicReference<>("");
		JImGuiUtil.runPer(1, imGui -> {
			imGui.getFont().setFallbackChar('*');
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
			if (imGui.button("Click me!")) count.getAndIncrement();
			imGui.setBackground(JImVec4.fromAWT(java.awt.Color.BLUE));
			imGui.sameLine();
			imGui.text("Click count: " + count);
			imGui.bulletText("fps: " + imGui.getIO().getFramerate());
			imGui.text("Boy\u2642next\u26a8door\n就是邻\u26a2家男\u26a3孩");
			imGui.textWrapped("Boy\u2642next\u26a8door deep dark fantasy oh yes sir billy harrington van darkholm");
			imGui.textDisabled("Boy\u2642next\u26a8door\n就是邻\u26a2家男\u26a3孩");
			imGui.labelText("Boy\u2642next\u26a8door", "就是邻\u26a2家男\u26a3孩");
			imGui.smallButton("Boy\u2642next\u26a8door\n就是邻\u26a2家男\u26a3孩");
			imGui.newLine();
			imGui.newLine();
			if (imGui.getIO().isWantSaveIniSettings()) {
				ini.set(imGui.saveIniSettingsToMemory());
				imGui.getIO().setWantSaveIniSettings(false);
			}
			imGui.text(ini.get());
			imGui.newLine();
			try (MutableJImVec4 red = JImVec4.fromAWT(java.awt.Color.RED);
			     MutableJImVec4 yellow = JImVec4.fromAWT(java.awt.Color.YELLOW);
			     MutableJImVec4 green = JImVec4.fromJFX(javafx.scene.paint.Color.GREEN)) {
				imGui.textColored(red, "Woa!");
				imGui.separator();
				imGui.textColored(green, "Woa!");
				imGui.spacing();
				imGui.bullet();
				imGui.textColored(yellow, "Woa!");
				imGui.arrowButton("Woa!", JImDir.Down);
			}
		});
	}
}
