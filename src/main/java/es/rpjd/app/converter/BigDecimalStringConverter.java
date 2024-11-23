package es.rpjd.app.converter;

import java.math.BigDecimal;

import javafx.util.StringConverter;

public class BigDecimalStringConverter extends StringConverter<BigDecimal> {

	@Override
	public String toString(BigDecimal object) {
		return object != null ? object.toString() : "";
	}

	@Override
	public BigDecimal fromString(String string) {
        if (string == null || string.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(string);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO; // Manejar valores no válidos
        }
	}

}
