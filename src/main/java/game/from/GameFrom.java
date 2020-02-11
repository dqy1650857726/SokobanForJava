package game.from;

import game.Main;
import game.bean.Player;
import game.enump.Code;
import game.utils.RenderingUtil;
import game.utils.SerializableUtil;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 游戏运行时窗口
 */
public class GameFrom extends JFrame {

    public static final int MAX_LEN = 9;

    JPanel panel = new JPanel(new GridLayout(MAX_LEN,MAX_LEN));
    RenderingUtil renderingUtil;
    List<Point> pointList;
    int[][] gameMap;
    Point nowPlayerXY = new Point();    //玩家当前位置
    public  static JLabel[][] grid;
    JFrame me = this;

    private void initData() throws Exception {
        gameMap = getGameMap();
        renderingUtil = new RenderingUtil(panel,gameMap);
    }

    private void closeNowFrm(){
        MainFrom.mainFrom.setVisible(true);
        me.dispose();
    }

    private void initMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenuItem restar = new JMenu("重新开始本关卡");
        JMenuItem quit = new JMenu("返回主菜单");
        menuBar.add(restar);
        menuBar.add(quit);
        //给菜单项添加响应事件
        restar.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e){
                try {
                    initData();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                renderingUtil.rendering();
                System.out.println("cx");
            }

            @Override
            public void mouseReleased(MouseEvent e){}

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        quit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
            @Override
            public void mousePressed(MouseEvent e){
                closeNowFrm();
                System.out.println("asaaa");
            }
            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {

            }
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        this.setJMenuBar(menuBar);
    }

    private void initUI(){
        grid=new JLabel[MAX_LEN][MAX_LEN];
        for(int i=0;i<MAX_LEN;i++)
            for (int j=0;j<MAX_LEN;j++) {
                grid[i][j] = new JLabel();
                panel.add(grid[i][j]);
            }
        this.setLayout(null);
        this.setSize(600,600);
        this.setVisible(true);
        this.add(panel);
        initMenu();
       // panel.setBackground(Color.WHITE);
        panel.setSize(450,450);

        renderingUtil.rendering();
        panel.repaint();

        this.addKeyListener(new KeyListener() {
            /**
             * 移动
             * @param keyCode 移动的方向
             */
            private void mov(int keyCode){
                if(Code.DOWN.value() == keyCode){
                    System.out.println("s");

                    //取得下个位置的状态
                    int next = gameMap[nowPlayerXY.x+1][nowPlayerXY.y];
                    //取得当前位置的状态
                    int now = gameMap[nowPlayerXY.x][nowPlayerXY.y];
                    if(next==Code.POINT.value()) {
                        gameMap[nowPlayerXY.x][nowPlayerXY.y] = Code.NUL.value();
                        gameMap[nowPlayerXY.x+1][nowPlayerXY.y] = Code.PLAYER_POINT.value();
                        nowPlayerXY.x++;
                    }else if(next==Code.NUL.value()){
                        if(now==Code.PLAYER_POINT.value()){ // 如果当前位置是复合状态
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.POINT.value();
                            gameMap[nowPlayerXY.x+1][nowPlayerXY.y]=Code.PLAYER.value();
                        }else {
                            gameMap[nowPlayerXY.x + 1][nowPlayerXY.y] = Code.PLAYER.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y] = Code.NUL.value();
                        }
                        nowPlayerXY.x++;
                    }else if(next == Code.BOX.value()){
                        int nextToNext = gameMap[nowPlayerXY.x+2][nowPlayerXY.y];
                        if(nextToNext==Code.NUL.value()){
                            gameMap[nowPlayerXY.x+2][nowPlayerXY.y]=Code.BOX.value();
                            gameMap[nowPlayerXY.x+1][nowPlayerXY.y]=Code.PLAYER.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.NUL.value();
                            if(now == Code.PLAYER_POINT.value())
                                gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.POINT.value();
                            nowPlayerXY.x++;
                        }else if(nextToNext == Code.POINT.value()){
                            gameMap[nowPlayerXY.x+2][nowPlayerXY.y]=Code.BOX_POINT.value();
                            gameMap[nowPlayerXY.x+1][nowPlayerXY.y]=Code.PLAYER.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.NUL.value();
                            nowPlayerXY.x++;
                        }
                    }else if(next == Code.BOX_POINT.value()) {//  如果下一个位置是箱子+目的地的复合状态
                        int nextToNext = gameMap[nowPlayerXY.x+2][nowPlayerXY.y];
                        if(nextToNext==Code.NUL.value()){
                            gameMap[nowPlayerXY.x+2][nowPlayerXY.y]=Code.BOX.value();
                            gameMap[nowPlayerXY.x+1][nowPlayerXY.y]=Code.PLAYER_POINT.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.NUL.value();
                            nowPlayerXY.x++;
                        }else if(nextToNext == Code.POINT.value()){
                            gameMap[nowPlayerXY.x+2][nowPlayerXY.y]=Code.BOX_POINT.value();
                            gameMap[nowPlayerXY.x+1][nowPlayerXY.y]=Code.PLAYER_POINT.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.NUL.value();
                            nowPlayerXY.x++;
                        }
                    }
                }else if(Code.UP.value() == keyCode){
                    System.out.println("w");

                    //取得下个位置的状态
                    int next = gameMap[nowPlayerXY.x-1][nowPlayerXY.y];
                    //取得当前位置的状态
                    int now = gameMap[nowPlayerXY.x][nowPlayerXY.y];
                    if(next==Code.POINT.value()) {
                        gameMap[nowPlayerXY.x][nowPlayerXY.y] = Code.NUL.value();
                        gameMap[nowPlayerXY.x - 1][nowPlayerXY.y] = Code.PLAYER_POINT.value();
                        nowPlayerXY.x--;
                    }else if(next==Code.NUL.value()){
                        if(now==Code.PLAYER_POINT.value()){ // 如果当前位置是复合状态
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.POINT.value();
                            gameMap[nowPlayerXY.x-1][nowPlayerXY.y]=Code.PLAYER.value();
                        }else {
                            gameMap[nowPlayerXY.x - 1][nowPlayerXY.y] = Code.PLAYER.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y] = Code.NUL.value();
                        }
                        nowPlayerXY.x--;
                    }else if(next == Code.BOX.value()){
                        int nextToNext = gameMap[nowPlayerXY.x-2][nowPlayerXY.y];
                        if(nextToNext==Code.NUL.value()){
                            gameMap[nowPlayerXY.x-2][nowPlayerXY.y]=Code.BOX.value();
                            gameMap[nowPlayerXY.x-1][nowPlayerXY.y]=Code.PLAYER.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.NUL.value();
                            if(now == Code.PLAYER_POINT.value())
                                gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.POINT.value();
                            nowPlayerXY.x--;
                        }else if(nextToNext == Code.POINT.value()){
                            gameMap[nowPlayerXY.x-2][nowPlayerXY.y]=Code.BOX_POINT.value();
                            gameMap[nowPlayerXY.x-1][nowPlayerXY.y]=Code.PLAYER.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.NUL.value();
                            nowPlayerXY.x--;
                        }
                    }else if(next == Code.BOX_POINT.value()) {//  如果下一个位置是箱子+目的地的复合状态
                        int nextToNext = gameMap[nowPlayerXY.x-2][nowPlayerXY.y];
                        if(nextToNext==Code.NUL.value()){
                            gameMap[nowPlayerXY.x-2][nowPlayerXY.y]=Code.BOX.value();
                            gameMap[nowPlayerXY.x-1][nowPlayerXY.y]=Code.PLAYER_POINT.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.NUL.value();
                            nowPlayerXY.x--;
                        }else if(nextToNext == Code.POINT.value()){
                            gameMap[nowPlayerXY.x-2][nowPlayerXY.y]=Code.BOX_POINT.value();
                            gameMap[nowPlayerXY.x-1][nowPlayerXY.y]=Code.PLAYER_POINT.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.NUL.value();
                            nowPlayerXY.x--;
                        }
                    }
                }else if(Code.LEFT.value() == keyCode){
                    System.out.println("a");

                    //取得下个位置的状态
                    int next = gameMap[nowPlayerXY.x][nowPlayerXY.y-1];
                    //取得当前位置的状态
                    int now = gameMap[nowPlayerXY.x][nowPlayerXY.y];

                    if(next==Code.POINT.value()) {
                        gameMap[nowPlayerXY.x][nowPlayerXY.y] = Code.NUL.value();
                        gameMap[nowPlayerXY.x][nowPlayerXY.y-1] = Code.PLAYER_POINT.value();
                        nowPlayerXY.y--;
                    }else if(next==Code.NUL.value()){
                        if(now==Code.PLAYER_POINT.value()){ // 如果当前位置是复合状态
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.POINT.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y-1]=Code.PLAYER.value();
                        }else {
                            gameMap[nowPlayerXY.x][nowPlayerXY.y-1] = Code.PLAYER.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y] = Code.NUL.value();
                        }
                        nowPlayerXY.y--;
                    }else if(next == Code.BOX.value()){
                        int nextToNext = gameMap[nowPlayerXY.x][nowPlayerXY.y-2];
                        if(nextToNext==Code.NUL.value()){
                            gameMap[nowPlayerXY.x][nowPlayerXY.y-2]=Code.BOX.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y-1]=Code.PLAYER.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.NUL.value();
                            if(now == Code.PLAYER_POINT.value())
                                gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.POINT.value();
                            nowPlayerXY.y--;
                        }else if(nextToNext == Code.POINT.value()){
                            gameMap[nowPlayerXY.x][nowPlayerXY.y-2]=Code.BOX_POINT.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y-1]=Code.PLAYER.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.NUL.value();
                            nowPlayerXY.y--;
                        }
                    }else if(next == Code.BOX_POINT.value()) {//  如果下一个位置是箱子+目的地的复合状态
                        int nextToNext = gameMap[nowPlayerXY.x][nowPlayerXY.y-2];
                        if(nextToNext==Code.NUL.value()){
                            gameMap[nowPlayerXY.x][nowPlayerXY.y-2]=Code.BOX.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y-1]=Code.PLAYER_POINT.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.NUL.value();
                            nowPlayerXY.y--;
                        }else if(nextToNext == Code.POINT.value()){
                            gameMap[nowPlayerXY.x][nowPlayerXY.y-2]=Code.BOX_POINT.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y-1]=Code.PLAYER_POINT.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.NUL.value();
                            nowPlayerXY.y--;
                        }
                    }

                }else if(Code.RIGHT.value() == keyCode){
                    System.out.println("d");

                    //取得下个位置的状态
                    int next = gameMap[nowPlayerXY.x][nowPlayerXY.y+1];
                    //取得当前位置的状态
                    int now = gameMap[nowPlayerXY.x][nowPlayerXY.y];
                    if(next==Code.POINT.value()) {
                        gameMap[nowPlayerXY.x][nowPlayerXY.y] = Code.NUL.value();
                        gameMap[nowPlayerXY.x][nowPlayerXY.y+1] = Code.PLAYER_POINT.value();
                        nowPlayerXY.y++;
                    }else if(next==Code.NUL.value()){
                        if(now==Code.PLAYER_POINT.value()){ // 如果当前位置是复合状态
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.POINT.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y+1]=Code.PLAYER.value();
                        }else {
                            gameMap[nowPlayerXY.x][nowPlayerXY.y+1] = Code.PLAYER.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y] = Code.NUL.value();
                        }
                        nowPlayerXY.y++;
                    }else if(next == Code.BOX.value()){
                        int nextToNext = gameMap[nowPlayerXY.x][nowPlayerXY.y+2];
                        if(nextToNext==Code.NUL.value()){
                            gameMap[nowPlayerXY.x][nowPlayerXY.y+2]=Code.BOX.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y+1]=Code.PLAYER.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.NUL.value();
                            if(now == Code.PLAYER_POINT.value())
                                gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.POINT.value();
                            nowPlayerXY.y++;
                        }else if(nextToNext == Code.POINT.value()){
                            gameMap[nowPlayerXY.x][nowPlayerXY.y+2]=Code.BOX_POINT.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y+1]=Code.PLAYER.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.NUL.value();
                            nowPlayerXY.y++;
                        }
                    }else if(next == Code.BOX_POINT.value()) {//  如果下一个位置是箱子+目的地的复合状态
                        int nextToNext = gameMap[nowPlayerXY.x][nowPlayerXY.y+2];
                        if(nextToNext==Code.NUL.value()){
                            gameMap[nowPlayerXY.x][nowPlayerXY.y+2]=Code.BOX.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y+1]=Code.PLAYER_POINT.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.NUL.value();
                            nowPlayerXY.y++;
                        }else if(nextToNext == Code.POINT.value()){
                            gameMap[nowPlayerXY.x][nowPlayerXY.y+2]=Code.BOX_POINT.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y+1]=Code.PLAYER_POINT.value();
                            gameMap[nowPlayerXY.x][nowPlayerXY.y]=Code.NUL.value();
                            nowPlayerXY.y++;
                        }
                    }
                }
            }

            /**
             * @return 判断胜负
             */
            private boolean checkWin(){
                for(Point i : pointList){
                    if(gameMap[i.x][i.y]!=Code.BOX_POINT.value())
                        return false;
                }
                return true;
            }

            /**
             * 键盘输入的内容
             * @param e 键盘事件
             */
            @Override
            public void keyTyped(KeyEvent e) {

            }

            /**
             * 键盘按下
             * @param e 键盘事件
             */
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                mov(keyCode);
                renderingUtil.rendering();
                if(checkWin()){
                    //保存游戏
                    MainFrom.player.nowCheck++;
                    SerializableUtil.serializable(MainFrom.player);//保存游戏
                    System.out.println("Win!");
                    JOptionPane.showMessageDialog(panel,"恭喜通关！点击按钮进入下一关！","提示",JOptionPane.QUESTION_MESSAGE);
                    //进入下一关
                    try {
                        initData();
                    }catch (Exception ee){
                        JOptionPane.showMessageDialog(panel,"没有下一关！你已经完成所有关卡!","提示",JOptionPane.QUESTION_MESSAGE);
                        closeNowFrm();
                    }
                    renderingUtil.rendering();
                    me.setTitle("推箱子-当前关卡:"+Integer.toString(MainFrom.player.nowCheck));
                }
            }

            /**
             * 键盘释放
             * @param e 键盘事件
             */
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    public GameFrom(){
        super("推箱子-当前关卡"+Integer.toString(MainFrom.player.nowCheck));
        try {
            initData();
        }catch (Exception e){
            JOptionPane.showMessageDialog(panel,"你已经通关!","提示",JOptionPane.QUESTION_MESSAGE);
            closeNowFrm();
            return;
        }
        initUI();
    }

    /**
     * @return 从文件中取得当前关卡的图
     */
    private int[][] getGameMap() throws Exception {
//        4-墙
//        0-空白
//        1-玩家
//        2-箱子
//        3-目标位置
        System.out.println(MainFrom.player.nowCheck);
        String mapFilePath = null;
        try {
            mapFilePath = this.getClass().getClassLoader()
                    .getResource("map/" + Integer.toString(MainFrom.player.nowCheck) + ".txt").getPath();
        }catch (Exception e){
            throw new Exception("没有下一关!");
        }

        File file = new File(mapFilePath);
        int[][] res = null;
        try {
            char[] buffer = new char[1024];
            FileReader fileReader = new FileReader(mapFilePath);
            int len = fileReader.read(buffer);
            res = getMapContent(buffer,len);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;

    }

    /**
     * @param arr  从io流读出来的char数组
     * @param len   数组长度
     * @return 地图的int[][] map数据
     */
    private int[][] getMapContent(char[] arr,int len){
        pointList = new ArrayList<>();
        int[][] res = new int[MAX_LEN][MAX_LEN];
        int x=0,y=0;
        for(int i=0;i<len;i++){
            if(arr[i]>='0' || arr[i]<=4){
                res[x][y] = arr[i] - '0';
                if(arr[i]=='1'){    //取得玩家位置
                    nowPlayerXY.x = x;
                    nowPlayerXY.y = y;
                }
                if(arr[i]=='3')
                    pointList.add(new Point(x,y));
                y++;
                if(y==MAX_LEN){
                    x++;
                    y=0;
                }
            }
        }
        System.out.println(nowPlayerXY);
        System.out.println(pointList);
        return res;
    }
}
