import com.jaunt.*;
import com.jaunt.component.*;
import java.io.*;
import java.util.*;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import java.util.regex.*;
import java.io.File;  
import java.io.PrintWriter;


class Info implements Serializable
{
  private class TrieNode implements Serializable
  {
    Map<Character,TrieNode> child;
    boolean endOfWord;
    char c;
    
    TrieNode()
    {
      child=new HashMap<>();
      endOfWord=false;
    }
  }
  TrieNode root;
  String drugname;
  LinkedList<DrugInfo> druglinkedlist=new LinkedList<DrugInfo>();
  HashSet<String> setOfDrugs=new HashSet<String>();
  

  HashSet<String> setOfDiseases=new HashSet<String>();
  HashSet<String> setOfDrugInteraction=new HashSet<String>();
  HashSet<String> setOfSideEffects=new HashSet<String>();
	
	Info()
	{
		root=new TrieNode();
		String drugname="";

	}
	/* insert disease in trie */
	public void insertDisease(String disease)
  	{
    insertDisease(root,disease,0);
  	}
 	public void insertDisease(TrieNode current,String disease,int index)
  	{
    	if(index==disease.length())
    	{
      		current.endOfWord=true;
      		return;
    	}
    	char ch=disease.charAt(index);
    	TrieNode node=current.child.get(ch);
    	if(node==null)
    	{
      		node=new TrieNode();
      		current.child.put(ch,node);
   		}
  		insertDisease(node,disease,index+1);
  	}
  	/* search for disease in the sentences */
  	public String searchDisease(String disease)
  	{
    	return searchDisease(root,disease,0);
  	}
  	public String searchDisease(TrieNode current,String disease,int index)
  	{
    	if(current.endOfWord){

        if (index == disease.length()) {
        return disease;
        }
        /* check if end of word is true and the 
        	last element of the word to searched
        	is '.' or ',' or 's' .For example we have 
        	to search for 'colds' it should return
        	'cold'.    */
      else if((index==disease.length()-1 && Pattern.matches("(.*)[\\.,s]{1}", disease)))
      {
        String temp=disease.substring(0,disease.length()-1);
        return temp;
      }
      /* check if end of word is true and the 
        	second last element of the word to searched
        	is '.' or ',' or 's' along with last element equals
        	 ',' or '.'.For example we have to search for 
        	 'colds.' or 'colds,' it should return 'cold'.    */
        	
     else if((index==disease.length()-2 && Pattern.matches("(.*)[s]{1}[,\\.]{1}", disease)))
     {
        String temp=disease.substring(0,disease.length()-2);
        return temp;
     }
   }
   /* check if subpart of the disease name exists witout end of 
   line if it exists then return the subpart else return null.
   eg:- hair loss is a disease but hair is not so while searching
   hair in trie then on reaching last index end of word would be false
   so return hair and append it to next word and check again */
      if(index==disease.length() && !current.endOfWord  )
        {
          TrieNode node1 = current.child.get(' ');
          if(node1==null)
          {
            return null;
          }
          else{
            disease=disease+"0";
            return disease;
          }
        }
     
        
        char ch = disease.charAt(index);

        TrieNode node = current.child.get(ch);
        if (node == null) {
            return null;
        }
       return searchDisease(node, disease, index + 1);
  
  }	
	public static void main(String... s) throws Exception
  {
  	int count=0;
    int index=0;
  	Info trie=new Info();
  	FileOutputStream fos = new FileOutputStream("datatextn.txt");
	ObjectOutputStream oos = new ObjectOutputStream(fos);
	ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream("datatextn.txt",true)){
            protected void writeStreamHeader() throws IOException {
                reset();
            }
        };

