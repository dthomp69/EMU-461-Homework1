package part1;

public class ProgramOutputs {

	public void writeOutputs(InfoPasser outputs) {
		System.out.println("RunTime: " + outputs.getRunTime());
		System.out.println("Swaps: " + outputs.getSwaps());
		System.out.println("BoardsSwapped: " + outputs.getBoardsSwapped());
	}

}
