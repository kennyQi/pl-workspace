$(function(){
	
});

/**
itemIndex:父导航index,从0开始
itemSubIndex:子导航index,从0开始
*/
function leftNavSelected(itemIndex,itemSubIndex){
	var allItems=$(".select-item");
	for(var i=0;i<allItems.length;i++){
		var tempItem=allItems.eq(i);
		
		if(tempItem.next() && tempItem.next().hasClass("select-item-sub")){
			tempItem.children().eq(0).css({"background":tempItem.attr("src1"),"background-position":"36px 14px"});
			tempItem.find(".other1").hide();
			tempItem.find(".other2").show();
		
			var tempItem2=tempItem.next().find(".select-item-sub-li");
			tempItem2
			.removeClass("selected")
			.each(function(){
				$(this).find(".light").hide();
				$(this).find(".light2").hide();
			});
			
			tempItem.next().hide();
			tempItem.next().find(".parent-light").hide();
		}
	}
	
	var itemOperate=allItems.eq(itemIndex);
	if(itemOperate.next() && itemOperate.next().hasClass("select-item-sub")){
		itemOperate.children().eq(0).css({"background":itemOperate.attr("src1"),"background-position":"36px 14px"});
		itemOperate.find(".other1").show();
		itemOperate.find(".other2").hide();
		itemOperate.next().find(".select-item-sub-li").eq(itemSubIndex)
		.addClass("selected");
		
		for(var i=0;i<itemSubIndex;i++){
			itemOperate.next().find(".select-item-sub-li").eq(i)
			.find(".light").hide();
			itemOperate.next().find(".select-item-sub-li").eq(i)
			.find(".light2").show();
		}
		itemOperate.next().find(".select-item-sub-li").eq(itemSubIndex)
			.find(".light").show();
			itemOperate.next().show();
			
			itemOperate.next().find(".parent-light").show();
	}
}