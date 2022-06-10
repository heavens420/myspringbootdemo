package com.es.eslearn.util;



import java.io.*;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Description:工具类
 * @ClassName EsUtil
 * @Author whj
 * @date 2020.12.12 14:02
 */
public class ToolsUtil {


    /**
     * 判断字符串引用不为null并且值不为空
     *
     * @param orgStr
     * @return
     */
    public static boolean isNotNullAndNotEmpty(String orgStr) {

        return (orgStr != null && orgStr.trim().length() != 0 && (!"null".equals(orgStr)));

    }

    /**
     * 判断Object引用不为null且值不为空
     *
     * @param obj
     * @return
     */
    public static boolean isNotNullAndNotEmpty(Object obj) {
        if (obj instanceof Object[]) {
            Object[] o = (Object[]) obj;
            for (int i = 0; i < o.length; i++) {
                Object object = o[i];
                if ((object != null) && (!("").equals(object)) && (!"null".equals(obj))) {
                    return true;
                }
            }
        } else if (obj instanceof Map) {
            return !((Map) obj).isEmpty();
        } else {
            if ((obj != null) && (!("").equals(obj)) && (!"null".equals(obj))) {
                return true;
            }
        }


        return false;
    }

    /**
     * 判断字符串引用为null或值为空
     *
     * @param orgStr
     * @return
     */
    public static boolean isNullOrEmpty(String orgStr) {
        return (orgStr == null || orgStr.trim().length() == 0 || ("null".equals(orgStr)));
    }

