package io.github.yukkuritaku.maidgradle.loom.util;

public class Utils {


    public static String lastStr(String regex, String inputStr){
        String[] split = inputStr.split(regex);
        return split[split.length - 1];
    }
}
