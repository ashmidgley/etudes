
import java.awt.*;
import javax.swing.*;

public class Main{

    protected static int canvas = 1000;

    public static void main(String[] args){
        int n;
        float r;
        if(args.length>0){
            JFrame f = new JFrame();

            //number of generations
            n = Integer.parseInt(args[0]);
            
            if(args.length>1){
                //ratio of toothpicks
                r = Float.parseFloat(args[1]);
                Toothpicks t = new Toothpicks(n, canvas, r);
            }else{
                Toothpicks t = new Toothpicks(n, canvas);
            }
            
            f.getContentPane().add(new Toothpicks());
            f.setSize(canvas, canvas);
            f.setVisible(true);
        }else{
            System.out.println("Please enter n (number of generations) as first command line argument.");
            System.out.println("OPTIONAL: Enter r (ratio) as second command line argument.");
        }
    }
}
