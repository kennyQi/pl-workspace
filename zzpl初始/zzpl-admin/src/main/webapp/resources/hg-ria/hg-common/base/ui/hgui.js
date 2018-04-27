/**
 * 初始化环境
 */
function initEnv() {
	
	if ( $.browser.msie && /6.0/.test(navigator.userAgent) ) {
		try {
			document.execCommand("BackgroundImageCache", false, true);
		}catch(e){}
	}
	//清理浏览器内存,只对IE起效
	if ($.browser.msie) {
		window.setInterval("CollectGarbage();", 10000);
	}
	
	setTimeout(function() {
		
		initUI();
	}, 10);
}

/**
 * 初始化指定区域控件
 */
function initUI(response) {

	var $p = $(response || document);

	// checkboxCtrl 全选/反选
	$(":button.checkboxCtrl, :checkbox.checkboxCtrl", $p).checkboxCtrl($p);
	
	// hgTab
	$("a[target=hgTab], button[target=hgTab]", $p).each(function() {
		
		var $this = $(this);
		if (!$this.data('binded')) {
			$(this).click(function(event) {
				
				var title = $this.attr("title") || $this.text();
				var tabid = $this.attr("rel") || "_blank";
				var url = unescape($this.attr("href"));
				var root = HG.getBooleanVal($this.attr("root")) || false;

				HG.debug(url);
				if (!url.isFinishedTm()) {
					HG.messager($this.attr("warn") || HG.msg("alertSelectMsg"));
					return false;
				}

				$.hgtab.open(url, tabid, title, root);

				event.preventDefault();
				return false;
			});
		}
		
		$this.data('binded', true);
	});
	
	// tree
	$("div[target=tree]", $p).hgJstree();
	
	// grid
	$("tbody[target=grid]", $p).each(function() {
		
		var $this = $(this);
		
		if($this.data('grid')) {
			$this.data('grid').load();
			return;
		}
		
		var _url = $this.attr('data-url');
		var _method = $this.attr('data-method') || 'POST';
		var _param = eval('(' + $this.attr('data-param') + ')') || {};
		var _setData = $this.attr('data-setData') || function() {};
		
		if (! $.isFunction(_setData)) _setData = eval('(' + _setData + ')');
		
		HG.debug(_url);
		if (!_url || !_url.isFinishedTm()) {
			HG.messager($this.attr("warn") || HG.msg("alertSelectMsg"));
			return false;
		}

		var childLength = $this.children().children().length;
		$this.grid({
			url: _url,		// 后台路径
			method: _method,
			params: _param,
			setData: _setData,
			noRecord: '<tr class="odd"><td colspan="' + childLength + '" class="dataTables_empty" >' + HG.msg("noRecord") + '</td></tr>'
		}).data('grid');

	});
	
	// search
	$("a[target=search], button[target=search]", $p).each(function() {
		
		var $this = $(this);
		
		$this.bind('click', function() {
			var _ref = $this.attr('data-ref');
			var _param = eval('(' + $this.attr('data-ref-param') + ')') || {};
			
			var _grid = $(_ref).data('grid');
			if (!_grid) return false;
			
			HG.debug(_param);
			_grid.load(_param);
		});
	});
	
	// dropDown
	$("select[target=dropDown]", $p).each(function() {
		
		var $this = $(this);
		$this.selectpicker();
	});
	
	// radio
	$("input[type=radio][target=radio]", $p).each(function() {
		
		var $this = $(this);
		var $clickBack = $this.attr('data-click') || function(event) {};
		
		if (! $.isFunction($clickBack)) $clickBack = eval('(' + $clickBack + ')');
		
		$this.iCheck({
			radioClass: 'iradio_square-aero'
		});
		
		$this.bind('ifClicked', $clickBack);
	});
	
	// checkbox
	$("input[type=checkbox][target=checkbox]", $p).each(function() {
		
		var $this = $(this);
		
		var $clickBack 	= $this.attr('data-click') 			|| function() {};
		var $checked 	= $this.attr('data-checked') 		|| function() {};
		var $unchecked  = $this.attr('data-unchecked') 		|| function() {};
		
		if (! $.isFunction($clickBack)) $clickBack 		= eval('(' + $clickBack + ')');
		if (! $.isFunction($checked)) $checked 			= eval('(' + $checked + ')');
		if (! $.isFunction($unchecked)) $unchecked 		= eval('(' + $unchecked + ')');
		
		$this.iCheck({
			checkboxClass: 'icheckbox_square-aero'
		});
		
		$this.bind('ifClicked', $clickBack);
		
		$this.bind('ifChecked', $checked);
		
		$this.bind('ifUnchecked', $unchecked);
	});

	// maxlength
	$("input[maxlength], textarea[maxlength]", $p).each(function() {
		
		var $this = $(this);
		
		$this.maxlength({
	        alwaysShow: true,
	        warningClass: "label label-success",
	        limitReachedClass: "label label-danger",
	        validate: true
	    });
	});
	
	// tags
	$("input[target=tags]", $p).each(function() {
		
		var $this = $(this);
		var _width = $this.attr('data-width') || 'auto';
		var $onAddTag = $this.attr('data-on-add') || function() {};
		var $onRemoveTag = $this.attr('data-on-remove') || function() {};
		
		if (! $.isFunction($onAddTag)) $onAddTag = eval('(' + $onAddTag + ')');
		if (! $.isFunction($onRemoveTag)) $onRemoveTag = eval('(' + $onRemoveTag + ')');
		
		$this.tagsInput({
            width: _width,
            'onAddTag': $onAddTag,
            'onRemoveTag': $onRemoveTag,
        });
	});
	
	// 表单验证
	$("form.required-validate", $p).each(function() {
		
		var $form = $(this);
		$form.validate({
			onsubmit: false,
			focusInvalid: false,
			focusCleanup: false,
			errorElement: "span",
			showErrors: function(errorMap, errorList) {
				
				var self = this;
				
				for(var i in errorList) {
					var item = errorList[i];
					$(item.element).parent().addClass('has-error');
				}
				self.defaultShowErrors();
			},
			errorPlacement: function(error, element) {
				error.addClass('help-block help-block-error');
				error.appendTo(element.parent());
			},
			success: function(error, element) {
				$(element).parent().removeClass('has-error');
				
			},
			ignore:".ignore",
			invalidHandler: function(form, validator) {
				var errors = validator.numberOfInvalids();
				if (errors) {
					var message = HG.msg("validateFormError", [errors]);
					HG.messager(message, 'info');
				} 
			}
		});

		$form.find('input[customvalid]').each(function(){
			var $input = $(this);
			$input.rules("add", {
				customvalid: $input.attr("customvalid")
			})
		});
	});
	
	// datepicker
	$("input[target=datepicker],div[target=datepicker]", $p).each(function() {
		
		var $this = $(this);
		// $(this).attr('readonly', 'readonly');
		var _format = $this.attr('data-date-format');
		if(!_format)
			$this.attr('data-date-format', 'yyyy-mm-dd');
		
		if($this.is('div'))
			$this.addClass('input-daterange');
		$(this).datepicker({
            rtl: Metronic.isRTL(),
            orientation: "left",
            autoclose: true
        });
	});
	
	// timepicker
	$("input[target=timepicker]", $p).each(function() {
		
		var $this = $(this);
		
		$this.timepicker({
	        autoclose: true
	    });
	});
	
	// spinner
	$("div[target=spinner]", $p).each(function() {
		
		var $this = $(this);
		var _disabled = HG.getBooleanVal($this.attr('data-disabled')) || false;
		var _value = $this.attr('data-value') || 0;
		var _step = $this.attr('data-step') || 5;
		
		var _min = $this.attr('data-min');
		var _max = $this.attr('data-max');
		
		var op = {disabled: _disabled, value:_value, step: _step};
		if(_min) op = $.extend(op, {min: _min});
		if(_max) op = $.extend(op, {max: _max});
		
		$this.spinner(op);
	});
	
	// imageUpload
	$("div[target=imageUpload]", $p).imageUpload();
	
	// imageUploadTemp
	$("div[target=imageUploadTemp]", $p).imageUploadTemp();
	
	// mixitup
	$("div[target=mixitup]", $p).mixitup();
	
	// fancybox
	$("a[target=fancybox]", $p).each(function() {
		$(".fancybox-button").fancybox({
	        groupAttr: 'data-rel',
	        prevEffect: 'none',
	        nextEffect: 'none',
	        closeBtn: true,
	        helpers: {
	            title: {
	                type: 'inside'
	            }
	        }
	    });
	});
	
	// jcrop
	$("img[target=jcrop]", $p).hgJrop();

	// editor
	$("script[target=editor]", $p).each(function() {
		
		var $this 		= $(this);
		var thisId 		= $this.attr('id');
		var name 		= $this.attr('name') || 'content';
		var _markId 	= $this.attr('markId');
		var _useType 	= $this.attr('useType');
		var _imgIdRef 	= $this.attr('imgIdRef');

		if (_markId && _useType && _imgIdRef) {
			var editor = UE.getEditor(thisId, {
				elementPathEnabled: false,
				textarea: name,
				_markId: _markId,
				_useType: _useType,
				_imgIdRef: _imgIdRef
			});
			
			$p.data('editorid', thisId);
		}
	});

	// submit
	$("a[target=submit]", $p).each(function() {
		
		var $this = $(this);
		
		if (!$this.data('binded')) {
			$(this).click(function(event) {
				
				var form = HG.getForm(this);
				
				form.onsubmit();
				event.preventDefault();
			});
		}

		$this.data('binded', true);
	});
	
	// cancel
	$("a[target=cancel]", $p).each(function() {
		
		$(this).click(function(event) {
			
			$.hgtab.back();
			event.preventDefault();
		});
	});

	// ajaxTodo
	if ($.fn.ajaxTodo)  $("a[target=ajaxTodo]", $p).ajaxTodo();
	
	if ($.fn.selectedTodo) $("a[target=selectedTodo]", $p).selectedTodo();

}