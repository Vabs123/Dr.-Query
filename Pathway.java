import java.util.*;
import java.io.*;

class Pathway implements Serializable{
	String name;
	LinkedList<DrugNode> drugName;

	Pathway(String pathway){
		name = pathway;
		drugName = new LinkedList<>();
	}

	@Override
	public String toString(){
		return name;
	}
}