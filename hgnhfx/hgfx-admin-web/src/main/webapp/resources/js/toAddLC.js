
	var imageAdd = {};

	imageAdd.imageUpload = function(me) {

		$.ajaxFileUpload({
			url : path + "/file/imgUpload",
			secureuri : false,
			fileElementId : 'imgFile',
			dataType : 'json',
			data : {
				maxSize : 20480
			},
			success : function(data, status) {
				if ("success" == data.status) {
					$("#imgView").attr("src", data.imageUrl);
					$("#provePathVal").attr("value", data.imageUrl);
					provePathValidate();
				} else {
					$("#provePath").html("");
					alertMessage(data.msg);
				}
			},
			error : function(data, status, e) {
				alertMsg.info("上传失败");
			}
		});
	}