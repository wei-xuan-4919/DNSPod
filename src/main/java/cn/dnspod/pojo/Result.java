package cn.dnspod.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author weixuan
 * @date 2022/3/113:47
 * @Description: 返回的结果集
 */
@Data
public class Result {

    public static final int SUCCESS_CODE = 200;

    public static final int FAIL_CODE = 500;

    /**
     * 返回的编码
     */
    private Integer code;

    /**
     * 返回的信息
     */
    private String message;

    /**
     * 返回的数据
     */
    private Object data;

    /**
     * 返回结果是否成功
     */
    private Boolean success;

    /**
     * 返回的日志
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String logMessage;

    private Result(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.success = this.code.equals(SUCCESS_CODE);
    }

    private Result(Integer code, String message, String logMessage) {
        this.code = code;
        this.message = message;
        this.logMessage = logMessage;
        this.success = this.code.equals(SUCCESS_CODE);
    }

    private Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = this.code.equals(SUCCESS_CODE);
    }

    private Result(Integer code, String message, String logMessage, Object data) {
        this.code = code;
        this.message = message;
        this.logMessage = logMessage;
        this.data = data;
        this.success = this.code.equals(SUCCESS_CODE);
    }

    public static Result success() {
        return new Result(SUCCESS_CODE, "success");
    }
    public static Result success(String msg) {
        return new Result(SUCCESS_CODE, msg, msg);
    }
    public static Result success(String msg, String logMsg) {
        return new Result(SUCCESS_CODE, msg, logMsg);
    }

    public static <T> Result success(T data) {
        return new Result(SUCCESS_CODE, "success", data);
    }
    public static <T> Result success(String msg, String logMsg, T data) {
        return new Result(SUCCESS_CODE, msg, logMsg, data);
    }

    public static Result fail() {
        return new Result(FAIL_CODE, "error");
    }

    public static Result fail(String msg) {
        return new Result(FAIL_CODE, msg, msg);
    }

    public static Result fail(String msg, String logMsg) {
        return new Result(FAIL_CODE, msg, logMsg);
    }

    public static Result fail(int code, String msg) {
        return new Result(code, msg, msg);
    }

    public static Result fail(int code, String msg, String logMsg) {
        return new Result(code, msg, logMsg);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(4);
        map.put("code", code);
        map.put("success", success);
        map.put("message", message);
        map.put("data", data);
        return map;
    }
}
