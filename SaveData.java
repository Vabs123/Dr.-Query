import java.util.*;
import java.io.*;

class SaveData {
	HashSet<DrugNode> drugList;
	HashSet<SideEffect> sideEffectList;
	HashSet<Indication> indicationList;
	HashSet<Target> targetList;
	HashSet<Pathway> pathwayList;

	public SaveData(HashSet<DrugNode> drugList, HashSet<SideEffect> sideEffectList,
	 				HashSet<Indication> indicationList, HashSet<Target> targetList, 
	 				HashSet<Pathway> pathwayList){
		this.drugList = drugList;
		this.sideEffectList = sideEffectList;
		this.indicationList = indicationList;
		this.targetList = targetList;
		this.pathwayList = pathwayList;
	}

	public void save() throws IOException{
		FileOutputStream file = new FileOutputStream("drugDump.txt");
		ObjectOutputStream out = new ObjectOutputStream(file);
		out.writeObject(drugList);
		out.writeObject(sideEffectList);
		out.writeObject(indicationList);
		out.writeObject(targetList);
		out.writeObject(pathwayList);
		out.close();
		file.close();
	}

	public void getData() throws IOException, ClassNotFoundException{
		FileInputStream file = new FileInputStream("drugDump.txt");
		ObjectInputStream oin = new ObjectInputStream(file);

		drugList = (HashSet<DrugNode>) oin.readObject();
		sideEffectList = (HashSet<SideEffect>) oin.readObject();
		indicationList = (HashSet<Indication>) oin.readObject();
		targetList = (HashSet<Target>) oin.readObject();
		pathwayList = (HashSet<Pathway>) oin.readObject();
	}
}
