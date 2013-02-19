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
}

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
    for (var StringFormat_s = this, StringFormat_i = 0; StringFormat_i < arguments.length; StringFormat_i++) {
        StringFormat_s = StringFormat_s.replace(new RegExp("\\{" + StringFormat_i + "\\}", "g"), arguments[StringFormat_i]);
    }
    return StringFormat_s;
}