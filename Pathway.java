import java.util.*;

class Pathway{
	String name;
	HashSet<DrugNode> drugName;

	Pathway(String pathway){
		name = pathway;
		drugName = new HashSet<>();
	}
}