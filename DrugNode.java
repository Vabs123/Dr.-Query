import java.util.LinkedList;

class DrugNode{
	Strng name;
	LinkedList<SideEffect> sideEffect;
	LinkedList<DrugNode> drugInteraction;
	LinkedList<Indicaton> indication;
	LinkedList<Target> target;
	LinkedList<Pathway> pathway;

	DrugNode(String name){
		this.name = name;
		sideEffect = new LinkedList<>();
		drugInteraction = new LinkedList<>();
		indication = new LinkedList<>();
		target = new LinkedList<>();
		pathway = new LinkedList<>();
	}
}