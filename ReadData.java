import java.io.*;
import java.util.*;
import java.math.BigDecimal;

import weka.core.FastVector;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.Instance;
import weka.core.DenseInstance;
import weka.classifiers.Classifier;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.functions.Logistic;
import weka.classifiers.Evaluation;

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

	public boolean containsD(String d){
		for(DrugInfo di:drugData){
			if(d.equals(di.drugName))
				return true;
		}
		return false;
	}

	

	public static void main(String[] args) throws Exception {
		
	/*	LinkedList<String> se = new LinkedList<>();
		LinkedList<String> i = new LinkedList<>();
		LinkedList<String> d_d = new LinkedList<>();
		se.add("se1");
		se.add("se2");
		se.add("se3");

		i.add("i1");
		i.add("i2");
		i.add("i3");

		

		d_d.add("drug2");
		d_d.add("drug3");
		d_d.add("drug4");

		DrugGraph dg = new DrugGraph();
		dg.create("drug1", se, i, d_d);
		




		 se = new LinkedList<>();
		 i = new LinkedList<>();
	    d_d = new LinkedList<>();
		se.add("se1");
		se.add("se5");
		se.add("se6");

		i.add("i4");
		i.add("i5");
		i.add("i6");


		d_d.add("drug9");
		d_d.add("drug8");
		d_d.add("drug7");
		d_d.add("drug3");

		
		dg.create("drug2", se, i,  d_d);

		*/


		int countse = 0,counti = 0 ;
		DrugInfo d = null;
		ReadData rd = new ReadData("drug_dump.txt");
		//System.out.println(rd.drugData);
		System.out.println("Done reading");
		DrugGraph dg = new DrugGraph();
		for(int i = 0;i <rd.drugData.size();i++){
			d = rd.drugData.get(i);
			dg.create(d.drugName, d.listofSideEffects, d.listofIndications, d.listofDrugInteraction);
		//	System.out.println(d.listofDrugInteraction);
		//	System.out.println("-----------------------------------------------------------------------------------------------------------------------");
		}
		//if(dg.containsD("amphotericin b lipid complex") != null)
		//	System.out.println("YES");


		//instance prepare
		Closeness cl = new Closeness();
		cl.setSEClosenessPT1(dg);
		System.out.println("Completed SE PT1");

		cl.setSEClosenessPT2(dg);
		System.out.println("Completed SE PT2");

		cl.setSEClosenessPT3(dg);
		System.out.println("Completed SE PT3");
		
		cl.setIClosenessPT1(dg);
		System.out.println("Completed I PT1");

		cl.setIClosenessPT2(dg);
		System.out.println("Completed I PT2");
		
		cl.setIClosenessPT3(dg);
		System.out.println("Completed I PT3");
		
		cl.setDDClosenessPT1(dg);
		System.out.println("Completed DD PT1");
		
		cl.setDDClosenessPT2(dg);
		System.out.println("Completed DD PT2");
		
		cl.setDDClosenessPT3(dg);
		System.out.println("Completed DD PT3");

		//PrepareInstances pt = new PrepareInstances();
	//	pt.makeTrainingInstances(dg,cl);
		//SaveData sd = new SaveData("train_instances.txt");
	//PrepareInstances pt = (PrepareInstances)sd.getData();
		//System.out.println(cl.ddClosenessPT1);
		//SaveData sd = new SaveData("drug_graph.txt");
		//DrugGraph dg = (DrugGraph) sd.getData();*/

	/*	System.out.println("Drugs ---------------------------->");
		int flag = 0;
		for(DrugNode drug:dg.gDrug){
		if(drug.sideEffect.size() == 0)
			countse++;
		if(drug.indication.size() == 0)
			counti++;
			//dg.printDrug(drug);
			//System.out.println("----------------------------------------------------------------------");
		}
		System.out.println(dg.gDrug.size());
		System.out.println("Indi ---> "+counti+" Se----------> "+countse);

		/*for(DrugNode drug:dg.gDrug){
			if(!rd.containsD(drug.name)){
				System.out.println(drug.name);
				break;
			}
		}

	//	SaveData sd = new SaveData("drug_graph.txt");
	//	sd.saveData(dg);	*/



		//model prepare
		/*Classifier cModel = (Classifier)new Logistic();
		PrepareModel model = new PrepareModel();
		model.prepareModel(cModel,dg);
		//System.out.println(model);
*/
		//SaveData sd = new SaveData("ml_model.txt");
		//sd.saveData(model);
		//PrepareModel model = (PrepareModel) sd.getData();
		//System.out.println(model);
	}
}