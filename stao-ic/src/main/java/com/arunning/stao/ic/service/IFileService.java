package com.arunning.stao.ic.service;

import com.arunning.stao.ic.model.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author chenliangliang
 * @since 2019-03-27
 */
public interface IFileService extends IService<File> {

    File getByUid(String uid);
}
