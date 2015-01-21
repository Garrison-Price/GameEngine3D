/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPack;

import java.util.Set;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 *
 * @author Dasty
 */
public class DisplayLWJGL 
{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "3D Game Engine LWJGL 0.01";
    
    private LWJGLGame game;
    private boolean running = false;
    
    public DisplayLWJGL()
    {
    }
    
    public void create() throws LWJGLException {
        
        game = new LWJGLGame();
        Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
        Display.setFullscreen(false);
        Display.setTitle(TITLE);
        Display.create();
        
        //Keyboard
        Keyboard.create();

        //Mouse
        Mouse.setGrabbed(false);
        Mouse.create();
        
        initGL();
        resizeGL();
    }
    
    
    private void start()
    {
        if(running)
            return;
        
        running = true;
        run();
    }
    
    private void stop()
    {
        if(!running)
            return;
        
    }
    
    public void destroy() 
    {
        //Methods already check if created before destroying.
        Mouse.destroy();
        Keyboard.destroy();
        Display.destroy();
    }
    
    public void initGL() 
    {
        //2D Initialization
        glClearColor(0.0f,0.0f,0.0f,0.0f);
        //glDisable(GL_DEPTH_TEST);
        //glDisable(GL_LIGHTING);
    }
    
    public void run()
    {
        int frames = 0;
        double unprocessedSeconds = 0;
        long previousTime = System.nanoTime();
        double secondsPerTick = 1 / 60.0;
        int tickCount = 0;
        boolean ticked = false;
        while(!Display.isCloseRequested() && running)
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
    
    public void tick()
    {
        game.tick();
    }
    
    private void render()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);     // Clear The Screen And The Depth Buffer
        glLoadIdentity();  

        //Draw a basic square
        glTranslatef((float)Math.sin(game.time % 2000.0 / 200 * Math.PI * 2)*200+400,(float)Math.cos(game.time % 2000.0 / 200 * Math.PI * 2)*200+300,0.0f);
        //glRotatef(0,0.0f,0.0f,1.0f);
        //glTranslatef(0.0f, 0.0f, -0.0f);
        glColor3f(0.0f,0.5f,0.5f);
        glBegin(GL_QUADS); // Start Drawing The Cube
		glColor3f(0.0f, 1.0f, 0.0f); // Set The Color To Green
		glVertex3f(1, 1, -1); // Top Right Of The Quad (Top)
		glVertex3f(-1, 1, -1); // Top Left Of The Quad (Top)
		glVertex3f(-1, 1, 1); // Bottom Left Of The Quad (Top)
		glVertex3f(1, 1, 1); // Bottom Right Of The Quad (Top)

		glColor3f(1.0f, 0.0f, 0.0f); // Set The Color To Orange
		glVertex3f(1, -1, 1); // Top Right Of The Quad (Bottom)
		glVertex3f(-1, -1, 1); // Top Left Of The Quad (Bottom)
		glVertex3f(-1, -1, -1); // Bottom Left Of The Quad (Bottom)
		glVertex3f(1, -1, -1); // Bottom Right Of The Quad (Bottom)

		glColor3f(1.0f, 0.0f, 0.0f); // Set The Color To Red
		glVertex3f(1, 1, 1); // Top Right Of The Quad (Front)
		glVertex3f(-1, 1, 1); // Top Left Of The Quad (Front)
		glVertex3f(-1, -1, 1); // Bottom Left Of The Quad (Front)
		glVertex3f(1, -1, 1); // Bottom Right Of The Quad (Front)

		glColor3f(1.0f, 1.0f, 0.0f); // Set The Color To Yellow
		glVertex3f(1, -1, -1); // Bottom Left Of The Quad (Back)
		glVertex3f(-1, -1, -1); // Bottom Right Of The Quad (Back)
		glVertex3f(-1, 1, -1); // Top Right Of The Quad (Back)
		glVertex3f(1, 1, -1); // Top Left Of The Quad (Back)

		glColor3f(0.0f, 0.0f, 1.0f); // Set The Color To Blue
		glVertex3f(-1, 1, 1); // Top Right Of The Quad (Left)
		glVertex3f(-1, 1, -1); // Top Left Of The Quad (Left)
		glVertex3f(-1, -1, -1); // Bottom Left Of The Quad (Left)
		glVertex3f(-1, -1, 1); // Bottom Right Of The Quad (Left)

		glColor3f(1.0f, 0.0f, 1.0f); // Set The Color To Violet
		glVertex3f(1, 1, -1); // Top Right Of The Quad (Right)
		glVertex3f(1, 1, 1); // Top Left Of The Quad (Right)
		glVertex3f(1, -1, 1); // Bottom Left Of The Quad (Right)
		glVertex3f(1, -1, -1); // Bottom Right Of The Quad (Right)
            glEnd(); // Done Drawing The Quad
        Display.update();
    }
    
    public void resizeGL() 
    {
        //2D Scene
        glViewport(0,0,WIDTH,HEIGHT);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluOrtho2D(0.0f,WIDTH,0.0f,HEIGHT);
        glPushMatrix();

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glPushMatrix();
    }
    
    public static void main(String[] args) throws LWJGLException {
        DisplayLWJGL game = new DisplayLWJGL();
        game.create();
        game.start();
        
    }
}
