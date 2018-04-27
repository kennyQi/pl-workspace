import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import hg.fx.command.smsCodeRecord.CreateSmsCodeRecordCommand;
import hg.fx.command.smsCodeRecord.ModifySmsCodeRecordCommand;
import hg.fx.domain.SmsCodeRecord;
import hg.fx.spi.SmsCodeRecordSPI;
import hg.fx.spi.qo.SmsCodeRecordSQO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 下午7:58:48
 * @版本： V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class SmsCodeRecordSPIServiceTest {

	@Autowired
	private SmsCodeRecordSPI smsCodeRecordSPI;

	//@Test
	public void testCreate() {
		CreateSmsCodeRecordCommand command = new CreateSmsCodeRecordCommand();
		command.setCode("123456");
		command.setMobile("18814820543");

		long createDate = System.currentTimeMillis();
		long invalidDate = createDate + 3 * 60 * 1000;

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(createDate);
		command.setCreateDate(c.getTime());
		c.setTimeInMillis(invalidDate);
		command.setInvalidDate(c.getTime());

		command.setType(SmsCodeRecord.TYPE_FORGET_PASSWORD);
//		System.out.println(JSON.toJSONString(smsCodeRecordSPI.create(command)));;
	}
	
	//@Test
	public void testQueryFirst(){
		SmsCodeRecordSQO sqo = new SmsCodeRecordSQO();
		sqo.setMobile("15305153869");
		sqo.setUsed(false);
		sqo.setType(SmsCodeRecord.TYPE_FORGET_PASSWORD);

//		System.out.println(JSON.toJSONString(smsCodeRecordSPI.queryFirst(sqo)));
	}
	
	//@Test
	public void testModifyUsedStatus(){
		ModifySmsCodeRecordCommand command = new ModifySmsCodeRecordCommand();
		command.setId("df729a517a584f89868a8a9025ee39b9");
		
//		System.out.println(JSON.toJSONString(smsCodeRecordSPI.modifyUsedStatus(command)));
	}
	
	@Test
	public void testQueryCount() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date today = new Date();
		Calendar cnew = Calendar.getInstance();
		cnew.setTime(today);
		cnew.add(Calendar.DATE, 1);// 当前天数加一天

		String beginTime = sdf.format(today) + " 00:00:00";// 开始时间
		String endTime = sdf.format(cnew.getTime()) + " 00:00:00";// 结束时间

		SmsCodeRecordSQO sqo = new SmsCodeRecordSQO();
		sqo.setMobile("15305153869");
		sqo.setType(SmsCodeRecord.TYPE_FORGET_PASSWORD);
		sqo.setBeginTime(sdf1.parse(beginTime));
		sqo.setEndTime(sdf1.parse(endTime));
		sqo.setUsed(null);
//		System.out.println(smsCodeRecordSPI.queryCount(sqo));
	}

}
