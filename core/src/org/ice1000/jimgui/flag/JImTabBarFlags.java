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
}
