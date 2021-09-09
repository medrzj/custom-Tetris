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
 * Creates the blue color theme. 
 * @author Jessica Medrzycki
 * @version December 7, 2017
 *
 */
public class BlueColorTheme extends AbstractColorTheme {

    /** Dark-ish blue. */
    private static final Color CORN_FLOWER = new Color(100, 149, 237);
    
    /** light blue. */
    private static final Color LIGHT_BLUE = new Color(135, 206, 250);
    
    /**
     * Creates the theme. 
     */
    public BlueColorTheme() {
        super(createBlueRadial(), createLinearRadial(), CORN_FLOWER);
    }
    
    /**
     * Creates the radial gradient for the theme.
     * @return  the created radial gradient.
     */
    private static RadialGradientPaint createBlueRadial() {
         
        final float[] spacing = {0f, .25f, .5f, .75f, 1f};
        final float radius = 150;
        final Color[] colors = {Color.BLUE, CORN_FLOWER, LIGHT_BLUE, CORN_FLOWER, Color.GRAY};
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
        final Color[] colors = {CORN_FLOWER, LIGHT_BLUE, Color.lightGray, Color.BLUE};
        
        return new LinearGradientPaint(start, end, dist, colors);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "Blue Theme";
    }

}
