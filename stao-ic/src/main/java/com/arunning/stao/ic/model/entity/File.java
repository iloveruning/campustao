package com.arunning.stao.ic.model.entity;

import com.arunning.stao.ic.model.enums.FileTypeEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author chenliangliang
 * @since 2019-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class File extends BaseEntity {

    private static final long serialVersionUID = 1160590983209002214L;
    /**
     * 文件名
     */
    @TableField("name")
    private String name;

    /**
     * 文件大小
     */
    @TableField("size")
    private Long size;

    /**
     * 文件类型：0->所有，1->图片，2->视频，3->文档doc
     */
    @TableField("type")
    private FileTypeEnum type;

    /**
     * 唯一id
     */
    @TableField("uid")
    private String uid;

    /**
     * 访问链接
     */
    @TableField("url")
    private String url;


    public static String parseUidFromUrl(String url) {
        if (url == null || (url = url.trim()).length() == 0) {
            throw new IllegalArgumentException("url is null");
        }

        int li = url.lastIndexOf("/");

        if (li < 0) {
            throw new IllegalArgumentException("illegal url : " + url);
        }

        String fileName = url.substring(li + 1);

        int i = fileName.lastIndexOf(".");

        if (i < 0) {
            return fileName;
        }

        return fileName.substring(0, i);
    }


}
