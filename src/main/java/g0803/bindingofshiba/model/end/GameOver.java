package g0803.bindingofshiba.model.end;

public class GameOver {

    private final boolean victory;

    public GameOver(boolean victory) {
        this.victory = victory;
    }

    public boolean isVictory() {
        return victory;
    }
}
