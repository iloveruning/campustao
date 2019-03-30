package com.arunning.stao.msg.api.res;

import com.arunning.stao.common.response.PlainResult;
import com.arunning.stao.msg.api.res.model.param.PutResourceRequest;
import com.arunning.stao.msg.api.res.model.vo.PutResourceVO;

/**
 * @author chenliangliang
 * @date 2019/3/28
 */
public interface ResourceFacade {

    PlainResult<PutResourceVO> putResource(PutResourceRequest request);


}
