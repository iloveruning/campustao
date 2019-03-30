package com.arunning.stao.ic.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author chenliangliang
 * @date 2019/2/27
 */
public class AliOSS {

    private static final String ENDPOINT ="http://oss-cn-hangzhou.aliyuncs.com";

    private static final String ACCESSKEY ="LTAIXf0Fdyykl2U2";

    private static final String ACCESS_SECRET ="Ge72ZnbXFIL1e2JLch7keSvACo7hor";

    private static final String BUCKET_NAME ="image-bkt";

    private static final String OBJECT_NAME ="images/test";

    private static OSS oss;

    static {
        OSSClientBuilder builder=new OSSClientBuilder();
        oss = builder.build(ENDPOINT, ACCESSKEY, ACCESS_SECRET);
    }

    public static void main(String[] args) throws Exception {

        putObject();

        //deleteObject();

       // getObject();

        oss.shutdown();
    }

    public static void putObject() throws Exception {

        PutObjectResult result = oss.putObject(BUCKET_NAME, OBJECT_NAME, new File("C:\\Users\\Shinelon\\Pictures\\Saved Pictures\\5f68e4c19a79b56b2369c149b620b4ce.jpg"));

        System.out.println(result.getETag());

    }

    public static void deleteObject(){

        oss.deleteObject(BUCKET_NAME,OBJECT_NAME);
    }

    public static void getObject() throws Exception{

        OSSObject object = oss.getObject(BUCKET_NAME, OBJECT_NAME);

        String contentType = object.getObjectMetadata().getContentType();

        String type=contentType.substring(contentType.indexOf("/")+1);

        InputStream objectContent = object.getObjectContent();

        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Shinelon\\Pictures\\Saved Pictures\\"+OBJECT_NAME+"."+type);

        byte[] bytes=new byte[1024];

        int len;

        while ((len=objectContent.read(bytes))>0){
            outputStream.write(bytes,0,len);
        }

        outputStream.flush();

        outputStream.close();

        objectContent.close();

        System.err.println(contentType);

    }
}
