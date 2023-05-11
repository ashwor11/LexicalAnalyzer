package Helpers;

import Exceptions.ParserErrorException;
import Tokens.MultiCharToken;
import Tokens.SingleCharToken;
import Tokens.Token;

import java.util.ArrayList;

import static Tokens.MultiCharToken.MultiCharTokenTYPE.DEFINE;
import static Tokens.MultiCharToken.MultiCharTokenTYPE.IDENTIFIER;
import static Tokens.SingleCharToken.SingleCharTokenTYPE.LEFTPAR;
import static Tokens.SingleCharToken.SingleCharTokenTYPE.RIGHTPAR;


public class ParserHelper {
    private ArrayList<Token> Tokens;
    private Token currentToken;
    private int length;
    private int pointer = -1;

    private StringBuilder output =new StringBuilder("");

    private int tabCounter = 0;
    public ParserHelper (ArrayList<Token> Tokens){
        this.Tokens = Tokens;
        this.length = Tokens.size();
        this.getNextToken();
    }

    public void startAnalyze()  {
        try{
            Program();
        }catch (ParserErrorException parserErrorException){
            output.append(parserErrorException.toString() +"\n");
        }

    }

    private void Program() throws ParserErrorException {
        Programi();

    }

    private void Programi() throws ParserErrorException {
        constructOutputDeeper("Program");
        if(TopLevelForm()){
            Programi();
        }else{
            incrementTabCounter();
            appendEpsilon();
        }
        decreaseTabCounter();

    }

    private boolean TopLevelForm() throws ParserErrorException {
        if(!match(SingleCharToken.SingleCharTokenTYPE.LEFTPAR))
            return false;
        constructOutputDeeper("TopLevelForm");
        constructOutputToken(currentToken);
        getNextToken();
        SecondLevelForm();
        if(!match(RIGHTPAR))
            throw new ParserErrorException(currentToken, RIGHTPAR);
        constructOutputToken(currentToken);
        getNextToken();
        decreaseTabCounter();
        return true;

    }
    private void SecondLevelForm() throws ParserErrorException{
        constructOutputDeeper("SecondLevelForm");
        if(match(SingleCharToken.SingleCharTokenTYPE.LEFTPAR)){
            constructOutputToken(currentToken);
            getNextToken();
            FunCall();
            if(!match(RIGHTPAR))
                throw new ParserErrorException(currentToken, RIGHTPAR);
            constructOutputToken(currentToken);
            getNextToken();
        }
        Definition();
        decreaseTabCounter();

    }
    private boolean Definition() throws ParserErrorException{
        constructOutputDeeper("Definition");
        if(!match(DEFINE))
            throw new ParserErrorException(currentToken, DEFINE);
        constructOutputToken(currentToken);
        getNextToken();
        DefinitionRight();
        decreaseTabCounter();
        return true;
    }

    private void DefinitionRight() throws ParserErrorException{
        if(match(MultiCharToken.MultiCharTokenTYPE.IDENTIFIER)){
            decreaseTabCounter();
            constructOutputToken(currentToken);
            getNextToken();
            Expression();
            decreaseTabCounter();

        }else if(match(SingleCharToken.SingleCharTokenTYPE.LEFTPAR)){
            decreaseTabCounter();
            constructOutputToken(currentToken);
            getNextToken();
            if(!match(MultiCharToken.MultiCharTokenTYPE.IDENTIFIER))
                throw new ParserErrorException(currentToken, IDENTIFIER);
            constructOutputToken(currentToken);
            getNextToken();
            ArgList();
            if(!match(RIGHTPAR))
                throw new ParserErrorException(currentToken, RIGHTPAR);
            constructOutputToken(currentToken);
            getNextToken();
            Statements();
            decreaseTabCounter();


        }else
            throw new ParserErrorException(currentToken, "'(' or IDENTIFIER");
    }

    private void ArgList() throws ParserErrorException{
        constructOutputDeeper("ArgList");

        if(match(MultiCharToken.MultiCharTokenTYPE.IDENTIFIER)){
            constructOutputToken(currentToken);
            getNextToken();
            ArgList();

        }else{
            appendEpsilon();

        }
        decreaseTabCounter();

    }

    private void Statements() throws ParserErrorException{
        constructOutputDeeper("Statements");
        if(!Expression()){
            Definition();
            Statements();
        }
        decreaseTabCounter();

    }

