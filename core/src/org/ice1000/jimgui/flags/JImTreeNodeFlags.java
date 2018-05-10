package org.ice1000.jimgui.flags;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImTreeNodeFlags {
	int Selected = 1 << 0;
	int Framed = 1 << 1;
	int AllowItemOverlap = 1 << 2;
	int NoTreePushOnOpen = 1 << 3;
	int NoAutoOpenOnLog = 1 << 4;
	int DefaultOpen = 1 << 5;
	int OpenOnDoubleClick = 1 << 6;
	int OpenOnArrow = 1 << 7;
	int Leaf = 1 << 8;
	int Bullet = 1 << 9;
	int FramePadding = 1 << 10;
	//  SpanAllAvailWidth = 1 << 11;
	//  NoScrollOnOpen = 1 << 12;
	int NavLeftJumpsBackHere = 1 << 13;
	int CollapsingHeader = Framed | NoAutoOpenOnLog;
}
