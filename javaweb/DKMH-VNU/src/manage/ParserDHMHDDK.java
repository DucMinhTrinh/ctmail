/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author MinhPC
 */
public class ParserDHMHDDK {

    public static JSONObject parseACourse(Element element) {
        try {
            JSONObject json = new JSONObject();
            Elements tdElements = element.getElementsByTag("td");
            String dataCrdidRegistered = tdElements.get(0).attr("data-crdid-registered");

            String CourseName = tdElements.get(1).text();
            json.put("CourseName", CourseName);
            //Số tín chỉ
            String creditsNo = tdElements.get(2).text();
            json.put("creditsNo", Integer.valueOf(creditsNo));
            //Lớp môn học
            String CourseCode = tdElements.get(3).text();
            json.put("CourseCode", CourseCode);
            //Giáo viên
            String getTeacher = tdElements.get(4).text();
            json.put("getTeacher", getTeacher);
            //Lịch học
            String getCalendar = tdElements.get(5).text();
            json.put("getCalendar", getCalendar);

            Element input = element.getElementsByTag("input").get(2);
            String data_rowindex = "";
            if (input.attr("data-rowindex").trim().length() > 0) {
                data_rowindex = input.attr("data-rowindex").trim();
            }
            json.put("dataCrdidRegistered", dataCrdidRegistered);
            json.put("data_rowindex", Integer.valueOf(data_rowindex));
            return json;
        } catch (JSONException e) {
            return null;
        }
    }

    public static void parseDanhSachMonHocDaDangKy() {
        JSONObject json = new JSONObject();
        try {
            String loadDanhSachMonHoc = D_common.F_ile.getContentOfFile("res\\loadDanhsachmonhocdadangky.html");
            Document document = Jsoup.parse("<table>" + loadDanhSachMonHoc + "</table>");
            Elements inputs = document.getElementsByTag("input");

            //Trạng thái đã thay đổi hay chưa
            String hasChange = inputs.get(0).val();
            json.put("hasChange", Boolean.valueOf(hasChange));

            //Tổng số môn học đã đăng ký
            String totalCredit = inputs.get(1).val();
            json.put("totalCredit", Integer.valueOf(totalCredit));

            //Tổng  số tín chỉ đã đăng ký
            String totalCrd = inputs.get(2).val();
            json.put("totalCrd ", Integer.valueOf(totalCrd));
            Elements elements = document.getElementsByTag("tr");
            JSONArray json_array = new JSONArray();
            for(int i=0;i<elements.size();i++){
                JSONObject new_json = parseACourse(elements.get(i));
                json_array.put(new_json);
            }
            json.put("metaData", json_array);
            System.out.println(json);
        } catch (NumberFormatException | JSONException e) {

        }
    }

    public static void main(String[] args) {
        ParserDHMHDDK.parseDanhSachMonHocDaDangKy();
    }
}
