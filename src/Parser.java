import java.io.*;
import java.util.*; 
import java.nio.charset.StandardCharsets; 
import java.nio.file.*; 
class Parser{
	/**
		All the member fields are initally null read and are intialised after a succesful  parsing of the input
		the parse assumes the keywords occur in a static sequence
	**/
	private String instanceName;
	private ArrayList<Slot> coursesSlots; //
	private ArrayList<Slot> labSlots;
	private ArrayList<String> courseIdentifiers;
	private ArrayList<String> LabIdentifiers;	
	private ArrayList<CoursePair> notCompatible;
	private ArrayList<CoursePair> unWanted; 
	private HashMap<CoursePair,String> preferences;
	private ArrayList<CoursePair> pairs;
	private ArrayList<CoursePair> partials;
	private HashMap<String,Slot> cslots; //ids to slots for courses
	private HashMap<String,Slot> lslots; //ids to slots for labs
	
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
		this.cslots = new HashMap<String,Slot>();
		this.lslots = new HashMap<String,Slot>();
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
			this.cslots.put(c.getId(),c);
		}else if(mode ==3){
			String[] temp =  line.split(",");
			Slot l = new Slot(temp[0].trim(),temp[1].trim(),Integer.parseInt(temp[2].trim()),Integer.parseInt(temp[3].trim()),false);
			this.labSlots.add(l);
			this.lslots.put(l.getId(),l);
		}else if(mode ==4){
			this.courseIdentifiers.add(line.trim());
		}else if(mode ==5){
			this.LabIdentifiers.add(line.trim());
		}else if(mode ==6){
			String[] temp =  line.split(",");
			CoursePair u = new CoursePair(temp[0].trim(),temp[1].trim());
			this.notCompatible.add(u);
		}else if(mode ==7){
			String[] temp =  line.split(",");
			Slot s = new Slot(temp[1].trim(),temp[2].trim());
			CoursePair u = new CoursePair(temp[0].trim(),s.getId());
			this.unWanted.add(u);
		}else if(mode ==8){
			String[] temp =  line.split(",");
			Slot s = new Slot(temp[1].trim(),temp[2].trim());
			CoursePair u = new CoursePair(temp[0].trim(),s.getId());
			this.preferences.put(u,temp[3].trim());
		}else if(mode ==9){
			String[] temp =  line.split(",");
			CoursePair u = new CoursePair(temp[0].trim(),temp[1].trim());
			this.pairs.add(u);
		}else if(mode == 10){
			String[] temp =  line.split(",");
			Slot s = new Slot(temp[1].trim(),temp[2].trim());
			CoursePair u = new CoursePair(temp[0].trim(),s.getId());
			this.partials.add(u);
		}
		
	}

	return 0;

	}


	public String getInstanceName() {
		return instanceName;
	}


	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}


	public ArrayList<Slot> getCoursesSlots() {
		return coursesSlots;
	}


	public void setCoursesSlots(ArrayList<Slot> coursesSlots) {
		this.coursesSlots = coursesSlots;
	}


	public ArrayList<Slot> getLabSlots() {
		return labSlots;
	}


	public void setLabSlots(ArrayList<Slot> labSlots) {
		this.labSlots = labSlots;
	}


	public ArrayList<String> getCourseIdentifiers() {
		return courseIdentifiers;
	}


	public void setCourseIdentifiers(ArrayList<String> courseIdentifiers) {
		this.courseIdentifiers = courseIdentifiers;
	}


	public ArrayList<String> getLabIdentifiers() {
		return LabIdentifiers;
	}


	public void setLabIdentifiers(ArrayList<String> labIdentifiers) {
		LabIdentifiers = labIdentifiers;
	}


	public ArrayList<CoursePair> getNotCompatible() {
		return notCompatible;
	}


	public void setNotCompatible(ArrayList<CoursePair> notCompatible) {
		this.notCompatible = notCompatible;
	}


	public ArrayList<CoursePair> getUnWanted() {
		return unWanted;
	}


	public void setUnWanted(ArrayList<CoursePair> unWanted) {
		this.unWanted = unWanted;
	}


	public HashMap<CoursePair, String> getPreferences() {
		return preferences;
	}


	public void setPreferences(HashMap<CoursePair, String> preferences) {
		this.preferences = preferences;
	}


	public ArrayList<CoursePair> getPairs() {
		return pairs;
	}


	public void setPairs(ArrayList<CoursePair> pairs) {
		this.pairs = pairs;
	}


	public ArrayList<CoursePair> getPartials() {
		return partials;
	}


	public void setPartials(ArrayList<CoursePair> partials) {
		this.partials = partials;
	}


	public HashMap<String, Slot> getCslots() {
		return cslots;
	}


	public void setCslots(HashMap<String, Slot> cslots) {
		this.cslots = cslots;
	}


	public HashMap<String, Slot> getLslots() {
		return lslots;
	}


	public void setLslots(HashMap<String, Slot> lslots) {
		this.lslots = lslots;
	}



	
}