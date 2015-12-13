package com.swissquote.voomer;

import org.junit.Assert;
import org.junit.Test;

import com.swissquote.App;

public class AppTest {

	@Test
	public void test() {
		Assert.assertEquals("I can't believe I broke this", "Voomer", App.appName());
	}

}
