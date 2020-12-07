
public class CoursePair implements Comparable<CoursePair> {
	
	public final static String EMPTY = "EMPTY";
	
	private String course;
	private String timeslot;
	
	public CoursePair (String course) {
		this.course = course;
	}
	
	public CoursePair(String course, String timeslot) {
		this.course = course;
		this.timeslot = timeslot;
	}
	
	public String getCourse() {
		return course;
	}
	
	public String getTime() {
		return timeslot;
	}
	
	public void schedule(String newTime) {
		this.timeslot = newTime;
	}
	
	//just for human-readability. Equivalent to cp.schedule(EMPTY);
	public void unschedule() {
		this.timeslot = EMPTY;
	}
	
	public boolean equals(Object o) {
		if (o == this) { 
            return true; 
        } 
        if (!(o instanceof CoursePair)) { 
            return false; 
        } 
        CoursePair c = (CoursePair) o;
		return course.equals(c.getCourse())&&timeslot.equals(c.getTime());
	}
	@Override
	  public int compareTo(CoursePair cp){
			if(cp.getTime().equals(CoursePair.EMPTY)&&this.getTime().equals(EMPTY)){
				return 0;
			}else if((!cp.getTime().equals(CoursePair.EMPTY))&&this.getTime().equals(EMPTY)) {
				return 1;
			}else {
				return -1;
			}
			
	     
	  }
	//For returning printable time
	public String getTime2() {
		String time2 = "" + this.getTime().substring(0,2) + ", ";
		if (Integer.parseInt(timeslot.substring(2,3)) != 1) {
			time2 = time2 + " ";
		}
		
		time2 = time2 + this.getTime().substring(2) + ":";
		if ((this.getTime().contains("T"))) {
			//System.out.println("found tuesday");
			if ((this.getTime().substring(2).equals("9")) ||
					(this.getTime().substring(2).equals("12")) ||
					(this.getTime().substring(2).equals("15")) ||
					(this.getTime().substring(2).equals("18"))) {
				time2 = time2 + "30";
			} else {
				time2 = time2 + "00";
			}
		} else {
			time2 = time2 + "00";
		}
		
		return time2;
	}
	
	
}
