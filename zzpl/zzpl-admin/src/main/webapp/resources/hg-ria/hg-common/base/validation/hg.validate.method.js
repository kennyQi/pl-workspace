/**
 * @requires jquery.validate.js
 * @author ZhangHuihua@msn.com
 */
(function($){
	if ($.validator) {
		$.validator.addMethod("alphanumeric", function(value, element) {
			return this.optional(element) || /^\w+$/i.test(value);
		}, "Letters, numbers or underscores only please");
		
		$.validator.addMethod("lettersonly", function(value, element) {
			return this.optional(element) || /^[a-z]+$/i.test(value);
		}, "Letters only please"); 
		
		$.validator.addMethod("phone", function(value, element) {
			return this.optional(element) || /^[0-9 \(\)]{7,30}$/.test(value);
		}, "Please specify a valid phone number");
		
		$.validator.addMethod("postcode", function(value, element) {
			return this.optional(element) || /^[0-9 A-Za-z]{5,20}$/.test(value);
		}, "Please specify a valid postcode");
		
		$.validator.addMethod("date", function(value, element) {
			value = value.replace(/\s+/g, "");
			if (String.prototype.parseDate){
				var $input = $(element);
				var pattern = $input.attr('dateFmt') || 'yyyy-MM-dd';
	
				return !$input.val() || $input.val().parseDate(pattern);
			} else {
				return this.optional(element) || value.match(/^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/);
			}
		}, "Please enter a valid date.");
		
		
		$.validator.addMethod("idcard", function(value, element) { 
			var idcheck = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;                 
			return this.optional(element) || idcheck.test(value);           
		}, "身份证格式不正确");
		
		// 判断是否包含中英文特殊字符，除英文"-_"字符外
		$.validator.addMethod("isSpec", function(value, element) {  
	         var reg = RegExp(/[(\ )(\`)(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\+)(\=)(\|)(\{)(\})(\')(\:)(\;)(\')(',)(\[)(\])(\.)(\<)(\>)(\/)(\?)(\~)(\！)(\@)(\#)(\￥)(\%)(\…)(\&)(\*)(\（)(\）)(\-)(\+)(\|)(\{)(\})(\‘)]+/);   
	         return this.optional(element) || !reg.test(value);       
	    }, "Please enter a valid date.");
		
		// 防止代码注入
		$.validator.addMethod("isInject", function(value, element) {  
			 value = value.replace(/[ ]/g, "");
	         var reg = RegExp(/[(\> )(\<)]+/);
	         return this.optional(element) || !reg.test(value);       
	    }, "Please enter a valid date.");

		$.validator.addMethod("tel", function(value, element) { 
			var idcheck = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;                 
			return this.optional(element) || idcheck.test(value);             
		}, "手机号格式不正确");
		
		$.validator.addMethod("chinese", function(value, element) { 
			var idcheck = /^([\u4E00-\uFA29]|[\uE7C7-\uE7F3])*$/;                 
			return this.optional(element) || idcheck.test(value);             
		}, "中文格式不正确");

		/*自定义js函数验证
		 * <input type="text" name="xxx" customvalid="xxxFn(element)" title="xxx" />
		 */
		$.validator.addMethod("customvalid", function(value, element, params) {
			try{
				return eval('(' + params + ')');
			}catch(e){
				return false;
			}
		}, "Please fix this field.");
		
		$.validator.addClassRules({
			date: {date: true},
			alphanumeric: { alphanumeric: true },
			lettersonly: { lettersonly: true },
			phone: { phone: true },
			postcode: {postcode: true}
		});
		$.validator.setDefaults({errorElement:"span"});
		$.validator.autoCreateRanges = true;
		
	}
	
})(jQuery);