package Exceptions;

import Tokens.Token;

public class TokenException extends Exception{
    public int row;
    public int column;
    public String token;

    public TokenException(int row, int column, String token ){
        this.row = row;
        this.column = column;
        this.token = token;
    }
    public TokenException(Token token){
        this.row = token.row;
        this.column = token.column;
        this.token = token.value;
    }
}
