package S05T02DiceGameMongo.Model.Exceptions;

public class PlayerNameAlreadyExistsException extends RuntimeException{

    public PlayerNameAlreadyExistsException (String msg){
        super(msg);
    }

}
