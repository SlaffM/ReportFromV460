package sample.Helpers;

import sample.v460.ResourceBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helpers {

    public static boolean isMatrixStartsAlarmClass(String srcMatrix){
        return (srcMatrix.startsWith("ОС_") ||
                srcMatrix.startsWith("ПС1_") ||
                srcMatrix.startsWith("ПС2_") ||
                srcMatrix.startsWith("АС_"));
    }

    public static boolean isMatrixEndsWithAlarmClass(String srcMatrix){
        return (srcMatrix.endsWith("_BM") ||
                srcMatrix.startsWith("_DM") ||
                srcMatrix.startsWith("_GM1") ||
                srcMatrix.startsWith("_GM2") ||
                srcMatrix.endsWith("_GM3"));
    }

    public static String getTextWithPattern(String text, String pattern){
        Pattern p = Pattern.compile(pattern, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = p.matcher(text);

        if(matcher.find()){
            return matcher.group(1);
        }else{
            return "";
        }
    }

    public static boolean isBeanSprecon850Driver(ResourceBean resourceBean){
        String findHex = getTextWithPattern(resourceBean.getRecourcesLabel(), "(\\d{2}\\.)+\\d{2}");
        return !findHex.isEmpty();
    }

}
