import java.util.*;

class Test{
	String name;
	Test t;
	Test(String n){
		name = n;
		t = null;
	}

	@Override
	public String toString(){
		return name;
	}


	public static void main(String[] args) {
		Test t1 = new Test("hello");
		Test t2 = new Test("hello");
		String s1 = t1.toString();
		String s2 = t2.toString();
		System.out.println(t1.toString().equals(t2.toString()));
	}
}