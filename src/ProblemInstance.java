import java.util.ArrayList;

public class ProblemInstance {
	
	public static final int BAD_CONSTR = 1; //schedule breaks some hard constraint
	public static final int PART_CONSTR = 2; //schedule is incomplete (some classes unassigned) but otherwise breaks no constraints
	public static final int GOOD_CONSTR = 3; //schedule is complete, breaks no constraints

	private ArrayList<CoursePair> schedule;
	private ArrayList<ProblemInstance> children;
	private Parser p;
	private int evalScore;
	
	public ProblemInstance() {
		this.evalScore = -1;
		//for each class, create an empty assignment.
	} // if no partassign
	
	public ProblemInstance(ArrayList<CoursePair> partAssign) {
		this.evalScore = -1;
		schedule.addAll(partAssign);
	} // if partassign exists; once we decide on input format, maybe pass raw input and translate it into ArrayList here.
	
	public static ProblemInstance findSchedule(ProblemInstance pr) {
		/**
		 * Recursive; compute each possible schedule, then see if it at least meets PART_CONSTR. If so, add to children. Then, for each child, evaluate it's optimality (Eval()).
		 * Once we evaluate, continue on from the most optimal to the least (I think we have to check each path until we have no valid solutions (they all break constr), but we might have to bound)
		 **/
		
		return null;
	}
	
	public int Constr() {
		//evaluate constraints, return appropriate typecode. Problems: need to be able to compute efficiently for large problem sets. How to compare timeslots easily for Course/Tutorial conflicts?
		return PART_CONSTR;
	} 
	
	public int Eval() {
		if(this.evalScore < 0) {
			//compute eval, set evalScore to result. Prevents having to compute Eval multiple times.
		}
		return this.evalScore;
	}
}

