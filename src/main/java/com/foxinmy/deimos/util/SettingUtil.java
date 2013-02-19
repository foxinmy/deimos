package com.foxinmy.deimos.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.ClassPathResource;

import com.foxinmy.deimos.common.SystemSetting;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public class SettingUtil {

	private static final String DEIMOS_CONFIG_XML_FILE_NAME = "deimos.config.xml";// 系统配置文件名称
	private static final String CACHE_MANAGER_BEAN_NAME = "cacheManager";// cacheManager
																			// Bean名称
	private static final String SETTING_CACHE_KEY = "DEIMOS_SETTING";// Setting缓存Key

	/**
	 * 读取系统配置信息
	 * 
	 * @return Setting
	 * 
	 * @throws URISyntaxException
	 * 
	 * @throws DocumentException
	 * 
	 * @throws IOException
	 */
	public static SystemSetting readSetting() throws URISyntaxException,
			DocumentException, IOException {
		File intcosoftXmlFile = new ClassPathResource(
				DEIMOS_CONFIG_XML_FILE_NAME).getFile();
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(intcosoftXmlFile);
		Node systemNameNode = document
				.selectSingleNode("/deimos/setting/systemName");
		Node systemVersionNode = document
				.selectSingleNode("/deimos/setting/systemVersion");
		Node systemDescriptionNode = document
				.selectSingleNode("/deimos/setting/systemDescription");
		Node contextPathNode = document
				.selectSingleNode("/deimos/setting/contextPath");
		Node imageUploadPathNode = document
				.selectSingleNode("/deimos/setting/imageUploadPath");
		Node imageBrowsePathNode = document
				.selectSingleNode("/deimos/setting/imageBrowsePath");
		Node adminLoginUrlNode = document
				.selectSingleNode("/deimos/setting/adminLoginUrl");
		Node adminLoginProcessingUrlNode = document
				.selectSingleNode("/deimos/setting/adminLoginProcessingUrl");
		Node isShowPoweredInfoNode = document
				.selectSingleNode("/deimos/setting/isShowPoweredInfo");
		Node hotSearchNode = document
				.selectSingleNode("/deimos/setting/hotSearch");
		Node metaKeywordsNode = document
				.selectSingleNode("/deimos/setting/metaKeywords");
		Node metaDescriptionNode = document
				.selectSingleNode("/deimos/setting/metaDescription");
		Node addressNode = document.selectSingleNode("/deimos/setting/address");
		Node phoneNode = document.selectSingleNode("/deimos/setting/phone");
		Node zipCodeNode = document.selectSingleNode("/deimos/setting/zipCode");
		Node emailNode = document.selectSingleNode("/deimos/setting/email");
		Node certtextNode = document
				.selectSingleNode("/deimos/setting/certtext");
		Node smtpFromMailNode = document
				.selectSingleNode("/deimos/setting/smtpFromMail");
		Node smtpHostNode = document
				.selectSingleNode("/deimos/setting/smtpHost");
		Node smtpPortNode = document
				.selectSingleNode("/deimos/setting/smtpPort");
		Node smtpUsernameNode = document
				.selectSingleNode("/deimos/setting/smtpUsername");
		Node smtpPasswordNode = document
				.selectSingleNode("/deimos/setting/smtpPassword");
		Node isGzipEnabledNode = document
				.selectSingleNode("/deimos/setting/isGzipEnabled");
		Node buildHtmlDelayTimeNode = document
				.selectSingleNode("/deimos/setting/buildHtmlDelayTime");

		SystemSetting setting = new SystemSetting();
		setting.setSystemName(systemNameNode.getText());
		setting.setSystemVersion(systemVersionNode.getText());
		setting.setSystemDescription(systemDescriptionNode.getText());
		setting.setContextPath(contextPathNode.getText());
		setting.setImageUploadPath(imageUploadPathNode.getText());
		setting.setImageBrowsePath(imageBrowsePathNode.getText());
		setting.setAdminLoginUrl(adminLoginUrlNode.getText());
		setting.setAdminLoginProcessingUrl(adminLoginProcessingUrlNode
				.getText());
		setting.setIsShowPoweredInfo(Boolean.valueOf(isShowPoweredInfoNode
				.getText()));
		setting.setHotSearch(hotSearchNode.getText());
		setting.setMetaKeywords(metaKeywordsNode.getText());
		setting.setMetaDescription(metaDescriptionNode.getText());
		setting.setAddress(addressNode.getText());
		setting.setPhone(phoneNode.getText());
		setting.setZipCode(zipCodeNode.getText());
		setting.setEmail(emailNode.getText());
		setting.setCerttext(certtextNode.getText());
		setting.setSmtpFromMail(smtpFromMailNode.getText());
		setting.setSmtpHost(smtpHostNode.getText());
		setting.setSmtpPort(Integer.valueOf(smtpPortNode.getText()));
		setting.setSmtpUsername(smtpUsernameNode.getText());
		setting.setSmtpPassword(smtpPasswordNode.getText());
		setting.setBuildHtmlDelayTime(Integer.valueOf(buildHtmlDelayTimeNode
				.getText()));
		setting.setIsGzipEnabled(Boolean.valueOf(isGzipEnabledNode.getText()));
		return setting;
	}

	/**
	 * 获取系统配置信息
	 * 
	 * @return Setting
	 */
	public static SystemSetting getSetting() {
		SystemSetting setting = null;
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil
				.getBean(CACHE_MANAGER_BEAN_NAME);
		try {
			setting = (SystemSetting) generalCacheAdministrator
					.getFromCache(SETTING_CACHE_KEY);
		} catch (NeedsRefreshException needsRefreshException) {
			boolean updateSucceeded = false;
			try {
				setting = readSetting();
				generalCacheAdministrator
						.putInCache(SETTING_CACHE_KEY, setting);
				updateSucceeded = true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (!updateSucceeded) {
					generalCacheAdministrator.cancelUpdate(SETTING_CACHE_KEY);
				}
			}
		}
		return setting;
	}

	/**
	 * 写入系统配置信息
	 * 
	 * @return Setting
	 */
	public static void writeSetting(SystemSetting setting) {
		File intcosoftXmlFile = null;
		Document document = null;
		try {
			intcosoftXmlFile = new ClassPathResource(
					DEIMOS_CONFIG_XML_FILE_NAME).getFile();
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(intcosoftXmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element rootElement = document.getRootElement();
		Element settingElement = rootElement.element("setting");
		Node hotSearchNode = document
				.selectSingleNode("/deimos/setting/hotSearch");
		Node metaKeywordsNode = document
				.selectSingleNode("/deimos/setting/metaKeywords");
		Node metaDescriptionNode = document
				.selectSingleNode("/deimos/setting/metaDescription");
		Node addressNode = document.selectSingleNode("/deimos/setting/address");
		Node phoneNode = document.selectSingleNode("/deimos/setting/phone");
		Node zipCodeNode = document.selectSingleNode("/deimos/setting/zipCode");
		Node emailNode = document.selectSingleNode("/deimos/setting/email");
		Node certtextNode = document
				.selectSingleNode("/deimos/setting/certtext");
		Node smtpFromMailNode = document
				.selectSingleNode("/deimos/setting/smtpFromMail");
		Node smtpHostNode = document
				.selectSingleNode("/deimos/setting/smtpHost");
		Node smtpPortNode = document
				.selectSingleNode("/deimos/setting/smtpPort");
		Node smtpUsernameNode = document
				.selectSingleNode("/deimos/setting/smtpUsername");
		Node smtpPasswordNode = document
				.selectSingleNode("/deimos/setting/smtpPassword");
		Node isGzipEnabledNode = document
				.selectSingleNode("/deimos/setting/isGzipEnabled");
		Node buildHtmlDelayTimeNode = document
				.selectSingleNode("/deimos/setting/buildHtmlDelayTime");

		if (hotSearchNode == null) {
			hotSearchNode = settingElement.addElement("hotSearch");
		}
		if (metaKeywordsNode == null) {
			metaKeywordsNode = settingElement.addElement("metaKeywords");
		}
		if (metaDescriptionNode == null) {
			metaDescriptionNode = settingElement.addElement("metaDescription");
		}
		if (addressNode == null) {
			addressNode = settingElement.addElement("address");
		}
		if (phoneNode == null) {
			phoneNode = settingElement.addElement("phone");
		}
		if (zipCodeNode == null) {
			zipCodeNode = settingElement.addElement("zipCode");
		}
		if (emailNode == null) {
			emailNode = settingElement.addElement("email");
		}
		if (certtextNode == null) {
			certtextNode = settingElement.addElement("certtext");
		}

		if (smtpFromMailNode == null) {
			smtpFromMailNode = settingElement.addElement("smtpFromMail");
		}
		if (smtpHostNode == null) {
			smtpHostNode = settingElement.addElement("smtpHost");
		}
		if (smtpPortNode == null) {
			smtpPortNode = settingElement.addElement("smtpPort");
		}
		if (smtpUsernameNode == null) {
			smtpUsernameNode = settingElement.addElement("smtpUsername");
		}
		if (smtpPasswordNode == null) {
			smtpPasswordNode = settingElement.addElement("smtpPassword");
		}
		if (buildHtmlDelayTimeNode == null) {
			buildHtmlDelayTimeNode = settingElement
					.addElement("buildHtmlDelayTime");
		}
		if (isGzipEnabledNode == null) {
			isGzipEnabledNode = settingElement.addElement("isGzipEnabled");
		}

		hotSearchNode.setText(setting.getHotSearch());
		metaKeywordsNode.setText(setting.getMetaKeywords());
		metaDescriptionNode.setText(setting.getMetaDescription());
		addressNode.setText(setting.getAddress());
		phoneNode.setText(setting.getPhone());
		zipCodeNode.setText(setting.getZipCode());
		emailNode.setText(setting.getEmail());
		certtextNode.setText(setting.getCerttext());
		smtpFromMailNode.setText(setting.getSmtpFromMail());
		smtpHostNode.setText(setting.getSmtpHost());
		smtpPortNode.setText(String.valueOf(setting.getSmtpPort()));
		smtpUsernameNode.setText(setting.getSmtpUsername());
		smtpPasswordNode.setText(setting.getSmtpPassword());
		buildHtmlDelayTimeNode.setText(setting.getBuildHtmlDelayTime()
				.toString());
		isGzipEnabledNode.setText(setting.getIsGzipEnabled().toString());

		try {
			OutputFormat outputFormat = OutputFormat.createPrettyPrint();// 设置XML文档输出格式
			outputFormat.setEncoding("UTF-8");// 设置XML文档的编码类型
			outputFormat.setIndent(true);// 设置是否缩进
			outputFormat.setIndent("	");// 以TAB方式实现缩进
			outputFormat.setNewlines(true);// 设置是否换行
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(
					intcosoftXmlFile), outputFormat);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新系统配置信息
	 * 
	 * @param setting
	 */
	public static void updateSetting(SystemSetting setting) {
		writeSetting(setting);
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil
				.getBean(CACHE_MANAGER_BEAN_NAME);
		generalCacheAdministrator.flushEntry(SETTING_CACHE_KEY);
	}

	/**
	 * 刷新系统配置信息
	 * 
	 */
	public void flush() {
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil
				.getBean(CACHE_MANAGER_BEAN_NAME);
		generalCacheAdministrator.flushEntry(SETTING_CACHE_KEY);
	}
}
