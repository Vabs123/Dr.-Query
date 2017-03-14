import java.util.*;
import java.io.*;

class CandidateMatch{
	LinkedList<SideEffect> seCandidates;
	LinkedList<DrugNode> ddCandidates;
	LinkedList<Indication> iCandidates;

	CandidateMatch(){
		seCandidates = new LinkedList<>();
		ddCandidates = new LinkedList<>();
		iCandidates = new LinkedList<>();
	}
	
	public void matchCandidates(VariableNode vNode, DrugGraph dg){
			for(String s:vNode.negativeEdges.sideEffect)
				seCandidates.add(dg.containsSE(s));
			for(String s:vNode.negativeEdges.drugInteraction)
				ddCandidates.add(dg.containsD(s));
			for(String s:vNode.positiveEdges.indication)
				iCandidates.add(dg.containsI(s));
	
		/****
				Check if Synonyms exist then we will have
				list of candidates
				Otherwise Only single name exixts
				The code is for if no synonyms.
		****/
	}

	public static void main(String[] args) throws IOException,ClassNotFoundException{
		VariableNode v = new VariableNode();
		LinkedList<String> seee = new LinkedList<>();
		seee.add("i1");
		seee.add("i4");
		v.addIndication(seee);
		SaveData sd = new SaveData("drugDump.txt");
		DrugGraph dg = (DrugGraph)sd.getData();
		




		CandidateMatch cm = new CandidateMatch();
		cm.matchCandidates(v,dg);
		System.out.println(cm.iCandidates);
		for(Indication s:cm.iCandidates)
			System.out.println(s.drugName);

	}
}