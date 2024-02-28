package moviesapp.model.exceptions;

public class NotAnIntegerException extends Exception{
    public NotAnIntegerException() {
        super("\n| Enter a value that is an integer to continue.");
    }
}
