package com.arunning.stao.common.response;

import com.arunning.stao.common.request.BaseRequest;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author chenliangliang
 * @date 2019/3/29
 */
public class BaseResult implements Serializable {

    private static final long serialVersionUID = 6986854056284429254L;

    private boolean success;
    private int code;
    private String message;
    private String requestId;

    public BaseResult(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public BaseResult() {
        this(true, CommonResultCode.SUCCESS.code, CommonResultCode.SUCCESS.message);
    }

    public void setErrorMessage(int code, String message) {
        this.code = code;
        this.success = false;
        this.message = message;
    }

    public void setErrorMessage(IErrorCode code, Object... args) {
        this.code = code.getCode();
        this.success = false;
        this.message = String.format(code.getMessage(), args);
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setRequestId(BaseRequest request) {
        this.requestId = request.getRequestId();
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseResult)) return false;
        BaseResult that = (BaseResult) o;
        return success == that.success &&
                code == that.code &&
                Objects.equals(message, that.message) &&
                Objects.equals(requestId, that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, code, message, requestId);
    }
}
