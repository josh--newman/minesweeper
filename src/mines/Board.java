
package mines;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.Time;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
	private final int NUM_IMAGES = 14;
    private final int CELL_SIZE = 15;
    private final double PERCENTAGE_OF_MINES = 0.15;
    private final double PERCENTAGE_OF_BIG_MINES = 0.80;
    private final double PERCENTAGE_OF_SMALL_MINES = 0.20;
    private final int TIMER_DELAY = 1000;
    
    // The number of the rows and columns for each difficulty level
    private final int EASY_NUM = 16;
    private final int MEDIUM_NUM = 28;
    private final int HARD_NUM = 48;
    
    private final int STATUS_SIZE = 60;

    private final int COVER_FOR_CELL = 11;
    private final int MARK_FOR_CELL = 11;
    private final int EMPTY_CELL = 0;
    private final int SMALL_MINE_CELL = 9;
    private final int BIG_MINE_CELL = 10;
    private final int SMALL_COVERED_MINE_CELL = SMALL_MINE_CELL + COVER_FOR_CELL;
    private final int BIG_COVERED_MINE_CELL = BIG_MINE_CELL + COVER_FOR_CELL;
    private final int MARKED_MINE_CELL = BIG_COVERED_MINE_CELL + MARK_FOR_CELL;

    private final int DRAW_SMALL_MINE = 9;
    private final int DRAW_BIG_MINE = 10;
    private final int DRAW_COVER = 11;
    private final int DRAW_MARK = 12;
    private final int DRAW_WRONG_MARK = 13;

    private int[] field;
    private boolean inGame;
    private int chancesLeft;
    private boolean gameSolved = false;
    private int mines_left;
    private Image[] img;
    private int bigMines;
    private int smallMines;
    private int rows;
    private int cols;
    private int all_cells;
    private JLabel statusbar;
    private Timer timer;
    private int timeElapsed = 0;
    
    private UndoRedoStack stack;
    
    public Board(JLabel statusbar, String difficulty) {
    	
    	// set the columns and rows to determine board size
        if (difficulty.equals("easy")) {
        	rows = EASY_NUM;
        	cols = EASY_NUM;
        } else if (difficulty.equals("medium")) {
        	rows = MEDIUM_NUM;
        	cols = MEDIUM_NUM;
        } else if (difficulty.equals("hard")) {
        	rows = HARD_NUM;
        	cols = HARD_NUM;
        }
        
        // set number of mines to % based on the number of squares on the board
        bigMines = (int)Math.round((rows * cols) * (PERCENTAGE_OF_MINES * PERCENTAGE_OF_BIG_MINES));
        smallMines = (int)Math.round((rows * cols) * (PERCENTAGE_OF_MINES * PERCENTAGE_OF_SMALL_MINES));
        System.out.println("Number of big mines: " + bigMines);
        System.out.println("Number of small mines: " + smallMines);
        
        // set the dimension of the board and extra room for the status bar
        setSize(new Dimension((cols*CELL_SIZE),(rows*CELL_SIZE) + STATUS_SIZE));
        
        // initialise timer and time
        timer = new Timer(TIMER_DELAY, timerListener);
    	
    	this.statusbar = statusbar;

        img = new Image[NUM_IMAGES];

        for (int i = 0; i < NUM_IMAGES; i++) {
            img[i] =
                    (new ImageIcon(this.getClass().getResource((i)
                        + ".png"))).getImage();
        }

        setDoubleBuffered(true);
        
        addMouseListener(new MinesAdapter());
        newGame();
    }
    
    public Board(JLabel statusbar, int[] field, int numMines) {
    	
    	this.field = field;
    	this.mines_left = numMines;
    	
    	inGame = true;
    	stack = new UndoRedoStack();
    	chancesLeft = 2;
    	all_cells = field.length;
    	
    	// set the columns and rows to determine board size
        if (field.length == EASY_NUM * EASY_NUM) {
        	rows = EASY_NUM;
        	cols = EASY_NUM;
        } else if (field.length == MEDIUM_NUM * MEDIUM_NUM) {
        	rows = MEDIUM_NUM;
        	cols = MEDIUM_NUM;
        } else if (field.length == HARD_NUM * HARD_NUM) {
        	rows = HARD_NUM;
        	cols = HARD_NUM;
        }
        
        // set number of mines to % based on the number of squares on the board
//        bigMines = (int)Math.round((rows * cols) * (PERCENTAGE_OF_MINES * PERCENTAGE_OF_BIG_MINES));
//        smallMines = (int)Math.round((rows * cols) * (PERCENTAGE_OF_MINES * PERCENTAGE_OF_SMALL_MINES));
//        System.out.println("Number of big mines: " + bigMines);
//        System.out.println("Number of small mines: " + smallMines);
        
        // set the dimension of the board and extra room for the status bar
        setSize(new Dimension((cols*CELL_SIZE),(rows*CELL_SIZE) + STATUS_SIZE));
        
        // initialise timer and time
        timer = new Timer(TIMER_DELAY, timerListener);
    	
    	this.statusbar = statusbar;

        img = new Image[NUM_IMAGES];

        for (int i = 0; i < NUM_IMAGES; i++) {
            img[i] =
                    (new ImageIcon(this.getClass().getResource((i)
                        + ".png"))).getImage();
        }

        setDoubleBuffered(true);
        
        addMouseListener(new MinesAdapter());
        validate();
        repaint();
    }
    
    ActionListener timerListener = new ActionListener() {
    	public void actionPerformed(ActionEvent evt) {
    		statusbar.setText(String.valueOf(timeElapsed));
    		timeElapsed++;
    	}
    };


	public void newGame() {
		
		stack = new UndoRedoStack();

		chancesLeft = 2;
        inGame = true;
        timer.start();
        mines_left = bigMines + smallMines;

        all_cells = rows * cols;
        field = new int[all_cells];
        
        for (int i = 0; i < all_cells; i++)
            field[i] = COVER_FOR_CELL;

        statusbar.setText(Integer.toString(mines_left));
        
        placeMines(BIG_COVERED_MINE_CELL, SMALL_COVERED_MINE_CELL, bigMines);
        placeMines(SMALL_COVERED_MINE_CELL, BIG_COVERED_MINE_CELL, smallMines);
        
    }
	
	private void placeMines(int COVERED_MINE_CELL, int OTHER, int numMines) {
		int i = 0;
        int position = 0;
        int cell = 0;
        
        Random random = new Random();
        int current_col;
        
        i = 0;
        while (i < numMines) {

            position = (int) (all_cells * random.nextDouble());

            if ((position < all_cells) &&
                (field[position] != COVERED_MINE_CELL) && (field[position] != OTHER)) {


                current_col = position % cols;
                field[position] = COVERED_MINE_CELL;
                i++;

                if (current_col > 0) { 
                    cell = position - 1 - cols;
                    if (cell >= 0)
                        if ((field[cell] != COVERED_MINE_CELL) && (field[cell] != OTHER))
                            field[cell] += 1;
                    cell = position - 1;
                    if (cell >= 0)
                        if ((field[cell] != COVERED_MINE_CELL) && (field[cell] != OTHER))
                            field[cell] += 1;

                    cell = position + cols - 1;
                    if (cell < all_cells)
                        if ((field[cell] != COVERED_MINE_CELL) && (field[cell] != OTHER))
                            field[cell] += 1;
                }

                cell = position - cols;
                if (cell >= 0)
                    if ((field[cell] != COVERED_MINE_CELL) && (field[cell] != OTHER))
                        field[cell] += 1;
                cell = position + cols;
                if (cell < all_cells)
                    if ((field[cell] != COVERED_MINE_CELL) && (field[cell] != OTHER))
                        field[cell] += 1;

                if (current_col < (cols - 1)) {
                    cell = position - cols + 1;
                    if (cell >= 0)
                        if ((field[cell] != COVERED_MINE_CELL) && (field[cell] != OTHER))
                            field[cell] += 1;
                    cell = position + cols + 1;
                    if (cell < all_cells)
                        if ((field[cell] != COVERED_MINE_CELL) && (field[cell] != OTHER))
                            field[cell] += 1;
                    cell = position + 1;
                    if (cell < all_cells)
                        if ((field[cell] != COVERED_MINE_CELL) && (field[cell] != OTHER))
                            field[cell] += 1;
                }
            }
        }
	}
	
	public void solveGame() {
		for (int i = 0; i < field.length; i++) {
			if (field[i] > BIG_MINE_CELL)
				field[i] -= COVER_FOR_CELL;
		}
		gameSolved = true;
		
		repaint();
	}
	
    public void find_empty_cells(int j) {

        int current_col = j % cols;
        int cell;

        if (current_col > 0) { 
            cell = j - cols - 1;
            if (cell >= 0)
                if (field[cell] > BIG_MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL)
                        find_empty_cells(cell);
                }

            cell = j - 1;
            if (cell >= 0)
                if (field[cell] > BIG_MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL)
                        find_empty_cells(cell);
                }

            cell = j + cols - 1;
            if (cell < all_cells)
                if (field[cell] > BIG_MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL)
                        find_empty_cells(cell);
                }
        }

        cell = j - cols;
        if (cell >= 0)
            if (field[cell] > BIG_MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL)
                    find_empty_cells(cell);
            }

        cell = j + cols;
        if (cell < all_cells)
            if (field[cell] > BIG_MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL)
                    find_empty_cells(cell);
            }

        if (current_col < (cols - 1)) {
            cell = j - cols + 1;
            if (cell >= 0)
                if (field[cell] > BIG_MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL)
                        find_empty_cells(cell);
                }

            cell = j + cols + 1;
            if (cell < all_cells)
                if (field[cell] > BIG_MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL)
                        find_empty_cells(cell);
                }

            cell = j + 1;
            if (cell < all_cells)
                if (field[cell] > BIG_MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL)
                        find_empty_cells(cell);
                }
        }

    }
    
    public void paint(Graphics g) {

        int cell = 0;
        int uncover = 0;
        
//        System.out.println("FIELD BEFORE PAINT: " + Arrays.toString(field));
//        System.out.println("inGame before 2mine check: " + inGame);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                cell = field[(i * cols) + j];
                

                if (inGame && cell == BIG_MINE_CELL)
                    inGame = false;
                
                if (chancesLeft == 0) {
                	inGame = false;
                }

                if (!inGame) {
                    if (cell == SMALL_COVERED_MINE_CELL) {
                        cell = DRAW_SMALL_MINE;
                    } else if (cell == BIG_COVERED_MINE_CELL || cell == BIG_MINE_CELL) {
                    	cell = DRAW_BIG_MINE;
                    } else if (cell == MARKED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > BIG_COVERED_MINE_CELL) {
                        cell = DRAW_WRONG_MARK;
                    } else if (cell > SMALL_MINE_CELL) {
                        cell = DRAW_COVER;
                    }


                } else {
                    if (cell > BIG_COVERED_MINE_CELL)
                        cell = DRAW_MARK;
                    else if (cell > SMALL_MINE_CELL) {
                        cell = DRAW_COVER;
                        uncover++;
                    }
                }

                g.drawImage(img[cell], (j * CELL_SIZE),
                    (i * CELL_SIZE), this);
            }
        }
