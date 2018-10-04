package helper.excelPoiSQL;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

/**
 * Created by c0240259 on 22/06/2017.
 */
public class eSQL {
    private String Column = null;
    private String Table = null;
    private List<String> SelectColumnList = null;
    private List<String> FromColumnList;
    private List<Map<String,String>> DataTableList;
    private Map<String, Map<String,String>> WhereFilterMap;
    private String ExcelPath;

    public eSQL(String ExcelPath) throws IOException {
        this.ExcelPath = ExcelPath;
    }

    public eSQL SELECT(String Column) throws eSQLException {
        this.Column = Column;
        if (Column != "*") {
            eSQLPattern pattern = new eSQLPattern();
            SelectColumnList = pattern.getSelectColumnList(Column);
        }
        return this;
    }

    public <String> eSQL SELECT(Collection<String> collectionColumn) throws eSQLException {
        if (collectionColumn instanceof LinkedHashSet || collectionColumn instanceof HashSet || collectionColumn instanceof TreeSet) {
            SelectColumnList = (ArrayList<java.lang.String>) new ArrayList<String>((collectionColumn));
        }
        if (collectionColumn instanceof ArrayList){
            SelectColumnList = (ArrayList<java.lang.String>) collectionColumn;
        }
        if (collectionColumn instanceof LinkedList){
            SelectColumnList = (ArrayList<java.lang.String>) new ArrayList<String>(collectionColumn);
        }
        return this;
    }


    public eSQL FROM(String Table, Boolean Transpose) throws eSQLException, IOException {
        this.Table = Table;
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(ExcelPath).getFile());
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheet(Table);
        FromColumnList = new ArrayList<String>();
        DataFormatter formatter = new DataFormatter();


