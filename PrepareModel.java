import weka.core.FastVector;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.Instance;
import weka.core.DenseInstance;
import weka.classifiers.Classifier;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.functions.Logistic;
import weka.classifiers.Evaluation;

import java.util.*;
import java.io.*;


//Implementation of Per Node training model
class PrepareModel{
	HashMap<String, Classifier> sePerNodeModel;
	HashMap<String, Classifier> iPerNodeModel;
	HashMap<String, Classifier> ddPerNodeModel;
	Classifier sePerTypeModel;
	Classifier iPerTypeModel;
	Classifier ddPerTypeModel;

	PrepareModel(){
		sePerNodeModel = new HashMap<>();
		iPerNodeModel = new HashMap<>();
		ddPerNodeModel = new HashMap<>();
	}

	//method for preparing instances set for input 
	public Instances prepareInstance(PrepareInstances trainingInstances, FastVector modelAttributes,
	 String type, String keyword, boolean isPerNode) throws Exception{
		//List of instances
		LinkedList<TrainInput> input = null;
		//training set
		Instances trainingSet = new Instances(type, modelAttributes,90000); 
		trainingSet.setClassIndex(3);
		//Single instance
		Instance instance;
		if(isPerNode)//get instances for a particular type(dd,i,se) and keyword (per node model)
			input = trainingInstances.getPerNodeTrainingInstances(type, keyword);
		else//get instances for a particular type(dd,i,se) (per type model)
			input = trainingInstances.getPerTypeTrainingInstances(type);
		//System.out.println(input);
		//creating training set	
		for(TrainInput trainInput:input){
			//creating single training instance
			instance = new DenseInstance(4);
			instance.setValue((Attribute)modelAttributes.elementAt(0), trainInput.cPT1);
 			instance.setValue((Attribute)modelAttributes.elementAt(1), trainInput.cPT2);
 			instance.setValue((Attribute)modelAttributes.elementAt(2), trainInput.cPT2);
 			instance.setValue((Attribute)modelAttributes.elementAt(3), "positive");
 			trainingSet.add(instance);
		}
		return trainingSet;
	}
	
	//method for preparing side effect model
	public void prepareSEModel(PrepareInstances trainingInstances, FastVector modelAttributes, DrugGraph dg, Classifier cModel) throws Exception{
	//	sePerNodeModel = new HashMap<>();
		Classifier copyModel = null;
		Instances trainingSet = null;
		//per node model preparation
		for(SideEffect se:dg.gSideEffect){
			copyModel = AbstractClassifier.makeCopy(cModel);
			trainingSet = prepareInstance(trainingInstances, modelAttributes, "SideEffect", se.name, true);
			copyModel.buildClassifier(trainingSet);
			sePerNodeModel.put(se.name,copyModel);
		}
		//per type model preparation
		trainingSet = prepareInstance(trainingInstances, modelAttributes, "SideEffect","", false);
		sePerTypeModel = AbstractClassifier.makeCopy(cModel);
		sePerTypeModel.buildClassifier(trainingSet);
	}

	//method for preparing indication model
	public void prepareIModel(PrepareInstances trainingInstances, FastVector modelAttributes, DrugGraph dg, Classifier cModel) throws Exception{
		//iPerNodeModel = new HashMap<>();
		Classifier copyModel = null;
		Instances trainingSet = null;
		//per node model preparation
		for(Indication i:dg.gIndication){
			copyModel = AbstractClassifier.makeCopy(cModel);
			trainingSet = prepareInstance(trainingInstances, modelAttributes, "Indication", i.name, true);
			copyModel.buildClassifier(trainingSet);
			iPerNodeModel.put(i.name,copyModel);
		}
		//per type model preparation
		trainingSet = prepareInstance(trainingInstances, modelAttributes, "Indication", "", false);
		iPerTypeModel = AbstractClassifier.makeCopy(cModel);
		iPerTypeModel.buildClassifier(trainingSet);
	}

	//method for preparing drug interaction model
	public void prepareDDModel(PrepareInstances trainingInstances, FastVector modelAttributes, DrugGraph dg, Classifier cModel) throws Exception{
		//ddPerNodeModel = new HashMap<>();
		Classifier copyModel = null;
		Instances trainingSet = null;
		//per node model preparation
		for(DrugNode dd:dg.gDrug){
			copyModel = AbstractClassifier.makeCopy(cModel);
			trainingSet = prepareInstance(trainingInstances, modelAttributes, "DrugInteraction", dd.name, true);
			copyModel.buildClassifier(trainingSet);
			ddPerNodeModel.put(dd.name,copyModel);
		}
		//per type model preparation
		trainingSet = prepareInstance(trainingInstances, modelAttributes, "DrugInteraction","", false);
		ddPerTypeModel = AbstractClassifier.makeCopy(cModel);
		ddPerTypeModel.buildClassifier(trainingSet);
	}

