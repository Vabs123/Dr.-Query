import java.util.*;
import java.io.*;

class DrugGraph implements Serializable{
	LinkedList<DrugNode> gDrug;
	LinkedList<SideEffect> gSideEffect;
	LinkedList<Indication> gIndication;
	LinkedList<Target> gTarget;
	LinkedList<Pathway> gPathway;

	DrugGraph(){
		gDrug = new LinkedList<>();
		gSideEffect = new LinkedList<>();
		gIndication = new LinkedList<>();
		gTarget = new LinkedList<>();
		gPathway = new LinkedList<>();
	}

	public SideEffect containsSE(String sname){
		Iterator<SideEffect> it = gSideEffect.iterator();
		while(it.hasNext()){
			SideEffect se = it.next();
			if(se.name.equals(sname))
				return se;
		} 
		return null;
	}

	public DrugNode containsD(String sname){
		Iterator<DrugNode> it = gDrug.iterator();
		while(it.hasNext()){
			DrugNode drug = it.next();
			if(drug.name.equals(sname))
				return drug;
		} 
		return null;
	}

	public Indication containsI(String sname){
		Iterator<Indication> it = gIndication.iterator();
		while(it.hasNext()){
			Indication indication = it.next();
			if(indication.name.equals(sname))
				return indication;
		} 
		return null;
	}

	public Target containsT(String sname){
		Iterator<Target> it = gTarget.iterator();
		while(it.hasNext()){
			Target target = it.next();
			if(target.name.equals(sname))
				return target;
		} 
		return null;
	}

	public Pathway containsP(String sname){
		Iterator<Pathway> it = gPathway.iterator();
		while(it.hasNext()){
			Pathway pathway = it.next();
			if(pathway.name.equals(sname))
				return pathway;
		} 
		return null;
	}

	public void addSideEffect(DrugNode drugNode, LinkedList<String> se){//If drugs are not unique make a check function so that the twice 
		//twice entry of a drug in sideeffect list can be avoided...
		SideEffect sEffect;
		for(String s:se){
			sEffect = containsSE(s);
			if(sEffect == null){
				sEffect = new SideEffect(s);
				gSideEffect.add(sEffect);

			}
			sEffect.drugName.add(drugNode);
			drugNode.sideEffect.add(sEffect);
		}
	}

	public void addIndication(DrugNode drugNode, LinkedList<String> i){
		Indication ind;
		for(String s:i){
			ind = containsI(s);
			if(ind == null){
				ind = new Indication(s);
				gIndication.add(ind);

			}
			ind.drugName.add(drugNode);
			drugNode.indication.add(ind);
		}
	}

	public void addTarget(DrugNode drugNode, LinkedList<String> t){
		Target tar;
		for(String s:t){
			tar = containsT(s);
			if(tar == null){
				tar = new Target(s);
				gTarget.add(tar);

			}
			tar.drugName.add(drugNode);
			drugNode.target.add(tar);
		}
	}

	public void addPathway(DrugNode drugNode, LinkedList<String> p){
		Pathway path;
		for(String s:p){
			path = containsP(s);
			if(path == null){
				path = new Pathway(s);
				gPathway.add(path);

			}
			path.drugName.add(drugNode);
			drugNode.pathway.add(path);
		}
	}

	public void addDrugInteraction(DrugNode drugNode, LinkedList<String> d_d){
		DrugNode dNode;
		for(String s:d_d){
			dNode = containsD(s);
			if(dNode == null){
				dNode = new DrugNode(s);
				gDrug.add(dNode);
			}
			dNode.drugInteraction.add(drugNode);
			drugNode.drugInteraction.add(dNode);
		}
	}



	public void create(String drugName, LinkedList<String> sEffect, LinkedList<String> indi, LinkedList<String> tar, LinkedList<String> path, LinkedList<String> drug_drug){
		// checkng if drug exists or not
		DrugNode drugNode = containsD(drugName);

		//if exists just update/add the attributes of drugnode
		if(drugNode == null){
			drugNode = new DrugNode(drugName);
			gDrug.add(drugNode);
		}
		addSideEffect(drugNode, sEffect);
		addIndication(drugNode, indi);
		addTarget(drugNode, tar);
		addPathway(drugNode, path);
		addDrugInteraction(drugNode, drug_drug);	
	}


