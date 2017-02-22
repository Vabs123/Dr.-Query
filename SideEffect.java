import java.util.*;
import java.io.*;

class SideEffect implements Serializable{
	String name;
	HashSet<DrugNode> drugName;
	SideEffect(String sideEffect){
		name = sideEffect;
		drugName = new HashSet<>();
	}
}