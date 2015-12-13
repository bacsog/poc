package com.swissquote.voomer.utils;

import org.junit.Assert;
import org.junit.Test;

import utils.DemoUtils;

public class DemoUtilsTest {

	@Test
	public void getRandomCurrency() {
		System.out.println(DemoUtils.randomCurrency());
		Assert.assertEquals("Currency symbol should be 3 char long", 3, DemoUtils.randomCurrency().length());
	}

}
