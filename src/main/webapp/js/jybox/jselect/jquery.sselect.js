/*
 * search select box
 * 2012-08-20 by jy.hu
 */
;
(function($){

_isFunction = function(){
	return this && (typeof this).toLowerCase() === "function" && this.constructor === Function;
};
// bubbling phase
_bubble = function(e){
		if (e && e.stopPropagation){
			e.preventDefault();
			e.stopPropagation();
		}else{
			window.event.cancelBubble = true;
		}
};
	$.fn.sselect = function(options){
		var $this = $(this);
		var config = $.extend({
		datas : [] , // 数据(需预先装载)
		dataNameKey : 'name',
		dataValueKey : 'id',
		valBox : '' , //存放选中项的值的容器
		subBox : 'select_warp' , // 存放数据展示容器
		subOp : 'dd' , // 列表显示
		hoverClass : 'on' , // 鼠标悬浮样式
		highlightColor : 'red',// 搜索高亮颜色
		maxHeight : '200px',
		warnMsg : '请选择一个:',//提示语
		preVal : "",
		defalutHtm :"",
		init:true,
		displayClose:false,
		initShowCount:20,
		iteratStep:2,
		itemCallBack : null,
		deleteCallBack : null
	},options);
		config.iteratIndex = config.initShowCount;
		var _bankHtml = undefined;
		var items = config.defaultItems;

		var _oWidth = $this.outerWidth();
		var _oHeight = $this.outerHeight();
		var _oLeft = $this.offset().left;
        var _oTop = $this.offset().top;
		var _cur_index_ = 1;
		config.defalutHtm = initSelectHtm();

		if($this.is(':input') && $this.attr('type')==='text'){
			var warp_object = $("#"+config.subBox);
			if(warp_object && warp_object.length > 0)
					warp_object.remove();
			var warp_div_html = "<div style='display:none;padding:0;overflow-y:scroll;overflow-x: hidden;height:"+config.maxHeight+";left:"+_oLeft+"px;top:"+(_oTop+_oHeight)+"px;' class='sselect' id='"+config.subBox+"'>";
			var warp_head_html = "<{1} style='min-width: "+(_oWidth-15)+"px;*width: "+_oWidth+"px; '></{1}>";
			var warp_head = 'dl';
			if(config.subOp === 'li')
				warp_head = 'ul';
			warp_head_html = warp_head_html.replace(/\{1\}/igm,warp_head); 
			if(config.displayClose){
				var input_delete_html = $('#'+config.subBox+'del');
				if(!(input_delete_html && input_delete_html.length > 0))
					input_delete_html = $("<a href='javascript:;' node-type='clear' id='"+config.subBox+"del' class='sselect_close' style='display:none'></a>");
				input_delete_html.css({
					top  : _oTop+3,
					left : _oLeft+_oWidth-18
				});
				input_delete_html.click(function(e){
					$this.val('');
					$('#'+config.valBox).val('');
					$(this).hide();
					if(_isFunction.call(config.deleteCallBack,null))
						config.deleteCallBack.call(this,arguments);
					config.init = true;
					warp_object.html($(warp_head_html).append(buildItem('')));
					bindItem(warp_object);
					bindEvent();
				});
				$this.after(input_delete_html);
			}
			$this.after(warp_div_html+warp_head_html+"</div>");
			warp_object = $("#"+config.subBox);
			warp_object.find(warp_head).html(buildItem($this.val()));
			bindItem(warp_object);
			bindEvent();
			
		}else{
			if(console && console.log)
				console.log('this ojbect is not support!');
		}
		function bindEvent(){
			//获取焦点
			$this.focus(function(e){
				$(".sselect").hide();
				if(config.displayClose){
					$('#'+config.subBox+'del').hide();
				}
				_oHeight = $this.outerHeight();
				 _oLeft = $this.offset().left;
				_oTop = $this.offset().top;
				warp_object.css({
					"left":_oLeft,
					"top":_oTop+_oHeight
				});
			});
			$this.click(function(e){
				warp_object.show();
				_bubble.call(this,e);
			});
			if(config.displayClose){
				$this.blur(function(e){
					if(this.value.length > 0)
						$('#'+config.subBox+'del').show();
				});
			}
			//鼠标点击
			$(document.body).click(function(event){
			
				var e = window.event || event;
				var obj = e.srcElement ? e.srcElement : e.target;
				var id = obj.getAttribute('id');
				if(id === 'e_type')
					return;
				var _del = null;
					
				$.each($(".sselect"),function(index,item){
					var _that = $(item);
					if(!_that.is(":hidden")){
						if(config.displayClose){
							_del  =$('#'+_that.attr('id')+'del')
							if(_del && _del.length > 0 && _del.is(":hidden") && _that.prev().val().length > 0){
								_del.show();
							}
						}
						_that.hide();
					}
				});
			});
			//项鼠标点击
			warp_object.find(config.subOp).live('click',function(){
				if(this.id === 'e_type')
					return;
				var _that = $(this);
				$this.val(_that.text());
				$('#'+config.valBox).val(_that.attr('original'));
				if(config.displayClose)
					$('#'+config.subBox+'del').show();
				warp_object.hide();
				if(_isFunction.call(config.itemCallBack,null))
					config.itemCallBack.call(_that,arguments);
			});
			//键盘触发
			document.onkeydown  = function(event){
				var e = window.event || event;  
				var k = e.keyCode || e.which;
				var item_object = warp_object.find(config.subOp);
				var item_lenght = item_object.length-1;
				var cur_item = warp_object.find(config.subOp+"[class="+config.hoverClass+"]");
				var cur_index = $("#"+config.subBox+" "+config.subOp).index(cur_item);
				var _cur_index = cur_index < 0 ? 0 : cur_index;
				var _sign = false;
				switch(k){
					case 40://下键
						if(cur_index == item_lenght){
							_cur_index = 1;
							warp_object.scrollTop(0);
						}else{
							_cur_index += 1;
							//warp_object[0].scrollTop = warp_object[0].scrollTop+100;
						}
						_sign =true;
					break;
					case 38://上键
						if(cur_index == 1){
							_cur_index = item_lenght;
							warp_object.scrollTop(warp_object[0].scrollHeight);
						}else{
							_cur_index -= 1;
							//warp_object.scrollTop(warp_object.scrollTop() - 25);
						}
						_sign =true;
					break;
					case 9://tab键关闭联想框
						warp_object.hide();
						_sign=false;
						break;
					case 13:
						$this.val(cur_item.text());
						if(_isFunction.call(config.itemCallBack,null))
							config.itemCallBack.call(this,arguments);
						warp_object.hide();
						break;
					default:
						_sign = false;
						break;
				}
				if(_sign){
					cur_item.removeClass(config.hoverClass);
					var _next_  = warp_object.find(config.subOp+":eq("+_cur_index+")");
					_next_.addClass(config.hoverClass);
					_cur_index_  = _cur_index;
				//	if(_next_.position().top < 30) {
				//		warp_object.scrollTop(_next_[0].offsetTop + _next_.height());
				//	}
				//	if(_next_[0].offsetTop + _next_.height() > warp_object[0].clientHeight){
				//		warp_object.scrollTop(warp_object.scrollTop() + warp_object[0].clientHeight - warp_object[0].clientHeight/2);
				//	}
				}
			}
			//键盘释放事件
			$this.keyup(function(e){
				if(e.keyCode==40 || e.keyCode==38 || 17==e.keyCode || 16==e.keyCode || 18==e.keyCode || 91==e.keyCode || (e.keyCode==13 && e.keyCode==27)){
					return false;
				}
				var cur_value = this.value;
                    cur_value = cur_value.replace(/(^\s*)(\s*$)/g,'');
			        var _e =  cur_value != '';
					if(config.displayClose && !_e && _isFunction.call(config.deleteCallBack,null))
						config.deleteCallBack.call(this,arguments);
                    if(_e || 8==e.keyCode || 46==e.keyCode){
						warp_object.find(warp_head).html(buildItem(cur_value));
						bindItem(warp_object);
                    }else if(!_e && (8==e.keyCode||46==e.keyCode)){//8=backspace,46=delete
                      return ;
                    }
			});
		}
		function bindItem(warp_object){
			var _items = warp_object.find(config.subOp);
			//项鼠标悬浮
			_items.hover(function(){
					if(this.id === 'e_type'){
						warp_object.find(config.subOp+":eq("+_cur_index_+")").addClass(config.hoverClass);
						return;
					}
					var _that=$(this);
					_items.removeClass(config.hoverClass);
					_that.addClass(config.hoverClass);
					_cur_index_  = $("#"+config.subBox+" "+config.subOp).index($(this));
					
				},function(){
					if(this.id === 'e_type')
						return;
					var _that=$(this);
					_that.removeClass(config.hoverClass);
				});
				//存放容器悬浮事件
			warp_object.hover(function(){
				},function(){
					$(this).find(config.subOp+":eq("+_cur_index_+")").addClass(config.hoverClass);
			});
		}
		function buildItem(cur_value){
		   cur_value = cur_value.replace(/(^\s*)(\s*$)/g,'');
			var _e =  cur_value != '';
			if(_bankHtml && !_e){
				return _bankHtml;
				}
			var warp_item_html = "<"+config.subOp+" id='e_type'>"+config.warnMsg+"</"+config.subOp+">";
			var _item = "<"+config.subOp+" title='$2' original='$0'>$1</"+config.subOp+">";
			var _val = $('#'+config.valBox).val();
			if(_e){
			    $("#"+config.subBox).unbind("scroll");
			    config.preVal = cur_value;
				$.each(config.datas, function (s,m) {
					if(m.id===_val){
						warp_item_html += "<"+config.subOp+" class="+config.hoverClass+">"+m[config.dataNameKey]+"</"+config.subOp+">";
					}else{
						if(m[config.dataNameKey].toLowerCase().indexOf(cur_value.toLowerCase())!=-1){
							var highlight = m[config.dataNameKey].replace(new RegExp("("+cur_value+")", "gmi"),function(){
								return "<font color="+config.highlightColor+">"+arguments[0]+"</font>"
							});
							warp_item_html += _item.replace(/(\$0)/ig,m[config.dataValueKey]).replace(/(\$1)/ig,highlight).replace(/(\$2)/ig,m[config.dataNameKey]);
						}
					}
				})
		        return warp_item_html;
			}else{
				 if(config.preVal != cur_value || config.init){
				    config.init = false;
				 	$("#"+config.subBox).bind("scroll",function(){
				 	    appendItem(this);
					});
					return config.defalutHtm;
				 }else{
					return _bankHtml;
				 }
			}

		}
		
		function initSelectHtm(){
		      var warpHtml ="";
		      var warpHtml = "<"+config.subOp+" id='e_type'>"+config.warnMsg+"</"+config.subOp+">";
			  var _item = "<"+config.subOp+" title='$2' original='$0'>$1</"+config.subOp+">";
			  var _val = $('#'+config.valBox).val();
		      for(var i = 0;i<config.datas.length;i++){
			      warpHtml += _item.replace(/(\$0)/ig,config.datas[i][config.dataValueKey]).replace(/(\$1)/ig,config.datas[i][config.dataNameKey]).replace(/(\$2)/ig,config.datas[i][config.dataNameKey]);
			      if(i==config.initShowCount)
			       break;
		     }
		     config.defalutHtm = warpHtml;
		     return config.defalutHtm;
		}
		
		function appendItem(obj){
	         if(obj.scrollHeight<obj.scrollTop+210){
				 if(config.datas.length>config.initShowCount){
				   var warpHtml ="";
				   var j = 0;
				   var _item = "<"+config.subOp+" title='$2' original='$0'>$1</"+config.subOp+">";
				   for(var i = config.iteratIndex+1;i<config.datas.length;i++){
				      warpHtml += _item.replace(/(\$0)/ig,config.datas[i][config.datavalueKey]).replace(/(\$1)/ig,config.datas[i][config.dataNameKey]).replace(/(\$2)/ig,config.datas[i][config.dataNameKey]);
				      j = j+1;
				      if(i==config.iteratIndex+config.iteratStep)
				         break;
				      
			      }
			      config.iteratIndex = config.iteratIndex+j;
			      if(warpHtml!=""){
			         $("#"+config.subBox).find("dl").append(warpHtml);
					 bindItem($("#"+config.subBox));
					}
				 }
			  }
		}
	};
})(jQuery);