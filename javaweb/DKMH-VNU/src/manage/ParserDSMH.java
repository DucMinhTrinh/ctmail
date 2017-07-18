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
public class ParserDSMH {
//    Kiểm tra một trạng thái của môn học trong danh sách môn học

    public static String getStatusOfMonHoc(String title) {
        if (title.length() == 0) {
            return "0";
        } else {
            return "1";
        }
    }

//    Tên của môn học
    public static String getCourseName(Element element) {
        String name = "";
        try {
            name = element.getElementsByTag("td").get(1).text();
            if (name.length() == 0) {
                name = "Chưa xác định";
            }
            return name;
        } catch (Exception e) {
            return name;
        }
    }

//    Số tín chỉ của một môn học
    public static String getCreditsNo(Element element) {
        String name = "";
        try {
            name = element.getElementsByTag("td").get(2).text();
            if (name.length() == 0) {
                name = "-1";
            }
            return name;
        } catch (Exception e) {
            return name;
        }
    }

    //    Lớp môn học
    public static String getCourseCode(Element element) {
        String name = "";
        try {
            name = element.getElementsByTag("td").get(4).text();
            if (name.length() == 0) {
                name = "Chưa xác định";
            }
            return name;
        } catch (Exception e) {
            return name;
        }
    }

    //    Lớp môn học
    public static String getTotalStudent(Element element) {
        String name = "";
        try {
            name = element.getElementsByTag("td").get(5).text();
            if (name.length() == 0) {
                name = "0";
            }
            return name;
        } catch (Exception e) {
            return name;
        }
    }

    //    Lớp môn học
    public static String getStudentRegistered(Element element) {
        String name = "";
        try {
            name = element.getElementsByTag("td").get(6).text();
            if (name.length() == 0) {
                name = "0";
            }
            return name;
        } catch (Exception e) {
            return name;
        }
    }

    //    Giáo viên
    public static String getTeacher(Element element) {
        String name = "";
        try {
            name = element.getElementsByTag("td").get(7).text();
            if (name.length() == 0) {
                name = "Chưa xác định";
            }
            return name;
        } catch (Exception e) {
            return name;
        }
    }

    public static String getCalendar(Element element) {
        String name = "";
        try {
            name = element.getElementsByTag("td").get(9).text();
            if (name.length() == 0) {
                name = "0";
            }
            return name;
        } catch (Exception e) {
            return name;
        }
    }

    public static JSONObject metaData(Element element, String status) {
        try {
            if (status.equals("0")) {
                Element input = element.getElementsByTag("input").get(0);
                JSONObject json = new JSONObject();
                json.put("data-rowindex", input.attr("data-rowindex"));
                json.put("data-crdid", input.attr("data-crdid"));
                json.put("data-numcrd", input.attr("data-numcrd"));
                return json;
            }
            return null;
        } catch (JSONException e) {
            return null;
        }
    }

    public static JSONArray parseDanhSachMonHoc() {
        JSONArray jsonArray = new JSONArray();
        try {
            String loadDanhSachMonHoc = D_common.F_ile.getContentOfFile("res\\loadDanhsachmonhoc.html");
            Document document = Jsoup.parse("<table>" + loadDanhSachMonHoc + "</table>");
            Elements elements = document.getElementsByTag("tr");
            for (Element element : elements) {
                JSONObject json = new JSONObject();
                String title = element.attr("title");
                String status = ParserDSMH.getStatusOfMonHoc(title);
                json.put("status", status);
                String CourseName = ParserDSMH.getCourseName(element);
                json.put("CourseName", CourseName);
                String creditsNo = ParserDSMH.getCreditsNo(element);
                json.put("creditsNo", creditsNo);
                String CourseCode = ParserDSMH.getCourseCode(element);
                json.put("CourseCode", CourseCode);
                String getTotalStudent = ParserDSMH.getTotalStudent(element);
                json.put("getTotalStudent", getTotalStudent);
                String getStudentRegistered = ParserDSMH.getStudentRegistered(element);
                json.put("getStudentRegistered", getStudentRegistered);
                String getTeacher = ParserDSMH.getTeacher(element);
                json.put("getTeacher", getTeacher);
                String getCalendar = ParserDSMH.getCalendar(element);
                json.put("getCalendar", getCalendar);
                JSONObject metaData = ParserDSMH.metaData(element, status);
                json.put("metaData", metaData);
               jsonArray.put(json);
            }
            return jsonArray;
        } catch (JSONException e) {
            return new JSONArray();
        }
    }

    public static void main(String[] args) {
        JSONArray jsonArray = ParserDSMH.parseDanhSachMonHoc();
        System.out.println(jsonArray);
    }
}
