package mines;

import java.util.Stack;

public class UndoRedo {
	private static int[] lastUndo;
    public static Stack<int[]> undoStack = new Stack<int[]>();
	
	public static int[] undo() {
		lastUndo = undoStack.pop();
		return lastUndo;
	}
	
	public static int[] redo() {
		undoStack.push(lastUndo);
		return lastUndo;
	}
}