package com.arunning.stao.ic.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.arunning.stao.ic.model.oss.OSSResult;
import com.arunning.stao.ic.service.OSSService;
import com.arunning.stao.ic.util.FileTypeUtil;
import com.arunning.stao.ic.util.RandomUtil;
import io.netty.util.NettyRuntime;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author chenliangliang
 * @date 2019/3/25
 */
@Service
public class OSSServiceImpl implements OSSService, InitializingBean, DisposableBean {

    private static final String ENDPOINT = "http://oss-cn-shanghai.aliyuncs.com";

    private static final String ACCESSKEY = "LTAIXf0Fdyykl2U2";

    private static final String ACCESS_SECRET = "Ge72ZnbXFIL1e2JLch7keSvACo7hor";

    private static final String BUCKET_NAME = "static-bkt";

    private static final String BASE_URL = "https://" + BUCKET_NAME + ".oss-cn-shanghai.aliyuncs.com/";

    private static final String IMAGE_PATH = "images/";

    private static final String VIDEO_PATH = "video/";

    private static final String FILE_PATH = "file/";

    private OSS oss;

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, NettyRuntime.availableProcessors() * 2, 30, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(2), new BasicThreadFactory.Builder().namingPattern("oss-pool-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());


    @Override
    public String getBucketName() {
        return BUCKET_NAME;
    }

    @Override
    public Future<OSSResult> putObject(String objName, String file, Function<String, String> objNameProcessor) {
        return this.executor.submit(() -> wrapResult(objName, name -> oss.putObject(getBucketName(), name, new File(file)), objNameProcessor));
    }


    @Override
    public Future<OSSResult> putObject(String file, Function<String, String> objNameProcessor) {
        return this.executor.submit(() -> {

            String objName = RandomUtil.dateRandom();

            return wrapResult(objName, name -> oss.putObject(getBucketName(), name, new File(file)), objNameProcessor);
        });
    }

    @Override
    public Future<OSSResult> putObject(String objName, InputStream in, Function<String, String> objNameProcessor) {
        return this.executor.submit(() -> wrapResult(objName, name -> oss.putObject(getBucketName(), name, in), objNameProcessor));
    }

    @Override
    public Future<OSSResult> putObject(InputStream in, Function<String, String> objNameProcessor) {
        return this.executor.submit(() -> wrapResult(name -> oss.putObject(getBucketName(), name, in), null));
    }

    @Override
    public Future<OSSResult> getObject(String objName) {
        return null;
    }

    @Override
    public Future<OSSResult> deleteObject(String objName) {
        return this.executor.submit(() -> wrapResult(objName, name -> oss.deleteObject(getBucketName(), name), null));
    }

    @Override
    public void destroy() throws Exception {
        this.oss.shutdown();
        this.executor.shutdownNow();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.oss = new OSSClientBuilder().build(ENDPOINT, ACCESSKEY, ACCESS_SECRET);
    }

    private String processObjName(final String objName, Function<String, String> objNameProcessor) {

        String objectName = objName;

        if (objNameProcessor != null) {
            objectName = objNameProcessor.apply(objectName);
        }

        if (objectName.startsWith("/")) {
            objectName = objectName.substring(1);
        }

        if (objectName.endsWith("/")) {
            objectName = objectName.substring(0, objectName.length() - 1);
        }

        String fileType = FileTypeUtil.getFileType(objectName);

        if (FileTypeUtil.isImage(fileType)) {
            objectName = IMAGE_PATH + objectName;
        } else if (FileTypeUtil.isVideo(fileType)) {
            objectName = VIDEO_PATH + objectName;
        } else {
            objectName = FILE_PATH + objectName;
        }
        return objectName;
    }


    private OSSResult wrapResult(final String objName, Consumer<String> consumer, Function<String, String> objNameProcessor) {

        String objectName = objName;

        OSSResult result = new OSSResult(true);
        result.setObjectName(objectName);
        try {
            objectName = processObjName(objName, objNameProcessor);
            result.setObjectName(objectName);
            consumer.accept(objectName);
            result.setUrl(getUrl(objectName));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCause(e);
        }
        return result;
    }

    private OSSResult wrapResult(Consumer<String> consumer, Function<String, String> objNameProcessor) {
        String objName = FILE_PATH + RandomUtil.dateRandom();
        return wrapResult(objName, consumer, objNameProcessor);
    }

    private String getUrl(String objName) {
        return BASE_URL + objName;
    }
}
