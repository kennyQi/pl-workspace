
$(function(){
	var cr=$("#container");
	var h1=$("#head1");
	var h2=$("#head2");
	var mk=$("#mk");
	var sl=$("#slideDown");
	function regScroll(){var i=0,h=0,o="",c=h1.offset().top,n=0,s=!1;cr.bind("scroll",function(){var l=cr.scrollTop(),r="up";return l>c?(h1.css("visibility","hidden"),h2.show()):(h1.css("visibility","visible"),h2.hide()),r=l>i?"up":"down",i=l,Math.abs(c-l)<20,0==h&&"up"==r&&c>l&&l>0&&0==n?(cr.animate({scrollTop:c+"px"},1e3,function(){mk.hide()}),h=1,mk.show()):1==h&&"down"==r&&c>l&&(n==c||s&&h2.is(":hidden"))&&(cr.animate({scrollTop:"0px"},1e3,function(){mk.hide()}),h=0,mk.show()),o=r,n=l,s=!h2.is(":hidden"),!1})}var cr=$("#container"),h1=$("#head1"),h2=$("#head2"),mk=$("#mk"),sl=$("#slideDown");regScroll(),h2.width(h1.width()),sl.click(function(){cr.scrollTop(100)});
	
	
});
