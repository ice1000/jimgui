package org.ice1000.jimgui.flag;

/**
 * Flags for ImGui::BeginTabBar()
 */
public interface JImTabBarFlags {
	int None = 0;
	/** Allow manually dragging tabs to re-order them + New tabs are appended at the end of list */
	int Reorderable = 1;
	/** Automatically select new tabs when they appear */
	int AutoSelectNewTabs = 1 << 1;
	/** Disable buttons to open the tab list popup */
	int TabListPopupButton = 1 << 2;
	/** Disable behavior of closing tabs (that are submitted with p_open != NULL) with middle mouse button. You can still repro this behavior on user's side with if (IsItemHovered() && IsMouseClicked(2)) *p_open = false. */
	int NoCloseWithMiddleMouseButton = 1 << 3;
	/** Disable scrolling buttons (apply when fitting policy is ImGuiTabBarFlags_FittingPolicyScroll) */
	int NoTabListScrollingButtons = 1 << 4;
	/** Disable tooltips when hovering a tab */
	int NoTooltip = 1 << 5;
	/** Resize tabs when they don't fit */
	int FittingPolicyResizeDown = 1 << 6;
	/** Add scroll buttons when tabs don't fit */
	int FittingPolicyScroll = 1 << 7;
	int FittingPolicyMask = FittingPolicyResizeDown | FittingPolicyScroll;
	int FittingPolicyDefault = FittingPolicyResizeDown;

	enum Type implements Flag {
		/**
		 * Used for reverse lookup results and enum comparison.
		 * Return the Nothing or Default flag to prevent errors.
		 */
		NoSuchFlag(JImTabBarFlags.None),
		None(JImTabBarFlags.None),
		/** @see JImTabBarFlags#Reorderable */
		Reorderable(JImTabBarFlags.Reorderable),
		/** @see JImTabBarFlags#AutoSelectNewTabs */
		AutoSelectNewTabs(JImTabBarFlags.AutoSelectNewTabs),
		/** @see JImTabBarFlags#TabListPopupButton */
		TabListPopupButton(JImTabBarFlags.TabListPopupButton),
		/** @see JImTabBarFlags#NoCloseWithMiddleMouseButton */
		NoCloseWithMiddleMouseButton(JImTabBarFlags.NoCloseWithMiddleMouseButton),
		/** @see JImTabBarFlags#NoTabListScrollingButtons */
		NoTabListScrollingButtons(JImTabBarFlags.NoTabListScrollingButtons),
		/** @see JImTabBarFlags#NoTooltip */
		NoTooltip(JImTabBarFlags.NoTooltip),
		/** @see JImTabBarFlags#FittingPolicyResizeDown */
		FittingPolicyResizeDown(JImTabBarFlags.FittingPolicyResizeDown),
		/** @see JImTabBarFlags#FittingPolicyScroll */
		FittingPolicyScroll(JImTabBarFlags.FittingPolicyScroll),
		/** @see JImTabBarFlags#FittingPolicyMask */
		FittingPolicyMask(JImTabBarFlags.FittingPolicyMask),
		/** @see JImTabBarFlags#FittingPolicyDefault */
		FittingPolicyDefault(JImTabBarFlags.FittingPolicyDefault);

		public final int flag;

		Type(int flag) {
			this.flag = flag;
		}

		@Override
		public int get() {
			return flag;
		}
	}
}
