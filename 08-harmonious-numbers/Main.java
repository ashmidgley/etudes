package hnumbers;

public class Main{

    public static void main(String[] args){
        if(args.length > 0){
            int n = Integer.parseInt(args[0]);
            HNumbers h = new HNumbers(n);
            h.findHNumbers();
        }else{
            System.out.println("Please enter stopping value for harmonious number combinations");
        }
    }
}
