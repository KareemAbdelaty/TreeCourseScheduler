import java.util.ArrayList;

public class Driver {

	public static void main(String[] args) {
		// Parse
		if(args.length < 9) {
			System.out.println("Too few arguments");
			System.out.println("Usage is: ");
			System.out.println("filepath wmin wpref wpair wsecdeff ");
			System.out.println("pen_coursemin pen_labmin pen_notpaired pen_section ");
		}
		Parser p =  new Parser();
		int i = p.parse(args[0]);
		if(i!=0){
			return;
		}
		ProblemInstance pr = new ProblemInstance(p);
		pr.wmin = Integer.parseInt(args[1]);
		pr.pref = Integer.parseInt(args[2]);
		pr.pair = Integer.parseInt(args[3]);
		pr.secdeff = Integer.parseInt(args[4]);
		pr.pen_coursemin = Integer.parseInt(args[5]);
		pr.pen_labmin = Integer.parseInt(args[6]);
		pr.pen_notpaired = Integer.parseInt(args[7]);
		pr.pen_section = Integer.parseInt(args[8]);
		pr.sort();
		int index =0;		
		ArrayList<CoursePair> temp = pr.getSchedule();
		for(int x= 0 ;x< temp.size();x++) {
			if(temp.get(x).getTime() == CoursePair.EMPTY) {
				index =x;
				break;
			}
		}
		ProblemInstance result = pr.findSchedule(index);
		if(result == null) {
			System.out.println("No possible solution with given parameter");
		}else {
			System.out.println("Eval score is " + result.getEvalScore());
			for(CoursePair c : result.getSchedule()) {
				System.out.println(c.getCourse() + " : " + c.getTime());
			}
		}
		

		
	}

}
