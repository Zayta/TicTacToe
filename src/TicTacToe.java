import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class TicTacToe extends Game{

	//key: team value: token that the team can place
	private Hashtable<Team, Character> tokens;
	
	
	public TicTacToe() {
		super();
		tokens = new Hashtable<>();
	}
	
	public TicTacToe(Board board, List<Team> teams) {
		super(board,teams);

		tokens = new Hashtable<>();
	}
	


	@Override
	protected void init(Scanner scanner) {
		
		//set victory condition
				System.out.println("How many tokens must be placed in a row in order to win? Enter a positive integer less than board size:");
				int targetTokensInARow = 0;
				while(!scanner.hasNextInt()) {
						System.out.println("Please enter a positive number: ");
						scanner.next();
				}
				
				targetTokensInARow = scanner.nextInt();
				while(targetTokensInARow<=0||targetTokensInARow>board.getDim()) {
					System.out.println("Please enter a positive number less than or equal to the board dimension "+board.getDim());
					while(!scanner.hasNextInt()) {
						System.out.println("Please enter a positive number: ");
					}
					targetTokensInARow = scanner.nextInt();
				};
				board.setTargetConsecutiveDim(targetTokensInARow);
		
		//init each team's token
		List<Team> teams = getTeams();
		for(int i = 0; i<teams.size();i++) {
			System.out.println("Team "+teams.get(i).getID()+", enter your nonEmpty token: ");
			char token = Board.emptyBoardSpace;
			do {
				token = scanner.next().charAt(0);
			}while(token==Board.emptyBoardSpace);
			
			tokens.put(teams.get(i), token);
			
			
		}
		
		
		
		
	}
	/**
	 * 	Tic tac toe team needs to place their token on the board.
	 */
	@Override
	protected void playerMove(Team team, Scanner scanner) {

		placeToken(tokens.get(team),scanner);
		
	}

	/**
	 * Returns the winning team.
	 * In tic tac toe, winning team is the team who has the winning token
	 */
	@Override
	public List<Team> determineWinningTeams() {
		char win = board.getWinningToken();
		
		List<Team> winningTeams = new ArrayList<>();
		//find teams with the winning token
		for(Team team:tokens.keySet()) {
			if(tokens.get(team)==win) {
				winningTeams.add(team);
			}
		}
		
		
		return winningTeams;
	}

	/**
	 * Override the ending condition for the game so that game ends when a winning token has been detected
	 */
	@Override
	protected boolean isGameEnd() {
		return super.isGameEnd()||board.getWinningToken()!=Board.emptyBoardSpace;
	}

}
