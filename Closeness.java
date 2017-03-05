import java.util.*;
import java.io.*;
class Closeness implements Serializable{
	HashMap<String, Integer> seClosenessPT1;
	HashMap<String, Integer> seClosenessPT2;
	HashMap<String, Integer> seClosenessPT3;
	HashMap<String, Integer> iClosenessPT1;
	HashMap<String, Integer> iClosenessPT2;
	HashMap<String, Integer> iClosenessPT3;
	HashMap<String, Integer> ddClosenessPT1;
	HashMap<String, Integer> ddClosenessPT2;
	HashMap<String, Integer> ddClosenessPT3;


	// This class will run only one time and write all closennes values in a file.

	public ArrayList<Integer> getCloseness(String type, String key) throws IOException,ClassNotFoundException{
		ArrayList<Integer> closeness = new ArrayList<>();
		Closeness c = null;
		SaveData sd = new SaveData("closeness.txt");
		c = (Closeness)sd.getData();
		if(type.equals("SideEffect")){
			closeness.add(c.seClosenessPT1.get(key));
			closeness.add(c.seClosenessPT2.get(key));
			closeness.add(c.seClosenessPT3.get(key));
		}
		else if(type.equals("Indication")){
			closeness.add(c.iClosenessPT1.get(key));
			closeness.add(c.iClosenessPT2.get(key));
			closeness.add(c.iClosenessPT3.get(key));
		}
		else if(type.equals("DrugInteraction")){
			closeness.add(c.ddClosenessPT1.get(key));
			closeness.add(c.ddClosenessPT2.get(key));
			closeness.add(c.ddClosenessPT3.get(key));
		}
		return closeness;
	}

	//Training
	//SideEffect 
	//SideEffect PathTypes -> D-D-SE(PathType1), D-I-D-SE(PathType2), D-SE-D-SE(PathType3)

	/// Only Path count is used as metric for closeness

	public void setSEClosenessPT1(DrugGraph dg){
		seClosenessPT1 = new HashMap<>();
		int count = 0;
		HashSet<DrugNode> drugList1 = new HashSet<>();
		HashSet<DrugNode> drugList2 = new HashSet<>();
		for(SideEffect se:dg.gSideEffect){
			count = 0;
			drugList1 = se.drugName;
			for(DrugNode drugNode:drugList1){
				drugList2 = drugNode.drugInteraction;
				count += drugList2.size();
			}
			seClosenessPT1.put(se.name, count);
		}
	}

	public void setSEClosenessPT2(DrugGraph dg){
		seClosenessPT2 = new HashMap<>();
		int count = 0;
		HashSet<DrugNode> drugList1 = new HashSet<>();
		HashSet<DrugNode> drugList2 = new HashSet<>();
		HashSet<Indication> iList = new HashSet<>();
		for(SideEffect se:dg.gSideEffect){
			count = 0;
			drugList1 = se.drugName;
			for(DrugNode drugNode:drugList1){
				iList = drugNode.indication;
				for(Indication i:iList){
					drugList2 = i.drugName;
					count += drugList2.size();
				}
			}
			seClosenessPT2.put(se.name, count);
		}
	}


	public void setSEClosenessPT3(DrugGraph dg){
		seClosenessPT3 = new HashMap<>();
		int count = 0;
		HashSet<DrugNode> drugList1 = new HashSet<>();
		HashSet<DrugNode> drugList2 = new HashSet<>();
		HashSet<SideEffect> seList = new HashSet<>();
		for(SideEffect se:dg.gSideEffect){
			count = 0;
			drugList1 = se.drugName;
			for(DrugNode drugNode:drugList1){
				seList = drugNode.sideEffect;
				for(SideEffect sE:seList){
					drugList2 = sE.drugName;
					count += drugList2.size();
				}
			}
			seClosenessPT3.put(se.name, count);
		}
	}

	//Indication
	//Indication PathTypes -> D-D-I(PT1), D-I-D-I(PT2), D-SE-D-I(PT3)


	public void setIClosenessPT1(DrugGraph dg){
		iClosenessPT1 = new HashMap<>();
		int count = 0;
		HashSet<DrugNode> drugList1 = new HashSet<>();
		HashSet<DrugNode> drugList2 = new HashSet<>();
		for(Indication i:dg.gIndication){
			count = 0;
			drugList1 = i.drugName;
			for(DrugNode drugNode:drugList1){
				drugList2 = drugNode.drugInteraction;
				count += drugList2.size();
			}
			iClosenessPT1.put(i.name, count);
		}
	}

