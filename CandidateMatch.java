import java.util.*;

class CandidateMatch{
	HashSet<SideEffect> seCandidates;
	HashSet<DrugNode> ddCandidates;
	HashSet<Indication> iCandidates;

	CandidateMatch(){
		seCandidates = new HashSet<>();
		ddCandidates = new HashSet<>();
		iCandidates = new HashSet<>();
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

	public static void main(String[] args) {
		VariableNode v = new VariableNode();
		HashSet<String> seee = new HashSet<>();
		seee.add("i1");
		seee.add("i2");
		v.addIndication(seee);
		DrugGraph dg = new DrugGraph();
		HashSet<String> se = new HashSet<>();
		HashSet<String> i = new HashSet<>();
		HashSet<String> t = new HashSet<>();
		HashSet<String> p = new HashSet<>();
		HashSet<String> d_d = new HashSet<>();
		se.add("se1");
		se.add("se2");
		se.add("se3");

		i.add("i1");
		i.add("i2");
		i.add("i3");

		t.add("t1");
		t.add("t2");
		t.add("t3");

		p.add("p1");
		p.add("p2");
		p.add("p3");

		d_d.add("drug2");
		d_d.add("drug3");
		d_d.add("drug4");

		
		dg.create("drug1", se, i, t, p, d_d);
		




		 se = new HashSet<>();
		 i = new HashSet<>();
		 t = new HashSet<>();
		 p = new HashSet<>();
	    d_d = new HashSet<>();
		se.add("se1");
		se.add("se5");
		se.add("se6");

		i.add("i4");
		i.add("i5");
		i.add("i6");

		t.add("t9");
		t.add("t8");
		t.add("t7");

		p.add("p6");
		p.add("p0");
		p.add("p5");

		d_d.add("drug9");
		d_d.add("drug8");
		d_d.add("drug7");

		
		dg.create("drug2", se, i, t, p, d_d);








		CandidateMatch cm = new CandidateMatch();
		cm.matchCandidates(v,dg);
		System.out.println(cm.iCandidates);
		for(Indication s:cm.iCandidates)
			System.out.println(s.drugName);

	}
}