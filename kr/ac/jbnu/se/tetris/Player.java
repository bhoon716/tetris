package kr.ac.jbnu.se.tetris;

//현재 플레이어의 정보를 저장하는 클래스
public class Player {
    private String userId = "";
    private int maxScore;
    private int level;
    private int exp = 0;
    private static int itemReserves = 3;
    
    public Player(String userId, int maxScore, int level, int exp, int itemReserves) {
        this.userId = userId;
        this.maxScore = maxScore;
        this.level = level;
        this.exp = exp;
        this.itemReserves = itemReserves;
    }

    public String getUserId() {
        return userId;
    }

    public int getMaxScore() {
        return this.maxScore;
    }

    public void setMaxScore(int score) {
        if(score > maxScore) this.maxScore = score;
    }

    public int getLevel() {
        return level;
    }

    public int getExp() { return exp; }

    public static int getItemReserves() { return itemReserves; }

    public static void subItemReserves() { itemReserves--; }

    public static void addItemReserves(){itemReserves++;}

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLevel() {
        {
            int calcLev = this.exp / 300;
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
                    break;
                default:
                    this.level = 6;
                    break;
            }
        }
    }

    public void addExp(int exp) {
        this.exp += exp;
        setLevel();
    }

    public void setExp(int exp) { this.exp = exp; }

    public void setItemReserves(int itemReserves) { this.itemReserves = itemReserves; }

    public void updateMaxScore(int score) {
        if (score > maxScore) {
            maxScore = score;
        }
    }
}
