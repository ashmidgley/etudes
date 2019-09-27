
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
    
import javax.swing.*; //frame and panel
import java.util.*;

public class Toothpicks extends JPanel{

    private static int n;
    private static float r = 1;
    protected static float halfTooth;
    protected static int canvas;
    protected static int start = 1;
    
    public Toothpicks(){}
    
    public Toothpicks(int n, int canvas){
        this.n = n;
        this.canvas = canvas;
        float defSize = (canvas/2)-(canvas/5);
        if(n > 1){
            int m=1;
            for(int i=0;i<n;i++){
                if(i%3 == 0){
                    m++;
                }
            }
            this.halfTooth = (defSize/m);
        }else{
            this.halfTooth = defSize;
        }
        System.out.println("Default size: "+halfTooth);
    }

    public Toothpicks(int n, int canvas, float r){
        this.n = n;
        this.canvas = canvas;
        this.r = r;
	float def = canvas/5;

        float size = findDefaultSize(def, 1);
        this.halfTooth = size;
    }
    
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        //set first line based on ratio (r)
        int mid = canvas/2;
        int[] def = {Math.round(mid-halfTooth), mid, Math.round(mid+halfTooth), mid};

        //Initial line for generation 0
        if(start == 1){
            g2.drawLine(def[0], def[1], def[2], def[3]);
        }
        
        if(n > 0 && start < n){
            drawToothpicks(g2, def, start);
        }
    }

    //Finds the default size for the first toothpick based on the r so
    //the n generations fit into the canvas
    public static float findDefaultSize(float def, int s){
        while(s<n){
	    if(sizeFitsCanvas(def, s)){
                start = s;
		break;
	    }else if(def == 1){
                //If we reach the min default size, move the start point forward through the n cases
                s+=1;
                System.out.print(s+" ");
                if(s < n){
                    start = s;
                }
            }else if(def > 1){
                def--;
            }
        }
        if(start > 1){
            System.out.println();
        }
        System.out.println("Default size: "+def);
        System.out.println("Starting generation: "+start);
        return def;
    }

    //checks to see if the toothpicks will fit in the canvas for a given
    // default size and the number of generations
    public static boolean sizeFitsCanvas(float def, int s){
        float xmax=def;
        float ymax=0;
        float maxC = (canvas/2)-(canvas/10);
        int origS = s;
        //find xmax and ymax values for given r and n
        while(s != n+1){
            float scale=r;
            for(int j=origS; j<s;j++){
                scale*=r;
            }
	    float adjust = def*scale;
            if(s%2 == 1){
                //vertical
		ymax += adjust;
            }else{
                //horizontal
		xmax += adjust; 
            }
            s++;
        }
        if(xmax < maxC && ymax < maxC){
            return true;
        }
        return false;
    }
    
    public void drawToothpicks(Graphics page, int[] i, int s){
        //stopping case
        if(s == this.n+1){
            return;
        }else{
            int x1 = i[0];
            int y1 = i[1];
            int x2 = i[2];
            int y2 = i[3];

            int[] i2 = new int[4];

            float scale=r;
            for(int j=start; j<s;j++){
                scale*=r;
            }

            int adjust = Math.round(halfTooth*scale);
            
            //1st, 3rd, 5th generation etc.
            if(s % 2 == 1){
    
                //add new vertical lines
                page.drawLine(x1, y1-adjust, x1, y1+adjust);
                page.drawLine(x2, y2-adjust, x2, y2+adjust);
                
                //recall method on first newly added toothpick and decrease s
                i2[0] = x1;
                i2[1] = y1-adjust;
                i2[2] = x1;
                i2[3] = y1+adjust;
                drawToothpicks(page, i2, s+1);

                //recall method on second newly added toothpick and decrease s
                i2[0] = x2;
                i2[1] = y2-adjust;
                i2[2] = x2;
                i2[3] = y2+adjust;
                drawToothpicks(page, i2, s+1);
            }
            
            //2nd, 4th, 6th generation etc
            else if(s % 2 == 0){
                //add new horizontal lines
                page.drawLine(x1-adjust, y1, x1+adjust, y1);
                page.drawLine(x2-adjust, y2, x2+adjust, y2);
                
                //recall method on first newly added toothpick and decrease s
                i2[0] = x1-adjust;
                i2[1] = y1;
                i2[2] = x1+adjust;
                i2[3] = y1;
                drawToothpicks(page, i2, s+1);
                 
                //recall method on second newly added toothpick and decrease s
                i2[0] = x2-adjust;
                i2[1] = y2;
                i2[2] = x2+adjust;
                i2[3] = y2;
                drawToothpicks(page, i2, s+1);
            }
        }
    }
}
