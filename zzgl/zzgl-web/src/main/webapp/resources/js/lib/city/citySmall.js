/* *
 * ---------------------------------------- *
 * 城市选择组件 v1.0
 * Author: VVG
 * QQ: 83816819
 * Mail: mysheller@163.com
 * http://www.cnblogs.com/NNUF/
 * ---------------------------------------- *
 * Date: 2012-07-10
 * ---------------------------------------- *
 * */

/* *
 * 全局空间 VcitySmall
 * */
var  VcitySmall= {};
/* *
 * 静态方法集
 * @name _m
 * */
VcitySmall._m = {
		/* 选择元素 */
		$:function (arg, context) {
			var tagAll, n, eles = [], i, sub = arg.substring(1);
			context = context || document;
			if (typeof arg == 'string') {
				switch (arg.charAt(0)) {
				case '#':
					return document.getElementById(sub);
					break;
				case '.':
					if (context.getElementsByClassName) return context.getElementsByClassName(sub);
					tagAll = VcitySmall._m.$('*', context);
					n = tagAll.length;
					for (i = 0; i < n; i++) {
						if (tagAll[i].className.indexOf(sub) > -1) eles.push(tagAll[i]);
					}
					return eles;
					break;
				default:
					return context.getElementsByTagName(arg);
				break;
				}
			}
		},

		/* 绑定事件 */
		on:function (node, type, handler) {
			node.addEventListener ? node.addEventListener(type, handler, false) : node.attachEvent('on' + type, handler);
		},

		/* 获取事件 */
		getEvent:function(event){
			return event || window.event;
		},

		/* 获取事件目标 */
		getTarget:function(event){
			return event.target || event.srcElement;
		},

		/* 获取元素位置 */
		getPos:function (node) {
			var scrollx = document.documentElement.scrollLeft || document.body.scrollLeft,
			scrollt = document.documentElement.scrollTop || document.body.scrollTop;
			var pos = node.getBoundingClientRect();
			return {top:pos.top + scrollt, right:pos.right + scrollx, bottom:pos.bottom + scrollt, left:pos.left + scrollx }
		},

		/* 添加样式名 */
		addClass:function (c, node) {
			if(!node)return;
			node.className = VcitySmall._m.hasClass(c,node) ? node.className : node.className + ' ' + c ;
		},

		/* 移除样式名 */
		removeClass:function (c, node) {
			var reg = new RegExp("(^|\\s+)" + c + "(\\s+|$)", "g");
			if(!VcitySmall._m.hasClass(c,node))return;
			node.className = reg.test(node.className) ? node.className.replace(reg, '') : node.className;
		},

		/* 是否含有CLASS */
		hasClass:function (c, node) {
			if(!node || !node.className)return false;
			return node.className.indexOf(c)>-1;
		},

		/* 阻止冒泡 */
		stopPropagation:function (event) {
			event = event || window.event;
			event.stopPropagation ? event.stopPropagation() : event.cancelBubble = true;
		},
		/* 去除两端空格 */
		trim:function (str) {
			return str.replace(/^\s+|\s+$/g,'');
		}
};

