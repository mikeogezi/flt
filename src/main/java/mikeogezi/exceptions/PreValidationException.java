package mikeogezi.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class PreValidationException extends Exception {

    private static Logger logger = LoggerFactory.getLogger(PreValidationException.class);

    public PreValidationException (Map<String, String> validationErrors) {
        for (String key: validationErrors.keySet()) {
            String message = validationErrors.get(key);
            logger.info("{}: {}", key, message);
        }
    }

}
