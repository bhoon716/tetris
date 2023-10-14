package kr.ac.jbnu.se.tetris;

public class AchievementList {
    private AchievementItem[] achievementItems = new AchievementItem[10];

    public AchievementList(){
        achievementItems[0] = new AchievementItem("초보자", "최고 점수 10000점 달성", false);
        achievementItems[1] = new AchievementItem("중수", "최고 점수 50000점 달성", false);
        achievementItems[2] = new AchievementItem("고수", "최고 점수 100000점 달성", false);
        achievementItems[3] = new AchievementItem("마스터", "최고 점수 200000점 달성", false);
        achievementItems[4] = new AchievementItem("콤보 초보자", "최고 콤보 10달성", false);
        achievementItems[5] = new AchievementItem("콤보 중수", "최고 콤보 20달성", false);
        achievementItems[6] = new AchievementItem("콤보 고수", "최고 콤보 30달성", false);
        achievementItems[7] = new AchievementItem("콤보 마스터", "최고 콤보 40달성", false);
        achievementItems[8] = new AchievementItem("레벨 3", "레벨 3 달성", false);
        achievementItems[9] = new AchievementItem("레벨 6", "레벨 6 달성", false);
    }

    public String[] getAchievement(){
        String[] achievedList = new String[achievementItems.length];
        int index = 0;
        for (int i=0; i<achievementItems.length; i++){
            if (achievementItems[i].getIsAchieved()){
                achievedList[index]  = "[" + achievementItems[i].getName() + "] : " + achievementItems[i].getDescription();
            }
        }
        return achievedList;
    }

    public void setAchievement(int index, boolean isAchieved){
        achievementItems[index].setIsAchieved(isAchieved);
    }

    public class AchievementItem{
        private String name;
        private String description;
        private boolean isAchieved;

        public AchievementItem(String name, String description, boolean isAchieved){
            this.name = name;
            this.description = description;
            this.isAchieved = isAchieved;
        }

        public String getName(){
            return name;
        }

        public String getDescription(){
            return description;
        }

        public boolean getIsAchieved(){
            return isAchieved;
        }

        public void setIsAchieved(boolean isAchieved){
            this.isAchieved = isAchieved;
        }

    }
}
