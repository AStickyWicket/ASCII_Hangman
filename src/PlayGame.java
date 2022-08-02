import java.util.Scanner;

public class PlayGame{
    public static void main(String[] args) {
        System.out.println("Hangman");

        Scanner input = new Scanner(System.in);
        System.out.println("Enter a difficulty from 1-3 (1 is easy, 3 is hard)");
        int difficulty = input.nextInt();
        if(difficulty == 1){
            System.out.println("You have chosen Easy");
        }
        else if(difficulty == 2){
            System.out.println("You have chosen Medium");
        }
        else if(difficulty == 3){
            System.out.println("You have chosen Hard");
        }
        else{
            System.out.println("You have entered an invalid difficulty");
        }

    }
}
