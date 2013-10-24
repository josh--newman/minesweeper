package mines;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class HighScores extends JFrame {
	
    private CardLayout cards;
    private JPanel cardPanel;
   // private static final JLabel
    
    public HighScores()
    { 
        
        //close frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("High Scores");
        setSize(300,200);
      
        //center the JFrame
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        //creating a border
        Border outline = BorderFactory.createLineBorder(Color.black);
        
        JPanel tabsPanel = new JPanel();
        tabsPanel.setBorder(outline);
        JButton easy = new JButton("Easy");
        JButton medium = new JButton("Medium");
        JButton hard = new JButton("Hard");
        easy.setActionCommand("Easy");
        easy.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
            	cards.show(cardPanel, "Easy");
            }
        });
        
        medium.setActionCommand("Medium");
        medium.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                cards.show(cardPanel, "Medium");
            }
        });
        
        hard.setActionCommand("Hard");
        hard.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
            	cards.show(cardPanel, "Hard");
            }
        });
        
        tabsPanel.add(easy);
        tabsPanel.add(medium);
        tabsPanel.add(hard);
        
        add(tabsPanel,BorderLayout.NORTH);
        
        
        cards = new CardLayout();
        cardPanel = new JPanel();
        cardPanel.setLayout(cards);
        cards.show(cardPanel, "Easy");
        
        JPanel easyCard = new JPanel();
        easyCard.setBackground(Color.WHITE);
        easyCard.setLayout(new BoxLayout(easyCard, BoxLayout.PAGE_AXIS));
        //add label for easy scores to use
        
        try {
        	String[] easyScores = FileManager.loadScores("easyScores.txt");
	        for(int i = 0; i < 5; i++) {
	        	JLabel easyLabel = new JLabel(String.valueOf(i+1) + ". " + easyScores[i]);
	        	easyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	        	easyLabel.setFont(new Font("arial", Font.PLAIN, 20));
	        	easyCard.add(easyLabel);
	        }
        }
        catch (IOException e) {
        	System.out.println("WTF");
        }
        
        JPanel mediumCard = new JPanel();
        mediumCard.setBackground(Color.WHITE);
        mediumCard.setLayout(new BoxLayout(mediumCard, BoxLayout.PAGE_AXIS));
        //add label for medium scores to use
        
        try {
        	String[] mediumScores = FileManager.loadScores("mediumScores.txt");
	        for(int i = 0; i < 5;i++) {
	        	JLabel mediumLabel = new JLabel(String.valueOf(i+1) + ". " + mediumScores[i]);
	        	mediumLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	        	mediumLabel.setFont(new Font("arial", Font.PLAIN, 20));
	        	mediumCard.add(mediumLabel);
	        }
        }
        catch (IOException e) {
        	System.out.println("WTF");
        }
        
        JPanel hardCard = new JPanel();
        hardCard.setBackground(Color.WHITE);
        hardCard.setLayout(new BoxLayout(hardCard, BoxLayout.PAGE_AXIS));
        //add label for high scores to use
        

        try {
        	String[] hardScores = FileManager.loadScores("hardScores.txt");
	        for(int i = 0; i < 5;i++) {
	        	JLabel hardLabel = new JLabel(String.valueOf(i+1) + ". " + hardScores[i]);
	        	hardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	        	hardLabel.setFont(new Font("arial", Font.PLAIN, 20));
	        	hardCard.add(hardLabel);
	        }
        }
        catch (IOException e) {
        	System.out.println("WTF");
        }
        cardPanel.add(easyCard, "Easy");
        cardPanel.add(mediumCard, "Medium");
        cardPanel.add(hardCard, "Hard");
        
        add(tabsPanel,BorderLayout.NORTH);
        add(cardPanel,BorderLayout.CENTER);
    }
    
    //add all buttons
    private void addButton(Container parent, String name)
    {
        JButton but = new JButton(name);
        but.setActionCommand(name);
        parent.add(but);
    }
}
