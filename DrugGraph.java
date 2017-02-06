import java.util.*;

class DrugGraph{
	LinkedList<DrugNode> gDrug;
	LinkedList<SideEffect> gSideEffect;
	LinkedList<DrugNode> gDrugInteraction;
	LinkedList<Indicaton> gIndication;
	LinkedList<Target> gTarget;
	LinkedList<Pathway> gPathway;

	DrugGraph(){
		gDrug = new LinkedList<>();
		gSideEffect = new LinkedList<>();
		gDrugInteraction = new LinkedList<>();
		gIndication = new LinkedList<>();
		gTarget = new LinkedList<>();
		gPathway = new LinkedList<>();
	}

	public boolean conatinsSE(String sname){
		Iterator<SideEffect> it = gSideEffect.iterator();
		while(it.hasNext()){
			if(it.next().name.equals(sname))
				return true;
		} 
		return false;
	}

	public boolean conatinsD(String sname){
		Iterator<DrugNode> it = gDrug.iterator();
		while(it.hasNext()){
			if(it.next().name.equals(sname))
				return true;
		} 
		return false;
	}

	public boolean containsI(String sname){
		Iterator<Indicaton> it = gIndication.iterator();
		while(it.hasNext()){
			if(it.next().name.equals(sname))
				return true;
		} 
		return false;
	}

	public boolean containsT(String sname){
		Iterator<Target> it = gTarget.iterator();
		while(it.hasNext()){
			if(it.next().name.equals(sname))
				return true;
		} 
		return false;
	}

	public boolean conatinsP(String sname){
		Iterator<Pathway> it = gPathway.iterator();
		while(it.hasNext()){
			if(it.next().name.equals(sname))
				return true;
		} 
		return false;
	}

	public void create(String drugName, String sideEffect, String indication, String target, String pathway){
		Iterator<SideEffect> it = gSideEffect.iterator();
		while(it.hasNext()){
			if(it.next().name.equals(sname))
				return true;
		} 
		return false;
	}

	public static void main(String[] args) {
		
	}
	
}