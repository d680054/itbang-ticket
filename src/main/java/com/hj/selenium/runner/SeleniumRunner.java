package com.hj.selenium.runner;

import com.hj.selenium.processor.AutoProcessor;
import lombok.extern.java.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Level;

/**
 * Created by David.Zheng on 2018/8/11.
 */
@Log
@Component
public class SeleniumRunner implements ApplicationRunner
{
	@Autowired
	private AutoProcessor autoProcessor;

	private WebDriver driver;

	@Override public void run(ApplicationArguments args)
	{
		try
		{
			initWebDriver();
			autoProcessor.process();
		}
		catch (Exception e)
		{
			log.severe("Errors:" + e.getMessage());
		}
		finally
		{
//			if (driver != null)
//				driver.close();
		}
	}

	/**
	 * initialize the web driver and each pages
	 */
	private void initWebDriver() throws NoSuchFieldException, IllegalAccessException
	{
		String path = System.getProperty("user.dir") + "/bin/geckodriver";
		log.info("当前working path:"+ path);
		System.setProperty("webdriver.gecko.driver", path);
		FirefoxOptions options = new FirefoxOptions();
		options.setAcceptInsecureCerts(false);
		options.setLogLevel(FirefoxDriverLogLevel.fromLevel(Level.OFF));
		options.setCapability("moz:webdriverClick", false);
		driver = new FirefoxDriver(options);
		autoProcessor.initPageInstance(driver);
	}

}
