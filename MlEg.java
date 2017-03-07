import weka.core.FastVector;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.Instance;
import weka.core.DenseInstance;
import weka.classifiers.Classifier;
import weka.classifiers.functions.Logistic;
import weka.classifiers.Evaluation;


class MlEg{

	public static void main(String[] args) throws Exception{
		Attribute attribute1 = new Attribute("firstCloseness");
		Attribute attribute2 = new Attribute("secondCloseness");
		Attribute attribute3 = new Attribute("thirdCloseness");

		FastVector fvClassVal = new FastVector(2);	
		fvClassVal.addElement("positive");
		fvClassVal.addElement("negative");

		Attribute classAttribute = new Attribute("Likelihood", fvClassVal);

		FastVector fvWekaAttributes = new FastVector(4);
		fvWekaAttributes.addElement(attribute1);
		fvWekaAttributes.addElement(attribute2);
		fvWekaAttributes.addElement(attribute3);
		fvWekaAttributes.addElement(classAttribute);

		Instances isTrainingSet = new Instances("Rel", fvWekaAttributes, 5);
		isTrainingSet.setClassIndex(3);

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
		System.out.println("Likelihood = "+fDistribution[0]);
		//System.out.println(cModel);
	}
}