/*
*
*
*/

jy.ns("jy.widget");

jy.widget.config = {
	base : {
		body : document.body,
		offset : null
	},
	modal : {
		id : 'jy-widget-modal',
		fadeSpeed : 300
	}
};

jy.util.extend(jy.widget,{
	modal : {
		me : jy.widget.config.modal,
		html : $("<div style='width:100%;height:100%;position:fixed; opacity: 0.25; z-index: 999; top: 0px; left: 0px; background-color: rgb(0, 0, 0); display: none;'></div>"),
		iefix : $("<iframe style='position:absolute;top:0;left:0;z-index:998;filter:alpha(opacity=0);display:none;' id='jy-widget-iefix-select' src='javascript:false;'></iframe>"),
		init : function(){
			if($("#"+this.me.id).length)
				return;
			if(!jy.widget.config.base.body){
				jy.widget.config.base.body = document.body;
				jy.widget.config.base.offset = {
					y : Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight),
					x : Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth)
				}
			}
			this.html.attr("id",this.me.id);
			if(jy.util.isIE){
				var ui_offset = jy.widget.config.base.offset;
				this.html.css({"filter":"alpha(opacity=25)","position":"absolute",
					"height" :  ui_offset.y,
					"width" : ui_offset.x});
				this.iefix.css({
					"height" :  ui_offset.y,
					"width" : ui_offset.x});
				this.iefix.appendTo(jy.widget.config.base.body);
			}
			this.html.appendTo(jy.widget.config.base.body);
		},
		show : function(){
			this.init();
			if(jy.util.isIE){
				this.iefix.show();
			}
			this.html.fadeIn(this.me.fadeSpeed);
		},
		close : function(){
			if(jy.util.isIE){
				this.iefix.hide();
			}
			this.html.fadeOut(this.me.fadeSpeed);
		},
		shortcut : function(callbackScope,callbackFun){
			var _that = this;
			var _close = _that.close;
			$(document).keydown(function(event){
				var e = window.event || event;  
				var k = e.keyCode || e.which;
				if(k == 27){
					_close.call(_that,null);
					if(callbackScope && jy.util.isFunction.call(callbackFun,null)){
						callbackFun.call(callbackScope,null);
					}
				}
			});
		}
	},
	drag : function(target){
		this._x=this._y = 0;
		var me  = this;
		this._start = function(e){
			me._x = target.offsetLeft - e.clientX;
			me._y = target.offsetTop - e.clientY;
		};
		this._move = function(e){
			window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
			target.style.left = Math.max(0,Math.min((me._x + e.clientX),jy.widget.config.base.offset.x)) + "px"; 
			target.style.top = Math.max(0,Math.min((me._y + e.clientY),jy.widget.config.base.offset.y)) + "px";
		}
		this._stop = function(e){
			$(document).unbind("mouseup mousemove");
		}
		this.bind('mousedown',function(e){
			me._start.call(me,e);
			$(document).bind("mousemove",me._move).bind("mouseup",me._stop);
		}).bind('mouseup',me._stop);
	}
});