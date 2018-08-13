package com.hj.eventbrite.page;

import com.hj.eventbrite.property.EventbriteProp;
import com.hj.selenium.page.AbstractPage;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by David.Zheng on 2018/8/10.
 */
@Log
public class PaymentPage extends AbstractPage
{
	@FindBy(id = "total_amount_due_0")
	private WebElement total_amount_due_0;

	@FindBy(id = "first_name")
	private WebElement first_name;

	@FindBy(id = "last_name")
	private WebElement last_name;

	@FindBy(id = "email_address")
	private WebElement email_address;

	@FindBy(id = "confirm_email_address")
	private WebElement confirm_email_address;

	@FindBy(id = "cc_type")
	private WebElement cc_type;

	@FindBy(id = "cc_num")
	private WebElement cc_num;

	@FindBy(id = "expiration-date-month")
	private WebElement dateMonth;

	@FindBy(id = "expiration-date-year")
	private WebElement dateYear;

	@FindBy(id = "cc_csc")
	private WebElement cc_csc;

	@FindBy(id = "bill_country")
	private WebElement bill_country;

	@FindBy(id = "bill_address_1")
	private WebElement bill_address_1;

	@FindBy(id = "bill_city")
	private WebElement bill_city;

	@FindBy(id = "bill_state_au")
	private WebElement bill_state_au;

	@FindBy(id = "bill_zip")
	private WebElement bill_zip;

	@FindBy(css = "a[data-automation='pay-button']")
	private WebElement primary_cta;

	/**
	 *
	 * @param eventbriteProp
	 */
	public void fulfillInputs(EventbriteProp eventbriteProp)
	{
		float userDefPrice = Float.valueOf(eventbriteProp.getMaxPayment());
		float amount = Float.valueOf(StringUtils.substringAfter(total_amount_due_0.getText(), "$").trim());
		if(amount > userDefPrice) {
			log.warning("********预付金额大于设置金额*************");
			System.exit(1);
		}

		first_name.sendKeys(eventbriteProp.getFirstName());
		last_name.sendKeys(eventbriteProp.getLastName());
		email_address.sendKeys(eventbriteProp.getEmailAddress());
		confirm_email_address.sendKeys(eventbriteProp.getEmailAddress());
		new Select(cc_type).selectByValue(eventbriteProp.getCcType());
		cc_num.sendKeys(eventbriteProp.getCcNum());
		new Select(dateMonth).selectByValue(eventbriteProp.getDateMonth());
		new Select(dateYear).selectByValue(eventbriteProp.getDateYear());
		cc_csc.sendKeys(eventbriteProp.getCcCSC());
		new Select(bill_country).selectByValue("AU");
		bill_address_1.sendKeys(eventbriteProp.getBillAddress());
		bill_city.sendKeys(eventbriteProp.getBillCity());
		new Select(bill_state_au).selectByValue("VIC");
		bill_zip.sendKeys(eventbriteProp.getBillZip());
	}

	/**
	 * click 'Pay Now' button
	 */
	public void payTicket() {
		primary_cta.click();
	}

}