    try
    {
      int t=0;
      UserAgent userAgent=new UserAgent();
      userAgent.visit("https://www.drugs.com/medical_conditions.html");
      Element alpha_div=userAgent.doc.findFirst("<div class='boxList boxListAZConditions'>");
      Element alpha_p=alpha_div.findFirst("<p class='paging-list-index clearAfter'>");
      Elements alpha_anchors=alpha_p.findEvery("<a href");
      /* iterate to all diseases alphabetical order */
      /* iterate through each alphabet */
      for(Element alpha_anchor:alpha_anchors)
      {
        UserAgent alpha_agent=new UserAgent();
        alpha_agent.visit(alpha_anchor.getAt("href"));
        Element alpha_ds_ul=alpha_agent.doc.findFirst("<ul class='column-list-2'>");
        Elements all_ds_list=alpha_ds_ul.findEvery("<a href");

        /* iterate through each diseases mentioned in 
        particulate aplphabet link*/
        for(Element ds_list:all_ds_list)
        {

          String ds_list_string=ds_list.getText().toLowerCase();



          if(ds_list_string.contains("("))
      {
        int start=ds_list_string.indexOf("(");
        int end=ds_list_string.lastIndexOf(")")+1;
        ds_list_string=ds_list_string.substring(0,start);
       if(ds_list_string.endsWith(" ")){int space=ds_list_string.lastIndexOf(" ");ds_list_string=ds_list_string.substring(0,space);}


      }
          trie.insertDisease(ds_list_string);
          if(ds_list_string.contains("pain"))
          {
            String temp=ds_list_string.replace("pain","ache");
            trie.insertDisease(temp);
          }
          if(ds_list_string.contains("ache"))
          {
            String temp=ds_list_string.replace("ache","pain");
            trie.insertDisease(temp);
          }
      }
      
    }
    System.out.println("trie done");
    UserAgent drugs=new UserAgent();	
    drugs.visit("https://www.drugs.com/drug_information.html");

      Element browseMedicine_div=drugs.doc.findFirst("<div class='search-browse'>");
      Element browseMedicine_div_span=browseMedicine_div.findFirst("<span class='alpha-list'>");
      Elements browseLists_alpha=browseMedicine_div_span.findEvery("<a href>");
      /* iterate through all the alphabets of drugs */
      for(Element browseList:browseLists_alpha)
      { 
      	//System.out.println(browseList.innerText());
      	/* scrapping data alphabatically */
      	if(browseList.innerText().equals("n")){
        UserAgent browsedrug=new UserAgent();
        browsedrug.visit(browseList.getAt("href"));
        Element paging_table=browsedrug.doc.findFirst("<td class='paging-list-index'>");
        Elements num_pages=paging_table.findEvery("<a href>");
        Element ul=browsedrug.doc.findFirst("<ul class='doc-type-list'>");
          Elements ull=ul.findEvery("<a href>");
          /* go through link of each drug mentioned */
          for(Element u:ull)
          {
          	if(!trie.setOfDrugs.contains(u.getText().toLowerCase())){
           trie.setOfDrugs.add(u.getText().toLowerCase());
           trie.drugname=u.getText();
           System.out.println(trie.drugname);
           UserAgent agent =new UserAgent();
           agent.visit(u.getAt("href"));
           Element e=null;
           try{
           	
          e=agent.doc.findFirst("<div id='content-box-nav-tabs'>");}
          catch(Exception eee){}
          finally{
          if (e==null) {
          	continue;
          }
          
          e=agent.doc.findFirst("<div id='content-box-nav-tabs'>");
          Elements es=e.findEvery("<li>");

          for(Element ee:es)
          {
          	/* if drug includes overview page which contains 
          	paragraph in which indiccations of the drug are
          	mentioned */
          	if(ee.innerText().equals("Overview"))
          	{
          		List<Element> llist=ee.getChildElements();
          		//System.out.println(llist);
          		for (int i = 0; i < llist.size(); i++) {
			//System.out.println(llist.get(i).getName());
			if(llist.get(i).getName().equals("a"))
			{
				Element anch=ee.findFirst("<a href>");
				agent.visit(anch.getAt("href"));
			}
		}
          		
          		Element div1=agent.doc.findFirst("<div class='contentAd contentAdM1 contentAdAlone'>").nextSiblingElement();
          		List<Element> pp=div1.getChildElements();
          			for (int i = 0; i < pp.size(); i++) {
					if(pp.get(i).getName().equals("b"))
					{
						while(true){
						div1=div1.nextSiblingElement();
						if (div1.getName().equals("h2")) {
							div1=div1.nextSiblingElement();
							//System.out.println(div1.getName());
							break;
							
						}
					}
				}
				}
      			 String check=div1.innerText();
       do
       {
          div1=div1.nextSiblingElement();
          check=check+" "+div1.innerText();
      }while(div1.nextSiblingElement().getName().equals("p"));
      check=check.toLowerCase();
      check=check.replaceAll("\\("," ");
      check=check.replaceAll("\\)"," ");
      //System.out.println(check);
       String something[]=check.split("\\s");
  int l=0;
  TrieNode parent=null;
  String temp="";
  String tempf="";
  String finalstring="";
  String prefix="";
  /* for every word in paragraph search in trie whether
  this word is a disease or not. if its is a disease
  add it to set of diseases. */
  while(l<something.length-2)
  {
    prefix=trie.searchDisease(something[l]);
  
    if(prefix==null )
    {
    	if(l<something.length-1){
      l++;}
    }
    else if(prefix!=null)
    {
      do{
      if(l<something.length-1){
      l++;}
      finalstring=prefix;
      if (prefix.endsWith("0")) {
        prefix=prefix.substring(0,prefix.length()-1);
        
      }
      temp=prefix+" "+something[l];
      prefix=trie.searchDisease(temp);
    }while(prefix!=null);
    if(!finalstring.endsWith("0"))
    {
    trie.setOfDiseases.add(finalstring);
    finalstring="";
     } }
   }
  
          	}
          	/* if the drug consists of the interaction link 
          	page then search for every drug drug interactions */
          	if(ee.innerText().equals("Interactions"))
          	{
          		Element anchr=ee.findFirst("<a href>");
          		agent.visit(anchr.getAt("href"));
          		Element ulelement=agent.doc.findFirst("<ul class='interactions interactions-label'>");
          		Elements listul=ulelement.findEvery("<li>");
          		for(Element lielement:listul)
          		{
          			Element pointer=lielement.findFirst("<a href>");
          			UserAgent interact=new UserAgent();
          			interact.visit(pointer.getAt("href"));
          			if(interact.doc.findEvery("<input type='checkbox'>").size() > 0){
          				
          			interact.doc.choose(Label.RIGHT, "Generic only");
          			interact.doc.submit("Go");}
          			Element drugin=interact.doc.findFirst("<ul class='interactions column-list-2'>");
          			Elements druginli=drugin.findEvery("<li>");
          			for(Element each:druginli)
          			{
          				trie.setOfDrugInteraction.add(each.innerText());
          			}
          			while(true)
          			{
          				if(!drugin.nextSiblingElement().getName().equals("div"))
          				{
          					break;
          				}
          				Element drugindiv=drugin.nextSiblingElement();
          				if(Pattern.matches("[A-Z]{1}",drugindiv.innerText()))
          				{
          				drugin=drugin.nextSiblingElement().nextSiblingElement();
          				if(drugin.getName().equals("ul"))
          				{
          					druginli=drugin.findEvery("<li>");
          			        for(Element each:druginli)
          			      {
          				trie.setOfDrugInteraction.add(each.innerText());
          			       }
          			       }
          				}
          				else
          				{
          					break;
          				}
          			}

          		}
          	}
          	/*if the drug has side effect link page then visit this 
          	link page and find for side effects it can cause */
          	if(ee.innerText().equals("Side Effects"))
          	{
          		boolean condition=false;
          		Element anchr=ee.findFirst("<a href>");
          		agent.visit(anchr.getAt("href"));
          		Element element=agent.doc.findFirst("<div class='contentBox'>");
          		Elements strongs=element.findEvery("<strong>");
          		if(strongs.size()>0){
          		for(Element strong:strongs)
          		{
          			/* if the list of side effect is given
          			then simply make condition true so as to iterate
          			through each list item */
          			if(strong.innerText().equalsIgnoreCase("rare")||strong.innerText().equalsIgnoreCase("Less common or rare:")||
    strong.innerText().equalsIgnoreCase("More common:")||strong.innerText().equalsIgnoreCase("Less common:")||
    strong.innerText().equalsIgnoreCase("Incidence not known:")||strong.innerText().equalsIgnoreCase("Symptoms of overdose:"))
          			{
          				condition=true;
          				break;
          			}
          		}
          		Elements ulelements=element.findEach("<ul>");
          		for(Element ulelement:ulelements)
   {
    if(!ulelement.hasAttribute("class"))
    {
    
    Elements llu=ulelement.findEach("<li>");
    for (Element ll:llu ) {
      if(ll.getChildElements().isEmpty()){
      	trie.setOfSideEffects.add(ll.getText());
      }
    }  
    }}}
    /* if list is not given search for side effects
    in the paragraph in the trie */
    else
    {
    	Elements pelements=element.findEvery("p");
    	String check="";
    	for(Element pelement:pelements)
    	{
    		check=check+pelement.innerText();
    	}
    	
    	check=check.toLowerCase();
      check=check.replaceAll("\\("," ");
      check=check.replaceAll("\\)"," ");
       String something[]=check.split("\\s");
  int l=0;
  TrieNode parent=null;
  String temp="";
  String tempf="";
  String finalstring="";
  String prefix="";
  
  while(l<something.length-2)
  {
    prefix=trie.searchDisease(something[l]);
  
    if(prefix==null )
    {
      l++;
    }
    else if(prefix!=null)
    {
      do{
      l++;
      finalstring=prefix;
      if (prefix.endsWith("0")) {
        prefix=prefix.substring(0,prefix.length()-1);
        
      }
      temp=prefix+" "+something[l];
      prefix=trie.searchDisease(temp);
    }while(prefix!=null);
    if(!finalstring.endsWith("0"))
    {
    trie.setOfSideEffects.add(finalstring);
    finalstring="";
     } }
   }


    }
     	}


          }
          /* add all the elements of the set to linnked list */
          LinkedList<String> llistofIndications=new LinkedList<String>(trie.setOfDiseases);
  LinkedList<String> llistofSideEffects=new LinkedList<String>(trie.setOfSideEffects);
  LinkedList<String> llistofDrugInteractions=new LinkedList<String>(trie.setOfDrugInteraction);
          DrugInfo drug_info=new DrugInfo(trie.drugname,llistofIndications,llistofSideEffects,llistofDrugInteractions);
          File f=new File("datatextn.txt");
          /* if the file exists then write in append mode */
          if(f.exists())
          {
          	oos1.writeObject(drug_info);
					
          	
          }
          /* if file does not exists then write in write mode */ 
          else
          {
          	oos.writeObject(drug_info);
			
          		
          }
          trie.druglinkedlist.add(drug_info);
          System.out.println("the indications are : "+ trie.setOfDiseases);
	      trie.setOfDiseases.clear();
          System.out.println("the set of drug interactions are       :"+trie.setOfDrugInteraction);
          	trie.setOfDrugInteraction.clear(); 
          	System.out.println("the set of side Effects are           :"+trie.setOfSideEffects);
          	trie.setOfSideEffects.clear();
         
      }
          }}//for ends
          /* now iterate through each page if pagination exists */ 
        for(Element page:num_pages)
        {
          UserAgent num_page=new UserAgent();
          num_page.visit(page.getAt("href"));
          
          Element ul1=num_page.doc.findFirst("<ul class='doc-type-list'>");
          Elements ull2=ul1.findEvery("<a href>");
          for(Element u1:ull2)
          {
          	if(!trie.setOfDrugs.contains(u1.getText().toLowerCase())){
           trie.setOfDrugs.add(u1.getText().toLowerCase());
           trie.drugname=u1.getText();
           System.out.println(trie.drugname);
           UserAgent agent =new UserAgent();
           agent.visit(u1.getAt("href"));
           Element e=null;
           try{
           	
          e=agent.doc.findFirst("<div id='content-box-nav-tabs'>");}
          catch(Exception eee){}
          finally{
          if (e==null) {
          	continue;
          }
          
          e=agent.doc.findFirst("<div id='content-box-nav-tabs'>");
          Elements es=e.findEvery("<li>");

          for(Element ee:es)
          {
          	if(ee.innerText().equals("Overview"))
          	{
          		List<Element> llist=ee.getChildElements();
          		for (int i = 0; i < llist.size(); i++) {
			if(llist.get(i).getName().equals("a"))
			{
				Element anch=ee.findFirst("<a href>");
				agent.visit(anch.getAt("href"));
			}
		}
          		
          		Element div1=agent.doc.findFirst("<div class='contentAd contentAdM1 contentAdAlone'>").nextSiblingElement();
          		List<Element> pp=div1.getChildElements();
          		//System.out.println(pp);
          			for (int i = 0; i < pp.size(); i++) {
					//System.out.println(pp.get(i).getName());
					if(pp.get(i).getName().equals("b"))
					{
						while(true){
						div1=div1.nextSiblingElement();
						if (div1.getName().equals("h2")) {
							div1=div1.nextSiblingElement();
							//System.out.println(div1.getName());
							break;
							
						}
					}
				}
				}
      			 String check=div1.innerText();
       do
       {
          div1=div1.nextSiblingElement();
          //System.out.println(div1.nextSiblingElement().getName());
          check=check+" "+div1.innerText();
      }while(div1.nextSiblingElement().getName().equals("p"));
      check=check.toLowerCase();
      check=check.replaceAll("\\("," ");
      check=check.replaceAll("\\)"," ");
      //System.out.println(check);
       String something[]=check.split("\\s");
  int l=0;
  TrieNode parent=null;
  String temp="";
  String tempf="";
  String finalstring="";
  String prefix="";
  while(l<something.length-2)
  {
    prefix=trie.searchDisease(something[l]);
  
    if(prefix==null )
    {
    	if(l<something.length-1){
      l++;}
    }
    else if(prefix!=null)
    {
      do{
      	if(l<something.length-1){
      l++;}
      finalstring=prefix;
      if (prefix.endsWith("0")) {
        prefix=prefix.substring(0,prefix.length()-1);
        
      }
      temp=prefix+" "+something[l];
      prefix=trie.searchDisease(temp);
    }while(prefix!=null);
    if(!finalstring.endsWith("0"))
    {
    trie.setOfDiseases.add(finalstring);
    finalstring="";
     } }
   }

          	}
          	if(ee.innerText().equals("Interactions"))
          	{
          		Element anchr=ee.findFirst("<a href>");
          		agent.visit(anchr.getAt("href"));
          		Element ulelement=agent.doc.findFirst("<ul class='interactions interactions-label'>");
          		Elements listul=ulelement.findEvery("<li>");
          		for(Element lielement:listul)
          		{
          			Element pointer=lielement.findFirst("<a href>");
          			UserAgent interact=new UserAgent();
          			interact.visit(pointer.getAt("href"));
          			if(interact.doc.findEvery("<input type='checkbox'>").size() > 0)
          			{
          			interact.doc.choose(Label.RIGHT, "Generic only");
          			interact.doc.submit("Go");}
          			Element drugin=interact.doc.findFirst("<ul class='interactions column-list-2'>");
          			Elements druginli=drugin.findEvery("<li>");
          			for(Element each:druginli)
          			{
          				trie.setOfDrugInteraction.add(each.innerText());
          			}
          			while(true)
          			{
          				if(!drugin.nextSiblingElement().getName().equals("div"))
          				{
          					break;
          				}
          				Element drugindiv=drugin.nextSiblingElement();
          				if(Pattern.matches("[A-Z]{1}",drugindiv.innerText()))
          				{
          				drugin=drugin.nextSiblingElement().nextSiblingElement();
          				if(drugin.getName().equals("ul"))
          				{
          					druginli=drugin.findEvery("<li>");
          			        for(Element each:druginli)
          			      {
          				trie.setOfDrugInteraction.add(each.innerText());
          			       }
          			       }
          				}
          				else
          				{
          					break;
          				}
          			}

          		}
          	}
          	if(ee.innerText().equals("Side Effects"))
          	{
          		boolean condition=false;
          		Element anchr=ee.findFirst("<a href>");
          		agent.visit(anchr.getAt("href"));
          		Element element=agent.doc.findFirst("<div class='contentBox'>");
          		Elements strongs=element.findEvery("<strong>");
          		if(strongs.size()>0){
          		for(Element strong:strongs)
          		{
          			if(strong.innerText().equalsIgnoreCase("rare")||strong.innerText().equalsIgnoreCase("Less common or rare:")||
    strong.innerText().equalsIgnoreCase("More common:")||strong.innerText().equalsIgnoreCase("Less common:")||
    strong.innerText().equalsIgnoreCase("Incidence not known:")||strong.innerText().equalsIgnoreCase("Symptoms of overdose:"))
          			{
          				condition=true;
          				break;
          			}
          		}
          		Elements ulelements=element.findEach("<ul>");
          		for(Element ulelement:ulelements)
   {
    if(!ulelement.hasAttribute("class"))
    {
    
    Elements llu=ulelement.findEach("<li>");
    for (Element ll:llu ) {
      if(ll.getChildElements().isEmpty()){
      	trie.setOfSideEffects.add(ll.getText());
      }
    }  
    }}} 
    else{

    	Elements pelements=element.findEvery("p");
    	String check="";
    	for(Element pelement:pelements)
    	{
    		check=check+pelement.innerText();
    	}
    	
    	check=check.toLowerCase();
      check=check.replaceAll("\\("," ");
      check=check.replaceAll("\\)"," ");
       String something[]=check.split("\\s");
  int l=0;
  TrieNode parent=null;
  String temp="";
  String tempf="";
  String finalstring="";
  String prefix="";
  
  while(l<something.length-2)
  {
    prefix=trie.searchDisease(something[l]);
  
    if(prefix==null )
    {
      l++;
    }
    else if(prefix!=null)
    {
      do{
      l++;
      finalstring=prefix;
      if (prefix.endsWith("0")) {
        prefix=prefix.substring(0,prefix.length()-1);
        
      }
      temp=prefix+" "+something[l];
      prefix=trie.searchDisease(temp);
    }while(prefix!=null);
    if(!finalstring.endsWith("0"))
    {
    trie.setOfSideEffects.add(finalstring);
    finalstring="";
     } }
   }

    }	}
    Thread.sleep(120);
          }

           LinkedList<String> llistofIndications=new LinkedList<String>(trie.setOfDiseases);
  LinkedList<String> llistofSideEffects=new LinkedList<String>(trie.setOfSideEffects);
  LinkedList<String> llistofDrugInteractions=new LinkedList<String>(trie.setOfDrugInteraction);
          DrugInfo drug_info=new DrugInfo(trie.drugname,llistofIndications,llistofSideEffects,llistofDrugInteractions);
          trie.druglinkedlist.add(drug_info);
          File f=new File("datatextn.txt");
          if(f.exists())
          {
          	oos1.writeObject(drug_info);
					
          	
          }
          else
          {
          	oos.writeObject(drug_info);
			
          		
          }

  System.out.println("the indications are : "+ trie.setOfDiseases);
  trie.setOfDiseases.clear();
          System.out.println("the set of drug interactions are       :"+trie.setOfDrugInteraction);
          	trie.setOfDrugInteraction.clear();  
          	System.out.println("the set of side Effects are            :"+trie.setOfSideEffects);
          	trie.setOfSideEffects.clear();
         
      }
          }}
    }
      }}
      PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
      Iterator<DrugInfo> itr=trie.druglinkedlist.iterator();
      while(itr.hasNext())
      {
      	writer.println(itr.next());

      }
      writer.close();
      oos.close();
      oos1.close();
      
}
  catch(JauntException e){ 
      System.out.println(e);
    }
    catch(Exception ee)
    {
    	System.out.println(ee);
    }
 }
}