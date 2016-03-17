package RC2K7.Plugins.RPGQuest.Util;

import RC2K7.Plugins.RPGQuest.Goal.GoalBase;

public class GoalSelection {
	
	private String Quest = null;
	private int Index = -1;
	private GoalBase Goal = null;
	
	public GoalSelection(String quest, int index, GoalBase goal)
	{
		setQuest(quest);
		setIndex(Index);
		setGoal(goal);
	}

	public GoalBase getGoal() {
		return this.Goal;
	}

	public void setGoal(GoalBase goal) {
		this.Goal = goal;
	}

	public int getIndex() {
		return this.Index;
	}

	public void setIndex(int index) {
		this.Index = index;
	}

	public String getQuest() {
		return this.Quest;
	}

	public void setQuest(String quest) {
		this.Quest = quest;
	}

}