	public void prepareModel(Classifier cModel) throws Exception{
		//Declaring numeric attributes
		Attribute attribute1 = new Attribute("firstCloseness");
		Attribute attribute2 = new Attribute("secondCloseness");
		Attribute attribute3 = new Attribute("thirdCloseness");
		//Declaring nominal attribute 
		FastVector fvClassVal = new FastVector(2);	
		fvClassVal.addElement("positive");
		fvClassVal.addElement("negative");
		Attribute classAttribute = new Attribute("Likelihood", fvClassVal);
		//Declare the feature vector
		FastVector fvModelAttributes = new FastVector(4);
		fvModelAttributes.addElement(attribute1);
		fvModelAttributes.addElement(attribute2);
		fvModelAttributes.addElement(attribute3);
		fvModelAttributes.addElement(classAttribute);
		//declare the drugGraph object
		SaveData sd = new SaveData("drug_graph.txt");
		DrugGraph dg = (DrugGraph)sd.getData();
		//declare the PrepareInstances object
		sd = new SaveData("train_instances.txt");
		PrepareInstances trainingInstances = (PrepareInstances)sd.getData();

		prepareSEModel(trainingInstances, fvModelAttributes, dg, cModel);
		prepareIModel(trainingInstances, fvModelAttributes, dg, cModel);
		//prepareDDModel(trainingInstances, fvModelAttributes, dg, cModel);

	}

	@Override
	public String toString(){
		String s = "SideEffect PerNodal Model:\n";
		StringBuilder sb = new StringBuilder(s);
		sb.append(sePerNodeModel.toString());
		sb.append("-----------------------------------------------\n"); 
		sb.append("SideEffect PerType Model:\n"); 
		sb.append(sePerTypeModel.toString()); 
		sb.append("-----------------------------------------------\n"); 
		sb.append("-----------------------------------------------\n"); 
		sb.append("Indication PerNode Model:\n"); 
		sb.append(iPerNodeModel.toString()); 
		sb.append("-----------------------------------------------\n"); 
		sb.append("Indication PerType Model:\n"); 
		sb.append(iPerTypeModel.toString()); 
		sb.append("-----------------------------------------------\n"); 
		sb.append("-----------------------------------------------\n"); 
		/*sb.append("Drug Interaction PerNode Model:\n"); 
		sb.append(ddPerNodeModel.toString()); 
		sb.append("-----------------------------------------------\n"); 
		sb.append("Drug Interaction PerType Model:\n"); 
		sb.append(ddPerTypeModel.toString()); 
		sb.append("-----------------------------------------------\n"); */
		return sb.toString();
		
	} 

	public static void main(String[] args) throws Exception{

		Classifier cModel = (Classifier)new Logistic();
		PrepareModel model = new PrepareModel();
		model.prepareModel(cModel);
		System.out.println(model);

		/*Attribute attribute1 = new Attribute("firstCloseness");
		Attribute attribute2 = new Attribute("secondCloseness");
		Attribute attribute3 = new Attribute("thirdCloseness");

		FastVector fvClassVal = new FastVector(2);	
		fvClassVal.addElement("positive");
		fvClassVal.addElement("negative");

		Attribute classAttribute = new Attribute("Likelihood", fvClassVal);

		FastVector fvModelAttributes = new FastVector(4);
		fvModelAttributes.addElement(attribute1);
		fvModelAttributes.addElement(attribute2);
		fvModelAttributes.addElement(attribute3);
		fvModelAttributes.addElement(classAttribute);

 		//////////////////////////////////////////////

		SaveData sd = new SaveData("drugDump.txt");
		DrugGraph dg = (DrugGraph)sd.getData();

		sd = new SaveData("closeness.txt");
		Closeness closeness = (Closeness)sd.getData(); 

			////////////////////////////////


		Instances isTrainingSet = new Instances("Rel", fvWekaAttributes, 20);
		isTrainingSet.setClassIndex(3);

		

		for()




		Instance iExample = new DenseInstance(4);
		iExample.setValue((Attribute)fvWekaAttributes.elementAt(0), 123);
 		iExample.setValue((Attribute)fvWekaAttributes.elementAt(1), 500);
 		iExample.setValue((Attribute)fvWekaAttributes.elementAt(2), 400);
 		iExample.setValue((Attribute)fvWekaAttributes.elementAt(3), "positive");

 		isTrainingSet.add(iExample);

 		Classifier cModel = (Classifier)new Logistic();
 		cModel.buildClassifier(isTrainingSet);

 		Instances isTestingSet = new Instances("RelTest", fvWekaAttributes, 5);
		isTestingSet.setClassIndex(3);

		iExample = new DenseInstance(4);
		iExample.setValue((Attribute)fvWekaAttributes.elementAt(0), 120);
 		iExample.setValue((Attribute)fvWekaAttributes.elementAt(1), 550);
 		iExample.setValue((Attribute)fvWekaAttributes.elementAt(2), 440);
 		iExample.setValue((Attribute)fvWekaAttributes.elementAt(3), "negative");

 		isTestingSet.add(iExample);

 		Evaluation eTest = new Evaluation(isTrainingSet);
 		eTest.evaluateModel(cModel,isTestingSet);

 		String strSummary = eTest.toSummaryString();
 		System.out.println(strSummary);

 		Instance iUse = new DenseInstance(3);
		iUse.setValue((Attribute)fvWekaAttributes.elementAt(0), 120);
 		iUse.setValue((Attribute)fvWekaAttributes.elementAt(1), 550);
 		iUse.setValue((Attribute)fvWekaAttributes.elementAt(2), 440);
 		//iUse.setValue((Attribute)fvWekaAttributes.elementAt(3), "positive");
		iUse.setDataset(isTrainingSet);
		double[] fDistribution = cModel.distributionForInstance(iUse);
		System.out.println("Likelihood = "+fDistribution[0]);*/
		//System.out.println(cModel);*/
	}
}