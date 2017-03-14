import java.util.*;
import java.io.*;

class Closeness implements Serializable{
	HashMap<String, ArrayList<Integer>> seClosenessPT1;
	HashMap<String, ArrayList<Integer>> seClosenessPT2;
	HashMap<String, ArrayList<Integer>> seClosenessPT3;
	HashMap<String, ArrayList<Integer>> iClosenessPT1;
	HashMap<String, ArrayList<Integer>> iClosenessPT2;
	HashMap<String, ArrayList<Integer>> iClosenessPT3;
	HashMap<String, ArrayList<Integer>> ddClosenessPT1;
	HashMap<String, ArrayList<Integer>> ddClosenessPT2;
	HashMap<String, ArrayList<Integer>> ddClosenessPT3;
	
	//SideEffect PathTypes -> D-D-SE(PathType1), D-I-D-SE(PathType2), D-SE-D-SE(PathType3)
	// Only Path count is used as metric for closeness
	
	public void setSEClosenessPT1(DrugGraph dg){
		int count = 0;
		this.seClosenessPT1 = new HashMap<>();
		ArrayList<Integer> seCloseness;
		for(SideEffect se:dg.gSideEffect){
			seCloseness = new ArrayList<>();
			for(DrugNode d1:se.drugName){
				count = 0;
				for(DrugNode d2:se.drugName){
					if(d1.name.equals(d2.name))
						continue;
					if(d2.containsDD(d1.name) != null)
						count++;
				}
				seCloseness.add(count);
			}
			seClosenessPT1.put(se.name, seCloseness);
		}
	}
	//D-I-D-SE(PathType2)
	public void setSEClosenessPT2(DrugGraph dg){
		int count = 0;
		this.seClosenessPT2 = new HashMap<>();
		ArrayList<Integer> seCloseness;
		for(SideEffect se:dg.gSideEffect){
			seCloseness = new ArrayList<>();
			for(DrugNode d1:se.drugName){
				count = 0;
				for(DrugNode d2:se.drugName){
					if(d1.name.equals(d2.name))
						continue;
					for(Indication i:d2.indication){
						if(i.containsDrug(d1.name) != null)
							count++;
					}
				}
				seCloseness.add(count);
			}
			seClosenessPT2.put(se.name,seCloseness);
		}
	}

	//D-SE-D-SE(PathType3)
	public void setSEClosenessPT3(DrugGraph dg){
		int count = 0;
		this.seClosenessPT3 = new HashMap<>();
		ArrayList<Integer> seCloseness;
		for(SideEffect se:dg.gSideEffect){
			seCloseness = new ArrayList<>();
			for(DrugNode d1:se.drugName){
				count = 0;
				for(DrugNode d2:se.drugName){
					if(d1.name.equals(d2.name))
						continue;
					for(SideEffect sideEffect:d2.sideEffect){
						if(sideEffect.containsDrug(d1.name) != null)
							count++;
					}
				}
				seCloseness.add(count);
			}
			seClosenessPT3.put(se.name,seCloseness);
		}
	}

	

	//Indication
	//Indication PathTypes -> D-D-I(PT1), D-I-D-I(PT2), D-SE-D-I(PT3)

	public void setIClosenessPT1(DrugGraph dg){
		int count = 0;
		this.iClosenessPT1 = new HashMap<>();
		ArrayList<Integer> iCloseness;
		for(Indication i:dg.gIndication){
			iCloseness = new ArrayList<>();
			for(DrugNode d1:i.drugName){
				count = 0;
				for(DrugNode d2:i.drugName){
					if(d1.name.equals(d2.name))
						continue;
					if(d2.containsDD(d1.name) != null)
						count++;
				}
				iCloseness.add(count);
			}
			iClosenessPT1.put(i.name, iCloseness);
		}
	}

	// D-I-D-I(PT2)
	public void setIClosenessPT2(DrugGraph dg){
		int count = 0;
		this.iClosenessPT2 = new HashMap<>();
		ArrayList<Integer> iCloseness;
		for(Indication indi:dg.gIndication){
			iCloseness = new ArrayList<>();
			for(DrugNode d1:indi.drugName){
				count = 0;
				for(DrugNode d2:indi.drugName){
					if(d1.name.equals(d2.name))
						continue;
					for(Indication i:d2.indication){
						if(i.containsDrug(d1.name) != null)
							count++;
					}
				}
				iCloseness.add(count);
			}
			iClosenessPT2.put(indi.name,iCloseness);
		}
	}

