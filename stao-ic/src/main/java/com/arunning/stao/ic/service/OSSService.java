package com.arunning.stao.ic.service;

import com.arunning.stao.ic.model.oss.OSSResult;

import java.io.InputStream;
import java.util.concurrent.Future;
import java.util.function.Function;

/**
 * @author chenliangliang
 * @date 2019/3/25
 */
public interface OSSService {


    String getBucketName();

    Future<OSSResult> putObject(String objName, String file,Function<String,String> objNameProcessor);


    Future<OSSResult> putObject(String file, Function<String,String> objNameProcessor);


    Future<OSSResult> putObject(String objName, InputStream in,Function<String,String> objNameProcessor);


    Future<OSSResult> putObject(InputStream in,Function<String,String> objNameProcessor);


    Future<OSSResult> getObject(String objName);


    Future<OSSResult> deleteObject(String objName);

}
