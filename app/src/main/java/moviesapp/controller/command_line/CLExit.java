package moviesapp.controller.command_line;

public class CLExit extends CLMethods {

    /**
     * Exit command that asks user if he is sure that he wants to leave the application, exit it if yes
     */
    void exitCommand(){
        if(askToConfirm("Are you sure that you want to leave the application?")){
            System.exit(0);
        }
    }
}
