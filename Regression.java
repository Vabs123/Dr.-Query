import java.io.BufferedReader;
import java.io.FileReader;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.Classifier;

class Regression1{
	public static void main(String args[]) throws Exception{
//load data
		Instances data = new Instances(new BufferedReader(new
		FileReader("house.arff")));
		data.setClassIndex(data.numAttributes() - 1);
//build model
		Classifier model = (Classifier)new LinearRegression();
		model.buildClassifier(data); //the last instance with missing class is not used
		System.out.println(model);
//classify the last instance
	Instance myHouse = data.lastInstance();
	double price = model.classifyInstance(myHouse);
System.out.println("My house ("+myHouse+"): "+price);
}
}