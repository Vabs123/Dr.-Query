import java.util.*;
import java.io.*;
class Target implements Serializable{
	String name;
	LinkedList<DrugNode> drugName;

	Target(String target){
		name = target;
		drugName = new LinkedList<>(); 
	}

	@Override
	public String toString(){
		return name;
	}
}