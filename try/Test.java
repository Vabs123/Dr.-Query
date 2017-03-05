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

	public static void main(String[] args) throws IOException,ClassNotFoundException{
		HashMap<String, Integer> data = new HashMap<>();
		data.put("hello",1);
		Test t = new Test();
		t.save(data);
		data = (HashMap<String, Integer>)t.get();
		System.out.println(data); 
	}
}