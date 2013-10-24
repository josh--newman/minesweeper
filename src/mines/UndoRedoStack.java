package mines;

import java.util.Stack;

public class UndoRedoStack extends Stack<int[]> {
	private int[] lastUndo;
	private int[] nextRedo;
	
    public UndoRedoStack() {
    	
    }
    
	public int[] undo() {
		lastUndo = pop();
		return lastUndo;
	}
	
	public int[] redo() {
		push(lastUndo);
		return nextRedo;
	}
	
	public void setNextRedo(int[] arr) {
		nextRedo = arr;
	}
	
}