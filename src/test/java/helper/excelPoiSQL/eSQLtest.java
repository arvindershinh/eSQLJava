package helper.excelPoiSQL;

import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by c0240259 on 22/06/2017.
 */
public class eSQLtest {
    private List<Map<String, String>> ActualResult;

//******Horizontal****************************************************************************************************
    //Page ObjectName ObjectPropetyType	ObjectPropetyValue	TestCase1	TestCase2

    @Test
    public void HorizontalExcel_Select_Set() throws IOException, eSQLException {

        //Expected Result
        List<Map<String,String>> ExpectedResult = new ArrayList<Map<String,String>>();

        Map<String,String> m1 =  new LinkedHashMap<String,String>();
        m1.put("Page", "P5");
        m1.put("ObjectName", "ON5");
        m1.put("ObjectPropetyValue", "OPY6");
        ExpectedResult.add(m1);

        //Coulmn Set
        Set<String> SelectColumnSet = new LinkedHashSet<String>();
        SelectColumnSet.add("Page");
        SelectColumnSet.add("ObjectName");
        SelectColumnSet.add("ObjectPropetyValue");

        eSQL eSQLObj1 = new eSQL("eSQLTestData//eSQLTestData1.xlsx");
        //eSQL Query with Select Set
        eSQLObj1.FROM("MoonSheetHorizontal", false);
        eSQLObj1.SELECT(SelectColumnSet);
        eSQLObj1.WHERE("Page = 'P5'");
        ActualResult = eSQLObj1.EXE();
        assertTrue(ActualResult.equals(ExpectedResult));
    }

    @Test
    public void HorizontalExcel_Select_List() throws IOException, eSQLException {

        //Expected Result
        List<Map<String,String>> ExpectedResult = new ArrayList<Map<String,String>>();

        Map<String,String> m1 =  new LinkedHashMap<String,String>();
        m1.put("Page", "P1");
        m1.put("TestCase1", "TC13");
        ExpectedResult.add(m1);

        Map<String,String> m2 =  new LinkedHashMap<String,String>();
        m2.put("Page", "P3");
        m2.put("TestCase1", "TC13");
        ExpectedResult.add(m2);

        Map<String,String> m3 =  new LinkedHashMap<String,String>();
        m3.put("Page", "P7");
        m3.put("TestCase1", "TC13");
        ExpectedResult.add(m3);

        //Coulmn List
        List<String> SelectColumnList = new LinkedList<String>();
        SelectColumnList.add("Page");
        SelectColumnList.add("TestCase1");

        eSQL eSQLObj2 = new eSQL("eSQLTestData//eSQLTestData1.xlsx");
        //eSQL Query with Select List
        eSQLObj2.FROM("MoonSheetHorizontal", false);
        eSQLObj2.SELECT(SelectColumnList);
        eSQLObj2.WHERE("TestCase1 = 'TC13'");
        ActualResult = eSQLObj2.EXE();
        assertTrue(ActualResult.equals(ExpectedResult));
    }

    @Test
    public void HorizontalExcel_Select_String_All_Where_OneAnd() throws IOException, eSQLException {

        //Expected Result
        List<Map<String,String>> ExpectedResult = new ArrayList<Map<String,String>>();

        Map<String,String> m1 =  new LinkedHashMap<String,String>();
        m1.put("Page", "P3");
        m1.put("ObjectName", "ON3");
        m1.put("ObjectPropetyType", "OPT3");
        m1.put("ObjectPropetyValue", "OPY6");
        m1.put("TestCase1", "TC13");
        m1.put("TestCase2", "TC23");
        ExpectedResult.add(m1);

        eSQL eSQLObj3 = new eSQL("eSQLTestData//eSQLTestData1.xlsx");
        //eSQL Query with * and Where with 1 And
        eSQLObj3.FROM("MoonSheetHorizontal", false);
        eSQLObj3.SELECT("*");
        eSQLObj3.WHERE("ObjectPropetyValue = 'OPY6' || TestCase1 = 'TC13'");
        ActualResult = eSQLObj3.EXE();
        assertTrue(ActualResult.equals(ExpectedResult));
    }


