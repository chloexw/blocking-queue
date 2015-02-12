package cs601.blkqueue;

/**
 * Created by Hanzhou on 10/5/14.
 * University of San Francisco
 */
public class StringSequence implements MessageSequence<String> {

    protected int a, b;
    protected int i;

    public StringSequence(int a, int b) {
        this.a = a;
        this.b = b;
        this.i = a;
    }

    @Override
    public boolean hasNext() {
        return i < b;
    }

    @Override
    public String next() {
        int next = i;
        i++;
        return String.valueOf(next);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean validSequenceMove(String previous, String current) {
        return current.equals(eof()) || Integer.parseInt(current) == Integer.parseInt(previous) + 1;
    }

    @Override
    public String eof() {
        return "";
    }
}
