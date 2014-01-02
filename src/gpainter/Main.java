package gpainter;

import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import java.util.Random;

public class Main extends JFrame {

    //instance variables
    private ImagePanel paintView;
    private ImagePanel bestView;
    private ImagePanel targetView;
    private JPanel controlView;

    private Scanner sc;

    /**
      * Instantiate the frame to display the images and other information.
      */
    public Main() {
        sc = new Scanner(System.in);

        // calculate sizes
        Dimension minSize = new Dimension(ImagePanel.WIDTH * 3, ImagePanel.HEIGHT);
        Dimension panelSize = new Dimension(ImagePanel.WIDTH, ImagePanel.HEIGHT);

        // setup images
        Container topPane = getContentPane();
        
        paintView = new ImagePanel();
        //paintView.setBackground(Color.BLUE);
        paintView.setMinimumSize(panelSize);
        paintView.setPreferredSize(panelSize);

        bestView = new ImagePanel();
        bestView.setMinimumSize(panelSize);
        bestView.setPreferredSize(panelSize);

        targetView = new ImagePanel("images/shapes.png");
        targetView.setMinimumSize(panelSize);
        targetView.setPreferredSize(panelSize);

        // setup controls
        //controlView = new JPanel();
        //controlView.setBackground(Color.YELLOW);
        
        //JButton start = new JButton("Start");
        //controlView.add(start);
      
        // display images side by side 
        Box imagePane = new Box(BoxLayout.LINE_AXIS);
        imagePane.add(paintView);
        imagePane.add(bestView);
        imagePane.add(targetView);

        //Box content = new Box(BoxLayout.PAGE_AXIS);
        //content.add(imagePane);
        //content.add(controlView);

        topPane.add(imagePane);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Genetic Painter");
        setMinimumSize(minSize);
        pack();
        setVisible(true);
    }
    
    /**
     * Kickoff the Genetic Algorithm by setting up a Population and evolving
     * it until the desired fitness is reached.
     */
    private void initGPainter() {
        Population pop = new Population();

        Individual best;
        int gen = 0;
        do {
            best = pop.generation[0];
            int i = 0;
            // calculate the fitness of each Individual
            //System.out.println("GENERATION " + gen);
            for (Individual candidate : pop.generation) {
                paintView.img = candidate.img;
                repaint();

                if (candidate.judgeFitness(targetView.img) < best.fitness) {
                    best = candidate;
                }

                //debug
                //System.out.println("");
                //System.out.println("gen: " + gen + " candidate: " + i);
                //for (int j = 0; j < candidate.genes.length; j++) {
                //    System.out.println(""+ j +": "+ candidate.genes[j].size +" @ "+candidate.genes[j].x +", "+ candidate.genes[j].y);
                //}
                //System.out.println("fitness: " + candidate.fitness);
                //sc.nextLine();
                //i++;
            }
            System.out.println("- GEN " + gen +": " + best.fitness);

            // show the best from the generation for a second
            bestView.img = best.img;
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
        //Tester tester = new Tester();
        //tester.testFitness100();
    }
    
}
