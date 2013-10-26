package mines;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
//import javax.swing.JMenuBar;

public class Mines extends JFrame {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
	private final int WIDTH = 800;
    private final int HEIGHT = 800;

    private JLabel statusbar;
    private JLabel timeStatus;
    private Board board;
    private String difficulty = "easy";
    
    public Mines() {
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Minesweeper");
        
        setJMenuBar(Menu.create());
        
        statusbar = new JLabel("");
        statusbar.setBorder(new EmptyBorder(5, 5, 5, 5));
        statusbar.setBackground(Color.blue);
        timeStatus = new JLabel("");
        timeStatus.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(statusbar, BorderLayout.SOUTH);
        add(timeStatus, BorderLayout.NORTH);
        
        board = new Board(statusbar, timeStatus, difficulty);
        System.out.println("Board size: " + board.getSize());
        add(board);
        setPreferredSize(board.getSize());
        pack();
        setResizable(false);
        setVisible(true);
    }
    
    public void newMineGame() {
    	board.getTimer().stop();
    	remove(board);
    	board = new Board(statusbar, timeStatus, difficulty);
    	add(board);
    	setPreferredSize(board.getSize());
    	validate();
    	repaint();
    	pack();
    }
    
    public void newMineGame(int[] field, int numMines) {
    	board.getTimer().stop();
    	remove(board);
    	board = new Board(statusbar, timeStatus, field, numMines);
    	add(board);
    	setPreferredSize(board.getSize());
    	validate();
    	repaint();
    	pack();
    }
    
    public Board getBoard() {
    	return board;
    }
    
    public String getDifficulty() {
    	return difficulty;
    }
    
    public void setDifficulty(String difficulty) {
    	this.difficulty = difficulty;
    }
    
    public static void main(String[] args) {
        CurrentGame.setCurrentGame(new Mines());
    }
}
