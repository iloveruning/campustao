package com.arunning.stao.ic.service.impl;


import com.arunning.stao.ic.mapper.FileMapper;
import com.arunning.stao.ic.model.entity.File;
import com.arunning.stao.ic.service.IFileService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author chenliangliang
 * @since 2019-03-27
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {


    @Override
    public File getByUid(String uid) {
        return this.getOne(new QueryWrapper<File>().lambda()
                .eq(File::getUid, uid)
                );
    }
}
