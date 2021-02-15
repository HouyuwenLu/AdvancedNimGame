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
public class NimHumanPlayer extends NimPlayer{
    // set multiple constructors can be invoked in Nimsys class
    public NimHumanPlayer(String username, String firstName, String lastName, int games, int wins) {
        super(username, firstName, lastName, games, wins);
    }

    public NimHumanPlayer(NimPlayer humanPlayer) {
        // copy constructor to avoid privacy leak
        super(humanPlayer);
    }

    public NimHumanPlayer(String username, String lastName, String firstName) {
        super(username, lastName, firstName);
    }

    public String writeData() { // the method will be used to write txt file
        return super.writeData() + ",Human\n";
    }


    public int removeStone(String playerName, int upperBound, int totalStones) {
        // remove stones function that can be used in the Nimsys class
        outputStones(totalStones);
        System.out.println(super.getFirstName() + "'s turn - remove how many?");
        int remove;
        // check if the stones input is not int or exceed the upperbound or left stones
        try {
            remove = Nimsys.scanner.nextInt();
            Nimsys.scanner.nextLine();
            if(remove<1 || remove > Math.min(upperBound, totalStones)){
                throw new InvalidMoveException(Math.min(upperBound, totalStones));
            }
        }catch(InvalidMoveException e) {
            System.out.println("");
            System.out.println(e.getMessage());
            remove = removeStone(playerName, upperBound, totalStones);
        }

        return remove;
    }
}

// programmer-defined Exception class
class InvalidMoveException extends Exception{

        public InvalidMoveException(int num) {

            super("Invalid move. You must remove between 1 and " + num + " stones.");

        }


}


