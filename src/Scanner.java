

import Exceptions.LexicalErrorException;
import Exceptions.ParserErrorException;
import Tokens.SingleCharToken;

import java.io.*;

public class Scanner {

    public static void main(String[] args)  {

        if(args.length != 2){
            System.out.println("Usage: NameOfTheProgram [fileName]");
            return;
        }
        String fileName = args[1];

        String code = getInput(fileName);

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
        lexicalAnalyzer.Analyse(code);
        String output = lexicalAnalyzer.TokensToString();

        SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer.getTokens());
        syntaxAnalyzer.AnalyzeSyntax();
        writeOutput(syntaxAnalyzer.toString());



    }

    public static String getInput(String fileName){
        String code = "";
        try {
            FileReader fileReader = new FileReader("./"+fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            bufferedReader.close();
            code = sb.toString();
        } catch (IOException e) {
            System.out.println("Could not find the spesific file." + e.getMessage());
        }
        return code;
    }
    public static void writeOutput(String output){
        try {
            FileWriter fileWriter = new FileWriter("./output.txt");
            fileWriter.write(output);
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Can not write to the file. " + e.getMessage());
        }
    }


}

