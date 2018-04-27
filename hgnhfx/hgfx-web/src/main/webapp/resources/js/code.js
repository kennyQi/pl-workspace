//add by yangjun

var hd={
		hand:'',
		timer:null,
		time:60,
		cycle:1000,
		process:null,
		initTX:'',
		reTX:'',
		isStop:true,
		isQuick:false,
		init:function(handle,process,initTX,reTX,time,isQuick){
			this.time=time;
			$("#code").attr("data-time2",time);
			this.hand=handle;
			this.process=process;
			this.initTX=initTX;
			this.reTX=reTX;
			this.isQuick=isQuick;
			var self=this;
			this.createEvent(function(){
				var text=+self.hand.innerHTML.replace(/\D/g,"");
				if(text>1){
					text-=1;
					self.hand.innerHTML=self.reTX+'('+text+')';
					self.hand.style.cssText+="background-color:#cccccc;border-top: 1px solid #cccccc;"
						+"  border-bottom: 1px solid #cccccc;  border-right: 1px solid #cccccc;";
				}else{
					self.hand.innerHTML=self.reTX;
					self.hand.style.cssText+="background-color:#4799ef;border-top: 1px solid #4799ef;"
						+"  border-bottom: 1px solid #4799ef;  border-right: 1px solid #4799ef;";
				}
			});
		},
		createEvent:function(func){
			var self=this;
			this.hand.onclick=function(e){
				var tempTime=$("#code").attr("data-time");
				$("#code").attr("data-time","");
				if(tempTime){
					self.time=+tempTime;
				}else{
					self.time=$("#code").attr("data-time2");
				}
				
				if(self.hand.innerHTML == '重新获取'){
					self.isQuick=false;
				}
				if(!self.isQuick){
					beforeClickOperate();
					
					if(hd.isStop){
						return;
					}
				}
				
				/*var code_text = $("#code").text();
				if(code_text == "重新获取"){
					beforeClickOperate();
					
					if(hd.isStop){
						return;
					}
				}*/
				
				var tx=this.innerHTML;
				if(tx == self.reTX || tx == self.initTX){
					this.innerHTML=self.reTX+'('+self.time+')';
					self.timeGo(func);
					self.process();
				}
				
				
			};
		},
		timeGo:function(func){
			this.timeStop();
			this.timer=setInterval(func,this.cycle);
		},
		timeStop:function(){
			clearInterval(this.timer);
		}
	};