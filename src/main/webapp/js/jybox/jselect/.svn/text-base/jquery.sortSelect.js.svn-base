/*
 * sort select
 * 2013-01-11 by jy.hu
 */
;
$(function(){
	$.fn.sortSelect = function(options){
		var _config = $.extend({
			keywordId : '', // 搜索关键字ID
			defaultTag : 'option',//默认排序的标签
			hasDefault : false, // 是否包含默认选项
			defaultOption : '不选',
			startIndex : 0 // 排序开始项
		},options);
		var _that = $(this);
		var _query = $("#"+_config.keywordId);
		if(_query.is(':input') && _query.attr('type')==='text'){
			var _by = function(_keyword){
				return function(op1,op2){
					var _op1 = $(op1);
					var _op2 = $(op2);
					var _reg = new RegExp(_keyword, "gim");
					var _match1 = _op1.text().match(_reg);
					var _match2 = _op2.text().match(_reg);
					/*
						_op2.text(_op2.text().replace(_reg,function(){
							return "<font color='red'>"+arguments[0]+"</font>";
						}));
					*/
					if(!_match1){
						_match1 = [];
					}
					if(!_match2){
						_match2 = [];
					}
					return (_match2.length - _match1.length);
				}
			}
			_query.bind('keyup',function(e){
					if(e.keyCode==40 || e.keyCode==38 || 17==e.keyCode || 16==e.keyCode || 18==e.keyCode || 91==e.keyCode || (e.keyCode==13 && e.keyCode==27)){
						return false;
					}
					var _options = $.makeArray(_that.find(_config.defaultTag));
					if(_config.hasDefault)
						_options = $.makeArray(_that.find(_config.defaultTag+':gt('+_config.startIndex+')'));
					var _keyword = this.value.replace(/(^\s*)|(\s*$)/g,'');
					if(_keyword != ''){
						_options.sort(_by(_keyword));
						if(_config.hasDefault){
							_that.empty().append($.makeArray($("<"+_config.defaultTag+" value='' selected class='selected'>"+_config.defaultOption+"</"+_config.defaultTag+">")).concat(_options));
						}else{
							_that.empty().append(_options);
							// 默认第一个选中
							if(_options.length > 0){
								// the select
								if(_options[0].selected || _options[0].checked)
									return;
								if(!_options[0].selected){
									_options[0].selected = true;
									return;
								}
								if(!_options[0].checked){
									_options[0].checked = true;
									return;
								}
								if(_options[0].hasOwnProperty('selected')){
									_options[0].selected = true;
									return;
								}
								// the check
								if(_options[0].hasOwnProperty('checked')){
									_options[0].checked = true;
									return;
								}
							}
						}
					}
			});
		}
	}
});