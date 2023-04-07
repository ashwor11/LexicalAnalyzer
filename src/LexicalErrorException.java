public class LexicalErrorException extends Exception{

    public int row;
    public int column;
    public String token;

    public LexicalErrorException(int row, int column, String token ){
        this.row = row;
        this.column = column;
        this.token = token;
    }
    @Override
    public String toString() {
        return "LEXICAL ERROR [" + this.row + ":" + this.column + "]:  Invalid token `" + this.token + "'";
    }
}