//        System.out.println("inGame after 2mine check: " + inGame);
        System.out.println("chancesLeft: " + chancesLeft);
        System.out.println("FIELD AFTER PAINT: " + Arrays.toString(field));


        if (uncover == 0 && inGame) {
            inGame = false;
            statusbar.setText("Game won");
        } else if (!inGame && !gameSolved) {
            statusbar.setText("Game lost");
        } else if (!inGame && gameSolved) {
        	statusbar.setText("Game solved");
        }
    }
    
    public int[] getField() {
    	return field;
    }
    
    public void setField(int[] field) {
    	this.field = field;
    }
    
    public int getNumMines() {
    	return mines_left;
    }
    
    public void setMinesLeft(int minesLeft) {
    	this.mines_left = minesLeft;
    }
    
    public Timer getTimer() {
    	return timer;
    }
    
    public int getTimeElapsed() {
    	return timeElapsed;
    }



    
    // UNDO AND REDO METHODS
    public void undo() {
    	int[] arr = stack.undo();
    	field = arr;
    	repaint();
    }
    
    public void redo() {
    	int[] arr = stack.redo();
    	// uncover each mine that matches the indexes in the array
    	field = arr;
    	repaint();
    }
    
    class MinesAdapter extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
        	
        	
        	if (stack.isEmpty()) {
        		stack.push(field.clone());
        		System.out.print("Pushed first state to stack\n");
        	} else if (!stack.peek().equals(field.clone())) {
        		stack.push(field.clone());
        		System.out.print("Pushed another state to stack\n");
        	}
        	
            int x = e.getX();
            int y = e.getY();

            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;

            boolean rep = false;

            if (!inGame) {
                newGame();
                repaint();
            }


            if ((x < cols * CELL_SIZE) && (y < rows * CELL_SIZE)) {

                if (e.getButton() == MouseEvent.BUTTON3) {

                    if (field[(cRow * cols) + cCol] > BIG_MINE_CELL) {
                        rep = true;

                        if (field[(cRow * cols) + cCol] <= SMALL_COVERED_MINE_CELL) {
                            if (mines_left > 0) {
                                field[(cRow * cols) + cCol] += MARK_FOR_CELL;
                                mines_left--;
                                statusbar.setText(Integer.toString(mines_left));
                            } else
                                statusbar.setText("No marks left");
                        } else {

                            field[(cRow * cols) + cCol] -= MARK_FOR_CELL;
                            mines_left++;
                            statusbar.setText(Integer.toString(mines_left));
                        }
                    }

                } else {

                    if (field[(cRow * cols) + cCol] > BIG_COVERED_MINE_CELL) {
                        return;
                    }

                    if ((field[(cRow * cols) + cCol] > BIG_MINE_CELL) &&
                        (field[(cRow * cols) + cCol] < MARKED_MINE_CELL)) {

                        field[(cRow * cols) + cCol] -= COVER_FOR_CELL;
                        rep = true;

                        if (field[(cRow * cols) + cCol] == BIG_MINE_CELL)
                            inGame = false;
                        if (field[(cRow * cols) + cCol] == SMALL_MINE_CELL) {
                            chancesLeft--;
                        	if (chancesLeft == 0)
                        		inGame = false;
                        }
                        if (field[(cRow * cols) + cCol] == EMPTY_CELL)
                            find_empty_cells((cRow * cols) + cCol);
                    }
                }

                if (rep)
                    repaint();

            }
            
            stack.setNextRedo(field.clone());
            
            if (!inGame) {
            	timer.stop();
            	timeElapsed = 0;
            }
        }
    }
}
