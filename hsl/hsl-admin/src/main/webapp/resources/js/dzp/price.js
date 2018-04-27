Date.prototype.dateDiff = function (interval, objDate2) { var d = this, i = {}, t = d.getTime(), t2 = objDate2.getTime(); i['y'] = objDate2.getFullYear() - d.getFullYear(); i['q'] = i['y'] * 4 + Math.floor(objDate2.getMonth() / 4) - Math.floor(d.getMonth() / 4); i['m'] = i['y'] * 12 + objDate2.getMonth() - d.getMonth(); i['ms'] = objDate2.getTime() - d.getTime(); i['w'] = Math.floor((t2 + 345600000) / (604800000)) - Math.floor((t + 345600000) / (604800000)); i['d'] = Math.floor(t2 / 86400000) - Math.floor(t / 86400000); i['h'] = Math.floor(t2 / 3600000) - Math.floor(t / 3600000); i['n'] = Math.floor(t2 / 60000) - Math.floor(t / 60000); i['s'] = Math.floor(t2 / 1000) - Math.floor(t / 1000); return i[interval]; }

Date.prototype.DateAdd = function (strInterval, Number) { var dtTmp = this; switch (strInterval) { case 's': return new Date(Date.parse(dtTmp) + (1000 * Number)); case 'n': return new Date(Date.parse(dtTmp) + (60000 * Number)); case 'h': return new Date(Date.parse(dtTmp) + (3600000 * Number)); case 'd': return new Date(Date.parse(dtTmp) + (86400000 * Number)); case 'w': return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number)); case 'q': return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number * 3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds()); case 'm': return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds()); case 'y': return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds()); } }

Date.prototype.DateToParse = function () { var d = this; return Date.parse(d.getFullYear() + '/' + (d.getMonth() + 1) + '/' + d.getDate()); }


