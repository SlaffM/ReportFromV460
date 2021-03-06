package reportV460.Helpers;

import reportV460.v460.ResourceBean;

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
                srcMatrix.endsWith("_DM") ||
                srcMatrix.endsWith("_GM1") ||
                srcMatrix.endsWith("_GM2") ||
                srcMatrix.endsWith("_GM3"));
    }

    public static String getTextWithPattern(String text, String pattern){

        if (text == null) {return "";}

        Pattern p = Pattern.compile(pattern, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = p.matcher(text);

        if(matcher.find()){
            return matcher.group(1);
        }else{
            return "";
        }
    }

    public static boolean isBeanSprecon850Driver(ResourceBean resourceBean){
        String findHex = getTextWithPattern(resourceBean.getRecourcesLabel(), "(\\w{2}\\.\\w{2}\\.\\w{2}\\.\\w{2})");
        return !findHex.isEmpty();
    }

    public static boolean tryParseInt(String value){
        try{
            String num = Helpers.getTextWithPattern(value, "(\\d+)");
            if (num.isEmpty()) { return false;}
            Integer.parseInt(num);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }



}
