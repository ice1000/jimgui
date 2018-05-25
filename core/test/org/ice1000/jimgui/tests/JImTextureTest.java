package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImTextureID;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.ice1000.jimgui.tests.JImGuiTest.useAlternativeJniLibAndCheckHeadless;

public class JImTextureTest {
	@BeforeClass
	public static void setup() {
		useAlternativeJniLibAndCheckHeadless();
	}

	@Test
	public void testSandbox() throws InterruptedException {
		main();
	}

	public static void main(String @NotNull ... args) throws InterruptedException {
		JniLoader.load();
		try (JImGui imGui = new JImGui()) {
			long latestRefresh = System.currentTimeMillis();
			long end = System.currentTimeMillis() + (long) 3000;
			JImTextureID texture = JImTextureID.fromPNG(JImTextureTest.class.getResource("/pics/ice1000.png"));
			while (!imGui.windowShouldClose() && System.currentTimeMillis() < end) {
				long currentTimeMillis = System.currentTimeMillis();
				long deltaTime = currentTimeMillis - latestRefresh;
				Thread.sleep(deltaTime * 2 / 3);
				if (deltaTime > (long) 16) {
					imGui.initNewFrame();
					if (end - System.currentTimeMillis() > 1500)
						imGui.image(texture, texture.width, texture.height);
					else imGui.image(texture, imGui.getWindowWidth(), imGui.getWindowHeight());
					imGui.render();
					latestRefresh = currentTimeMillis;
				}
			}
		}
	}
}
