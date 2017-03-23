import java.io.*;
import java.util.*;

class ReadData{
	LinkedList<DrugInfo> drugData;

	ReadData(String file) throws Exception{
		SaveData sd = null;
		File f = new File(file);
		if(f.exists()){
			sd = new SaveData(file);
			drugData = (LinkedList<DrugInfo>)sd.getData();
		}
		else{
			drugData = new LinkedList<>();
		}
	}

	public static void main(String[] args) throws Exception {
		/*int count = 0;
		DrugInfo d = null;
		ReadData rd = new ReadData("drug_dump.txt");
		System.out.println(rd.drugData);
		DrugGraph dg = new DrugGraph();
		for(int i = 0;i < 30;i++){
			d = rd.drugData.get(i);
			dg.create(d.drugName, d.listofSideEffects, d.listofIndications, d.listofDrugInteraction);
		}*/
		SaveData sd = new SaveData("drug_graph.txt");
		DrugGraph dg = (DrugGraph) sd.getData();

		System.out.println("Drugs ---------------------------->");

		for(DrugNode drug:dg.gDrug){
			dg.printDrug(drug);
			System.out.println("----------------------------------------------------------------------");
		}

		//SaveData sd = new SaveData("drug_graph.txt");
		//sd.saveData(dg);	
	}
}