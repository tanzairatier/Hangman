/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mandahomeworks;

import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author tanza
 */
public class Hangman  {
    
    
    public static void main (String[] args) throws IOException
   {
      JFrame frame = new JFrame ("Hangman");
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

      HangmanWindow panel = new HangmanWindow();
      frame.getContentPane().add(panel);

      frame.pack();
      frame.setVisible(true);
   }
    
}       
