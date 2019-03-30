package com.arunning.stao.ic.model.oss;

import lombok.Data;
import lombok.ToString;

/**
 * @author chenliangliang
 * @date 2019/3/25
 */
@Data
@ToString
public class OSSResult {

    private boolean success;

    private String objectName;

    private String url;

    private Throwable cause;

    public OSSResult(boolean success) {
        this.success = success;
    }

    public OSSResult() {
    }
}
