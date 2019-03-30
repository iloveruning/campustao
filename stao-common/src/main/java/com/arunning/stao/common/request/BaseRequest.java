package com.arunning.stao.common.request;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author chenliangliang
 * @date 2019/3/29
 */
public class BaseRequest implements Serializable {

    private static final AtomicLong REQUEST_ID_GENERATOR = new AtomicLong(0);

    private static final long serialVersionUID = 2900045484497855536L;
    private String requestId;

    public BaseRequest(String requestId) {
        this.requestId = requestId;
    }

    public BaseRequest() {
        this.requestId = String.valueOf(REQUEST_ID_GENERATOR.getAndIncrement());
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseRequest)) return false;
        BaseRequest that = (BaseRequest) o;
        return Objects.equals(requestId, that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId);
    }

}
