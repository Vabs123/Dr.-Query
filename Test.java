import java.util.*;
import java.io.*;
class Test implements Serializable,Cloneable{
	int x;
	
	public void save(Object o)throws IOException{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("abc.txt"));
		out.writeObject(o);
		out.close();
	}

	public Object get()throws IOException, ClassNotFoundException{
		ObjectInputStream oin = new ObjectInputStream(new FileInputStream("abc.txt"));
		Object o = oin.readObject();
		oin.close();
		return o;
	}

	public ArrayList<Integer> getData(){
		ArrayList<Integer> a = new ArrayList<Integer>();
		a.add(5);
		return a;
	}
	public ArrayList<Integer> getData1(){
		ArrayList<Integer> a = new ArrayList<Integer>();
		a.add(9);
		return a;
	}

	public static void day(String s,boolean f){
		if(f)
			System.out.println(s);
		else
			System.out.println("yeah");
	}

	@Override
	public Object clone()throws CloneNotSupportedException{
		return super.clone();
	}

	public static void main(String[] args) throws Exception{
		//HashMap<String, ArrayList<Integer>> data = new HashMap<>();
		//ArrayList<Integer> al;
	/*	Test t = new Test();
		t.day();
		SaveData sd = new SaveData("test.txt");
		t = (Test)sd.getData();
		System.out.println(t.x);
		/*Closeness cl = (Closeness)new SaveData("closeness.txt").getData();
		DrugGraph dg = (DrugGraph)new SaveData("drugDump.txt").getData();
		SideEffect se = dg.gSideEffect.get(0);
		System.out.println(cl.getCloseness("SideEffect",se.name));*/

		//t.save(data);
		//data = (HashMap<String, Integer>)t.get();
		//System.out.println(data); 
	
		day("hello",true);
		day("",false);

	}
}