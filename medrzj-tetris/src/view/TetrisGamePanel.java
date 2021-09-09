/*
 * TCSS 305
 * Assignment 6 - Tetris
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import model.Block;
import model.Board;
import themes.AbstractColorTheme;

/**
 * The Panel that holds the tetris board. 
 * 
 * @author Jessica Medrzycki
 * @version November 22, 2017
 */
public class TetrisGamePanel extends JPanel implements Observer {

    /** Generate serial UID. */
    private static final long serialVersionUID = 1619723792719761913L;

    /** The default size of the paint panel.*/
    private static final Dimension PANEL_DEFAULT_SIZE = new Dimension(300, 600);
    
    /** The square size (width/length) of the block.*/
    private static final int BLOCK_SIZE = 30;
 
//    /** The amount of blocks on the width of the panel. */
//    private static final int BLOCKS_PER_WIDTH = 10;
    
    /** The amount of blocks on the length of the panel. */
    private static final int BLOCKS_PER_LENGTH = 20;

    /** List containing the board data. */
    private final List<Block[]> myList;
    
    /** Blocks width with panel ratio. */
    private final int myBlockWidth = BLOCK_SIZE;
    
    /** Blocks legth with panel ratio. */
    private final int myBlockHeight = BLOCK_SIZE; 
    
    /** The paused status of the game. */
    private boolean myPauseStatus;
    
    /** The grid status. */
    private boolean myGridStatus;
    
    /** The linear gradient. */
    private LinearGradientPaint myGradient;
    
    /** The color for the pause menu. */
    private Color myColor;
  
    
    /** Instantiates the panel. */
    public TetrisGamePanel() {
        super();
        myPauseStatus = false;
        myGridStatus = false;
        setPreferredSize(PANEL_DEFAULT_SIZE);
        myList = new ArrayList<Block[]>();
        setBorder(BorderFactory.createLineBorder(Color.black, 2));
        
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics); //NOT paintComponents
        final Graphics2D g2d = (Graphics2D) theGraphics;    
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON);
    
        
        if (myPauseStatus) {
            g2d.setPaint(myColor);
            g2d.fillRect(0, 0, getWidth(), getHeight());
           // setBackground(Color.PINK);
            final int fontSize = 40;
            final Font f = new Font("Impact", Font.BOLD, fontSize);
            g2d.setFont(f);
            g2d.setPaint(Color.BLACK);
            final int pauseX = getWidth() / 3;
            g2d.drawString("PAUSED", pauseX, getHeight() / 2);
        } else {
            // setBackground(Color.lightGray);
            g2d.setPaint(myGradient); //gradient
            g2d.drawRect(0, 0, getWidth(), getHeight());

            final Iterator<Block[]> itr = myList.iterator();
         //   g2d.setPaint(Color.lightGray);

            for (int i = BLOCKS_PER_LENGTH - 1; i >= 0; i--) {

                if (itr.hasNext()) {
                    final Block[] row = itr.next();

                    for (int j = row.length - 1; j >= 0; j--) {

                        if (row[j] != null) {

                            g2d.fillRect(j * myBlockHeight, i * myBlockWidth,
                                         myBlockWidth - 1, myBlockHeight - 1); 


                        }

                    }
                }
            }


            //Draws the grid
            if (myGridStatus) {
                g2d.draw(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
                for (int i = 0; i < getWidth(); i += BLOCK_SIZE) {
                    g2d.setColor(Color.BLACK);
                    g2d.drawLine(i, 0, i, getHeight());

                }
                for (int i = 0; i < getHeight(); i += BLOCK_SIZE) {
                    g2d.setColor(Color.BLACK);
                    g2d.drawLine(0, i, getWidth(), i);

                }
            }

        }
    }
    
    /**
     * Method to update the pause menu status if
     * the pause menu should be shown. 
     * @param theStatus whether or not the pause menu should be shown.
     */
    protected void showPauseMenu(final boolean theStatus) {
        myPauseStatus = theStatus;
        repaint();             
    }
    
    /**
     * Method to update the board with the grid. 
     * @param theStatus if the grid should be shown
     */
    protected void showGrid(final boolean theStatus) {
        myGridStatus = theStatus;
        repaint();             

    }

    @Override
    public void update(final Observable theO, final Object theBoardData) {
        if (theO instanceof Board && theBoardData instanceof List) {
            myList.clear();
            final List<?> temp = (List<?>) theBoardData;
            for (int i = 0; i < temp.size(); i++) { //(List) theBoardData)
                final Block[] block = (Block[]) temp.get(i);
                myList.add(block);
            }
            repaint();
        }
        if (theO instanceof TetrisGUI && theBoardData instanceof AbstractColorTheme) {
            myGradient = ((AbstractColorTheme) theBoardData).getLinear();
            myColor = ((AbstractColorTheme) theBoardData).getColor();
            repaint();
        }
        
    }

}

