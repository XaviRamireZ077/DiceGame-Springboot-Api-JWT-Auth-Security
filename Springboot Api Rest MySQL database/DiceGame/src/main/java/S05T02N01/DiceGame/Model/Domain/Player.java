package S05T02N01.DiceGame.Model.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Getter;

import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "player")
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    // ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "player_name", // Column name in database
            columnDefinition = "varchar(255) default 'Anonymous'") // Default name if null
    private String playerName;

   // private String password;

    @Transient
    @Column(name = "success_rate") // Column name in database
    private Double successRate;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "reg_date") // Column name in database
    private Date regDate;

    // Entities relationship
    @JsonIgnore //To fix issue with infinite recursion
    @OneToMany(mappedBy="player", cascade = {CascadeType.ALL})

    private List<DiceGame> diceRolls;

    // CONSTRUCTORS

    public Player(){
        this.playerName = addName(getPlayerName());
    }

    public Player(String playerName){
        this.playerName = playerName;
    }

    // METHODS

    // Set player as Anonymous if no name given
    private String addName(String name) {
        if (name == null) {
            name = "Anonymous";

        }
        return name;
    }

    public double getSuccessRateOfPlayers(List<DiceGame> diceRollsList) {
        double successRate = 0;
        if (diceRollsList != null && diceRollsList.size() > 0) {
            int gameListSize = diceRollsList.size();
            int totalWin = 0;
            for (DiceGame game : diceRollsList) {
                if (game.isWon()) {
                    totalWin++;
                }
            }
            successRate = (totalWin * 100) / gameListSize;
        }
        return successRate;
    }


    // Player information
    @Override
    public String toString() {
        return "Player [id = " + id + ", player name = " + playerName + ", registration date = " + regDate + ", dice rolls = "
                + ", success rate = " + successRate + " ]";
    }

    // Update success rate
 /**   public void updateSuccessRate() {

        // Count dice rolls won
        int diceRollsWon = 0;
        for (DiceGame diceRoll : diceRolls) {
            if (diceRoll.checkIfWon()) {
                diceRollsWon++;
            }
        }
        // Calculates success rate = dice rolls won / number of dice rolls
        this.setSuccessRate ((double) diceRollsWon / (double) this.diceRolls());
    }

    // Roll the dices
    public DiceGame rollTheDices() {
        // Get random number from 1-6 for two dices
        Random random = new Random();
        int dice1 = random.nextInt(5) + 1;
        int dice2 = random.nextInt(5) + 1;
        int sum = dice1 + dice2;

        // Check if player has won (sum of dices must be 7 in order to win)
        if (sum == 7) {
            System.out.println("You WON!");
        } else {
            System.out.println("You LOST!");
        }

        // Add a DiceRoll object to players dice roll list with obtained values
        DiceGame diceRoll = new DiceGame(null, dice1, dice2, this);
        this.diceRolls.add(diceRoll);

        //Update success rate
        this.updateSuccessRate();

        System.out.println(diceRoll.toString());

        return diceRoll;**/
    }







