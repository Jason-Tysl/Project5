class Assign implements Stmt {
	// type is
	// 0 if id := <expr> assignment
	// 1 if id[<expr>] := <expr> assignment
	// 2 if "new" assignment
	// 3 if "record" assignment
	int type;
	// assignTo is the id on the LHS of assignment
	Id assignTo;
	// assignFrom is the id on RHS of "record" assignment
	Id assignFrom;
	// Two possible expressions in an assignment
	Expr index;
	Expr expr;
	
	public void parse() {
		type = 0;
		assignTo = new Id();
		assignTo.parse();
		if (Parser.scanner.currentToken() == Core.LBRACE) {
			type = 1;
			Parser.scanner.nextToken();
			index = new Expr();
			index.parse();
			Parser.expectedToken(Core.RBRACE);
			Parser.scanner.nextToken();
		}
		Parser.expectedToken(Core.ASSIGN);
		Parser.scanner.nextToken();
		if (Parser.scanner.currentToken() == Core.NEW) {
			type = 2;
			Parser.scanner.nextToken();
			Parser.expectedToken(Core.RECORD);
			Parser.scanner.nextToken();
			Parser.expectedToken(Core.LBRACE);
			Parser.scanner.nextToken();
			index = new Expr();
			index.parse();
			Parser.expectedToken(Core.RBRACE);
			Parser.scanner.nextToken();
		} else if (Parser.scanner.currentToken() == Core.RECORD) {
			type = 3;
			Parser.scanner.nextToken();
			assignFrom = new Id();
			assignFrom.parse();
		} else {
			expr = new Expr();
			expr.parse();
		}
		Parser.expectedToken(Core.SEMICOLON);
		Parser.scanner.nextToken();
	}
	
	public void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		assignTo.print();
		if (type == 1) {
			System.out.print("[");
			index.print();
			System.out.print("]");
		}
		System.out.print(":=");
		if (type == 0 || type == 1) {
			expr.print();
		} else if (type == 2) {
			System.out.print("new record[");
			index.print();
			System.out.print("]");
		} else if (type == 3) {
			System.out.print("record ");
			assignFrom.print();
		}
		System.out.println(";");
	}
	
	public void execute() {
		if (type == 0) {
			Memory.store(assignTo.getString(), expr.execute());
		} else if (type == 1) {
			Memory.store(assignTo.getString(), index.execute(), expr.execute());
		} else if (type == 2) {
			Memory.allocate(assignTo.getString(), index.execute());
			GarbageCollector.allocateGC();
			GarbageCollector.printGC(GarbageCollector.allGarbage());
		}else if (type == 3) {
			Memory.alias(assignTo.getString(), assignFrom.getString());
			handleGarbageCollectionTrackingAlias();
		}
	}

	// if the assignTo variable is not null subtract it, then add it to the GC
	private void handleGarbageCollectionTrackingAlias() {
		Memory.Value value = Memory.getLocalOrGlobal(assignTo.getString());
		if (value.recordVal != null) {
			GarbageCollector.takeVarFromGC(assignTo.getString());
		}
		GarbageCollector.addVarToGC(assignTo.getString());
		GarbageCollector.checkIfIdIsAtZero(assignTo.getString());
	}
}