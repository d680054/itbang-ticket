package com.hj.selenium.page;

import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

/**
 * Created by David.Zheng on 1/05/2014.
 */
@Data
public abstract class AbstractPage
{
	private WebDriver driver;

	private Wait wait;

	public void openPage(String url)
	{
		driver.get(url);
	}

	public String getTitle()
	{
		return getDriver().getTitle();
	}

	public void closeBrowser()
	{
		driver.close();
	}

	//--------------------copied from selenium framework project-----------------------------
	protected WebElement cssSelector(String css)
	{
		By by = By.cssSelector(css);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));

		return getDriver().findElement(by);
	}

	protected WebElement xpathSelector(String xpath)
	{
		By by = By.xpath(xpath);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));

		return getDriver().findElement(by);
	}

	protected void scrollIntoView(By by)
	{
		waitForElement(by);
		((JavascriptExecutor) getDriver())
				.executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(by));
	}

	protected void waitForAjax()
	{
		wait.until(new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply(WebDriver webDriver)
			{
				return (Boolean) ((JavascriptExecutor) driver).executeScript("return (jQuery.active == 0)");
			}
		});

	}

	protected void waitForElement(By by)
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	protected void hardWait(int second)
	{
		try
		{
			Thread.sleep(second * 1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