function CreateCalendar(para, paraJsonName) {
    var c = para.c;
    var y = para.y; //初始年
    var m = para.m; //初始月
    if (arguments.length == 4 && arguments[2] == "pre") {
        //当选择上一月时款年的判断
        if(arguments[3] == 1){
            m =12;
        }else{
            m = arguments[3] - 1;
        }
    }
    else if (arguments.length == 4 && arguments[2] == "next") {
        //当选择下一月时 跨年判断
        if(arguments[3] == 12){
            m = 1;
        }else{
            m =  parseInt(arguments[3]) + parseInt(1);
        }
    }
    else if (arguments.length == 3) {
        m = arguments[2];
    }

    var a = para.a;
    var f = para.f;
    var today = new Date();//获取当前日期
    today = new Date(today.getFullYear(), today.getMonth(), today.getDate());//转换为2014-12-12的格式
    var can = today.getMonth()+1;//保存当前是今年的第几个月份
    if (y == 0 || m == 0) { y = today.getFullYear(); m = today.getMonth() + 1;};//当未填写初始的年月是默认使用当前的年月（测试无用）
    var dmin = a.d1.replace(/-/g, "/"), dmax = a.d2;//获取时间上限和下限，并转换为2014/12/13的格式
    var year =  today.getFullYear();
    if(m < can){
        y = y+1;
    }
    var i1 = 0, i2 = 0, i3 = 0, d2;
    var num = 0;
    var d1 = new Date(dmin),//将起始日期转换为日期变量
        today = today.DateToParse();
    if (Date.parse(d1.getFullYear() + '/' + (d1.getMonth() + 1) + '/1') > Date.parse(new Date(y, m - 1, 1))) {
        y = d1.getFullYear(); m = d1.getMonth() + 1;
    }
    $('#' + c).html('');//初始化容器
    var ca = new Calendar();//定义日历对象
    tmp = '';
    for (var i = 0; i <= f; i++) {
        d1 = new Date(y, m - 1 + i);//获取初始日期
        y = d1.getFullYear();//年
        m = d1.getMonth() + 1;//月

        tmp += '<table cellpadding="0" border="1" width="500px">';
        tmp += '  <tr class="month" >';
        tmp += '    <td colspan="7" style="text-align:left;border:none;"><strong>'+y+'年'+m+'月</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
        tmp += '    <select style="width:100px;height:31px;" onchange="CreateCalendar(' + paraJsonName + ',\'' + paraJsonName + '\',this.value);">';
        tmp += '    <option value="" >--选择日期--</option>';
        var ii = 0;
        for ( var int = 0; int < 12; int++) {//没跨年
            if((can+int) == m){
                tmp += '    <option selected ="selected" value='+(can+int)+'>'+y+'年'+(can+int)+'月</option>';
            }else if((can+int) > 12){//跨年了
                ii++;
                //获取今年的年份
                var today1 = new Date();
                var currentYear = today1.getFullYear();
                year = currentYear+1;
                if(ii == m){
                    tmp += '    <option selected ="selected" value='+ii+'>'+year+'年'+ii+'月</option>';
                }else{
                    tmp += '    <option value='+ii+'>'+year+'年'+ii+'月</option>';
                }
            }else{
                tmp += '    <option value='+(can+int)+'>'+y+'年'+(can+int)+'月</option>';
            }
        };
        tmp += '    </select>';
        tmp += '    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input value="今天"  style="height:31px;width:100px;" type="button" onclick="CreateCalendar(' + paraJsonName + ',\'' + paraJsonName + '\',\'' +can + '\');"/>';
        if (i == 0) {
            i1 = Date.parse(y + '/' + m + '/1');//i1为当前第一个月的日期起始时间
            d1 = new Date(dmin);//alert(d1); 有效时间段的起始时间
            if (Date.parse(d1.getFullYear() + '/' + (d1.getMonth() + 1) + '/1') < i1) {
                //当有效时间段的起始时间比当前的月份小
                d1 = new Date(y, m - 2 - f, 1);
                tmp += '    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input value="上个月"  style="height:31px;width:100px; background-color:red;" type="button" onclick="CreateCalendar(' + paraJsonName + ',\'' + paraJsonName + '\',\'pre\',\'' +m + '\');"/>';
            } else {
                tmp += '    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input value="上个月"  style="height:31px;width:100px;" type="button" />';
            }
        }
        if (i == f) {
            if(m < can){
                y = y+1;
            }

            i1 = Date.parse(y + '/' + m + '/1');
            d1 = new Date(dmax.trim().substring(0,4)+"-"+dmax.trim().substring(4,6)+"-"+dmax.trim().substring(6,8));
            i2 = Date.parse(d1.getFullYear() + '/' + (d1.getMonth() + 1) + '/1');
            //alert("d1:"+d1.DateToParse()+"***i2:"+i2+"****"+i1);
            if (i2 > i1) {
                d1 = new Date(y, Date.parse(new Date(y, m + 1, 1)) > i2 ? m - f : m, 1);
                tmp += '    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input value="下个月"  style="height:31px;width:100px; background-color:red;" type="button" onclick="CreateCalendar(' + paraJsonName + ',\'' + paraJsonName + '\',\'next\',\'' +m + '\');"/>';
            } else {
                tmp += '    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input value="下个月"  style="height:31px;width:100px;" type="button" />';
            }
        }
        tmp += '</tr>';
        tmp += '    </tr>';
        tmp +='<tr></tr>'
        tmp += '    <tr class="week">';
        tmp += '    <th class="weekEnd">星期日</th>';
        tmp += '    <th>星期一</th>';
        tmp += '    <th>星期二</th>';
        tmp += '    <th>星期三</th>';
        tmp += '    <th>星期四</th>';
        tmp += '    <th>星期五</th>';
        tmp += '    <th class="weekEnd">星期六</th>';
        tmp += '  </tr>';

        var maxdays = (new Date(Date.parse(new Date(y, m, 1)) - 86400000)).getDate();  //当前月的天数
        d1 = new Date(y, m - 1); //要显示的日期
        i1 = d1.getDay(); //这个月的第一天是星期几
        for (var j = 1; j <= 6; j++) {
            tmp += '<tr>';
            for (var k = 1; k <= 7; k++) {
                i2 = (j - 1) * 7 + k - i1;
                if (i2 < 1 || i2 > maxdays) {
                    //当当前格子不在本月日期之内时，则为空
                    tmp += '<td class="noNeed" style="background-color:#C7C7C7;"></td>';
                } else {
                    var aa = para.aa;
                    var num = 0;
                    var myobj=eval(aa);
                    i3 = Date.parse(new Date(y, m - 1, i2));//当前格子的日期
                    for(var r1=0;r1<myobj.length;r1++){
                        var saleDate = myobj[r1].saleDate;
                        var years = saleDate.substring(0,4);
                        var months = saleDate.substring(4,6);
                        var days = saleDate.substring(6,8);
                        var time1 = new Date(years+"-"+months+"-"+days);
                        time1 = time1.DateToParse();
                        num = num +1;
                        if(i3>time1 ||i3<time1){
                            if(num < myobj.length){
                                continue;
                            }
                            if(num == myobj.length){
                                tmp += '<td name ='+'dateTd >';
                                tmp += ' <input name = "time" style="display: none;" value =' + y + '-' + m + '-' + i2 + '><p><em class="noc">' + i2 + '</em><br/><br/><br/><br/><input name ="settlementPrice" style="display: none;" /><input type= "text" style="height:40px;width:150px;" disabled="disabled" /></td>';
                            }
                        }else{
                            d1 = new Date(i3);//转换为日期类型
                            var ll = ca.getlf(d1);
                            if (ll == '') {
                                ll = ca.getsf(d1);
                                if (ll == '') {
                                    ll = ca.getst(d1);
                                    if (ll == '') ll = ca.getls(d1)[3];
                                }
                            }
                            tmp += '<td name ='+'dateTd >';
                            if(myobj[r1].salePrice != ""){
                                tmp +='<input name = "time" style="display: none;" value =' + y + '-' + m + '-' + i2 + '><p><em>' + i2 + '</em><br/><br/><br/><br/><input type= "text" name="price" style="height:40px;width:150px;font-size: 20px;" disabled="disabled" value= '+"￥"+Math.round(myobj[r1].salePrice*100)/100+'></p></td>';
                            }else{
                                tmp += ' <input name = "time" style="display: none;" value =' + y + '-' + m + '-' + i2 + '><p><em class="noc">' + i2 + '</em><br/><br/><br/><br/><input name ="price" style="display: none;" /><input type= "text" style="height:40px;width:150px;"  /></td>';
                            }

                            break;
                        }
                    }
                }
            }
            tmp += '</tr>';
        }
        tmp += '</table>';
        //console.log(tmp);
    }
    $('#' + c).append(tmp);
    $('input.date').each(function(){
        var $this=$(this);
        var opts={};
        $this.datepicker(opts);
    });
}

