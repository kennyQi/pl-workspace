/**
 * 
 * @authors liuyz (you@example.org)
 * @date    2014-08-14 00:19:47
 * @version $Id$
 */

 $(function(){
 	scrollReflash()
 })

 function scrollReflash(){
 	var myScroll,
		pullDownEl, pullDownOffset,
		pullUpEl, pullUpOffset;
	
	//加载函数
	loaded() ;

	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);//监听touchmove

	document.addEventListener('DOMContentLoaded', function () { setTimeout(loaded, 200); }, false);//监听加载

 }

 //下拉刷新
	function pullDownAction () {
		// setTimeout(function () {	// <-- 模拟网络拥塞，从生产中删除setTimeout的！!
			// alert("请忽略下拉刷新操作@@")

			// myScroll.refresh();		// 记得刷新时内容被加载（即：在阿贾克斯完成） (ie: on ajax completion)
		// }, 1000);	// <-- 模拟网络拥塞，从生产中删除setTimeout的！
	}
	//上拉加载更多
	function pullUpAction () {
		//setTimeout(function () {	// <-- 模拟网络拥塞，从生产中删除setTimeout的！
		load(false, false);
		myScroll.refresh();		// 记得刷新时内容被加载（即：在阿贾克斯完成）ie: on ajax completion)
		// }, 1000);	// <-- 模拟网络拥塞，从生产中删除setTimeout的！
	}
	//加载函数
	function loaded() {
		pullDownEl = document.getElementById('pullDown');
		pullDownOffset = pullDownEl.offsetHeight;
		pullUpEl = document.getElementById('pullUp');	
		pullUpOffset = pullUpEl.offsetHeight;
		
		myScroll = new iScroll('wrapper', {
			useTransition: true,   //是否使用CSS变换
			topOffset: pullUpOffset,  //已经滚动的基准值(一般用在拖动刷新)
			vScrollbar:false,  //是否显示垂直滚动条
			onRefresh: function () { //refresh 的回调
				if (pullDownEl.className.match('loading')) {
					pullDownEl.className = '';
					pullDownEl.querySelector('.pullDownLabel').innerHTML = "";
				} else if (pullUpEl.className.match('loading')) {
					pullUpEl.className = '';
					pullUpEl.querySelector('.pullUpLabel').innerHTML = '上滑加载更多...';
				}
			},
			onScrollMove: function () { //内容移动的回调
				if (this.y> 5 && !pullDownEl.className.match('flip')) {//触动刷新
				//this.y 位置y轴的坐标值>5 $$ down的className != flip
					pullDownEl.className = 'flip';
					pullDownEl.querySelector('.pullDownLabel').innerHTML = '';
					this.minScrollY = 0;//设置最小值
				} else if (this.y < 5 && pullDownEl.className.match('flip')) {//取消触动
					pullDownEl.className = '';
					pullDownEl.querySelector('.pullDownLabel').innerHTML = '';
					this.minScrollY = -pullDownOffset;
				} else if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
					pullUpEl.className = 'flip';
					pullUpEl.querySelector('.pullUpLabel').innerHTML = '松开刷新页面...';
					this.maxScrollY = this.maxScrollY;//设置最大值
				} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
					pullUpEl.className = '';
					pullUpEl.querySelector('.pullUpLabel').innerHTML = '上滑加载更多...';
					this.maxScrollY = pullUpOffset;
				}
			},
			onScrollEnd: function () {//在滚动完成后的回调
				if (pullDownEl.className.match('flip')) {//下划
					pullDownEl.className = 'loading';
					pullDownEl.querySelector('.pullDownLabel').innerHTML = '';	
					pullDownAction();	// 执行自定义功能函数 (ajax call?)
				} else if (pullUpEl.className.match('flip')) {//上划
					pullUpEl.className = 'loading';
					pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Loading...';				
					pullUpAction();	// 执行自定义功能函数 (ajax call?)
				}
			}
		});
		
		setTimeout(function () { document.getElementById('wrapper').style.left = '0'; }, 800);
	}

