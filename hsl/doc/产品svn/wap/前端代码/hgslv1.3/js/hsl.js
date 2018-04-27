String.prototype.trim= function(){  
    return this.replace(/(^\s*)|(\s*$)/g, "");  
};

String.prototype.startsWith = function(s){
	return (this.match("^" + s) == s);
};

String.prototype.endsWith = function(s) {
	return (this.match(s + "$") == s);
};

var JP = new function() {
	return {
		getClassType : function(classCode) {
			var result = "经济舱";
			if (classCode.startsWith('F')) { 
				result = '头等舱'; 
			} else if (classCode.startsWith('C')) { 
				result = '公务舱'; 
			}
			return result;
		},
		getJPNumber : function(num) {
			if (num == parseInt(num)) {
				if (num > 9) {
					return ">9";
				} else {
					return num;
				}
			} else {
				return ">9";
			}
		}
	};
};

var JPO = new function() {
	return {
		getStatus : function(status) {
			var result = status;
			if (status == '1') {
				result = '待提交';
			} else if (status == '2') {
				result = '订单关闭';
			} else if (status == '3') {
				result = '待支付';
			} else if (status == '4') {
				result = '出票失败待退款';
			} else if (status == '5') {
				result = '出票失败已退款';
			} else if (status == '6') {
				result = '';
			} else if (status == '7') {
				result = '申请废票';
			} else if (status == '8') {
				result = '废票成功';
			} else if (status == '9') {
				result = '废票成功待退款';
			} else if (status == '10') {
				result = '废票成功已退款';
			} else if (status == '11') {
				result = '已出票(废票失败)';
			} else if (status == '12') {
				result = '申请退票';
			} else if (status == '13') {
				result = '退票成功';
			} else if (status == '14') {
				result = '退票成功待退款';
			} else if (status == '15') {
				result = '退票成功已退款';
			} else if (status == '16') {
				result = '已出票(退票失败)';
			} else if (status == '17') {
				result = '订单取消';
			} else if (status == '18') {
				result = '支付成功';
			} else if (status == '19') {
				result = '收款超时';
			} else if (status == '20') {
				result = '扣款失败';
			} else if (status == '21') {
				result = '下单失败';
			} else if (status == '22') {
				result = '出票成功';
			} else if (status == '23') {
				result = '申请出票成功';
			}
			return result;
		}
	};
};