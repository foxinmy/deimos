/*
 * File upload - jQuery Plugin
 * Version: 1.0 (2012/11/22)
 * Requires: jQuery v1.4+
 *
 *   https://github.com/foxinmy
 *   http://act.iteye.com
 */
;(function($){
	// the predefine variables
	var iframe,title,form,table,bottom,mask,wrap,ahref,addButton,submitButton,cancelButton,deleteButton,text='共${0}个文件,合计大小：${1}',
		imgLoader=new Image(),imgRegExp = /\.(jpg|gif|png|bmp|jpeg)(.*)?$/i,busy=false,isIE=false,isIE6=false,total=0,files=[],
		trtdTmplate = "<ul><li style='width:70%'><span title='${0}'>${0}</span></li><li  style='width:13%;text-align:right'>${1}</li><li style='width:12%;text-align:center'><a href='javascript:void(0);' title='上传' name='aupload'><img src='ico/submit.png' alt='上传'></a>&nbsp;<a href='javascript:void(0);' title='取消' name='acancel'><img src='ico/cancel.png' alt='取消'></a></li></ul>",
	// the default configuration
	_config = {
		fmName : "_form-upload",
		feName : "files",
		fmType : "post",
		width:640,
		height:150,
		fmDatas : null,
		url : "fileUpload" ,
		maxLimit : 5120 ,
		allowedTypes : "*" ,
		onCallback : function(){}
	},
	// print message to console
	_print = function(msg){
		if(console && console.log)
			console.log(msg);
	},
	// bind the event to element 
	_bind = function(target,type,fn){
		target = target.get(0);
		if(!(target || type || fn)) return;
		if(isIE){
			target.setCapture();
			target.attachEvent('on'+type,fn);
		}else{
			//target.captureEvents();
			//target.preventDefault();
			target.addEventListener(type,fn,false);
		}
		return target;
	},
	// cleanup data or content
	_abort = function(){
		form.find(":file").empty();
		imgLoader.onerror = imgLoader.onload = null;
		table.find('ul').remove();
		bottom.find('span').empty().hide();
		if(isIE)$('#_iframe-fix-ie6-select').hide();
		busy = false;
	},
	// reset file information
	_reset = function(){
		total = 0;
		files.length = 0;
		table.find('div').slideDown('slow');
		bottom.find('span').empty().hide();
	},
	// exception handler
	_error = function(){
		
	},
	// drag the title div and drag file from desktop (nonsupport ie browser)
	_drag = function(){
		//--------------drag title begin
		title.bind('mousedown',function(e){
			 $(this).data('pre_offset',wrap.offset())
				  .data('pre_postion',{X:e.clientX,Y:e.clientY})
				  .data('is_move',true);
		}).bind('mouseup',function(e){
			$(this).data('is_move',false);
			if(isIE) this.releaseCapture();
		}).bind('mousemove',function(e){
			var $this = $(this);
			if(e.srcElement.tagName != 'DIV') {$this.data('is_move',false);return;};
			var offset = $this.data('pre_offset');
			var pos = $this.data('pre_postion');
			if(!$this.data('is_move')) return;
			wrap.css({
				left: (offset.left+e.clientX-pos.X),
				top: (offset.top+e.clientY-pos.Y)
			});
			if(isIE){
				this.setCapture();
				//window.event.returnValue = false; // 阻止浏览器默认行为
				//window.event.cancelBubble = true; // 阻止浏览器事件冒泡 
			}else{
				e.preventDefault();
				e.stopPropagation();
			}
		});
		//-----------------drag title end
		//-----------------drag file begin
		if((window.File && window.FileList && window.FileReader) || false){
			table.bind('dragenter',function(e){
				if(!$(this).hasClass('tbhover'))
					$(this).addClass('tbhover');
				_bubble(e);
			}).bind('dragover',function(e){
				if(!$(this).hasClass('tbhover'))
					$(this).addClass('tbhover');
				_bubble(e);
			}).bind('dragleave',function(e){
				_bubble(e);
				if($(this).hasClass('tbhover'))
					$(this).removeClass('tbhover');
			});
			_bind(table,'drop',function(e){
				$(this).find('div').hide();
				$(this).removeClass('tbhover');
				var fs = e.target.files || e.dataTransfer.files;
				$.merge(files,fs);// 合并数组
				_append(fs);
				_bubble(e);
			});
		}
		//-----------------drag file end
	},
	// bubbling phase
	_bubble = function(e){
		if (e && e.stopPropagation){
			e.preventDefault();
			e.stopPropagation();
		}else{
			window.event.cancelBubble = true;
		}
	},
	// close modal window
	_close = function(){
		if (busy || wrap.is(':hidden')) {
			return;
		}
		busy = true;
		mask.fadeOut(300);
		wrap.fadeOut(300,_abort);
	},
	// show shade and wrap div
	_show = function(){
		mask.fadeIn(300);
		wrap.fadeIn(300,function(){
			busy = false;
		});
	},
	// remove an item from files
	_remove = function(index){
		if(typeof index === 'number'){
			if(index >= files.length || -index >= files.length) return;
			if(index < 0) index = files.length + index;
			files.splice(index,1);
		}else{
			$.each(files,function(i,item){
				if(index == item){
					files.splice(item.index,1);
					return false;
				}
			});
		}
	},
	// update span's text
	_span = function(){
		total = _total(files);
		var span = bottom.find('span');
		span.empty();
		span.append(text.replace(/\$\{0\}/ig,files.length).replace(/\$\{1\}/,_size(total)));
		if(span.is(':hidden')) span.show();
	},
	// append file information to table ui
	_append = function(fs){
		$.each(fs,function(index,file){
			$(trtdTmplate.replace(/\$\{0\}/ig,file.name).replace(/\$\{1\}/,_size(file.size)))
			.appendTo(table).fadeIn('slow').find('a[name="acancel"]').bind('click',_acancel);
		});
		_span();
	},
	_datas = function(options){
		var formData;
		if (typeof options.fmDatas === 'function') {
			return options.fmDatas(options.fmName);
        }
		if ($.isArray(options.fmDatas)) {
			return options.fmDatas;
		}
		if (options.fmDatas) {
			fmDatas = [];
			$.each(options.fmDatas, function (name, value) {
				formData.push({name: name, value: value});
			});
			return formData;
		}
		return [];
	},
	_size = function(size){
		if(size < 1024){
			return (Math.round(size)).toString() + 'byte';
		}else if(size < 1024*1024){
			return (Math.round(size * 100 / 1024) / 100).toString() + 'KB';
		}else if(size > 1024*1024*1024){
			 return (Math.round(size * 100 / (1024 * 1024*1024)) / 100).toString() + 'GB';
		}else{
			return (Math.round(size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
		}
	},
	_total = function(files){
		var total = 0;
		$.each(files,function(index,file){
			total += file.size || 0;
		});
		return total;
	},
	_acancel = function(e){
		$(this).parent().parent().fadeOut(300,function(e){
			if(table.find('ul').length ==1){
				_reset();
			}else{
				_remove($(this).index()*1-1);
				_span();
			}
			$(this).remove();
		});
	},
	// initialize html block
	_init = function(){

		if($('#dialog_wrap_div').length){
			return;
		}
			
		iframe = $("<iframe height='0' width='0' frameborder='0' scrolling='no' name='_iframe-upload' src='javascript:false;' style='display:none'></iframe>");
		title = $("<div id='dialog_wrap_title'></div>");
		table = $("<div title='drag the file from your desktop' class='table'><div class='drag'>drag the file from your desktop</div></div>");
		bottom = $("<div id='dialog_wrap_bottom'><span></span></div>");
		form = $("<form target='_iframe-upload'></form>");
		mask = $("<div style='position:fixed; opacity: 0.25; z-index: 99999; top: 0px; left: 0px; background-color: rgb(0, 0, 0); display: none;'></div>")
						.css("height","100%").css("width","100%");
		wrap = $("<div id='dialog_wrap_div'></div>");
		ahref = $("<a id='dialog_close_a'></a>").appendTo(form);
		addButton = $("<span class='btn addbtn'><span>添加文件..</span><input type='file' name='files[]' multiple='multiple'></span>").appendTo(title);
		submitButton = $("<input type='button' value='开始上传' class='btn submitbtn'>").appendTo(bottom);
		cancelButton = $("<input type='button' value='取消上传' class='btn cancelbtn'>").appendTo(title);
		deleteButton = $("<input type='button' value='全部删除' class='btn deletebtn'>").appendTo(title);
		
		title.appendTo(form);
		table.appendTo(form);
		bottom.appendTo(form);
		form.append(iframe).appendTo(wrap);
		isIE = (document.all) ? true : false;//navigator.userAgent.toLowerCase().search(/msie/g)>-1;

		if(isIE){
			var _height = Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight);
			var _width = Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth);
			if(/[msie](\s*)?(\d)\.0/ig.exec(navigator.userAgent.toLowerCase())[2] == 6){
				isIE6 = true;
				mask.css({"filter":"alpha(opacity=25)","position":"absolute",
				"height" :  _height,
				"width" : _width});
			}
			$("<iframe style='position:absolute;top:0;left:0;filter:alpha(opacity=0);display:none;' id='_iframe-fix-ie6-select' src='javascript:false;'></iframe>")
				.css({"height":_height,"width":_width}).appendTo(document.body);
		}
		wrap.appendTo(document.body);
		mask.appendTo(document.body);
	},
	// 
	_start = function(){
		ahref.bind('click',_close);
		deleteButton.bind('click',function(e){
			if(!table.find('div').is(":hidden")) return;
			busy=true;
			$.each(table.find('ul'),function(index,ul){
				$(ul).fadeOut(300,function(e){
					$(this).remove();
				});
			});
			busy=false;
			_reset();
		});
		addButton.find(":file").bind('change',function(e){
			var $this = $(this);
			if($this.val().length == 0)return;
			var $files = $this.attr('files');
			if(table.is(":hidden")) table.fadeIn(300);
			table.find('div').hide();
			// html5 multiple 
			if($files){
				$.merge(files,$files);// 合并数组
				_append($files);
			}
		});
		submitButton.bind('click',function(e){
			if(busy)return;
			if(files && files.length > 0)
				form.get(0).submit();
		});
	};
	// main method
	$.fn.upload = function(options){

		var me = $(this);
		var config = $.extend(_config,options);
		
		var width = config.width;
		var height = config.height;
		
		wrap.css("min-height",height+"px").css("width",width+"px")//.css("margin-left",(-width/2)+"px").css("margin-top",(-height/2)+"px");
		form.attr('method',config.fmType).attr('action',config.url).attr('id',config.fmName);
		
		$.each(_datas(config),function(index,field){
			$("<input type='hidden'>").attr('name',field.name).val(field.value).appendTo(form);
		});
		
		addButton.find(":file").attr('accept',config.allowedTypes);
		
		me.unbind('click').bind('click',function(e){
			table.find('div').show();
			if(isIE){
				if(isIE6)
					$('#_iframe-fix-ie6-select').show();
				addButton.css('width',110);
				cancelButton.css('width',110);
				deleteButton.css('width',90);
			}
			_show();
			_start();
		});
	};
	// document ready
	$(function(){
		_init();
		_drag();
	});
})(jQuery);