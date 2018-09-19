package com.hj.eventbrite.service;

import com.hj.eventbrite.page.EventbritePage;
import com.hj.eventbrite.page.PaymentPage;
import com.hj.eventbrite.property.EventbriteProp;
import com.hj.selenium.annotation.Page;
import com.hj.selenium.processor.AutoProcessor;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by David.Zheng on 2018/8/11.
 */
@Log
@Service
public class AutoEventbritePayment implements AutoProcessor
{
	@Page
	private EventbritePage eventbritePage;

	@Page
	private PaymentPage paymentPage;

	@Autowired
	private EventbriteProp eventbriteProp;

	@Override public void process()
	{
		eventbritePage.openPage(eventbriteProp.getPageUrl());
		if (!eventbritePage.isSaleEnd())
		{
			if (eventbritePage.isSaleStart())
			{
				buyingTicket();
			}
			else
			{
				//not started yet
				eventbritePage.clickTicketBtn();
				String startTimeStr = eventbritePage.parseStartTime();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
				LocalTime startTime = LocalTime.parse(startTimeStr.toUpperCase() , formatter);
				long millis = Duration.between(LocalTime.now(), startTime).toMillis();
				log.info("*******活动将于[" + startTime + "]开始,请等待[" + (millis / 60000) + "分钟后],系统会自动抢票");
				log.info("*******等待中..., 请不要关闭本系统,抢票完毕会自动退出");
				eventbritePage.closeBrowser();

				ScheduledExecutorService exec = Executors.newScheduledThreadPool(2);
				printingDot(exec);
				delayToRun(exec, millis);
			}
		}
		else
		{
			log.warning("********抢票活动已经结束************");
		}
	}

	private void buyingTicket()
	{
		log.info("*********开始自动购票");
		eventbritePage.buyTicket();
		paymentPage.fulfillInputs(eventbriteProp);
		if (StringUtils.contains(eventbriteProp.getFullRunning().toUpperCase(), "Y"))
		{
			paymentPage.payTicket();
		}
		log.info("**********抢票成功...");
		System.exit(0); //正常退出系统
		//paymentPage.closeBrowser(); 不自动关闭浏览器,留下证据
	}

	/**
	 *
	 * @param exec
	 * @param millis
	 */
	private void delayToRun(ScheduledExecutorService exec, long millis)
	{
		exec.schedule(() -> {
			try
			{
				//webdriver session would timeout, need to create new driver to inject into the page.
				FirefoxDriver driver = new FirefoxDriver();
				initPageInstance(driver);
			}
			catch (Exception e)
			{
				log.severe("Not able to init Page instance");
			}
			eventbritePage.openPage(eventbriteProp.getPageUrl());
			buyingTicket();
		}, millis, TimeUnit.MILLISECONDS);
	}

	/**
	 * 表示我还活着
	 *
	 * @param exec
	 */
	private void printingDot(ScheduledExecutorService exec)
	{
		exec.scheduleAtFixedRate(() -> {
			int i = 0;

			if (++i % 60 == 0)
			{
				System.out.print((i / 60) + "分钟" + System.getProperty("line.separator"));
			}
			System.out.print(".");

		}, 0, 1, TimeUnit.SECONDS);
	}

}
