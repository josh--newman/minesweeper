package mines;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
//import javax.swing.JMenuBar;

public class Mines extends JFrame {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private JLabel statusbar;
    private JLabel timeStatus;
    private JLabel chances;
    private JPanel status;
    private Board board;
    private String difficulty = "easy";
    
    public Mines() {
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Minesweeper");
        
        setJMenuBar(Menu.create());
        
        status = new JPanel();
        status.setBackground(new Color(99,132,194));
        status.setBorder(BorderFactory.createCompoundBorder());
        
        
        statusbar = new JLabel("");
        statusbar.setBorder(new EmptyBorder(5, 5, 5, 5));
        statusbar.setForeground(Color.white);
        timeStatus = new JLabel("");
        timeStatus.setBorder(new EmptyBorder(5, 5, 5, 5));
        timeStatus.setForeground(Color.white);
        chances = new JLabel("");
        chances.setBorder(new EmptyBorder(5, 5, 5, 5));
        chances.setForeground(Color.white);
        
        status.add(statusbar);
        status.add(timeStatus);
        status.add(chances);
        
        add(status, BorderLayout.SOUTH);
        
        board = new Board(statusbar, timeStatus, chances, difficulty);
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
    	board = new Board(statusbar, timeStatus, chances, difficulty);
    	add(board);
    	setPreferredSize(board.getSize());
    	validate();
    	repaint();
    	pack();
    }
    
    public void newMineGame(int[] field, int numMines) {
    	board.getTimer().stop();
    	remove(board);
    	board = new Board(statusbar, timeStatus, chances, field, numMines);
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
