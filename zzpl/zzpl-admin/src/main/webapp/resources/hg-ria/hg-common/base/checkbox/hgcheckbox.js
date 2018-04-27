/**
 * @author ZhangHuihua@msn.com
 */
(function($) {
	$.fn.extend({

		checkboxCtrl : function(parent) {
			return this.each(function() {
				
				var $trigger = $(this);
				var group = $trigger.attr("group");
				
				if(!group)
					return false;
				
				$trigger.iCheck({
					checkboxClass: 'icheckbox_square-aero'
				});
				
				$trigger.bind('ifChecked', function(json) {
					var type = 'all';
					$.checkbox.select(group, type, parent);
				});
				
				$trigger.bind('ifUnchecked', function(json) {
					var type = 'none';
					$.checkbox.select(group, type, parent);
				});
				
			});
		}
	});

	$.checkbox = {
		selectAll : function(_name, _parent) {
			this.select(_name, "all", _parent);
		},
		unSelectAll : function(_name, _parent) {
			this.select(_name, "none", _parent);
		},
		selectInvert : function(_name, _parent) {
			this.select(_name, "invert", _parent);
		},
		select : function(_name, _type, _parent) {
			$parent = $(_parent || document);
			$checkboxLi = $parent.find(":checkbox[name='" + _name + "']");
			switch (_type) {
			case "invert":
				$checkboxLi.iCheck('toggle');
				break;
			case "none":
				$checkboxLi.iCheck('uncheck');
				break;
			default:
				$checkboxLi.iCheck('check');
				break;
			}
		}
	};
})(jQuery);
