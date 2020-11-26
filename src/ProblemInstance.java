import java.util.ArrayList;

public class ProblemInstance {
	
	public final int BAD_CONSTR = 1;
	public final int PART_CONSTR = 2;
	public final int GOOD_CONSTR = 3;

	private ArrayList<CoursePair> schedule;
	
	public ProblemInstance() {
		
	} // if no partassign
	
	public ProblemInstance(ArrayList<CoursePair> partAssign) {
		
	} // if partassign exists; once we decide on input format, maybe pass raw input and translate it into ArrayList here.
	
	public boolean Constr() {
		
		return true;
	} //maybe use typecode system? Breaks some constraint, good but not all courses scheduled, good and schedule complete?
	
	public int Eval() {
		
		return 1;
	}
}

