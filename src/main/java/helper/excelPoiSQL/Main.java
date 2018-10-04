package helper.excelPoiSQL;

import java.io.IOException;
import java.util.*;

/**
 * Created by c0240259 on 22/06/2017.
 */
public class Main {
    //http://www.oracle.com/technetwork/articles/java/architect-streams-pt2-2227132.html
    //https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html

    public static void main(String[] args) throws IOException {


        List<Map<String,String>> ResultTableList;
        try {
////******Horizontal****************************************************************************************************

//            //Page ObjectName ObjectPropetyType	ObjectPropetyValue	TestCase1	TestCase2
//            eSQL eSQLObj = new eSQL("eSQLTestData//eSQLTestData1.xlsx");
//
//            //Set
//            Set<String> SelectColumnSet = new LinkedHashSet<String>();
//            SelectColumnSet.add("ObjectName");
//            SelectColumnSet.add("ObjectPropetyValue");
//            SelectColumnSet.add("Page");
//
//            //List
//            List<String> SelectColumnList = new LinkedList<String>();
//            SelectColumnList.add("TestCase1");
//            SelectColumnList.add("Page");
//
//            //String
//            String SelectColumnString = "ObjectPropetyValue,TestCase1,Page,ObjectName";
//
//
//
////            eSQLObj.FROM("MoonSheetHorizontal", false);
////            eSQLObj.SELECT(SelectColumnString);
//            //eSQLObj.SELECT("*");
////             eSQLObj.SELECT(SelectColumnSet);
//            //eSQLObj.SELECT(SelectColumnList);
////            eSQLObj.WHERE("Page = 'P3'");
////            ResultTableList = eSQLObj.EXE();
//
//
//              eSQLObj.FROM("MoonSheetHorizontal", false);
//              ResultTableList = eSQLObj.SELECT("Page").EXE();
////            ResultTableList = eSQLObj.SELECT("*").WHERE("Page = 'P8'").EXE();
////            eSQLObj.SELECT(SelectColumnString);
////            eSQLObj.WHERE("ObjectName = 'ON2'").EXE().stream().forEach(System.out::println);
////            eSQLObj.WHERE("ObjectName = 'ON4'").EXE().stream().forEach(System.out::println);
////            eSQLObj.WHERE("ObjectName = 'ON5'").EXE().stream().forEach(System.out::println);
////            eSQLObj.WHERE("Page = 'P3'").EXE().stream().forEach(System.out::println);
//
//
////            ResultTableList = eSQLObj.EXE();
////
////
//            Iterator<Map<String,String>> ResultTableListIterator =  ResultTableList.iterator();
//            while (ResultTableListIterator.hasNext()){
//                Map<String,String> ResultTableMap = new LinkedHashMap<String, String>();
//                ResultTableMap = ResultTableListIterator.next();
//                System.out.println(ResultTableMap);
//            }



//********Vertical***********************************************************************************************************

            //Page ObjectName ObjectPropetyType	ObjectPropetyValue	TestCase1	TestCase2
            eSQL eSQLObj = new eSQL("eSQLTestData//eSQLTestData1.xlsx");

            //Set
            Set<String> SelectColumnSet = new LinkedHashSet<String>();
            SelectColumnSet.add("ObjectName");
            SelectColumnSet.add("ObjectPropetyValue");

            //List
            List<String> SelectColumnList = new LinkedList<String>();
            SelectColumnList.add("TestCase1");
            SelectColumnList.add("Page");

            //String
            String SelectColumnString = "ObjectPropetyValue,TestCase1,Page,ObjectName";

            eSQLObj.FROM("MoonSheetVertical", true);
            eSQLObj.SELECT("*");
//            eSQLObj.SELECT(SelectColumnString);
            //eSQLObj.WHERE("Page = 'P3'");
            eSQLObj.WHERE("Page = 'P3' || ObjectPropetyValue = 'OPY' || TestCase2 = 'TC26'");
//"Page = 'P3' || TestCase1 = 'TC13' || ObjectPropetyType = 'OPT3'"
            ResultTableList = eSQLObj.EXE();

            Iterator<Map<String, String>> ResultTableListIterator = ResultTableList.iterator();
            while (ResultTableListIterator.hasNext()) {
                Map<String, String> ResultTableMap = new LinkedHashMap<String, String>();
                ResultTableMap = ResultTableListIterator.next();
                System.out.println(ResultTableMap);
            }

//********************************************************************************************************************
        }catch (eSQLException e){
            System.out.println(e);
        }
    }
}
