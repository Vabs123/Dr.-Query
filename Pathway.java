import java.util.*;
import java.io.*;

class Pathway implements Serializable{
	String name;
	HashSet<DrugNode> drugName;

	Pathway(String pathway){
		name = pathway;
		drugName = new HashSet<>();
	}

	@Override
	public String toString(){
		return name;
	}
}