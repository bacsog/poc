package utils;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Random;

import com.swissquote.voomer.domain.Instrument;

public class DemoUtils {

	public static List<Currency> currencies = new ArrayList<Currency>();

	public static Instrument randomInstrument() {
		Random r = new Random();
		Instrument.InstrumentBuilder b = new Instrument.InstrumentBuilder();
		b.withBase(DemoUtils.randomCurrency()) //
				.withTerm(DemoUtils.randomCurrency()) //
				.withExposure(r.nextDouble()) //
				.withMargin(1000 * r.nextDouble() % 100);
		return b.build();
	}

	public static String randomCurrency() {
		// super-lazy init. Mom, Dad... Sorry.
		if (currencies.size() == 0) {
			currencies.addAll(Currency.getAvailableCurrencies());
		}
		return currencies.get(new Random().nextInt(currencies.size())).getSymbol();
	}

}
