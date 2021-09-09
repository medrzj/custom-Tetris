/*
 * TCSS 305
 * Assignment 6 - Tetris
 */
package themes;

import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

/**
 * The pink color theme.
 * @author Jessica Medrzycki
 * @version December 7, 2017
 *
 */
public class PinkColorTheme extends AbstractColorTheme {  
    
    /** Indian red. */
    private static final Color INDIAN_RED = new Color(205, 92, 92);
    
    /** Coral. */
    private static final Color CORAL = new Color(240, 128, 128);
    
    /**
     * Creates the pink theme. 
     */
    public PinkColorTheme() {
        super(createPinkRadial(), createLinearRadial(), CORAL);
    }
    
    /**
     * Creates the radial gradient for the theme.
     * @return  the created radial gradient.
     */
    private static RadialGradientPaint createPinkRadial() {
        
        final float[] spacing = {0f, .25f, .5f, .75f, 1f};
        final float radius = 150;
        final Color[] colors = {INDIAN_RED, CORAL, Color.PINK, INDIAN_RED, Color.GRAY};
        final Point2D center = new Point2D.Float(110, 100);
        return new RadialGradientPaint(center, 
                                       radius, spacing, colors);
    }
    
    /**
     * Creates the linear gradient for the theme. 
     * @return  the linear gradient. 
     */
    private static LinearGradientPaint createLinearRadial() {
        final Point2D start = new Point2D.Float(0, 0);
        final Point2D end = new Point2D.Float(300, 600);
        final float[] dist = {0.0f, 0.45f, 0.85f, 1.0f};
        final Color[] colors = {INDIAN_RED, Color.PINK, Color.lightGray, INDIAN_RED};
        
        return new LinearGradientPaint(start, end, dist, colors);
    }

    @Override
    public String getName() {
        return "Pink Theme";
        
    }

}
