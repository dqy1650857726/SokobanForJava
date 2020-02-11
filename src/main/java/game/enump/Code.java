package game.enump;

/**
 * 公共枚举
 */
public enum  Code {
    LEFT(65),
    RIGHT(68),
    UP(87),
    DOWN(83),

    PLAYER(1),
    WALL(4),
    NUL(0),
    BOX(2),
    POINT(3),
    PLAYER_POINT(6),
    BOX_POINT(5);

    private int k;

    private Code(int i) {
        this.k = i;
    }

    public int value(){
        return k;
    }
}
