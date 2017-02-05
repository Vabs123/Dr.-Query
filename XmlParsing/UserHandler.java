import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UserHandler extends DefaultHandler {

   boolean bFirstName = false;
   boolean bLastName = false;
   boolean bNickName = false;
   boolean bMarks = false;
   int count = 0;
   int nc = 0;

   @Override
   public void startElement(String uri, 
   String localName, String qName, Attributes attributes)
      throws SAXException {
      if (qName.equalsIgnoreCase("targets")) {
         bFirstName = true;
         nc++;
      } /*else if (qName.equalsIgnoreCase("firstname")) {
         bFirstName = true;
      } else if (qName.equalsIgnoreCase("lastname")) {
         bLastName = true;
      } else if (qName.equalsIgnoreCase("nickname")) {
         bNickName = true;
      }
      else if (qName.equalsIgnoreCase("marks")) {
         bMarks = true;
      }*/
   }

   @Override
   public void endElement(String uri, 
   String localName, String qName) throws SAXException {
      if (qName.equalsIgnoreCase("transporters")) {
         System.out.println("End Element :" + qName+" "+count++);
         nc = 0;
      }
   }

   @Override
   public void characters(char ch[], 
      int start, int length) throws SAXException {
      if (bFirstName && nc == 1) {
         System.out.println("First Name: " 
            + new String(ch, start, length));
         bFirstName = false;
      } /*else if (bLastName) {
         System.out.println("Last Name: " 
            + new String(ch, start, length));
         bLastName = false;
      } else if (bNickName) {
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