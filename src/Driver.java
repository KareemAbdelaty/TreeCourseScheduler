import java.util.ArrayList;
import java.util.Collections;

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
		System.out.println("Proccessing file");
		int i = p.parse(args[0]);
		if(i!=0){
			System.out.println("Failed to Proccess file");
			return;
		}
		System.out.println("file Proccesed");
		ProblemInstance pr = new ProblemInstance(p);
		ProblemInstance.wmin = Integer.parseInt(args[1]);
		ProblemInstance.pref = Integer.parseInt(args[2]);
		ProblemInstance.pair = Integer.parseInt(args[3]);
		ProblemInstance.secdeff = Integer.parseInt(args[4]);
		ProblemInstance.pen_coursemin = Integer.parseInt(args[5]);
		ProblemInstance.pen_labmin = Integer.parseInt(args[6]);
		ProblemInstance.pen_notpaired = Integer.parseInt(args[7]);
		ProblemInstance.pen_section = Integer.parseInt(args[8]);
		pr.sort();
		ArrayList<String> tempstrings = new ArrayList<String>();
		int index =0;		
		ArrayList<CoursePair> temp = pr.getSchedule();
		for(int x= 0 ;x< temp.size();x++) {
			if(temp.get(x).getTime() == CoursePair.EMPTY) {
				index =x;
				break;
			}
		}
		System.out.println("Starting Search Instance");
		ProblemInstance result = pr.findSchedule(index);
		if(result == null) {
			System.out.println("No possible solution with given parameter");
		}else {
			System.out.println("Eval-value: " + result.getEvalScore());
			for(CoursePair c : result.getSchedule()) {
				tempstrings.add(String.format("%-25s: %s\n", c.getCourse(), c.getTime2()));
			}
			Collections.sort(tempstrings);
			for (String s : tempstrings) {
				System.out.print(s);
			}
		}


		
	}

}
