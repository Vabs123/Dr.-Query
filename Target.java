import java.util.LinkedList;

class Target{
	String target;
	LinkedList<DrugNode> drugName;

	Target(String target){
		this.target = target;
		drugName = new LinkedList<>(); 
	}
}