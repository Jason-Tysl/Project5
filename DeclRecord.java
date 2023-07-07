class DeclRecord {
	Id id;
	
	public void parse() {
		Parser.expectedToken(Core.RECORD);
		Parser.scanner.nextToken();
		id = new Id();
		id.parse();
		Parser.expectedToken(Core.SEMICOLON);
		Parser.scanner.nextToken();
	}
	
	public void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		System.out.print("record ");
		id.print();
		System.out.println(";");
	}
	
	public void execute() {
		Memory.declareRecord(id.getString());
	}
}