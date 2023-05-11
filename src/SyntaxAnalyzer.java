import Exceptions.ParserErrorException;
import Helpers.ParserHelper;
import Tokens.Token;

import java.util.ArrayList;

public class SyntaxAnalyzer {
    private ParserHelper parserHelper;

    public SyntaxAnalyzer(ArrayList<Token> tokens){
        parserHelper = new ParserHelper(tokens);
    }


    public void AnalyzeSyntax() {
        parserHelper.startAnalyze();

    }

    @Override
    public String toString() {
        return parserHelper.toString();
    }
}
