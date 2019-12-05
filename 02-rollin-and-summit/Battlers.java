package rollin;

import java.util.*;

/** Class that implements abstract class Rollin.java.
 *  Rolls a set of 6 dice with values between 1-6.
 *  Continues rolling 1 extra die until the set has 2 complete sets.
 *  A set can be either 3 die with the same value or a sequence of 3 values.
 *  @author Ash Midgley, Nick Piercy, Rav Lal
 */
public class Battlers extends Rollin{

    protected static Random random = new Random();
    protected static int[] set = new int[6];
    //copy array
    protected static int turns=0;

    /** Constructor that calls the super class constructor.
     *  @param a original set of dice.
     */
    public Battlers(int[] a){
	super(a);
    }

    /** Main method.
     *  Initializes set of dice with pseudo-random values between 1 and 6.
     *  Passes set into Battlers object.
     *  While the set is not  made up of 2 complete sets, we repeatedly call
     *  the handleRoll method with a new die.
     *  @param args command line arguments (not used).
     */
    public static void main(String[] args){
        
	for(int i=0;i<set.length;i++){
	    int roll = random.nextInt(6)+1;
	    set[i] = roll;
	}
	
	Battlers b = new Battlers(set);

	int roll = random.nextInt(6)+1;
	b.handleRoll(roll);

        System.out.println("COMPLETED SEQUENCE");
	System.out.println("Turns taken: "+turns);
	System.out.println("\nFinished Set:");
	for(int z=0;z<set.length;z++){
	    System.out.print(set[z]+" ");
	}
	System.out.println();
    }

    /** Method that takes a new die roll and assesses whether it should be
     *  substituted into the set of dice and if so where it should be inserted.
     *  Uses support methods insertionSort, isSet2 and handleNotSet.
     *  @param roll random die roll with value between 1 and 6.
     *  @return result index that roll value will be substituted into set or -1
     *  if it will not be substituted.
     */
    public int handleRoll(int roll){
	//copy 'set' array into 'copy'
	//every time we make a change to set, do it to copy instead
	while(!isComplete()){
	    System.out.println("-----New Roll-----\n");
	    List<Integer> notSet = new ArrayList<Integer>();
	    int[] index = new int[6];

	    System.out.println("Original set:");
	    for(int i: set){
		System.out.print(i+" ");
	    }
	    System.out.println();

	    //runs insertion sort on set
	    insertionSort(set);

	    System.out.println("\nSet after sorting:");
	    for(int i: set){
		System.out.print(i+" ");
	    }
	    System.out.println("\n");

	    //initializes flag values
	    for(int k = 0; k<index.length;k++){
		index[k] = 0;
	    }

	    //Checks set for sequences
	    System.out.println("Splits:");
	    for(int i=0;i<4;i++){
		int[]temp = new int[3];
		System.arraycopy(set, i, temp, 0, 3);
		for(int j: temp){
		    System.out.print(j+" ");
		}
		System.out.println();

		if(isSet2(temp)){
		    System.out.println("Sequence begins at index: "+i);
		    index[i] = 1;
		    index[i+1] = 1;
		    index[i+2] = 1;
		    //breaks if a sequence is found
		    break;
		}
	    }

	    //fills not set list with values that are not part of a set
	    for(int g=0;g<set.length;g++){
		if(index[g] == 0){
		    notSet.add(set[g]);
		}
	    }

	    System.out.println("\nNot set list:");
	    for(int f=0;f<notSet.size();f++){
		System.out.print(notSet.get(f)+" ");
	    }
	    System.out.println();
	
	    System.out.println("\nRoll: "+roll);

	    //returns index to change OR -1
	    int result = handleNotSet(notSet, roll);

	    //if index != -1 it swaps into given index in original set
	    boolean swap = false;
	    if(result != -1){
		for(int y=0;y<index.length;y++){
		    if(index[y] == 0 && !swap){
			set[y+result] = roll;
			System.out.println("Swapped roll into set");
			swap = true;
		    }
		}
	    }
	
	    System.out.println("\nNew Set List");
	    for(int t=0;t<set.length;t++){
		System.out.print(set[t]+" ");
	    }
	    System.out.println();
	    turns++;
	    roll = random.nextInt(6)+1;
	}

	//copy 'copy' back into 'set'
	
        //returns index
	return -1;
    }

    /** Sorts an input array using insertion sort.
     *  @param a array that will be sorted.
     */
    public static void insertionSort(int[] a){
        int j;
	int key;
	int i;
	for(j=1;j<a.length;j++){
	    key = a[j];
	    for(i = j-1;(i>=0) && (a[i]> key);i--){
		a[i+1] = a[i];
	    }
	    a[i+1] = key;
	}
    }

    /** Determine whether the dice in a set form a set.
     *  @param i the set of 3 values
     *  @return true if the dice at those indices form a set, false otherwise.
     */
    public static boolean isSet2(int[] i) {
        int a = i[0];
        int b = i[1];
        int c = i[2];

        if (a == b && b == c) {
            return true;
        }

        if (a == b || a == c || b == c) {
            return false;
        }
        
        int max = Math.max(a, Math.max(b, c));
        int min = Math.min(a, Math.min(b, c));
        return max - min == 2;
    }

    /** Determines the best index to substitute the roll value into out of the
     *  remaining values that do not make up a set.
     *  @param i list of values that do not make up a set.
     *  @param roll new die roll value between 1 and 6.
     *  @return index index that handleRoll uses to determine which index to
     *  substitute for roll.
     */
    public static int handleNotSet(List<Integer> i, int roll){
	int size = i.size();
	int index = -1;

        //size is 3
	if(size == 3){
	    int a = i.get(0);
	    int b = i.get(1);
	    int c = i.get(2);

	    //doubles check
	    if(a == b && roll == a){
		index = 2;
	    }else if(b == c && roll == b){
		index = 0;
	    }else if(a == c && a == roll){
		index = 1;

            //sequences check
	    }else if(a + 1 == b && b + 1 == roll){
		index = 2;
	    }else if(roll+1 == b && b+1 == c){
		index = 0;
	    }else if(b+2 == c && b+1 == roll){
		index = 0;
	    }else if(a+2 == b && a+1 ==roll){
		index = 2;
	    }else if(b+1 == c && b+2 == roll){
		index = 0;
	    }else if(a+1 == b && roll == a-1){
		index = 2;

            //other cases check
	    }else if(a+2 == b && b+2 == c){
                index = 1;
            }else if(a+2 == b && b+3 == c){
                index = 2;
            }else if(a+3 == b && b+2 == c){
                index = 0;
            }

	    return index;
	}else{
	    //Size is 6
            index = random.nextInt(3);
	    return index;
	}
    }  
}