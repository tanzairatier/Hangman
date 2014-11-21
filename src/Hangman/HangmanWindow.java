/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mandahomeworks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author tanza
 */
public class HangmanWindow extends JPanel { 
    
    String word_to_guess;
    boolean[] previous_guesses;
    boolean[] letters_known;
    int num_guesses = 0;
    int max_guesses = 6;
    String words[];
    
    public HangmanWindow() throws IOException {
        
        this.loadWords();
        
        
        previous_guesses = new boolean[26];
        for (int i = 0; i < previous_guesses.length; i++) {
            previous_guesses[i] = false;
        }
        
        Random rgen = new Random();
        int choice = rgen.nextInt(words.length);
        
        word_to_guess = words[choice];
        String category = "";
        letters_known = new boolean[word_to_guess.length()];
        for (int i = 0; i < letters_known.length; i++) {
            letters_known[i] = false;
        }
        
        String output = "<html>";
        for (int i = 0; i < word_to_guess.length(); i++) {
            if (word_to_guess.charAt(i) == ' ') {
                output += "<font size='16'> &nbsp &nbsp </font>";
            }
            else {
                output += "<font size='16'> <u>&nbsp</u> </font>";
            }
        }
        output += "</html>";
        
        category = "<html><font size='11'>" + category + "</font></html>";
        
        
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.white);
        
        JLabel enter_guess_label = new JLabel("Enter Guess:");
        JLabel previous_guesses_label = new JLabel("Previous Guesses:");
        JTextField enter_guess_textfield = new JTextField(5);
        JLabel error_label = new JLabel("");
        JLabel previous_guesses_list_label = new JLabel("<html><font size='11' color='#C0C0C0'>ABCDEDFGHIJKMNOPQRSTUVWXYZ</font></html>");
        
        
        final BufferedImage logo = ImageIO.read(getClass().getResource("hangman_logo.png"));
        JLabel title_label = new JLabel("<html><div align='center'><font size='24'>HANGMAN</font></div></html>", SwingConstants.CENTER);
        JPanel title_picture = new JPanel(){
            public void paintComponent(Graphics g) {
                g.drawImage(logo, this.getWidth()/2 - logo.getWidth()/2, this.getHeight()/2 - logo.getHeight()/2, this);
            }
        };
        JLabel hangman_word_to_guess = new JLabel(output, SwingConstants.CENTER);
        JLabel category_label = new JLabel(category, SwingConstants.CENTER);
        
        
        BufferedImage[] hanging_men = new BufferedImage[8];
        for (int i = 0; i < hanging_men.length; i++) {
            hanging_men[i] = ImageIO.read(getClass().getResource("hangman" + (i+1) + ".png"));
        }
        
