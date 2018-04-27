package hsl.admin.controller.dzp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.dzpw.dealer.client.api.v1.request.TicketPolicyQO;
import hg.log.util.HgLogger;
import hsl.admin.common.ArraysUtil;
import hsl.admin.controller.BaseController;
import hsl.app.service.local.dzp.policy.DZPTicketPolicyLocalService;
import hsl.app.service.local.dzp.scenicspot.DZPScenicSpotLocalService;
import hsl.domain.model.dzp.order.DZPTicketOrder;
import hsl.domain.model.dzp.policy.DZPTicketPolicy;
import hsl.domain.model.dzp.policy.DZPTicketPolicyDatePrice;
import hsl.domain.model.dzp.scenicspot.DZPScenicSpot;
import hsl.pojo.exception.DZPTicketPolicyException;
import hsl.pojo.qo.dzp.policy.DZPTicketPolicyQO;
import hsl.pojo.qo.dzp.scenicspot.DZPScenicSpotQO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 电子票Controller
 * Created by hgg on 2016/3/10.
 */
@Service
@RequestMapping(value="/dzp/ticket")
public class DZPTicketPolicyController extends BaseController{

    private String                            devName = "hgg";
    @Autowired
    private DZPTicketPolicyLocalService       dzpTicketPolicyLocalService;
    @Autowired
    private DZPScenicSpotLocalService         dzpScenicSpotLocalService;


    /**
     * 电子票-票务list
     * @param model
     * @param dzpTicketPolicyQO
     * @param numPerPage
     * @param pageNum
     * @return
     */
    @RequestMapping(value="/list")
    public String list(Model model,DZPTicketPolicyQO dzpTicketPolicyQO,Integer numPerPage,Integer pageNum){
        //【1】票务列表
        Pagination pagination = new Pagination();
        //设置显示票务类型（只显示单票和联票）
        dzpTicketPolicyQO.setType(DZPTicketPolicy.TICKET_POLICY_TYPE_GROUP, DZPTicketPolicy.TICKET_POLICY_TYPE_SINGLE);
        pagination.setCondition(dzpTicketPolicyQO);
        pagination.setPageNo(pageNum == null ? 1 : pageNum);
        pagination.setPageSize(numPerPage == null ? 20 : numPerPage);
        pagination = dzpTicketPolicyLocalService.queryPagination(pagination);
        List<DZPTicketPolicy> dzpTicketPolicys = (List<DZPTicketPolicy>) pagination.getList();

        model.addAttribute("pagination",pagination);
        model.addAttribute("qo",dzpTicketPolicyQO);
        model.addAttribute("dzpTicketPolicys",dzpTicketPolicys);

        //【2】景区列表
        DZPScenicSpotQO dzpScenicSpotQO = new DZPScenicSpotQO();
        List<DZPScenicSpot> dzpScenicSpots = dzpScenicSpotLocalService.queryList(dzpScenicSpotQO);
        model.addAttribute("dzpScenicSpots",dzpScenicSpots);

        return "/dzp/policy/list.html";
    }

    /**
     * 单票明细
     * @param model
     * @param ticketId
     * @return
     */
    @RequestMapping(value="/single")
    public String singleTicket(Model model,String ticketId){

        DZPTicketPolicyQO dzpTicketPolicyQO = new DZPTicketPolicyQO();
        dzpTicketPolicyQO.setId(ticketId);
        DZPTicketPolicy dzpTicketPolicy = dzpTicketPolicyLocalService.queryUnique(dzpTicketPolicyQO);
        model.addAttribute("dzpTicketPolicy",dzpTicketPolicy);

        return "/dzp/policy/single_ticket.html";
    }

    /**
     * 联票明细
     * @param model
     * @param ticketId
     * @return
     */
    @RequestMapping(value="/group")
    public String groupTicket(Model model,String ticketId){

        DZPTicketPolicyQO dzpTicketPolicyQO = new DZPTicketPolicyQO();
        dzpTicketPolicyQO.setId(ticketId);
        DZPTicketPolicy dzpTicketPolicy = dzpTicketPolicyLocalService.queryUnique(dzpTicketPolicyQO);
        model.addAttribute("dzpTicketPolicy",dzpTicketPolicy);

        return "/dzp/policy/group_ticket.html";
    }

    /**
     * 某个政策的价格日历详情
     * @param model
     * @param ticketId
     * @return
     */
    @RequestMapping(value="/price")
    public String datePriceDetail(Model model,String ticketId){

        DZPTicketPolicyQO dzpTicketPolicyQO = new DZPTicketPolicyQO();
        dzpTicketPolicyQO.setId(ticketId);
        dzpTicketPolicyQO.setPriceFetch(true);
        DZPTicketPolicy dzpTicketPolicy = dzpTicketPolicyLocalService.queryUnique(dzpTicketPolicyQO);
        Map<String, DZPTicketPolicyDatePrice> priceMap = dzpTicketPolicy.getPrice();
        if(priceMap.size() > 1){
            List<JSONObject> objects = new ArrayList<JSONObject>();
            Set<String> keys = priceMap.keySet();
            for(String key : keys){
                DZPTicketPolicyDatePrice price = priceMap.get(key);
                JSONObject jo = new JSONObject();
                jo.put("settlePrice",null);
                jo.put("saleDate", price.getDate());
                jo.put("salePrice", price.getPrice() == null ? 0D:price.getPrice());
                objects.add(jo);
            }
            model.addAttribute("strJson", JSON.toJSONString(objects));
            model.addAttribute("maxDate", priceMap.get(keys.toArray()[keys.size()-1]).getDate().trim());
        }else{
            JSONArray json = new JSONArray();
            JSONObject jo = new JSONObject();
            jo.put("settlePrice", null);
            jo.put("saleDate", DateUtil.formatDate(new Date()));
            jo.put("salePrice", null);
            json.add(jo);
            model.addAttribute("strJson", JSON.toJSONString(json));
            model.addAttribute("maxDate", DateUtil.formatDate(new Date()));
            HgLogger.getInstance().info(devName, "跳转到价格日历页面 ,没有查询到改票务的价格日历信息");
        }
        return "/dzp/policy/price.html";
    }

    /**
     * 同步单个电子票
     * @param model
     * @param ticketId
     * @return
     */
    @RequestMapping(value="/sync-single")
    @ResponseBody
    public String syncSingleTicket(Model model,String ticketId){

        String message = "同步成功！！";
        String status = DwzJsonResultUtil.STATUS_CODE_200;

        try {

            TicketPolicyQO ticketPolicyQO = new TicketPolicyQO();
            String[] ticketIds = {ticketId};
            ticketPolicyQO.setTicketPolicyIds(ticketIds);
            //调用电子票务接口 同步单个票务
            dzpTicketPolicyLocalService.syncTicketPolicy(ticketPolicyQO);
        } catch (DZPTicketPolicyException e) {
            HgLogger.getInstance().error(devName,"同步单个电子票务异常!!票务ID:"+ticketId);
            status = DwzJsonResultUtil.STATUS_CODE_500;
            message = e.getMessage();
        }
        return DwzJsonResultUtil.createJsonString(status, message, DwzJsonResultUtil.FLUSH_FORWARD, "dzpTicket");
    }

}
