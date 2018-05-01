package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.util.JImGuiUtil;

public class Sandbox {
	public static void main(String[] args) {
		JImGuiUtil.run(imGui -> {
			imGui.button("Hey!");
			imGui.text("Hey!");
		});
	}
}
