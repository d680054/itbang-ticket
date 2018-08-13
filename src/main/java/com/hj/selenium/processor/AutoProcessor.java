package com.hj.selenium.processor;

import org.openqa.selenium.WebDriver;

/**
 * Created by David.Zheng on 2018/8/11.
 */
public interface AutoProcessor
{

	/**
	 *
	 * simulate all the manual steps, put the business logics here
	 *
	 */
	void  process();

	/**
	 * automatically initialize each page.
	 *
	 * @param driver
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	void initPageInstance(final WebDriver driver) throws IllegalAccessException, NoSuchFieldException;

}
