<#assign contextPath=springMacroRequestContext.getContextPath() />

<#import "/public/frame/default.html" as dk>
<@dk.head "" >
</@dk.head>

<script >
	function warning1(data,index){
		//$(".login").css({"border":"1px solid #ea5657"});
		var msg = data.split("##");
		var type = -1;
		if(msg.length > 1){
			type = parseInt(msg[1]);
		}
		if(type != -1){
			index = type;
		}
		if(index == 1){
			$("#winAccount").find(".login").css({"border":"1px solid #ea5657"});
			$(".tip-show-a").css("top", "66px");
		} else if(index == 2) {
			$("#winAccount").find(".password").css({"border":"1px solid #ea5657"});
			$(".tip-show-a").css("top","133px");
		}
		$(".tip-show-a").find(".show-text").html(msg[0]);
		$(".tip-show-a").show();
	}
	
	function warningRemove1(){
		$(".login").css({"border":"1px solid #d5d5d5"});
		$(".password").css({"border":"1px solid #d5d5d5"});
		$(".tip-show-a").hide();
	}
	
	function warning2(data,index){
		//$(".login").css({"border":"1px solid #ea5657"});
		var msg = data.split("##");
		var type = -1;
		if(msg.length > 1){
			type = parseInt(msg[1]);
		}
		if(type != -1){
			index = type;
		}
		$(".tip-show-b").css("overflow", "hidden");
		$(".tip-show-b").css("position", "absolute");
		$(".tip-show-b").css("right", "10px");
		$(".tip-show-b").find("img").css("float", "right");
		$(".tip-show-b").find("img").css("width", "14px");
		$(".tip-show-b").find("img").css("margin-left", "10px");
		$(".tip-show-b").find("span").css("margin-top", "-6px");
		$(".tip-show-b").find("span").css("color", "#585858");
		$(".tip-show-b").find("span").css("font-size", "12px");
		$(".tip-show-b").find("span").css("float", "left");
		if(index == 1){
			$(".tip-show-b").css("top", "66px");
			$(".originalPass").css({"border":"1px solid #ea5657"});
		} else if(index == 2) {
			$(".tip-show-b").css("top", "133px");
			$(".newPass").css({"border":"1px solid #ea5657"});
		} else if(index == 3) {
			$(".tip-show-b").css("top", "200px");
			$(".againPass").css({"border":"1px solid #ea5657"});
		}
		$(".tip-show-b").find(".show-text").html(msg[0]);
		$(".tip-show-b").show();
	}
	
	function warningRemove2(){
		$(".originalPass").css({"border":"1px solid #d5d5d5"});
		$(".newPass").css({"border":"1px solid #d5d5d5"});
		$(".againPass").css({"border":"1px solid #d5d5d5"});
		$(".tip-show-b").hide();
	}
	
	$(function(){
		//请输入正确的手机号码提示
		//warning1("hello");
		//请输入正确的手机号码提示取消
		//warningRemove1();
		//短信验证码错误请重新输入提示
		//warning2();
		//短信验证码错误请重新输入提示取消
		//warningRemove2();
		
		
	});
	
	$(function(){
		$(".login-btn").hover(function(){
			$(this).css("background","url(${contextPath}/resources/img/forget-password/next2.png) no-repeat");
		},function(){
			$(this).css("background","url(${contextPath}/resources/img/forget-password/next.png) no-repeat");
		});
	});
	
</script>

