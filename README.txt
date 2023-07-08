Name: Jason Tysl

Files:

The only new files is GarbageCollector. The rest are taken from the "Perfect Project 3"
on Carmen. Some of the other files were slightly modified to account for the different executions when dealing
with tracking and handleing the varaiables scopes.

Assign.java - this file contains the parsing and executing for the three different productions of <assign>. 
    It also contains two semantic error checks that check if the id has been declared and if it
    is a record id when needed.

Call.java - this file contains the specific function calls within the program. It allows access to the HashMap
    of function definitions from earlier in the <decl-seq>.

Cmpr.java - this file contains the parsing and executing for the three different productions of <cmpr>.

Cond.java - this file contains the parsing and executing for the three different productions of <cond>.

Core.java - this file contains the Core enum that is used to check if we're accessing the correct token.

CoreType.java - this file is a pseudo map that initiales integers to 0 and records to null.
    This space is also used to determine what types are stored where and gain access to them easier.

Decl.java - this file contains the parsing and executing for the two different productions of <decl>.

DeclInteger.java - this file contains the parsing and executing for the <decl-integer> production. It stores
    the id into the map to be checked later. It also checks for doubly declaring.

DeclRecord.java - this file contains the parsing and executing for the <decl-record> production. It stores
    the id into the map to be checked later. It also checks for doubly declaring.

DeclSeq.java - this file contains the parsing and executing for the four different productions of <decl-seq>.
    it could be just a declaration, multiple declarations, a function declaration, or a function declaration
    and one or more variable declarations.

Executor.java - this file contains the scanner that is used for input of data as well as the initialize function
    which initializes the memory spaces.

Expr.java - this file contains the parsing and executing for the three different productions of <expr>.

Factor.java - this file contains the parsing and executing for the five different productions of <factor>.
    It is the last production.

Function.java - this file handles the declarations of new functions. It adds the new function to a HashMap with
    the function name as the key and the function itself as the value.

GarbageCollector.java - this file handles a majority of the functions that deal with tracking and handling the 
    record variables as they come in and go out of scope. There are many helper functions that get used in various
    files' execute functions.

If.java - this file contains the parsing and executing for the two different productions of <if>.

Id.java - this file contains the parsing and executing for the two different productions of <index>.

Loop.java - this file contains the parsing and executing for the <loop>.

Main.java - this file creates two scanners which get used by the parser which parses the input file.
    The second scanner is used by the executor to execute the parsed data.

Memory.java - this file contains the memory map, stack of maps, and function declarations that get used by the 
    executor functions. For this project, two additional functions were created to account for the erasing of 
    variables from the scope during function calls and ifs and loops. The loop functionality wasn't working
    perfectly, but that will be discussed more in the bug handling.

Out.java - this file contains the parsing and executing for the <out>.

Parameters.java - this file contains the storage of formal parameters within a fuction definition as well as the
    function call parameters.

Parser.java - the parser of the input file.

Procedure.java - this file contains the parsing and executing for the two productions of <procedure>.

README.txt - this file contains the creator's name and information about how the entire parser program works.

Scanner.java - this file contains the scanner that was taken from the "PerfectProject1.zip" from carmen. It
    scans and tokenizes all of the words from an input file.

Stmt.java - this file contains the parsing and executing for the six different productions of <stmt>. With the
    PerfectProject3 implementation, this is slightly changed and handled in StmtSeq.java.

StmtSeq.java - this file contains the parsing and executing for the two different productions of <stmt-seq>.
 
Term.java - this file contains the parsing and executing for the three different productions of <term>.


Special Features: Nothing majorly special was done one way or another.

Overall Design: Most of the design was taken from the PerfectProject4 on Carmen. A few files were modified to 
    track the GarbageCollector, which was the biggest part of the design. I created a list of integers which
    was the main method of tracking each variable as it went in and out of scope. One of the biggest changes
    to existing files was in the execute function in Assign.java. I had to create a new tracker of a record
    variable if a "new" assign was performed (eg. varName := new record[1]). I added a tracker to the list,
    which could then be altered as the program ran. Another main portion was at the end of the procedure, we
    have to represent all of the existing variables going out of scope, so I just created a simple loop to go
    through the remaining list until it was empty (making sure not to repeat any numbers). There were various
    other tricky parts that are discussed in the testing and bugs portion below.

Testing/Bugs: Testing started pretty early on. First I just wanted to get a very basic representation of the
    garbage collector. As a new record was created, add one to the GC. At the end of the procedure, empty the
    list. Then I tested these with the few test files that didn't have anything complicated (loops or calls).
    This fortunately worked pretty well on the first go, but there may have been a minor bug. Then I moved on
    to function calls which were trickier at first, but after creating the helper function in memory to keep 
    track of if the variable existed, that made it easier. I tested them here and there was an issue where on
    one file it worked correctly, but another had the "gc:1" print and the procedure output out of order. This
    was fixed by adding an if statement to the pushFrameAndExecute fuction which would essentially just add to
    the GC tracker if it was used in the function call. I then moved onto the loop and if, which the if worked
    well from the start but maybe I was missing something. They both handle the local scope of record variables
    in the same way so there's no need to create separate helper functions to remove the tracker scope at the
    end of the statement sequence. I tested with a bunch of print statements to try and figure out why the loop
    wasn't working and it seems like the assign wasn't creating a new space for the variable and linking it to
    the id string, it was just making it null, which would cause the function that's supposed to remove it at
    the end of the stmtSeq to gloss over it. The assign was working in every other test but for some reason not
    with the while loop. My guess is that there is some issue with that helper function in the Memory.java file,
    but I ran out of time when trying to figure it out.