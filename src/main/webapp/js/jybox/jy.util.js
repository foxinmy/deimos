/*
*
*
*/

jy.ns("jy.util");

jy.util.isIE = (document.all) || false;

jy.util.isObject = function(){
	return this && (typeof this).toLowerCase() === "object" && this.constructor === Object;
};
jy.util.isFunction = function(){
	return this && (typeof this).toLowerCase() === "function" && this.constructor === Function;
};
jy.util.isString = function(){
	return this && (typeof this).toLowerCase() === "string" && this.constructor === String;
};
jy.util.extend = function(target,source,deep){
	for(var property in source){
		if(source.hasOwnProperty(property)){
			if(deep && this.isObject.call(source[property],[])){
				target[property]  = target[property] || {};
				this.extend(target[property],[source.property],true);
			}else{
				target[property] = source[property];
			}
		}
	}
	return target;
};