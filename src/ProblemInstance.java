import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Collections;
public class ProblemInstance {
	
	public static final int BAD_CONSTR = 1; //schedule breaks some hard constraint
	public static final int PART_CONSTR = 2; //schedule is incomplete (some classes unassigned) but otherwise breaks no constraints
	public static final int GOOD_CONSTR = 3; //schedule is complete, breaks no constraints
	private static int evalScore = Integer.MAX_VALUE; 
	public static int wmin;
	public static int pref;
	public static int pair;
	public static int secdeff;
	public static int pen_coursemin;
	public static int pen_labmin;
	public static int pen_notpaired;
	public static int pen_section;
	
	private ArrayList<CoursePair> schedule;
	private Parser p;
	
	public ProblemInstance(Parser pa) {
		this.p = pa;
		this.schedule = p.getPartials();
		for(String c : p.getCourseIdentifiers()) {
			boolean exists = false;
			for(CoursePair cp : this.schedule) {
				if(c.equals(cp.getCourse())) {
					exists = true;
				}
			}
			if(exists == false) {
				CoursePair cp = new CoursePair(c,CoursePair.EMPTY);
				this.schedule.add(cp);
			}
		}
		for(String c : p.getLabIdentifiers()) {
			boolean exists = false;
			for(CoursePair cp : this.schedule) {
				if(c.equals(cp.getCourse())) {
					exists = true;
				}
			}
			if(exists == false) {
				CoursePair cp = new CoursePair(c,CoursePair.EMPTY);
				this.schedule.add(cp);
			}
		}
	} 
	

	
	public  ProblemInstance findSchedule(int index) {
		ProblemInstance found = null;
		CoursePair c = this.schedule.get(index);
//		if(this.isCourse(c.getCourse())) {
//			
//		} else {
//			
//		}
		if(this.isCourse(c.getCourse())) {
			for(Slot s : this.p.getCoursesSlots()) {
				c.schedule(s.getId());
				s.setMax(s.getMax()-1);
				s.setMin(s.getMin()-1);
				if(this.Constr()==BAD_CONSTR) {
					s.setMax(s.getMax()+1);
					s.setMin(s.getMin()+1);
					continue;
				}
				if(!this.Eval()) {
					s.setMax(s.getMax()+1);
					s.setMin(s.getMin()+1);
					continue;
				}
				if(index<this.schedule.size()-1) {
					ProblemInstance pr = new ProblemInstance(this.p);
					pr.setSchedule(this.schedule);
					ProblemInstance result = pr.findSchedule(index+1);
					if(result!=null) {
						found = result;
					} 
				}else {
					ProblemInstance pr = new ProblemInstance(this.p);
					pr.setSchedule(this.schedule);
					return pr;
				}
			}
		}else {
			for(Slot s : this.p.getLabSlots()) {
				c.schedule(s.getId());
				s.setMax(s.getMax()-1);
				s.setMin(s.getMin()-1);
				if(this.Constr()==BAD_CONSTR) {
					s.setMax(s.getMax()+1);
					s.setMin(s.getMin()+1);
					continue;
				}
				if(!this.Eval()) {
					s.setMax(s.getMax()+1);
					s.setMin(s.getMin()+1);
					continue;
				}
				if(index<this.schedule.size()-1) {
					ProblemInstance pr = new ProblemInstance(this.p);
					pr.setSchedule(this.schedule);
					ProblemInstance result = pr.findSchedule(index+1);
					if(result!=null) {
						found = result;
					} 
				}else {
					ProblemInstance pr = new ProblemInstance(this.p);
					pr.setSchedule(this.schedule);
					return pr;
				}
			}
		}
		
		return found;

	}
	
	public static int getEvalScore() {
		return evalScore;
	}



	public static void setEvalScore(int evalScore) {
		ProblemInstance.evalScore = evalScore;
	}



	public ArrayList<CoursePair> getSchedule() {
		return schedule;
	}



	public void setSchedule(ArrayList<CoursePair> schedule) {
		this.schedule = schedule;
	}



