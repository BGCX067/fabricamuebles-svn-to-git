package ar.edu.utn.sigmaproject.view.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * User: Gian Franco Zabarino
 * Date: 24/10/12
 */
public class RoundingUtils {

    public final static int WOOD_DECIMALS = 1;
    public final static int MONEY_DECIMALS = 2;

    public static BigDecimal roundWoodSize(BigDecimal size) {
        if (size != null) {
            size = size.setScale(WOOD_DECIMALS, RoundingMode.HALF_EVEN);
        }
        return size;
    }

    public static BigDecimal roundMoneyValue(BigDecimal moneyValue) {
        if (moneyValue != null) {
            moneyValue = moneyValue.setScale(WOOD_DECIMALS, RoundingMode.HALF_EVEN);
        }
        return moneyValue;
    }
}
