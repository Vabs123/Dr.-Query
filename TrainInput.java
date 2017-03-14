import java.io.Serializable;

class TrainInput implements Serializable{
	int cPT1,cPT2,cPT3;
	TrainInput(int cPT1, int cPT2, int cPT3){
		this.cPT1 = cPT1;
		this.cPT2 = cPT2;
		this.cPT3 = cPT3;
	}

	@Override
	public String toString(){
		String s = "["+cPT1+", "+cPT2+", "+cPT3+"]";
		return s;
	}
}