package com.arunning.stao.ic.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;

/**
 * @author chenliangliang
 * @date 2019/3/28
 */
@AllArgsConstructor
public enum FileTypeEnum implements IEnum<Integer> {

    ALL(0),
    IMAGE(1),
    VIDEO(2),
    DOC(3),
    OTHER(4);


    private Integer value;

    @Override
    public Integer getValue() {
        return this.value;
    }
}
