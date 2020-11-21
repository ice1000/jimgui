package org.ice1000.jimgui.flag;

/**
 * Flags for ImGui::BeginTabItem()
 */
public interface JImTabItemFlags {
    int None = 0;
    /** Append '*' to title without affecting the ID, as a convenience to avoid using the ### operator. Also: tab is selected on closure and closure is deferred by one frame to allow code to undo it without flicker. */
    int UnsavedDocument = 1;
    /** Trigger flag to programmatically make the tab selected when calling BeginTabItem() */
    int SetSelected = 1 << 1;
    /** Disable behavior of closing tabs (that are submitted with p_open != NULL) with middle mouse button. You can still repro this behavior on user's side with if (IsItemHovered() && IsMouseClicked(2)) *p_open = false. */
    int NoCloseWithMiddleMouseButton = 1 << 2;
    /** Don't call PushID(tab->ID)/PopID() on BeginTabItem()/EndTabItem() */
    int NoPushId = 1 << 3;
    /** Disable tooltip for the given tab */
    int NoTooltip = 1 << 4;
    /** Disable reordering this tab or having another tab cross over this tab */
    int NoReorder = 1 << 5;
    /** Enforce the tab position to the left of the tab bar (after the tab list popup button) */
    int Leading = 1 << 6;
    /** Enforce the tab position to the right of the tab bar (before the scrolling buttons) */
    int Trailing = 1 << 7;

    enum Type implements Flag {
        /**
         * Used for reverse lookup results and enum comparison.
         * Return the Nothing or Default flag to prevent errors.
         */
        NoSuchFlag(JImTabItemFlags.None),
        None(JImTabItemFlags.None),
        /** @see JImTabItemFlags#UnsavedDocument */
        UnsavedDocument(JImTabItemFlags.UnsavedDocument),
        /** @see JImTabItemFlags#SetSelected */
        SetSelected(JImTabItemFlags.SetSelected),
        /** @see JImTabItemFlags#NoCloseWithMiddleMouseButton */
        NoCloseWithMiddleMouseButton(JImTabItemFlags.NoCloseWithMiddleMouseButton),
        /** @see JImTabItemFlags#NoPushId */
        NoPushId(JImTabItemFlags.NoPushId),
        /** @see JImTabItemFlags#NoTooltip */
        NoTooltip(JImTabItemFlags.NoTooltip),
        NoReorder(JImTabItemFlags.NoReorder),
        Leading(JImTabItemFlags.Leading),
        Trailing(JImTabItemFlags.Trailing);

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
