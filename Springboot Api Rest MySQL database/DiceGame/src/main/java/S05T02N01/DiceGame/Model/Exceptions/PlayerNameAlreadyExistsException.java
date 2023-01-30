package S05T02N01.DiceGame.Model.Exceptions;

public class PlayerNameAlreadyExistsException extends RuntimeException{
    public PlayerNameAlreadyExistsException(String s) {
        super(s);

    }
}
