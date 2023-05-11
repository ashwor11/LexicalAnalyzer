package Helpers;

import Exceptions.LexicalErrorException;
import Tokens.MultiCharToken;
import Tokens.SingleCharToken;
import Tokens.Token;

import java.util.ArrayList;

public class LexicalHelper {

    public static boolean IsString(String token){

        if(!token.startsWith("\"")) return false;
        if(!token.endsWith("\"")) return false;

        int x =1;
        int length = token.length();
        boolean escape = false;

        char currChar = token.charAt(x);
        while ( currChar> -1 && currChar<256){
            if(currChar == '\\'){
                escape = !escape;
            }else if(currChar == '\"' && !escape){
                break;
            }else{
                escape = false;
            }
            x++;
            currChar = token.charAt(x);
        }
        x++;
        if(length != x) return false;

        return true;

    }
    public static boolean IsNumber(String token){

        try {
            int i;
            char currChar;
            int length = token.length();

            if(token.startsWith("0x")){
                i = 2;
                currChar = token.charAt(i);
                while( (currChar >= '0' && currChar <= '9' || currChar >= 'a' && currChar <= 'f' || currChar >= 'A' && currChar <= 'F')  ){

                    i++;
                    if(length == i){
                        break;
                    }
                    currChar = token.charAt(i);
                }
                if(length != i || i == 2) return false;
                return true;
            }  //hexadecimal unsigned integer

            else if(token.startsWith("0b")){ //binary unsigned integer
                i = 2;
                currChar = token.charAt(i);
                while( currChar >= '0' && currChar <= '1' ){

                    i++;
                    if(length == i){
                        break;
                    }
                    currChar = token.charAt(i);
                }
                if(length != i || i == 2) return false;
                return true;
            }
            else // int and floating points
            {
                i = 0;
                currChar = token.charAt(i);
                if(token.startsWith("-") || token.startsWith("+")) {
                    i = 1;
                    currChar = token.charAt(i);
                }
                while( currChar >= '0' && currChar <= '9'){

                    i++;
                    if(length == i){
                        break;
                    }
                    currChar = token.charAt(i);
                }
                //if(i == 2) return false;

                if(i == length)return true; //integer
                currChar = token.charAt(i);

                if(currChar == '.'){ // floating v.1
                    i++;
                    currChar = token.charAt(i);
                    int firstDecimalPoint = i;

                    while( currChar >= '0' && currChar <= '9'){

                        i++;
                        if(length == i){
                            break;
                        }
                        currChar = token.charAt(i);
                    }
                    if(i >= firstDecimalPoint || i == length) return true;

                    currChar = token.charAt(i);
                    if(currChar != '+') return false;
                    i++;
                    if(i == length) return true;
                    currChar = token.charAt(i);

                    if(!(currChar == 'e' || currChar == 'E')) return false;
                    i++;
                    currChar = token.charAt(i);
                    if(currChar == '-' || currChar =='+') {
                        i++;
                        currChar = token.charAt(i);
                    }
                    if(!(currChar > '0' && currChar < '9')) return false;
                    i++;
                    currChar = token.charAt(i);

                    while( currChar >= '0' && currChar <= '9'){

                        i++;
                        if(length == i){
                            break;
                        }
                        currChar = token.charAt(i);
                    }

                    if(i == length) return true;

                    return false;


                }

                else if(currChar == 'e' || currChar == 'E'){ // floating point v.2
                    i++; currChar = token.charAt(i);
                    if(currChar == '+' || currChar == '-'){
                        i++; currChar = token.charAt(i);
                    }
                    while( currChar >= '0' && currChar <= '9'){

                        i++;
                        if(length == i){
                            break;
                        }
                        currChar = token.charAt(i);
                    }
                    if(length != i || i == 2) return false;
                    return true;

                }else return false;

            }


        }catch (StringIndexOutOfBoundsException exception){
            return false;
        }


    }
    public static boolean IsBool(String token){
        if(token.equals("#t") || token.equals("#f")) return true;
        return false;
    }
    public static boolean IsChar(String token){
        if(token.equals("#\\newline")) return true;
        if(token.equals("#\\space")) return true;
        if(token.equals("#\\tab")) return true;

        if(token.charAt(0) != '#') return false;
        if(token.charAt(1) != '\\') return false;
        if(token.charAt(2) >= 0 && token.charAt(2) <= 255) return true;

        return false;
    }
    public static boolean IsKeyword(String token){
        if(token.equals("lambda") || token.equals("define") || token.equals("let") || token.equals("cond")
                || token.equals("if") || token.equals("begin") || token.equals("quote")){
            return true;
        }
        return false;
    }
    public static boolean IsIdentifier(String token){
        if( token.equals("define") || token.equals("let") || token.equals("cond")
                || token.equals("if") || token.equals("begin") ||  token.equals("false") || token.equals("true")){
            return false;
        }

        int length = token.length();
        char currChar = token.charAt(0);
        if(!(currChar >= 'a' && currChar <= 'z' || currChar >= 'A' && currChar <= 'Z' || currChar=='.' || currChar=='+'|| currChar=='-' || currChar=='!' || currChar=='*' || currChar=='/' || currChar==':' || currChar=='<' || currChar=='=' || currChar=='>' || currChar=='?')) {

            return false;
        }

        int i = 1;
        if(i < length){
            currChar = token.charAt(i);
        }
        while(i <length){
            if(!((!(currChar >= 'a' && currChar <= 'z') && !(currChar >= 'A' && currChar <= 'Z')) || currChar != '.' || currChar != '+' || currChar != '-' ||
                    !(currChar >= '0' && currChar <= '9'))) {

                return false;
            }
            i++;
            if(i < length){
                currChar = token.charAt(i);
            }
        }
        return true;


    }


