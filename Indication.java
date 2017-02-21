import java.util.*;

class Indication{
	String name;
	HashSet<DrugNode> drugName;

	Indication(String indication){
		name = indication;
		drugName = new HashSet<>();
	} 
}