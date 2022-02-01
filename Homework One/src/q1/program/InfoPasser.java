package q1.program;

public class InfoPasser {
	private long runTime;
	private int swaps;
	private int boardsSearched;
	
	public InfoPasser(long runTime, int swaps, int boardsSearched) {
		this.runTime = runTime;
		this.swaps = swaps;
		this.boardsSearched = boardsSearched;
	}
	
	public long getRunTime() {
		return runTime;
	}
	public void setRunTime(long runTime) {
		this.runTime = runTime;
	}
	public int getSwaps() {
		return swaps;
	}
	public void setSwaps(int swaps) {
		this.swaps = swaps;
	}
	public int getBoardsSearched() {
		return boardsSearched;
	}
	public void setBoardsSearched(int boardsSwapped) {
		this.boardsSearched = boardsSwapped;
	}
}
