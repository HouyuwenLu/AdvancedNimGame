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
 * NimPlayer class and invoked in Nimsys class
 * @author Houyuwen Lu
 */
import java.text.DecimalFormat;
public abstract class NimPlayer {
    // declare the name, wins, and games as instance variables in the class NimPlayer
    private String username;
    private String lastName;
    private String firstName;
    private int wins;
    private int games;

    // set multiple constructors with different parameters can be invoked by
    // different method in NimHumanPlayer class, NimAIPlayer class and Nimsys class

    public NimPlayer(String username, String lastName, String firstName) {
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
    }



    public NimPlayer(String username, String firstName, String lastName, int games, int wins) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.games = games;
        this.wins = wins;
    }



    public NimPlayer(NimPlayer player) {
        this.username = player.username;
        this.lastName = player.lastName;
        this.firstName = player.firstName;
        this.wins = player.wins;
        this.games = player.games;
    }

    public abstract int removeStone(String playerName, int upperBound, int totalStones);
    // will be defined later in NimHumanPlayer class and NimAIPlayer class

    public void reset() { // the method will be invoked in Nimsys class in resetstats method to set wins and games for each player to 0
        wins = 0;
        games = 0;
    }

    public void updateWins() {
        // update wins can be used in NimGame class
        this.wins += 1;
    }

    public void updateGames() {
        // update games can be used in NimGame class
        this.games += 1;
    }

    public void display() {
        // method display to display all players in the NimPlayer array and will be invoked by Nimsys class
        System.out.println(username + ", " + firstName + ", " + lastName + ", " + games + " games, " + wins + " wins");
    }

    public double calWinRate() {
        // the method will be invoked in Nimsys class to calculate the win rate of each player and get the rankings
        if(games == 0) {
            return 0;
        }
        return (double)wins/games;
    }

    public void rankingDisplay() {
        // format the display of the rankings
        DecimalFormat df1 = new DecimalFormat("###%"); // multiply by 100 and show as percentage
        DecimalFormat df2 = new DecimalFormat("00"); // 2 digits
        double rate = (double)Math.round(calWinRate());
        System.out.printf("%-5s| %s games | %s %s\n", df1.format(rate), df2.format(games), firstName, lastName);
    }

    // use getter and setter to get and set the username, lastname, firstname, games, and wins for each player
    public int getWins() {
        return wins;
    }

    public int getGames() {
        return games;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String writeData() { // will be used to IO file
        return username + "," + firstName + "," +
                lastName + "," + games + "," + wins;
    }

    public void outputStones(int stones) {
        // display stones left number and pattern output
        // will be invoked in removeStone method in both NimHumanPlayer class and NimAIPlayer class
        System.out.println("");
        System.out.print(stones + " stones left:");
        for (int i = 1; i <= stones; i++) {
            System.out.print(" *");
        }
        System.out.println();
    }

}



