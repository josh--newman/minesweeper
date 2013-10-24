package mines;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class HighScores extends JFrame {
	
    CardLayout cards;
    JPanel cardPanel;
    
    public HighScores()
    { 
        
        //make sure the program exits when the frame closes
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("High Scores");
        setSize(400,300);
      
        //This will center the JFrame in the middle of the screen
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        //creating a border to highlight the JPanel areas
        Border outline = BorderFactory.createLineBorder(Color.black);
        
        JPanel tabsPanel = new JPanel();
        tabsPanel.setBorder(outline);
        JButton easyCard = new JButton("Easy");
        JButton mediumCard = new JButton("Medium");
        JButton hardCard = new JButton("Hard");
        easyCard.setActionCommand("Easy");
        easyCard.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
            	cards.show(cardPanel, "Easy");
            }
        });
        
        mediumCard.setActionCommand("Medium");
        mediumCard.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                cards.show(cardPanel, "Medium");
            }
        });
        
        hardCard.setActionCommand("Hard");
        hardCard.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
            	cards.show(cardPanel, "Hard");
            }
        });
        
        tabsPanel.add(easyCard);
        tabsPanel.add(mediumCard);
        tabsPanel.add(hardCard);
        
        add(tabsPanel,BorderLayout.NORTH);
        
        
        cards = new CardLayout();
        cardPanel = new JPanel();
        cardPanel.setLayout(cards);
        cards.show(cardPanel, "Easy");
        
        JPanel firstCard = new JPanel();
        firstCard.setBackground(Color.GREEN);
        
        
        JPanel secondCard = new JPanel();
        secondCard.setBackground(Color.YELLOW);
        
        
        JPanel thirdCard = new JPanel();
        thirdCard.setBackground(Color.RED);
        
        
        cardPanel.add(firstCard, "Easy");
        cardPanel.add(secondCard, "Medium");
        cardPanel.add(thirdCard, "Hard");
        
        add(tabsPanel,BorderLayout.NORTH);
        add(cardPanel,BorderLayout.CENTER);
    }
    
    //All the buttons are following the same pattern
    //so create them all in one place.
    private void addButton(Container parent, String name)
    {
        JButton but = new JButton(name);
        but.setActionCommand(name);
        parent.add(but);
    }
}
