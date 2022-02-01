package q1.program;

import java.io.BufferedWriter;

public class ProgramOutputs {

	private InfoPasser outputs;

	public void writeOutputs(InfoPasser outputs) {
		this.outputs = outputs;

		System.out.println("RunTime (Nanoseconds): " + outputs.getRunTime());
		System.out.println("RunTime (Milliseconds): " + outputs.getRunTime() / 1000000);
		System.out.println("Swaps: " + outputs.getSwaps());
		System.out.println("BoardsSearched: " + outputs.getBoardsSearched());
	}

	public void writeToFile() {
//		BufferedWriter writer = new BufferedWriter();
	}

}
