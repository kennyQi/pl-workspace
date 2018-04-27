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
				// var tabid = $this.attr("rel") || "_blank";
				var tabid = "_blank";
				var url = unescape($this.attr("href"));
				var $curTab = $.hgtab.getCurrent();
				var parTabid = $curTab.data('_tabid');

				$.hgtab.open(url, tabid, title, parTabid);

				event.preventDefault();
				return false;
			});
		}
		
		$this.data('binded', true);
	});
	
	// upload
	$("input[type=file]", $p).each(function() {
		
		var $this = $(this);
		
		var uploadUrl = $this.attr('upload-url');
		var maxSize = $this.attr('max-size') || '';
		var maxHeight = $this.attr('max-height') || '';
		var maxWidth = $this.attr('max-width') || '';
		var imgRef = $this.attr('img-ref');
		var idRef = $this.attr('id-ref');
		
		$this.attr("id", "_imgFile_" + HG.getRandom());
		$('#' + imgRef).addClass("img-polaroid");

		var op = {
	       		maxHeight: maxHeight,
	       		maxWidth: maxWidth
	       	};
		new AjaxUpload($this, {
			action : uploadUrl,
			name : 'file',
			responseType : 'json',
			data : op,
			onSubmit : function(file, ext, fileSize) {
				if(maxSize && maxSize < fileSize) {
					HG.messager("请上传大小不超过" + maxSize / 1024000 + "M的图片！", "info");
					return false;
				}
				if (!(ext && /^(png|jpg|gif|jpeg|PNG|JPG|GIF|JPEG)$/.test(ext))) {
					HG.messager("请上传图片格式文件！", "info");
					return false;
				}
			},
			onComplete : function(file, response, fileSize) {
				
				if ("success" == response.status) {
    	  		
	    	  		$('#' + imgRef).attr("src", response.imageUrl);
	    	  		$('#' + idRef).val(response.imageId);
	    	  	} else {
	    	  		HG.messager(response.msg, "info");
	    	  	}
			}
		});
	});

	// submit
	$("a[target=submit]", $p).each(function() {
		
		var $this = $(this);
		
		if (!$this.data('binded')) {
			$(this).click(function(event) {
				
				console.info($this.attr('disabled'));
				if($this.attr('disabled') != 'disabled') {
					$this.attr('disabled', 'disabled');
					$this.attr('orgBackground', $this.css('background'));
					$this.attr('orgColor', $this.css('color'));
					$this.attr('orgHtml', $this.html());
					$this.css('background', 'rgb(238, 238, 238)');
					$this.css('color', '#000');
					$this.html('保存中...');
					
					setTimeout(function() {
						$this.removeAttr('disabled');
						
						$this.css('background', $this.attr('orgBackground'));
						$this.css('color', $this.attr('orgColor'));
						$this.html($this.attr('orgHtml'));
					}, 1000);
					
					var form = HG.getForm(this);
					
					form.onsubmit();
					event.preventDefault();
				}
			});
		}
		
		$this.data('binded', true);
	});
	
	// cancel
	$("a[target=cancel]", $p).each(function() {
		
		$(this).click(function(event) {
			
			var $curTab = $.hgtab.getCurrent();
			var parTabid = $curTab.data('_parTabid');
			$.hgtab.openByTabid(parTabid);
			event.preventDefault();
		});
		
	});
	
	// radio
	$("input[type=radio][target=radio]", $p).each(function() {
		
		var $this = $(this);
		var $clickBack = $this.attr('data-click') || function(event) {};
		
		if (! $.isFunction($clickBack)) $clickBack = eval('(' + $clickBack + ')');
		
		$this.iCheck({
			radioClass: 'iradio_minimal-blue'
		});
		
		$this.bind('ifClicked', $clickBack);
	});
	
	// checkbox
	$("input[type=checkbox][target=checkbox]", $p).each(function() {
		
		var $this = $(this);
		
		var $clickBack = $this.attr('data-click') || function() {};
		
		if (! $.isFunction($clickBack)) $clickBack = eval('(' + $clickBack + ')');
		
		$this.iCheck({
			checkboxClass: 'icheckbox_minimal-blue'
		});
		
		$this.bind('ifClicked', $clickBack);
	});
	
	// combobox
	$("select[target=combobox]", $p).each(function() {
		
		var $this = $(this);
		var suggesturl = $this.attr('suggesturl');
		var dataName = $this.attr('data-name');
		var dataValue = $this.attr('data-value');
		var dataAutoLoad = $this.attr('data-auto-load') || true;
		var defValue = $this.attr('def-value');
		
		var wrapperClass = $this.attr('wrapperClass') || '';
		
		var dataCascadeId = $this.attr('data-cascade-id');
		
		var $dataOnchange = $this.attr('data-onchange') || function() {};
		
		if (! $.isFunction($dataOnchange)) $dataOnchange = eval('(' + $dataOnchange + ')');
		
		// 1. 声明级联方法
		var onChange = function(selected) {if($dataOnchange) $dataOnchange(selected);};
		if(dataCascadeId && $('#' + dataCascadeId)) { 
			
			var $cascade = $('#' + dataCascadeId);
			var cascadeSuggesturl = $cascade.attr('suggesturl');
			
			onChange = function(selected) {
				
				var newSuggesturl = (HG.fullProperty(cascadeSuggesturl, selected));
				
				$cascade.attr('suggesturl', newSuggesturl);
				$cascade.attr('data-auto-load', true);
				
				initUI($cascade.parent());
				
				if($dataOnchange)
					$dataOnchange(selected);
			}
		}
			
		// 2. 声明绑定方法
		var bindFun = function() {
			
			var oldEasyDrop = $this.data('easyDrop');
			if(oldEasyDrop) {
				oldEasyDrop.easyDropDown('destroy');
			}
			
			if(!$this.html().trim()) 
				$this.html('<option>请选择</option>');
			
			var op = {
				cutOff: 10,
				onChange: onChange
			}
			
			if(wrapperClass)
				op =  $.extend(op, {wrapperClass: wrapperClass});
			var easyDrop = $this.easyDropDown(op);
			
			$this.data('easyDrop', easyDrop);
		}

		// 3. 绑定
		if(suggesturl && dataName && dataValue && suggesturl.isFinishedTm() && dataAutoLoad) {
			// 3.1   查询数据
			$.ajax({
				global:false,
				type:'POST', dataType:"json", url:suggesturl, cache: false,
				success: function(response) {
					
					if (!response) return;
					var html = '';
					$.each(response, function(i) {
						
						html += '<option value="' + this[dataValue] + '"' 
							+ ((this[dataValue] && this[dataValue] == defValue) 
									? 'selected="selected"' : '') + '>'
							+ this[dataName] + '</option>';
						
					});
					$this.html(html);
					
					// 3.2   绑定控件
					bindFun();
				}
			});
		} else {
			// 3.2   绑定控件
			bindFun();
		}
		
	});
	
	
	// 表单验证
	$("form.required-validate", $p).each(function() {
		
		var $form = $(this);
		$form.validate({
			onsubmit: false,
			focusInvalid: false,
			focusCleanup: false,
			errorElement: "span",
			ignore:".ignore",
			invalidHandler: function(form, validator) {
				var errors = validator.numberOfInvalids();
				if (errors) {
					HG.messager('提交数据不完整，' + errors + '个字段有错误，请改正后再提交!', 'info');
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
	
	// editor
	$("script[target=editor]", $p).each(function() {
		
		var $this = $(this);
		var thisId = $this.attr('id');
		var name = $this.attr('name') || 'editorValue';
		var _markId = $this.attr('markId');
		var _useType = $this.attr('useType');
		var _imgIdRef = $this.attr('imgId-ref');
		var _maximumWords = $this.attr('maximumWords') || 1000;

		if (_markId && _useType && _imgIdRef) {
			var editor = UE.getEditor(thisId, {
				elementPathEnabled: false,
				maximumWords: _maximumWords,
				textarea: name,
				_markId: _markId,
				_useType: _useType,
				_imgIdRef: _imgIdRef
			});
			
			$p.data('editorid', thisId);
		}
	});
	
	// ajaxTodo
	if ($.fn.ajaxTodo)  $("a[target=ajaxTodo]", $p).ajaxTodo();
	
	if ($.fn.selectedTodo) $("a[target=selectedTodo]", $p).selectedTodo();

}