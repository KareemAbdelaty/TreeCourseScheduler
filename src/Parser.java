import java.io.*;
import java.util.*; 
import java.nio.charset.StandardCharsets; 
import java.nio.file.*; 
class Parser{
	/**
		All the member fields are initally null read and are intialised after a succesful  parsing of the input
		the parse assumes the keywords occur in a static sequence
	**/
	public String instanceName;
	public ArrayList<Slot> coursesSlots; //
	public ArrayList<Slot> labSlots;
	public ArrayList<String> courseIdentifiers;
	public ArrayList<String> LabIdentifiers;	
	public ArrayList<CoursePair> notCompatible;
	public ArrayList<CoursePair> unWanted; 
	public HashMap<CoursePair,String> preferences;
	public ArrayList<CoursePair> pairs;
	public ArrayList<CoursePair> partials;
	
	public Parser(){
		this.coursesSlots = new ArrayList<Slot>(); //
		this.labSlots = new ArrayList<Slot>();
		this.courseIdentifiers = new ArrayList<String>();
		this.LabIdentifiers = new ArrayList<String>();	
		this.notCompatible = new ArrayList<CoursePair>();
		this.unWanted = new ArrayList<CoursePair>() ; 
		this.preferences = new HashMap<CoursePair,String>();
		this.pairs = new ArrayList<CoursePair>();
		this.partials = new ArrayList<CoursePair>();
	}
	
	
	public int parse(String filePath){
    List<String> lines = Collections.emptyList(); 
    try
    { 
      lines = 
       Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8); 
    }   
    catch (IOException e) 
    { 
		System.out.println("Unable to read file");
		return -1;
    }
	int mode =0; //specfies which list are we populating
	Iterator<String> itr = lines.iterator(); 
    while (itr.hasNext()) {
		String line = itr.next(); 
		if(line.trim().equals("")){
			continue;
		}
		if(line.contains("Name:")){
			mode = 1;
			continue;
		}else if(line.contains("Course slots:")){
			mode = 2;
			continue;
		}else if(line.contains("Lab slots:")){
			mode = 3;
			continue;
		}else if(line.contains("Courses:")){
			mode = 4;
			continue;
		}else if(line.contains("Labs:")){
			mode = 5;
			continue;
		}else if(line.contains("Not compatible:")){
			mode = 6;
			continue;
		}else if(line.contains("Unwanted:")){
			mode = 7;
			continue;
		}else if(line.contains("Preferences:")){
			mode = 8;
			continue;
		}else if(line.contains("Pair:")){
			mode = 9;
			continue;
		}else if(line.contains("Partial assignments:")){
			mode = 10;
			continue;
		}
		if(mode ==1){
			this.instanceName = line;
		}else if(mode ==2){
			String[] temp =  line.split(",");
			Slot c = new Slot(temp[0].trim(),temp[1].trim(),Integer.parseInt(temp[2].trim()),Integer.parseInt(temp[3].trim()),true);
			this.coursesSlots.add(c);
		}else if(mode ==3){
			String[] temp =  line.split(",");
			Slot l = new Slot(temp[0].trim(),temp[1].trim(),Integer.parseInt(temp[2].trim()),Integer.parseInt(temp[3].trim()),false);
			this.labSlots.add(l);
		}else if(mode ==4){
			this.courseIdentifiers.add(line);
		}else if(mode ==5){
			this.LabIdentifiers.add(line);
		}else if(mode ==6){
			String[] temp =  line.split(",");
			CoursePair u = new CoursePair(temp[0],temp[1]);
			this.notCompatible.add(u);
		}else if(mode ==7){
			String[] temp =  line.split(",");
			Slot s = new Slot(temp[1],temp[2]);
			CoursePair u = new CoursePair(temp[0],s.id);
			this.unWanted.add(u);
		}else if(mode ==8){
			String[] temp =  line.split(",");
			Slot s = new Slot(temp[1],temp[2]);
			CoursePair u = new CoursePair(temp[0],s.id);
			this.preferences.put(u,temp[3]);
		}else if(mode ==9){
			String[] temp =  line.split(",");
			CoursePair u = new CoursePair(temp[0],temp[1]);
			this.pairs.add(u);
		}else if(mode == 10){
			String[] temp =  line.split(",");
			Slot s = new Slot(temp[1],temp[2]);
			CoursePair u = new CoursePair(temp[0],s.id);
			this.unWanted.add(u);
		}
		
	}
	System.out.println(coursesSlots);
	System.out.println(labSlots);
	System.out.println(courseIdentifiers);
	System.out.println(LabIdentifiers);
	System.out.println(notCompatible);
	System.out.println(unWanted);
	System.out.println(preferences);
	System.out.println(pairs);
	System.out.println(partials);
	return 0;

	}
	public static void main(String args[]){
		Parser p =  new Parser();
		p.parse(args[0]);
		
	}
	
}