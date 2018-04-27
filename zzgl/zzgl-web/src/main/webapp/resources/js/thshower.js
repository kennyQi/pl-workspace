/*
作者：陈焕许
修改时间：2014-01-14 13:27  修改插件使之可以配置是否连续左右滚动
*/
(function ($) {
    $.fn.extend({
        thshower: function (options) {
            var opts = $.extend({}, $.fn.thshower.defaults, options);
            var divs = this.children();
            var leftDiv = $(divs[0]);//左滚动按钮A
            var imgDiv = $(divs[1]);//缩略图容器DIV
            var rightDiv = $(divs[2]);//右滚动按钮A
            this.addClass("thshower");
            leftDiv.addClass("is-left");
            rightDiv.addClass("is-right");

            imgDiv.addClass("img-p");
            var imgC = imgDiv.find("div.first");

            if (opts.width) {//由用户自定义本插件的总宽度
                this.width(opts.width);
            }
            var thisWidth = this.width();
            //兼容IE6
            var ie6 = !-[1, ] && !window.XMLHttpRequest;
            if (ie6) {
                imgDiv.width(thisWidth - 24); //alert(imgDiv.width());
            }

            //alert(this.width());
            //绑定作为图标容器的A元素的单击事件
            var bindEventToAImg = function (aobj) {
                aobj.bind("click", aobj, function (e) {
                    var imgObj = e.data.find("img:first");
                    e.data.parent().children().removeClass("sel");
                    e.data.addClass("sel");
                    opts.imgClick(imgObj);
                });
            }

            var setDis = function (aobj, isdis) {
                if (isdis) {
                    aobj.find("span").addClass("dis"); aobj.attr("dis", "true");
                } else {
                    aobj.find("span").removeClass("dis"); aobj.attr("dis", "false");
                }

            }

            var isDis = function (aobj) {
                return aobj.attr("dis") == "true";
            }

            var imgNailWidth = 58;//一张小图占据的宽度，用于一次移动的距离
            var aobjs = imgDiv.children();
            if (aobjs.length > 0) {
                for (var i = 0; i < aobjs.length; i++) {
                    var aobj = $(aobjs[i]);// alert(opts.nailSize);
                    if (opts.nailSize) {
                        var imgObj = aobj.find("img:first");

                        imgObj.attr("width", opts.nailSize.width);
                        imgObj.attr("height", opts.nailSize.height);

                        aobj.width(opts.nailSize.width);
                        aobj.height(opts.nailSize.height);
                    }
                    bindEventToAImg($(aobjs[i]));
                    if (i == 1) {
                        imgNailWidth = $(aobjs[1]).offset().left - $(aobjs[0]).offset().left;// alert($(aobjs[0]).parent().offset().left+"|"+$(aobjs[2]).offset().left + "-" + $(aobjs[1]).offset().left);
                    }
                }

                $(aobjs[0]).trigger("click");
            }
            //兼容火狐
            if (navigator.userAgent.toLowerCase().indexOf('firefox') >= 0) {
                imgNailWidth += 6;
            }

           // setDis(rightDiv, true);//A
            if (imgNailWidth * aobjs.length<= imgDiv.width()) {
                
                setDis(leftDiv, true);//A
                return;
            }

            var imgmovetimer = null;//定时移动对象
            var movelength = 0;//记录图标已经移动了多少距离
            leftDiv.bind("click", imgDiv, function (e) {
                if (movelength > 0) {
                    return;
                }
                if (isDis($(this))) {
                    return;
                }
                setDis(rightDiv, false);
                imgmovetimer = window.setInterval(imgMoveHandler(true), 3);
            });

            //为了连续滚动，当滚动到右边无法继续，需要克隆原数量的图片并记录下当前左滚动位置，当再次到最右边时，跳到这个位置而不用重新克隆元素也可以保证连续左滚动
            var oneSrlLeft = 0;
            var oneSrcRight = 0;
           
            var imgMoveHandler = function (toleft) {
                return function () {
                    var l = 0;
                    if (toleft) {
                        l = imgDiv.scrollLeft() + 1;
                       
                    } else {
                        l = imgDiv.scrollLeft() - 1;
                    }
                    imgDiv.scrollLeft(l); 
                    movelength++; 
                    //document.title = imgDiv.width() % imgNailWidth;// imgDiv[0].scrollWidth - l;
                    if (opts.moveAlway) {
                        if (toleft) {
                            if (imgDiv[0].scrollWidth == imgDiv.width() + l) {
                                if (imgDiv[0].scrollWidth < 2 * imgDiv.width() || imgDiv.attr("clone") != 'yes') {
                                    oneSrcRight = imgDiv[0].scrollWidth;
                                    var oldLen = imgDiv.children().length;
                                    imgDiv.append(imgDiv.html());
                                    imgDiv.attr("clone", "yes");
                                    oneSrlLeft = l; 
                                    var aobjs = imgDiv.children();
                                    if (aobjs.length > 0) {
                                        for (var i = oldLen; i < aobjs.length; i++) {
                                            bindEventToAImg($(aobjs[i]));
                                        }
                                    }

                                } else {
                                    imgDiv.scrollLeft(oneSrlLeft);
                                }
                            }
                        } else {
                            if (l<=0) {
                                if (imgDiv[0].scrollWidth < 2 * imgDiv.width() || imgDiv.attr("clone") != 'yes') {
                                    oneSrlLeft = imgDiv[0].scrollWidth - imgDiv.width(); //alert(oneSrlLeft);
                                    var oldLen = imgDiv.children().length;
                                    imgDiv.append(imgDiv.html());
                                    imgDiv.attr("clone", "yes");
                                   
                                    var aobjs = imgDiv.children();
                                    oneSrcRight = $(aobjs[oldLen]).offset().left - $(aobjs[0]).offset().left;
                                    //imgDiv.scrollLeft(oneSrcRight);
                                    if (aobjs.length > 0) {
                                        for (var i = oldLen; i < aobjs.length; i++) {
                                            bindEventToAImg($(aobjs[i]));
                                        }
                                    }

                                } else {
                                    imgDiv.scrollLeft(oneSrcRight);
                                }
                            }
                        }
                    }
                    if (movelength >= imgNailWidth) {
                        if (toleft) {
                            if (imgDiv[0].scrollWidth <= imgDiv.width() + imgDiv.scrollLeft() && !opts.moveAlway) {
                                setDis(leftDiv, true);
                            }
                        } else {
                            if (l <= 0 && !opts.moveAlway) {
                                setDis(rightDiv, true);
                            }
                        }
                       
                        window.clearInterval(imgmovetimer);
                        movelength = 0;
                    }

                }
            }

            rightDiv.bind("click", imgDiv, function (e) {
                if (movelength > 0) {
                    return;
                }
                if (isDis($(this))) {
                    return;
                }
                setDis(leftDiv, false);
                imgmovetimer = window.setInterval(imgMoveHandler(false), 3);
            });
        }
    });

    //var js = document.getElementsByTagName("script");
    //var jsPath = "";

    //for (var i = 0; i < js.length; i++) {
    //    if (js[i].src.toLowerCase().indexOf("thshower/thshower.js") != -1) {
    //        jsPath = js[i].src.substring(0, js[i].src.lastIndexOf("/") + 1);
    //        break;
    //    }
    //}
    //var headObj = document.getElementsByTagName("head")[0];
    //var css = document.createElement("link");
    //css.setAttribute("rel", "stylesheet");
    //css.setAttribute("type", "text/css");
    //css.setAttribute("href", jsPath + "thshower.css"); //alert(jsPath);
    //headObj.appendChild(css);

})(jQuery);

$.fn.thshower.defaults = { imgClick: function (imgObj) { }, moveAlway: false };