	public int Constr() {
		//evaluate constraints, return appropriate typecode. Problems: need to be able to compute efficiently for large problem sets. How to compare timeslots easily for Course/Tutorial conflicts?
		boolean containsEmpty = false;
		//bad if a slot has too many courses
		for(Slot s : this.p.getCoursesSlots()) {
			if(s.getMax() <= 0) {
				System.out.println("Course slot overscheduled");
				return BAD_CONSTR;
			}
		}
		// same as above, for labs
		for(Slot s : this.p.getLabSlots()) {
			if(s.getMax() <= 0) {
				System.out.println("Lab slot overscheduled");
				return BAD_CONSTR;
			}
		}
		//check each course against each other course for incompatibilities
		//optimize: only compare against nodes that it hasn't been compared against (ie ones at higher indices)
		for(CoursePair c : p.getNotCompatible()) {
			String time1 = "not";
			String time2 = "equal";
			for(CoursePair cp : this.schedule) {
				if(cp.getCourse().equals(c.getCourse())) {
					time1 = cp.getTime();
				}else if(cp.getCourse().equals(c.getTime())) {
					time2 = cp.getTime();
				}
			}
			if(!time1.equals(CoursePair.EMPTY) && time1.equals(time2)) {
				System.out.println("Incompatible class pair");
				return BAD_CONSTR;
			}
		}
		//Check no courses are in unwanted assignments
		for(CoursePair c : p.getUnWanted()) {
			for(CoursePair cp : this.schedule) {
				if(cp.getCourse().equals(c.getCourse())&&(cp.getTime().equals(c.getTime()))) {
					System.out.println("Unwanted class pair");
					return BAD_CONSTR;
				}
			}
		}
		//Check evening courses are in the evening
		for(CoursePair c : this.schedule) {
			if(c.getCourse().contains("LEC 9") && Integer.getInteger(c.getTime().substring(2, 3)) <= 18) {
				System.out.println("Evening class not scheduled in evening");
				return BAD_CONSTR;
			}
		}
		//Check 500-level courses are in different slots
		ArrayList<String> times = new ArrayList<String>();
		for(CoursePair c : this.schedule) {
			if (c.getCourse().matches(".*5\\d\\d.*")) {
				if (times.contains(c.getTime())) {
					System.out.println("500-level class not scheduled in unique timeslot");
					return BAD_CONSTR;
				} else if (!c.getTime().equals(CoursePair.EMPTY)) {
					times.add(c.getTime());
				}
			}
		}
		//check nothing scheduled 11:00-12:30 TTh
		for(CoursePair c : this.schedule) {
			if(c.getTime().contains("TU") && Integer.parseInt(c.getTime().substring(2)) == 11) {
				System.out.println("Class scheduled at lunchtime on Tuesday");
				return BAD_CONSTR;
			}
		}
		//Check 813 and 913 are in the right time.
		for(CoursePair c : this.schedule) {
			if(c.getCourse().contains("CPSC 813")) {
				if(!c.getTime().equals("TU18")){
					System.out.println("813 scheduled at wrong time");
					return BAD_CONSTR;
				}
				for(CoursePair cp : this.schedule) {
					if(cp.getCourse().contains("CPSC 313") && cp.getTime().equals(c.getTime())) {
						System.out.println("813 scheduling incompatible");
						return BAD_CONSTR;
					}
				}
			}
			if(c.getCourse().contains("CPSC 913")) {
				if(!c.getTime().equals("TU18")){
					System.out.println("913 scheduled at wrong time");
					return BAD_CONSTR;
				}
				for(CoursePair cp : this.schedule) {
					if(cp.getCourse().contains("CPSC 413") && cp.getTime().equals(c.getTime())) {
						System.out.println("913 scheduling incompatible");
						return BAD_CONSTR;
					}
				}
			}
		}
		
		if(containsEmpty) {
			return PART_CONSTR;
		}
		else {
			return GOOD_CONSTR;
		}
	} 
	
	public boolean Eval() {
		int e = (this.Evalwmin() * wmin) + (pref*this.Evalpref()) + (pair*this.Evalpair() ) + (secdeff*this.Evalsecdeff());
		if(e<=evalScore) {
			evalScore =e;
			return true;
		}
		else {
			return false;
		}
	}

	private int Evalpair() {		
		int sum =0;
		for(CoursePair c : p.getPairs()) {
			String time1 = "not";
			String time2 = "equal";
			for(CoursePair cp : this.schedule) {
				if(cp.getCourse().equals(c.getCourse())) {
					time1 = cp.getTime();
				}else if(cp.getCourse().equals(c.getTime())) {
					time2 = cp.getTime();
				}
			}
			if(!time1.equals(time2)) {
				sum+= pen_notpaired;
			}
		}
		
		return sum;
	}