/* 所有城市数据,可以按照格式自行添加（北京|beijing|bj），前16条为热门城市 */
VcitySmall.hotCity=['北京|beijing|bj','上海|shanghai|sh','广州|guangzhou|gz','深圳|shenzhen|sz','成都|chengdu|cd','杭州|hangzhou|hz','武汉|wuhan|wh','西安|xian|xa','重庆|chongqing|cq','青岛|qingdao|qd','长沙|zhangsha|zs','南京|nanjing|nj','厦门|xiamen|xm','昆明|kunming|km','大连|dalian|dl','天津|tianjin|tj','郑州|zhengzhou|zz','三亚|sanya|sy','济南|jinan|jn','福州|fuzhou|fz'];
VcitySmall.allCity = ['阿勒泰|aletai|alt','安康|ankang|ak','阿克苏|akesu|aks','鞍山|anshan|as','安庆|anqing|aq','安顺|anshun|as','阿拉善左旗|alashanzuoqi|alszq','阿里|ali|al','阿拉善右旗|alashanyouqi|alsyq','阿尔山|aershan|aes','百色|baise|bs','包头|baotou|bt','毕节|bijie|bj','北海|beihai|bh','博乐|bole|bl','保山|baoshan|bs','北京|beijing|bj','巴彦淖尔|bayannaoer|byne','昌都|changdu|cd','常德|changde|cd','长春|zhangchun|zc','朝阳|chaoyang|cy','赤峰|chifeng|cf','长治|zhangzhi|zz','重庆|chongqing|cq','长海|zhanghai|zh','长沙|zhangsha|zs','成都|chengdu|cd','常州|changzhou|cz','池州|chizhou|cz','长白山|zhangbaishan|zbs','大同|datong|dt','达县|daxian|dx','稻城|daocheng|dc','丹东|dandong|dd','迪庆|diqing|dq','大连|dalian|dl','大理市|dalishi|dls','敦煌|dunhuang|dh','东营|dongying|dy','大庆|daqing|dq','德令哈|delingha|dlh','德宏|dehong|dh','鄂尔多斯|eerduosi|eeds','额济纳旗|ejinaqi|ejnq','恩施|enshi|es','二连浩特|erlianhaote|elht','福州|fuzhou|fz','阜阳|fuyang|fy','佛山|foshan|fs','抚远|fuyuan|fy','广州|guangzhou|gz','广汉|guanghan|gh','格尔木|geermu|gem','广元|guangyuan|gy','固原|guyuan|gy','赣州|ganzhou|gz','贵阳|guiyang|gy','桂林|guilin|gl','光化|guanghua|gh','红原|hongyuan|hy','海口|haikou|hk','河池|hechi|hc','邯郸|handan|hd','黑河|heihe|hh','呼和浩特|huhehaote|hhht','合肥|hefei|hf','杭州|hangzhou|hz','淮安|huaian|ha','怀化|huaihua|hh','海拉尔|hailaer|hle','哈密市|hamishi|hms','哈尔滨|haerbin|heb','和田市|hetianshi|hts','汉中|hanzhong|hz','黄山|huangshan|hs','景德镇|jingdezhen|jdz','加格达奇|jiagedaqi|jgdq','嘉峪关|jiayuguan|jyg','井冈山|jinggangshan|jgs','金昌|jinchang|jc','九江|jiujiang|jj','晋江|jinjiang|jj','佳木斯|jiamusi|jms','济宁|jining|jn','锦州|jinzhou|jz','鸡西|jixi|jx','九寨沟|jiuzhaigou|jzg','揭阳|jieyang|jy','济南|jinan|jn','库车|kuche|kc','康定|kangding|kd','喀什市|kashenshi|kss','凯里|kaili|kl','喀纳斯|kanasi|kns','昆明|kunming|km','库尔勒|kuerle|kel','克拉玛依|kelamayi|klmy','黎平|liping|lp','龙岩|longyan|ly','兰州|lanzhou|lz','梁平|liangping|lp','丽江|lijiang|lj','荔波|libo|lb','吕梁|luliang|ll','临沧|lincang|lc','拉萨|lasa|ls','林西|linxi|lx','洛阳|luoyang|ly','连云港|lianyungang|lyg','临沂|linyi|ly','柳州|liuzhou|lz','泸州|luzhou|lz','林芝|linzhi|lz','牡丹江|mudanjiang|mdj','绵阳|mianyang|my','梅州|meizhou|mz','满洲里|manzhouli|mzl','漠河|mohe|mh','南昌|nanchang|nc','南充|nanchong|nc','宁波|ningbo|nb','南京|nanjing|nj','那拉提|neilati|nlt','南宁|nanning|nn','南阳|nanyang|ny','南通|nantong|nt','攀枝花|panzhihua|pzh','普洱|puer|pe','且末|qiemo|qm','庆阳|qingyang|qy','黔江|qianjiang|qj','衢州|quzhou|qz','齐齐哈尔|qiqihaer|qqhe','秦皇岛|qinhuangdao|qhd','青岛|qingdao|qd','日喀则|rikaze|rkz','神农架|shennongjia|snj','上海|shanghai|sh','沈阳|shenyang|sy','沙市|shashi|ss','石家庄|shijiazhuang|sjz','三亚|sanya|sy','深圳|shenzhen|sz','台州|taizhou|tz','塔城|tacheng|tc','腾冲|tengchong|tc','铜仁市|tongrenshi|trs','通辽|tongliao|tl','天水|tianshui|ts','吐鲁番|tulufan|tlf','通化|tonghua|th','天津|tianjin|tj','唐山|tangshan|ts','太原|taiyuan|ty','乌兰浩特|wulanhaote|wlht','乌鲁木齐|wulumuqi|wlmq','潍坊|weifang|wf','威海|weihai|wh','文山县|wenshanxian|wsx','温州|wenzhou|wz','乌海|wuhai|wh','武汉|wuhan|wh','武夷山|wuyishan|wys','无锡|wuxi|wx','梧州|wuzhou|wz','万州|wanzhou|wz','兴义|xingyi|xy','夏河|xiahe|xh','西双版纳|xishuangbanna|xsbn','襄阳|xiangyang|xy','西昌|xichang|xc','锡林浩特|xilinhaote|xlht','西安|xian|xa','厦门|xiamen|xm','西宁|xining|xn','徐州|xuzhou|xz','延安|yanan|ya','银川|yinchuan|yc','伊春|yichun|yc','永州|yongzhou|yz','榆林|yulin|yl','宜宾|yibin|yb','运城|yuncheng|yc','宜春|yichun|yc','宜昌|yichang|yc','伊宁市|yiningshi|yns','义乌|yiwu|yw','延吉|yanji|yj','烟台|yantai|yt','盐城|yancheng|yc','扬州|yangzhou|yz','玉树县|yushuxian|ysx','郑州|zhengzhou|zz','张家界|zhangjiajie|zjj','舟山|zhoushan|zs','张掖|zhangye|zy','昭通|zhaotong|zt','中山|zhongshan|zs','湛江|zhanjiang|zj','中卫|zhongwei|zw','张家口|zhangjiakou|zjk','珠海|zhuhai|zh','遵义|zunyi|zy',
	                  '九华山|jiuhuashan|jhs','井冈山|jinggangshan|jgs'];

