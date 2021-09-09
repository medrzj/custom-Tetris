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
 * The hold piece panel.
 * 
 * @author Jessica Medrzycki
 * @version December 7, 2017
 *
 */
public class HoldPiecePanel extends JPanel implements Observer {
    
    /** Generated UID. */
    private static final long serialVersionUID = 8006354681279748229L;
    
    /** The default size of the paint panel.*/
    private static final Dimension PANEL_DEFAULT_SIZE = new Dimension(150, 150);

    /** Font for the next string. */
    private static final Font HOLD_FONT = new Font("Impact", Font.BOLD, 25);
    
    /** The block's lenth of the sides. */
    private static final int BLOCK_SIZE = 32;
    
    /** The tetris piece to hold. */
    private TetrisPiece myHoldPiece;
    
    /** The color of the blocks. */
    private Color myColor;
    
    /**
     * Constructs the hold piece panel.
     */
    protected HoldPiecePanel() {
        super();
        setPreferredSize(PANEL_DEFAULT_SIZE);        
        setBorder(BorderFactory.createLineBorder(Color.black, 2));

    }
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics); //NOT paintComponents
        final Graphics2D g2d = (Graphics2D) theGraphics;    
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);


        //creates the title for the panel
        g2d.setFont(HOLD_FONT);
        g2d.setColor(Color.BLACK);
        final int stringPoint = getWidth() / 3;
        g2d.drawString("HOLD", stringPoint, HOLD_FONT.getSize()); 

        //draws two lines to help check if pieces are centered
        //         g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        //         g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());

        if (myHoldPiece != null) { 
            g2d.setColor(myColor);
            for (int i = 0; i < myHoldPiece.getPoints().length; i++) { 

                final int pieceHeight = myHoldPiece.getHeight();
                final int pieceWidth = myHoldPiece.getWidth();
                final double shiftX = (getWidth() - (pieceWidth * BLOCK_SIZE)) / 2 + 2;
                final double shiftY = (getHeight() - (pieceHeight * BLOCK_SIZE)) / 6;


                final Point point = myHoldPiece.getPoints()[i];

                if (myHoldPiece == TetrisPiece.I) {
                    g2d.fill(new Rectangle2D.Double(shiftX + (point.x() * BLOCK_SIZE), 
                                                    (-point.y() * BLOCK_SIZE) 
                                            + (getHeight()) - (pieceHeight * BLOCK_SIZE), 
                                                    BLOCK_SIZE - 1 , BLOCK_SIZE - 1));
                } else if (myHoldPiece == TetrisPiece.O) {
                    final int spacer = 3;
                    g2d.fill(new Rectangle2D.Double(shiftX / 2 - spacer
                                                    + (point.x() * BLOCK_SIZE),
                                                    (-point.y() * BLOCK_SIZE) + shiftY + 2 
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
        if (theO instanceof Board && theArg instanceof TetrisPiece[]) {
            final TetrisPiece[] piece = (TetrisPiece[]) theArg;
            myHoldPiece = piece[0];
            repaint();
        }
        if (theO instanceof TetrisGUI && theArg instanceof Boolean) {
            final TetrisPiece none = null;
            myHoldPiece = none;
            repaint();
        }
        if (theO instanceof TetrisGUI && theArg instanceof AbstractColorTheme) {
            myColor = ((AbstractColorTheme) theArg).getColor();
            repaint();
        }
    }

}
