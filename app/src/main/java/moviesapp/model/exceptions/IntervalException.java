package moviesapp.model.exceptions;

public class IntervalException extends Exception{

    public IntervalException(){
        super("\n| Please enter a valid value between the interval.");
    }
}
