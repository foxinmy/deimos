<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link rel="stylesheet" href="${base}/js/slider/default.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="${base}/js/slider/nivo-slider.css" type="text/css" media="screen" />
    <script type="text/javascript" src="${base}/js/slider/jquery.nivo.slider.pack.js"></script>
    <script type="text/javascript">
    $(window).load(function() {
        $('#body-slider').nivoSlider();
    });
    </script>
</head>
<body>
	<div id="content" class="slider-wrapper theme-default">
		<div id="body-slider" class="nivoSlider">
			<img src="${base}/images/slider/toystory.jpg" data-thumb="${base}/images/slider/toystory.jpg" alt="" />
			<img src="${base}/images/slider/up.jpg" data-thumb="${base}/images/slider/up.jpg" alt="" title="This is an example of a caption" />
			<img src="${base}/images/slider/walle.jpg" data-thumb="${base}/images/slider/walle.jpg" alt="" data-transition="slideInLeft" />
			<img src="${base}/images/slider/nemo.jpg" data-thumb="${base}/images/slider/nemo.jpg" alt="" title="#htmlcaption" />
		</div>
	</div>
</body>
</html>