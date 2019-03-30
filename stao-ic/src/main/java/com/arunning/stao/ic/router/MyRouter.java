package com.arunning.stao.ic.router;

import com.arunning.stao.ic.model.entity.File;
import com.arunning.stao.ic.model.enums.FileTypeEnum;
import com.arunning.stao.ic.model.oss.OSSResult;
import com.arunning.stao.ic.service.IFileService;
import com.arunning.stao.ic.service.OSSService;
import com.arunning.stao.ic.util.FileTypeUtil;
import com.arunning.vertx.web.spring.annotation.*;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author chenliangliang
 * @date 2019/3/22
 */
@RestRouter("/my/")
@Slf4j
public class MyRouter {

    @Autowired
    private OSSService ossService;

    @Autowired
    private IFileService fileService;


    @RouteMapping(url = "hello/", order = 1)
    public Object hello(HttpServerRequest request) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("1", "2");
        map.put("uri", request.uri());
        return map;
    }

    @RouteMapping(url = "/pathParam/:name")
    public Object pathParam(HttpServerRequest request, @PathParam String name) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("1", "2");
        map.put("uri", request.uri());
        map.put("name", name);
        return map;
    }

    @RouteMapping(url = "/exception")
    public Object exception(HttpServerRequest request) {
        throw new RuntimeException("我是异常");
    }

    @RouteMapping(url = "/session")
    public Object session(Session session, @RequestParam(defaultValue = "defaultValue") String name, @CookieValue("vertx-web.session") String sessionId) {

        session.put(name, name);
        session.put("sid", sessionId);
        return session.data();
    }


    @RouteMapping(url = "/upload", method = HttpMethod.POST)
    public Object upload(FileUpload fileUpload) throws ExecutionException, InterruptedException {

        Map<String, Object> map = new HashMap<>(8);

        map.put("name", fileUpload.name());
        map.put("filename", fileUpload.fileName());
        map.put("charSet", fileUpload.charSet());
        map.put("size", fileUpload.size());
        map.put("uploadedFileName", fileUpload.uploadedFileName());
        map.put("contentTransferEncoding", fileUpload.contentTransferEncoding());

        File file = new File();

        Future<OSSResult> future = ossService.putObject(fileUpload.uploadedFileName(), objName -> {

            System.out.println("objName=" + objName);

            file.setUid(objName);

            return objName + "." + FileTypeUtil.getFileType(fileUpload.fileName());

        });


        OSSResult result = future.get();

        result.setUrl("http" + result.getUrl().substring(5));



        file.setName(fileUpload.fileName());
        file.setSize(fileUpload.size());
        file.setUrl(result.getUrl());

        file.setType(FileTypeEnum.DOC);

        boolean save = fileService.save(file);

        map.put("result", result);

        map.put("saved", save);
        map.put("file", fileService.getByUid(file.getUid()));

        return map;
    }

}
