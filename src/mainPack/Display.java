/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPack;

import Graphics.Render;
import Graphics.Screen;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;

/**
 *
 * @author Dasty
 */
public class Display extends Canvas implements Runnable
{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "3D Game Engine 0.01";
    
    private Thread thread;
    private Game game;
    private boolean running = false;
    private Screen screen;
    private BufferedImage img;
    private int[] pixels;
    private InputHandler input;
    
    public Display()
    {
        screen = new Screen(WIDTH, HEIGHT);
        game = new Game();
        img = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
        
        input = new InputHandler();
        addKeyListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);
        addFocusListener(input);
    }
    
    private void start()
    {
        if(running)
            return;
        
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    private void stop()
    {
        if(!running)
            return;
        
        try
        {
            thread.join();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public void run()
    {
        int frames = 0;
        double unprocessedSeconds = 0;
        long previousTime = System.nanoTime();
        double secondsPerTick = 1 / 60.0;
        int tickCount = 0;
        boolean ticked = false;
        while(running)
        {
            long currentTime = System.nanoTime();
            long passedTime = currentTime - previousTime;
            previousTime = currentTime;
            unprocessedSeconds += passedTime / 1000000000.0;
            
            while(unprocessedSeconds > secondsPerTick)
            {
                tick();
                unprocessedSeconds -= secondsPerTick;
                ticked = true;
                tickCount++;
                if(tickCount % 60 == 0)
                {
                    System.out.println(frames + "fps");
                    previousTime += 1000;
                    frames = 0;
                }
            }
            if(ticked)
            {
                render();
                frames++;
            }
            render();
            frames++;
        }
    }
    
    private void tick()
    {
        game.tick(input.key);
    }
    
    private void render()
    {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null)
        {
            createBufferStrategy(3);
            return;
        }
        
        screen.render(game);
        for(int i = 0; i < WIDTH*HEIGHT;i++)
        {
            pixels[i] = screen.pixels[i];
        }
        
        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
        g.dispose();
        bs.show();
    }
    
    public static void main(String[] args)
    {
        Display game = new Display();
        JFrame frame = new JFrame(TITLE);
        frame.add(game);
        frame.pack();
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        
        game.start();
    }
}
