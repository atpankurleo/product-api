package redsky.exception;

import javax.ws.rs.BadRequestException;
import java.util.HashMap;
import java.util.Map;

public class MyRetailException extends BadRequestException {
    Map<String, String> errorParams;
    public enum ErrorCode {
        PRODUCT_NOT_FOUND
    }

    MyRetailException() {
        super();
    }
    public MyRetailException(ErrorCode errorCode, Map<String, String> errorparams) {
        super(errorCode.toString());
        this.errorParams = new HashMap<>(errorparams);
    }
}
