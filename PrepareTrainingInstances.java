import java.util.*;
import java.io.*;

class PrepareTrainingInstances implements Serializable{
	LinkedList<TrainInput> seTrainInput;
	LinkedList<TrainInput> iTrainInput;
	LinkedList<TrainInput> ddTrainInput;

	public void makeInstances(DrugGraph dg, Closeness cl) throws SizeNotMatchedException,IOException {
		seTrainInput = new LinkedList<>();
		iTrainInput = new LinkedList<>();
		ddTrainInput = new LinkedList<>(); 
		TrainInput trainInput;
		ArrayList<Integer> c1,c2,c3;
		for(SideEffect se:dg.gSideEffect){
			c1 = cl.seClosenessPT1.get(se.name);
			c2 = cl.seClosenessPT2.get(se.name);
			c3 = cl.seClosenessPT3.get(se.name);
			if(c1.size() != c2.size() || c1.size() != c3.size())
				throw new SizeNotMatchedException("sideEffect sizes didn't match");
			for(int i = 0;i < c1.size(); i++){
				trainInput = new TrainInput(c1.get(i),c2.get(i),c3.get(i));
				seTrainInput.add(trainInput);
			}
		}
		for(Indication indi:dg.gIndication){
			c1 = cl.iClosenessPT1.get(indi.name);
			c2 = cl.iClosenessPT2.get(indi.name);
			c3 = cl.iClosenessPT3.get(indi.name);
			if(c1.size() != c2.size() || c1.size() != c3.size())
				throw new SizeNotMatchedException("indications sizes didn't match");
			for(int i = 0;i < c1.size(); i++){
				trainInput = new TrainInput(c1.get(i),c2.get(i),c3.get(i));
				iTrainInput.add(trainInput);
			}	
		}
		for(DrugNode dd:dg.gDrug){
			c1 = cl.ddClosenessPT1.get(dd.name);
			c2 = cl.ddClosenessPT2.get(dd.name);
			c3 = cl.ddClosenessPT3.get(dd.name);
			if(c1.size() != c2.size() || c1.size() != c3.size())
				throw new SizeNotMatchedException("sideEffect sizes didn't match");
			for(int i = 0;i < c1.size(); i++){
				trainInput = new TrainInput(c1.get(i),c2.get(i),c3.get(i));
				ddTrainInput.add(trainInput);
			}
		}
		SaveData sd = new SaveData("train_instances.txt");
		sd.saveData(this);
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder("SideEffect Training Inputs --> ");
		sb.append(seTrainInput.toString());
		sb.append("\n");
		sb.append("Indication Training Inputs --> ");
		sb.append(iTrainInput.toString());
		sb.append("\n");
		sb.append("Drug-Drug Training Inputs --> ");
		sb.append(ddTrainInput.toString());
		sb.append("\n");
		return sb.toString();
	}
}

class SizeNotMatchedException extends Exception{
	SizeNotMatchedException(String s){
		super(s);
	}
}