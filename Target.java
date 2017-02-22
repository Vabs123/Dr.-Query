import java.util.*;
import java.io.*;
class Target implements Serializable{
	String name;
	HashSet<DrugNode> drugName;

	Target(String target){
		name = target;
		drugName = new HashSet<>(); 
	}
}