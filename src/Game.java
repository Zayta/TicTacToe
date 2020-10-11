
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Skeleton class for a turn-based board game
 * @author szhen
 *
 */
public abstract class Game {
	private final String inputFormat = "[0-9],[0-9]";

	private List<Team> teams; //teams that are competing
	protected Board board;
	public Game() {
		teams = new ArrayList<>();
		board = new Board(3);
	}
	
	/**
	 * Constructs TicTacToe Game
	 * @param board - the board used for the game
	 * @param numTeams - number of teams in the game
	 */
	public Game(Board board, List<Team> teams) {
		this.teams = teams;
		this.board = board;
	}
	
	
	protected List<Team> getTeams(){
		return this.teams;
	}
	
	/**
	 * runs the game
	 * @param sc - scanner used to prompt user input to run the game
	 */
	public void play(Scanner sc) {

		init(sc);
		
		int turn=0;//variable the keeps track of whose turn it is
		Team nextTeam = teams.get(turn); 
		
			
		board.clear();
			//while board still has empty board spaces that can be filled for player to win
		while(!isGameEnd()) {
			
			//make team move
			p("Team "+nextTeam.getID()+"'s turn. ");
			int i = 0;
			while(i<nextTeam.getMovesPerTurn()) {
				playerMove(nextTeam,sc);
				i++;
				//print the board after the team has made its turn
				p(board.toString());
			}
			
			//next team's turn
			turn++;
			nextTeam = teams.get(turn%teams.size());
			
		}
			
			
	}
	
	/**
	 * A generic board game ends when there is no more space on the board
	 * @return whether or not game has ended
	 */
	protected boolean isGameEnd() {
		return !board.hasEmptySpace();
	}
	
	/**
	 * Places the playerToken on the board
	 * @param playerToken - token to be placed on the board
	 * @param sc - scanner used to prompt player to indicate position
	 */
	protected void placeToken(char playerToken,Scanner sc) {
		p("Enter your move "+ "([0-"+board.getMaxRow()+"],[0-"+board.getMaxCol()+"]): ");
		int row,col;
		Board.MoveStatus set = Board.MoveStatus.OK;
		do {
			String str;
			//make player format correctly
			while (!sc.hasNext(inputFormat)) {
				p("Invalid Format. Enter a value in range ([0-"+board.getMaxRow()+"],[0-"+board.getMaxCol()+"])");
		        sc.next(); 
		    }
			str = sc.next(inputFormat);
			
			//make sure board has valid position
			try {

				//parse row and column from string
				String [] pos = str.split(",");
				row = Integer.parseInt(pos[0]);
				col = Integer.parseInt(pos[1]);
				
				set = board.set(playerToken, row, col);
				
				if(set==Board.MoveStatus.POSITION_TAKEN) {
					p("Position is already taken. Enter another position. ");
				}
				if(set==Board.MoveStatus.OUT_OF_BOUNDS) {

					p("Invalid range. Please enter a value in range ([0-"+board.getMaxRow()+"],[0-"+board.getMaxCol()+"])");
				}
			
			}catch(NumberFormatException e) {
				p("Invalid format. Please enter two numbers separated by comma "+ "([0-"+board.getMaxRow()+"],[0-"+board.getMaxCol()+"])"+" without spaces: ");
				continue;
			}
			
			
		}while(set!=Board.MoveStatus.OK);
	}
	
	
	
	
	
	//OVERRIDE THE BELOW METHODS TO MAKE A NEW BOARD GAME
	/**
	 * Initializes the game with data provided by user
	 */
	protected abstract void init(Scanner scanner);
	
	/**
	 * Defines what the specific team can do during their turn
	 * @param team - the team that needs to take action
	 * @param scanner - used to prompt the team for input
	 */
	protected abstract void playerMove(Team team, Scanner scanner);

	/**
	 * Determines the winning team of the game
	 * @return the Team that won
	 */
	protected abstract List<Team> determineWinningTeams();
	
	
	
	
	
	
	private void p(String s) {
		System.out.println(s);
	}
	public void addTeam(Team team) {
		this.teams.add(team);
	}
	public void removeTeam(Team team) {
		this.teams.remove(team);
	}
	
	
	
	
}
