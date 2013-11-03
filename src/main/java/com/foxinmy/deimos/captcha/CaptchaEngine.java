package com.foxinmy.deimos.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

public class CaptchaEngine extends ListImageCaptchaEngine {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static final String CAPTCHA_SERVICE_BEAN_NAME = "captchaService";// CaptchaService Bean名称
	public static final String IMAGE_CAPTCHA_KEY = "imageCaptcha";// ImageCaptcha对象存放在Session中的key
	public static final String CAPTCHA_PARAMETER_NAME = "j_captcha";// 验证码输入表单名称
	public static final String CAPTCHA_IMAGE_URL = "/images/jcaptcha";// 验证码图片URL
	private static final Integer MIN_WORD_LENGTH = 4;// 验证码最小长度
	private static final Integer MAX_WORD_LENGTH = 4;// 验证码最大长度
	private static final Integer IMAGE_HEIGHT = 40;// 验证码图片高度
	private static final Integer IMAGE_WIDTH = 80;// 验证码图片宽度
	private static final Integer MIN_FONT_SIZE = 23;// 验证码最小字体
	private static final Integer MAX_FONT_SIZE = 23;// 验证码最大字体
	private static final String RANDOM_WORD = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";// 随机字符
	// private static final String IMAGE_PATH =
	// "./com/foxinmy/deimos/captcha/";//随机背景图片路径

	// 验证码随机字体
	private static final Font[] RANDOM_FONT = new Font[] {
			new Font("nyala", Font.BOLD, MIN_FONT_SIZE),
			new Font("Bell MT", Font.PLAIN, MIN_FONT_SIZE),
			new Font("Credit valley", Font.BOLD, MIN_FONT_SIZE) };
	// 验证码随机颜色
	private static final Color[] RANDOM_COLOR = new Color[] {
			new Color(23, 170, 27), new Color(220, 34, 11),
			new Color(23, 67, 172) };

	@Override
	protected void buildInitialFactories() {
		/*
		 * WordGenerator dictionnaryWords = new ComposeDictionaryWordGenerator(
		 * new FileDictionary("toddlist"));
		 */
		WordGenerator wordGenerator = new RandomWordGenerator(RANDOM_WORD);
		RandomListColorGenerator randomListColorGenerator = new RandomListColorGenerator(
				RANDOM_COLOR);
		TextPaster randomPaster = new DecoratedRandomTextPaster(
				MIN_WORD_LENGTH, MAX_WORD_LENGTH, randomListColorGenerator,
				new TextDecorator[] {});
		BackgroundGenerator background = new UniColorBackgroundGenerator(
				IMAGE_WIDTH, IMAGE_HEIGHT, Color.white);
		FontGenerator font = new RandomFontGenerator(MIN_FONT_SIZE,
				MAX_FONT_SIZE, RANDOM_FONT);

		ImageDeformation postDef = new ImageDeformationByFilters(
				new ImageFilter[] {});
		ImageDeformation backDef = new ImageDeformationByFilters(
				new ImageFilter[] {});
		ImageDeformation textDef = new ImageDeformationByFilters(
				new ImageFilter[] {});

		WordToImage word2image = new DeformedComposedWordToImage(font,
				background, randomPaster, backDef, textDef, postDef);
		addFactory(new GimpyFactory(wordGenerator, word2image));
	}
}
