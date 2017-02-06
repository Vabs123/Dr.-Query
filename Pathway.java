class Pathway{
	String name;
	LinkedList<DrugNode> drugName;

	Pathway(String pathway){
		name = pathway;
		drugName = new LinkedList<>();
	}
}