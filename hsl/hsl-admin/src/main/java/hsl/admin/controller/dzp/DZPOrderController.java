package hsl.admin.controller.dzp;

import com.alibaba.fastjson.JSON;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hsl.admin.controller.BaseController;
import hsl.app.service.local.dzp.order.DZPTicketOrderLocalService;
import hsl.app.service.local.dzp.scenicspot.DZPScenicSpotLocalService;
import hsl.domain.model.dzp.order.DZPTicketOrder;
import hsl.domain.model.dzp.scenicspot.DZPScenicSpot;
import hsl.domain.model.yxjp.YXJPOrder;
import hsl.pojo.qo.dzp.order.DZPTicketOrderQO;
import hsl.pojo.qo.dzp.scenicspot.DZPScenicSpotQO;
import hsl.pojo.util.HSLConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 电子票-后台Controller
 * Created by hgg on 2016/3/9.
 */
@Controller
@RequestMapping(value = "/dzp")
public class DZPOrderController extends BaseController {

    private String devName = "hgg";

    @Autowired
    private DZPTicketOrderLocalService     dzpTicketOrderLocalService;
    @Autowired
    private DZPScenicSpotLocalService      dzpScenicSpotLocalService;

    /**
     * 电子票----订单列表
     * @return
     */
    @RequestMapping(value="/order/list")
    public String orderList(Model model,Integer numPerPage,Integer pageNum,DZPTicketOrderQO  dzpTicketOrderQO){

        Pagination pagination = new Pagination();
        pagination.setCondition(dzpTicketOrderQO);
        pagination.setPageNo(pageNum == null ? 1 : pageNum);
        pagination.setPageSize(numPerPage == null ? 20 : numPerPage);
        pagination = dzpTicketOrderLocalService.queryPagination(pagination);
        List<DZPTicketOrder> orders = (List<DZPTicketOrder>) pagination.getList();

        model.addAttribute("pagination",pagination);
        model.addAttribute("qo",dzpTicketOrderQO);
        model.addAttribute("orders",orders);
        //【1】订单状态MAP
        Map<String, String> dzpTicketOrderStatusMap = HSLConstants.DZPTicketOrderStatus.dzpTicketOrderStatusMap;
        model.addAttribute("dzpTicketOrderStatusMap",dzpTicketOrderStatusMap);
        //【2】景区列表
        DZPScenicSpotQO dzpScenicSpotQO = new DZPScenicSpotQO();
        List<DZPScenicSpot> dzpScenicSpots = dzpScenicSpotLocalService.queryList(dzpScenicSpotQO);
        model.addAttribute("dzpScenicSpots",dzpScenicSpots);

        return "/dzp/order/list.html";
    }

    /**
     * 电子票----订单详情
     * @param model
     * @param orderNo
     * @return
     */
    @RequestMapping(value="/order/detail")
    public String orderDetail(Model model,String orderNo){

        if(StringUtils.isBlank(orderNo)){
            //为空处理
        }
        DZPTicketOrderQO  dzpTicketOrderQO = new DZPTicketOrderQO();
        dzpTicketOrderQO.setId(orderNo);
        DZPTicketOrder dzpTicketOrder = dzpTicketOrderLocalService.queryUnique(dzpTicketOrderQO);
        model.addAttribute("dzpOrder",dzpTicketOrder);

        //【1】订单状态MAP
        Map<String, String> dzpTicketOrderStatusMap = HSLConstants.DZPTicketOrderStatus.dzpTicketOrderStatusMap;
        model.addAttribute("dzpTicketOrderStatusMap",dzpTicketOrderStatusMap);
        //【2】订单退款状态MAP
//        Map<String, String> dzpTicketOrderRefundStatusMap = HSLConstants.DZPTicketOrderStatus.;
//        model.addAttribute("dzpTicketOrderRefundStatusMap",dzpTicketOrderRefundStatusMap);
        //【3】游玩人景区状态MAP
        Map<String, String> dzpSingleTicketStatusMap = HSLConstants.DZPSingleTicketStatus.dzpSingleTicketStatusMap;
        model.addAttribute("dzpSingleTicketStatusMap",dzpSingleTicketStatusMap);

        return "/dzp/order/order_detail.html";
    }

    /**
     * 景区状态是，待退款时候,申请退款
     * @param orderNo
     * @return
     */
    @RequestMapping(value="/order/refund")
    @ResponseBody
    public String refund(String orderNo){

        String message = "操作成功！！";
        String status = DwzJsonResultUtil.STATUS_CODE_500;

        return DwzJsonResultUtil.createJsonString(status, message, DwzJsonResultUtil.FLUSH_FORWARD, "dzpTicket");
    }

    /**
     * 景区状态是，待退款时候,申请退款
     * @param orderNo
     * @return
     */
    @RequestMapping(value="/order/settlement")
    @ResponseBody
    public String settlement(String orderNo){

        String message = "操作成功！！";
        String status = DwzJsonResultUtil.STATUS_CODE_500;

        return DwzJsonResultUtil.createJsonString(status, message, DwzJsonResultUtil.FLUSH_FORWARD, "dzpTicket");
    }

}
