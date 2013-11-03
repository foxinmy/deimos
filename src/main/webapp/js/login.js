$(document).ready(function() {
	$(".fancybox").fancybox({
		type:"ajax",
		helpers : {
			overlay : {
				css : {
					'background-color' : '#eee'	
				}
			}
		}
	});
});