    private void FunCall() throws ParserErrorException{
        constructOutputDeeper("FunCall");
        if(!match(MultiCharToken.MultiCharTokenTYPE.IDENTIFIER))
            throw new ParserErrorException(currentToken, IDENTIFIER);
        constructOutputToken(currentToken);
        getNextToken();
        Expressions();
        decreaseTabCounter();
    }
    private void Expressions() throws ParserErrorException{
        constructOutputDeeper("Expressions");
        if(Expression()){
            Expressions();
        }else{
            appendEpsilon();
        }
        decreaseTabCounter();

    }
    private boolean Expression() throws ParserErrorException{


        boolean condition = (match(MultiCharToken.MultiCharTokenTYPE.IDENTIFIER) || match(MultiCharToken.MultiCharTokenTYPE.NUMBER) || match(MultiCharToken.MultiCharTokenTYPE.CHAR) ||
                match(MultiCharToken.MultiCharTokenTYPE.BOOLEAN) || match(MultiCharToken.MultiCharTokenTYPE.STRING)) ||match(SingleCharToken.SingleCharTokenTYPE.LEFTPAR);
        if(!condition)
            return false;

        constructOutputDeeper("Expression");

        if(match(MultiCharToken.MultiCharTokenTYPE.IDENTIFIER) || match(MultiCharToken.MultiCharTokenTYPE.NUMBER) || match(MultiCharToken.MultiCharTokenTYPE.CHAR) ||
                match(MultiCharToken.MultiCharTokenTYPE.BOOLEAN) || match(MultiCharToken.MultiCharTokenTYPE.STRING)){
            constructOutputToken(currentToken);
            getNextToken();

        }else if(match(SingleCharToken.SingleCharTokenTYPE.LEFTPAR)) {
            constructOutputToken(currentToken);
            getNextToken();
            Expr();
            if(!match(RIGHTPAR))
                throw new ParserErrorException(currentToken, RIGHTPAR);
            constructOutputToken(currentToken);
            getNextToken();
        }
        decreaseTabCounter();
        return true;
    }

    private void Expr() throws ParserErrorException{

        boolean condition = match(MultiCharToken.MultiCharTokenTYPE.LET) || match(MultiCharToken.MultiCharTokenTYPE.COND) || match(MultiCharToken.MultiCharTokenTYPE.IF) ||
                match(MultiCharToken.MultiCharTokenTYPE.BEGIN) || match(MultiCharToken.MultiCharTokenTYPE.IDENTIFIER);

        if(!condition)
            throw new ParserErrorException(currentToken, "LET | COND | IF | BEGIN | IDENTIFIER");

        constructOutputDeeper("Expr");
        if(match(MultiCharToken.MultiCharTokenTYPE.LET))
            LetExpression();
        else if(match(MultiCharToken.MultiCharTokenTYPE.COND))
            CondExpression();
        else if(match(MultiCharToken.MultiCharTokenTYPE.IF))
            IfExpression();
        else if(match(MultiCharToken.MultiCharTokenTYPE.BEGIN))
            BeginExpression();
        else if(match(MultiCharToken.MultiCharTokenTYPE.IDENTIFIER))
            FunCall();
        decreaseTabCounter();


    }

    private void LetExpression() throws ParserErrorException{
        constructOutputDeeper("LetExpression");

        constructOutputToken(currentToken);
        getNextToken();
        LetExpr();
        decreaseTabCounter();


    }

    private void LetExpr() throws ParserErrorException{

        boolean condition = match(MultiCharToken.MultiCharTokenTYPE.IDENTIFIER) || match(SingleCharToken.SingleCharTokenTYPE.LEFTPAR);

        if(!condition)
            throw new ParserErrorException(currentToken, "IDENTIFIER | '('");

        constructOutputDeeper("LetExpr");

        if(match(MultiCharToken.MultiCharTokenTYPE.IDENTIFIER)){
            constructOutputToken(currentToken);
            getNextToken();
            if(!match(SingleCharToken.SingleCharTokenTYPE.LEFTPAR))
                throw new ParserErrorException(currentToken, LEFTPAR);
            constructOutputToken(currentToken);
            getNextToken();
            if(!VarDefs())
                throw new ParserErrorException(currentToken, "VarDefs");
            if(!match(RIGHTPAR))
                throw new ParserErrorException(currentToken, RIGHTPAR);
            constructOutputToken(currentToken);
            getNextToken();
            Statements();

        }else if(match(SingleCharToken.SingleCharTokenTYPE.LEFTPAR)){
            constructOutputToken(currentToken);
            getNextToken();
            VarDefs();
            if(!match(RIGHTPAR))
                throw new ParserErrorException(currentToken, RIGHTPAR);
            constructOutputToken(currentToken);
            getNextToken();
            Statements();

        }
        decreaseTabCounter();


    }
    private boolean VarDefs() throws ParserErrorException{
        if(!match(SingleCharToken.SingleCharTokenTYPE.LEFTPAR))
            return false;
        constructOutputDeeper("VarDefs");
        constructOutputToken(currentToken);
        getNextToken();
        if(!match(MultiCharToken.MultiCharTokenTYPE.IDENTIFIER))
            throw new ParserErrorException(currentToken, IDENTIFIER);
        constructOutputToken(currentToken);
        getNextToken();
        Expression();
        if(!match(RIGHTPAR))
            throw new ParserErrorException(currentToken, RIGHTPAR);
        constructOutputToken(currentToken);
        getNextToken();
        VarDef();
        decreaseTabCounter();
        return true;

    }

