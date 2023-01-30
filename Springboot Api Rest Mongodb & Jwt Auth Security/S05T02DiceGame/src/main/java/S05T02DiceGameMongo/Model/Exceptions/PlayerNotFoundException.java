package S05T02DiceGameMongo.Model.Exceptions;

public class PlayerNotFoundException extends RuntimeException{

    public PlayerNotFoundException (String msg){
        super(msg);
    }
}
