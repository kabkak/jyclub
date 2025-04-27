package com.jiangying.pojo.entry;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;



/**
 * Comment 实体类，用于映射MongoDB中的 comment 集合。
 */
@Document(collection = "InterviewsMemory")
@Data
public class InterviewsMemory {

    /**
     * MongoDB文档的唯一标识符，作为主键使用。
     */
    @Id
    private ObjectId id;

    private String MemoryId;
    /**
     * 用户与大模型沟通的内容。
     */
    private String content;

    /**
     * 用户唯一标识符。
     */
    @Field("user_id")
    private String userId;


}
