//import com.jaunt.*;
//import com.jaunt.component.*;
import java.io.*;
import java.util.*;
import java.util.Iterator;
//import org.apache.commons.lang3.StringUtils;
import java.util.regex.*;
import java.io.File;  
import java.io.PrintWriter;
class Readdata
{
	public  HashSet<DrugInfo> getlinkedlist() {
        Readdata rd =new Readdata();

        HashSet<DrugInfo> druglinkedlist=new HashSet<>();
        int ch = 1;
        while(ch < 39){
	       try{
           
	           ObjectInputStream is = new ObjectInputStream(new FileInputStream("data/"+ch+".txt"));
	           while(true){
                DrugInfo dg=(DrugInfo)is.readObject();
                druglinkedlist.add(dg);
                         }
                }catch(Exception e){System.out.println(e);}
                ch += 1;
            }

        return druglinkedlist;


}
}
class finaldata
{
    
    public static void main(String[] args)throws Exception {
        Readdata rd=new Readdata();
        LinkedList<DrugInfo> dr=new LinkedList(rd.getlinkedlist());
        System.out.println(dr.size());
        SaveData sd = new SaveData("drug_dump.txt");
        sd.saveData(dr);
      /*  LinkedList<DrugInfo> dr = (LinkedList<DrugInfo>) sd.getData();
        int count = 0;
        for(DrugInfo drug:dr){
            if(drug.getIndications().size() == 0){
            count++;
            System.out.println(drug.toString());
        }
}
System.out.println(count);*/
    }
}