    /**
     * 判断Object引用为null或者值为空
     *
     * @param obj
     * @return
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj instanceof Object[]) {
            Object[] o = (Object[]) obj;
            for (int i = 0; i < o.length; i++) {
                Object object = o[i];
                if ((object == null) || (("").equals(object)) || ("null".equals(obj))) {
                    return true;
                }
            }
        } else if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        } else {
            if ((obj == null) || (("").equals(obj)) || ("null".equals(obj))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 不同类型获取X轴
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static List<String> getDays(String startTime, String endTime, String dayType) {

        List<String> days = new ArrayList<String>();//返回的时间list
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if ("0".equals(dayType)) {
            //今日
            String time = dateFormat.format(new Date());
            for (int i = 0; i < 24; i++) {
                String times = "";
                if (i < 10) {
                    times = "0" + i;
                } else {
                    times = i + "";
                }
                days.add(time + " " + times + "时");
            }

        } else if ("1".equals(dayType)) {
            //昨日
            Date today = new Date();
            Date yestoday = new Date(today.getTime() - 24 * 3600 * 1000);
            String time = dateFormat.format(yestoday);
            for (int i = 0; i < 24; i++) {
                String times = "";
                if (i < 10) {
                    times = "0" + i;
                } else {
                    times = i + "";
                }
                days.add(time + " " + times + "时");
            }

        } else if ("2".equals(dayType) || "3".equals(dayType)) {
            //近一周或近一月
            days = getDayList(startTime, endTime);

        } else {
            //时间范围
            int betweenDays = DateUtil.daysBetween(startTime, endTime);
            if (betweenDays > 3) {
                days = getDayList(startTime, endTime);
            } else {
                days = getDayHourList(startTime, endTime);
            }

        }

        return days;
    }

    /**
     * 得到天数+小时list
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getDayHourList(String startTime, String endTime) {
        List<String> days = new ArrayList<String>();//返回的时间list
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                int hourBegin = Integer.parseInt(startTime.substring(11, 13));
                int hourEnd = Integer.parseInt(endTime.substring(11, 13));
                if (dateFormat.format(tempStart.getTime()).substring(0, 10).equals(startTime.substring(0, 10))) {
                    for (int hour = hourBegin; hour < 24; hour++) {
                        String times = "";
                        if (hour < 10) {
                            times = "0" + hour;
                        } else {
                            times = hour + "";
                        }
                        days.add(dateFormat.format(tempStart.getTime()).substring(0, 10) + " " + times + "时");
                    }
                } else if (!(dateFormat.format(tempStart.getTime()).substring(0, 10).equals(startTime.substring(0, 10))) &&
                        !(dateFormat.format(tempStart.getTime()).substring(0, 10).equals(endTime.substring(0, 10)))) {
                    for (int hour = 0; hour < 24; hour++) {
                        String times = "";
                        if (hour < 10) {
                            times = "0" + hour;
                        } else {
                            times = hour + "";
                        }
                        days.add(dateFormat.format(tempStart.getTime()).substring(0, 10) + " " + times + "时");
                    }
                } else if (dateFormat.format(tempStart.getTime()).substring(0, 10).equals(endTime.substring(0, 10))) {
                    for (int hour = 0; hour <= (hourEnd + 1); hour++) {
                        String times = "";
                        if (hour < 10) {
                            times = "0" + hour;
                        } else {
                            times = hour + "";
                        }
                        days.add(dateFormat.format(tempStart.getTime()).substring(0, 10) + " " + times + "时");
                    }
                    break;
                }
                tempStart.add(Calendar.DAY_OF_YEAR, 1);


            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;

    }


    /**
     * 得到天数+星期list
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getDayList(String startTime, String endTime) {
        List<String> days = new ArrayList<String>();//返回的时间list
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()) + getWeekOfDate(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;

    }

    /**
     * 获取周几
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }
}
//
///************************************excel导出********************/
//
//    /**
//     * @param sheetName       生成Excel的sheet名称
//     * @param firstHead       第一行表头
//     * @param secondHead      第二行表头
//     * @param dataList        导出数据列表
//     * @param isMultiTitle    是否复合表头
//     * @param dataKeys    	  Map中的key值
//     * @return
//     */
//    public static XSSFWorkbook createWorkBook(String sheetName, String[] firstHead, String[][] secondHead, List<Map<String, Object>> dataList, boolean isMultiTitle, String[] dataKeys, int[] widthValues){
//        XSSFRow firstRow = null;
//        XSSFRow secondRow = null;
//        XSSFCell cell = null;
//        XSSFWorkbook workBook = null;
//        int colIndex = 0;
//        int dataIndex = 1;
//        workBook = new XSSFWorkbook();
//        //创建Sheet
//        XSSFSheet sheet = workBook.createSheet(sheetName);// 表名
//        firstRow = sheet.createRow(0);
//        secondRow = sheet.createRow(1);
//        int firstIndex = 0;
//
//        //设置标题背景色并居中显示
//        XSSFCellStyle style = workBook.createCellStyle();
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        //style.setFillForegroundColor(IndexedColors.RED.index);
//        style.setFillForegroundColor(IndexedColors.WHITE1.index);
//        style.setFillBackgroundColor(IndexedColors.WHITE.index);
//        style.setBorderBottom(BorderStyle.THIN); //下边框
//        style.setBorderLeft(BorderStyle.THIN);//左边框
//        style.setBorderTop(BorderStyle.THIN);//上边框
//        style.setBorderRight(BorderStyle.THIN);//右边框
//
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//
//
//        if(!isMultiTitle){
//            for(int j=0; j<firstHead.length; j++){
//                cell = createCell(firstRow, workBook, firstIndex++);
//                cell.setCellType(CellType.STRING);
//                cell.setCellValue(firstHead[j]);
//                cell.setCellStyle(style);
//            }
//        }else{
//            for(int i=0; i<secondHead.length; i++){
//                for(int j=0; j<secondHead[i].length; j++){
//                    cell = createCell(firstRow, workBook, colIndex);
//                    cell.setCellType(CellType.STRING);
//                    cell.setCellValue(firstHead[i]);
//
//                    cell = createCell(secondRow, workBook, colIndex++);
//                    cell.setCellType(CellType.STRING);
//                    cell.setCellValue(secondHead[i][j]);
//                    cell.setCellStyle(style);
//                }
//            }
//        }
//        //如果是复合表头则设置表头合并单元格
//        if(isMultiTitle){
//            int mergeCol = 0;
//            for(int i=0; i<secondHead.length; i++){
//                if(secondHead[i].length > 1){
//                    sheet.addMergedRegion(new CellRangeAddress(0, 0, mergeCol, mergeCol + secondHead[i].length - 1));
//                }else{
//                    sheet.addMergedRegion(new CellRangeAddress(0, 1, mergeCol, mergeCol));
//                }
//                mergeCol += secondHead[i].length;
//            }
//            dataIndex++;
//        }
//
//        //设置行高
//        firstRow.setHeightInPoints(25);
//        secondRow.setHeightInPoints(25);
//
//        style = workBook.createCellStyle();
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        XSSFRow dataRow = null;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dataValue = "";
//        //写入数据
//        for(int i=0; i<dataList.size(); i++){
//            dataRow = sheet.createRow(dataIndex + i);
//            dataRow.setHeightInPoints(20);
//            Map<String, Object> map = dataList.get(i);
//            for(int j=0; j<dataKeys.length; j++){
//                cell = createCell(dataRow, workBook, j);
//                cell.setCellType(CellType.STRING);
//                Object value = map.get(dataKeys[j]);
//                if (value instanceof Date){
//                    Date date=(Date) value;
//                    dataValue = sdf.format(date);
//                } else {
//                    dataValue = null == value ? "" : value.toString();
//                }
//                cell.setCellValue(dataValue);
//                cell.setCellStyle(style);
//            }
//
//        }
//        //设置导出Excel的列宽
//        for(int k=0; k<widthValues.length; k++){
//            sheet.setColumnWidth(k, widthValues[k]);
//        }
//        return workBook;
//    }
//
//    //创建单元格并设置水平和垂直居中
//    public static XSSFCell createCell(XSSFRow row, XSSFWorkbook workBook, int col){
//        XSSFCellStyle style = workBook.createCellStyle();
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        XSSFCell cell = row.createCell(col);
//        cell.setCellStyle(style);
//        return cell;
//    }
//
//    /**
//     * @param filePath
//     * 			Excel文件
//     * @param sheetName
//     * 			Excel的sheet名称
//     * @param isMultiTitle
//     * 			是否复合表头
//     * @param dataIndexs
//     * 			Map中的key值
//     * @return
//     * @throws Exception
//     */
//    public static List<Map<String, Object>> parseExcelToList(File filePath, String sheetName, boolean isMultiTitle, String[] dataIndexs) throws Exception{
//        //默认从第二行开始解析
//        int startRow = 1;
//        //如果是复合表头从第三行开始解析
//        if(isMultiTitle){
//            startRow = 2;
//        }
//        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
//
//        Workbook wb =null;
//        wb = readExcel(filePath);
//        if(wb != null){
//            Sheet sheet = wb.getSheet(sheetName);
//            Map<String, Object> map = null;
//            for(int i=startRow; i<=sheet.getLastRowNum(); i++){
//                map = new HashMap<String, Object>();
//                Row row = sheet.getRow(i);
//                for(int j=0; j<row.getLastCellNum(); j++){
//                    Cell cell = row.getCell(j);
//                    String value = getCellFormatValue(cell);
//                    map.put(dataIndexs[j], value);
//                }
//                resultList.add(map);
//            }
//        }
//        return resultList;
//    }
//
//    private static String getCellFormatValue(Cell cell) {
//        String cellValue = "";
//        if (cell != null) {
//            switch (cell.getCellType()) {
//                case NUMERIC:
//                    if(HSSFDateUtil.isCellDateFormatted(cell)){
//                        Date date = cell.getDateCellValue();
//                        cellValue = DateUtil.gettDateStrFromSpecialDate(date, "yyyy-MM-dd");
//                    }else{
//                        DecimalFormat df = new DecimalFormat("0");
//                        cellValue = df.format(cell.getNumericCellValue());
//                    }
//                    break;
//
//                case STRING:
//                    cellValue = cell.getStringCellValue();
//                    break;
//
//                case BOOLEAN:
//                    cellValue = cell.getBooleanCellValue() + "";
//                    break;
//
//                case FORMULA:
//                    cellValue = cell.getCellFormula() + "";
//                    break;
//
//                case BLANK:
//                    cellValue = "";
//                    break;
//
//
//                default:
//                    cellValue = "未知错误";
//                    break;
//            }
//        } else {
//            cellValue = "";
//        }
//        return cellValue;
//    }
//
//    //读取excel
//    public static Workbook readExcel(File file){
//        Workbook wb = null;
//        if(file==null){
//            return null;
//        }
//        String extString = file.getName().substring(file.getName().lastIndexOf("."));
//        InputStream is = null;
//        try {
//            is = new FileInputStream(file);
//            if(".xls".equals(extString)){
//                return wb = new HSSFWorkbook(is);
//            }else if(".xlsx".equals(extString)){
//                return wb = new XSSFWorkbook(is);
//            }else{
//                return wb = null;
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return wb;
//    }
//    /**
//     *
//     * @Title: objectToMap
//     * @Description: 对象转Map
//     * @param @param obj
//     * @param @return
//     * @param @throws Exception
//     * @return Map<String,Object>
//     * @throws
//     */
//    public static Map<String, Object> objectToMap(Object obj) throws Exception {
//        if(obj == null){
//            return null;
//        }
//
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        Field[] declaredFields = obj.getClass().getDeclaredFields();
//        for (Field field : declaredFields) {
//            field.setAccessible(true);
//            map.put(field.getName(), field.get(obj));
//        }
//
//        return map;
//    }
//
//    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
//        List<Map<String, Object>> list = new ArrayList<>();
//        if (objList != null && objList.size() > 0) {
//            Map<String, Object> map = null;
//            T bean = null;
//            for (T t : objList) {
//                bean = t;
//                map = beanToMap(bean, false, true);
//                list.add(map);
//            }
//        }
//        return list;
//    }
//
//
//
//}
