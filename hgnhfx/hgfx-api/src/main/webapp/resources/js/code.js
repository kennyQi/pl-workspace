//add by yangjun
var hd={
		hand:'',
		timer:null,
		cycle:1000,
		process:null,
		initTX:'',
		reTX:'',
		init:function(handle,process,initTX,reTX){
			this.hand=handle;
			this.process=process;
			this.initTX=initTX;
			this.reTX=reTX;
			var self=this;
			this.createEvent(function(){
				var text=+self.hand.innerHTML.replace(/\D/g,"");
				if(text>1){
					text-=1;
					self.hand.innerHTML=self.reTX+'('+text+')';
				}else{
					self.hand.innerHTML=self.reTX;
				}
			});
		},
		createEvent:function(func){
			var self=this;
			this.hand.onclick=function(){
				var tx=this.innerHTML;
				if(tx == self.reTX || tx == self.initTX){
					this.innerHTML=self.reTX+'(30)';
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