package S05T02DiceGameMongo.Model.Service.Implementation;

import S05T02DiceGameMongo.Model.Domain.DiceGame;
import S05T02DiceGameMongo.Model.Domain.Player;
import S05T02DiceGameMongo.Model.Exceptions.PlayerNameAlreadyExistsException;
import S05T02DiceGameMongo.Model.Exceptions.PlayerNotFoundException;
import S05T02DiceGameMongo.Model.Repository.DiceGameRepository;
import S05T02DiceGameMongo.Model.Repository.PlayerRepository;
import S05T02DiceGameMongo.Model.Service.DiceGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class DiceGameServiceImpl implements DiceGameService {

    private static String ErrorMessage1 = "doesn't exist";
    private static String ErrorMessage2 = "already exists";
    private static String ErrorMessage3 = "Error: " ;


    @Autowired
    private DiceGameRepository diceGameRepository;
    @Autowired
    private PlayerRepository playerRepository;

    /**
     * @param player
     * @return
     */
    @Override
    public Player addPlayer(Player player) {
            try{
                if(player.getUserName() == null || player.getUserName().equals("")){
                    player.setUserName("Anonymous");
                    return playerRepository.save(player);
                } else if(playerRepository.existsByUserName(player.getUserName())){
                    throw  new PlayerNameAlreadyExistsException("Player with name: " + player.getUserName() + ErrorMessage2);
                } else{
                    return playerRepository.save(player);
                }
            } catch (Exception e){
                System.out.println(ErrorMessage3 + e.getMessage());
            }
            return null;
    }

    /**
     * @param playerId
     * @param player
     * @return
     */
    @Override
    public Player updatePlayer(String playerId, Player player) {
        try {
            if(playerRepository.findById(playerId) == null || playerRepository.existsByUserName(player.getUserName())){
                throw new PlayerNotFoundException("Player with id: " + playerId + ErrorMessage1);
            } else{
                player.setId(playerId);
                player.setUserName(player.getUserName());
                player.setRegDate(playerRepository.findById(playerId).getRegDate());
                return playerRepository.save(player);
            }
        } catch (Exception e){
            System.out.println(ErrorMessage3 + e.getCause());
        }
        return null;
    }

    /**
     * @param playerId
     * @return
     */
    @Override
    public DiceGame rollTheDices(String playerId) {
        if(playerRepository.findById(playerId) != null ){
            try {
                DiceGame game = new DiceGame(playerRepository.findById(playerId));
                game.rollTheDices();
                return diceGameRepository.save(game);
            } catch (Exception e){
                System.out.println("Error -> " + e.getMessage());
            }
        } else{
            throw new PlayerNotFoundException("Player with id: " + playerId + ErrorMessage1);
        }
        return null;
    }

    /**
     * @param playerId
     */
    @Override
    public void deleteGameByPlayerId(String playerId) {
        Player player = playerRepository.findById(playerId);
        try {
            diceGameRepository.deleteByPlayer(player);
        } catch (Exception e){
            System.out.println(ErrorMessage3 + e.getMessage());
        }

    }
    /**
     * @return
     */
    @Override
    public Player getLoserPlayer() {
        try {
            if(getListPlayerWithRate().isEmpty()){
                throw new PlayerNotFoundException("Players List is empty");
            } else{
                List<Player> listAllPlayers = getListPlayerWithRate();
                listAllPlayers.sort(Comparator.comparing(Player::getSuccessRate));
                return listAllPlayers.get(0);
            }
        } catch (Exception e){
            System.out.println(ErrorMessage3 + e.getMessage());
        }
        return null;
    }

    /**
     * @return
     */
    @Override
    public Player getWinPlayer() {
        try {
            if (getListPlayerWithRate().isEmpty()) {
                throw new PlayerNotFoundException("");

            } else {
                List<Player> listAllPlayers = getListPlayerWithRate();
                listAllPlayers.sort(Comparator.comparing(Player::getSuccessRate).reversed());
                return listAllPlayers.get(0);
            }
        } catch (Exception e) {
            System.out.println(ErrorMessage3 + e.getMessage());
        }
        return null;
    }


    /**
     * @return
     */
    @Override
    public List<Player> listOfPlayers() {
        return playerRepository.findAll();
    }

    /**
     * @param playerId
     * @return
     */
    @Override
    public List<DiceGame> getPlayerGames(String playerId) {
        return diceGameRepository.getRollDiceGameByPlayerId(playerId);
    }

    /**
     * @return
     */
    @Override
    public List<Player> getPlayersRanking() {
        List<Player> listAllPlayers = playerRepository.findAll();
        List<DiceGame> listOfGamesActualPlayer;

        if(listAllPlayers != null && listAllPlayers.size() > 0){
            try {
                for(Player player: listAllPlayers){
                    listOfGamesActualPlayer = diceGameRepository.getRollDiceGameByPlayerId(player.getId());
                    double successRate = player.getSuccessRateOfPlayers(listOfGamesActualPlayer);
                    player.setSuccessRate(Double.valueOf(Double.toString(successRate)));
                }
                listAllPlayers.sort(Comparator.comparing(Player::getSuccessRate).reversed());
            } catch (Exception e){
                System.out.println(ErrorMessage3 + e.getMessage());
            }
        }
        return listAllPlayers;
    }

    /**
     * @param playerId
     * @return
     */
    @Override
    public Player findPlayer(String playerId) {
        if (playerRepository.findById(playerId) != null) {
            return playerRepository.findById(playerId);
        } else {
            try {
                throw new PlayerNotFoundException("Player with id -> " + playerId + ErrorMessage1);
            } catch (PlayerNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }


    @Override
    public Map<String, Double> getPlayersRating() {
        List<Player> listAllPlayers = playerRepository.findAll();
        Map<String, Double> mapPlayersRating = new HashMap<String, Double>();

        if(listAllPlayers != null && listAllPlayers.size() > 0){
            List<DiceGame> gamesOfActualPlayer;

            for(Player player: listAllPlayers){
                gamesOfActualPlayer = diceGameRepository.getRollDiceGameByPlayerId(player.getId());

                if(gamesOfActualPlayer.size() > 0){
                    String key = player.getUserName();
                    Double value = player.getSuccessRateOfPlayers(diceGameRepository.getRollDiceGameByPlayerId(player.getId()));
                    System.out.println();
                    mapPlayersRating.put(key,value);

                } else{
                    mapPlayersRating.put(player.getUserName(), (double) 0) ;
                }
            }
        }
        return mapPlayersRating;
    }

    public List<Player> getListPlayerWithRate(){
        List<Player> listAllPlayers = playerRepository.findAll();
        List<DiceGame> listOfGamesActualPlayer;

        if(listAllPlayers != null && listAllPlayers.size() > 0){
            try {
                for(Player player: listAllPlayers){
                    listOfGamesActualPlayer = diceGameRepository.getRollDiceGameByPlayerId(player.getId());
                    double successRate = player.getSuccessRateOfPlayers(listOfGamesActualPlayer);
                    player.setSuccessRate(Double.valueOf(Double.toString(successRate)));
                }

            } catch (Exception e){
                System.out.println(ErrorMessage3 + e.getMessage());
            }
        }

        return listAllPlayers;
    }

}
