//package vabs.drquery.helper;

import java.util.*;
import java.io.*;

/***********************************************
* These candidates will be used during testing.
* Here we are finding candidates of particular
* type and keyword
***********************************************/

public class CandidateMatch{
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
		seee.add("high fever");
		v.addSE(seee);
		SaveData sd = new SaveData("drug_graph.txt");
		DrugGraph dg = (DrugGraph)sd.getData();
		




		CandidateMatch cm = new CandidateMatch();
		cm.matchCandidates(v,dg);
		System.out.println(cm.seCandidates);
		for(SideEffect s:cm.seCandidates)
			System.out.println(s.drugName);

	}
}