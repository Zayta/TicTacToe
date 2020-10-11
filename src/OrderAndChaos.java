import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import javafx.geometry.Side;

public class OrderAndChaos extends Game{
	
	private enum Side{
		ORDER,CHAOS;
	}
	//key: the side. value: the teams that are on that side. (note: teams could be working together)
	private Hashtable<Side,List<Team>> sides;
	private List<Character> tokens;
	public OrderAndChaos() {
		super();
		tokens=new ArrayList<>();
		sides = new Hashtable<>();
		for(Side side:Side.values()) {
			sides.put(side, new ArrayList<>());
		}
	}
	public OrderAndChaos(Board board, List<Team> teams) {
		super(board,teams);
		tokens=new ArrayList<>();
		sides = new Hashtable<>();
		for(Side side:Side.values()) {
			sides.put(side, new ArrayList<>());
		}
	}


	
	@Override
	protected void init(Scanner scanner) {
		
		//set the tokens to be used in this game
		int num = getTeams().size();
		System.out.println("There will be as many tokens as there are teams. Please enter "+num+" characters to be used as tokens");
		for(int i = 0; i<num;i++) {
			char token = scanner.next().charAt(0);
			
			while(token==Board.emptyBoardSpace||tokens.contains(token)) {
				System.out.println("Token already taken. Please enter another: ");
				token = scanner.next().charAt(0);
			}
			tokens.add(token);
		}
		
		System.out.println("The two tokens used will be : "+Arrays.toString(tokens.toArray()));
		
		
		//set sides
		for(Team team: getTeams()) {
			System.out.println("Team "+team.getID()+", what side are you on? "+Arrays.toString(Side.values()));
			Side s = null;
			while(s==null) {
				try {
					s = Side.valueOf(scanner.next().toUpperCase().trim());
				}catch(IllegalArgumentException e) {
					s = null;
					System.out.println("That side is unavailable. Please choose one of the following: "+Arrays.toString(Side.values()));
					
				}
			};
			sides.get(s).add(team);
			
		}
		
		
	}
	/**
	 * During team's turn, they choose token and place it on the board
	 */
	@Override
	protected void playerMove(Team team, Scanner scanner) {
		System.out.print("Choose token from "+Arrays.toString(tokens.toArray()));
		char token = scanner.next().charAt(0);
		while(!tokens.contains(token)) {

			System.out.println("Token is not used in game. Choose another token:");
			token = scanner.next().charAt(0);
		};
		placeToken(token,scanner);
		
	}
	
	@Override
	protected List<Team> determineWinningTeams() {
		// TODO Auto-generated method stub
		if(board.getWinningToken()==Board.emptyBoardSpace) {
			return sides.get(Side.CHAOS);
		}
		else
			return sides.get(Side.ORDER);
		
	}
	
	/**
	 * Override the ending condition for the game so that game ends when a winning token has been detected
	 */
	@Override
	protected boolean isGameEnd() {
		return super.isGameEnd()||board.getWinningToken()!=Board.emptyBoardSpace;
	}
	
}
