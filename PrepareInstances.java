import java.util.*;
import java.io.*;


class PrepareInstances implements Serializable{
	HashMap<String, LinkedList<TrainInput>> sePerNodeTrainInput;  
	HashMap<String, LinkedList<TrainInput>> iPerNodeTrainInput;
	HashMap<String, LinkedList<TrainInput>> ddPerNodeTrainInput;
	LinkedList<TrainInput> sePerTypeTrainInput;
	LinkedList<TrainInput> iPerTypeTrainInput;
	LinkedList<TrainInput> ddPerTypeTrainInput;


/************************************************************************
*Below is preparation of training instances
*************************************************************************/

	public LinkedList<TrainInput> getPerNodeTrainingInstances(String type, String keyword) throws TypeNotMatchedException{
		if(type.equals("SideEffect")) return sePerNodeTrainInput.get(keyword);
		if(type.equals("Indication")) return iPerNodeTrainInput.get(keyword);
		if(type.equals("DrugInteraction")) return ddPerNodeTrainInput.get(keyword);
		throw new TypeNotMatchedException("Please enter a valid type.");
	}

	public LinkedList<TrainInput> getPerTypeTrainingInstances(String type) throws TypeNotMatchedException{
		if(type.equals("SideEffect")) return sePerTypeTrainInput;
		if(type.equals("Indication")) return iPerTypeTrainInput;
		if(type.equals("DrugInteraction")) return ddPerTypeTrainInput;
		throw new TypeNotMatchedException("Please enter a valid type.");
	}

	public void makeTrainingInstances(DrugGraph dg, Closeness cl) throws SizeNotMatchedException,IOException {
		sePerNodeTrainInput = new HashMap<>();
		iPerNodeTrainInput = new HashMap<>();
		ddPerNodeTrainInput = new HashMap<>();
		sePerTypeTrainInput = new LinkedList<>();
		iPerTypeTrainInput = new LinkedList<>();
		ddPerTypeTrainInput = new LinkedList<>(); 
		TrainInput trainInput;
		LinkedList<TrainInput> temp;
		ArrayList<Integer> c1,c2,c3;
		for(SideEffect se:dg.gSideEffect){
			temp = new LinkedList<>();
			c1 = cl.seClosenessPT1.get(se.name);
			c2 = cl.seClosenessPT2.get(se.name);
			c3 = cl.seClosenessPT3.get(se.name);
			if(c1.size() != c2.size() || c1.size() != c3.size())
				throw new SizeNotMatchedException("sideEffect sizes didn't match");
			for(int i = 0;i < c1.size(); i++){
				trainInput = new TrainInput(c1.get(i),c2.get(i),c3.get(i));
				sePerTypeTrainInput.add(trainInput);
				temp.add(trainInput);
			}
			sePerNodeTrainInput.put(se.name,temp);
		}
		for(Indication indi:dg.gIndication){
			temp = new LinkedList<>();
			c1 = cl.iClosenessPT1.get(indi.name);
			c2 = cl.iClosenessPT2.get(indi.name);
			c3 = cl.iClosenessPT3.get(indi.name);
			if(c1.size() != c2.size() || c1.size() != c3.size())
				throw new SizeNotMatchedException("indications sizes didn't match");
			for(int i = 0;i < c1.size(); i++){
				trainInput = new TrainInput(c1.get(i),c2.get(i),c3.get(i));
				iPerTypeTrainInput.add(trainInput);
				temp.add(trainInput);
			}	
			iPerNodeTrainInput.put(indi.name, temp);
		}
		for(DrugNode dd:dg.gDrug){
			temp  = new LinkedList<>();
			c1 = cl.ddClosenessPT1.get(dd.name);
			c2 = cl.ddClosenessPT2.get(dd.name);
			c3 = cl.ddClosenessPT3.get(dd.name);
			if(c1.size() != c2.size() || c1.size() != c3.size())
				throw new SizeNotMatchedException("Drug Interaction sizes didn't match");
			for(int i = 0;i < c1.size(); i++){
				trainInput = new TrainInput(c1.get(i),c2.get(i),c3.get(i));
				ddPerTypeTrainInput.add(trainInput);
				temp.add(trainInput);
			}
			ddPerNodeTrainInput.put(dd.name, temp);
		}
		SaveData sd = new SaveData("train_instances.txt");
		sd.saveData(this);
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder("SideEffect Training Inputs --------------------------------------------------------------> ");
		sb.append(sePerNodeTrainInput.toString());
		sb.append("\n");
		sb.append("Indication Training Inputs ----------------------------------------------------------------------------------------> ");
		sb.append(iPerNodeTrainInput.toString());
		sb.append("\n");
		sb.append("Drug-Drug Training Inputs -----------------------------------------------------------------------------------------> ");
		sb.append(ddPerNodeTrainInput.toString());
		sb.append("\n");
		return sb.toString();
	}
}

class SizeNotMatchedException extends Exception{
	SizeNotMatchedException(String s){
		super(s);
	}
}

class TypeNotMatchedException extends Exception{
	TypeNotMatchedException(String s){
		super(s);
	}
}