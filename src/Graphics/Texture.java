/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author Dasty
 */
public class Texture 
{
    public static Render floor = loadBitmap("/textures/floor.png");
    
    public static Render loadBitmap(String filename)
    {
        try
        {
            BufferedImage image = ImageIO.read(Texture.class.getResource(filename));
            int width = image.getWidth();
            int height = image.getHeight();
            Render result = new Render(width, height);
            image.getRGB(0, 0, width, height, result.pixels, 0, width);
            return result;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
