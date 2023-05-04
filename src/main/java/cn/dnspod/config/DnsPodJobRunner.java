package cn.dnspod.config;

import cn.dnspod.service.IDnsPodJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @className: DnsPodJobRunner
 * @author: weixuan
 * @date: 2023/5/4 10:30
 **/
@Component
public class DnsPodJobRunner implements ApplicationRunner {

    @Autowired
    private IDnsPodJobService dnsPodJobService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 启动时添加定时任务
        dnsPodJobService.addDnsPodJob();
    }
}
