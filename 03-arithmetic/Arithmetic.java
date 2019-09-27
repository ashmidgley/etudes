
package arithmetic;

import java.io.*;
import java.util.*;

/** takes sequence of scenarios as input from stdin.
 *  Each scenario is exactly 2 lines.
 *  The first line is the numbers to use.
 *  The second line consists of a target value, followed by a space, followed by an N or L
 *  character indicating whether normal order of operations, or left to right is to be
 *  assumed.
 *  Output for each scenario is the character representing the order used, followed by the
 *  completed equation, or impossible.
 */
public class Arithmetic{

    protected static List<Integer> values = new ArrayList<Integer>();
    protected static int result;
    protected static String type;
    protected static int stepsTaken = 0;
    protected static boolean[] op;
    protected static boolean keepRunning = true;

    public static void main(String[] args){
        String line = null;
        int lineNum=0;
        int valIndex=0;
        Scanner scan = new Scanner(System.in);
        String r;

        //while we have input from stdin
        while(scan.hasNextLine()){
            line = scan.nextLine();
            Scanner temp = new Scanner(line);
            if(lineNum == 1){
                result = temp.nextInt();
                type = temp.next();

                setDefaultOp();
             
                if(type.charAt(0) == 'N'){
                    int[]copy = new int[values.size()];
                    for(int i=0;i<values.size();i++){
                        copy[i] = values.get(i);
                    }
                    nTest(1, copy, op);
                }else{
                    lTest(1, values.get(0), op);
                }
                lineNum = 0;
                values.clear();
                keepRunning = true;
            }else{
                while(temp.hasNext()){
                    values.add(temp.nextInt());
                }
                lineNum++;
            }
        }
        
        //close input stream
        scan.close();
        
    }

    /** Runs the tests for the normal order of operation.
     *  Jumps to printImpossible() method if no solutions, or jumps to printEquation() if a solution is found. 
     */
    public static void nTest(int x, int[] c, boolean[] op){
        
        //gets to deepest (end) case in tree
        if(x == c.length){
            int count = 0;
            for(int i=0;i<c.length;i++){
                count += c[i];
            }
            if(count == result){
                printEquation(op);
                keepRunning = false;
                return;
            } else{
                return;
            }
        }
        
        //+ branch
        //dont have to set as op[i-1] is + by default
        op[x-1] = false;
        nTest(x+1,c, op);
        if(!keepRunning){
            return;
        }
        
        op[x-1] = true;

        int[] copy = c.clone();
        //alter mult values 
        copy[x] = copy[x-1] * copy[x];
        copy[x-1]=0;
        
        nTest(x+1, copy, op);
        if(!keepRunning){
            return;
        }

        //if we go down + branch and * branch and return back to index 1, we know it is impossible (all cases tryed)
        if(x == 1){
            printImpossible();
        }
    }

    /** Runs the tests for the left-to-right order of operation.
     *  Jumps to printImpossible() method if no solutions, or jumps to printEquation() if a solution is found. 
     */
    public static void lTest(int x, int r, boolean[] op){

        //gets to deepest (end) case
        if(x == values.size()){
            if(r == result){
                //we have found correct equation
                //we finish branching
                printEquation(op);
                keepRunning = false;
                return;
            }else{
                //just return from the branch
                return;
            }
        }
        //pre-checking
        if(r > result){
            return;
        }
        //+ branch
        //dont have to set as op[i-1] is + by default
        lTest(x+1, r + values.get(x), op);
        if(!keepRunning){
            return;
        }
        op[x-1] = true;
        lTest(x+1, r * values.get(x), op);
        if(!keepRunning){
            return;
        }
        
        //if we go down + branch and * branch and return back to index 1, we know it is impossible (all cases tryed)
        if(x==1){
            printImpossible();
        }
    }

    /** Fills the operand array with the default values at the size of the values list - 1.
     */
    public static void setDefaultOp(){
        op = new boolean[values.size()-1];
        for(int i=0;i<op.length;i++){
            op[i] = false;
        }
    }

    /** Prints out the type plus the equation that works for the values and result.
     */
    public static void printEquation(boolean[] b){
        String output = type+" ";
        for(int i=0;i<b.length;i++){
            if(b[i]){
                output += values.get(i)+" * ";
            }else{
                output += values.get(i)+" + ";
            }
        }
        output += values.get(values.size()-1);
        System.out.println(output);
    }

    /** Prints type of operation plus impossible.
     */
    public static void printImpossible(){
        System.out.println(type+" impossible");
    }
}
