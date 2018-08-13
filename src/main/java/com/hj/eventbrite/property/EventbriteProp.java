package com.hj.eventbrite.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by David.Zheng on 2018/8/10.
 */
@Data
@Component
@ConfigurationProperties
@PropertySource("file:${user.dir}/bin/eventbrite.properties")
public class EventbriteProp
{
	private String fullRunning;

	private String pageUrl;

	private String maxPayment;

	private String firstName;

	private String lastName;

	private String emailAddress;

	private String ccType;

	private String	ccNum;

	private String	dateMonth;

	private String	dateYear;

	private String	ccCSC;

	private String billAddress;

	private String	billCity;

	private String	billZip;
}
