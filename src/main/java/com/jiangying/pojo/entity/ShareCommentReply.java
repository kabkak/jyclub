package com.jiangying.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("share_comment_reply")
public class ShareCommentReply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    private Integer momentId;
    private Integer replyType;
    private Long toId;
    private String toUser;
    private Integer toUserAuthor;
    private Long replyId;
    private String replyUser;
    private Integer replayAuthor;
    private String content;
    private String picUrls;
    @TableId
    private Integer parentId;
    private String leafNode;
    private String children;
    private String rootNode;

    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;
} 