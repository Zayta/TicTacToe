
public class Board {
	public static final char emptyBoardSpace = '\u0000';
	//board of characters 'X' denoting player X, 'O' denoting player O, '' denoting freespace
	private char [][] board;
	
	public enum MoveStatus{
		OK,POSITION_TAKEN,OUT_OF_BOUNDS;
		
	}
	
	private int dim;//dimension of the board
	private int targetConsecutiveDim;//'targetConsecutiveDim' tokens in a row are needed for a game to end
	public Board() {
		this.dim = 3;
		board = new char[dim][dim];
		this.targetConsecutiveDim=dim;
	}
	/*
	 * @param dim: the dimension of the board
	 * Assuming board must always be a square
	 */
	public Board(int dim) {
		this.dim = dim;
		board = new char[dim][dim];
		this.targetConsecutiveDim = dim;
	}
	public Board(int dim, int targetConsecutiveDim) {
		this.dim = dim;
		board = new char[dim][dim];
		this.targetConsecutiveDim = targetConsecutiveDim;
	}
	
	public void clear() {
		for(int i = 0; i<board.length;i++)
			for(int j = 0; j<board[0].length;j++)
				board[i][j] = emptyBoardSpace;
	}
	/*
	 * @param playerToken
	 * @param row
	 * @param col
	 * @return whether or not the row and column was successfully set.
	 */
	public MoveStatus set(char playerToken, int row, int col){
		if(row<board.length&&col<board[0].length) {
			if(board[row][col]==emptyBoardSpace) {//if spot is not yet taken/ value of char is still initial value
				board[row][col] = playerToken;
				return MoveStatus.OK;
			}
			return MoveStatus.POSITION_TAKEN;
		}

		return MoveStatus.OUT_OF_BOUNDS;
	}
	/**
	 * Detects whether a token is placed 'targetConsecutiveDim' in a row, col, or diagonal.
	 * @return token that is placed 'targetConsecutiveDim' (emptyBoardSpace token if none player tokens)
	 */
	public char getWinningToken() {
		//check row and col
		
		for(int row = 0; row<targetConsecutiveDim; row++) {
			for(int start = 0; start<=board.length-targetConsecutiveDim;start++) {
				char rowStartChar = board[row][start];
				char colStartChar = board[start][row];
				for(int end = start; end<start+targetConsecutiveDim; end++) {
					if(board[row][end]!=rowStartChar) {
						rowStartChar = Board.emptyBoardSpace;
					}
					if(board[end][row]!=colStartChar) {
						colStartChar = Board.emptyBoardSpace;
					}
				}
				if(rowStartChar!=Board.emptyBoardSpace) {
					
					return rowStartChar;
				}
				if(colStartChar!=Board.emptyBoardSpace) {

					return colStartChar;
				}
				
			}
		}
		
		
		

		
		//check diag win(both diagonals)
		for(int s = 0; s<=board.length-targetConsecutiveDim;s++) {
			char firstCorner = board[s][s];
			char lastCorner = board[board.length-1-s][s];
			for(int i = s+1; i<s+targetConsecutiveDim; i++) {
				if(board[i][i]!=firstCorner) {
					firstCorner = emptyBoardSpace;
				}
				if(board[board.length-1-i][i]!=lastCorner) {
					lastCorner = emptyBoardSpace;
				}
			}
			if(firstCorner!=emptyBoardSpace)
				return firstCorner;
			if(lastCorner!=emptyBoardSpace)
				return lastCorner;
		}
		
		return emptyBoardSpace;
	}
	
	/**
	 * 
	 * @return whether or not there are empty spaces left in the board
	 */
	public boolean hasEmptySpace() {
		
		boolean hasSpaceLeft=false;
		for(int i = 0; i<board.length;i++)
			for(int j =0;j<board[0].length;j++)
				if(board[i][j]==emptyBoardSpace)
					hasSpaceLeft=true;
		return hasSpaceLeft;
		
	}
	
	
	public int getMaxRow() {
		return dim-1;
	}
	public int getMaxCol() {
		return dim-1;
	}
	public int getDim() {
		return dim;
	}
	
	public void setTargetConsecutiveDim(int targetConsecutiveDim) {
		this.targetConsecutiveDim = targetConsecutiveDim;
	}
	
	//prints the board in string format
	@Override
	public String toString() {
		String ret = "";
		String border = "";
		for(int i = 0; i<board[0].length;i++) {
			border+="+--";
		}
		border+="+";
		for(int r = 0; r<board.length ;r++) {

			ret+="\n"+border+"\n";
			for(int c = 0; c<board[0].length ;c++) {
				ret+="|"+board[r][c]+" ";
				
			}
			ret+="|";
		}
		ret+="\n"+border;
		return ret;
	}

}
