package com.arunning.vertx.web.spring;

import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * @author chenliangliang
 * @date 2019/3/23
 */
@Component
public class FileUploadRouterMethodArgumentResolver extends AbstractClassTypeRouterMethodArgumentResolver<FileUpload> {

    @Override
    public Object resolveArgument(MethodParameter parameter, RoutingContext ctx) throws Exception {

        Iterator<FileUpload> it = ctx.fileUploads().iterator();

        if (it.hasNext()) {
            return it.next();
        }

        return null;
    }
}
