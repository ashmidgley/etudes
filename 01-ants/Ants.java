
package ants;

import java.util.ArrayList;
import java.util.*;
import java.util.List;;
import java.io.*;
import java.awt.Point;

/** Class that implements a custom version of the Langton's Ant algorithm.
 *  Takes an input file containing multiple sequences as the first command line argument.
 *  The Ant moves across a map based on the sequence combinations.
 *  @author Ash Midgley, Rav Lal
 */
public class Ants{

    protected static List<DNASeq> seq = new ArrayList<DNASeq>();
    protected static HashMap<Point, Character> map = new HashMap<Point, Character>();
    protected static int steps=0;
    protected static int x=0;
    protected static int y=0; 

    /** Uses list of DNA sequences to find and implement move of ant.
     *  @param args command line arguments - unused.
     */
    public static void main(String[] args){
        String line = null;           
        try{
            //read file from first command line argument
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            while((line = bufferedReader.readLine()) != null){
                char c = ' ';
                if(!line.trim().isEmpty()){
                    c = line.charAt(0);
                }
                if(isLeadingDigit(c)){
                    steps = Integer.parseInt(line);
                    //deal with sequence and produce output
                    handleAnt();
                    //clear sequence list
                    seq.clear();
                    //clear map
                    map.clear();
                    //reset coords
                    x=0; y=0;
                    //reset steps
                    steps=0;
                    System.out.println();
                }else if(c != '#' && c != ' ' && line.length() == 11){
                    DNASeq dna = new DNASeq(line.substring(0,1),line.substring(2,6),line.substring(7,11));
                    seq.add(dna);
                }
            }
            //close file
            bufferedReader.close();

           
            
        }catch(IOException e){
            System.out.println("Error reading input!");
        }
    }

    /** Use this method to check if the line in the input file is the number of steps we will be performing.
     * @param c character at beginning of line in input file.
     * @return result true if character is a number and false otherwise.
     */
    public static boolean isLeadingDigit(char c){
        boolean result =  (c >= '0' && c <= '9'); 
        return result;
    }

    /** Uses list of DNA sequences to find and implement move of ant.
     */
    public static void handleAnt(){
        //first move
        //starts facing North at (0,0)
        char pDir = 'N';
        char cDir = seq.get(0).getCompassPos(0);

        //loops until we have completed number of steps
        for(int i=0;i<steps;i++){
                    
            //set table states
            Point coord = new Point(x,y);
            Point coordBeforeChange = coord;
            boolean newSQ = false;
                    
            //default state for square
            char state = seq.get(0).getState(); 
            //default state after move
            char stateAfter = seq.get(0).getNewState(0);

            //makes new square object
            Square sq = new Square(coord, state);

            if(map.containsKey(coord)){
                //get state that corresponds with the coord value already in table
                state = map.get(coord);
            }else{
                //set new entry to default and add a new square object
                map.put(coord, state);
                newSQ = true;
            }
                    
            sq.setState(state);
                    
            //find index of sequence in list we are dealing with based on state
            int seqNum = 0;
            for(int k=0;k<seq.size();k++){
                if(seq.get(k).getState() == state){
                    seqNum = k;
                }
            }
            //if not first step we find current direction based on sequence and previous direction
            if(i!=0){
                for(int m=0;m<seq.size();m++){
                    if(pDir == 'N'){
                        cDir = seq.get(seqNum).getCompassPos(0);
                        stateAfter = seq.get(seqNum).getNewState(0);
                    }else if(pDir == 'E'){
                        cDir = seq.get(seqNum).getCompassPos(1);
                        stateAfter = seq.get(seqNum).getNewState(1);
                    }else if(pDir == 'S'){
                        cDir = seq.get(seqNum).getCompassPos(2);
                        stateAfter = seq.get(seqNum).getNewState(2);
                    }else if(pDir == 'W'){
                        cDir = seq.get(seqNum).getCompassPos(3);
                        stateAfter = seq.get(seqNum).getNewState(3);
                    }
                }
            }
            //Direction combinations
            if(cDir == 'N'){
                y++;
                sq.flipState(stateAfter);
                pDir = 'N';
            }else if(cDir == 'E') {
                x++;
                sq.flipState(stateAfter);
                pDir = 'E';
            }else if(cDir == 'S') {
                y--;
                sq.flipState(stateAfter);
                pDir = 'S';
            }else if(cDir == 'W') {
                x--;
                sq.flipState(stateAfter);
                pDir = 'W';
            }
            //change state of square in hash map
            map.put(coordBeforeChange, sq.getState());
        }

        for(int l=0;l<seq.size();l++){
            seq.get(l).printSeq();
        }
        System.out.println(steps);
        System.out.println("# "+x +" "+y);
    }
}
