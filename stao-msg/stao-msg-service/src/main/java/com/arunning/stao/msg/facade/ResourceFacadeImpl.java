package com.arunning.stao.msg.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.arunning.stao.common.response.PlainResult;
import com.arunning.stao.msg.api.res.ResourceFacade;
import com.arunning.stao.msg.api.res.model.param.PutResourceRequest;
import com.arunning.stao.msg.api.res.model.vo.PutResourceVO;

/**
 * @author chenliangliang
 * @date 2019/3/29
 */
@Service(timeout = 2000)
public class ResourceFacadeImpl implements ResourceFacade {

    @Override
    public PlainResult<PutResourceVO> putResource(PutResourceRequest request) {


        return null;
    }
}
