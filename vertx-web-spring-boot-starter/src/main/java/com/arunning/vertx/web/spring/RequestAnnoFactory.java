package com.arunning.vertx.web.spring;

import com.arunning.vertx.web.spring.annotation.*;

import java.lang.annotation.Annotation;


/**
 * @author chenliangliang
 * @date 2019/3/22
 */
public class RequestAnnoFactory {


    public static RequestAnno getRequestAnno(Annotation annotation) {
        if (annotation instanceof RequestParam) {
            return new RequestParamAnno((RequestParam) annotation);
        } else if (annotation instanceof PathParam) {
            return new PathParamAnno((PathParam) annotation);
        } else if (annotation instanceof SessionAttr) {
            return new SessionAttrAnno((SessionAttr) annotation);
        } else if (annotation instanceof RequestHeader) {
            return new RequestHeaderAnno((RequestHeader) annotation);
        }else if(annotation instanceof CookieValue){
            return new CookieValueAnno((CookieValue) annotation);
        } else {
            throw new UnsupportedOperationException("Unsupported this annotation+" + annotation);
        }

    }


    public interface RequestAnno {

        String value();

        boolean required();

        String defaultValue();
    }


    private static class RequestParamAnno implements RequestAnno {

        RequestParam requestParam;

        RequestParamAnno(RequestParam requestParam) {
            this.requestParam = requestParam;
        }

        @Override
        public String value() {
            return requestParam.value();
        }

        @Override
        public boolean required() {
            return requestParam.required();
        }

        @Override
        public String defaultValue() {
            return requestParam.defaultValue();
        }
    }

    private static class PathParamAnno implements RequestAnno {

        PathParam pathParam;

        PathParamAnno(PathParam pathParam) {
            this.pathParam = pathParam;
        }

        @Override
        public String value() {
            return pathParam.value();
        }

        @Override
        public boolean required() {
            return pathParam.required();
        }

        @Override
        public String defaultValue() {
            return pathParam.defaultValue();
        }
    }

    private static class SessionAttrAnno implements RequestAnno {

        SessionAttr sessionAttr;

        SessionAttrAnno(SessionAttr sessionAttr) {
            this.sessionAttr = sessionAttr;
        }

        @Override
        public String value() {
            return sessionAttr.value();
        }

        @Override
        public boolean required() {
            return sessionAttr.required();
        }

        @Override
        public String defaultValue() {
            return sessionAttr.defaultValue();
        }
    }

    private static class RequestHeaderAnno implements RequestAnno {

        RequestHeader requestHeader;

        RequestHeaderAnno(RequestHeader requestHeader) {
            this.requestHeader = requestHeader;
        }

        @Override
        public String value() {
            return requestHeader.value();
        }

        @Override
        public boolean required() {
            return requestHeader.required();
        }

        @Override
        public String defaultValue() {
            return requestHeader.defaultValue();
        }
    }

    private static class CookieValueAnno implements RequestAnno {

        CookieValue cookieValue;

        CookieValueAnno(CookieValue cookieValue) {
            this.cookieValue = cookieValue;
        }

        @Override
        public String value() {
            return cookieValue.value();
        }

        @Override
        public boolean required() {
            return cookieValue.required();
        }

        @Override
        public String defaultValue() {
            return cookieValue.defaultValue();
        }
    }
}
