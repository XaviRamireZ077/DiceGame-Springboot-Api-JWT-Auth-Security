package S05T02N01.DiceGame.Model.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Random;

@Getter
@Setter
@Entity
@Table(name = "DiceGame")
public class DiceGame implements Serializable {

    private static final long serialVersionUID = 1L;
    private static String Status = "WON";
    private static String Status2 = "LOST";
    private Random random = new Random();


    // ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dice_1") // Column name in database
    private int dice1;
    @Column(name = "dice_2") // Column name in database
    private int dice2;
    @Column(name = "won")
    private boolean won;


    @ManyToOne(fetch = FetchType.LAZY) // Entities relationship
    @JsonIgnore // To fix issue with infinite recursion
    @JoinColumn(name = "player_id")
    private Player player;

    // CONSTRUCTORS

    public DiceGame() {
    }

    public DiceGame(Player player) {
        this.player = player;
    }


    public DiceGame(Long id, int dice1, int dice2, boolean won, Player player) {
        this.id = id;
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.won = won;
        this.player = player;
    }

    // METHODS


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
        //Random rand = new Random();
        int rand = random.nextInt(6) + 1;
        return rand;
        //int random = (int) Math.floor(Math.random() * 6 + 1);
        //return random;
    }
}