	//D-SE-D-I(PT3)
	public void setIClosenessPT3(DrugGraph dg){
		int count = 0;
		this.iClosenessPT3 = new HashMap<>();
		ArrayList<Integer> iCloseness;
		for(Indication i:dg.gIndication){
			iCloseness = new ArrayList<>();
			for(DrugNode d1:i.drugName){
				count = 0;
				for(DrugNode d2:i.drugName){
					if(d1.name.equals(d2.name))
						continue;
					for(SideEffect sideEffect:d2.sideEffect){
						if(sideEffect.containsDrug(d1.name) != null)
							count++;
					}
				}
				iCloseness.add(count);
			}
			iClosenessPT3.put(i.name,iCloseness);
		}
	}

	//Drug Interaction
	//Drug Interaction PathTypes -> D-D-D(PT1), D-I-D-D(PT2), D-SE-D-D(PT3)
	public void setDDClosenessPT1(DrugGraph dg){
		int count = 0;
		this.ddClosenessPT1 = new HashMap<>();
		ArrayList<Integer> ddCloseness;
		for(DrugNode dd:dg.gDrug){
			ddCloseness = new ArrayList<>();
			for(DrugNode d1:dd.drugInteraction){
				count = 0;
				for(DrugNode d2:dd.drugInteraction){
					if(d1.name.equals(d2.name))
						continue;
					if(d2.containsDD(d1.name) != null)
						count++;
				}
				ddCloseness.add(count);
			}
			ddClosenessPT1.put(dd.name, ddCloseness);
		}
	}

 	//D-I-D-D(PT2)
	public void setDDClosenessPT2(DrugGraph dg){
		int count = 0;
		this.ddClosenessPT2 = new HashMap<>();
		ArrayList<Integer> ddCloseness;
		for(DrugNode dd:dg.gDrug){
			ddCloseness = new ArrayList<>();
			for(DrugNode d1:dd.drugInteraction){
				count = 0;
				for(DrugNode d2:dd.drugInteraction){
					if(d1.name.equals(d2.name))
						continue;
					for(Indication i:d2.indication){
						if(i.containsDrug(d1.name) != null)
							count++;
					}
				}
				ddCloseness.add(count);
			}
			ddClosenessPT2.put(dd.name,ddCloseness);
		}
	}

	//D-SE-D-D(PT3)
	public void setDDClosenessPT3(DrugGraph dg){
		int count = 0;
		this.ddClosenessPT3 = new HashMap<>();
		ArrayList<Integer> ddCloseness;
		for(DrugNode dd:dg.gDrug){
			ddCloseness = new ArrayList<>();
			for(DrugNode d1:dd.drugInteraction){
				count = 0;
				for(DrugNode d2:dd.drugInteraction){
					if(d1.name.equals(d2.name))
						continue;
					for(SideEffect sideEffect:d2.sideEffect){
						if(sideEffect.containsDrug(d1.name) != null)
							count++;
					}
				}
				ddCloseness.add(count);
			}
			ddClosenessPT3.put(dd.name,ddCloseness);
		}
	}


	public static void main(String[] args) throws IOException,ClassNotFoundException,SizeNotMatchedException{
		SaveData sd = new SaveData("drugDump.txt");
		DrugGraph dg = (DrugGraph)sd.getData();
		Closeness cl = new Closeness();
		cl.setSEClosenessPT1(dg);
		cl.setSEClosenessPT2(dg);
		cl.setSEClosenessPT3(dg);
		cl.setIClosenessPT1(dg);
		cl.setIClosenessPT2(dg);
		cl.setIClosenessPT3(dg);
		cl.setDDClosenessPT1(dg);
		cl.setDDClosenessPT2(dg);
		cl.setDDClosenessPT3(dg);
		PrepareTrainingInstances pt = new PrepareTrainingInstances();
		pt.makeInstances(dg,cl);
		sd = new SaveData("train_instances.txt");
		pt = (PrepareTrainingInstances)sd.getData();
		System.out.println(pt);
		//System.out.println(cl.ddClosenessPT1);
		//System.out.println(cl.ddClosenessPT2);
	}
}