/*******************************************************************/


VcitySmall.regEx = /^([\u4E00-\u9FA5\uf900-\ufa2d]+)\|(\w+)\|(\w)\w*$/i;
VcitySmall.regExChiese = /([\u4E00-\u9FA5\uf900-\ufa2d]+)/;

/* *
 * 格式化城市数组为对象oCity，按照a-h,i-p,q-z,hot热门城市分组：
 * {HOT:{hot:[]},ABCDEFGH:{a:[1,2,3],b:[1,2,3]},IJKLMNOP:{i:[1.2.3],j:[1,2,3]},QRSTUVWXYZ:{}}
 * */

(function () {
	/*var citys = VcitySmall.allCity, match, letter,
            regEx = VcitySmall.regEx,
            reg2 = /^[a-h]$/i, reg3 = /^[i-p]$/i, reg4 = /^[q-z]$/i;
    if (!VcitySmall.oCity) {
        VcitySmall.oCity = {hot:{},ABCD:{},EFGH:{}, IJKLP:{},MNOP:{}, QRSTU:{},VWXYZ:{}};
        //console.log(citys.length);
        for (var i = 0, n = citys.length; i < n; i++) {
            match = regEx.exec(citys[i]);
            letter = match[3].toUpperCase();
            if (reg2.test(letter)) {
                if (!VcitySmall.oCity.ABCDEFGH[letter]) VcitySmall.oCity.ABCDEFGH[letter] = [];
                VcitySmall.oCity.ABCDEFGH[letter].push(match[1]);
            } else if (reg3.test(letter)) {
                if (!VcitySmall.oCity.IJKLMNOP[letter]) VcitySmall.oCity.IJKLMNOP[letter] = [];
                VcitySmall.oCity.IJKLMNOP[letter].push(match[1]);
            } else if (reg4.test(letter)) {
                if (!VcitySmall.oCity.QRSTUVWXYZ[letter]) VcitySmall.oCity.QRSTUVWXYZ[letter] = [];
                VcitySmall.oCity.QRSTUVWXYZ[letter].push(match[1]);
            }
	 *//* 热门城市 前16条 *//*
            if(i<16){
                if(!VcitySmall.oCity.hot['hot']) VcitySmall.oCity.hot['hot'] = [];
                VcitySmall.oCity.hot['hot'].push(match[1]);
            }
        }
    }*/

	var citys = VcitySmall.allCity, match, letter,
	regEx = VcitySmall.regEx,
	reg2 = /^[a-d]$/i, reg3 = /^[e-h]$/i, reg4 = /^[i-l]$/i, reg5 = /^[m-p]$/i, reg6 = /^[q-u]$/i, reg7 = /^[v-z]$/i;
	var hots=VcitySmall.hotCity;
	if (!VcitySmall.oCity) {
		VcitySmall.oCity = {hot:{},ABCD:{},EFGH:{}, IJKL:{},MNOP:{}, QRSTU:{},VWXYZ:{}};
		//console.log(citys.length);
		for (var i = 0;i<hots.length;i++) {
			match = regEx.exec(hots[i]);
			if(!VcitySmall.oCity.hot['hot']) VcitySmall.oCity.hot['hot'] = [];
			VcitySmall.oCity.hot['hot'].push(match[1]);
		}
		for (var i = 0, n = citys.length; i < n; i++) {
			match = regEx.exec(citys[i]);
			if( match[3]==null){
				console.erro(match);
			}
			letter = match[3].toUpperCase();
			if (reg2.test(letter)) {
				if (!VcitySmall.oCity.ABCD[letter]) VcitySmall.oCity.ABCD[letter] = [];
				VcitySmall.oCity.ABCD[letter].push(match[1]);
			} else if (reg3.test(letter)) {
				if (!VcitySmall.oCity.EFGH[letter]) VcitySmall.oCity.EFGH[letter] = [];
				VcitySmall.oCity.EFGH[letter].push(match[1]);
			} else if (reg4.test(letter)) {
				if (!VcitySmall.oCity.IJKL[letter]) VcitySmall.oCity.IJKL[letter] = [];
				VcitySmall.oCity.IJKL[letter].push(match[1]);
			}
			else if (reg5.test(letter)) {
				if (!VcitySmall.oCity.MNOP[letter]) VcitySmall.oCity.MNOP[letter] = [];
				VcitySmall.oCity.MNOP[letter].push(match[1]);
			}
			else if (reg6.test(letter)) {
				if (!VcitySmall.oCity.QRSTU[letter]) VcitySmall.oCity.QRSTU[letter] = [];
				VcitySmall.oCity.QRSTU[letter].push(match[1]);
			}
			else if (reg7.test(letter)) {
				if (!VcitySmall.oCity.VWXYZ[letter]) VcitySmall.oCity.VWXYZ[letter] = [];
				VcitySmall.oCity.VWXYZ[letter].push(match[1]);
			}
		}
	}
})();
/* 城市HTML模板 */
VcitySmall._template = [
                   '<p class="tip">热门城市(支持汉字/拼音)</p>',
                   '<ul>',
                   '<li class="on">热门城市</li>',
                   '<li>ABCD</li>',
                   '<li>EFGH</li>',
                   '<li>IJKL</li>',

                   '<li>MNOP</li>',
                   '<li>QRSTU</li>',
                   '<li>VWXYZ</li>',
                   '</ul>'
                   ];

