package q1.program;

public class ProgramOutputs {

	public void writeOutputs(InfoPasser outputs) {
		System.out.println("RunTime (Nanoseconds): " + outputs.getRunTime());
		System.out.println("RunTime (Milliseconds): " + outputs.getRunTime() / 1000000);
		System.out.println("Swaps: " + outputs.getSwaps());
		System.out.println("BoardsSearched: " + outputs.getBoardsSearched());
	}

}
