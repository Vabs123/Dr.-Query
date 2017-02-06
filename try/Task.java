import java.util.*;

class Task{
	LinkedList<Ob> ll;
	Task(){
		ll = new LinkedList<>();
	}

	public void contains(LinkedList ll, Object o){
		
		if(o.getClass().getName().equals("Ob")){
		ListIterator<Ob> it = ll.listIterator();
		while(it.hasNext()){
			if(it.next().name == "hello")
				System.out.println(it.previous().name);
			it.next();
		}}
	}
	public static void main(String[] args) {
		Task t = new Task();
		Ob ob = new Ob("hello");
		Ob ob1 = new Ob("bello");
		//Iterator it;
		t.ll.offer(ob);
		t.ll.offer(ob1);
		t.contains(t.ll, ob);
	/*	Class c = ob.getClass();
		System.out.println(c.getName());
try{
	
		System.out.println( c.newInstance().name);
	}
	catch (Exception e) {
		
	}
		/*;
		String s = ob.getClass().getName();
		if(s.equals("Ob"))
		 it = t.ll.iterator();
		while(it.hasNext()){
			System.out.println(it.next().name);
		}*/
		
	//	t.contains(t.ll);
		//Class my = ob.getClass().getName;
		//Class my = ob.getClass();
	//	for(:t.ll){
	//		System.out.println(a.name);
	//	}
		
		//System.out.println(it.next().name);
	}
}

class Ob{
	String name;
	Ob(String name){
		this.name = name;
	}
	Ob(){
		name= "hello";
	}
}