import java.util.LinkedList;

class SideEffect{
	String name;
	LinkedList<DrugNode> drugName;
	SideEffect(String sideEffect){
		name = sideEffect;
		drugName = new LinkedList<>();
	}
}