class Main {
	public static void main(String[] args) {
		// Initialize the scanner with the input file
		// Scanner S = new Scanner(args[0]);

		Scanner S = new Scanner("Correct/8.code");
		Parser.scanner = S;
		
		Procedure p = new Procedure();
		
		p.parse();
		
		// Memory.data = new Scanner(args[1]);
		
		Memory.data = new Scanner("Correct/6.data");

		p.execute();
	}
}