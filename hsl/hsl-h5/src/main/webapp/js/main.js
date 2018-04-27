/**
 * 
 * @authors Liuyz (you@example.org)
 * @date    2014-09-17 10:16:01
 * @version $Id$
 */
var arr=[];
function overHide(){
	$(".about").each(function(){
		var content=$("p", $(this)).html();
		arr.push(content);
		var $p = $("p", $(this)).eq(0);
		if($p.outerHeight()<160){
			$(this).find("a").hide();
		}else{
			while($p.outerHeight()>160){
				$p.text($p.text().replace(/(\s)*([a-zA-Z0-9]+|\W)(\.\.\.)?$/, "..."));
			}
		}
		return arr;
	});

	$(".about a").click(function(){
		var index=$(this).parent().attr("name");
		$(this).parent().find("p").html(arr[index]);
		$(this).hide();
	})
}