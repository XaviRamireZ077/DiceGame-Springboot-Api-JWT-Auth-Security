package S05T02N01.DiceGame.Model.Service.Implementation;

import S05T02N01.DiceGame.Model.Exceptions.PlayerNameAlreadyExistsException;
import S05T02N01.DiceGame.Model.Exceptions.PlayerNotFoundException;
import S05T02N01.DiceGame.Model.Domain.DiceGame;
import S05T02N01.DiceGame.Model.Domain.Player;
import S05T02N01.DiceGame.Model.Repository.DiceGameRepository;
import S05T02N01.DiceGame.Model.Repository.PlayerRepository;
import S05T02N01.DiceGame.Model.Service.DiceGameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
@Service @RequiredArgsConstructor @Transactional @Slf4j
public class DiceGameServiceImpl implements DiceGameService {



    @Autowired
    private DiceGameRepository diceGameRepository;

    @Autowired
    private PlayerRepository playerRepository;


    @Override
    public Player addPlayer(Player player) {
        try{
            if(player.getPlayerName() == null || player.getPlayerName() == ""){
                player.setPlayerName("Anonymous");
                return playerRepository.save(player);
            } else if(playerRepository.existsByPlayerName(player.getPlayerName())){
                throw  new PlayerNameAlreadyExistsException("Player with name: " + player.getPlayerName() + " already exists");
            } else{
                return playerRepository.save(player);
            }
        } catch (Exception e){
            System.out.println("Error -->> " + e.getMessage());
        }
        log.info("Adding players {} to database",player.getPlayerName());
        return null;
    }

    @Override
    public Player updatePlayer(Long id, Player player) {
        try {
            if(playerRepository.findById(id) == null || playerRepository.existsByPlayerName(player.getPlayerName())){
                throw new PlayerNotFoundException("Player with id: " + id + " doesn't exist");
            } else{
                player.setId(id);
                player.setPlayerName(player.getPlayerName());
                player.setRegDate(playerRepository.findById(id).getRegDate());
                return playerRepository.save(player);
            }
        } catch (Exception e){
            System.out.println("Error ->>> " + e.getCause());
        }
        return null;
    }

    /**
     * @param player_id
     */
    @Override
    public DiceGame rollTheDices(Long player_id) {
        if(playerRepository.findById(player_id) != null ){
            try {
                DiceGame game = new DiceGame(playerRepository.findById(player_id));
                game.rollTheDices();
                return diceGameRepository.save(game);
            } catch (Exception e){
                System.out.println("Error -> " + e.getMessage());
            }
        } else{
            throw new PlayerNotFoundException("Player with id: " + player_id + " does not exist!!!");
        }
        return null;
    }

    /**
     * @param player_Id
     */
    @Override
    public void deleteGameByPlayerId(Long player_Id) {
        Player player = playerRepository.findById(player_Id);
        try {
            diceGameRepository.deleteByPlayer(player);
        } catch (Exception e){
            System.out.println("Error -> " + e.getMessage());
        }

    }

    /**
     * @return
     */
    @Override
    public Player getLoserPlayer() {
        try {
            if(getListPlayerWithRate().isEmpty()){
                throw new PlayerNotFoundException("No loser because NOT players yet!!!");
            } else{
                List<Player> listAllPlayers = getListPlayerWithRate();
                listAllPlayers.sort(Comparator.comparing(Player::getSuccessRate));
                return listAllPlayers.get(0);
            }
        } catch (Exception e){
            System.out.println("Error ->>> " + e.getMessage());
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
            System.out.println("Error" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Player> listOfPlayers() {

        return playerRepository.findAll();
    }

    /**
     * @param player_Id
     */
    @Override
    public List<DiceGame> getPlayerGames(Long player_Id) {
        return diceGameRepository.getRollDiceGameByPlayerId(player_Id);
    }

    // To obtain players ranking
    @Override
    public List<Player> getPlayersRanking() {
        List<Player> listAllPlayers = playerRepository.findAll();
        List<DiceGame> listOfGamesActualPlayer = new ArrayList<>();

        if(listAllPlayers != null && listAllPlayers.size() > 0){
            try {
                for(Player player: listAllPlayers){
                    listOfGamesActualPlayer = diceGameRepository.getRollDiceGameByPlayerId(player.getId());
                    Double successRate = player.getSuccessRateOfPlayers(listOfGamesActualPlayer);
                    player.setSuccessRate(Double.valueOf(successRate.toString()));
                }
                listAllPlayers.sort(Comparator.comparing(Player::getSuccessRate).reversed());
            } catch (Exception e){
                System.out.println("Error -> " + e.getMessage());
            }
        }
        return listAllPlayers;
    }

    @Override
    public Player findPlayer(Long player_id) {
        if (playerRepository.findById(player_id) != null) {
            return playerRepository.findById(player_id);
        } else {
            try {
                throw new PlayerNotFoundException("Player with id -> " + player_id + " does not exist!!!");
            } catch (PlayerNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

    }

    @Override
    public Map<String, Double> getPlayersRating() {
        List<Player> listAllPlayers = playerRepository.findAll();
        Map<String, Double> mapPlayersWithAvgSuccessRate = new HashMap<String, Double>();

        if(listAllPlayers != null && listAllPlayers.size() > 0){
            List<DiceGame> gamesOfActualPlayer;

            for(Player player: listAllPlayers){
                gamesOfActualPlayer = diceGameRepository.getRollDiceGameByPlayerId(player.getId());

                if(gamesOfActualPlayer.size() > 0){
                    String key = player.getPlayerName();
                    Double value = player.getSuccessRateOfPlayers(diceGameRepository.getRollDiceGameByPlayerId(player.getId()));
                    System.out.println();
                    mapPlayersWithAvgSuccessRate.put(key,value);

                } else{
                    mapPlayersWithAvgSuccessRate.put(player.getPlayerName(), (double) 0) ;
                }
            }
        }
        return mapPlayersWithAvgSuccessRate;
    }

    public List<Player> getListPlayerWithRate(){
        List<Player> listAllPlayers = playerRepository.findAll();
        List<DiceGame> listOfGamesActualPlayer = new ArrayList<DiceGame>();

        if(listAllPlayers != null && listAllPlayers.size() > 0){
            try {
                for(Player player: listAllPlayers){
                    listOfGamesActualPlayer = diceGameRepository.getRollDiceGameByPlayerId(player.getId());
                    Double successRate = player.getSuccessRateOfPlayers(listOfGamesActualPlayer);
                    player.setSuccessRate(Double.valueOf(successRate.toString()));
                }

            } catch (Exception e){
                System.out.println("Error -> " + e.getMessage());
            }
        }

        return listAllPlayers;
    }



}