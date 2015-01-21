/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import mainPack.Game;

/**
 *
 * @author Dasty
 */
public class Render3D extends Render
{
    public double[] zBuffer;
    private double renderDistance = 5000;
    
    public Render3D(int w, int h)
    {
        super(w,h);
        zBuffer = new double[w*h];
    }
    
    public void floor(Game game)
    {
        double floorPosition = 8;
        double ceilingPosition = 28;
        double right = game.controls.x;
        double forward = game.controls.z;
        
        double rotation = game.controls.rotation;
        double cosine = Math.cos(rotation);
        double sine = Math.sin(rotation);
        
        for(int  y= 0; y < height; y++)
        {
            double ceiling = (y - height / 2.0) / height;
            
            double z = floorPosition / ceiling;
            
            if(ceiling < 0)
            {
                z = ceilingPosition / - ceiling;
            }
            
            for(int x = 0; x <width; x++)
            {
                double depth = (x - width /2.0) / height;
                depth *=z;
                
                double xx = depth * cosine + z * sine;
                double yy = z * cosine - depth * sine; 
                
                int xPix = (int) (xx + right);
                int yPix = (int) (yy + forward);
                zBuffer[x+y*width] = z;
                pixels[x+y*width] = Texture.floor.pixels[(xPix&255) + (yPix&255) *256];
                //System.out.println(xPix+" & "+7);
            }
        }
    }
    
    public void renderDistanceLimiter()
    {
        for(int i = 0; i<width*height;i++)
        {
            int color = pixels[i];
            int brightness = (int) (renderDistance/zBuffer[i]);
            if(brightness < 0)
            {
                brightness = 0;
            }
            else if(brightness > 255)
            {
                brightness = 255;
            }
            
            int r = (color >> 16) & 0xff;
            int g = (color >> 8) & 0xff;
            int b = color & 0xff;
            
            r = r * brightness / 255;
            g = g * brightness / 255;
            b = b * brightness / 255;
            
            pixels[i] = r << 16 | g << 8 | b;
        }
    }
}