    @Test
    public void HorizontalExcel_Select_String_multipleColumn_Where_MultipleAnd() throws IOException, eSQLException {

        //Expected Result
        List<Map<String,String>> ExpectedResult = new ArrayList<Map<String,String>>();

        Map<String,String> m1 =  new LinkedHashMap<String,String>();
        m1.put("Page", "P3");
        m1.put("ObjectName", "ON6");
        m1.put("ObjectPropetyValue", "OPY6");
        m1.put("TestCase1", "TC16");
        m1.put("TestCase2", "TC26");
        ExpectedResult.add(m1);

        eSQL eSQLObj4 = new eSQL("eSQLTestData//eSQLTestData1.xlsx");
        //eSQL Query with multiple columns and Where with multiple And
        eSQLObj4.FROM("MoonSheetHorizontal", false);
        eSQLObj4.SELECT("Page,ObjectName,ObjectPropetyValue,TestCase1,TestCase2");
        eSQLObj4.WHERE("Page = 'P3' || ObjectPropetyValue = 'OPY6' || TestCase2 = 'TC26'");
        ActualResult = eSQLObj4.EXE();
        assertTrue(ActualResult.equals(ExpectedResult));
    }




//******Vertical****************************************************************************************************

    @Test
    public void VerticalExcel_Select_Set() throws IOException, eSQLException {
        //Expected Result
        List<Map<String,String>> ExpectedResult = new ArrayList<Map<String,String>>();

        Map<String,String> m1 =  new LinkedHashMap<String,String>();
        m1.put("Page", "P5");
        m1.put("ObjectName", "ON5");
        m1.put("ObjectPropetyValue", "OPY6");
        ExpectedResult.add(m1);

        //Coulmn Set
        Set<String> SelectColumnSet = new LinkedHashSet<String>();
        SelectColumnSet.add("Page");
        SelectColumnSet.add("ObjectName");
        SelectColumnSet.add("ObjectPropetyValue");

        eSQL eSQLObj5 = new eSQL("eSQLTestData//eSQLTestData1.xlsx");
        //eSQL Query with Select Set
        eSQLObj5.FROM("MoonSheetVertical", true);
        eSQLObj5.SELECT(SelectColumnSet);
        eSQLObj5.WHERE("Page = 'P5'");
        ActualResult = eSQLObj5.EXE();
        assertTrue(ActualResult.equals(ExpectedResult));
    }

    @Test
    public void VerticalExcel_Select_List() throws IOException, eSQLException {

        //Expected Result
        List<Map<String,String>> ExpectedResult = new ArrayList<Map<String,String>>();

        Map<String,String> m1 =  new LinkedHashMap<String,String>();
        m1.put("Page", "P1");
        m1.put("TestCase1", "TC13");
        ExpectedResult.add(m1);

        Map<String,String> m2 =  new LinkedHashMap<String,String>();
        m2.put("Page", "P3");
        m2.put("TestCase1", "TC13");
        ExpectedResult.add(m2);

        Map<String,String> m3 =  new LinkedHashMap<String,String>();
        m3.put("Page", "P7");
        m3.put("TestCase1", "TC13");
        ExpectedResult.add(m3);

        //Coulmn List
        List<String> SelectColumnList = new LinkedList<String>();
        SelectColumnList.add("Page");
        SelectColumnList.add("TestCase1");

        eSQL eSQLObj6 = new eSQL("eSQLTestData//eSQLTestData1.xlsx");
        //eSQL Query with Select List
        eSQLObj6.FROM("MoonSheetVertical", true);
        eSQLObj6.SELECT(SelectColumnList);
        eSQLObj6.WHERE("TestCase1 = 'TC13'");
        ActualResult = eSQLObj6.EXE();
        assertTrue(ActualResult.equals(ExpectedResult));
    }

