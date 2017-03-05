import java.util.*;
import java.io.*;

class SaveData {
	File file;

	public SaveData(String file){
		this.file = new File(file);
	}

	public void saveData(Object o) throws IOException{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(o);
		out.close();
	}

	public Object getData() throws IOException, ClassNotFoundException{
		ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
		Object o = oin.readObject();
		oin.close();
		return o;
	}
}
