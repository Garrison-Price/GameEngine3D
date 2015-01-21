/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPack;

import java.awt.event.KeyEvent;

/**
 *
 * @author Dasty
 */
public class Game 
{
    public int time;
    public Controller controls;
    
    public Game()
    {
        controls = new Controller();
    }
    
    public void tick(boolean[] key)
    {
        time++;
        boolean forward = key[KeyEvent.VK_W];
        boolean backward = key[KeyEvent.VK_S];
        boolean left = key[KeyEvent.VK_A];
        boolean right = key[KeyEvent.VK_D];
        boolean turnLeft = key[KeyEvent.VK_Q];
        boolean turnRight = key[KeyEvent.VK_E];
        
        controls.tick(forward, backward, left, right, turnLeft, turnRight);
    }
}