	private int Evalsecdeff() {
		int sum =0;
		for(CoursePair c : this.schedule) {
			for(CoursePair cp : this.schedule) {
				if(this.compareCourses(c.getCourse(), cp.getCourse())==3) {
					if(!c.getTime().equals(cp.getTime())){
						sum += pen_section;
					}
				}
			}

		}
		
		return sum;
	}

	private int Evalwmin() {
		int sum =0;
		for(Slot s : this.p.getCoursesSlots()) {
			if(s.getMin()!=0) {
				sum += pen_coursemin;
			}
		}
		for(Slot s : this.p.getLabSlots()) {
			if(s.getMin()!=0) {
				sum += pen_labmin;
			}
		}
		return sum;
	}

	private int Evalpref() {
		int sum = 0;
		Iterator hmIterator = this.p.getPreferences().entrySet().iterator();
		while (hmIterator.hasNext()) { 
            Map.Entry mapElement = (Map.Entry)hmIterator.next(); 
            String s = ((String)mapElement.getValue()); 
            CoursePair c = (CoursePair) mapElement.getKey(); 
            boolean fullfilled = false;
			for(CoursePair cp : this.schedule) {
				if(cp.equals(c)) {
						fullfilled = true;
				}
			}
            if(!fullfilled) {
            	sum += Integer.parseInt(s);
            }
            
        } 
		return sum;
	}
	
	public boolean isCourse(String s) {
		boolean result =true;
		String[] temp = s.split("LEC");
		if(temp.length == 1) {
			result = false;
		}
		else if(temp[1].length() > 2) {
			result = false;
		}
		return result;
	}
	
	public String getCoursename(String s) {
		String[] temp = s.split("LEC");
		return temp[0];
	}
	
	public String getCourseSection(String s) {
		String[] temp = s.split("LEC");
		return temp[1];
	}
	
	public String getLabName(String s) {
		String[] temp = s.split("LEC");
		return temp[0];
	}
	
	public String getLabSection(String s) {
		String[] temp = s.split("LEC");
		if(temp.length == 1) {
			String[] temp1 = s.split("TUT");
			String[] temp2 = s.split("LAB");
			if(temp1.length >1) {
				return temp1[1];
			}else if(temp2.length>1) {
				return temp2[1];
			}else {
				System.out.println("Failed to split" + s);
			}
		}
		else {
			String[] temp1 = temp[1].split("TUT");
			String[] temp2 = temp[1].split("LAB");
			if(temp1.length >1) {
				return temp1[1];
			}else if(temp2.length>1) {
				return temp2[1];
			}else {
				System.out.println("Failed to split" + s);
			}
			
		}
		return temp[1];
	}
	/*
	 * a return value of 0 implies 2 courses that are not the same i.e.  CPSC 413 and CPSC 433
	 * a return value of 1  implies a course and a Lab that dont belong to each other
	 * a value of 2 implies a course and a lab that belong to each other
	 * a value of 3 implies 2 courses that are equal 
	 * a value of 4 implies 2 labs that dont belong
	 * a value of 5 implies to labs that belong */
	public int compareCourses(String c1, String c2) {
		if(this.isCourse(c1)&&this.isCourse(c2)) {
			String c1name = this.getCoursename(c1);
			String c2name = this.getCoursename(c2);
			if(c1name.equals(c2name)) {
				return 3;
			}else {
				return 0;
			}
		}else if(this.isCourse(c1)&&!this.isCourse(c2)) {
			String c1name = this.getCoursename(c1);
			String c2name = this.getLabName(c2);
			if(c1name.equals(c2name)) {
				return 2;
			}else {
				return 1;
			}
		}else if(!this.isCourse(c1)&&this.isCourse(c2)) {
			String c1name = this.getCoursename(c1);
			String c2name = this.getLabName(c2);
			if(c1name.equals(c2name)) {
				return 2;
			}else {
				return 1;
			}
		}else if(!this.isCourse(c1)&&!this.isCourse(c2)) {
			String c1name = this.getCoursename(c1);
			String c2name = this.getLabName(c2);
			if(c1name.equals(c2name)) {
				return 5;
			}else {
				return 4;
			}
	}else {
			System.out.println("failed");
			return -1;
	}
}



	public void sort() {
		Collections.sort(this.schedule);
		
	}
}