    public static MultiCharToken GenerateKeywordToken(int row, int column, String value) {
        MultiCharToken.MultiCharTokenTYPE tokenTYPE = MultiCharToken.MultiCharTokenTYPE.KEYWORD;
        if (value.equals("define")) tokenTYPE = MultiCharToken.MultiCharTokenTYPE.DEFINE;
        else if (value.equals("let")) tokenTYPE = MultiCharToken.MultiCharTokenTYPE.LET;
        else if (value.equals("cond")) tokenTYPE = MultiCharToken.MultiCharTokenTYPE.COND;
        else if (value.equals("if")) tokenTYPE = MultiCharToken.MultiCharTokenTYPE.IF;
        else if (value.equals("begin")) tokenTYPE = MultiCharToken.MultiCharTokenTYPE.BEGIN;
        MultiCharToken token = new MultiCharToken(row, column, value, tokenTYPE);
        return token;
    }
    public static void PrintTokens(ArrayList<Token> tokens) {
        tokens.forEach(token -> System.out.println(token.toString()));
    }

    public static void GenerateSinglecharTokenAndAddToList(int row, int column, char token, ArrayList<Token> tokens){
        if (token == '(')
            tokens.add(new SingleCharToken(row, column, "(", SingleCharToken.SingleCharTokenTYPE.LEFTPAR));
        else if (token == ')')
            tokens.add(new SingleCharToken(row, column, ")", SingleCharToken.SingleCharTokenTYPE.RIGHTPAR));
        else if (token == '[')
            tokens.add(new SingleCharToken(row, column, "[", SingleCharToken.SingleCharTokenTYPE.LEFTSQUAREB));
        else if (token == ']')
            tokens.add(new SingleCharToken(row, column, "]", SingleCharToken.SingleCharTokenTYPE.RIGHTSQUAREB));
        else if (token == '{')
            tokens.add(new SingleCharToken(row, column, "{", SingleCharToken.SingleCharTokenTYPE.LEFTCURLYB));
        else if (token == '}')
            tokens.add(new SingleCharToken(row, column, "}", SingleCharToken.SingleCharTokenTYPE.RIGHTCURLYB));
    }
    public static void GenerateMulticharTokenAndAddToList(int row, int column, String token, ArrayList<Token> tokens) throws LexicalErrorException {

        if (LexicalHelper.IsString(token))
            tokens.add(new MultiCharToken(row, column, token, MultiCharToken.MultiCharTokenTYPE.STRING));
        else if (LexicalHelper.IsNumber(token))
            tokens.add(new MultiCharToken(row, column, token, MultiCharToken.MultiCharTokenTYPE.NUMBER));
        else if (LexicalHelper.IsIdentifier(token))
            tokens.add(new MultiCharToken(row, column, token, MultiCharToken.MultiCharTokenTYPE.IDENTIFIER));
        else if (LexicalHelper.IsChar(token))
            tokens.add(new MultiCharToken(row, column, token, MultiCharToken.MultiCharTokenTYPE.CHAR));
        else if (LexicalHelper.IsKeyword(token)) tokens.add(GenerateKeywordToken(row, column, token));
        else if (LexicalHelper.IsBool(token))
            tokens.add(new MultiCharToken(row, column, token, MultiCharToken.MultiCharTokenTYPE.BOOLEAN));
        else {
            throw new LexicalErrorException(row, column, token);
        }
    }
    public static boolean IsSingleCharTokenValue(char value){
        return (value  == '(' || value  == ')' || value  == '{' || value  == '}' || value  == '[' || value  == ']');
    }

}


