package mikeogezi.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class DefaultUtils {

    public static int count = 5;

    public static List<String> accountNumbers = Arrays.asList("0123456789", "0178647729", "0234598765", "1122334455", "0987654321");

    public static List<String> bankCodes = Arrays.asList("058", "058", "058", "076", "076");

    public static List<BigDecimal> amounts = ApplicationUtils.getAmountList(count);

    public static List<String> transactionIds = getTransactionIdList(count);

    private static List<String> getTransactionIdList (int count) {
        List<String> list = ApplicationUtils.getTransactionIdList(count - 1);
        list.add("10-10-2019-11740697-ed92-43fc-9d96-81c41ba3a77d");
        return list;
    }

}
