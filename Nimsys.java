
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Nimsys {
    // set both private and public static variables of the Nimsys class
    private final char SYMBOL = '$';
    // scanner can be used in NimPlayer class to get input for stones removed
    public static Scanner scanner = new Scanner(System.in);
    // commands are grouped in a array and can be added or deleted from this array
    private final Command command = new Command();
    private final String[] COMMANDLISTS = command.getCommandLists();
    private NimPlayer[] nimPlayers = new NimPlayer[100]; // the game can store max 100 players so the NimPlayer can have 100 instances in the array
    private static int index = 0;
    private final static String fileName = "players.dat";// all the players' records will be put into the player.txt including human players and AI players

    public static void main(String[] args) {

        Nimsys nimsys = new Nimsys();
        System.out.println("");
        nimsys.welcome();
        nimsys.readFile(fileName);
        nimsys.commandInput();
    }

    public void welcome() {
        // set welcome method that can be used by invocation
        System.out.println("Welcome to Nim");
        System.out.println();
        System.out.println("Please enter a command to continue or type 'help' for more information");
        System.out.println();
    }

    public void commandInput() {
        // get command input to invoke the relative methods
        // use while loop when the command input is "exit", the program will be terminated
        boolean operation = true;
        while (operation) {
            System.out.print(SYMBOL + " ");
            String commandInput = scanner.nextLine(); // get command input
            commandInput = commandInput.replaceAll(",", ""); // get rid of the comma from input to replace with none
            // use split method in an array to get separate inputs from the same line input by one space between each word
            String[] subCommand = commandInput.split(" ");
            // try-throw-catch block to catch the exception from the command input that is not in the command lists
            try {
                if (subCommand[0].equals(COMMANDLISTS[0])) {
                    // no parameters for input exit
                    writeData(fileName);
                    operation = false;
                } else if (subCommand[0].equals(COMMANDLISTS[1])) {
                    addPlayer(subCommand[1], subCommand[2], subCommand[3]); //addplayer's username, family name, given name

                } else if (subCommand[0].equals(COMMANDLISTS[2])) {
                    addaiPlayer(subCommand[1], subCommand[2], subCommand[3]); //addaiplayer's username, family name, given name

                } else if (subCommand[0].equals(COMMANDLISTS[3])) {
                    // removeplayer [username]
                    if (subCommand.length == 1) {
                        removePlayer();
                    } else {
                        removePlayer(subCommand[1]);
                    }

                } else if (subCommand[0].equals(COMMANDLISTS[4])) {
                    // editplayer username, new_family_name, new_given_name
                    editPlayer(subCommand[1], subCommand[2], subCommand[3]);

                } else if (subCommand[0].equals(COMMANDLISTS[5])) {
                    // resetstats [username]
                    if (subCommand.length == 1) {
                        resetstats();
                    } else {
                        resetstats(subCommand[1]);
                    }

                } else if (subCommand[0].equals(COMMANDLISTS[6])) {
                    // displayplayer [username]
                    if (subCommand.length == 1) {
                        displayplayer();
                    } else {
                        displayplayer(subCommand[1]);
                    }

                } else if (subCommand[0].equals(COMMANDLISTS[7])) {
                    // rankings [asc|desc]
                    if (subCommand.length == 1) {
                        rankings();
                    } else {
                        rankings(subCommand[1]);
                    }

                } else if (subCommand[0].equals(COMMANDLISTS[8])) {
                    // startgame (initialstones, upperbound, username1, username2)
                    startGame(subCommand[1], subCommand[2], subCommand[3], subCommand[4]);

                } else if (subCommand[0].equals(COMMANDLISTS[9])) {
                    //startadvancedgame
                    startadvancedgame();

                } else if (subCommand[0].equals(COMMANDLISTS[10])) {
                    // display all the commands when input is command
                    commandList();

                } else if (subCommand[0].equals(COMMANDLISTS[11])) {
                    // display help message
                    help();

                } else {
                    // if the command input is not in the command lists,
                    // program will throw a message and output SYMBOL sign to get an existing command
                    throw new InvalidInputException(subCommand[0]);
                }
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Incorrect number of arguments supplied to command.");
            }
            System.out.println("");
        }
    }

    public void readFile(String fileName) {
        // check if the fileObject exists
        File fileObject = new File(fileName); // create a file named "player.txt"
        if (fileObject.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(fileName); // find the file and create new instance of FileInputStream
                BufferedReader bufferedRead = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedRead.readLine()) != null) {
                    String[] readData = line.split(",");
                    if (readData[5].equals("Human")) {
                        nimPlayers[index] = new NimHumanPlayer(readData[0], readData[1], readData[2],
                                Integer.parseInt(readData[3]), Integer.parseInt(readData[4]));
                    } else {
                        nimPlayers[index] = new NimAIPlayer(readData[0], readData[1], readData[2],
                                Integer.parseInt(readData[3]), Integer.parseInt(readData[4]));
                    }
                    index++;
                }
                inputStream.close();
                bufferedRead.close();
            } catch (FileNotFoundException e) {
                System.out.println("The file is not found.");
            } catch (IOException e) {
                System.out.println("The file cannot be opened.");
            }
        }
    }

    public void writeData(String fileName) {
        // try the file exists
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            for (int i = 0; i < index; i++) {
                bufferedWriter.write(nimPlayers[i].writeData());
            }
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("The file is not found.");
        } catch (IOException e) {
            System.out.println("No players display in the file");
        }
    }

    public void addPlayer(String username, String familyName, String givenName) {
        // set exist as the position of the existence of the player
        // if the player exists, the exist will return the position of the player
        int exist = findPlayer(username);
        if (exist != -1) {
            System.out.println("The player already exists.");
        } else {
            nimPlayers[index] = new NimHumanPlayer(username, familyName, givenName);
            index++;
        }
    }

    public void addaiPlayer(String username, String familyName, String givenName) {
        // set exist as the position of the existence of the player
        // if the player exists, the exist will return the position of the player
        int exist = findPlayer(username);
        if (exist != -1) {
            System.out.println("The player already exists.");
        } else {
            nimPlayers[index] = new NimAIPlayer(username, familyName, givenName);
            index++;
        }
    }

    public void removePlayer() {
        // remove all the players to reset the index to 0 so the NimPlayer array is empty
        System.out.println("Are you sure you want to remove all players? (y/n)");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("y")) {
            nimPlayers = new NimPlayer[100];
            index = 0;
        }
    }

    public void removePlayer(String username) {
        // find the position of the player that will be removed
        int position = findPlayer(username);
        // if the player being removed does not exist, the sentence will be printed out.
        if (position == -1) {
            System.out.println("The player does not exist.");
            // if the player does exist, the next player of the player being removed will replace the index of the removed player
            // until the loop reaches the index of index-1
        } else {
            if (index - 1 - position >= 0)
                System.arraycopy(nimPlayers, position + 1, nimPlayers, position, index - 1 - position);
            nimPlayers[index - 1] = null;
            index--;
        }
    }

    public void editPlayer(String username, String familyName, String givenName) {
        int position = findPlayer(username); // set position of the player being removed
        if (position == -1) {
            System.out.println("The player does not exist.");
        } else {
            String lastName = nimPlayers[position].getLastName();
            String firstName = nimPlayers[position].getFirstName();
            if (familyName.equals(lastName) && givenName.equals(firstName)) {
                return;
            } else {
                nimPlayers[position].setLastName(familyName); // reset last name used method in NimPlayer
                nimPlayers[position].setFirstName(givenName); // reset first name used method in NimPlayer
            }
        }
    }

    public void resetstats() {
        System.out.println("Are you sure you want to reset all players? (y/n)");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("y")) {
            for (int i = 0; i < index; i++) {
                nimPlayers[i].reset();
            }
        }
    }

    public void resetstats(String username) {
        int position = findPlayer(username); // set position of the player being removed
        if (position == -1) {
            System.out.println("The player does not exist.");
        } else {
            nimPlayers[position].reset();
        }
    }

    private int findPlayer(String username) {
        for (int i = 0; i < 100; i++) {
            if (nimPlayers[i] == null) {
                return -1; // -1 represents that the username is not found
            }else if(nimPlayers[i].getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    public void displayplayer() {
        // sort the players alphabetically
        for (int i = 0; i < index - 1; i++) {
            for (int j = i + 1; j < index; j++) {
                if (nimPlayers[i].getUsername().compareTo(nimPlayers[j].getUsername()) > 0) {
                    NimPlayer curr = nimPlayers[j] instanceof NimHumanPlayer ? new NimHumanPlayer(nimPlayers[j]) : new NimAIPlayer(nimPlayers[j]);
                    nimPlayers[j] = nimPlayers[i] instanceof NimHumanPlayer ? new NimHumanPlayer(nimPlayers[i]) : new NimAIPlayer(nimPlayers[i]);
                    nimPlayers[i] = curr instanceof NimHumanPlayer ? new NimHumanPlayer(curr) : new NimAIPlayer(curr);
                }
            }
        }

        for (int i = 0; i < index; i++) {
            nimPlayers[i].display();
        }
    }

    public void displayplayer(String username) {
        int position = findPlayer(username); // set position of the player being removed
        if (position == -1) {
            System.out.println("The player does not exist.");
        } else {
            nimPlayers[position].display();
        }
    }

    public void rankings() {
        // players will be ranked by decreasing order by default if not specified in command input
        rankings("desc");
    }

    public void rankings(String order) {
        // players will be ranked by either desc or asc based on command input request
        String order1 = "desc";
        String order2 = "asc";
        if (order.equals(order1)) {
            rankingDesc();
        } else if (order.equals(order2)) {
            rankingAsc();
        }
        for (int i = 0; i < Math.min(10, index); i++) {
            nimPlayers[i].rankingDisplay();
        }
    }

    public void startGame(String initialStones, String upperStones, String username1, String username2) {
        // check if the players exist in the game if not it will not start the game
        if (findPlayer(username1) == -1 || findPlayer(username2) == -1) {
            System.out.println("One of the players does not exist.");
            return;
        }
        // the initial stones and upperbound are input as string, so need parseInt method to parse String into int
        int totalStones = Integer.parseInt(initialStones);
        int upperBound = Integer.parseInt(upperStones);
        // if the player exists, the index of the player will be passed into playerGame method
        NimPlayer player1 = nimPlayers[findPlayer(username1)];
        NimPlayer player2 = nimPlayers[findPlayer(username2)];
        // create instance nimGame of NimGame class
        NimGame nimGame = new NimGame(upperBound, totalStones, player1, player2);
        // invoke the playGame method in NimGame class
        nimGame.playGame();
    }

    public void startadvancedgame() {
        System.out.println("This method is not yet developed.");
    }

    private void help() {
        // display the help to guide players
        System.out.println("");
        System.out.println("Type 'commands' to list all available commands\n"
                + "Type 'startgame' to play game\n" +
                "The player that removes the last stone loses!");
    }

    // use array to put all the commands as a loop
    private void commandList() {
        // to display all the commands from Command class in instance command and invoke toSting method in command class
        System.out.print(command.toString());
    }

    private int rankingDesc() { // players ranking by desc order
        for (int i = 0; i < index - 1; i++) {
            for (int j = i + 1; j < index; j++) {
                if (nimPlayers[i].calWinRate() < nimPlayers[j].calWinRate() ||
                        nimPlayers[i].calWinRate() == nimPlayers[j].calWinRate() &&
                                nimPlayers[i].getUsername().compareTo(nimPlayers[j].getUsername()) > 0) {
                    NimPlayer temp = nimPlayers[i] instanceof NimHumanPlayer ? new NimHumanPlayer(nimPlayers[i]) : new NimAIPlayer(nimPlayers[i]);
                    nimPlayers[i] = nimPlayers[j] instanceof NimHumanPlayer ? new NimHumanPlayer(nimPlayers[j]) : new NimAIPlayer(nimPlayers[j]);
                    nimPlayers[j] = temp instanceof NimHumanPlayer ? new NimHumanPlayer(temp) : new NimAIPlayer(temp);
                }
            }
        }
        return index;
    }

    private int rankingAsc() { // players rankings by asc order
        for (int i = 0; i < index - 1; i++) {
            for (int j = i + 1; j < index; j++) {
                if (nimPlayers[i].calWinRate() > nimPlayers[j].calWinRate() ||
                        nimPlayers[i].calWinRate() == nimPlayers[j].calWinRate() &&
                                nimPlayers[i].getUsername().compareTo(nimPlayers[j].getUsername()) < 0) {
                    NimPlayer temp = nimPlayers[i] instanceof NimHumanPlayer ? new NimHumanPlayer(nimPlayers[i]) : new NimAIPlayer(nimPlayers[i]);
                    nimPlayers[i] = nimPlayers[j] instanceof NimHumanPlayer ? new NimHumanPlayer(nimPlayers[j]) : new NimAIPlayer(nimPlayers[j]);
                    nimPlayers[j] = temp instanceof NimHumanPlayer ? new NimHumanPlayer(temp) : new NimAIPlayer(temp);
                }
            }
        }
        return index;
    }
}

// programmer-defined Exception class
class InvalidInputException extends Exception {

    public InvalidInputException(String message) {

        super("'" + message + "' is not a valid command.");

    }

}


