package S05T02N01.DiceGame.Model.Service;

import S05T02N01.DiceGame.Model.Domain.DiceGame;
import S05T02N01.DiceGame.Model.Domain.Player;


import java.util.List;
import java.util.Map;

public interface DiceGameService {

    // CRUD METHODS

    // add player & Save
    public Player addPlayer(Player player);

    // Update player
    public Player updatePlayer(Long id ,Player player);

    // Get all players
    public List<Player> listOfPlayers();

    // Get players success-rating
    public Map<String, Double> getPlayersRating();

    // Get player ranking
    public List<Player> getPlayersRanking();

    // Find players by id
    public Player findPlayer(Long player_id);

    // Roll the dices
    public DiceGame rollTheDices(Long player_id);

    // Get players games
    public List<DiceGame> getPlayerGames(Long player_Id);

    // Delete Game from a player by id
    public void deleteGameByPlayerId(Long player_Id);

    public Player getLoserPlayer();

    public Player getWinPlayer();


}
