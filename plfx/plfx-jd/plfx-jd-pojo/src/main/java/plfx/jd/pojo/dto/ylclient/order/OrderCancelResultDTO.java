

package plfx.jd.pojo.dto.ylclient.order;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OrderCancelResultDTO implements Serializable{
	//取消订单返回结果
    protected boolean successs;

    public boolean isSuccesss() {
        return successs;
    }

    public void setSuccesss(boolean value) {
        this.successs = value;
    }

}
