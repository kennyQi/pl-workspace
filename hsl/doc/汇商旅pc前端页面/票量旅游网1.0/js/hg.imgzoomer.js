/*
作者：陈焕许
修改时间：2013-11-25 16:27
*/

(function ($) {
    $.extend($.fn, {
        HG: function (setting) {
        }
    });

})(jQuery);

function disableSelection(target) {
    if (typeof target.onselectstart != "undefined") //IE route 
        target.onselectstart = function () { return false }
    else if (typeof target.style.MozUserSelect != "undefined") //Firefox route 
        target.style.MozUserSelect = "none"
    else //All other route (ie: Opera) 
        target.onmousedown = function () { return false }
    target.style.cursor = "default"
}

(function ($) {
    $.extend($.fn.HG, {
        Scal: function (setting) {//--Scal开始
            var ps = $.extend({
                renderTo: null, //此插件在哪个元素里面显示
                enable: true, //是否允许拖动
                min: { width: 30, height: 30 }, //缩放最小宽度
                max: { width: 410, height: 250 }, //缩放最小高度
                onChanging: function (e) { }, //
                onChanged: function (e) { }, //
                onMoving: function (e) { }, //
                onStart: function () { }, //
                onEnd: function () { },
                overdrag: true//
            }, setting);
            ps.renderTo = (typeof ps.renderTo == 'string' ? $("#" + ps.renderTo) : ps.renderTo);

            var scalObj = $('<div class="scanl"><a class="pos1"></a> <a class="pos2"></a> <a class="pos3"></a><a class="pos4"></a><a class="pos5"></a><a class="pos6"></a><a class="pos7"></a><a class="pos8"></a></div>');
            
            //if (isIE(6)) {
            //    var js = document.getElementsByTagName("script");
            //    var jsPath = "";

            //    for (var i = 0; i < js.length; i++) {
            //        if (js[i].src.toLowerCase().indexOf("/hg.imgzoomer.js") != -1) {
            //            jsPath = js[i].src.substring(0, js[i].src.lastIndexOf("/") + 1);
            //            break;
            //        }
            //    }    alert(jsPath);
            //   // scalObj.css({ _background: "none", _filter: 'progid:DXImageTransform.Microsoft.AlphaImageLoader(src="' + jsPath + 'img/zoomtm.png")' });
            //   // scalObj.append("<img src='"+jsPath+"img/zoomtm.gif' width=100 height=100 />");
            //}

            scalObj.appendTo(ps.renderTo);

            if (ps.overdrag) {
                scalObj.find("a").hide();
            }
            var _width = 100;
            this.getWidth = function () {
                return _width;
            }
            var _height = 100;
            this.getHeight = function () {
                return _height;
            }

            this.show = function () {
                scalObj.show();
            }

            this.hide = function () {
                scalObj.hide();
            }
            var divMove = {
                drag: function (e) {
                    var d = e.data;
                    var l = Math.min(Math.max(e.pageX - d.pageX + d.left, 0), ps.renderTo.width() - d.width);
                    var t = Math.min(Math.max(e.pageY - d.pageY + d.top, 0), ps.renderTo.height() - d.height);
                    scalObj.css({ left: l + "px", top: t + "px" });
                    //window.document.title = l + "|" + t +"|"+ d.width+"|"+d.height;
                    ps.onMoving({
                        left: l,
                        top: t,
                        width: d.width,
                        height: d.height
                    });
                },
                drop: function (e) {
                    scalObj.css("cursor", "auto");
                    ps.onEnd();
                    $(document).unbind('mousemove', divMove.drag).unbind('mouseup', divMove.drop);
                }
            };
            if (!ps.overdrag) {
                scalObj.bind("mousedown", function (e) {
                    window.document.title = e.pageX + "|" + e.pageY + "|" + scalObj.offset().left;
                    var lll = e.pageX - scalObj.offset().left;
                    var ttt = e.pageY - scalObj.offset().top;
                    var www = parseInt(scalObj.width() / 2);

                    if (!(www - 10 < lll && www + 10 > lll && www - 10 < ttt && www + 10 > ttt)) {
                        return;
                    }

                    ps.onStart();
                    if (e.target.tagName == "A") {
                        return;
                    }
                    $(this).css("cursor", "move");
                    _width = scalObj.width();
                    _height = scalObj.height();
                    var d = {
                        left: parseInt(scalObj.css('left')),
                        top: parseInt(scalObj.css('top')),
                        pageX: e.pageX,
                        pageY: e.pageY,
                        width: _width,
                        height: _height
                    };
                    $(document).bind('mousemove', d, divMove.drag).bind('mouseup', d, divMove.drop);
                });
            }
            this.drag = function (x, y, e) {
                scalObj.css({ top: y, left: x });
                ps.onStart();
                $(this).css("cursor", "move");
                _width = scalObj.width();
                _height = scalObj.height();
                var d = {
                    left: parseInt(scalObj.css('left')),
                    top: parseInt(scalObj.css('top')),
                    pageX: e.pageX,
                    pageY: e.pageY,
                    width: _width,
                    height: _height
                };
                $(document).bind('mousemove', d, divMove.drag);
            }

            this.drop = function () {
                divMove.drop();
            }

            var dowmType = 1;
            var chging = function (e) {
                var d = e.data;
                var w = d.width + (d.pageX - e.pageX);
                var h = d.height + (d.pageY - e.pageY);
                if (dowmType == 3 || dowmType == 4) {
                    w = d.width - (d.pageX - e.pageX);
                }
                if (dowmType == 5) {
                    w = d.width - (d.pageX - e.pageX); h = d.height - (d.pageY - e.pageY);
                }
                if (dowmType == 6 || dowmType == 7) {
                    h = d.height - (d.pageY - e.pageY);
                }

                var newW = Math.min(Math.max(w, ps.min.width), ps.max.width);
                var newH = Math.min(Math.max(h, ps.min.height), ps.max.height);
                var bW = d.width - newW;
                var bH = d.height - newH;
                if (dowmType == 1 || dowmType == 5 || dowmType == 3 || dowmType == 7) {
                    scalObj.width(newW); scalObj.height(newH);
                    scalObj.css({ top: (d.top + parseInt(bH / 2)) + "px", left: parseInt(d.left + bW / 2) + "px" });
                }

                if (dowmType == 2 || dowmType == 6) {
                    scalObj.height(newH);
                    scalObj.css({ top: (d.top + parseInt(bH / 2)) + "px" });
                }

                if (dowmType == 4 || dowmType == 8) {
                    scalObj.width(newW);
                    scalObj.css({ left: (d.left + parseInt(bW / 2)) + "px" });
                }
                _width = scalObj.width();
                _height = scalObj.height();
                ps.onChanging({
                    left: parseInt(scalObj.css('left')),
                    top: parseInt(scalObj.css('top')),

                    width: _width,
                    height: _height
                });
            }

            var chgend = function (e) {
                var d = e.data;

                $(document).unbind('mousemove', chging).unbind('mouseup', chgend);
                _width = scalObj.width();
                _height = scalObj.height();
                ps.onChanged({
                    left: parseInt(scalObj.css('left')),
                    top: parseInt(scalObj.css('top')),

                    width: _width,
                    height: _height
                });
            }

            var aobjs = scalObj.children();
            for (var a = 0; a < aobjs.length; a++) {
                var aObj = $(aobjs[a]);

                aObj.bind('mousedown', a, function (e) {

                    dowmType = e.data + 1; //alert(dowmType);
                    _width = scalObj.width();
                    _height = scalObj.height();
                    var d = {
                        left: parseInt(scalObj.css('left')),
                        top: parseInt(scalObj.css('top')),
                        pageX: e.pageX,
                        pageY: e.pageY,
                        width: _width,
                        height: _height
                    };
                    $(document).bind('mousemove', d, chging).bind('mouseup', d, chgend);
                });
            }

        }//---Scal结束
    });

})(jQuery);

