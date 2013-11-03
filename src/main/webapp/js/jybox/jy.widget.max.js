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
	},
	drag : {
		limitX : false,
		limitY : false,
		limitR : 1500,
		limitB : 900,
		onStart : null,
		onMove : null,
		onStop : null
	},
	layout : {
		north : {
			panelName : "jy-widget-layout-north",
			height : 50,
			minHeight : 50,
			maxHeight : 100,
			resizer : {
				size : 8
			}
		},
		west : {
			panelName : "jy-widget-layout-west",
			height : 400,
			width : 100,
			minHeight : 100,
			maxHeight : 500,
			minWidth : 50,
			maxWidth : 500,
			resizer : {
				size : 8
			}
		},
		center : {
			panelName : "jy-widget-layout-center",
			height : 400,
			width : 500,
			minHeight : 100,
			maxHeight : 500,
			minWidth : 50,
			maxWidth : 500
		},
		east : {
			panelName : "jy-widget-layout-east",
			minWidth : 50
		},
		south : {
			panelName : "jy-widget-layout-south",
			minWidth : 50
		}
	}
};

jy.base.extend(jy.widget,{
	init : function(){
		if(!jy.widget.config.base.body){
			jy.widget.config.base.body = document.body;
		}
		jy.widget.config.base.offset = {
			y : Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight),
			x : Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth)
		}
	},
	modal : {
		me : jy.widget.config.modal,
		html : $("<div style='width:100%;height:100%;position:fixed; opacity: 0.25; z-index: 999; top: 0px; left: 0px; background-color: rgb(0, 0, 0); display: none;'></div>"),
		iefix : $("<iframe style='position:absolute;top:0;left:0;z-index:998;filter:alpha(opacity=0);display:none;' id='jy-widget-iefix-select' src='javascript:false;'></iframe>"),
		init : function(){
			if($("#"+this.me.id).length)
				return;
			jy.widget.init();
			this.html.attr("id",this.me.id);
			if(jy.base.isIE){
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
		show : function(options){
			jy.base.extend(this.me,options);
			this.init();
			if(jy.base.isIE){
				this.iefix.show();
			}
			if(this.me.fadeSpeed > 0){
				this.html.fadeIn(this.me.fadeSpeed);
			}else{
				this.html.show();
			}
		},
		close : function(){
			if(jy.base.isIE){
				this.iefix.hide();
			}
			if(this.me.fadeSpeed > 0){
				this.html.fadeOut(this.me.fadeSpeed);
			}else{
				this.html.hide();
			}
		},
		shortcut : function(callbackScope,callbackFun){
			var _that = this;
			var _close = _that.close;
			$(document).keydown(function(event){
				var e = window.event || event;  
				var k = e.keyCode || e.which;
				if(k == 27){
					_close.apply(_that,[]);
					if(callbackScope && jy.base.isFun.apply(callbackFun,[])){
						callbackFun.apply(callbackScope,[]);
					}
				}
			});
		}
	},
	drag : function(options){
		var $this = this;
		var me = jy.widget.config.drag;
		jy.base.extend(me,options);
		this.style.position = "absolute";
		this.style.cursor = "move";
		jy.widget.init();
		$this._start = function(e){
			if(jy.base.isFun.apply(me.onStart,[])){
				me.onStart.call($this,{"x":$this.e.clientX,"y":e.clientY});
			};
			$this.ox = e.clientX;
			$this.oy = e.clientY;
			$this._x = $this.offsetLeft - e.clientX;
			$this._y = $this.offsetTop - e.clientY;
			jy.base.bind.call(document,"mousemove",$this._move);
			jy.base.bind.call(document,"mouseup",$this._stop);
		};
		$this._move = function(e){
			window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
			var _left,_top;
			if(!me.limitX){
				_left = e.clientX + $this._x;
				var _limitR = me.limitR;
				if(jy.base.funs.isNumber(_limitR)){
					_left = Math.min(_left, _limitR);
				}else{
					_left = Math.min(_left, jy.widget.config.base.offset.x - $this.offsetLeft);
				}
				$this.style.left = _left + "px";
			};
			if(!me.limitY){
				_top = e.clientY + $this._y;
				var _limitB = me.limitB;
				if(jy.base.funs.isNumber(_limitB)){
					_top = Math.min(_top, _limitB);
				}else{
					_top = Math.min(_top, jy.widget.config.base.offset.y - $this.offsetTop);
				}
				$this.style.top = _top + "px";
			};
			if(jy.base.isFun.apply(me.onMove,[])){
				me.onMove.call($this,{"x":_left,"y":_top,"ox":$this.ox,"oy":$this.oy});
			};
		};
		$this._stop = function(e){
			if(jy.base.isFun.apply(me.onStop,[])){
				me.onStop.call($this,null);
			};
			jy.base.unbind.call(document,"mousemove",$this._move);
		};
		jy.base.bind.call($this,"mousedown",$this._start);
	},
	layout : function(options){
		this.styleTemplate = "position:absolute; margin: 0px; text-align: left; display: block; visibility: visible; z-index: 2; top: {0}; bottom:{1}; left: {2}; right: {3}; width: {4}; height: {5};{6}";
		this.me = jy.widget.config.layout;
		jy.base.extend(this.me,options);
		this.northPanel = $("div[class='"+this.me.north.panelName+"']");
		this.westResizer = $("<div class='widget-layout-resizer'></div>");
		this.northResizer = $("<div class='widget-layout-resizer'></div>");
		this.westPanel = $("div[class='"+this.me.west.panelName+"']");
		this.centerPanel = $("div[class='"+this.me.center.panelName+"']");
		this.eastPanel = $("div[class='"+this.me.east.panelName+"']");
		this.southPanel = $("div[class='"+this.me.south.panelName+"']");
		this.checkedPanel = (function(that){
			var northPanelReuired = (that.northPanel && that.northPanel.length > 0);
			var centerPanelReuired = (that.centerPanel && that.centerPanel.length > 0);
			if(!northPanelReuired) return "the north container is required!";
			if(!centerPanelReuired) return "the center container is required!";
		})(this);
		
		var westPanelFlag = (this.westPanel && this.westPanel.length > 0);
		var eastPanelFlag = (this.eastPanel && this.eastPanel.length > 0);
		var southPanelFlag = (this.southPanel && this.southPanel.length > 0);
		
		var borderWidth = function(el,attr){
			var b = "border"+ attr.substr(0,1).toUpperCase() + attr.substr(1); // left => Left
			return Math.round(parseFloat(el.css(b)) || 0);
		};
		var paddingWidth = function(el,attr){
			var b = "padding"+ attr.substr(0,1).toUpperCase() + attr.substr(1); // left => Left
			return Math.round(parseFloat(el.css(b)) || 0);
		};
		
		if(!this.checkedPanel){
			var $layout = {};
			var $window=$(window),top=0,bottom=0,left=0,right=0,width=$window.width(),height=this.me.north.height;
			/*north*/
			this.northPanel.addClass("widget-layout-pane");
			this.northPanel.attr("style",this.styleTemplate.format(top,bottom,left,right,width+"px",height+"px",""));
			top = this.northPanel.get(0).offsetHeight;
			height = this.me.north.resizer.size;
			this.northResizer.attr("style",this.styleTemplate.format(top+"px",bottom,left,right,width+"px",height+"px","cursor: n-resize; overflow: hidden"));
			this.northPanel.after(this.northResizer);
			$layout.north = this.northPanel;
			$layout.northResizer = this.northResizer;
			jy.widget.drag.call(this.northResizer.get(0),{
				limitX:true,
				onMove : function(e){
					var _north_offset_heigth = $layout.north.get(0).offsetHeight;
					var _north_resizer_offset_heigth = this.offsetHeight;
					var _north_height = $layout.north.height();
					var _y =  _north_offset_heigth - _north_height;
					$layout.north.height(e.y - _y);
					$layout.center.offset({"top":(_north_offset_heigth+_north_resizer_offset_heigth)});
					$layout.center.height($layout.center.height() + _north_height - $layout.north.height());
				}
			});
			
			/*center*/
			this.centerPanel.addClass("widget-layout-pane");
			width = width - paddingWidth(this.centerPanel,"left") - paddingWidth(this.centerPanel,"right") - borderWidth(this.centerPanel,"left") - borderWidth(this.centerPanel,"right");
			top = this.northResizer.offset().top + this.northResizer.get(0).offsetHeight;
			height = this.me.center.height;
			if(westPanelFlag){
				left = this.me.west.width + this.me.west.resizer.size + 2;
				width = (width - left -2);
			}
			if(southPanelFlag){
				height = ($window.height() - top -2);
			}else{
				height = $window.height();
			}
			this.centerPanel.attr("style",this.styleTemplate.format(top+"px",bottom,left+"px",right,width+"px",height+"px",""));
			$layout.center = this.centerPanel;
			
			/*west*/
			if(westPanelFlag){
				left = 0;
				width = this.me.west.width;
				height = this.me.west.height;
				this.westPanel.addClass("widget-layout-pane");
				this.westPanel.attr("style",this.styleTemplate.format(top+"px",left,width+"px",height+"px","right: auto"));
				left = this.me.west.width + 2;
				width = this.me.north.resizer.size;
				height = this.me.west.height + 2;
				this.westResizer.attr("style",this.styleTemplate.format(top+"px",left+"px",width+"px",height+"px","cursor: w-resize"));
				this.westPanel.after(this.westResizer);
					jy.widget.drag.call(this.westResizer.get(0),{
					limitY:true
				});
			}
			/*east*/
			if(eastPanelFlag){
				
			}
			
			/*suoth*/
			if(southPanelFlag){
				
			}
			
			/*window onresize*/
			$(window).bind("resize",function(e){
				
			});
		}else{
			jy.base.print(this.checkedPanel);
		}
	}
});