	public void setIClosenessPT2(DrugGraph dg){
		iClosenessPT2 = new HashMap<>();
		int count = 0;
		HashSet<DrugNode> drugList1 = new HashSet<>();
		HashSet<DrugNode> drugList2 = new HashSet<>();
		HashSet<Indication> iList = new HashSet<>();
		for(Indication ind:dg.gIndication){
			count = 0;
			drugList1 = ind.drugName;
			for(DrugNode drugNode:drugList1){
				iList = drugNode.indication;
				for(Indication i:iList){
					drugList2 = i.drugName;
					count += drugList2.size();
				}
			}
			iClosenessPT2.put(ind.name, count);
		}
	}

	public void setIClosenessPT3(DrugGraph dg){
		iClosenessPT3 = new HashMap<>();
		int count = 0;
		HashSet<DrugNode> drugList1 = new HashSet<>();
		HashSet<DrugNode> drugList2 = new HashSet<>();
		HashSet<SideEffect> seList = new HashSet<>();
		for(Indication i:dg.gIndication){
			count = 0;
			drugList1 = i.drugName;
			for(DrugNode drugNode:drugList1){
				seList = drugNode.sideEffect;
				for(SideEffect se:seList){
					drugList2 = se.drugName;
					count += drugList2.size();
				}
			}
			iClosenessPT3.put(i.name, count);
		}
	}

	//Drug Interaction
	//Drug Interaction PathTypes -> D-D-D(PT1), D-I-D-D(PT2), D-SE-D-D(PT3)


	public void setDDClosenessPT1(DrugGraph dg){
		ddClosenessPT1 = new HashMap<>();
		int count = 0;
		HashSet<DrugNode> drugList1 = new HashSet<>();
		HashSet<DrugNode> drugList2 = new HashSet<>();
		for(DrugNode drug:dg.gDrug){
			count = 0;
			drugList1 = drug.drugInteraction;
			for(DrugNode drugNode:drugList1){
				drugList2 = drugNode.drugInteraction;
				count += drugList2.size();
			}
			ddClosenessPT1.put(drug.name, count);
		}
	}

	public void setDDClosenessPT2(DrugGraph dg){
		ddClosenessPT2 = new HashMap<>();
		int count = 0;
		HashSet<DrugNode> drugList1 = new HashSet<>();
		HashSet<DrugNode> drugList2 = new HashSet<>();
		HashSet<Indication> iList = new HashSet<>();
		for(DrugNode drug:dg.gDrug){
			count = 0;
			drugList1 = drug.drugInteraction;
			for(DrugNode drugNode:drugList1){
				iList = drugNode.indication;
				for(Indication i:iList){
					drugList2 = i.drugName;
					count += drugList2.size();
				}
			}
			ddClosenessPT2.put(drug.name, count);
		}
	}

	public void setDDClosenessPT3(DrugGraph dg){
		ddClosenessPT3 = new HashMap<>();
		HashMap<SideEffect,Integer> closeness = new HashMap<>();
		int count = 0;
		HashSet<DrugNode> drugList1 = new HashSet<>();
		HashSet<DrugNode> drugList2 = new HashSet<>();
		HashSet<SideEffect> seList = new HashSet<>();
		for(DrugNode drug:dg.gDrug){
			count = 0;
			drugList1 = drug.drugInteraction;
			for(DrugNode drugNode:drugList1){
				seList = drugNode.sideEffect;
				for(SideEffect se:seList){
					drugList2 = se.drugName;
					count += drugList2.size();
				}
			}
			ddClosenessPT3.put(drug.name, count);
		}
	}
 



	public static void main(String[] args) throws IOException,ClassNotFoundException{
		SaveData sd = new SaveData("drugDump.txt");
		DrugGraph dg = (DrugGraph)sd.getData();

		Closeness closeness = new Closeness();
		closeness.setSEClosenessPT1(dg);
		closeness.setSEClosenessPT2(dg);
		closeness.setSEClosenessPT3(dg);
		closeness.setIClosenessPT1(dg);
		closeness.setIClosenessPT2(dg);
		closeness.setIClosenessPT3(dg);
		closeness.setDDClosenessPT1(dg);
		closeness.setDDClosenessPT2(dg);
		closeness.setDDClosenessPT3(dg);
		
		System.out.println(closeness.seClosenessPT1);
		System.out.println(closeness.seClosenessPT2);
		System.out.println(closeness.seClosenessPT3);
		System.out.println(closeness.iClosenessPT1);
		System.out.println(closeness.iClosenessPT2);
		System.out.println(closeness.iClosenessPT3);
		System.out.println(closeness.ddClosenessPT1);
		System.out.println(closeness.ddClosenessPT2);
		System.out.println(closeness.ddClosenessPT3);

		sd = new SaveData("closeness.txt");
		sd.saveData(closeness);

		System.out.println("----------------------------------------------");
		System.out.println(closeness.getCloseness("SideEffect","se1"));

		/*
		closeness = (Closeness)sd.getData();
		System.out.println(closeness.seClosenessPT3);*/
	}
}