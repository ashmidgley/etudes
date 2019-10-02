
import java.util.*;

public class CountingUp{
    
    public static void main(String[] args){
	if(args.length == 2){
	    long n = Long.parseLong(args[0]);
	    long k = Long.parseLong(args[1]);
	    long result = countUp(n, k);
	    System.out.println(result);
	}else{
	    System.err.println("Please enter n and k as command line arguments.");
	}
    }

    public static long countUp(long n, long k){
        //top of equation
	Long[] nfac = facList(n);

        //bottom of equation
	Long[] kfac = facList(k); 
	Long[] nlesskfac = facList(n-k);
        Long[] bottom = combineList(kfac, nlesskfac);

        //equation with all like terms cancelled out
        Long[] finalEquation = cancelLikeTerms(nfac, bottom);

        //multiply remaining terms
        long result = 1L;
        for(int i=1; i < finalEquation.length; i++){
            if(finalEquation[i] != null){
                result *= finalEquation[i];
            }
        }
        return result;
    }

    public static Long[] facList(long l){
        Long[] result = new Long[(int)l];
        int index=0;
        for(long i=l; i>0; i--){
            result[index] = i;
            index++;
        }
        return result;
    }

    public static Long[] combineList(Long[] a, Long[] b){
        Long[] result = new Long[a.length+b.length];
        int index=0;
        for(Long l : a){
            result[index] = l;
            index++;
        }
        for(Long l : b){
            result[index] = l;
            index++;
        }
        return result;
    }

    public static Long[] cancelLikeTerms(Long[] top, Long[] bot){
        //sort top and bottom of equation
        insertionSort(top);
        insertionSort(bot);

        //set same values and 1's to null
        for(int i=0; i<top.length; i++){
            long a = top[i];
            for(int j=0; j<bot.length; j++){
                if(bot[j]!=null){
                    long b = bot[j];
                    if(a == b){
                        top[i] = null;
                        bot[j] = null;
                        break;
                    }else if(b == 1){
                        bot[j] = null;
                        break;
                    }
                }
            }
        }
        
        //factor out remaining values that are not null
        for(int i=0; i<top.length; i++){
            if(top[i]!=null){
                long a = top[i];
                for(int j=0; j<bot.length; j++){
                    if(bot[j]!=null){
                        long b = bot[j];
                        if(a % b == 0){
                            top[i] = a/b;
                            bot[j] = null;
                            break;
                        }
                    }
                }
            }
        }
        
        //return remaining top list        
        return top;
    }

    public static void insertionSort(Long[] a){
        for(int i=1; i<a.length; i++){
            long temp = a[i];
            int j;
            for(j=i-1; j >= 0 && temp < a[j]; j--){
                a[j+1] = a[j];
            }
            a[j+1] = temp;
        }
    }
    
    public static void printList(Long[] li){
        for(long l : li){
            System.out.print(l+" ");
        }
        System.out.println();
    }
}
