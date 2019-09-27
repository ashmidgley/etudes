
import java.util.*;
import java.io.*;

public class PAndP{

    public static boolean[][] grid = new boolean[1000][1000];
    public static List<Integer> pretzel = new ArrayList<Integer>();
    public static List<Integer> peanut = new ArrayList<Integer>();
    public static int pretz;
    public static int pea;

    public static void main(String[] args){
	Scanner scan = new Scanner(System.in);
	int lNum = 1;
	while(scan.hasNextLine()){
	    String line = scan.nextLine();
	    Scanner sc = new Scanner(line);
	    if(lNum == 1){
                pea = Integer.parseInt(sc.next());
		pretz = Integer.parseInt(sc.next());
	    }else{
                String peanutMoves = sc.next();
                String pretzelMoves = sc.next();
                addAllMoves(peanutMoves, pretzelMoves);
	    }
	    lNum++;
	}
        //add in default moves
        peanut.add(1);
        pretzel.add(0);
        peanut.add(0);
        pretzel.add(1);

        printValues();
        fillGrid();
        printGrid();
        printOutput();
    }

    public static void fillGrid(){
        for(int i=0; i<=pretz; i++){
	    for(int j=0; j<=pea; j++){
		//add in true false values for combinations of moves in grid
		for(int y=0;y<peanut.size();y++){
                    //check if combo fits in grid
                    int rPretz = i - pretzel.get(y);
                    int rPea = j - peanut.get(y);
                    if(rPretz > -1 && rPea > -1){
                        boolean b = grid[rPretz][rPea];
                        if(!b){
                            grid[i][j] = true;
                        }
                    }
                }
	    }
	}
    }
    
    public static void addAllMoves(String a, String b){
        char chA = a.charAt(0);
        char chB = b.charAt(0);
        if(chA == '<' && Character.isDigit(chB)){
            //EG: <4 3
            int n = Integer.parseInt(a.substring(1));
            int[] moves = lessValues(n);
            
            for(int i=0;i<moves.length;i++){
                peanut.add(moves[i]);
                pretzel.add(Integer.parseInt(b));
            }
        }else if(chA == '>' && Character.isDigit(chB)){
            //EG: >4 3
            int n = Integer.parseInt(a.substring(1));
            int max = pea;
            int[] moves = greaterValues(n, max);
            
            for(int i=0;i<moves.length;i++){
                peanut.add(moves[i]);
                pretzel.add(Integer.parseInt(b));
            }
        }else if(Character.isDigit(chA) && chB == '<'){
            //EG: 4 <3
            int n = Integer.parseInt(b.substring(1));
            int[] moves = lessValues(n);
            
            for(int i=0;i<moves.length;i++){
                peanut.add(Integer.parseInt(a));
                pretzel.add(moves[i]);
            }
        }else if(Character.isDigit(chA) && chB == '>'){
            //EG: 4 >3
            int n = Integer.parseInt(b.substring(1));
            int max = pretz;
            int[] moves = greaterValues(n, max);
            
            for(int i=0;i<moves.length;i++){
                peanut.add(Integer.parseInt(a));
                pretzel.add(moves[i]);
            }
        }else if(chA == '>' && chB == '>'){
            //EG >2 >2
            int n = Integer.parseInt(a.substring(1));
            int max = pea;
            int[] nutMoves = greaterValues(n, max);
            
            n = Integer.parseInt(b.substring(1));
            max = pretz;
            int[] pretzMoves = greaterValues(n, max);

            for(int i=0;i<nutMoves.length;i++){
                peanut.add(nutMoves[i]);
                pretzel.add(pretzMoves[i]);
            }
        }else if(chA == '>' && chB == '<'){
            //EG >2 <2
            int n = Integer.parseInt(a.substring(1));
            int max = pea;
            int[] nutMoves = greaterValues(n, max);
            
            n = Integer.parseInt(b.substring(1));
            int[] pretzMoves = lessValues(n);

            for(int i=0;i<nutMoves.length;i++){
                peanut.add(nutMoves[i]);
                pretzel.add(pretzMoves[i]);
            }
        }else if(chA == '<' && chB == '>'){
            //EG <2 >2
            int n = Integer.parseInt(a.substring(1));
            int[] nutMoves = lessValues(n);

            n = Integer.parseInt(b.substring(1));
            int max = pretz;
            int[] pretzMoves = greaterValues(n, max);

            for(int i=0;i<nutMoves.length;i++){
                peanut.add(nutMoves[i]);
                pretzel.add(pretzMoves[i]);
            }
            
        }else if(chA == '<' && chB == '<'){
            //<2 <2
            int n = Integer.parseInt(a.substring(1));
            int[] nutMoves = lessValues(n);

            n = Integer.parseInt(a.substring(1));
            int[] pretzMoves = lessValues(n);
            
            for(int i=0;i<nutMoves.length;i++){
                peanut.add(nutMoves[i]);
                pretzel.add(pretzMoves[i]);
            }
        }else{
            //EG: 2 2
            peanut.add(Integer.parseInt(a));
            pretzel.add(Integer.parseInt(b));
        }
    }

    public static int[] lessValues(int n){
        int[] result = new int[n];
        int val = 0;
        for(int i=0;i<result.length;i++){
            result[i] = i;
        }
        return result;
    }

    public static int[] greaterValues(int n, int max){
        int[] result = new int[max-n];
        int val = n+1;
        for(int i=0;i<result.length;i++){
            result[i] = val;
            val++;
        }
        return result;
    }
    
    public static void printOutput(){
        if(!grid[pretz][pea]){
            System.out.println("Output: 0 0");
        }else{
            for(int y=0;y<peanut.size();y++){
                int pretzMove = peanut.size() - peanut.get(y);
                int nutMove = pretzel.size() - pretzel.get(y);
                if(!grid[pretzMove][nutMove]){
                    System.out.println("Output: "+peanut.get(y)+" "+pretzel.get(y));
                    break;
                }
            }
        }
    }

    public static void printValues(){
        System.out.println("Peanuts: "+pea);
	System.out.println("Pretzels: "+pretz);
	System.out.println("\nCombo's");
	for(int i=0; i<peanut.size();i++){
	    System.out.print(peanut.get(i)+" "+pretzel.get(i)+"\n");
	}

    }
    
    public static void printGrid(){
        System.out.println("\nGrid");
	for(int i=0; i<=pretz; i++){
	    for(int j=0; j<=pea; j++){
		System.out.print(grid[i][j]+" ");
	    }
            System.out.println();
	}
	System.out.println();
    }
}
