
import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;
public class SAXParserDemo {
   public static void main(String[] args){

      try {	
         File inputFile = new File("full database.xml");
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser = factory.newSAXParser();
         UserHandler userhandler = new UserHandler();
         saxParser.parse(inputFile, userhandler); 

      } catch (Exception e) {
         e.printStackTrace();
      }
   }   
}

class UserHandler extends DefaultHandler {
   int count = 0;
   boolean bFirstName = false;
   boolean bLastName = false;
   boolean bNickName = false;
   boolean bMarks = false;
   int nd = 0;
   int nt = 0;
   int nts = 0;
   Set<String> set = new HashSet<>();
   @Override
   public void startElement(String uri, 
      String localName, String qName, Attributes attributes)
         throws SAXException {
      if (qName.equalsIgnoreCase("target")) {
        // System.out.println("<"+qName+">");
         nt = 1;
      }
      else if(qName.equals("name") && nt == 1){
         bFirstName = true;
      }
      else if(qName.equals("drug")){
       //  System.out.println("<"+qName+">");
         nd++;
      }
      else if(qName.equals("name") && nd == 1){
         bMarks = true;
      }
   }

   

   @Override
   public void endElement(String uri, 
      String localName, String qName) throws SAXException {
      if (qName.equalsIgnoreCase("target")) {
         System.out.println("</"+qName+">");
      }
      else if(qName.equals("transporters")){
         System.out.println("----------------------------------------- drug end -----------------------------------------------");
         nd = 0;
      }
   }


    @Override
      public void endDocument(){
         System.out.println("Yipeee");
      }

   @Override
   public void characters(char ch[], 
      int start, int length) throws SAXException {
       if (bFirstName && nt == 1) {
         String s =  new String(ch, start, length);
         System.out.println("Target Name: " 
            +s);
         bFirstName = false;
         nt = 0;
      } 
      

       else if (bMarks && nd == 1) {
         System.out.println("Drug Name: " 
         + new String(ch, start, length));
         bMarks= false;
         nd++;
      }/* else if (bNickName) {
         System.out.println("Nick Name: " 
         + new String(ch, start, length));
         bNickName = false;
      } else if (bMarks) {
         System.out.println("Marks: " 
         + new String(ch, start, length));
         bMarks = false;
      }*/
   }
}