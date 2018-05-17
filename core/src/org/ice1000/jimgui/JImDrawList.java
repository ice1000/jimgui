package org.ice1000.jimgui;

import org.jetbrains.annotations.Contract;

public class JImDrawList extends JImGuiDrawListGen {
	/**
	 * package-private by design
	 *
	 * @param nativeObjectPtr native pointer ImDrawList *
	 */
	@Contract
	JImDrawList(long nativeObjectPtr) {
		super(nativeObjectPtr);
	}
}
