package com.hj.eventbrite.service;

import com.hj.eventbrite.page.EventbritePage;
import com.hj.eventbrite.page.PaymentPage;
import com.hj.eventbrite.property.EventbriteProp;
import com.hj.selenium.annotation.Page;
import com.hj.selenium.processor.AbstractAutoProcessor;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by David.Zheng on 2018/8/11.
 */
@Log
@Service
public class AutoEventbritePayment extends AbstractAutoProcessor
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
			boolean loopWait = true;
			while (loopWait)
			{
				if (eventbritePage.isSaleStart())
				{
					log.info("*********开始自动购票");
					eventbritePage.buyTicket();
					paymentPage.fulfillInputs(eventbriteProp);
					if (StringUtils.contains(eventbriteProp.getFullRunning().toUpperCase(), "Y"))
					{
						paymentPage.payTicket();
					}
					loopWait = false;
					log.info("**********抢票成功...");
					//paymentPage.closeBrowser(); 不自动关闭浏览器
				}
				else
				{
					//not started yet
					eventbritePage.clickTicketBtn();
					String startTimeStr = eventbritePage.parseStartTime();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
					LocalTime startTime = LocalTime.parse(startTimeStr, formatter);
					long millis = Duration.between(LocalTime.now(), startTime).toMillis();
					log.info("*******活动将于[" + startTime + "]开始,请等待[" + (millis / 60000) + "分钟后],系统会自动抢票");
					log.info("*******等待中..., 请不要关闭浏览器和本系统,抢票完毕会自动退出");
					try
					{
						Thread.sleep(millis);
						//休息结束,重新踏上征途,需要刷新页面
						eventbritePage.openPage(eventbriteProp.getPageUrl());
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		else
		{
			log.warning("********抢票活动已经结束************");
		}
	}

}
