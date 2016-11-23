package nigelhenshaw.com.cameraintenttutorial;

/**
 * Created by guilherme on 23/11/16.
 */

public class Message {
    public boolean ifOk = true;
    public String msg = "";

    public Message() {
    }

    public Message(boolean result, String msgSub){
        ifOk = result;
        msg = msgSub;
    }
}
