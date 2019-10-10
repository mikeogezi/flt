package mikeogezi.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ApplicationUtils {

    private static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static String generateTransactionId () {
        return String.format("%s-%s", dateFormat.format(Calendar.getInstance().getTime()), UUID.randomUUID().toString());
    }

    public static List<BigDecimal> getAmountList (int count) {
        List<BigDecimal> amountList = new ArrayList<>(count);

        for (int i = 0; i < count; ++i) {
            amountList.add(i, new BigDecimal(new Random().nextInt(1000)));
        }

        return amountList;
    }

    public static List<String> getTransactionIdList (int count) {
        List<String> transactionList = new ArrayList<>();

        for (int i = 0; i < count; ++i) {
            transactionList.add(generateTransactionId());
        }

        return transactionList;
    }

}
