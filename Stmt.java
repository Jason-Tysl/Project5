//Stmt is an interface so we can take advantage of some polymorphism in StmtSeq
interface Stmt {
	void parse();
	void execute();
	void print(int indent);
}