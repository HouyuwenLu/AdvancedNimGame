
public class NimAIPlayer extends NimPlayer{
    // set multiple constructors can be invoked in Nimsys class
    public NimAIPlayer(String username, String lastName, String firstName, int games, int wins) {
        super(username, lastName, firstName, games, wins);
    }

    public NimAIPlayer(NimPlayer aiPlayer) {
        // copy constructor to avoid privacy leak
        super(aiPlayer);
    }

    public NimAIPlayer(String username, String lastName, String firstName) {
        super(username, lastName, firstName);
    }

    public String writeData() { // the method will be used to write txt file
        return super.writeData() + ",AI\n";
    }

    public int removeStone(String playerName, int upperBound, int totalStones) {
        //when play with AI, ensure AI always win
        outputStones(totalStones);
        System.out.println(super.getFirstName() + "'s turn - remove how many?");
        return Math.max((totalStones-1) % (upperBound + 1), 1);
    }
}