/* *
 * 城市控件构造函数
 * @CitySelector
 * */

VcitySmall.CitySelector = function () {
	this.initialize.apply(this, arguments);
};

VcitySmall.CitySelector.prototype = {

		constructor:VcitySmall.CitySelector,

		/* 初始化 */

		initialize :function (options) {
			var input = options.input;
			this.input = VcitySmall._m.$('#'+ input);
			this.inputEvent();
		},

		/* *
		 * @createWarp
		 * 创建城市BOX HTML 框架
		 * */

		createWarp:function(){
			var inputPos = VcitySmall._m.getPos(this.input);
			var div = this.rootDiv = document.createElement('div');
			var that = this;

			// 设置DIV阻止冒泡
			VcitySmall._m.on(this.rootDiv,'click',function(event){
				VcitySmall._m.stopPropagation(event);
			});

			// 设置点击文档隐藏弹出的城市选择框
			VcitySmall._m.on(document, 'click', function (event) {
				event = VcitySmall._m.getEvent(event);
				var target = VcitySmall._m.getTarget(event);
				if(target == that.input) return false;
				//console.log(target.className);

				if (that.cityBox)VcitySmall._m.addClass('hide', that.cityBox);
				if (that.ul)VcitySmall._m.addClass('hide', that.ul);
				if(that.myIframe)VcitySmall._m.addClass('hide',that.myIframe);
			});
			div.className = 'citySelector';
			div.style.position = 'absolute';
			div.style.left = inputPos.left + 'px';
			div.style.top = inputPos.bottom + 'px';
			div.style.zIndex = 999999;

			// 判断是否IE6，如果是IE6需要添加iframe才能遮住SELECT框
			var isIe = (document.all) ? true : false;
			var isIE6 = this.isIE6 = isIe && !window.XMLHttpRequest;
			if(isIE6){
				var myIframe = this.myIframe =  document.createElement('iframe');
				myIframe.frameborder = '0';
				myIframe.src = 'about:blank';
				myIframe.style.position = 'absolute';
				myIframe.style.zIndex = '-1';
				this.rootDiv.appendChild(this.myIframe);
			}

			var childdiv = this.cityBox = document.createElement('div');
			childdiv.className = 'cityBox';
			childdiv.id = 'cityBox';
			childdiv.innerHTML = VcitySmall._template.join('');
			var hotCity = this.hotCity =  document.createElement('div');
			hotCity.className = 'hotCity';
			childdiv.appendChild(hotCity);
			div.appendChild(childdiv);
			this.createHotCity();
		},

		/* *
		 * @createHotCity
		 * TAB下面DIV：hot,a-h,i-p,q-z 分类HTML生成，DOM操作
		 * {HOT:{hot:[]},ABCDEFGH:{a:[1,2,3],b:[1,2,3]},IJKLMNOP:{},QRSTUVWXYZ:{}}
		 **/

		createHotCity:function(){
			var odiv,odl,odt,odd,odda=[],str,key,ckey,sortKey,regEx = VcitySmall.regEx,
			oCity = VcitySmall.oCity;
			for(key in oCity){
				odiv = this[key] = document.createElement('div');
				// 先设置全部隐藏hide
				odiv.className = key + ' ' + 'cityTab hide';
				sortKey=[];
				for(ckey in oCity[key]){
					sortKey.push(ckey);
					// ckey按照ABCDEDG顺序排序
					sortKey.sort();
				}
				for(var j=0,k = sortKey.length;j<k;j++){
					odl = document.createElement('dl');
					odt = document.createElement('dt');
					odd = document.createElement('dd');
					odt.innerHTML = sortKey[j] == 'hot'?'&nbsp;':sortKey[j];
					odda = [];
					for(var i=0,n=oCity[key][sortKey[j]].length;i<n;i++){
						str = '<a >' + oCity[key][sortKey[j]][i] + '</a>';
						odda.push(str);
					}
					odd.innerHTML = odda.join('');
					odl.appendChild(odt);
					odl.appendChild(odd);
					odiv.appendChild(odl);
				}

				// 移除热门城市的隐藏CSS
				VcitySmall._m.removeClass('hide',this.hot);
				this.hotCity.appendChild(odiv);
			}
			document.body.appendChild(this.rootDiv);
			/* IE6 */
			this.changeIframe();

			this.tabChange();
			this.linkEvent();
		},

		/* *
		 *  tab按字母顺序切换
		 *  @ tabChange
		 * */

		tabChange:function(){
			var lis = VcitySmall._m.$('li',this.cityBox);
			var divs = VcitySmall._m.$('div',this.hotCity);
			var that = this;
			for(var i=0,n=lis.length;i<n;i++){
				lis[i].index = i;
				lis[i].onclick = function(){
					for(var j=0;j<n;j++){
						VcitySmall._m.removeClass('on',lis[j]);

						VcitySmall._m.addClass('hide',divs[j]);
					}
					VcitySmall._m.addClass('on',this);
					VcitySmall._m.removeClass('hide',divs[this.index]);
					/* IE6 改变TAB的时候 改变Iframe 大小*/
					that.changeIframe();
				};
			}
		},

		/* *
		 * 城市LINK事件
		 *  @linkEvent
		 * */

		linkEvent:function(){
			var links = VcitySmall._m.$('a',this.hotCity);
			var that = this;
			for(var i=0,n=links.length;i<n;i++){
				links[i].onclick = function(){
					that.input.value = this.innerHTML;

					VcitySmall._m.addClass('hide',that.cityBox);
					//list页面更改目的地
					if(typeof changeDirect=="function"){
						changeDirect(that.input.value);
					}
					/* 点击城市名的时候隐藏myIframe */
					VcitySmall._m.addClass('hide',that.myIframe);
				}
			}
		},

		/* *
		 * INPUT城市输入框事件
		 * @inputEvent
		 * */

		inputEvent:function(){
			var that = this;
			VcitySmall._m.on(this.input,'click',function(event){
				event = event || window.event;
				if(!that.cityBox){
					that.createWarp();
				}else if(!!that.cityBox && VcitySmall._m.hasClass('hide',that.cityBox)){
					// slideul 不存在或者 slideul存在但是是隐藏的时候 两者不能共存
					if(!that.ul || (that.ul && VcitySmall._m.hasClass('hide',that.ul))){
						VcitySmall._m.removeClass('hide',that.cityBox);

						/* IE6 移除iframe 的hide 样式 */
						//alert('click');
						VcitySmall._m.removeClass('hide',that.myIframe);
						that.changeIframe();
					}
				}
			});
			VcitySmall._m.on(this.input,'focus',function(){
				that.input.select();
				if(that.input.value == '城市名') that.input.value = '';
			});
			VcitySmall._m.on(this.input,'blur',function(){
				if(that.input.value == '') that.input.value = '城市名';
			});
			VcitySmall._m.on(this.input,'keyup',function(event){
				event = event || window.event;
				var keycode = event.keyCode;

				VcitySmall._m.addClass('hide',that.cityBox);
				that.createUl();

				/* 移除iframe 的hide 样式 */
				VcitySmall._m.removeClass('hide',that.myIframe);

				// 下拉菜单显示的时候捕捉按键事件
				if(that.ul && !VcitySmall._m.hasClass('hide',that.ul) && !that.isEmpty){
					that.KeyboardEvent(event,keycode);
				}
			});
		},

		/* *
		 * 生成下拉选择列表
		 * @ createUl
		 * */

		createUl:function () {
			//console.log('createUL');
			var str;
			var value = VcitySmall._m.trim(this.input.value);
			// 当value不等于空的时候执行
			if (value !== '') {
				var reg = new RegExp("^" + value + "|\\|" + value, 'gi');
				// 此处需设置中文输入法也可用onpropertychange
				var searchResult = [];
				for (var i = 0, n = VcitySmall.allCity.length; i < n; i++) {
					if (reg.test(VcitySmall.allCity[i])) {
						var match = VcitySmall.regEx.exec(VcitySmall.allCity[i]);
						if (searchResult.length !== 0) {
							str = '<li><b class="cityname">' + match[1] + '</b><b class="cityspell">' + match[2] + '</b></li>';
						} else {
							str = '<li class="on"><b class="cityname">' + match[1] + '</b><b class="cityspell">' + match[2] + '</b></li>';
						}
						searchResult.push(str);
					}
				}
				/*for (var i = 0, n = VcitySmall.allCity2.length; i < n; i++) {
                if (reg.test(VcitySmall.allCity2[i])) {
                    var match = VcitySmall.regEx.exec(VcitySmall.allCity2[i]);
                    if (searchResult.length !== 0) {
                        str = '<li><b class="cityname">' + match[1] + '</b><b class="cityspell">' + match[2] + '</b></li>';
                    } else {
                        str = '<li class="on"><b class="cityname">' + match[1] + '</b><b class="cityspell">' + match[2] + '</b></li>';
                    }
                    searchResult.push(str);
                }
            }*/
				this.isEmpty = false;
				// 如果搜索数据为空
				if (searchResult.length == 0) {
					this.isEmpty = true;
					str = '<li class="empty">对不起，没有找到数据 "<em>' + value + '</em>"</li>';
					searchResult.push(str);
				}
				// 如果slideul不存在则添加ul
				if (!this.ul) {
					var ul = this.ul = document.createElement('ul');
					ul.className = 'cityslide';
					this.rootDiv && this.rootDiv.appendChild(ul);
					// 记录按键次数，方向键
					this.count = 0;
				} else if (this.ul && VcitySmall._m.hasClass('hide', this.ul)) {
					this.count = 0;
					VcitySmall._m.removeClass('hide', this.ul);
				}
				this.ul.innerHTML = searchResult.join('');

				/* IE6 */
				this.changeIframe();

				// 绑定Li事件
				this.liEvent();
			}else{

				VcitySmall._m.addClass('hide',this.ul);
				VcitySmall._m.removeClass('hide',this.cityBox);

				VcitySmall._m.removeClass('hide',this.myIframe);

				this.changeIframe();
			}
		},

		/* IE6的改变遮罩SELECT 的 IFRAME尺寸大小 */
		changeIframe:function(){
			if(!this.isIE6)return;
			this.myIframe.style.width = this.rootDiv.offsetWidth + 'px';
			this.myIframe.style.height = this.rootDiv.offsetHeight + 'px';
		},

		/* *
		 * 特定键盘事件，上、下、Enter键
		 * @ KeyboardEvent
		 * */

		KeyboardEvent:function(event,keycode){
			var lis = VcitySmall._m.$('li',this.ul);
			var len = lis.length;
			switch(keycode){
			case 40: //向下箭头↓
				this.count++;
				if(this.count > len-1) this.count = 0;
				for(var i=0;i<len;i++){
					VcitySmall._m.removeClass('on',lis[i]);
				}
				VcitySmall._m.addClass('on',lis[this.count]);
				break;
			case 38: //向上箭头↑
				this.count--;
				if(this.count<0) this.count = len-1;
				for(i=0;i<len;i++){
					VcitySmall._m.removeClass('on',lis[i]);
				}
				VcitySmall._m.addClass('on',lis[this.count]);
				break;
			case 13: // enter键
				this.input.value = VcitySmall.regExChiese.exec(lis[this.count].innerHTML)[0];

				VcitySmall._m.addClass('hide',this.ul);
				VcitySmall._m.addClass('hide',this.ul);
				/* IE6 */
				VcitySmall._m.addClass('hide',this.myIframe);
				break;
			default:
				break;
			}
		},

		/* *
		 * 下拉列表的li事件
		 * @ liEvent
		 * */

		liEvent:function(){
			var that = this;
			var lis = VcitySmall._m.$('li',this.ul);
			for(var i = 0,n = lis.length;i < n;i++){
				VcitySmall._m.on(lis[i],'click',function(event){
					event = VcitySmall._m.getEvent(event);
					var target = VcitySmall._m.getTarget(event);
					that.input.value = VcitySmall.regExChiese.exec(target.innerHTML)[0];
					//alert(4)
					VcitySmall._m.addClass('hide',that.ul);
					/* IE6 下拉菜单点击事件 */
					VcitySmall._m.addClass('hide',that.myIframe);
				});
				VcitySmall._m.on(lis[i],'mouseover',function(event){
					event = VcitySmall._m.getEvent(event);
					var target = VcitySmall._m.getTarget(event);
					VcitySmall._m.addClass('on',target);
				});
				VcitySmall._m.on(lis[i],'mouseout',function(event){
					event = VcitySmall._m.getEvent(event);
					var target = VcitySmall._m.getTarget(event);
					VcitySmall._m.removeClass('on',target);
				})
			}

		}
};



