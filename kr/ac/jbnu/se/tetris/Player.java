package kr.ac.jbnu.se.tetris;

//현제 플레이어의 정보를 저장하는 클래스
public class Player {
    private String userId = "";
    private int maxScore = 0;
    private int maxCombo = 0;
    private int level = 1;
    private int exp = 0;
    private static int itemReserves = 3;
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

    public static int getItemReserves() { return itemReserves; }

    public static void subItemReserves() { itemReserves--; }

    public static void addItemReserves(){itemReserves++;}

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

    public void setLevel() {
        {
            int calcLev = this.exp / 100;
            if(calcLev > level - 1){
                for(int i = calcLev + 1 - level; i > 0; i--) addItemReserves();
            }
            switch (calcLev) {
                case 0:
                    this.level = 1;
                    break;
                case 1:
                    this.level = 2;
                    break;
                case 2:
                    this.level = 3;
                    break;
                case 3:
                    this.level = 4;
                    break;
                case 4:
                    this.level = 5;
                default:
                    this.level = 6;
                    break;
            }
        }
    }

    public void getExp(int exp) {
        this.exp += exp;
        setLevel();
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
