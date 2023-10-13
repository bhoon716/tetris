package kr.ac.jbnu.se.tetris;

//현제 플레이어의 정보를 저장하는 클래스
public class Player {
    private String userId = "";
    private int maxScore = 0;
    private int maxCombo = 0;
    private int level = 0;
    private int exp = 0;
    private int itemReserves = 0;
    private AchievementList achievementList;
    
    public Player(String userId, int maxScore, int maxCombo, int level, int exp, int itemReserves, AchievementList achievementList) {
        this.userId = userId;
        this.maxScore = maxScore;
        this.maxCombo = maxCombo;
        this.level = level;
        this.exp = exp;
        this.itemReserves = itemReserves;
        this.achievementList = achievementList;
    }

    public String getUserId() {
        return userId;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getMaxCombo() {
        return maxCombo;
    }

    public int getLevel() {
        return level;
    }

    public int getExp() { return exp; }

    public int getItemReserves() { return itemReserves; }

    public AchievementList getAchievementList() {
        return achievementList;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public void setMaxCombo(int maxCombo) {
        this.maxCombo = maxCombo;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExp(int exp) { this.exp = exp; }

    public void setItemReserves(int itemReserves) { this.itemReserves = itemReserves; }



    public void setAchievementList(AchievementList achievementList) {
        this.achievementList = achievementList;
    }

    public void updateMaxScore(int score) {
        if (score > maxScore) {
            maxScore = score;
        }
    }

    public void updateMaxCombo(int combo) {
        if (combo > maxCombo) {
            maxCombo = combo;
        }
    }
}
