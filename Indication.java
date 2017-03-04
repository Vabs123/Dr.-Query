import java.util.*;
import java.io.*;

class Indication implements Serializable{
	String name;
	HashSet<DrugNode> drugName;

	Indication(String indication){
		name = indication;
		drugName = new HashSet<>();
	} 
	@Override
	public String toString(){
		return name;
	}
}