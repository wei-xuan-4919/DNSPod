package cn.dnspod.pojo.constant;

/**
 * TODO
 *
 * @className: DdnsPodConstant
 * @author: weixuan
 * @date: 2023/4/23 15:16
 **/
public class DnsPodConstant {

    /**
     * 配置参数
     */
    public interface CONFIG {
        /**
         * 登陆token
         */
        String TOKEN = "token";

        /**
         *域名
         */
        String DOMAIN = "domain";

        /**
         * 子域名
         */
        String SUB_DOMAIN = "sub_domain";
    }


    /**
     * 请求地址
     */
    public interface URL {

        /**
         * 获取域名解析记录列表
         * login_token   登陆token
         * format        返回的数据格式（json）
         * domain        域名
         * sub_domain    子域名
         * record_type    类型
         */
        String RECORD_LIST = "https://dnsapi.cn/Record.List";


        /**
         * 修改域名记录值
         * login_token  登陆token
         * format       返回的数据格式（json）
         * domain       域名
         * record_id    记录id
         * sub_domain   子域名
         * record_type  记录类型
         * record_line  记录线路 （默认）
         * value        记录值（IP地址）
         */
        String RECORD_MODIFY = "https://dnsapi.cn/Record.Modify";
    }

    /**
     * 动态域名解析的token
     */
    public static final String DNS_POD_TOKEN = "token";

    /**
     * 任务cron表达式
     */
    public static final String DNS_POD_CRON = "cron";
}
