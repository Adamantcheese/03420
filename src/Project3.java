import java.util.Scanner;

/**
 * Created by Jacob on 11/18/2015.
 */
public class Project3 {
    public static void main(String[] args) {
        boolean first;
        int time;
        Scanner kb = new Scanner(System.in);

        char[][] board = new char[8][8];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = '-';
            }
        }

        while(true) {
            System.out.print("Do you want to go first (y/n)? ");
            char in = kb.nextLine().toLowerCase().charAt(0);
            if(in == 'y') {
                first = true;
                break;
            } else if (in == 'n') {
                first = false;
                break;
            } else {
                System.out.println("Not an option.");
            }
        }

        while(true) {
            System.out.print("How many seconds should the computer think? ");
            try {
                time = Integer.parseInt(kb.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Not a number.");
            }
        }

        printBoard(board);

        int[] userMove = null;

        if(first) {
            while(true) {
                System.out.print("Enter your move: ");
                String input = kb.nextLine();
                userMove = moveToIndeces(input);
                if (userMove == null) {
                    System.out.println("That isn't a valid move!");
                } else if(board[userMove[0]][userMove[1]] != '-') {
                    System.out.println("That isn't a valid move!");
                } else if(userMove[0] < 0 || userMove[0] > 7 || userMove[1] < 0 || userMove[1] > 7) {
                    System.out.println("That isn't a valid move!");
                } else {
                    break;
                }
            }

            board[userMove[0]][userMove[1]] = 'o';

            printBoard(board);
        }

        while(getWinner(board) == 0) {
            //do computer move
            System.out.println("Calculating computer move...");
            Solver s = new Solver(board);
            s.start();
            try {
                Thread.sleep((long) time*1000);
            } catch (InterruptedException e) {
                System.out.println("Something went wrong.");
                System.exit(-1);
            }
            s.kill();

            int[] compMove = s.getMove();
            board[compMove[0]][compMove[1]] = 'x';

            printBoard(board);

            System.out.println("Computer move: " + indecesToMove(compMove));

            //do user move
            while(true) {
                System.out.print("Enter your move: ");
                String input = kb.nextLine();
                userMove = moveToIndeces(input);
                if (userMove == null) {
                    System.out.println("That isn't a valid move!");
                } else if(board[userMove[0]][userMove[1]] != '-') {
                    System.out.println("That isn't a valid move!");
                } else if(userMove[0] < 0 || userMove[0] > 7 || userMove[1] < 0 || userMove[1] > 7) {
                    System.out.println("That isn't a valid move!");
                } else {
                    break;
                }
            }

            board[userMove[0]][userMove[1]] = 'o';

            printBoard(board);
        }

        if(first) {
            System.out.println(getWinner(board) == 1 ? "You won!" : "You lost!");
        } else {
            System.out.println(getWinner(board) == 1 ? "You lost!" : "You won!");
        }

        System.out.println("Enter anything to continue...");
        kb.nextLine();
    }

    public static int[] moveToIndeces(String input) {
        input = input.toLowerCase();
        if(input.length() != 2) {
            return null;
        }
        int[] ret = new int[2];
        switch(input.charAt(0)) {
            case 'a':
                ret[0] = 0;
                break;
            case 'b':
                ret[0] = 1;
                break;
            case 'c':
                ret[0] = 2;
                break;
            case 'd':
                ret[0] = 3;
                break;
            case 'e':
                ret[0] = 4;
                break;
            case 'f':
                ret[0] = 5;
                break;
            case 'g':
                ret[0] = 6;
                break;
            case 'h':
                ret[0] = 7;
                break;
            default:
                ret[0] = 500;
                break;
        }
        ret[1] = Integer.parseInt(input.substring(1)) - 1;
        return ret;
    }

    public static String indecesToMove(int[] move) {
        String ret = "";
        switch (move[0]) {
            case 0:
                ret = "a";
                break;
            case 1:
                ret = "b";
                break;
            case 2:
                ret = "c";
                break;
            case 3:
                ret = "d";
                break;
            case 4:
                ret = "e";
                break;
            case 5:
                ret = "f";
                break;
            case 6:
                ret = "g";
                break;
            case 7:
                ret = "h";
                break;
        }
        ret += Integer.toString(move[1] - 1);
        return ret;
    }

    public static void printBoard(char[][] board) {
        System.out.println("  1 2 3 4 5 6 7 8");
        for(int i = 0; i < 8; i++) {
            switch (i) {
                case 0:
                    System.out.print("A");
                    break;
                case 1:
                    System.out.print("B");
                    break;
                case 2:
                    System.out.print("C");
                    break;
                case 3:
                    System.out.print("D");
                    break;
                case 4:
                    System.out.print("E");
                    break;
                case 5:
                    System.out.print("F");
                    break;
                case 6:
                    System.out.print("G");
                    break;
                case 7:
                    System.out.print("H");
                    break;
            }
            System.out.print(' ');
            for(int j = 0; j < 8; j++) {
                System.out.print("" + board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int getWinner(char[][] board) {
        //check all horizontals
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 5; j++) {
                if(board[i][j] == 'x' && board[i][j+1] == 'x' &&  board[i][j+2] == 'x' && board[i][j+3] == 'x') {
                    return 1;
                } else if (board[i][j] == 'o' && board[i][j+1] == 'o' &&  board[i][j+2] == 'o' && board[i][j+3] == 'o') {
                    return 2;
                }
            }
        }

        //check all verticals
        for(int j = 0; j < 8; j++) {
            for(int i = 0; i < 5; i++) {
                if(board[i][j] == 'x' && board[i+1][j] == 'x' &&  board[i+2][j] == 'x' && board[i+3][j] == 'x') {
                    return 1;
                } else if (board[i][j] == 'o' && board[i+1][j] == 'o' &&  board[i+2][j] == 'o' && board[i+3][j] == 'o') {
                    return 2;
                }
            }
        }

        //no current winner
        return 0;
    }
}
