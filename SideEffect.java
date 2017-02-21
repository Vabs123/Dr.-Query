import java.util.*;

class SideEffect{
	String name;
	HashSet<DrugNode> drugName;
	SideEffect(String sideEffect){
		name = sideEffect;
		drugName = new HashSet<>();
	}
}