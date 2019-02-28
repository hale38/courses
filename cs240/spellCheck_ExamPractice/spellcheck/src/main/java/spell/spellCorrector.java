package spell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Created by hale38 on 2/2/18.
 */

public class spellCorrector implements ISpellCorrector {
    public trie Dictionary = new trie();

    TreeSet<String>firstDistance;
    TreeSet<String>secondDistance;

    public spellCorrector(){
        Dictionary = new trie();
        firstDistance = new TreeSet<>();
        secondDistance = new TreeSet<>();
    }



    public Boolean chkEquals(spellCorrector spell)
    {
       return Dictionary.equals(spell.Dictionary);
    }


    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file =new File(dictionaryFileName);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext())
        {
            String word = scanner.next();
            word.toLowerCase();
            try {
                for (char c : word.toCharArray()) {
                    if (!Character.isAlphabetic(c)) {
                        throw new Exception();
                    }
                }
                Dictionary.add(word);
            }

            catch (Exception e)
            {
                continue;
            }
        }
        Dictionary.toString();
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord.toLowerCase();
        if (Dictionary.find(inputWord)==null)
        {

            buildFirstDistance(new StringBuilder(inputWord));
            int bestFrequency =0;
            ArrayList<String> bestWords = new ArrayList<>();

            for(String word: firstDistance)
            {
                ITrie.INode result = Dictionary.find(word);
                if (result!=null)
                {
                    if (result.getValue() > bestFrequency)
                    {
                        bestFrequency = result.getValue();
                        bestWords = new ArrayList<>();
                        bestWords.add(word);
                    }

                    else if (result.getValue() == bestFrequency)
                    {
                        bestWords.add(word);
                    }
                }
            }


            if (bestWords.size()==1)
            {
                return bestWords.get(0);
            }

            if (bestWords.size()>1) {
                java.util.Collections.sort(bestWords);
                return bestWords.get(0);
                //return alphabetically first letter
            }

            if (bestWords.size()==0)
            {
                for(String word : firstDistance)
                {
                    buildSecondDistance(new StringBuilder(word));
                }

                for(String word: secondDistance)
                {
                    ITrie.INode result = Dictionary.find(word);
                    if (result!=null)
                    {
                        if (result.getValue() > bestFrequency)
                        {
                            bestFrequency = result.getValue();
                            bestWords = new ArrayList<>();
                            bestWords.add(word);
                        }

                        else if (result.getValue() == bestFrequency)
                        {
                            bestWords.add(word);
                        }
                    }
                }

            }

            if (bestWords.size()==0) return null;
            if (bestWords.size()==1) return bestWords.get(0);
            if (bestWords.size()>1)
            {
                java.util.Collections.sort(bestWords);
                return bestWords.get(0);
            }

        }
        else
        {
            return inputWord.toLowerCase();
        }

        return null;
    }

    private void buildFirstDistance(StringBuilder word)
    {
        insertion(word,firstDistance);
        deletion(word,firstDistance);
        alteration(word,firstDistance);
        transposition(word,firstDistance);
    }

    private void buildSecondDistance(StringBuilder word)
    {
        insertion(word,secondDistance);
        deletion(word,secondDistance);
        alteration(word,secondDistance);
        transposition(word,secondDistance);
    }


    private void insertion (StringBuilder word, TreeSet<String>words)
    {
        for(int i =0 ; i < word.length()+1; i++)
        {
            for(int j=0; j < 26; j++)
            {
                StringBuilder temp = new StringBuilder(word);
                temp.insert(i, (char) (j + 'a'));

                if(temp.toString().length()>0) {
                    words.add(temp.toString());
                }
            }

        }
    }

    private void deletion (StringBuilder word, TreeSet<String>words)
    {
        for(int i = 0; i < word.length(); i++)
        {
            StringBuilder temp = new StringBuilder(word);
            temp.deleteCharAt(i);
            if(temp.toString().length()>0) {
                words.add(temp.toString());
            }
        }
    }

    private void alteration (StringBuilder word, TreeSet<String>words)
    {
        for(int i =0 ; i < word.length(); i++)
        {
            for(int j=0; j < 26; j++)
            {
                StringBuilder temp = new StringBuilder(word);
                temp.setCharAt(i, (char)(j + 'a'));
                if(temp.toString().length()>0) {
                    words.add(temp.toString());
                }
            }
        }
    }

    private void transposition (StringBuilder word, TreeSet<String>words)
    {
        for(int i =0 ; i < word.length()-1; i++)
        {
          StringBuilder temp = new StringBuilder(word);
            char c = temp.charAt(i);
            temp.setCharAt(i, temp.charAt(i+1));
            temp.setCharAt(i+1, c);
            if(temp.toString().length()>0) {
                words.add(temp.toString());
            }
        }

    }



}
