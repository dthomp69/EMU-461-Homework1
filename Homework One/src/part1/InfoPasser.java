package part1;

public class InfoPasser {
	private int runTime;
	private int swaps;
	private int boardsSearched;
	
	public InfoPasser(int runTime, int swaps, int boardsSearched) {
		this.runTime = runTime;
		this.swaps = swaps;
		this.boardsSearched = boardsSearched;
	}
	
	public int getRunTime() {
		return runTime;
	}
	public void setRunTime(int runTime) {
		this.runTime = runTime;
	}
	public int getSwaps() {
		return swaps;
	}
	public void setSwaps(int swaps) {
		this.swaps = swaps;
	}
	public int getBoardsSwapped() {
		return boardsSearched;
	}
	public void setBoardsSwapped(int boardsSwapped) {
		this.boardsSearched = boardsSwapped;
	}
}
