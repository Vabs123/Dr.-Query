import java.util.*;
import java.io.*;

class DrugNode implements Serializable{
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

	public SideEffect containsSE(String sEffect){
		for(SideEffect se:this.sideEffect){
			if(se.name.equals(sEffect))
				return se;
		}
		return null;
	}

	public DrugNode containsDD(String drug_drug){
		for( DrugNode drug:this.drugInteraction){
			if(drug.name.equals(drug_drug))
				return drug;
		}
		return null;
	}

	public Indication containsI(String indi){
		for(Indication i:this.indication){
			if(i.name.equals(indi))
				return i;
		}
		return null;
	}

	@Override
	public String toString(){
		return name;
	}
}