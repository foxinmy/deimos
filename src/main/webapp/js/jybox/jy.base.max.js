/*
*
*
*/
(function(){
	String.prototype.trim = function(){
		return this.replace(/(^\s*)(\s*$)/g, "");
	}

	String.prototype.ltrim = function(){
		return this.replace(/(^\s*)/g,"");
	}

	String.prototype.rtrim = function(){
		return this.replace(/(\s*$)/g,"");
	}

	String.prototype.format = function(){
		if (arguments.length == 0) {
			return this;
		}
		for (var s = this, i = 0; i < arguments.length; i++) {
			s = s.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
		}
		return s;
	}
})();

/*
*
*
*/

var jy = window.jy || {};

jy.ns = function(name){
	if(!name || (typeof name).toLowerCase() !== "string")
		return;
	if(name.trim().length ==0)
		return;
	var packages = name.split(".");
	var me = this;
	for(var i=(packages[0] == "jy") ? 1 : 0;i<packages.length;i++){
		me[packages[i]] = me[packages[i]] || {};
		me = me[packages[i]];
	}
	return me;
};


/*
*
*
*/

jy.ns("jy.base");

jy.base = {
	$ : function(){
		return document.getElementById(this);
	},
	isIE : (document.all) || false,
	isObj : function(){
		return this && (typeof this).toLowerCase() === "object" && this.constructor === Object;
	},
	isFun : function(){
		return this && (typeof this).toLowerCase() === "function" && this.constructor === Function;
	},
	isString : function(){
		return this && (typeof this).toLowerCase() === "string" && this.constructor === String;
	},
	extend : function(target,source,deep){
		for(var property in source){
			if(source.hasOwnProperty(property)){
				if(deep && jy.util.isObj.apply(source[property],[])){
					target[property]  = target[property] || {};
					this.extend(target[property],[source.property],true);
				}else{
					target[property] = source[property];
				}
			}
		}
		return target;
	},
	bind : function(type,fn){
		var events = type.split(",");
		for(var i in events){
			if(events[i].trim().length == 0) continue;
			if(this.addEventListener){
				this.addEventListener(events[i],fn,false);
			}else if(this.attachEvent){
				this.attachEvent("on"+events[i],fn);
			}else{
				this["on"+events[i]] = fn;
			}
		}
	},
	unbind : function(type,fn){
		var events = type.split(",");
		for(var i in events){
			if(events[i].trim().length == 0) continue;
			if(this.removeEventListener){
				this.removeEventListener(events[i],fn,false);
			}else if(this.detachEvent){
				this.detachEvent("on"+events[i],fn);
			}else{
				this["on"+events[i]] = null;
			}
		}
	},
	bubble : function(e){
		if (e && e.stopPropagation){
			e.preventDefault();
			e.stopPropagation();
		}else{
			window.event.cancelBubble = true;
		}
	},
	funs : {
		isNumber : function(arg){
			var reg = /^[0-9]+$/;
			return reg.test(arg);
		}
	},
	print : function(msg){
		if(window.console && window.console.log){
			window.console.log(msg);
		}else{
			window.alert(msg);
		}
	}
}