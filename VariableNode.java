import java.util.*;

class VariableNode{
	NegativeEdge negativeEdges;
	PositiveEdge positiveEdges;

	public VariableNode(){
		negativeEdges = new NegativeEdge();
		positiveEdges = new PositiveEdge();
	}

	public void addSE(LinkedList<String> se){
		negativeEdges.sideEffect.addAll(se);
	}
	public void addDInteraction(LinkedList<String> dd){
		negativeEdges.drugInteraction.addAll(dd);
	}
	public void addIndication(LinkedList<String> indi){
		positiveEdges.indication.addAll(indi);
	}
	
}