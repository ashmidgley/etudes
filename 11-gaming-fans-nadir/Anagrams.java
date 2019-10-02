
import java.util.*;
import java.io.*;

public class Anagrams{

    static List<String> dic = new ArrayList<String>();
    static List<String> anagrams = new ArrayList<String>();
    static int n;
    static String str;
   
    public static void main(String[] args){
	if(args.length > 1){
	    str = args[0];
	    n = Integer.parseInt(args[1]);
	    try{
		InputStreamReader ir = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(ir);
                String line;
                while((line = br.readLine()) != null){
                    String clean = "";
                    for(int i=0;i<line.length();i++){
                        char ch = line.charAt(i);
                        if((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
                            clean += ch;
                        }
                    }
                    str = str.toLowerCase();
                    clean = clean.toLowerCase();
                    String cleanCheck = order(clean);
                    String strCheck = order(str);
                    
                    //check if dic entry in anagram string
                    if(within(cleanCheck, strCheck)){
                        dic.add(clean);
                    }
		}
		br.close();
            }catch(IOException e){
		e.printStackTrace();
	    }

            Collections.sort(dic, new Comparator<String>(){

                    @Override
                    public int compare(String s1, String s2){
                        return s2.length()-s1.length();
                    }
                });

            if(n > 1){
                permutation(dic);
            }else if(n == 1){
                singleWordAnagram();
            }else{
                System.err.println("n value must be greater than 0.");
            }


            //sort individual anagrams
            sortBasedOnWordlength();
            sortBasedOnAlphabetical();
            
            //sort collection of anagrams
            sortOnFirstWordLen();

            //sort single anagrams
            for(int i=0;i<anagrams.size();i++){
                int numWords = numberOfWords(anagrams.get(i));
                if(numWords == 1 && i != 0){
                    String s = anagrams.get(0);
                    s += " "+anagrams.get(i);
                    anagrams.set(0, s);
                    anagrams.remove(i);
                    i--;
                }else if(numWords > 1){
                    break;
                }
            }
            
            //print anagrams
            for(String s : anagrams){
                System.out.println(s);
            }
            
        }else{
	    System.err.println("Usage: java anagrams.Anagrams str n < dictionary.txt");
	    System.err.println("str: String we are using to make anagrams.");
	    System.err.println("n: Number of words we can use from the dictionary to "+
            "create anagrams.");
	}
    }

    public static void sortOnFirstWordLen(){
        //based on insertion sort
        for (int i=0; i<anagrams.size(); i++){
            for (int j=1; j<anagrams.size(); j++){ 
                int p=j;
                String s = anagrams.get(p);
                Scanner scan = new Scanner(s);
                String word = scan.next();
                scan = new Scanner(anagrams.get(p-1));
                String word2 = scan.next();
                while (p>0 && (word.length()>=word2.length())){
		     if(word.length() == word2.length()){
			 //dealWithAlphabetical(p, p-1);
		     }else{
			 anagrams = wordSwap(anagrams, p, p-1);
		     }
                    p--;
                } 
                p=j;
            }
        }
    }
    
    public static void dealWithAlphabetical(int index1, int index2){
	boolean swap = false;
        Scanner scan1 = new Scanner(anagrams.get(index1));
	Scanner scan2 = new Scanner(anagrams.get(index2));
	while(scan1.hasNext() && scan2.hasNext()){
	    String s1 = scan1.next();
	    String s2 = scan2.next();
	    if(isAlphabeticallyGreater(s1, s2)){
		anagrams = wordSwap(anagrams, index1, index2);
	    }
	}
    }
    
    public static void sortBasedOnWordlength(){
        //based on insertion sort
        for (int i=0; i<anagrams.size(); i++){
            String s = anagrams.get(i);
            List<String> wordList = new ArrayList<String>();
            Scanner scan = new Scanner(s);
            while(scan.hasNext()){
                wordList.add(scan.next());
            }
            for (int j=1; j<wordList.size(); j++){ 
                int p=j;
                while (p>0 && (wordList.get(p).length()>wordList.get(p-1).length())){ 
                    wordList = wordSwap(wordList, p, p-1);
                    p--;
                } 
                p=j;
            }
            String in = toString(wordList);
            anagrams.set(i,in);
        }
    }

    public static void sortBasedOnAlphabetical(){
        for (int i=0; i<anagrams.size(); i++){
            String s = anagrams.get(i);
            List<String> wordList = new ArrayList<String>();
            for(String word : s.split(" ")) {
                wordList.add(word);
            }
            for (int j=1; j<wordList.size(); j++){ 
                int p=j;
                while (p>0 && (wordList.get(p).length() == wordList.get(p-1).length())){
                    if(isAlphabeticallyGreater(wordList.get(p-1), wordList.get(p))){
                        wordList = wordSwap(wordList, p, p-1);
                    }
                    p--;
                } 
                p=j;
            }
            String in = toString(wordList);
            anagrams.set(i ,in);
        }
    }

    public static boolean isAlphabeticallyGreater(String a, String b){
        for(int i=0;i<a.length();i++){
            if(a.charAt(i) > b.charAt(i)){
                return true;
            }else if(a.charAt(i) < b.charAt(i)){
                return false;
            }
        }
        return false;
    }
    
    public static List<String> wordSwap(List<String> li, int i1, int i2){
        String s1 = li.get(i1);
        li.set(i1, li.get(i2));
        li.set(i2, s1);
        return li;
    }

    public static String toString(List<String> li){
        String result = "";
        for(String s : li){
            result += s+" ";
        }
        return result;
    }
    
    public static void permutation(List<String> li){
        permutation("", li);
    }

    //remove word from dic once we have used it
    private static void permutation(String perm, List<String> words){
        List<String> result = new ArrayList<String>();
        if(words.isEmpty()){
            String permTrimmed = removeSpaces(perm);
            String prev="";
            if(anagrams.size() > 0){
                prev = anagrams.get(anagrams.size()-1);
            }
            //System.out.println(permTrimmed);
            if(numberOfWords(perm) <= n && permTrimmed.length() == str.length() && within(permTrimmed, str) && !perm.equals(prev)){
               anagrams.add(perm);
            }
        }else{
            for(int i=0;i < words.size();i++){
                List<String> li = new ArrayList<String>();
                for(int j=0; j<=i;j++){
                    if(j!=i){
                        li.add(words.get(j));
                    }
                }
                String permT = removeSpaces(perm);
                if(permT.length() >= str.length()){
                    permutation(perm, new ArrayList<String>());
                }else{
                    permutation(perm + words.get(i)+" ", li);
                }
            }
        }
    }

    public static int numberOfWords(String s){
        Scanner scan = new Scanner(s);
        int count=0;
        while(scan.hasNext()){
            scan.next();
            count++;
        }
        return count;
    }

    public static String removeSpaces(String s){
        StringBuilder sb = new StringBuilder();
        Scanner scan = new Scanner(s);
        while(scan.hasNext()){
            sb.append(scan.next());
        }
        return sb.toString();
    }

    public static void singleWordAnagram(){
        for(String s: dic){
            if(s.length() == str.length() && !s.equals(str) && isAnagram(s, str)){
                anagrams.add(s);
            }
        }
    }
    
    //checking if all char in string a are within string b
    public static boolean within(String a, String b){
        a = order(a);
        b = order(b);
        if(a.length() > 1){
            int index=0;
            for(int i=0;i<b.length();i++){
                if((index+1) == a.length() && a.charAt(index) == b.charAt(i)){
                    index++;
                    break;
                }
                if(a.charAt(index) == b.charAt(i)){
                    index++;
                }
            
            }
            if(index == a.length()){
                return true;
            }
        }else{
            //for length 1 case
            for(int i=0; i<b.length();i++){
                if(a.charAt(0) == b.charAt(i)){
                    return true;
                }
            }
        }
        return false;
    }

    public static String difference(String a, String b){
        int index=0;
        StringBuilder sb = new StringBuilder();
        String aO = order(a);
        String bO = order(b);
        
        for(int i=0;i<bO.length();i++){
            if(index < aO.length() && aO.charAt(index) == bO.charAt(i)){
                index++;
            }else{
                sb.append(bO.charAt(i));
            }
        }
        index = 0;
        String result = "";
        for(int i=0; i<b.length();i++){
            char bCh = b.charAt(i);
            for(int j=0;j<sb.length();j++){
                char dCh = sb.charAt(j);
                if(dCh == bCh){
                    result += bCh;
                    sb.deleteCharAt(j);
                    break;
                }
            }
        }
        return result;
    }

    public static boolean isAnagram(String a, String b){
        if(a.length() != b.length()){
            return false;
        }
        a = a.toLowerCase();
        b = b.toLowerCase();
        char[] c1 = a.toCharArray();
        char[] c2 = b.toCharArray();
        Arrays.sort(c1);
        Arrays.sort(c2);
        String s1 = new String(c1);
        String s2 = new String(c2);
        return s1.equals(s2);
    }

    public static String order(String s){
        char[] sA= s.toCharArray();
        Arrays.sort(sA);
        String result = String.valueOf(sA);
        return result; 
    }

    public static void printStringList(List<String> li){
        for(String s : li){
            System.out.print(s+" ");
        }
        System.out.println();
    }
}
