import java.util.*;

class Target{
	String name;
	HashSet<DrugNode> drugName;

	Target(String target){
		name = target;
		drugName = new HashSet<>(); 
	}
}