    private void VarDef() throws ParserErrorException {
        constructOutputDeeper("VarDef");
        if(!VarDefs()){
            appendEpsilon();
        }
        decreaseTabCounter();

    }

    private void CondExpression() throws ParserErrorException{
        constructOutputDeeper("CondExpression");
        constructOutputToken(currentToken);
        getNextToken();
        CondBranches();
        decreaseTabCounter();



    }

    private void CondBranches() throws ParserErrorException{
        if(!match(SingleCharToken.SingleCharTokenTYPE.LEFTPAR))
            throw new ParserErrorException(currentToken, LEFTPAR);
        constructOutputToken(currentToken);
        getNextToken();
        Expression();
        Statements();
        if(!match(RIGHTPAR))
            throw new ParserErrorException(currentToken, RIGHTPAR);
        constructOutputToken(currentToken);
        getNextToken();
        //TO DO HERE NOT CLEARE INFORMATION ON PDF ( I THINK IT WOULD BE COND BRANCH)

        decreaseTabCounter();


    }

    private void CondBranch() throws  ParserErrorException{
        if(!match(SingleCharToken.SingleCharTokenTYPE.LEFTPAR))
            return;
        constructOutputDeeper("CondBranch");
        if(match(SingleCharToken.SingleCharTokenTYPE.LEFTPAR)){
            constructOutputToken(currentToken);
            getNextToken();
            Expression();
            Statements();
            if(!match(RIGHTPAR))
                throw new ParserErrorException(currentToken, RIGHTPAR);
            constructOutputToken(currentToken);
            getNextToken();
        }
        decreaseTabCounter();

    }

    private void IfExpression() throws ParserErrorException{
        constructOutputDeeper("IfExpression");
        constructOutputToken(currentToken);
        getNextToken();
        Expression();
        Expression();
        EndExpression();
        decreaseTabCounter();


    }

    private void EndExpression() throws ParserErrorException{
        boolean expressionCondition = (match(MultiCharToken.MultiCharTokenTYPE.IDENTIFIER) || match(MultiCharToken.MultiCharTokenTYPE.NUMBER) || match(MultiCharToken.MultiCharTokenTYPE.CHAR) ||
                match(MultiCharToken.MultiCharTokenTYPE.BOOLEAN) || match(MultiCharToken.MultiCharTokenTYPE.STRING)) ||match(SingleCharToken.SingleCharTokenTYPE.LEFTPAR);
        if(expressionCondition){
            constructOutputDeeper("EndExpression");
            Expression();
            decreaseTabCounter();

        }


    }

    private void BeginExpression() throws ParserErrorException{
        constructOutputDeeper("BeginExpression");
        constructOutputToken(currentToken);
        getNextToken();
        Statements();
        decreaseTabCounter();



    }



    private boolean match(SingleCharToken.SingleCharTokenTYPE tokenTYPE){
        if(currentToken.getTokenType() == tokenTYPE)
            return true;
        return false;
    }
    private boolean match(MultiCharToken.MultiCharTokenTYPE tokenTYPE){
        if(currentToken.getTokenType() == tokenTYPE)
            return true;
        return false;
    }
    private boolean match(){

        return false;
    }


    private boolean getNextToken(){
        pointer++;
        if(this.pointer >= length)
            return false;
        this.currentToken = Tokens.get(pointer);
        return true;
    }

    private void constructOutputDeeper(String rule){
        for (int i = 0; i < tabCounter; i++) {
            output.append("\t");
        }
        output.append("<"+rule+">\n");
        tabCounter++;
    }
    private void constructOutputUpper(String rule){
        tabCounter--;
        for (int i = 0; i < tabCounter; i++) {
            output.append("\t");
        }
        output.append("<"+rule+">\n");
    }
    private void constructOutputToken(Token token){
        for (int i = 0; i < tabCounter; i++) {
            output.append("\t");
        }
        output.append(token.getTokenType().toString() + " ("+token.value+")\n");
    }

    private void appendEpsilon(){
        for (int i = 0; i < tabCounter; i++) {
            output.append("\t");
        }
        output.append("__\n");
    }
    private void decreaseTabCounter(){tabCounter--;}
    private void incrementTabCounter(){tabCounter++;}

    @Override
    public String toString() {
        return output.toString();
    }
}