(function ($) {
    $.extend($.fn.HG, {
        imgzoomer: function (container, setting) {

            var getBili = function () {
                var vvv = Math.min(scal.getWidth(), scal.getHeight());
                vvv = Math.round(ps.size.width / vvv);
                return vvv;
            }

            var getLargeImgUrl = function (imgObj) {
                var lSrc = imgObj.attr("imgurl");
                if (lSrc == undefined || lSrc == "") {
                    lSrc = imgObj.attr("alt");
                }
                if (lSrc == undefined || lSrc == "") {
                    lSrc = imgObj.attr("src");
                }
                return lSrc;
            }
          
            var ps = $.extend({
                enable: true, //是否允许拖动
                size: { width: 410, height: 410 }, //开始放大的尺寸,默认通过计算左边原图区域大小
                nailSize: {width:50,height:50},
                overdrag:true//鼠标移动到顶部图片上就显示放大镜效果，否则需要按下鼠标移动
            }, setting);
            container = (typeof container == 'string' ? $("#" + container) : container);

            var cW = container.width();
            var cH = container.height() - 60;
            if (cW > ps.size.width) {
                ps.size.width = cW;
            }
            if (cH > ps.size.height) {
                ps.size.height = cH;
            }

            var isshow = false;//放大镜容器是否显示的状态，若不显示，则显示
            var scal = new $.fn.HG.Scal({
                renderTo: "imgDiv",
                overdrag: ps.overdrag,
                onMoving: function (e) {
                    var lll = e.left;// parseInt(e.left - (ps.size.width - imgSrc[0].offsetWidth) / 2);
                    var ttt = e.top;// parseInt(e.top - (ps.size.height - imgSrc[0].offsetHeight) / 2);
                    var t = ttt * bili;
                    var r = (lll + e.width) * bili;
                    var b = (ttt + e.height) * bili;
                    var l = lll * bili;
                    imgView[0].style.clip = "rect(" + t + "px " + r + "px " + b + "px " + l + "px)";
                    imgView.css({ left: -l + "px", top: -t + "px" });
                    //window.document.title = bili+imgView[0].style.clip;//l + "|" + t + "|" + e.width + "|" + e.height + "--" + bili;
                    if (!isshow) {
                        div3.show();
                    }
                },
                onChanging: function (e) {
                    bili = getBili();
                    var t = e.top * bili;
                    var r = (e.left + e.width) * bili;
                    var b = (e.top + e.height) * bili;
                    var l = e.left * bili;

                    imgView[0].style.clip = "rect(" + t + "px " + r + "px " + b + "px " + l + "px)";
                    imgView.css("left", "-" + l + "px");
                    if (!isshow) {
                        div3.show();
                    }
                },
                onEnd: function () {
                    div3.hide();
                }
				,
                onChanged: function (e) {
                    bili = getBili();
                    div3.hide();
                }
            });

            var bili = 1;//缩放比例，默认比例是 原图与大小与当前显示预览的大小之间的比例，表示放大的图与原图大小一致

            var divs = container.children();

            var div1 = $(divs[0]);//原图片显示区域
            var div2 = $(divs[1]);//图片滚动区域
            var div3 = $(divs[2]);//放大镜显示区域

           

            var imgSrc = div1.find("img");//原IMG对象
            var imgView = div3.find("img");//放大预览的IMG对象
           
            var imgWidth = imgView.width();
            if (imgWidth <= 0) {
                imgWidth = 410;
            }

            if (ps.overdrag) {
                div1.bind("mouseleave", function (e) {
                    div3.hide();
                    scal.drop();
                    scal.hide();
                });

                div1.bind("mouseenter", function (e) {
                    scal.show();
                    scal.drag(e.pageX - div1.offset().left - 50, e.pageY - div1.offset().top - 50, e);
                });
            }

            var div2subs = div2.children();//滚动区域的子节点，依次是0左边按钮 1图标显示DIV，2右边按钮

            div2.thshower({
                nailSize: ps.nailSize, imgClick: function (imgObj) {
                    var lSrc = getLargeImgUrl(imgObj);

                    imgSrc.attr("src", lSrc);
                    imgView.attr("src", lSrc);
                    imgView[0].width = imgSrc[0].offsetWidth * getBili();
                }
            });

            //状态初始化
            div3.css({ width: ps.size.width, height: ps.size.height, left: div1.width() + 5 });
            div3.hide();

            bili = getBili();
            scal.hide();

            if (!ps.overdrag) {
                disableSelection(document.body); //Disable text selection on entire
            }
        }
    });
    ////Sample usages 
    //var js = document.getElementsByTagName("script");
    //var jsPath = "";

    //for (var i = 0; i < js.length; i++) {
    //    if (js[i].src.toLowerCase().indexOf("/hg.imgzoomer.js") != -1) {
    //        jsPath = js[i].src.substring(0, js[i].src.lastIndexOf("/") + 1);
    //        break;
    //    }
    //}

    //var headObj = document.getElementsByTagName("head")[0];
    //var css = document.createElement("link");
    //css.setAttribute("rel", "stylesheet");
    //css.setAttribute("type", "text/css");
    //css.setAttribute("href", jsPath + "imgzoom.css");
    //headObj.appendChild(css);
})(jQuery);