import java.util.Hashtable;

public class ScoreBoard {
	//team score board
	private Hashtable<String,Integer> teamBoard;
	
	//individual player scoreboard
	private Hashtable<String,Integer> playerBoard;
	
	
	public ScoreBoard() {
		teamBoard = new Hashtable<String,Integer>();

		playerBoard = new Hashtable<String,Integer>();
	}

	/**
	 * 
	 * @param teamId - the team to update
	 * @param wins - how many wins to add to the team's existing score
	 */
	public void addToTeamScore(String teamId, int wins) {
		if(teamBoard.get(teamId)==null)
			teamBoard.put(teamId, 0);
		teamBoard.put(teamId, teamBoard.get(teamId)+wins);
	}
	
	/**
	 * 
	 * @param playerId - the playerId to update
	 * @param wins - how many wins to add to the team's existing score
	 */
	public void addToPlayerScore(String playerId, int wins) {
		if(playerBoard.get(playerId)==null)
			playerBoard.put(playerId, 0);
		playerBoard.put(playerId, playerBoard.get(playerId)+wins);
	}

	public boolean containsTeam(String teamName) {
		return teamBoard.containsKey(teamName);
	}
	/**
	 * Returns string of team scores in neat format
	 * @return
	 */
	public String printTeamScores() {
		String ret = "Team scores are: ";
		for(String team: teamBoard.keySet()) {
			ret+="\n"+team+": "+teamBoard.get(team);
		}
		return ret;
	}
	
	public String printPlayerScores() {
		String ret = "Player scores are: ";
		for(String p: playerBoard.keySet()) {
			ret+="\n"+p+": "+playerBoard.get(p);
		}
		return ret;
	}
	
}
