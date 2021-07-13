package utils;

import java.util.Arrays;

public class StringUtil {
    
    public static String replicate(Character c, int n) {
        char[] charArray = new char[n];
        Arrays.fill(charArray,c);
        return new String(charArray);
    }    
}
