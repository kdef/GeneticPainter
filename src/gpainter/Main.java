package gpainter;

import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {

    // instance variables
    private static final int DEFAULT_HEIGHT = 600;
    private static final int DEFAULT_WIDTH = 800;

    
    public Main() {
        
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        
        Container topPane = getContentPane();
        // display images
        ImagePanel paintView = new ImagePanel();
        paintView.setBackground(Color.BLUE);
        ImagePanel originalView = new ImagePanel("/home/kyle/src/java/GeneticPainter/images/test.png");
        //originalView.setBackground(Color.RED);
        // display controls
        JPanel controlView = new JPanel();
        controlView.setBackground(Color.YELLOW);
        
        JButton start = new JButton("Start");
        controlView.add(start);
      
        // display images side by side 
        Box imagePane = new Box(BoxLayout.LINE_AXIS);
        imagePane.add(paintView);
        imagePane.add(originalView);

        Box contentPane = new Box(BoxLayout.PAGE_AXIS);
        contentPane.add(imagePane);
        contentPane.add(controlView);

        topPane.add(contentPane);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void initGPainter() {
        this.setTitle("Genetic Painter");
        this.setVisible(true);
    }
    
    public static void main(String[] args)
    {        
        Main window = new Main();
        window.initGPainter();        

        Individual indv = new Individual();

    }
    
}
