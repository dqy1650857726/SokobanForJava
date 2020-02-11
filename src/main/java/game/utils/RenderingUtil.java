package game.utils;

import ch.qos.logback.core.util.TimeUtil;
import game.Main;
import game.from.GameFrom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 渲染游戏界面
 */
public class RenderingUtil {
    JPanel panel;
    int[][] gameMap;
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
      2,2,1000, TimeUnit.SECONDS,new ArrayBlockingQueue<>(2));
    private static ImageIcon[] imageIcons = new ImageIcon[7];
    private static final Logger LOGGER = LoggerFactory.getLogger(RenderingUtil.class);

    /**
     * @param p 在哪个画板画
     * @param arr   游戏当前状态数组
     */
    public RenderingUtil(JPanel p,int[][] arr){
        this.panel = p;
        this.gameMap = arr;
        String path = this.getClass().getClassLoader().getResource("img/0.jpg").getPath();
        path = path.substring(0,path.length()-5);
        for(int i=0;i<imageIcons.length;i++){
            imageIcons[i]  = new ImageIcon(path + Integer.toString(i) + ".jpg");
        }
    }

    /**
     * 一个渲染游戏当前状态的任务
     */
    private class Task implements Runnable{
        int b;
        int e;
        public Task(int b,int e){this.b = b;this.e=e;}

        @Override
        public void run() {
            String imgName = "";
            for(int i=b;i<=e;i++) {
                for(int j=0;j<gameMap.length;j++) {
                    GameFrom.grid[i][j].setIcon(imageIcons[gameMap[i][j]]);
                }
            }
            LOGGER.info("渲染完毕!");
        }
    }

    public void rendering(){
        threadPoolExecutor.execute(new Task(0,4));
        threadPoolExecutor.execute(new Task(5,GameFrom.MAX_LEN-1));
    }
}
