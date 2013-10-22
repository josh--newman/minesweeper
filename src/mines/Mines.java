package mines;

import java.awt.BorderLayout;


import javax.swing.JFrame;
import javax.swing.JLabel;
//import javax.swing.JMenuBar;

public class Mines extends JFrame {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
	private final int WIDTH = 800;
    private final int HEIGHT = 800;

    private JLabel statusbar;
    private Board board;
    
    public Mines(String diff) {
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Minesweeper");
        
        setJMenuBar(Menu.create());
        
        statusbar = new JLabel("");
        add(statusbar, BorderLayout.SOUTH);
        
        board = new Board(statusbar, diff);
        System.out.println("Board size: " + board.getSize());
        add(board);
        setSize(board.getSize());
        
        setResizable(false);
        setVisible(true);
    }
    
    public Board getBoard() {
    	return board;
    }
    
    public static void main(String[] args) {
        new Mines("easy");
    }
}
