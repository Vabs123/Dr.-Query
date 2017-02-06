import java.util.LinkedList;
import java.io.*;

class ReadObject{
	LinkedList<String> l = new LinkedList<>();
	WriteObject wo = null;

	public static void main(String[] args) throws IOException, ClassNotFoundException{
		ReadObject ro = new ReadObject();
		FileInputStream fi = new FileInputStream("abc.txt");
		ObjectInputStream oin = new ObjectInputStream(fi);
		ro.wo = (WriteObject) oin.readObject();
		System.out.println(ro.wo.lname+"ppppp");
		fi.close();
		oin.close();
	}
}