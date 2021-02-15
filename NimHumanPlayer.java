
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


