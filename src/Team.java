import java.util.ArrayList;
import java.util.List;

public class Team {
	private int wins=0;
	private final String ID;
	private int movesPerTurn;//moves that the team is allowed to make per turn
	
	private List<Player>players;
	/*
	 * @param token - denotes what player uses to put on the board (X or O)
	 */
	public Team() {
		this.wins=0;
		this.ID="";
		movesPerTurn = 1;
		this.players = new ArrayList<>();
	}
	public Team(String ID) {
		this.ID=ID;
		this.wins=0;
		this.players = new ArrayList<>();
		movesPerTurn = 1;
		
	}
	public void addPlayer(Player player) {
		this.players.add(player);
	}
	
	public void removePlayer(Player player) {
		this.players.remove(player);
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public void setMovesPerTurn(int moves) {
		//moves must be at least 1
		movesPerTurn = Math.max(1, moves);
	}
	public int getMovesPerTurn() {
		return this.movesPerTurn;
	}
	public String getID() {
		return ID;
	}
	
	public int getWins() {
		return wins;
	}
	public void incrementWins() {
		wins+=1;
	}
	public String printScore() {
		return "Team "+ID+": "+wins+" wins";
	}
}