<@dk.main "" "accountManage">
	<div class="right-content">
		<div class="right-content-pure">
			<div class="right-content-title">
				<span class="tt">
					<img src="${contextPath}/resources/img/order/wd3.png">
					<span class="tl">个人管理<span>>> 账号管理</span></span>
				</span>
				
			</div>
			
			<div class="wait-mobey-info">
				<div class="wait-mobey-info-wrap">
					<div class="wait-mobey-info-wrap2">
						<div class="wait-mobey-info-wrap2-title">
							积分信息
							<a href="${contextPath}/bill/list"><span class="account-detail">
								<img src="${contextPath}/resources/img/order/click3.png">
								<span>账单明细></span>
							</span></a>
						</div>
						
						<div class="wait-mobey-info-wrap2-content">
							<div class="A-info">
								<div class="A-wrap">
									<span class="A-wrap-info">
										<div class="money1">${user.distributor.reserveInfo.amount!?html}</div>
										<div class="money1-tip">积分余额</div>
										<div class=" money1-tip freeze-money1">(冻结积分 ${user.distributor.reserveInfo.freezeBalance!?html})</div>
									</span>
								</div>
							</div>
							<div class="B-info">
								<div class="B-wrap">
									<span class="B-wrap-info">
										<div class="money1">${user.distributor.reserveInfo.usableBalance!?html}</div>
										<div class="money1-tip">可用积分</div>
										<div id="money2" class=" money1-tip freeze-money1">(可用积分 ${user.distributor.reserveInfo.usableBalance!?html})</div>
									</span>
								</div>
							</div>
							<div class="C-info"></div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="base-info" >
				<p class="base-title">基本信息</p>
				
				<p class="base-infos"><span class="a">主账号</span>:<span class="b">${user.loginName!?html}</span></p>
				<p class="base-infos"><span class="a">运营商姓名</span>:<span class="b">${user.distributor.linkMan!?html}</span></p>
				<p class="base-infos"><span class="a">公司名称</span>:<span class="b">${user.distributor.name!?html}</span></p>
				<p class="base-infos"><span class="a">公司主站</span>:<span class="b">${user.distributor.webSite!?html}</span></p>
			</div>
			<div class="base-info" style="border-bottom:0px;">
				<p class="base-title">账号安全</p>
				<#if userSess?? && userSess.status == 1>
					<p class="base-infos"><span class="a">手机号码</span>:<span class="b">${user.distributor.phone!?html}</span><a href="javascript:showModifyMobileWin();"><span class="c">修改号码></span></a></p>
				</#if>
				<p class="base-infos"><span style="  width: 26%;" class="a"><img src="${contextPath}/resources/img/order/yellow-point.jpg">您可以修改账号密码</span><a href="javascript:showModifyPasswordWin();"><span class="d">修改密码></span></a></p>
			</div>
		</div>
	</div>
	
	
	
	<div id="winP" style="display:none;"></div>
	<div id="winAccount" style="display:none;">
		<div class="win-p" >
			<div class="table-pure">
				<div class="table-pure-content" style="  border-bottom: 1px dashed #d5e2ef; margin-bottom:20px;">修改绑定手机
					<div id="closeWin_1" onclick="hideModifyMobileWin();"><img src="${contextPath}/resources/img/order/close.png"></div>
				</div>
				
				<div class="win-account">
					<div class="win-account-content">
						<div id="user"  class="login">
							<img src="${contextPath}/resources/img/order/tel3.png"><input type="text" id="mobile" placeholder="您的新绑定手机号码">
						</div>
						<div id="name"  class="password" style="  margin: 24px 0 40px 0;"><img style="  width: 21px;" src="${contextPath}/resources/img/order/detail3.png"><input type="text" id="smsCode" placeholder="短信验证码"><a href="javascript:void(0);"><div id="code">获取验证码</div></a></div>
						<a href="javascript:void(0);"><div style="background:url(${contextPath}/resources/img/order/confirm-modify.png) no-repeat;" id="addAccount2"></div></a>
					
						<div class="tip-show-a" ><img src="${contextPath}/resources/img/login-tip/red-warning.png"><span class="show-text">用户名只可由字母、数字和下划线</span></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="modifyAccount" style="display:none;">
		<div class="win-p" >
			<div class="table-pure">
				<div class="table-pure-content" style="  border-bottom: 1px dashed #d5e2ef; margin-bottom:20px;">修改密码
					<div id="closeWin_2" onclick="hideModifyPasswordWin();"><img src="${contextPath}/resources/img/order/close.png"></div>
				</div>
				
				<div class="win-account">
					<div class="win-account-content">
						<div id="user2" class="originalPass"><img src="${contextPath}/resources/img/order/pa.png"><input type="password" id="originalPass" placeholder="请输入原始密码"></div>
						<div id="name2" class="newPass" style="  margin: 24px 0 22px 0;"><img style=" " src="${contextPath}/resources/img/order/pa.png"><input type="password" id="newPass" placeholder="8~20位由字符/数字/符号组成的新密码"></div>
						<div id="name3" class="againPass" style="  margin: 0px 0 32px 0;"><img style="  width: 21px;" src="${contextPath}/resources/img/order/pa-open.png"><input id="againPass" type="password" placeholder="请再次输入您的新密码"></div>
						<div>
							<a href="javascript:void(0);"><div id="pa_ok"></div></a>
							<a href="javascript:void(0);"><div id="pa_cancel"></div></a>
							<div style="clear:both;"></div>
						</div>
					
						<div class="tip-show-b" style="display:none;" ><img src="${contextPath}/resources/img/login-tip/red-warning.png"><span class="show-text">用户名只可由字母、数字和下划线</span></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="modifytip" style="display:none;">
		<div class="win-p" >
			<div class="table-pure">
				<div class="table-pure-content" style="  border-bottom: 1px dashed #d5e2ef; margin-bottom:20px;">修改密码成功
					<div id="closeWin_3" onclick="hideModifySucWin();"><img src="${contextPath}/resources/img/order/close.png"></div>
				</div>
				
				<div class="win-account">
					<div class="win-account-content">
						<div class="middle"><img style="  margin: -30px 0 0 0;" src="${contextPath}/resources/img/order/personal3.png">恭喜您密码修改成功！</div>
					
						<a href="javascript:void(0);"><div id="see"></div></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</@dk.main>


<@dk.end "" >
</@dk.end>

<script src="${contextPath}/resources/js/account-modify-window.js" charset="utf-8"></script>
<script type="text/javascript"  language="javascript" src="${contextPath}/resources/js/code.js"></script>
<script type="text/javascript"  language="javascript" src="${contextPath}/resources/js/account/modify_mob.js?ver=0000002"></script>
<script type="text/javascript">
	var webRoot = "${contextPath}";
	var mob_near = "${_MOB_SESSION_NEAR_OP!?html}";
	var token = '${token}';
</script>
