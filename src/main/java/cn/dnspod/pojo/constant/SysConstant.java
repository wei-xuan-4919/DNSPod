package cn.dnspod.pojo.constant;

/**
 * @author weixuan
 * @date 2022/10/26 11:16
 * @Description:
 */
public class SysConstant {

    /**
     * 状态
     */
    public interface STATE {

        /**
         * 启用
         */
        int ENABLE = 1;

        /**
         * 禁用
         */
        int DISABLE = 2;

        /**
         * 成功
         */
        int SUCCESS = 1;

        /**
         * 失败
         */
        int FAIL = 2;
    }

    /**
     * Job任务名称
     */
    public static final String JOB_NAME = "Job";
}
