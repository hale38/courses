package evilHangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by hale38 on 1/26/18.
 */

public class EvilHangmanGame implements IEvilHangmanGame{

    private TreeSet <String> words;
    private TreeSet <Character> guessedChar = new TreeSet<>();
    private HashMap<String,TreeSet<String>>wordBase;

    public Boolean won = new Boolean(false);

    int numGuesses;
    int wordLength;
    StringBuilder currentWord;

    public EvilHangmanGame(){
      //  this.numGuesses=new Integer(0);




    };

    public EvilHangmanGame(int wordLength, int numGuesses){
        words=new TreeSet<>();
        this.numGuesses=numGuesses;


    }


    @Override
    public void startGame(File dictionary, int wordLength) {
        try {
            words=new TreeSet<>();

            Scanner scan = new Scanner(dictionary);
            while (scan.hasNext()){
                   String next = scan.next();
                    try {
                        for (char c : next.toCharArray()) {
                            if (!Character.isAlphabetic(c)) {
                                throw new Exception("Bad Word");
                            }
                        }
                    }catch ( Exception e){
                        continue;
                    }

                if (next.length()==wordLength)
                {
                    words.add(next.toLowerCase());
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        currentWord =new StringBuilder();
        for (int i =0; i< wordLength; i++)
        {
            currentWord.append('-');
        }


        // System.out.println("next");
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        guess = Character.toLowerCase(guess);
        if (guessedChar.contains(guess)) throw new GuessAlreadyMadeException();
        guessedChar.add(guess);
        partition(guess);
        return words;
    }

    private void partition(char guess) // breaks a set up into a hash map containing a key to a set of words
    {
        wordBase = new HashMap<>();
        for (String word : words){
            StringBuilder tempBuilder = new StringBuilder(currentWord);

            for (int i = 0; i< word.length(); i++ )
            {
                if (guess == word.toCharArray()[i])
                {
                    tempBuilder.setCharAt(i,guess);
                }
            } //end of making new key

            if(wordBase.containsKey(tempBuilder.toString()))
            {
                wordBase.get(tempBuilder.toString()).add(word);
            }

            else {
                TreeSet<String> set = new TreeSet<>();
                set.add(word);
                wordBase.put(tempBuilder.toString(),set);
            }
           // System.out.println("Done");
        }//end of iterating through each word
        //bestSet(wordBase,guess);
        words = new TreeSet<>(bestSet(wordBase,guess));
    }

    private TreeSet<String> bestSet(HashMap inputData, char guess)
    {
        HashMap<String,TreeSet<String>> data = (HashMap)inputData;
        int bestSize=0;
        ArrayList<String>bestKeys = new ArrayList<>();
        for (String key : data.keySet())
        {
           if(data.get(key).size()>bestSize)
           {
               bestSize=data.get(key).size();
               bestKeys=new ArrayList<>();
               bestKeys.add(key);
           }

            else if(data.get(key).size()==bestSize)
            {
                bestKeys.add(key);
            }
        }
        if (bestKeys.size()==1)
        {
            if (contains(bestKeys.get(0),guess)==0)
            {
                numGuesses = new Integer(numGuesses-1);
            }
            currentWord = new StringBuilder(bestKeys.get(0));
            return data.get(bestKeys.get(0));
        }//returns largest set if only one exist

        int lowestFrequency = contains(bestKeys.get(0),guess);
        //(bestKeys.get(0),guess);
        ArrayList<String>lowestFreqkeys = new ArrayList<>();

        for (String key: bestKeys){
            if (contains(key,guess)==0)
            {
                numGuesses = new Integer(numGuesses-1);
                return data.get(key);
            } //returns largest set that DOES NOT have the new letter
            else if(contains(key,guess)<lowestFrequency)
            {
                lowestFreqkeys= new ArrayList<>();
                lowestFreqkeys.add(key);
                lowestFrequency=contains(key,guess);
            }
            else if (contains(key,guess)==lowestFrequency)
            {
                lowestFreqkeys.add(key);
            }
        }

        if (lowestFreqkeys.size()==1){
            if(contains(lowestFreqkeys.get(0),guess)==0){
                numGuesses =new Integer(numGuesses-1);
            }
            currentWord = new StringBuilder(lowestFreqkeys.get(0));
            return data.get(lowestFreqkeys.get(0));

        }
        else {
            String bestKey = new String (rightMost(lowestFreqkeys,guess));
            if(contains(bestKey,guess)>0){
                currentWord = new StringBuilder(bestKey);
            }
            else{
                numGuesses = new Integer(numGuesses-1);
            }
            return data.get(bestKey);
        }

        //return data.get(b)

    }

    public String getGuesses()
    {
        return guessedChar.toString();
    }


    private String rightMost(ArrayList<String> strings, char guess)
    {
        int keySize = strings.get(0).length()-1;
        ArrayList<String>tempArray = new ArrayList<>(strings);
        ArrayList<String>results = new ArrayList<>();

        for(int i = keySize; i >0; i--)
        {

            for (String word : tempArray) {
                if (word.toCharArray()[i] == guess) {
                        results.add(word);
                    }
                }

            if (results.size()==1)
            {
                currentWord = new StringBuilder(results.get(0));
                return results.get(0);
            }

            if (results.size()!=0)
            {
                tempArray = new ArrayList<>(results);
                results=new ArrayList<>();
            }

        }
        currentWord = new StringBuilder(results.get(0));
        return results.get(0);
    }


    private int contains (String word, Character c)
    {
        int frequency =0;
        for(int i =0; i<word.length(); i++)
        {
            if(word.charAt(i)==c)
            {
                frequency++;
            }
        }
        return frequency;
    }



 public int checkStaus(char guess)
 {
     if (numGuesses ==0 ){
         return 0;
     }//game lost
     else if (contains(currentWord.toString(),'-') == 0)
     {
         return 1;
     }//game one
     else if (contains(currentWord.toString(),guess)>0) {
         return 3;
     }//correct guess

     else return 2;
     //inco
 }

    public String getLosingWord()
    {
        return words.first();
    }


public int getCorrectguess(char g){
    return contains(currentWord.toString(), g );
}


    public String getWord()
    {
        return currentWord.toString();
    }

    public String getGuessed()
    {
        return guessedChar.toString();
    }



}