	public void printDrug(DrugNode drug){
		printSE(drug);
		printI(drug);
		printT(drug);
		printP(drug);
		printd_d(drug);
	}

	public void printSE(DrugNode d){
		System.out.println(d.name+" - SideEffects --->");
		for(SideEffect se:d.sideEffect){
			System.out.println(se.name);
		}
	}

	public void printT(DrugNode d){
		System.out.println(d.name+" - Targets --->");
		for(Target se:d.target){
			System.out.println(se.name);
		}
	}

	public void printI(DrugNode d){
		System.out.println(d.name+" - Indications --->");
		for(Indication se:d.indication){
			System.out.println(se.name);
		}
	}

	public void printP(DrugNode d){
		System.out.println(d.name+" - Pathway --->");
		for(Pathway se:d.pathway){
			System.out.println(se.name);
		}
	}

	public void printd_d(DrugNode d){
		System.out.println(d.name+" - drugInteractions --->");
		for(DrugNode se:d.drugInteraction){
			System.out.println(se.name);
		}
	}

	

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		LinkedList<String> se = new LinkedList<>();
		LinkedList<String> i = new LinkedList<>();
		LinkedList<String> t = new LinkedList<>();
		LinkedList<String> p = new LinkedList<>();
		LinkedList<String> d_d = new LinkedList<>();
		se.add("se1");
		se.add("se2");
		se.add("se3");

		i.add("i1");
		i.add("i2");
		i.add("i3");

		t.add("t1");
		t.add("t2");
		t.add("t3");

		p.add("p1");
		p.add("p2");
		p.add("p3");

		d_d.add("drug2");
		d_d.add("drug3");
		d_d.add("drug4");

		DrugGraph dg = new DrugGraph();
		dg.create("drug1", se, i, t, p, d_d);
		




		 se = new LinkedList<>();
		 i = new LinkedList<>();
		 t = new LinkedList<>();
		 p = new LinkedList<>();
	    d_d = new LinkedList<>();
		se.add("se1");
		se.add("se5");
		se.add("se6");

		i.add("i4");
		i.add("i5");
		i.add("i6");

		t.add("t9");
		t.add("t8");
		t.add("t7");

		p.add("p6");
		p.add("p0");
		p.add("p5");

		d_d.add("drug9");
		d_d.add("drug8");
		d_d.add("drug7");

		
		dg.create("drug2", se, i, t, p, d_d);


	//	SaveData sd = new SaveData(dg.gDrug, dg.gSideEffect, dg.gIndication, dg.gTarget, dg.gPathway);
	//	sd.save();
		SaveData sd = new SaveData("drugDump.txt");
		sd.saveData(dg);
		dg = (DrugGraph)sd.getData();

	/*	dg.gDrug = sd.drugList;
		dg.gSideEffect = sd.sideEffectList;
		dg.gIndication = sd.indicationList;
		dg.gTarget = sd.targetList;
		dg.gPathway = sd.pathwayList;*/


		System.out.println("Drugs ---------------------------->");

		for(DrugNode d:dg.gDrug){
			dg.printDrug(d);
			System.out.println("----------------------------------------------------------------------");
		}	



		System.out.println("SideEffects-----------------------------------------------------");
		int count = 1;
		for(SideEffect ss:dg.gSideEffect){
			System.out.println("SideEffect "+count+++" NAME --> "+ss.name);
			for(DrugNode dp:ss.drugName){
				System.out.println(dp.name);
			}
		}






		System.out.println("--------------=====================================");
		System.out.println(dg.gSideEffect.contains("se1"));
		/*System.out.println("SideEffects -------------------------------->");

		for(SideEffect d:dg.gSideEffect){
			System.out.println(d.name);
		}

		System.out.println("Indiations -------------------------------------->");
		for(Indication d:dg.gIndication){
			System.out.println(d.name);
		}

		System.out.println("Pathways -------------------------------------------->");
		for(Pathway d:dg.gPathway){
			System.out.println(d.name);
		}

		System.out.println("Targets ------------------------------------------------>");
		for(Target d:dg.gTarget){
			System.out.println(d.name);
		}*/


	}
	
}
