package evilHangman;

import java.io.File;
import java.util.Scanner;

/**
 * Created by hale38 on 1/26/18.
 */

public class main {


    private static Boolean checkGuess(String word)
    {
        if (word.length()==0 || word.length()>1) return false;
        else if (!(Character.isAlphabetic(word.toCharArray()[0]))) return false;
        else return true;
    }


    public static void main(String[] args) {
        int guesses = new Integer(args[2]);


        EvilHangmanGame game = new EvilHangmanGame(new Integer(args[1]), new Integer(args[2]));
        game.startGame(new File(args[0]), new Integer(args[1]));

        String status = new String();
        while (game.numGuesses > 0) {
            System.out.println();
            System.out.println("You have " + game.numGuesses + " left");
            System.out.println("Used letters " + game.getGuessed());
            System.out.println("Word: " + game.getWord());
            System.out.print("Enter a guess:");
            Scanner scan = new Scanner(System.in);


            String guess = scan.nextLine();


            if (checkGuess(guess)){
                guess.toLowerCase();
                try {
                    game.makeGuess(guess.toCharArray()[0]);
                }
                catch ( IEvilHangmanGame.GuessAlreadyMadeException e)
                {
                    System.out.println("Letter already guessed");
                }

            }
            else {
                System.out.println("Invalid input");
            }


            switch (game.checkStaus(guess.toCharArray()[0])){
                case 0 : status = new String("You Lost");
                    System.out.println(status);
                    System.out.println("The word was: " +game.getLosingWord());
                    continue;
                case 1: status = new String("You Won!");
                    System.out.println(status);
                    System.out.println("The word was: " + game.getWord());
                    game.numGuesses = 0;
                    continue;
                case 2: status = new String("Sorry, there was no " + guess);
                    System.out.println(status);
                    break;

                case 3: status = new String("Yes, there is " + game.getCorrectguess(guess.toCharArray()[0]) + ' ' + guess);
                    System.out.println(status);
                    break;
            }





        }
    }

}
