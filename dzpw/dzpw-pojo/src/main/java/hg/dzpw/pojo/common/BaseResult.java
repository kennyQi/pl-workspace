package hg.dzpw.pojo.common;

import java.io.Serializable;

/**
 * Created by yuxx on 2014/9/9.
 */
@SuppressWarnings("serial")
public class BaseResult implements Serializable {

    /**
     * 返回提示消息
     */
    private String message;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}