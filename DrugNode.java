import java.util.*;

class DrugNode{
	String name;
	HashSet<SideEffect> sideEffect;
	HashSet<DrugNode> drugInteraction;
	HashSet<Indication> indication;
	HashSet<Target> target;
	HashSet<Pathway> pathway;

	DrugNode(String name){
		this.name = name;
		sideEffect = new HashSet<>();
		drugInteraction = new HashSet<>();
		indication = new HashSet<>();
		target = new HashSet<>();
		pathway = new HashSet<>();
	}
}