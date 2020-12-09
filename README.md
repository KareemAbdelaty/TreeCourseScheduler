# TreeCourseScheduler
This program was developed by Tutorial 3 Group 1 in order schedule courses based on the Requirments defined in problem description
by utlising an And-tree based search. The program expects 10 inputs defined below. The code assumes that all arguments entered are
valid(correct filepaths and integer values for weights and penalties) and will not attempt to handle invalid inputs. If valid inputs
are passed to the program, the Program will start searching for the optimal solution. After the search is complete the program will
either print to the console "no possible solutions" if there are no possible solutions to the given input or it will print the best
solution it has found with its eval value. Lastly, during the proccess of the search the code will update the output file whenever it
finds a better solution. This allows the user to extract valid non-optimal solutions even if the search has not terminated. At the end
of the search the output file will contain the optimal solution(the same one being outputted to the console). 

here is the neccasry information in order to be able to run this Program:

Compiling and running the code:
	Compiling the code:
		- compiling from command line:
			- Open Command prompt(windows) or shell(linux)
			- navigate to the src folder using the cd command
			- compile the code using javac *.java command
			- you can redirect the output to the bin folder using the -d javac command 
		- Compiling from an Ide such as eclipse:
			- Compiling the code using an Ide is simple, you only need to import the TreeCourseScheduler folder
			  as git project and the Ide will automatically compile the code for you when you try to run
			 
	Running the program:
		- make sure all 5 class are in the same folder
			- running from command line:
				- Open Command prompt(windows) or shell(linux)
				- navigate to the folder containing the Class files using the cd command
				- run the code using the following command. You will need to replace the arguments 
				  with the arguments you want to run the Program with
				- java Driver inputfilepath wmin wpref wpair wsecdeff pen_coursemin pen_labmin pen_notpaired pen_section OutFilepath
				- inputfilepath is the path to the input file and the OutFilepath is the path to Output file 
				- the rest of the parameters are the same as defined in the assignment description  
Here is some more information about the file structure of the program

The Src folder contains the projects code that defines the structure of the Program this is your starting point if you want 
to compile the program :
	The code consists of the following 5 classes defined in the files inside Src.
	- CoursePair.java represents the datastructure for a (Class,slot) pairing.
	- The Driver code is responsible for parsing the command line arguments
	  passing the arguments to the parser, passing the results of the parser to the Scheduler
	  and formatting the output
	- Parser.java is responsible for transforming the input text file into that datastructes used in the search
	- ProblemInstance.java defines And-tree structure,findSchedule that preforms a DFS on the and tree for a solution,
	  the bounding methods Eval and Constr, and other helper functions
	- Slot.java defines a Slot and keeps track of all Slot releated data like Max and min assignments 

The bin folder contains already compiled source. This is your starting point whenever you want to run the code

the Test folder contains the tests input that this program was tested with

