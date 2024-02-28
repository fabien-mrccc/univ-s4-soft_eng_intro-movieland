package moviesapp.model.exceptions;

public class IndexException extends Exception {

    public IndexException() {
        super("\n| Please enter a valid index to select your element.");
    }

    /**
     * Checks if the given index is valid for a list of the specified size.
     *
     * @param index the index to check.
     * @param size the size of the list.
     * @throws IndexException if the index is negative or exceeds the size of the list.
     */
    public static void isValidIndex(int index, int size) throws IndexException {

        boolean isValidIndex = index >= 0 && index < size;

        if(!isValidIndex) throw new IndexException();;
    }
}
