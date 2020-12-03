import java.util.*; 
public class Slot{
	private String day;
	private String time;
	public String id;
	boolean courseSlot;
	boolean evening;
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
		if(Integer.parseInt(ti) >= 18){
			this.evening = true;
		}else{
			this.evening = false;
		}
		this.id ="";
		this.id += this.day;
		this.id += ti;
		
		
	}
}