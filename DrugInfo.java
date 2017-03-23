import com.jaunt.*;
import com.jaunt.component.*;
import java.io.*;
import java.util.*;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import java.util.regex.*;
import java.io.File;  
import java.io.PrintWriter;

class DrugInfo implements Serializable
	{
	String drugName;
	private static final long serialVersionUID = 8309080721495266420L;
	LinkedList<String> listofIndications=new LinkedList<String>();
	LinkedList<String> listofSideEffects=new LinkedList<String>();
	LinkedList<String> listofDrugInteraction=new LinkedList<String>();
	DrugInfo(){}
	DrugInfo(String drugname,LinkedList<String> listofIndications,LinkedList<String> listofSideEffects,LinkedList<String> listofDrugInteraction)
	{
		this.drugName=drugname;
		this.listofIndications=listofIndications;
		this.listofSideEffects=listofSideEffects;
		this.listofDrugInteraction=listofDrugInteraction;
	}
	public String getDrugname()
	{
		return this.drugName;
	}
	public LinkedList<String> getIndications()
	{
		return this.listofIndications;
	}
	public LinkedList<String> getSideeffects()
	{
		return this.listofSideEffects;
	}
	public LinkedList<String> getdruginteraction()
	{
		return this.listofDrugInteraction;
	}
	public String toString() {
		String s=this.getDrugname();
        return s;
    }

}
