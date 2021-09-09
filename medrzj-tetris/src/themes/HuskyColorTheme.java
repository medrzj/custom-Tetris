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
 * Creates the husky colors theme.
 * @author Jessica Medrzycki
 * @version December 7, 2017
 * 
 */
public class HuskyColorTheme extends AbstractColorTheme {
    
    /** Husky gold color. */
    private static final Color GOLD = new Color(232, 211, 162);
    
    /** Husky purple color. */
    private static final Color PURPLE = new Color(51, 0, 111);
    
    /** Husky metallic color. */
    private static final Color METALLIC = new Color(145, 123, 76);

    /**
     * Creates the husky theme. 
     */
    public HuskyColorTheme() {
        super(createRadial(), createLinear(), PURPLE);
    }
    
    /**
     * Creates the radial gradient for the theme.
     * @return  the created radial gradient.
     */
    private static RadialGradientPaint createRadial() {
        
        final float[] spacing = {0f, .25f, .5f, .75f, 1f};
        final float radius = 150;
        final Color[] colors = {GOLD, METALLIC, PURPLE, GOLD, Color.GRAY};
        final Point2D center = new Point2D.Float(110, 100);
        return new RadialGradientPaint(center, 
                                       radius, spacing, colors);
    }
    
    /**
     * Creates the linear gradient for the theme. 
     * @return  the linear gradient. 
     */
    private static LinearGradientPaint createLinear() {
        final Point2D start = new Point2D.Float(0, 0);
        final Point2D end = new Point2D.Float(300, 600);
        final float[] dist = {0.0f, 0.5f, 0.75f, 1.0f};
        final Color[] colors = {PURPLE, GOLD, METALLIC, PURPLE};
        
        return new LinearGradientPaint(start, end, dist, colors);
    }


    @Override
    public String getName() {
        return "Husky Theme";
    }

}
