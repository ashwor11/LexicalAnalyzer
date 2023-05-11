package Exceptions;

import Tokens.Token;

public class LexicalErrorException extends TokenException{

    public LexicalErrorException(int row, int column, String token ){
        super(row,column,token);

    }
    public LexicalErrorException(Token token){
        super(token);
    }
    @Override
    public String toString() {
        return "LEXICAL ERROR [" + this.row + ":" + this.column + "]:  Invalid token `" + this.token + "'";
    }
}
