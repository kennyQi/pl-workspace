/**
 * @author zzb
 */
(function($) {
	
	$.fn.extend({
		
		ajaxTodo:function() {
			return this.each(function() {
				var $this = $(this);
				$this.unbind('click').bind('click', function(event) {

					var url = unescape($this.attr("href"));
					var title = $this.attr("title");
					if (title) {
						HG.confirm('确认', title, function(r) {
							ajaxTodo(url, $this.attr("callback"));
						});
					} else {
						ajaxTodo(url, $this.attr("callback"));
					}
					event.preventDefault();
				});
			});
		},
		
		selectedTodo: function() {
			
			function _getIds(selectedIds) {
				var ids = "";
				var $box = $.hgtab.getTab($.hgtab.getCurrent());
				$box.find("input:checked").filter("[name='"+selectedIds+"']").each(function(i){
					var val = $(this).val();
					ids += i==0 ? val : ","+val;
				});
				return ids;
			}
			return this.each(function() {
				var $this = $(this);
				var selectedIds = $this.attr("rel") || "ids";
				var postType = $this.attr("postType") || "map";

				$this.unbind('click').bind('click', function(){
					var targetType = $this.attr("targetType");
					var ids = _getIds(selectedIds, targetType);
					if (!ids) {
						HG.messager("请选择信息！", "info");
						return false;
					}
					
					var _callback = $this.attr("callback") || hgTabAjaxDone;
					if (! $.isFunction(_callback)) _callback = eval('(' + _callback + ')');
					
					function _doPost(){
						$.ajax({
							type:'POST', url:$this.attr('href'), dataType:'json', cache: false,
							data: function(){
								if (postType == 'map'){
									return $.map(ids.split(','), function(val, i) {
										return {name: selectedIds, value: val};
									})
								} else {
									var _data = {};
									_data[selectedIds] = ids;
									return _data;
								}
							}(),
							success: _callback,
							error: HG.ajaxError
						});
					}
					var title = $this.attr("title");
					if (title) {
						HG.confirm('确认', title, _doPost);
					} else {
						_doPost();
					}
					return false;
				});
				
			});
		}, 
		
		hgJstree: function() {
			// 绑定选中事件
			function _bindSelect($this, _callback) {
				$this.on('select_node.jstree', function(e, data) {
					// HG.debug(data);
					
					if (! $.isFunction(_callback)) _callback = eval('(' + _callback + ')');
					
					_callback(data);
	
					var node = data.node;
					var _ref = $this.attr('data-ref');
					var _refParam = eval('(' + $this.attr('data-ref-param') + ')') || {};
					var _grid = $(_ref).data('grid');
					if (!_grid) return false;

					_grid.load(_refParam);
		        });
			}
			// loaded事件
			function _bindLoaded($this) {
				$this.on('ready.jstree', function(e, data) {
					// $this.jstree("open_all");

					setTimeout(function() {
						var checkVal = $this.data('checkVal').trim();
						var array = checkVal.split(",");
				        for (var i in array) {
				        	var item = array[i];
				        	if (item && item.trim() != "") {
				        		console.info($('#' + item, $this));
				        		$this.jstree('check_node', $('#' + item));
				        	}
				        }
					}, 500);
				});
			}
			// check事件
			function _bindCheck($this) {
				$this.on('check_node.jstree', function(node, selected, event) {
					// 1. 重设选中ids
					var checked = $this.jstree('get_checked');
					var $inpt = $this.data('inpt');
					$inpt.val(checked.join(","));
				});
			}
			// uncheck事件
			function _bindUnCheck($this) {
				$this.on('uncheck_node.jstree', function(node, selected, event) {
					// 1. 重设选中ids
					var checked = $this.jstree('get_checked');
					var $inpt = $this.data('inpt');
					$inpt.val(checked.join(","));
				});
			}
			return this.each(function() {
				
				$.jstree.defaults.checkbox.tie_selection = false;
				// 1. 读取属性
				var $this = $(this);
				
				var tree = $.jstree.reference ($this);
				if(tree) {
					tree.refresh();
					return;
				}
				
				var wholerow  = HG.getBooleanVal($this.attr('data-wholerow'))  || true;
				var checkbox  = HG.getBooleanVal($this.attr('data-checkbox'))  || false;
				var url 	  = $this.attr('data-url') 		 || '';
				var param 	  = $this.attr('data-param') 	 || 'parentId';
				var _callback = $this.attr('data-callback')  || function() {};
				var checkVal  = $this.attr('data-check-val') || '';
				var _name  	  = $this.attr('name') 			 || '';
				// var nodeId 	  = $this.attr('data-nodeId') 	 || 'id';
				var nodeId 	  = 'id';
				
				if (! $.isFunction(_callback)) _callback = eval('(' + _callback + ')');
				
				// 2. 设置默认属性
				var coreOp = {
		            "themes" : {
		                "responsive": false
		            }
				};
				var typeOp = {
		            "default" : {
		                "icon" : "fa fa-folder icon-state-warning icon-lg"
		            },
		            "file" : {
		                "icon" : "fa fa-file icon-state-warning icon-lg"
		            }
				};
				var plugins = ["dnd", "types"];
				
				// 3. 设置属性
				if (wholerow) {
					plugins.push("wholerow");
				}
				if (checkbox) {
					plugins.push("checkbox");
				}
				if (url) {
					coreOp = $.extend(coreOp, {
						'data' : {
			            	'url' : function (node) {
			                	return url;
			                },
			                'data' : function (node) {
			                	var dataOp = {};
			                	dataOp[param] = node[nodeId].trim() 
			                		== "#" ? "" : node[nodeId].trim();
			                	
			                	// HG.debug(dataOp);
			                	return dataOp;
			                }
			            }
					});
				}

				// 4. 绑定控件
				$this.jstree({
					core: coreOp,
					types: typeOp,
					plugins: plugins
				});
				
				// 5. 添加input
				var $inpt = $('<input name="' + _name + '" type="hidden" value="' + checkVal + '"/>');
				$this.after($inpt);
				$this.data('inpt', $inpt);
				$this.data('checkVal', checkVal);

				// 6. 绑定事件
				_bindLoaded ($this);
				_bindSelect	($this, _callback);
				_bindCheck	($this);
				_bindUnCheck($this);
				
			});
		},
		
		imageUploadTemp: function() {
			return this.each(function() {
				var $this = $(this);
				
				if ($this.data('binded'))
					$this.children().remove();
				
				// 显示图片
				var _imageId = $this.attr('value');
				
				var $imgDiv = $('<div class="thumbnail"' +
						'style="width: 200px; height: 150px; margin-bottom: 5px;"></div>');
				
				var src = '';
				if (_imageId)
					src = HG._set.proUrl + "/" + HG._set.imagePriewUrl + "/" + _imageId;
				var $imgPreview = $('<img src="' + src + '" +  />');
				
				$imgDiv.append($imgPreview);
				$this.append($imgDiv);
				
				// 上传btn
				var maxSize = $this.attr('max-size') || '';
				var $uploadBtn = $('<a class="btn grey">上传</a>');
				$this.append($uploadBtn);
				
				// 隐藏input
				var _name = $this.attr('name') || 'imageId';
				var $hidInpt = $('<input type="hidden" name="' + _name + '" value="' 
						+ _imageId + '" class="' + $this.attr('class') + '"/>');
				$this.append($hidInpt);
				
				// 绑定上传组件
				new AjaxUpload($uploadBtn, {
					action : HG._set.proUrl + HG._set.imageUploadTempUrl,
					name : 'file',
					responseType : 'json',
					onSubmit : function(file, ext, fileSize) {
						if (maxSize && (maxSize * 1024) < fileSize) {
							HG.messager(HG.msg("fileTooLar"), "info");
							return false;
						}
						if (!(ext && /^(png|jpg|gif|jpeg|PNG|JPG|GIF|JPEG)$/.test(ext))) {
							HG.messager(HG.msg("fileExtError"), "info");
							return false;
						}
					},
					onComplete : function(file, response, fileSize) {
						
						if ("success" == response.status) {

							$imgPreview.attr("src", response.imageUrl);
							$hidInpt.val(response.imageId);
			    	  	} else {
			    	  		HG.messager(response.msg, "info");
			    	  	}
					}
				});
				
				$this.attr('binded', true);
			});
		},
		
		imageUpload: function() {
			return this.each(function() {
				var $this = $(this);
				
				if ($this.data('binded'))
					$this.children().remove();
				
				// 属性
				var _fileInfo =  $this.attr('file-info');
				var _fileName =  $this.attr('file-name');
				var _imageStaticUrl =  $this.attr('image-static-url');
				var _imageUploadUrl =  $this.attr('image-upload-url') || HG._set.proUrl + HG._set.imageUploadUrl;
				
				var $imgDiv = $('<div class="thumbnail"' +
						'style="width: 200px; height: 150px; margin-bottom: 5px;"></div>');
				
				var src = '';
				if (_fileInfo && _imageStaticUrl) {
					var fileInfoObj = HG.jsonEval(_fileInfo);
					src = _imageStaticUrl + fileInfoObj.uri;
				}
				var $imgPreview = $('<img src="' + src + '" +  />');
				
				$imgDiv.append($imgPreview);
				$this.append($imgDiv);
				
				// 上传btn
				var maxSize = $this.attr('max-size') || '';
				var $uploadBtn = $('<a class="btn grey">上传</a>');
				$this.append($uploadBtn);
				
				// 隐藏input
				var _fileInfoInput = $this.attr('file-info-input') || 'fileInfo';
				var $infoHidInpt = $('<input type="hidden" name="' + _fileInfoInput + '" value="' 
						+ _fileInfo + '" class="' + $this.attr('class') + '"/>');
				$this.append($infoHidInpt);
				var _fileNameInput = $this.attr('file-name-input') || 'fileName';
				var $nameHidInpt = $('<input type="hidden" name="' + _fileNameInput + '" value="' 
						+ _fileName + '" class="' + $this.attr('class') + '"/>');
				$this.append($nameHidInpt);
				
				// 绑定上传组件
				new AjaxUpload($uploadBtn, {
					action : _imageUploadUrl,
					name : 'file',
					responseType : 'json',
					onSubmit : function(file, ext, fileSize) {
						if(maxSize && (maxSize * 1024) < fileSize) {
							HG.messager(HG.msg("fileTooLar"), "info");
							return false;
						}
						if (!(ext && /^(png|jpg|gif|jpeg|PNG|JPG|GIF|JPEG|xls)$/.test(ext))) {
							HG.messager(HG.msg("fileExtError"), "info");
							return false;
						}
					},
					onComplete : function(file, response, fileSize) {
						
						console.info(response);
						if ("success" == response.status) {

							$imgPreview.attr("src", response.imageUrl);
							$infoHidInpt.val(response.fileInfo);
							$nameHidInpt.val(response.imageName);
			    	  	} else {
			    	  		HG.messager(response.msg, "info");
			    	  	}
					}
				});
				
				$this.attr('binded', true);
			});
		},
		
		hgJrop: function() {
			// 缩放比例换算
			function zoomRatioConve(num, zoomRatio) {
				return (Number(num) * zoomRatio).toFixed(0)
			};
	        // input值改变事件
	        function bindInptChange(op, jcrop_api) {
	        	var top 	= zoomRatioConve(op.$top.val(), op.zoomRatio);
	        	var left 	= zoomRatioConve(op.$left.val(), op.zoomRatio);
	        	var width 	= zoomRatioConve(op.$width.val(), op.zoomRatio);
	        	var height 	= zoomRatioConve(op.$height.val(), op.zoomRatio);

	            jcrop_api.setSelect([top, left, left + width, top + height]);
	        };
	        // 创建预览标签
	        function createPrev($prevDiv, $img) {
	        	// 1. 修改样式
	        	$prevDiv.addClass('col-md-6 responsive-1024').css("width", "auto");
	        	
	        	// 2. 插入标签
	        	$prevDiv.append('<div id="preview-pane" style="position: initial;">' + 
						'<div class="preview-container">' + 
						'<img src="' + $img.attr('src') + '" class="jcrop-preview" alt="预览"/>' + 
					'</div>' + 
				'</div>');
	        };
	        // 裁剪change事件
	        function cutChange(op, c) {
	        	// 1. input输入框值
	        	if (op.$top) 		op.$top.val(zoomRatioConve(c.x, op.zoomRatio));
				if (op.$left) 		op.$left.val(zoomRatioConve(c.y, op.zoomRatio));
				if (op.$width)		op.$width.val(zoomRatioConve(c.w, op.zoomRatio));
				if (op.$height) 	op.$height.val(zoomRatioConve(c.h, op.zoomRatio));
				
				// 2. 预览图
				if (op.$pimg) {
					var rx = op.xsize / c.w;
		            var ry = op.ysize / c.h;

		            op.$pimg.css({
		              width: Math.round(rx * boundx) + 'px',
		              height: Math.round(ry * boundy) + 'px',
		              marginLeft: '-' + Math.round(rx * c.x) + 'px',
		              marginTop: '-' + Math.round(ry * c.y) + 'px'
		            });
				}
				
				// 3. 回调
				op.$cutCallback(c);
	        };
	        // 清空数据
	        function release(op, c) {
	        	if (op.$top) 		op.$top.val('');
				if (op.$left) 		op.$left.val('');
				if (op.$width)		op.$width.val('');
				if (op.$height) 	op.$height.val('');
	        };
	        // 设置预览属性
	        function setPrevOp($prev, jcropOp, op) {
	        	var $preview = $('#preview-pane', $prev);
				var $pcnt = $('#preview-pane .preview-container', $prev);
				var $pimg = $('#preview-pane .preview-container img', $prev);
	            
	            var xsize = $pcnt.width();
	            var ysize = $pcnt.height();
				var ratio = xsize / ysize;
				
				jcropOp.aspectRatio = ratio;		// 限定长宽比
				op.xsize = xsize;
				op.ysize = ysize;
				op.$pimg = $pimg;
	        };
	        // 设置组件事件
	        function setEvent(jcropOp, op) {
	        	jcropOp = $.extend(jcropOp, {
					onChange:   function(c) {
						cutChange(op, c);
					},
					onSelect:   function(c) {
						cutChange(op, c);
					},
					onRelease:   function(c) {
						release(op, c);
					}});
	        };
	        // 改变预览图尺寸事件
	        function changePrev(e, curOp) {
	        	// 1. 获取属性
	        	var jcrop_api = e.data.jcrop_api;
	        	var op = e.data.op;
	        	var jcropOp = e.data.jcropOp;
	        	var $prev = op.$prev;
	        	
	        	if (!$prev) return;
	        	
	        	curOp.height = (175 / curOp.width) * curOp.height;
	        	curOp.width = 175;

	        	// 2. 设置样式和属性和事件
	        	var $pcnt = $('#preview-pane .preview-container', $prev);
	        	$pcnt.width(curOp.width).height(curOp.height);
	        	setPrevOp($prev, jcropOp, op);
	        	setEvent(jcropOp, op);
	        	
	        	// 3. 重新设置属性
	        	jcrop_api.setOptions(jcropOp);
	        	
	        };
			return this.each(function() {
				
				// 1. 获取属性
				var $this 	= $(this);
				var $top 	= $($this.attr('x-ref'));
				var $left 	= $($this.attr('y-ref'));
				var $width 	= $($this.attr('w-ref'));
				var $height = $($this.attr('h-ref'));
				var $prev   = $($this.attr('prev-ref'));
				var $cutCallback = $this.attr('cut-callback') || function() {};
				var jcrop_api;
				if (! $.isFunction($cutCallback)) $cutCallback = eval('(' + $cutCallback + ')');
				
				// 2. 设置属性
				var jcropOp = {};
				var op = {$top: $top, $left: $left, $width: $width, $height: $height, $cutCallback: $cutCallback, $prev: $prev};
				// 2.1 预览
				if ($prev) {
					createPrev($prev, $this);
					setPrevOp($prev, jcropOp, op);
				}
				// 2.2 压缩比例
				var zoomRatio = 1;
				var img_url = $this.attr('src');
				var img = new Image();
				img.src = img_url;
				img.onload = function() {
					zoomRatio = img.width / $this.width();
					op.zoomRatio = zoomRatio;
				}
				// 2.3 属性事件
				setEvent(jcropOp, op);

				// 3. 绑定组件
				$this.Jcrop(jcropOp, function() {
		        	// Use the API to get the real image size
		            var bounds = this.getBounds();
		            boundx = bounds[0];
		            boundy = bounds[1];
		            // Store the API in the jcrop_api variable
		            jcrop_api = this;
		        });

				// 4. 绑定input事件
				if ($top) 		$top.change(function(e) {bindInptChange(op, jcrop_api);})
				if ($left) 		$left.change(function(e) {bindInptChange(op, jcrop_api);})
				if ($width)		$width.change(function(e) {bindInptChange(op, jcrop_api);})
				if ($height) 	$height.change(function(e) {bindInptChange(op, jcrop_api);})
				
				// 5. 绑定自定义的改变预览图尺寸事件
				$this.bind('changePrev', {jcrop_api: jcrop_api, op: op, jcropOp: jcropOp}, changePrev);
			});
			
		}
		
	});
})(jQuery);