public class SingleCharToken extends Token{
    public enum SingleCharTokenTYPE{
        LEFTPAR,
        RIGHTPAR,
        LEFTSQUAREB,
        RIGHTSQUAREB,
        LEFTCURLYB,
        RIGHTCURLYB
    }
    public SingleCharTokenTYPE TokenType;
    public SingleCharToken(int row, int column, SingleCharTokenTYPE tokenType) {
        super(row, column);
        this.TokenType = tokenType;
    }
    public SingleCharToken(int row, int column, String value, SingleCharTokenTYPE tokenType){
        super(row, column, value);
        this.TokenType = tokenType;
    }

    @Override
    public String toString() {
        return this.TokenType.toString() + " " + this.row +":" + this.column;
    }
}
