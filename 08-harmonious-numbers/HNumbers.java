package hnumbers;

import java.util.*;

public class HNumbers{

    private int n;

    public HNumbers(int n){
        this.n = n;
    }

    public void findHNumbers(){
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        
        for(int i=2; i < n; i++){
            int a = sumOfDivisors(i);
            int b = sumOfDivisors(a);
            if(b == i && !map.containsKey(a)){
                System.out.println(i+" "+a);
                map.put(i, a);
            }
        }
    }

    public int sumOfDivisors(int val){
        int count = 0;
        for(int i=2; i < Math.sqrt(val); i++){
            if(val%i == 0){
                count += i;
                int divisorPair = val/i; 
                if(val % divisorPair == 0){
                    count += divisorPair;
                }
            }
        }
        return count;
    }

    public boolean divisorValuesEqual(int a, int b){
        return (a == b && a != 0 && b != 0);
    }
}
