package game.utils;

import game.bean.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 序列化工具
 */
public class SerializableUtil {
    static final String FILE_PATH = "c:\\game\\player.out";
    static final Logger LOGGER = LoggerFactory.getLogger(SerializableUtil.class);

    /**
     * @param o 需要序列化的对象
     * @return  是否成功
     */
    static public boolean serializable(Object o) {
        File f = new File("c:\\game");
        if(!f.exists())
            f.mkdir();
        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_PATH);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(o);
            objectOutputStream.close();
            outputStream.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return 反序列化出来的对象
     */
    static public Player unSerializable() {
        try {
            FileInputStream inputStream = new FileInputStream(FILE_PATH);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Player res = (Player)objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
            return res;
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }
    }
}
