package Constants;

import Tokens.MultiCharToken;
import Tokens.SingleCharToken;

import java.util.HashMap;
import java.util.Map;

public class TokenTypeToString {
    private static final Map<Enum,String> map = new HashMap<>();
    static{
        map.put(MultiCharToken.MultiCharTokenTYPE.NUMBER,"NUMBER");
        map.put(MultiCharToken.MultiCharTokenTYPE.BOOLEAN,"BOOLEAN");
        map.put(MultiCharToken.MultiCharTokenTYPE.CHAR,"CHAR");
        map.put(MultiCharToken.MultiCharTokenTYPE.STRING,"STRING");
        map.put(MultiCharToken.MultiCharTokenTYPE.DEFINE,"DEFINE");
        map.put(MultiCharToken.MultiCharTokenTYPE.LET,"LET");
        map.put(MultiCharToken.MultiCharTokenTYPE.COND,"COND");
        map.put(MultiCharToken.MultiCharTokenTYPE.IF,"IF");
        map.put(MultiCharToken.MultiCharTokenTYPE.BEGIN,"BEGIN");
        map.put(MultiCharToken.MultiCharTokenTYPE.IDENTIFIER,"IDENTIFIER");
        map.put(MultiCharToken.MultiCharTokenTYPE.KEYWORD,"KEYWORD");
        map.put(SingleCharToken.SingleCharTokenTYPE.LEFTPAR,"(");
        map.put(SingleCharToken.SingleCharTokenTYPE.RIGHTPAR,")");
        map.put(SingleCharToken.SingleCharTokenTYPE.LEFTSQUAREB,"[");
        map.put(SingleCharToken.SingleCharTokenTYPE.RIGHTSQUAREB,"]");
        map.put(SingleCharToken.SingleCharTokenTYPE.LEFTCURLYB,"{");
        map.put(SingleCharToken.SingleCharTokenTYPE.RIGHTCURLYB,"}");
    }
    public static String get(Enum key){
        return map.get(key);
    }

}
