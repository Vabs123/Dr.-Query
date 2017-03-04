import java.util.*;

class VariableNode{
	NegativeEdge negativeEdges;
	PositiveEdge positiveEdges;

	public VariableNode(){
		negativeEdges = new NegativeEdge();
		positiveEdges = new PositiveEdge();
	}

	public void addSE(HashSet<String> se){
		negativeEdges.sideEffect.addAll(se);
	}
	public void addDInteraction(HashSet<String> dd){
		negativeEdges.drugInteraction.addAll(dd);
	}
	public void addIndication(HashSet<String> indi){
		positiveEdges.indication.addAll(indi);
	}
	
}