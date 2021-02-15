
public class Command {
    // set two array lists to store commands and its parameters
    private final String[] COMMANDLISTS = {"exit", "addplayer", "addaiplayer",
            "removeplayer", "editplayer", "resetstats", "displayplayer", "rankings",
            "startgame", "startadvancedgame", "commands", "help"};
    private final String[] PARAMETER = {"no parameters", "username, secondname, firstname", "username, secondname, firstname", "optional username",
            "username, secondname, firstname", "optional username", "optional username", "optional asc", "initialstones, upperbound, username1, username2",
            "initialstones, upperbound, username1, username2", "no parameters", "no parameters"};

    public String[] getCommandLists() {
        // private instance that can be accessed in other class
        return COMMANDLISTS;
    }

    public String toString() {
        // display all the available commands in the commandLists array when invoked commandLists method in Nimsys class
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < COMMANDLISTS.length; i++){
            String curr = String.format("%2d: ", i+1);
            curr += String.format("%-18s(%s)", COMMANDLISTS[i], PARAMETER[i]); // format the output 
            result.append(curr);
            if(i != COMMANDLISTS.length-1) {
                result.append("\n");
            }
        }
        return result.toString();
    }
}
