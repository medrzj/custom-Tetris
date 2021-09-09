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
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import model.Board;
import model.Point;
import model.TetrisPiece;
import themes.AbstractColorTheme;


/**
 * The panel that shows the next piece. 
 * @author Jessica Medrzycki
 * @version November 23, 2017
 */
public class NextPiecePanel extends JPanel implements Observer { 

    /** Generated serial ID. */
    private static final long serialVersionUID = 4660558087503641108L;

    /** The default size of the paint panel.*/
    private static final Dimension PANEL_DEFAULT_SIZE = new Dimension(200, 200); 
    
    /** Font for the next string. */
    private static final Font NEXT_FONT = new Font("Impact", Font.BOLD, 25);
    
    /** The block's lenth of the sides. */
    private static final int BLOCK_SIZE = 40;
    
    /** The next tetris piece to be displayed. */
    private TetrisPiece myNextPiece;
    
    /** The radial gradient to display. */
    private RadialGradientPaint myGradient;
    
    /**
     * Instantiates the next piece panel. 
     */
    protected NextPiecePanel() {
        super();
        setPreferredSize(PANEL_DEFAULT_SIZE);
        //setBackground(Color.GRAY);
        setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics); //NOT paintComponents
        final Graphics2D g2d = (Graphics2D) theGraphics;    
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setPaint(myGradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        
        //creates the title for the panel
        g2d.setFont(NEXT_FONT);
        g2d.setColor(Color.BLACK);
        final int stringPoint = getWidth() / 3 + 14;
        g2d.drawString("NEXT", stringPoint, NEXT_FONT.getSize()); 
        
        //draws two lines to help check if pieces are centered
//         g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
//         g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());

        if (myNextPiece != null) { 
            
            for (int i = 0; i < myNextPiece.getPoints().length; i++) { 
                
                final int pieceHeight = myNextPiece.getHeight();
                final int pieceWidth = myNextPiece.getWidth();
                final double shiftX = (getWidth() - (pieceWidth * BLOCK_SIZE)) / 2 + 2;
                final double shiftY = (getHeight() - (pieceHeight * BLOCK_SIZE)) / 6;

                
                final Point point = myNextPiece.getPoints()[i];
                g2d.setColor(Color.BLACK);
 
                if (myNextPiece == TetrisPiece.I) {
                    g2d.fill(new Rectangle2D.Double(shiftX + (point.x() * BLOCK_SIZE), 
                                                    (-point.y() * BLOCK_SIZE) 
                                          + (getHeight()) - (pieceHeight * BLOCK_SIZE), 
                                                    BLOCK_SIZE - 1 , BLOCK_SIZE - 1));
                } else if (myNextPiece == TetrisPiece.O) {
                    final int spacer = 7;
                    g2d.fill(new Rectangle2D.Double(shiftX / 2 + spacer 
                                                    + (point.x() * BLOCK_SIZE),
                                     (-point.y() * BLOCK_SIZE) + shiftY 
                                     + (getHeight()) - (pieceHeight * BLOCK_SIZE), 
                                                BLOCK_SIZE - 1 , BLOCK_SIZE - 1));
                } else {
                    g2d.fill(new Rectangle2D.Double(shiftX + (point.x() * BLOCK_SIZE), 
                                         (-point.y() * BLOCK_SIZE) + shiftY
                                         + (getHeight()) - (pieceHeight * BLOCK_SIZE), 
                                                    BLOCK_SIZE - 1 , BLOCK_SIZE - 1));
                }
                
            }
        }

        
    }
    
    @Override
    public void update(final Observable theO, final Object theArg) {
        if (theO instanceof Board && theArg instanceof TetrisPiece) {
            myNextPiece = (TetrisPiece) theArg;
            repaint();
        }
        if (theO instanceof TetrisGUI && theArg instanceof AbstractColorTheme) {
            myGradient = ((AbstractColorTheme) theArg).getRadial();
            repaint();
        }
       
    }
}