    @Test
    public void VerticalExcel_Select_String_All_Where_OneAnd() throws IOException, eSQLException {

        //Expected Result
        List<Map<String,String>> ExpectedResult = new ArrayList<Map<String,String>>();

        Map<String,String> m1 =  new LinkedHashMap<String,String>();
        m1.put("Page", "P3");
        m1.put("ObjectName", "ON3");
        m1.put("ObjectPropetyType", "OPT3");
        m1.put("ObjectPropetyValue", "OPY6");
        m1.put("TestCase1", "TC13");
        m1.put("TestCase2", "TC23");
        ExpectedResult.add(m1);

        eSQL eSQLObj7 = new eSQL("eSQLTestData//eSQLTestData1.xlsx");
        //eSQL Query with * and Where with 1 And
        eSQLObj7.FROM("MoonSheetVertical", true);
        eSQLObj7.SELECT("*");
        eSQLObj7.WHERE("ObjectPropetyValue = 'OPY6' || TestCase1 = 'TC13'");
        ActualResult = eSQLObj7.EXE();
        assertTrue(ActualResult.equals(ExpectedResult));
    }


    @Test
    public void VerticalExcel_Select_String_multipleColumn_Where_MultipleAnd() throws IOException, eSQLException {

        //Expected Result
        List<Map<String,String>> ExpectedResult = new ArrayList<Map<String,String>>();

        Map<String,String> m1 =  new LinkedHashMap<String,String>();
        m1.put("Page", "P3");
        m1.put("ObjectName", "ON6");
        m1.put("ObjectPropetyValue", "OPY6");
        m1.put("TestCase1", "TC16");
        m1.put("TestCase2", "TC26");
        ExpectedResult.add(m1);

        eSQL eSQLObj8 = new eSQL("eSQLTestData//eSQLTestData1.xlsx");
        //eSQL Query with multiple columns and Where with multiple And
        eSQLObj8.FROM("MoonSheetVertical", true);
        eSQLObj8.SELECT("Page,ObjectName,ObjectPropetyValue,TestCase1,TestCase2");
        eSQLObj8.WHERE("Page = 'P3' || ObjectPropetyValue = 'OPY6' || TestCase2 = 'TC26'");
        ActualResult = eSQLObj8.EXE();
        assertTrue(ActualResult.equals(ExpectedResult));
    }




//******Horizontal-Vertical****************************************************************************************************

    @Test
    public void Verify_Horizontal_Equal_Vertical() throws IOException, eSQLException {
        List<Map<String, String>> ResultHorizontal;
        eSQL eSQLObj9 = new eSQL("eSQLTestData//eSQLTestData1.xlsx");
        eSQLObj9.FROM("MoonSheetHorizontal", false);
        eSQLObj9.SELECT("*");
        ResultHorizontal = eSQLObj9.EXE();

        List<Map<String, String>> ResultVertical;
        eSQL eSQLObj10 = new eSQL("eSQLTestData//eSQLTestData1.xlsx");
        eSQLObj10.FROM("MoonSheetVertical", true);
        eSQLObj10.SELECT("*");
        ResultVertical = eSQLObj10.EXE();

        assertTrue(ResultHorizontal.equals(ResultVertical));
    }


//***********Blank Row/Column/Cell**************************************************************************************************

    public List<Map<String, String>> TestHorizontal() throws IOException, eSQLException {
        List<Map<String, String>> Result;
        eSQL eSQLObj10 = new eSQL("eSQLTestData//eSQLTestData2.xlsx");
        eSQLObj10.FROM("MoonSheetHorizontal", false);
        eSQLObj10.SELECT("*");
        Result = eSQLObj10.EXE();
        return Result;
    }


    public List<Map<String, String>> TestVertical() throws IOException, eSQLException {
        List<Map<String, String>> Result;
        eSQL eSQLObj10 = new eSQL("eSQLTestData//eSQLTestData2.xlsx");
        eSQLObj10.FROM("MoonSheetVertical", true);
        eSQLObj10.SELECT("*");
        Result = eSQLObj10.EXE();
        return Result;
    }

    @Test
    public void TestHorizontalVertical() throws IOException, eSQLException {
        List ResultHorizontal;
        List ResultVertical;
        ResultHorizontal = TestHorizontal();
        ResultVertical = TestVertical();
        assertTrue(ResultHorizontal.equals(ResultVertical));
    }

}