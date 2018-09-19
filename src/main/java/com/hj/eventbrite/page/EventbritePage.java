package com.hj.eventbrite.page;

import com.hj.selenium.page.AbstractPage;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by David.Zheng on 2018/8/10.
 */
public class EventbritePage extends AbstractPage
{
	@FindBy(css = "button[data-automation='ticket-modal-btn']")
	private WebElement ticketBtn;

	@FindBy(css = "button[data-automation='ticket-modal-register-button']")
	private WebElement checkoutBtn;

	@FindBy(css = "div[data-automation='micro-ticket-box-status']")
	private WebElement ticketStatus;

	@FindBy(css = "div[data-automation='order-box-notification-message'] p:last-child")
	private WebElement notification;

	public boolean isDetailsBtn()
	{
		boolean status = false;

		String btnText =
				getDriver().findElement(By.cssSelector("button[data-automation='ticket-modal-btn']")).getText();
		if (StringUtils.containsIgnoreCase(btnText, "details"))
		{
			status = true;
		}

		return status;
	}

	public boolean isSaleStart()
	{
		return !isDetailsBtn();
	}

	public boolean isSaleEnd()
	{
		return isDetailsBtn() && (StringUtils.containsIgnoreCase(ticketStatus.getText(), "ended") || StringUtils
				.containsIgnoreCase(ticketStatus.getText(), "sold"));
	}

	/**
	 * click ticket button
	 */
	public void clickTicketBtn()
	{
		ticketBtn.click();
	}

	/**
	 * click the ticket btn and then checkout btn
	 */
	public void buyTicket()
	{
		ticketBtn.click();
		checkoutBtn.click();
	}

	/**
	 * 解析开售时间
	 *
	 * @return
	 */
	public String parseStartTime()
	{
		String timeReg = "[0-1]?[0-9]:[0-5][0-9]\\s+[A|a|P|p][m|M]$";
		Pattern p = Pattern.compile(timeReg);
		Matcher m = p.matcher(notification.getText());
		while (m.find())
		{
			return m.group(0);
		}
		return "";
	}
}
