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
}
