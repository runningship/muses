package com.jm.muses.test;

import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.jm.muses.controller.trade.purchase.PurchaseController;

public class VUserTest {

	@Test
	public void testAddUser() throws Exception{
		XmlWebApplicationContext context = new XmlWebApplicationContext();
		context.setConfigLocations(new String[]{"classpath:spring/ApplicationContext.xml" , "classpath:spring/ApplicationContext-mvc.xml"});
		context.refresh();
		context.start();
		PurchaseController controller =context.getBean(PurchaseController.class);
		controller.save();
	}
}
