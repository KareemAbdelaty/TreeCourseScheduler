public class Slot{
	private String day;
	private String time;
	private String id;
	private boolean courseSlot;
	private boolean evening;
	private int max;
	private int min;
	
    public Slot(String d,String t ){
		this.day =d;
		this.time = t;
		this.id ="";
		this.id += this.day;
		String ti = this.time.split(":")[0];
		this.id += ti;
		
	}
	public Slot(String d, String t, int ma, int mi,boolean cs){
		this.day = d;
		this.time = t;
		this.max = ma;
		this.min = mi;
		this.courseSlot = cs;
		String ti = this.time.split(":")[0];
		if(Integer.parseInt(ti.trim()) >= 18){
			this.evening = true;
		}else{
			this.evening = false;
		}
		this.id ="";
		this.id += this.day;
		this.id += ti;
		
		
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isCourseSlot() {
		return courseSlot;
	}
	public void setCourseSlot(boolean courseSlot) {
		this.courseSlot = courseSlot;
	}
	public boolean isEvening() {
		return evening;
	}
	public void setEvening(boolean evening) {
		this.evening = evening;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
}