package helper.excelPoiSQL;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by c0240259 on 22/06/2017.
 */
public class eSQLPattern {

    //http://www.regular-expressions.info/repeat.html
    //https://docs.oracle.com/javase/tutorial/essential/regex/quant.html

    public List getSelectColumnList(String Column) throws eSQLException {

        //String ReGex = "^\\s*([a-zA-Z][a-zA-Z0-9]*)\\s*,((\\s*[a-zA-Z][a-zA-Z0-9]*\\s*,)*)\\s*([a-zA-Z][a-zA-Z0-9]*)\\s*$";
        String ReGex = "^((\\s*[a-zA-Z][a-zA-Z0-9]*\\s*,)*)\\s*([a-zA-Z][a-zA-Z0-9]*)\\s*$";
        Pattern pattern = Pattern.compile(ReGex);
        Matcher matcher = pattern.matcher(Column);
        List ColumnList = new ArrayList<String>();
        if (matcher.find()) {
            String ReGexSub = "\\s*([a-zA-Z][a-zA-Z0-9]*)\\s*,";
            Pattern patternSub = Pattern.compile(ReGexSub);
            Matcher matcherSub = patternSub.matcher(matcher.group(1));
            while(matcherSub.find()) {
                ColumnList.add(matcherSub.group(1));
            }
            ColumnList.add(matcher.group(3));
        } else {
            throw new eSQLException("SELECT->Incorrect parameter Syntax");
        }
        return ColumnList;
    }

    public Map getWhereFilterMap(String Filter) throws eSQLException {
        Map<String,Map<String,String>> FilterMap = new LinkedHashMap();
        Map<String,String> AndFilterMap = new LinkedHashMap();
        if (Filter != null) {
            String ReGex = "^(\\s*[a-zA-Z][a-zA-Z0-9]*\\s*=\\s*'[^']+'\\s*\\|\\|)*\\s*[a-zA-Z][a-zA-Z0-9]*\\s*=\\s*'[^']+'\\s*$";
            Pattern pattern = Pattern.compile(ReGex);
            Matcher matcher = pattern.matcher(Filter);
            if (matcher.find()) {
                String ReGexSub = "\\s*([a-zA-Z][a-zA-Z0-9]*)\\s*=\\s*'([^']+)'\\s*";
                Pattern patternSub = Pattern.compile(ReGexSub);
                Matcher matcherSub = patternSub.matcher(Filter);
                while (matcherSub.find()) {
                    AndFilterMap.put(matcherSub.group(1), matcherSub.group(2));
                }
                FilterMap.put("||", AndFilterMap);
            }
            else {
                throw new eSQLException("WHERE->Incorrect parameter Syntax");
            }
        }else
        {FilterMap = null;}
        return FilterMap;
    }
}


//            String ReGex = "^\\s*([a-zA-Z][a-zA-Z0-9]*)\\s*=\\s*'([a-zA-Z][a-zA-Z0-9]*)'\\s*$";
//            if (matcher.find()) {
//                String[] SubFilters = Filter.split("\\|\\|");
//                String ReGexSub = "^\\s*([a-zA-Z][a-zA-Z0-9]*)\\s*=\\s*'([a-zA-Z][a-zA-Z0-9]*)'\\s*$";
//                for (String SubFilter : SubFilters) {
//                    Pattern patternSub = Pattern.compile(ReGexSub);
//                    Matcher matcherSub = patternSub.matcher(SubFilter);
//                    if (matcherSub.find()) {
//                        FilterMap.put(matcherSub.group(1), matcherSub.group(2));
//                    }
//                }
//                FilterMap.entrySet().forEach(System.out::println);
//            }
