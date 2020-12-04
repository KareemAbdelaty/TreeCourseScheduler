
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
	
	
}
