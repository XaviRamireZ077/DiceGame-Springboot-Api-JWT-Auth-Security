package S05T02DiceGameMongo.Model.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Document(collection = "DiceGame")
public class DiceGame implements Serializable {


    private static final long serialVersionUID = 1L;

    // ATTRIBUTES
    @Id
    private String gameId;

    private int dice1;
    private int dice2;
    private boolean won;

    @ManyToOne(fetch = FetchType.LAZY) // Entities relationship
    @JoinColumn(name = "player_id")
    @JsonIgnore // To fix issue with infinite recursion
    private Player player;

    // CONSTRUCTORS

    public DiceGame() {
    }

    public DiceGame(Player player) {
        this.player = player;
    }


    public DiceGame(String gameId, int dice1, int dice2, boolean won, Player player) {
        this.gameId = gameId;
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.won = won;
        this.player = player;
    }

    // METHODS

    // Check if dice roll was won (sum of dices must be 7 in order to win)

    public void rollTheDices() {
        this.dice1 = getRandomNumber();
        this.dice2 = getRandomNumber();
        calculateResult();
    }

    public void calculateResult() {
        if (this.dice1 + this.dice2 == 7) {
            this.won = true;
        } else {
            this.won = false;
        }
    }

    public int getRandomNumber() {
        return (int) Math.floor(Math.random() * 6 + 1);
    }
}
