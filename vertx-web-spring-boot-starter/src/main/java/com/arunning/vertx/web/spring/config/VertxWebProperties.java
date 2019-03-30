package com.arunning.vertx.web.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;

/**
 * @author chenliangliang
 * @date 2019/3/22
 */
@ConfigurationProperties(prefix = "vertx.web")
public class VertxWebProperties implements Serializable {

    private static final long serialVersionUID = 8701584394991214935L;

    private int workPoolSize;

    private int connectTimeout;

    private int port = 8070;

    private boolean log = false;

    @NestedConfigurationProperty
    private Session session = new Session();

    @NestedConfigurationProperty
    private Body body = new Body();

    public int getWorkPoolSize() {
        return workPoolSize;
    }

    public void setWorkPoolSize(int workPoolSize) {
        this.workPoolSize = workPoolSize;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isLog() {
        return log;
    }

    public void setLog(boolean log) {
        this.log = log;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public class Session {

        private boolean enable = false;

        private int timeoutSeconds = 60 * 30;

        public int getTimeoutSeconds() {
            return timeoutSeconds;
        }

        public void setTimeoutSeconds(int timeoutSeconds) {
            this.timeoutSeconds = timeoutSeconds;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }

    public class Body {

        private long limit = 65525;

        private boolean deleteUploadedFilesOnEnd = true;

        private String uploadsDirectory;

        /**
         * merge any form attributes into the request parameters
         */
        private boolean mergeFormAttributes = true;

        public long getLimit() {
            return limit;
        }

        public void setLimit(long limit) {
            this.limit = limit;
        }

        public boolean isDeleteUploadedFilesOnEnd() {
            return deleteUploadedFilesOnEnd;
        }

        public void setDeleteUploadedFilesOnEnd(boolean deleteUploadedFilesOnEnd) {
            this.deleteUploadedFilesOnEnd = deleteUploadedFilesOnEnd;
        }

        public String getUploadsDirectory() {
            return uploadsDirectory;
        }

        public void setUploadsDirectory(String uploadsDirectory) {
            this.uploadsDirectory = uploadsDirectory;
        }

        public boolean isMergeFormAttributes() {
            return mergeFormAttributes;
        }

        public void setMergeFormAttributes(boolean mergeFormAttributes) {
            this.mergeFormAttributes = mergeFormAttributes;
        }
    }
}
