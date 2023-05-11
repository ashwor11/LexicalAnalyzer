import com.sun.istack.internal.NotNull;

public abstract class Token {

    public int row;
    public int column;

    public String value;

    public Token(int row, int column){
        this.row = row;
        this.column = column;
    }
    public Token(int row, int column, String value){
        this.row = row;
        this.column = column;
        this.value = value;
    }


}
