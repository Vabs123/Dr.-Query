import java.util.*;
import java.io.*;

class SaveData {
	File file;

	public SaveData(String file){
		this.file = new File(file);
	}

	public void saveData(Object o) {
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(o);
			out.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	public Object getData(){
		ObjectInputStream oin = null;
		Object o = null;
		try{
			if(this.file.exists() && this.file.length() != 0){				
				oin = new ObjectInputStream(new FileInputStream(file));
				o = oin.readObject();
				oin.close();
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return o;
	}

	public File getFile(){
		return this.file;
	}

	public boolean deleteFile(){
		return this.file.delete();
	}
}
