/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestAnalytical;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
/**
 *
 * @author MinhPC
 */
public class MainParser {

    public static void main(String[] args) throws JSONException, IOException, ParserConfigurationException, org.xml.sax.SAXException {

        //Begin
        HashMap<String,String> obj = new HashMap<String,String>();
        obj.put("Số thuê bao", "formIn:tabViewAllInOne:0:tabViewAllSubInfo:j_idt2136");
        obj.put("Tên gói cước", "formIn:tabViewAllInOne:0:tabViewAllSubInfo:j_idt11047");
        obj.put("Trạng thái thuê bao", "formIn:tabViewAllInOne:0:tabViewAllSubInfo:j_idt11054");
        obj.put("Ngày kích hoạt", "formIn:tabViewAllInOne:0:tabViewAllSubInfo:j_idt11068");
        obj.put("TK gốc", "formIn:tabViewAllInOne:0:tabViewAllSubInfo:j_idt11089");
        obj.put("TK khuyến mại", "formIn:tabViewAllInOne:0:tabViewAllSubInfo:j_idt11110");
        obj.put("TK hàng tháng", "formIn:tabViewAllInOne:0:tabViewAllSubInfo:j_idt11402");
        obj.put("TK SMS", "formIn:tabViewAllInOne:0:tabViewAllSubInfo:j_idt11192");
        obj.put("TK Free SMS2", "formIn:tabViewAllInOne:0:tabViewAllSubInfo:j_idt11327");
        obj.put("TK Free sms3", "formIn:tabViewAllInOne:0:tabViewAllSubInfo:j_idt11817");
        obj.put("Serial", "formIn:tabViewAllInOne:0:tabViewAllSubInfo:j_idt3448");
        obj.put("other", "formIn:tabViewAllInOne:0:tabViewAllSubInfo:j_idt4481");
        
        
        
        //End
        
    }

    
    //Convert string xml to w3.documents
    public static Document parseXmlFromString(String xmlString) throws ParserConfigurationException, org.xml.sax.SAXException, IOException {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
	DocumentBuilder b = f.newDocumentBuilder();
	Document doc = b.parse(new ByteArrayInputStream(xmlString.getBytes("UTF-8")));
        return doc;
    }

    //Get value from key on xml string of response
    public static HashMap<String,String> getStringFromXML(String body, List<String> params) {
       HashMap<String,String> hash = new HashMap<String,String>();
       Document doc = null;
       try{
           doc = MainParser.parseXmlFromString(body);
           for (int i = 0; i < params.size(); i++) {
               String key = params.get(i);
               String value = null;
               try{
                   try{
                        value = doc.getElementById(key).getTextContent();
                       
                   }catch(NullPointerException ex){
                        value = null;
                   }
               }catch(DOMException e){
                   value = null;
               }
               hash.put(key, value);
           }
           return hash;
       }catch(IOException | ParserConfigurationException | org.xml.sax.SAXException e){
           return null;
       }
        
    }

}
