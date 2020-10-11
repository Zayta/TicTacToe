import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


/**
 * 
 * @author szhen
 * This class serves as a UI for users before and after a game
 */
public class GameController {
	private ScoreBoard scoreBoard;
	public GameController() {
		scoreBoard = new ScoreBoard();
	}
	
	/**
	 * Runs the user interface
	 */
	public void run() {
		p("Welcome to the Wonderful World of Tic Tac Toe.");
			
		Scanner sc=new Scanner(System.in);
		//set the teams up
		List<Team>teams =configTeams(sc);
		
		boolean running = true;
		
		while(running) {
			
			//let player configure settings of the game
			Game game = configGame(sc,teams);
			
			//play the game
			game.play(sc);
			
			//update winning team scores
			List<Team> winningTeams = game.determineWinningTeams();
			updateWinningTeamScores(winningTeams);
			
			//if users do not want to play another game
			if(!playAnother(sc)) {
				break;
			}
			
		}
		
		sc.close();
		
		
		
		printScores();
			
	}
	
	
	
	
	/**
	 * Lets player define game settings
	 * @param sc - scanner used to prompt user input
	 * @return the game configured by the player
	 */
	private Game configGame(Scanner sc, List<Team> teams) {
		Game game=null;
		//choose type of game
		p("What game do you want to play? "+Arrays.toString(GameOptions.values()));

		GameOptions gm = null;
		while(gm==null) {
			try {
				gm = GameOptions.valueOf(sc.next().toUpperCase().trim());
			}catch(IllegalArgumentException e) {
				gm = null;
				p("That game is unavailable. Please choose one of the following: "+Arrays.toString(GameOptions.values()));
				
			}
		};
		
		//configure board
		p("What is the dimension of the square board you want to play with? (Enter a single positive integer) : ");
		int dim = promptPositiveInteger(sc);
		Board board = new Board(dim);
		
		switch(gm) {
			case ORDERANDCHAOS:

				game = new OrderAndChaos(board,teams);
				break;
			case TICTACTOE: 

				game = new TicTacToe(board,teams);
				break;
			default:
				game = new TicTacToe(board,teams);
				break;
				
		}
		
		return game;
	}
	
	private List<Team> configTeams(Scanner sc) {
		//how many teams
			p("How many teams are playing? ");
			List<Team> teams = new ArrayList<Team>();
			int ts = promptPositiveInteger(sc);
			for(int i = 0; i<ts;i++) {
				p("Enter team"+i+"'s name: ");
				Team team =new Team(sc.next());
				teams.add(team);
				scoreBoard.addToTeamScore(team.getID(), 0);
				//set how many tokens this team is allowed to place on their turn
				p("How many moves per turn are allowed for this team? (Enter positive integer or any other key to skip to default: 1)");
				if(sc.hasNextInt()) {
					team.setMovesPerTurn(sc.nextInt());
				}
				//who is in each team?
				p("How many players are in this team?");
				int players =promptPositiveInteger(sc);
				for(int x = 0; x<players;x++) {
					p("Player "+x+" Name: ");
					Player player = new Player(sc.next());
					team.addPlayer(player);

					scoreBoard.addToPlayerScore(player.getID(), 0);
				}
				
			} 
			return teams;
	}
	/*
	 * prompts user to enter an integer
	 * returns the integer that the user has entered.
	 */
	private int promptPositiveInteger(Scanner sc) {
		int i = 0;
		boolean flag = false;
		do {
			if(flag) {
				p("Integer needs to be positive. Enter positive integer: ");
			}
			while(!sc.hasNextInt()) {
				p("Please enter a positive integer: ");
				sc.next();
			}

			i = sc.nextInt();
			flag = true;
			
		}while(i<=0);
		return i;
	}
	
	
	

	/**
	 * Updates the scores of the winning team
	 * @param winningTeam - team to update
	 */
	private void updateWinningTeamScores(List<Team> winningTeams) {
		if(!winningTeams.isEmpty())
		for(Team winningTeam:winningTeams) {
			if(winningTeam!=null){
				p("Team "+winningTeam.getID()+" won!");
				
				scoreBoard.addToTeamScore(winningTeam.getID(), 1);
				for(Player p: winningTeam.getPlayers()) {
					scoreBoard.addToPlayerScore(p.getID(), 1);
				}
				
			}
		}
	}
	/**
	 * Prompts the user to decide if they want to play another round or not
	 * @param sc - scanner used to let user input their choice
	 * @return whether or not user wants to play another game or round
	 */
	private boolean playAnother(Scanner sc) {
		p("Do you want to play another game? (Y/N)");
		String ans = sc.nextLine();
		while(!ans.equalsIgnoreCase("N")&&!ans.equalsIgnoreCase("Y")) {
			p("Enter Y or N: ");
			ans = sc.nextLine();
		}
		//if yes:
		if(ans.equalsIgnoreCase("Y")) {
			return true;
		}
		if(ans.equalsIgnoreCase("N")) {
			return false;
		}
		return false;
		
	}
	
	
	/**
	 * Prints team and player scores
	 */
	private void printScores() {

		p(scoreBoard.printTeamScores());
		p("========");
		p(scoreBoard.printPlayerScores());
	}
	
	
	private void p(String s) {
		System.out.println(s);
	}

}