        JPanel hangman_victim_panel = new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(hanging_men[num_guesses], this.getWidth()/2 - hanging_men[num_guesses].getWidth()/2, this.getHeight()/2 - hanging_men[num_guesses].getHeight()/2, this);
                repaint();
            }
        };
        hangman_victim_panel.setOpaque(false);
        JPanel top_panel = new JPanel();  //title + victim + word + category
        JPanel guess_panel = new JPanel(); //guess label and guess input box
        JPanel previous_guess_panel = new JPanel(); //previous guess and its list
        
        JButton new_game_button = new JButton("New Game");
        new_game_button.setVisible(false);
        new_game_button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                
                previous_guesses = new boolean[26];
                for (int i = 0; i < previous_guesses.length; i++) {
                    previous_guesses[i] = false;
                }

                Random rgen = new Random();
                int choice = rgen.nextInt(words.length);

                word_to_guess = words[choice];
                String category = "";
                letters_known = new boolean[word_to_guess.length()];
                for (int i = 0; i < letters_known.length; i++) {
                    letters_known[i] = false;
                    
                }   
        
                String output = "<html>";
                for (int i = 0; i < word_to_guess.length(); i++) {
                    if (word_to_guess.charAt(i) == ' ') {
                        output += "<font size='16'> &nbsp &nbsp </font>";
                    }
                    else {
                        output += "<font size='16'> <u>&nbsp</u> </font>";
                    }
                }
                output += "</html>";
                category = "<html><font size='11'>" + category + "</font></html>";
                hangman_word_to_guess.setText(output);
                num_guesses = 0;
                
                previous_guesses_list_label.setText("<html><font size='11' color='#C0C0C0'>ABCDEDFGHIJKMNOPQRSTUVWXYZ</font></html>");
                enter_guess_textfield.setText("");
                new_game_button.setVisible(false);
                
                
            }
        });
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 1.0;
        c.weighty = 0.7;
        this.add(top_panel, c);
        top_panel.setOpaque(false);
        //top_panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 1.0;
        c.weighty = 0.01;
        this.add(guess_panel, c);
        guess_panel.setOpaque(false);
        //guess_panel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 1.0;
        c.weighty = 0.01;
        this.add(previous_guess_panel, c);
        previous_guess_panel.setOpaque(false);
        //previous_guess_panel.setBorder(BorderFactory.createLineBorder(Color.RED));
        
        
        top_panel.setLayout(new GridBagLayout());
        
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 1.0;
        c.weighty = 0.45;
        top_panel.add(title_picture,c);
        //title_label.setBorder(BorderFactory.createLineBorder(Color.RED));      
        
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 1.0;
        c.weighty = 0.90;
        top_panel.add(hangman_victim_panel,c);
        
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 1.0;
        c.weighty = 0;
        top_panel.add(category_label,c);
        
        
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 1.0;
        c.weighty = 0;
        top_panel.add(hangman_word_to_guess,c);
        
        guess_panel.add(enter_guess_label);
        guess_panel.add(enter_guess_textfield);
        guess_panel.add(error_label);
        guess_panel.add(new_game_button);
        
        previous_guess_panel.add(previous_guesses_label);
        previous_guess_panel.add(previous_guesses_list_label);
        
        
        enter_guess_textfield.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (num_guesses < 6) {
                    String text_input = enter_guess_textfield.getText();
                    if (text_input.length() > 0) {
                        if (text_input.length() > 1 || text_input.isEmpty()) {
                            error_label.setText("<html><font color='Red'>Please input a single letter.</font></html>");
                        }
                        else {
                            char guess = text_input.charAt(0);
                            if (Character.isLetter(guess)) {
                                int pos = guess - 'a';
                                if (pos < 0) pos += 32;
                                if (previous_guesses[pos]) {
                                    error_label.setText("<html><font color='Red'>That letter has already been guessed.</font></html>");
                                }
                                else {
                                    error_label.setText("");
                                    previous_guesses[pos] = true;
                                    String output = "<html>";
                                    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

                                    for (int i = 0; i < previous_guesses.length; i++) {
                                        if (previous_guesses[i]) {
                                            output += "<font size='11' color='Black'>" + alphabet.charAt(i) + "</font>";
                                        }
                                        else {
                                            output += "<font size='11' color='#C0C0C0'>" + alphabet.charAt(i) + "</font>";
                                        }
                                    }
                                    output += "</html>";
                                    previous_guesses_list_label.setText(output);
                                    previous_guesses_list_label.repaint();

                                    output = "<html>";
                                    if (!Character.isLowerCase(guess)) {
                                        guess = Character.toLowerCase(guess);
                                    }
                                    boolean lose_a_life = true;
                                    boolean all_known = true;
                                    int evaluates = 0;

                                    for (int i = 0; i < word_to_guess.length(); i++) {
                                        char what;
                                        if (!Character.isLowerCase(word_to_guess.charAt(i))) {
                                            what = Character.toLowerCase(word_to_guess.charAt(i));
                                        }
                                        else {
                                            what = word_to_guess.charAt(i);
                                        }
                                        if (word_to_guess.charAt(i) == ' ') {
                                            output += "<font size='16'> &nbsp </font>";
                                        }
                                        else if (what == guess) {
                                            output += "<font size='16'><u>" + word_to_guess.charAt(i) + "</u></font>";
                                            letters_known[i] = true;
                                            evaluates += 1;
                                            lose_a_life = false;
                                        }
                                        else if (letters_known[i]) {
                                            output += "<font size='16'><u>" + word_to_guess.charAt(i) + "</u></font>";
                                        }
                                        else {
                                            all_known = false;
                                            output += "<font size='16'> <u>&nbsp</u> </font>";
                                        }
                                    }
                                    output += "</html>";
                                    hangman_word_to_guess.setText(output);



                                    if (lose_a_life) {
                                        num_guesses += 1;
                                        if (num_guesses >= 6) {
                                            error_label.setText("<html><font color='Blue'> The letter '" + guess + "' was not found.  Game Over!</font><html>");
                                        }
                                        else {
                                            error_label.setText("<html><font color='Blue'> The letter '" + guess + "' was not found.  " + (6-num_guesses) + " incorrect guesses remaining.</font><html>");
                                        }
                                    }
                                    else {
                                        if (all_known) {
                                            error_label.setText("<html><font color='Green'> The letter '" + guess + "' was found " + evaluates + " times!  You've won!  Game Over!</font><html>");
                                        }
                                        else {
                                            error_label.setText("<html><font color='Green'> The letter '" + guess + "' was found " + evaluates + " times!  " + (6-num_guesses) + " incorrect guesses remaining.</font><html>");
                                        }
                                    }

                                    if (num_guesses > 6) num_guesses = 6;

                                    if (num_guesses >= 6) {
                                        //game over here - reveal all
                                        output = "<html>";
                                        for (int i = 0; i < word_to_guess.length(); i++) {
                                            if (word_to_guess.charAt(i) == ' ') {
                                                output += "<font size='16'> &nbsp &nbsp </font>";
                                            }
                                            else {
                                                output += "<font size='16'> <u>" + word_to_guess.charAt(i) + "</u> </font>";
                                            }
                                        }
                                        output += "</html>";
                                        hangman_word_to_guess.setText(output);
                                        new_game_button.setVisible(true);
                                    }

                                    if (all_known) num_guesses = 7;

                                }

                            }
                            else {
                                error_label.setText("<html><font color='Red'>Only letters are allowed.</font></html>");
                            }

                            enter_guess_textfield.setText("");
                        }
                    }
                    else {
                        error_label.setText("");
                    }
                }
            }});

        
    }
    
    public int countLines(String filename) throws IOException {
        
        URL url = getClass().getResource(filename);
        File file = new File(url.getPath());
        
        InputStream is = new BufferedInputStream(new FileInputStream( file ));
        
        
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }

    public void loadWords() throws IOException {
        /*
        words = new String[countLines("Antworth")];
        URL url = getClass().getResource("Antworth");
        File file = new File(url.getPath());
        
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int cnt = 0;
        while ((line = br.readLine()) != null) {
           words[cnt] = line;
           cnt += 1;
        }
        br.close();
        
        
        */
        
        HangmanWords mywords = new HangmanWords();
        words = mywords.words_array;
        
    }
    
}
