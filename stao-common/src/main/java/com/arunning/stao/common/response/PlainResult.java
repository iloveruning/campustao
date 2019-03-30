package com.arunning.stao.common.response;

import com.arunning.stao.common.exception.BizException;

import java.util.Objects;

/**
 * @author chenliangliang
 * @date 2019/3/29
 */
public class PlainResult<T> extends BaseResult {

    private static final long serialVersionUID = -655729927513882452L;
    private T data;

    public PlainResult() {
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> PlainResult<T> success(T data) {
        PlainResult<T> result = new PlainResult<>();
        result.setCode(CommonResultCode.SUCCESS.code);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static PlainResult<Boolean> success() {
        return success(Boolean.TRUE);
    }

    public static <T> PlainResult<T> error(Exception e) {
        int errorCode = -100;
        if (e instanceof BizException) {
            errorCode = ((BizException)e).getErrorCode();
        }

        String message = e.getMessage();
        return error(errorCode, message);
    }

    public static <T> PlainResult<T> error(int errorCode, String message) {
        PlainResult<T> result = new PlainResult<>();
        result.setSuccess(false);
        result.setCode(errorCode);
        result.setMessage(message);
        return result;
    }

    @Override
    public String toString() {
        return "PlainResult(super=" + super.toString() + ", data=" + this.data + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlainResult)) return false;
        if (!super.equals(o)) return false;
        PlainResult<?> that = (PlainResult<?>) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
