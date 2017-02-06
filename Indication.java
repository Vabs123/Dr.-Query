import java.util.LinkedList;

class Indication{
	String name;
	LinkedList<DrugNode> drugName;

	Indication(String indication){
		name = indication;
		drugName = new LinkedList<>();
	} 
}