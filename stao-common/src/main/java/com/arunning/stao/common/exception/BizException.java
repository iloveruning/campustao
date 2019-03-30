package com.arunning.stao.common.exception;

import com.arunning.stao.common.response.IErrorCode;

/**
 * @author chenliangliang
 * @date 2019/3/29
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -274887674310264440L;

    private final int errorCode;

    public BizException(int errorCode) {
        this.errorCode = errorCode;
    }

    public BizException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BizException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BizException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public BizException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
    }

    public BizException(IErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode.getCode();
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
