/*
 * TCSS 305
 * Assignment 6 - Tetris
 */
package themes;

import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;

/**
 * Creates the color themes with two different
 * gradients. 
 * @author Jessica Medrzycki
 * @version December 7, 2017
 *
 */
public abstract class AbstractColorTheme {

    /** The radial gradient for the next piece panel. */
    private final RadialGradientPaint myRadial;
    
    /** The linear gradient for the main board. */
    private final LinearGradientPaint myLinear;
    
    /** The color representation of the theme. */
    private final Color myColor;
    /**
     * Constructs a color theme object
     * with themes set to null. 
     * @param   theRadial   the radial gradient.
     * @param   theLinear   the linear gradient 
     * @param   theColor    the color of the theme.
     */
    public AbstractColorTheme(final RadialGradientPaint theRadial,
                              final LinearGradientPaint theLinear, 
                              final Color theColor) {
        myRadial = theRadial;
        myLinear = theLinear;
        myColor = theColor;
    }
    
    /**  
     * Gets the name of the theme.
     * @return the name of the theme.
     */
    public abstract String getName();
    
    /**
     * Gets the radial gradient. 
     * @return  the radial gradient. 
     */
    public RadialGradientPaint getRadial() {
        return myRadial;
    }
    /**
     * Gets the linear gradient.
     * @return  the linear gradient. 
     */
    public LinearGradientPaint getLinear() {
        return myLinear;
    }
    
    /**
     * Gets the color of the theme. 
     * @return the color theme. 
     */
    public Color getColor() {
        return myColor;
    }
}
