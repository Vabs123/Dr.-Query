import java.util.LinkedList;
import java.io.*;
class WriteObject implements Serializable{
	LinkedList<String> lname;
	String name;

	WriteObject(String name){
		this.name = name;
		lname = new LinkedList<>();
	}

	public static void main(String[] args) throws IOException {
		FileOutputStream fo = new FileOutputStream("abc.txt");
		ObjectOutputStream out = new ObjectOutputStream(fo);
		WriteObject wo = new WriteObject("hello");
		wo.lname.offer("brother");
		wo.lname.offer("Bello");
		out.writeObject(wo);
		Poo p = new Poo(10);
		out.writeObject(p);
		fo.close();
		out.close();

	}
}
class Poo implements Serializable{
	int data;
	Poo(int d){
		data = d;
	}
}