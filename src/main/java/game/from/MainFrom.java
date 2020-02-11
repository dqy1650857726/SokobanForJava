package game.from;

import game.Main;
import game.bean.Player;
import game.utils.SerializableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * 游戏开始的窗口
 */
public class MainFrom extends JFrame{
    static final Logger LOGGER = LoggerFactory.getLogger(MainFrom.class);

    public static Player player = null;

    JButton comtBtn = new JButton("继续游戏");
    JButton startBtn = new JButton("开始新游戏");
    JButton exitBtn = new JButton("退出");
    JPanel panel = new JPanel();
    public static MainFrom mainFrom = null;

    /**
     * 初始化UI
     */
    private void initUI(){
        Dimension btnSize = new Dimension(100,30);

        comtBtn.setSize(btnSize);
        comtBtn.setLocation(100,10);
        startBtn.setSize(btnSize);
        startBtn.setLocation(100,40);
        exitBtn.setLocation(100,70);
        exitBtn.setSize(btnSize);
        panel.setLayout(null);
        panel.add(startBtn);
        panel.add(exitBtn);
        panel.add(comtBtn);
        this.add(panel);
        this.setSize(300,150);
        this.setVisible(true);
        mainFrom = this;

        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                LOGGER.info("游戏退出");
                System.exit(1);
            }
        });

        //继续游戏
        comtBtn.addActionListener((ActionEvent e)->{
            GameFrom gameFrom = new GameFrom();
            mainFrom.setVisible(false);
        });

        //开始新游戏
        startBtn.addActionListener((ActionEvent e)->{
            if(MainFrom.player == null)
                MainFrom.player = new Player(1);
            else
                MainFrom.player.nowCheck = 1;
            GameFrom gameFrom = new GameFrom();
            SerializableUtil.serializable(player);
            mainFrom.setVisible(false);
        });
    }

    /**
     * 初始化数据
     */
    private void initData(){
        player = SerializableUtil.unSerializable();
        if(player==null){
            comtBtn.setEnabled(false);
        }
    }

    public MainFrom(String title){
        super(title);
        initUI();
        initData();
    }
}
