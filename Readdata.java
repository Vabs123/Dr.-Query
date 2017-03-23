import com.jaunt.*;
import com.jaunt.component.*;
import java.io.*;
import java.util.*;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import java.util.regex.*;
import java.io.File;  
import java.io.PrintWriter;
class Readdata
{
	public  LinkedList<DrugInfo> getlinkedlist() {
        Readdata rd =new Readdata();

        LinkedList<DrugInfo> druglinkedlist=new LinkedList<DrugInfo>();
	       try{
           
	           ObjectInputStream is = new ObjectInputStream(new FileInputStream("datatextb.txt"));
	           while(true){
                DrugInfo dg=(DrugInfo)is.readObject();
                druglinkedlist.add(dg);
                         }
                }catch(Exception e){System.out.println(e);}

        return druglinkedlist;


}
}
class finaldata
{
    
    public static void main(String[] args) {
        Readdata rd=new Readdata();
        LinkedList<DrugInfo> dr=rd.getlinkedlist();
        for(DrugInfo drug:dr){
        System.out.println(drug.toString());
    System.out.println("---------------------------------------------------------------------");
}
    }
}