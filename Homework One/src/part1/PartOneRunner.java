package part1;

public class PartOneRunner {

	public static void main(String[] args) {
		UserInputs inputs = new UserInputs();
		
		inputs.askForInputs();
		
		System.out.println(inputs.getPuzzleSize());
		System.out.println(inputs.getInitialBoard());
		System.out.println(inputs.getGoalBoard());
		System.out.println(inputs.getEvaluationOption());
		System.out.println(inputs.getHeuristicOption());
		
		AStarSliding algorithm = new AStarSliding();
		
		
		
	}

}
