public class MultiCharToken extends Token{
    public enum MultiCharTokenTYPE{
        NUMBER,
        BOOLEAN,
        CHAR,
        STRING,
        DEFINE,
        LET,
        COND,
        IF,
        BEGIN,
        IDENTIFIER,
        KEYWORD
    }
    public MultiCharTokenTYPE TokenType;
    public MultiCharToken(int row, int column, MultiCharTokenTYPE TokenType) {
        super(row, column);
        this.TokenType = TokenType;
    }

    public MultiCharToken(int row, int column, String value, MultiCharTokenTYPE TokenType) {
        super(row, column, value);
        this.TokenType = TokenType;

    }

    @Override
    public String toString() {
        return this.TokenType.toString() + " " + this.row +":" + this.column;
    }
}
