import java.util.*;
import java.io.*;
class Test{
	
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

	public static void main(String[] args) throws IOException,ClassNotFoundException,MyException{
		HashMap<String, ArrayList<Integer>> data = new HashMap<>();
		ArrayList<Integer> al;

		/*Test t = new Test();
		al = t.getData();
		data.put("ye",al);
		al = t.getData1();
		data.put("dekho",al);
		System.out.println(data);*/
		if(1 != 2)
			throw new MyException("hahaha");
		//t.save(data);
		//data = (HashMap<String, Integer>)t.get();
		//System.out.println(data); 
	}
}

class MyException extends Exception{
	MyException(String s){
		super(s);
	}
}