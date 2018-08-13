package com.hj.selenium.processor;

import com.hj.selenium.annotation.Page;
import com.hj.selenium.page.AbstractPage;
import lombok.extern.java.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Created by David.Zheng on 2018/8/11.
 */
@Log
public abstract class AbstractAutoProcessor implements AutoProcessor
{
	/**
	 * @param driver
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	@Override public void initPageInstance(final WebDriver driver) throws IllegalAccessException, NoSuchFieldException
	{
		final Object o = this;

		ReflectionUtils.doWithFields(this.getClass(), new ReflectionUtils.FieldCallback()
		{
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException
			{
				Page page = field.getAnnotation(Page.class);
				if (page != null)
				{
					Class clazz = field.getType();
					AbstractPage p = (AbstractPage) PageFactory.initElements(driver, clazz);
					p.setDriver(driver);
					p.setWait(new WebDriverWait(driver, 60));
					field.setAccessible(true);
					field.set(o, p);
					log.info("initialize the page instance");
				}
			}
		});
	}
}
