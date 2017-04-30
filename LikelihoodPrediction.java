//package vabs.drquery.machine_learning;

//weka library
import weka.core.FastVector;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.Instance;
import weka.core.DenseInstance;
import weka.classifiers.Classifier;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.functions.Logistic;
import weka.classifiers.Evaluation;
//java
import java.util.*;
import java.io.*;
import java.math.BigDecimal;
//personal
//import vabs.drquery.helper.TrainInput;
//import vabs.drquery.helper.Closeness;

/**********************************************************************
* This class will be used for prediction of likelihood.
* The machine learning models were prepared in PrepapreModel
* class. Test instances will be prepared in getCloseness 
* function in Closeness class.
**********************************************************************/

class LikelihoodPrediction{
	HashMap<String, LinkedList<BigDecimal>> seLikelihood;
	HashMap<String, LinkedList<BigDecimal>> iLikelihood;
	HashMap<String, LinkedList<BigDecimal>> ddLikelihood;




	private void saveLikelihood(String keyword, String type, BigDecimal likelihood) throws Exception{
		LinkedList<BigDecimal> val = null;
		if(type.equals("SideEffect")){
			if(seLikelihood == null){
				val = new LinkedList<>();
				val.add(likelihood);
				seLikelihood = new HashMap<>();
				seLikelihood.put(keyword, val);
			}
			else{
				if(seLikelihood.containsKey(keyword)){
					val = seLikelihood.get(keyword);
					val.add(likelihood);
				}
				else{
					val = new LinkedList<>();
					val.add(likelihood);
					seLikelihood.put(keyword, val);
				}
			}	
		}
		else if(type.equals("Indication")){
			if(iLikelihood == null){
				val = new LinkedList<>();
				val.add(likelihood);
				iLikelihood = new HashMap<>();
				iLikelihood.put(keyword, val);
			}
			else{
				if(iLikelihood.containsKey(keyword)){
					val = iLikelihood.get(keyword);
					val.add(likelihood);
				}
				else{
					val = new LinkedList<>();
					val.add(likelihood);
					iLikelihood.put(keyword, val);
				}
			}	
		}
		else if(type.equals("DrugInteraction")){
			if(ddLikelihood == null){
				val = new LinkedList<>();
				val.add(likelihood);
				ddLikelihood = new HashMap<>();
				ddLikelihood.put(keyword, val);
			}
			else{
				if(ddLikelihood.containsKey(keyword)){
					val = ddLikelihood.get(keyword);
					val.add(likelihood);
				}
				else{
					val = new LinkedList<>();
					val.add(likelihood);
					ddLikelihood.put(keyword, val);
				}
			}	
		}
		else
			throw new Exception("Please enter a valid Type");
	}


	public void predictLikelihood(TrainInput trainInput,String keyword, String type)throws Exception{
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

		SaveData sd = new SaveData("ml_model.txt");
		PrepareModel prepareModel = (PrepareModel) sd.getData();
		//Pertype prediction
		///Mleg se evaluation and prediction copy karo. Classifier agya model se, abhi ke liye
		//per type karo. Instance train Instance se bnao ya prepare model se function ke through mangwa lo

		Classifier cModel =	prepareModel.getPerTypeModel(type);


		Instance test = new DenseInstance(3);
		test.setValue((Attribute)fvModelAttributes.elementAt(0), trainInput.cPT1);
 		test.setValue((Attribute)fvModelAttributes.elementAt(1), trainInput.cPT2);
 		test.setValue((Attribute)fvModelAttributes.elementAt(2), trainInput.cPT3);
 		//iUse.setValue((Attribute)fvWekaAttributes.elementAt(3), "positive");
		//iUse.setDataset(isTrainingSet);
		//System.out.println(iUse.classAttribute());
		double[] fDistribution = cModel.distributionForInstance(test);
		BigDecimal prediction = new BigDecimal(fDistribution[0]);
		//System.out.println("Likelihood = "+fDistribution[0]);
		System.out.println("Likelihood = "+prediction.toString());
		saveLikelihood(keyword, type, prediction);
	}
	
	public void setLikelihood(DrugGraph dg, String drugName, String drugProperty, String propertyType)throws Exception{
		BigDecimal likelihood = null;
		DrugNode drug = dg.containsD(drugName);
		if(propertyType.equals("SideEffect")){
			if(drug.containsSE(drugProperty) != null){
				likelihood = new BigDecimal("1");
				saveLikelihood(drugProperty, propertyType, likelihood);
			}
		}
		else if(propertyType.equals("Indication")){
			if(drug.containsI(drugProperty) != null){
				likelihood = new BigDecimal("1");
				saveLikelihood(drugProperty, propertyType, likelihood);
			}
		}
		else if(propertyType.equals("DrugInteraction")){
			if(drug.containsDD(drugProperty) != null){
				likelihood = new BigDecimal("1");
				saveLikelihood(drugProperty, propertyType, likelihood);
			}
		}
		if(likelihood == null)
			predictLikelihood(PrepareInstances.getTestInstance(dg, drugName, drugProperty, propertyType), drugProperty ,propertyType);
	}

	public static void main(String[] args) {
		
	}
}