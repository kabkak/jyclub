package com.jiangying.pojo.result;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 后端统一返回结果
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    private Integer code; //编码：200成功，0和其它数字为失败
    private String msg; //错误信息
    private T data; //数据

//    private Map map = new HashMap();//动态数据
    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 200;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 200;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }


//    public Result<T> add(String key,Object value){
//
//        this.map.put(key,value);
//
//        return this;
//    }
}
