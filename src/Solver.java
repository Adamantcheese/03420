/**
 * Created by Jacob on 11/19/2015.
 */
public class Solver extends Thread {
    private char[][] board;
    private boolean stop;
    private int[] calculatedMove;

    public Solver(char[][] b) {
        board = b;
        stop = false;
    }

    public void run() {
        do {

        } while(!stop);
    }

    public void kill() {
        stop = true;
    }

    public int[] getMove() {
        return calculatedMove;
    }
}