        if (Transpose == false) {
            
            int metaDataRowNum = 0;
            Integer emptyColumnIterator=0;
            XSSFRow metaDataRow = sheet.getRow(metaDataRowNum);
            int metaDataLastCellNum = metaDataRow.getLastCellNum();

            int dataRowStart = metaDataRowNum+1;
            int dataRowEnd = sheet.getLastRowNum();

            for (int metaDataCellIndex = 0; metaDataCellIndex < metaDataLastCellNum; metaDataCellIndex++) {
                XSSFCell metaDataCell = metaDataRow.getCell(metaDataCellIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                if(metaDataCell == null) {
                    FromColumnList.add("emptyColumn".concat(emptyColumnIterator.toString()));
                    emptyColumnIterator++;
                }else{
                    String cellContent = formatter.formatCellValue(metaDataCell);
                    if (cellContent == "") {
                        FromColumnList.add("emptyColumn".concat(emptyColumnIterator.toString()));
                        emptyColumnIterator++;
                    }else {FromColumnList.add(cellContent);
                    }
                }
            }

            DataTableList = new ArrayList<Map<String, String>>();
            for (int rowNum = dataRowStart; rowNum <= dataRowEnd; rowNum++) {
                XSSFRow dataRow = sheet.getRow(rowNum);
                    if (dataRow != null) {
                        Map<String,String> dataRowMap = new LinkedHashMap();
                        for (int dataCellIndex = 0; dataCellIndex < FromColumnList.size(); dataCellIndex++) {
                            XSSFCell dataCell = dataRow.getCell(dataCellIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                            if (dataCell == null) {
                                dataRowMap.put(FromColumnList.get(dataCellIndex), "");
                            } else {
                                String cellContent = formatter.formatCellValue(dataCell);
                                dataRowMap.put(FromColumnList.get(dataCellIndex), cellContent);
                            }
                        }
                        DataTableList.add(dataRowMap);
                    }
            }
        }


        if (Transpose == true) {
            int metaDataRowNum = 0;
            Integer emptyColumnIterator=0;
            int dataRowStart = metaDataRowNum+1;
            int dataRowEnd = sheet.getRow(0).getLastCellNum();
            int columnStart = 0;
            int columnEnd = sheet.getLastRowNum();

            for (int columnNum = columnStart; columnNum <= columnEnd; columnNum++) {
                XSSFRow FromColumn = sheet.getRow(columnNum);
                if (FromColumn == null) {
                    FromColumnList.add("emptyColumn".concat(emptyColumnIterator.toString()));
                    emptyColumnIterator++;
                }else{
                    XSSFCell metaDataCell = FromColumn.getCell(metaDataRowNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    if (metaDataCell == null) {
                        FromColumnList.add("emptyColumn".concat(emptyColumnIterator.toString()));
                        emptyColumnIterator++;
                    }else{
                        String cellContent = formatter.formatCellValue(metaDataCell);
                        if (cellContent == "") {
                            FromColumnList.add("emptyColumn".concat(emptyColumnIterator.toString()));
                            emptyColumnIterator++;
                        }else {FromColumnList.add(cellContent);}
                    }
                }
            }

            DataTableList = new ArrayList<Map<String, String>>();
            for (int dataRow = dataRowStart; dataRow < dataRowEnd; dataRow++) {
                Map dataRowMap = new LinkedHashMap();
                for (int columnNum = columnStart; columnNum < FromColumnList.size(); columnNum++) {
                    XSSFRow FromColumn = sheet.getRow(columnNum);
                    if (FromColumn == null) {
                        dataRowMap.put(FromColumnList.get(columnNum), "");
                    } else {
                        XSSFCell dataCell = FromColumn.getCell(dataRow, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        if (dataCell == null) {
                            dataRowMap.put(FromColumnList.get(columnNum), "");
                        } else {
                            String cellContent = formatter.formatCellValue(dataCell);
                            dataRowMap.put(FromColumnList.get(columnNum), cellContent);
                        }
                    }
                }
                DataTableList.add(dataRowMap);
            }
        }


        return this;
    }


    public eSQL FROM(ArrayList<Map<String,String>> DataTableList){
        this.DataTableList = DataTableList;
        return this;
    }


    public eSQL WHERE(String Filter) throws eSQLException{
        eSQLPattern pattern= new eSQLPattern();
        WhereFilterMap = pattern.getWhereFilterMap(Filter);
        return this;
    }

    public List<Map<String,String>> EXE() throws eSQLException {
        List<Map<String,String>> ResultTableList = new ArrayList<Map<String,String>>();

        if (Column == "*") {
            SelectColumnList = FromColumnList;
        }

        if (SelectColumnList == null || Table == null){
            throw new eSQLException("Incorrect order of eSQL. SELECT And/Or FROM were not called before EXE or were called with null value");
        }

        if (!FromColumnList.containsAll(SelectColumnList)){
            throw new eSQLException("Incorrect SELECT clause -> One or more column name is incorrect");
        }

        if (WhereFilterMap!=null) {
            if (!FromColumnList.containsAll(new ArrayList<>(WhereFilterMap.get("||").keySet()))) {
                throw new eSQLException("Incorrect WHERE clause -> One or more column name is incorrect");
            }
        }

        if ((FromColumnList.equals(SelectColumnList)) && (WhereFilterMap==null)) {
            ResultTableList = DataTableList;
        }

        if ((FromColumnList.containsAll(SelectColumnList)) && (FromColumnList.size() > SelectColumnList.size()) && (WhereFilterMap==null)) {
            Predicate<Map.Entry<String, String>> p = q -> false;
            for (String selectColumn : SelectColumnList) {p = p.or(q -> q.getKey().equals(selectColumn));}
            Predicate<Map.Entry<String, String>> h = p;
            Function< Map<String,String>, Map<String,String>> f = m -> m.entrySet().stream().filter(h).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (String a, String b) -> a, LinkedHashMap::new));
            ResultTableList = DataTableList.stream().map(x -> f.apply(x)).collect(toCollection(ArrayList::new));
        }

        if ((FromColumnList.equals(SelectColumnList)) && (WhereFilterMap!=null)) {
            ResultTableList = DataTableList.stream().filter(k->k.entrySet().containsAll(WhereFilterMap.get("||").entrySet())).collect(toCollection(ArrayList::new));
        }

        if ((FromColumnList.containsAll(SelectColumnList)) && (FromColumnList.size() > SelectColumnList.size()) && (WhereFilterMap!=null)) {
            Predicate<Map.Entry<String, String>> p = q -> false;
            for (String selectColumn : SelectColumnList) {p = p.or(q -> q.getKey().equals(selectColumn));}
            Predicate<Map.Entry<String, String>> h = p;
            Function< Map<String,String>, Map<String,String>> f = m -> m.entrySet().stream().filter(h).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (String a, String b) -> a, LinkedHashMap::new));
            ResultTableList = DataTableList.stream().filter(k->k.entrySet().containsAll(WhereFilterMap.get("||").entrySet())).map(x -> f.apply(x)).collect(toCollection(ArrayList::new));
        }
        return ResultTableList;
    }

    public void INSERT(String Table, int row, int column, String Data) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(ExcelPath).getFile());
        FileInputStream fileInputStream = new FileInputStream(file);

        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheet(Table);

        XSSFRow dataRow = sheet.createRow(row);
        XSSFCell cell = dataRow.createCell(column);
        cell.setCellValue(Data);
        fileInputStream.close();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }

    public void GET_LAST_ROW(String Table) throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(ExcelPath).getFile());
        FileInputStream fileInputStream = new FileInputStream(file);

        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheet(Table);

        DataFormatter formatter = new DataFormatter();
        int dataRowEnd = sheet.getLastRowNum();

        }


}
