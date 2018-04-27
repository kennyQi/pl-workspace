package zzpl.api.client.test.jp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.dto.jp.GNFlightListDTO;
import zzpl.api.client.dto.jp.GNPassengerDTO;
import zzpl.api.client.request.jp.GNBookCommand;
import zzpl.api.client.response.jp.GNBookResponse;

import com.alibaba.fastjson.JSON;

public class BookGNFlightTest {

	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.2.227:15046/api", "ios","e88041ecd73941888060e47081741399");		
		GNBookCommand gnBookCommand = new GNBookCommand();
		GNFlightListDTO gnFlightListDTO = new GNFlightListDTO();
		gnFlightListDTO.setAirComp("CZ");
		gnFlightListDTO.setAirCompanyName("中国南方航空公司");
		gnFlightListDTO.setArrivalAirport("哈尔滨太平国际机场");
		gnFlightListDTO.setArrivalTerm("T3");
		gnFlightListDTO.setBuildFee(50.00);
		gnFlightListDTO.setCabinDiscount("230");
		gnFlightListDTO.setCabinName("公务折扣舱");
		gnFlightListDTO.setDepartAirport("杭州萧山国际机场");
		gnFlightListDTO.setDepartTerm("--");
		gnFlightListDTO.setDstCity("PEK");
		gnFlightListDTO.setDstCityName("北京");
		gnFlightListDTO.setCabinCode("Y");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long long1 = new Long("1443367800000");
		String string = simpleDateFormat.format(long1);
		gnFlightListDTO.setEndTime(simpleDateFormat.parse(string));
		gnFlightListDTO.setEncryptString("cd340ebbf9944da066c80e91b1ede141045d7abfdb75a3a8e7e74458a8302c329cb297695e2fe7e310d7bc2ecd184fd6ad9acea1f49260ef7cbae895d3fb81470771d01a83300526c1cda3109eac652f2ba1c4ae44622c74");
		gnFlightListDTO.setFlightNO("CZ3744");
		gnFlightListDTO.setOilFee(130.0);
		gnFlightListDTO.setOrgCity("HGH");
		gnFlightListDTO.setOrgCityName("哈尔滨");
		gnFlightListDTO.setPlaneType("319");
		Long long2 = new Long("1443356100000");
		String string1 = simpleDateFormat.format(long2);
		gnFlightListDTO.setStartTime(simpleDateFormat.parse(string1));
		gnFlightListDTO.setIbePrice("910");
		gnBookCommand.setFlightGNListDTO(gnFlightListDTO);
		gnBookCommand.setLinkName("苍松");
		gnBookCommand.setLinkEmail("lianxiren@zzpl.com");
		gnBookCommand.setLinkTelephone("18888888888");
		gnBookCommand.setNote("have a good trip");
		GNPassengerDTO gnPassengerDTO = new GNPassengerDTO();
		gnPassengerDTO.setIdNO("239005199206052011");
		gnPassengerDTO.setIdType("0");
		gnPassengerDTO.setPassengerType("1");
		gnPassengerDTO.setTelephone("15343541630");
		gnPassengerDTO.setPassengerName("苍松");
//		GNPassengerDTO gnPassengerDTO1 = new GNPassengerDTO();
//		gnPassengerDTO1.setIdNO("411023198907202515");
//		gnPassengerDTO1.setIdType("0");
//		gnPassengerDTO1.setPassengerType("1");
//		gnPassengerDTO1.setTelephone("18888888881");
//		gnPassengerDTO1.setPassengerName("苍松");
//		GNPassengerDTO gnPassengerDTO2 = new GNPassengerDTO();
//		gnPassengerDTO2.setIdNO("239005199206062011");
//		gnPassengerDTO2.setIdType("0");
//		gnPassengerDTO2.setPassengerType("1");
//		gnPassengerDTO2.setTelephone("18888888882");
//		gnPassengerDTO2.setPassengerName("钱婷");
		
		List<GNPassengerDTO> passengerList = new ArrayList<GNPassengerDTO>();
		passengerList.add(gnPassengerDTO);
//		passengerList.add(gnPassengerDTO1);
//		passengerList.add(gnPassengerDTO2);
		gnBookCommand.setPassengerList(passengerList);
		gnBookCommand.setReimbursementStatus(1);
		
		
		gnBookCommand.setUserID("37056202ee3a4d249ed1aff4990fc585");
		gnBookCommand.setWorkflowID("be89823c4ec3496c841fc1ed7b86658d");
		gnBookCommand.setCurrentStepNO(1);
		gnBookCommand.setNextStepNO(2);
		List<String> strings = new ArrayList<String>();
		strings.add("173485bdf8c2498cb3d66e94e863bed5");
		gnBookCommand.setNextUserIDs(strings);
		gnBookCommand.setCostCenterID("9f5a148253504e6ea448dd7bc53c6dfc");
		ApiRequest request = new ApiRequest("BookGNFlight", "671e97c098e74263a1655ee9f1c50123", gnBookCommand, "1.0");		
		GNBookResponse response = client.send(request,GNBookResponse.class);
		System.out.println(JSON.toJSON(response));

	}
}


/*
GNPassengerDTO gnPassengerDTO3= new GNPassengerDTO();
gnPassengerDTO3.setIdNO("897644198501231657");
gnPassengerDTO3.setIdType("0");
gnPassengerDTO3.setPassengerType("1");
gnPassengerDTO3.setTelephone("18888888883");
gnPassengerDTO3.setPassengerName("孙票量");
GNPassengerDTO gnPassengerDTO4= new GNPassengerDTO();
gnPassengerDTO4.setIdNO("330327198602280310");
gnPassengerDTO4.setIdType("0");
gnPassengerDTO4.setPassengerType("1");
gnPassengerDTO4.setTelephone("18888888884");
gnPassengerDTO4.setPassengerName("周票量");
GNPassengerDTO gnPassengerDTO5= new GNPassengerDTO();
gnPassengerDTO5.setIdNO("331023199102080519");
gnPassengerDTO5.setIdType("0");
gnPassengerDTO5.setPassengerType("1");
gnPassengerDTO5.setTelephone("18888888885");
gnPassengerDTO5.setPassengerName("吴票量");
GNPassengerDTO gnPassengerDTO6= new GNPassengerDTO();
gnPassengerDTO6.setIdNO("430224198502030217");
gnPassengerDTO6.setIdType("0");
gnPassengerDTO6.setPassengerType("1");
gnPassengerDTO6.setTelephone("18888888886");
gnPassengerDTO6.setPassengerName("郑票量");
GNPassengerDTO gnPassengerDTO7= new GNPassengerDTO();
gnPassengerDTO7.setIdNO("14240119911112229x");
gnPassengerDTO7.setIdType("0");
gnPassengerDTO7.setPassengerType("1");
gnPassengerDTO7.setTelephone("18888888887");
gnPassengerDTO7.setPassengerName("王票量");*/
/*passengerList.add(gnPassengerDTO2);
passengerList.add(gnPassengerDTO3);
passengerList.add(gnPassengerDTO4);
passengerList.add(gnPassengerDTO5);
passengerList.add(gnPassengerDTO6);
passengerList.add(gnPassengerDTO7);*/
