package Exceptions;

import Constants.TokenTypeToString;
import Tokens.Token;

public class ParserErrorException extends TokenException{

    public Enum expectedToken = null;
    private String errorDefinition = "";
    public ParserErrorException(int row, int column, String token ){
        super(row,column,token);
    }

    public ParserErrorException(Token token){
        super(token);
    }
    public ParserErrorException(Token token, Enum expectedToken){
        super(token);
        this.expectedToken = expectedToken;
    }
    public ParserErrorException(Token token, String errorDefinition){
        super(token);
        this.errorDefinition = errorDefinition;
    }
    private String getExpectedToken(){
        return TokenTypeToString.get(expectedToken);
    }

    @Override
    public String toString() {
        if(expectedToken != null)
            return "SYNTAX ERROR ["+this.row+ ":"+this.column +"]: '"+getExpectedToken()+"' is expected";
        else{
            return "SYNTAX ERROR ["+this.row+ ":"+this.column +"]: '"+this.errorDefinition+"' is expected";

        }
    }
}
