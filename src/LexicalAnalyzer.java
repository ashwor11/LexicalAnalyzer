import Exceptions.LexicalErrorException;
import Helpers.LexicalHelper;
import Tokens.Token;

import java.util.ArrayList;

public class LexicalAnalyzer {
    public static ArrayList<Token> Tokens = new ArrayList<Token>();

        public static void Analyse(String code){

            //initializing variables
            int curColumn = 0;
            int curRow = 0;
            String token = "";
            boolean inString = false; //if its true the spaces in string does not seperate tokens
            boolean escape = false; // if its true following " does not break the string


            int length = code.length();

            try{
                for (int i = 0; i < length; i++, curColumn++) {
                    if (code.charAt(i) == '~') { //if its comment starter
                        i++;
                        if (i < length) {
                            char currChart = code.charAt(i); //get next char if its possible(not the end of the string)
                        }

                        while (code.charAt(i) != '\n' && i < length) { //ignore the chars until the end of the line
                            i++;
                            if (i < length) {
                                char currChart = code.charAt(i);
                            }
                        }

                    }

                    if (code.charAt(i) == '\n') {                // when reached to newline char
                        if (!token.isEmpty()) {                // if there is a token create the token(which means "token" variable is not empty)
                            int column = curColumn - token.length(); //to get the starting index of the token subtract its length from its last chars index

                            LexicalHelper.GenerateMulticharTokenAndAddToList(curRow, column, token, Tokens);

                        }
                        token = "";              //because of the fact that we come to the end of the line. reinitialize values
                        curColumn = 0;           //initializing curColumn to 0 because at the end of the for loop curColumn already increased by 1
                        curRow++;
                        continue;                //rest is not important
                    }
                    else if (code.charAt(i) == '\\') { //if char is \ escape becomes !escape
                        escape = !escape;
                    } else if (code.charAt(i) == '\"') { //if char is "
                        if (!escape) {              // if escape is false the string starts or ends
                            inString = !inString;   //so changing the value of inString
                        }

                    } else if (escape) {    // if the previous char was a \
                        escape = false;
                    }
                    if (LexicalHelper.IsSingleCharTokenValue(code.charAt(i)) && !inString )// if its one of the single char tokens values
                    {
                        if (!token.isEmpty()) { // because of the fact a single token and multi token side by side if token is not empty we should create a new multi char token
                            int column = curColumn - token.length(); //get start index of token

                            LexicalHelper.GenerateMulticharTokenAndAddToList(curRow, column, token, Tokens); // create multichar token and add to list

                        }

                        char value = code.charAt(i);
                        LexicalHelper.GenerateSinglecharTokenAndAddToList(curRow,curColumn,value,Tokens); //create singlechar token and add to list
                        token = "";

                    }//single char token end

                    else if (code.charAt(i) == ' ')      //if current char space
                    {
                        if (inString) { //if its in string just add space in to the string
                            token += ' ';
                        } else if (!inString && !token.isEmpty()) { // if its not in string and token is not empty which means there is a multi char before the space
                            int column = curColumn - token.length(); // get the multichar tokens start index

                            LexicalHelper.GenerateMulticharTokenAndAddToList(curRow, column, token, Tokens); //create multichar token and add to list

                            token = ""; //reinitialize the token to empty
                        }
                        //there is no else for space so if token is empty(which means there were a space before current space) so this space just for indendation and spacing
                    }

                    else {  //if current char is not a space, not a single char token value just simply add this char to current token
                        token += code.charAt(i);
                    }

                }

            }catch (LexicalErrorException exception){
                System.out.println(exception.toString());
            }



        }

        public String TokensToString(){
            String output = "";
            for (Token token:
                 Tokens) {
                output += token.toString() +"\n";
            }
            return output;
        }

        public ArrayList<Token> getTokens(){
            return Tokens;
        }
}
