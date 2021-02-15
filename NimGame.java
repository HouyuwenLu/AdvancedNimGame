/**
 * The university of Melbourne
 * School of Computing and Information Systems
 * COMP90041 Programming and Software Development
 * Lecturer: Dr Tilman Dingler, Dr Thuan Pham
 * Semester 2, October 15th, 2020
 * Project 1, The Game of Nim
 * Student Name: Houyuwen Lu
 * Student Number: 1205947
 * Github repository link: https://github.com/COMP90041/assignment-2-HouyuwenLu-COMP90041.git
 * Main class of the game of Nim and will invoke NimPlayer class
 * @author Houyuwen Lu
 */
public class NimGame {
    private int upperBound;
    private int totalStones;
    private NimPlayer player1;
    private NimPlayer player2;


    public NimGame(int upperBound, int totalStones, NimPlayer player1, NimPlayer player2) {
        this.upperBound = upperBound;
        this.totalStones = totalStones;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void playGame() {
        // play game
        System.out.println("");
        System.out.println("Initial stone count: " + totalStones);
        System.out.println("Maximum stone removal: " + upperBound);
        System.out.printf("Player 1: %s %s\n", player1.getFirstName(), player1.getLastName());
        System.out.printf("Player 2: %s %s\n", player2.getFirstName(), player2.getLastName());

        String currName = player1.getFirstName();
        NimPlayer currPlayer = player1;
        int round = 0;
        int move;
        while (totalStones > 0) {
            currName = currPlayer.getFirstName();
            move = currPlayer.removeStone(currName, upperBound, totalStones);
            totalStones -= move;
            round += 1;

            if (round % 2 == 1) {
                currPlayer = player2;
            } else {
                currPlayer = player1;
            }
            if (totalStones == 0) {
                break;
            }
        }
        currPlayer.updateWins(); // invoke updateWins method from NimPlayer class
        player1.updateGames(); // invoke updateGames method from NimPlayer class
        player2.updateGames(); // invoke updateGames method from NimPlayer class
        System.out.println("");
        System.out.println("Game Over\n" + currPlayer.getFirstName() + " " + currPlayer.getLastName() + " wins!");
    }

}

