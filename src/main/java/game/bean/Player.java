package game.bean;

import java.io.Serializable;

/**
 * 用于保存游戏进度
 */
public class Player implements Serializable {
    public int nowCheck;

    /**
     * @param nowCheck 游戏已经进行到第几关
     */
    public Player(int nowCheck){
        this.nowCheck = nowCheck;
    }

    @Override
    public String toString() {
        return "nowCheck=" + Integer.toString(nowCheck);
    }
}
