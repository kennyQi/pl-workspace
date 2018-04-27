package zzpl.api.client.test.jp;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.dto.jp.GJFlightDTO;
import zzpl.api.client.dto.jp.GJPassengerBaseInfoDTO;
import zzpl.api.client.dto.jp.GJPassengerDTO;
import zzpl.api.client.request.jp.GJBookCommand;
import zzpl.api.client.response.jp.GJFlightResponse;

import com.alibaba.fastjson.JSON;

public class BookGJFlightTest {

	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.10.65:8080/zzpl-api/api", "ios", "7a0e792cba184df98e5eb56cd51732fe");
		GJBookCommand gjBookCommand = new GJBookCommand();
		gjBookCommand.setEncryptString("encryptStringencryptStringencryptStringencryptStringencryptString");
		List<GJFlightDTO> gjFlightDTOs = new ArrayList<GJFlightDTO>();
		GJFlightDTO gjFlightDTO = new GJFlightDTO();
		gjFlightDTO.setAirComp("阿联酋航空");
		gjFlightDTO.setArrivalAirport(null);
		gjFlightDTO.setArrivalTerm("T1");
		gjFlightDTO.setCabinCode("A");
		gjFlightDTO.setDepartAirport(null);
		gjFlightDTO.setDepartTerm("T2");
		gjFlightDTO.setDstCity("PEK");
		gjFlightDTO.setDuration(56);
		gjFlightDTO.setEndTime(new Date());
		gjFlightDTO.setFlightNO("AB3233");
		gjFlightDTO.setMealCode("L");
		gjFlightDTO.setOrgCity("DWC");
		gjFlightDTO.setPlaneType("380");
		gjFlightDTO.setStartTime(new Date());
		gjFlightDTOs.add(gjFlightDTO);
		gjFlightDTOs.add(gjFlightDTO);
		gjBookCommand.setGjFlightDTOs(gjFlightDTOs);
		gjBookCommand.setIbePrice("1880");
		gjBookCommand.setLinkAddress("中共中央国务院黑龙江省拜泉县");
		gjBookCommand.setLinkEmail("zzpl@ply365.com");
		gjBookCommand.setLinkName("联系人");
		gjBookCommand.setLinkTelephone("18888888888");
		gjBookCommand.setNote("备注备注备注备注备注备注备注备注备注备注备注备注备注备注");
		List<GJPassengerDTO> gjPassengerDTOs = new ArrayList<GJPassengerDTO>();
		GJPassengerDTO gjPassengerDTO = new GJPassengerDTO();
		GJPassengerBaseInfoDTO gjPassengerBaseInfoDTO = new GJPassengerBaseInfoDTO();
		gjPassengerBaseInfoDTO.setBirthday(new Date());
		gjPassengerBaseInfoDTO.setExpiryDate(new Date());
		gjPassengerBaseInfoDTO.setIdNo("123409199001015678");
		gjPassengerBaseInfoDTO.setIdType(1);
		gjPassengerBaseInfoDTO.setMobile("13888888888");
		gjPassengerBaseInfoDTO.setNationality("CHN");
		gjPassengerBaseInfoDTO.setPassengerName("乘机人1");
		gjPassengerBaseInfoDTO.setPassengerType(1);
		gjPassengerBaseInfoDTO.setSex(2);
		gjPassengerDTO.setBaseInfo(gjPassengerBaseInfoDTO);
		gjPassengerDTOs.add(gjPassengerDTO);
		gjPassengerDTOs.add(gjPassengerDTO);
		gjBookCommand.setPassengerList(gjPassengerDTOs);
		gjBookCommand.setReimbursementStatus(1);
		gjBookCommand.setTotalTax(523.36);
		gjBookCommand.setUserID("a7c0c94da12644e1a69df6c56901fc91");
		gjBookCommand.setWorkflowID("5b704444d2394f4b9a474f78cd0c0cd6");
		ApiRequest request = new ApiRequest("BookGJFlight", "752a9f7c91f34482a50932d12a48cff6", gjBookCommand, "1.0");
		GJFlightResponse response = client.send(request,GJFlightResponse.class);
		System.out.println(JSON.toJSON(response));

	}
}
