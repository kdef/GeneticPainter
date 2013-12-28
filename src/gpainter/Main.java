package gpainter;

import java.awt.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class Main extends JFrame {

    //instance variables
    private ImagePanel paintView;
    private ImagePanel targetView;
    private JPanel controlView;

    /**
      * Instantiate the frame to display the images and other information.
      */
    public Main() {
        // calculate sizes
        Dimension minSize = new Dimension(ImagePanel.WIDTH * 2, ImagePanel.HEIGHT + 50);
        Dimension panelSize = new Dimension(ImagePanel.WIDTH, ImagePanel.HEIGHT);

        // setup images
        Container topPane = getContentPane();
        
        paintView = new ImagePanel();
        //paintView.setBackground(Color.BLUE);
        paintView.setMinimumSize(panelSize);
        paintView.setPreferredSize(panelSize);

        targetView = new ImagePanel("images/star.png");
        targetView.setMinimumSize(panelSize);
        targetView.setPreferredSize(panelSize);

        // setup controls
        controlView = new JPanel();
        controlView.setBackground(Color.YELLOW);
        
        JButton start = new JButton("Start");
        controlView.add(start);
      
        // display images side by side 
        Box imagePane = new Box(BoxLayout.LINE_AXIS);
        imagePane.add(paintView);
        imagePane.add(targetView);

        Box content = new Box(BoxLayout.PAGE_AXIS);
        content.add(imagePane);
        content.add(controlView);

        topPane.add(content);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Genetic Painter");
        setMinimumSize(minSize);
        pack();
        setVisible(true);
    }
    
    private void initGPainter() {
        Population pop = new Population();

        Individual best;
        int gen = 0;
        do {
            best = pop.generation[0];

            // calculate the fitness of each Individual
            //System.out.println("GENERATION " + gen);
            for (Individual candidate : pop.generation) {
                //paintView.img = candidate.img;
                //repaint();
                if (candidate.judgeFitness(targetView.img) < best.fitness) {
                    best = candidate;
                }
            }
            System.out.println("- GEN " + gen +": " + best.fitness);

            // show the best from the generation for a second
            paintView.img = best.img;
            repaint();

            //try {
            //    TimeUnit.SECONDS.sleep(1);
            //} catch(InterruptedException ie) {
            //    System.exit(1);
            //}

            // evolve!
            pop.evolve();

            gen++;
        } while (best.fitness > 1000);
    }
    
    public static void main(String[] args)
    {        
        Main window = new Main();
        window.initGPainter();        
    }
    
}
