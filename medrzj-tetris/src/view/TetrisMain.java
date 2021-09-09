/*
 * TCSS 305
 * Assignment 6 - Tetris
 */
package view;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.Board;


/**
 * Creates the gui and starts the program.
 * @author Jessica Medrzycki
 * @version November 21, 2017
 */
public final class TetrisMain {

    /**
     * Prevents external instantiation.
     */
    private TetrisMain() {
        throw new IllegalStateException();
    }

    /**
     * 
     * @param theArgs   the argument passed in.
     */
    public static void main(final String[] theArgs) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (final IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (final InstantiationException ex) {
            ex.printStackTrace();
        } catch (final ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TetrisGUI(); 
            }
        });

    }

}
