package org.ice1000.jimgui.flag;

/**
 * Flags for ImGui::BeginTabItem()
 */
public interface JImTabItemFlags {
    int None = 0;
    int UnsavedDocument = 1;
    int SetSelected = 1 << 1;
    int NoCloseWithMiddleMouseButton = 1 << 2;
    int NoPushId = 1 << 3;

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
        NoPushId(JImTabItemFlags.NoPushId);

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
