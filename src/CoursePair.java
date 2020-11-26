
public class CoursePair {
	
	public final String EMPTY = "EMPTY";
	
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
	
}
