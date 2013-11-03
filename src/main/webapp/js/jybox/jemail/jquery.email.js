

;
(function($){
	// 默认配置
	var settings = {
		minChars : 1 ,
		maxItems : 100 ,
		subBox : 'email_auto_warp' ,
		subOp : 'dd' ,
		hoverClass : 'on' ,
		selectedColor : '#E6ECF2' ,
		defaultItems : ['@qq.com' , '@163.com' , '@sina.com' , '@126.com' , '@gmail.com' , '@sohu.com' ,  '@hotmail.com']
	};
	$.fn.emailAutoCompele = function(options){
		var config = $.extend(settings,options);
		var items = config.defaultItems;
		var $this = $(this);
		var _oWidth = $this.outerWidth();
		var _oHeight = $this.outerHeight();
		var _oLeft = $this.offset().left;
        var _oTop = $this.offset().top;
		if($this.is(':input') && $this.attr('type')==='text'){
			var warp_div_html = "<div id='"+config.subBox+"'>";
			var warp_head_html = "<{1} style='min-width: "+_oWidth+"px; '></{1}>";
			var warp_head = 'dl';
			if(config.subOp === 'li')
				warp_head = 'ul';
			warp_head_html = warp_head_html.replace('{1}',warp_head); 
			$this.after(warp_div_html+warp_head_html+"</div>");
			//键盘释放事件
			$this.keyup(function(e){
				if(e.keyCode==40 || e.keyCode==38){
					return false;
				}
				var warp_object = $("#"+config.subBox);
				var cur_value = this.value;
				if(this.value.replace(/(^\s*)(\s*$)/g,'') != ''){
					if(e.keyCode!=13 && e.keyCode!=27){
						warp_object.css({
							"left":_oLeft,
							"top":_oTop+_oHeight,
							"display":"block"
						});
						var warp_item_html = "<"+config.subOp+" id='e_type'>请选择邮件类型:</"+config.subOp+">";
						warp_item_html += "<"+config.subOp+" class='on'>"+cur_value+"</"+config.subOp+">";
						var _item = "<"+config.subOp+">{1}</"+config.subOp+">";
						var _e = cur_value.indexOf('@');
						if(_e >= 0){
							var _sh=cur_value.substring(0,_e)
							var _se=cur_value.substring(_e);
							$.each(config.defaultItems, function (s,m) {
								if(s+1>config.maxItems)
									return false;
								if(m.indexOf(_se)!=-1){
									warp_item_html += _item.replace('{1}', _sh + m);
								}
							})
						}else{
							for(var i = 0;i<items.length;i++){
								if(i+1>config.maxItems)
									break;
								warp_item_html += _item.replace('{1}',cur_value+items[i]);
							}
						}
						warp_object.find(warp_head).html(warp_item_html);
						//获取焦点
						$this.focus(function(e){
							if(warp_object.is(':hidden'))
								warp_object.show();
						});
						//项鼠标悬浮
						warp_object.find(config.subOp).hover(function(){
							console.log('dd--入');
							if(this.id === 'e_type')
								return;
							var _that=$(this);
							_that.addClass(config.hoverClass)					   
						},function(){
							console.log('dd--出');
							var _that=$(this);
							_that.removeClass(config.hoverClass)			
						});
						warp_object.hover(function(){
							console.log('div--入');
						},function(){
							console.log('div--出');
						});
						//项鼠标点击
						warp_object.find(config.subOp).click(function(){
							if(this.id === 'e_type')
								return;
							var _that = $(this);
							$this.val(_that.text());
						});
						//鼠标点击
						document.onclick = function(event){
							var e = window.event || event;  
							var k = e.keyCode || e.which;
							var obj = e.srcElement ? e.srcElement : e.target;
							var id = obj.getAttribute('id');
							if(id === 'e_type')
								return;
							if(id != $this.attr('id')){
								warp_object.hide();
								if (e && e.stopPropagation)
									e.stopPropagation()
								else
									window.event.cancelBubble=true
							}
						}
						//键盘触发
						document.onkeydown = function(evnet){
							var e = window.event || event;  
							var k = e.keyCode || e.which;
							var item_lenght = warp_object.find(config.subOp).length;
							var cur_item = warp_object.find(config.subOp+"[class="+config.hoverClass+"]");
							var cur_index = cur_item.index();
							var _cur_index = cur_index;
							switch(k){
								case 40://下键
									if(cur_index == item_lenght)
										_cur_index = 1;
									else
										_cur_index += 1;
									break;
								case 38://上键
									if(cur_index == 1)
										_cur_index -= 1;
									else
										_cur_index = item_lenght;
									break;
								case 9://tab键关闭联想框
								warp_object.hide();
									break;
								default:
									break;
							}
						}
					}
				}else{
					warp_object.hide();
				}
			});
		}else{
			if(console && console.log)
				console.log('this ojbect is not support!');
		}
	};
})(jQuery);