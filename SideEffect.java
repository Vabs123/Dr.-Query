import java.util.*;
import java.io.*;

class SideEffect implements Serializable{
	String name;
	LinkedList<DrugNode> drugName;
	SideEffect(String sideEffect){
		name = sideEffect;
		drugName = new LinkedList<>();
	}

	public DrugNode containsDrug(String d){
		for(DrugNode drug:this.drugName){
			if(drug.name.equals(d))
				return drug;
		}
		return null;
	}

	@Override
	public String toString(){
		return name;
	}
}