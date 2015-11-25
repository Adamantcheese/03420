import java.util.ArrayList;

/**
 * Created by Jacob on 11/19/2015.
 */
public class Solver extends Thread {
    private char[][] board;
    private boolean stop;
    private int[] calculatedMove;
    public enum player{computer,user}
    public Solver(char[][] b) {
        board = b;
        stop = false;
    }

    public void run() {
        do {
        	int cost = MiniMax(2,player.computer,Integer.MAX_VALUE,Integer.MAX_VALUE);
            //DO THE MINMAX HERE, implement a similar kill method for both the min and max functions to exit ASAP once it is set

        } while(!stop);
    }
	public void kill() {
        stop = true;
    }

    public int[] getMove() {
        return calculatedMove;
    }
    private int MiniMax(int level, player p, int alpha, int beta) {
    	int score = 0;
		//if(gameOver||level==0){
		if(level==0 || gameOver()){
			return score;
		}
		//populate children
		ArrayList<char[][]> children = PopulateChildren(board);	
		if(p==player.computer){
			for(char[][] child:children){
				score = MiniMax(level-1,player.user,alpha,beta);
				if(score>alpha)
					alpha = score;
				if(alpha>=beta)
					break;
			}
			return alpha;
		}else{
			for(char[][] child:children){
				score = MiniMax(level-1, player.computer,alpha,beta);
				if(score<beta)
					beta = score;
				if(alpha<=beta)
					break;
			}
			return beta;
		}
	
	}
	private boolean gameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	private ArrayList<char[][]> PopulateChildren(char[][] parentBoard) {
		ArrayList<char[][]>children = new ArrayList<char[][]>();
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				char[][]childBoard = parentBoard.clone();
				if(childBoard[i][j] == '-'){
					//can put our move here, place
					childBoard[i][j] = 'o';
					children.add(childBoard.clone());
				}
			}
		}
		
		return null;
	}
}
