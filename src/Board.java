/**
 * Created by Jacob on 11/26/2015.
 */
public class Board {
    private char[][] board;
    private int evaluationValue;
    private int[] latestMove;
    private int numMoves;

    public Board() {
        board = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = '-';
            }
        }
        latestMove = null;
        evaluationValue = 0;
        numMoves = 0;
    }

    public Board(char[][] b, int[] lastMv) {
        board = b;
        latestMove = lastMv;
        calcEvalValue();
        numMoves = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] != '-') {
                    numMoves++;
                }
            }
        }
    }

    public void makeMove(int[] move, char token) {
        board[move[0]][move[1]] = token;
        numMoves++;
    }

    public boolean testMove(int[] move) {
        if (board[move[0]][move[1]] == '-') {
            return true;
        }
        return false;
    }

    public int getWinner() {
        //1 is for the computer winning, 2 is for the player winning, 0 is for no current winner, 3 for tie game
        //horizontal cases
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i][j] == 'x' && board[i][j + 1] == 'x' && board[i][j + 2] == 'x' && board[i][j + 3] == 'x') {
                    return 1;
                } else if (board[i][j] == 'o' && board[i][j + 1] == 'o' && board[i][j + 2] == 'o' && board[i][j + 3] == 'o') {
                    return 2;
                }
            }
        }

        //vertical cases
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 5; i++) {
                if (board[i][j] == 'x' && board[i + 1][j] == 'x' && board[i + 2][j] == 'x' && board[i + 3][j] == 'x') {
                    return 1;
                } else if (board[i][j] == 'o' && board[i + 1][j] == 'o' && board[i + 2][j] == 'o' && board[i + 3][j] == 'o') {
                    return 2;
                }
            }
        }

        //all tiles marked here means draw, otherwise game isn't done yet
        return (numMoves == 64) ? 3 : 0;
    }

    public void printBoard() {
        System.out.println("  1 2 3 4 5 6 7 8");
        for (int i = 0; i < 8; i++) {
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
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public int[] getLastMove() {
        return latestMove;
    }

    public int getEvaluationValue() {
        return evaluationValue;
    }

    private void calcEvalValue() {
        if (numMoves < 2) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    int tempAdd;
                    if (board[i][j] == 'x') {
                        tempAdd = 1;
                    } else if (board[i][j] == 'o') {
                        tempAdd = -1;
                    } else {
                        tempAdd = 0;
                    }
                    switch (i) {
                        case 3:
                        case 4:
                            tempAdd *= 2;
                        case 2:
                        case 5:
                            tempAdd *= 2;
                        case 1:
                        case 6:
                            tempAdd *= 2;
                        case 0:
                        case 7:
                            break;
                    }
                    switch (j) {
                        case 3:
                        case 4:
                            tempAdd *= 2;
                        case 2:
                        case 5:
                            tempAdd *= 2;
                        case 1:
                        case 6:
                            tempAdd *= 2;
                        case 0:
                        case 7:
                            break;
                    }
                    evaluationValue += tempAdd;
                }
            }
        } else {
            for (int i = 0; i < 8; i++) {
                int state = 0, count = 0;
                for (int j = 0; j < 8; j++) {
                    switch (state) {
                        case 0:
                            if (board[i][j] == 'x') {
                                state = 1;
                                count = 1;
                            } else if (board[i][j] == 'o') {
                                state = 2;
                                count = 1;
                            }
                            break;
                        case 1:
                            if (board[i][j] == '-') {
                                if (4 - count > 0) {
                                    count++;
                                }
                            } else if (board[i][j] == 'x') {
                                count++;
                            } else if (board[i][j] == 'o') {
                                evaluationValue += Math.pow(16.0, count);
                                state = 2;
                                count = 1;
                            }
                            break;
                        case 2:
                            if (board[i][j] == '-') {
                                if (4 - count > 0) {
                                    count++;
                                }
                            } else if (board[i][j] == 'x') {
                                evaluationValue -= 2*Math.pow(16.0, count);
                                state = 1;
                                count = 1;
                            } else if (board[i][j] == 'o') {
                                count++;
                            }
                            break;
                    }
                }
            }

            for (int i = 0; i < 8; i++) {
                int state = 0, count = 0;
                for (int j = 0; j < 8; j++) {
                    switch (state) {
                        case 0:
                            if (board[j][i] == 'x') {
                                state = 1;
                                count = 1;
                            } else if (board[j][i] == 'o') {
                                state = 2;
                                count = 1;
                            }
                            break;
                        case 1:
                            if (board[j][i] == '-') {
                                if (4 - count > 0) {
                                    count++;
                                }
                            } else if (board[j][i] == 'x') {
                                count++;
                            } else if (board[j][i] == 'o') {
                                evaluationValue += Math.pow(16.0, count);
                                state = 2;
                                count = 1;
                            }
                            break;
                        case 2:
                            if (board[j][i] == '-') {
                                if (4 - count > 0) {
                                    count++;
                                }
                            } else if (board[j][i] == 'x') {
                                evaluationValue -= 2*Math.pow(16.0, count);
                                state = 1;
                                count = 1;
                            } else if (board[j][i] == 'o') {
                                count++;
                            }
                            break;
                    }
                }
            }
        }
    }
}
