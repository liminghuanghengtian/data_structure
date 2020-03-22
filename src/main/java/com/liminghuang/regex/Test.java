package com.liminghuang.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang
 * Description:
 * <p>
 * CreateTime: 2019/12/28 20:35
 * Modifier: Adaministrator
 * ModifyTime: 2019/12/28 20:35
 * Comment:
 *
 * @author Adaministrator
 */
public class Test {
    private static Pattern sPattern = Pattern.compile("0+");
    
    private static boolean isAllNumericZero(String str) {
        Matcher isAllNumZero = sPattern.matcher(str);
        return isAllNumZero.matches();
    }
    
    public static void main(String[] arg) {
        System.out.println(isAllNumericZero("00000000000000000000001000000"));
        System.out.println(isAllNumericZero("00000000000"));
        System.out.println(isAllNumericZero("0"));
        System.out.println(isAllNumericZero(""));
        System.out.println(isAllNumericZero("1110"));
        System.out.println(isAllNumericZero("111"));
    }
}
