package cn.dnspod.service.impl;

import cn.dnspod.job.DnsPodJobHandler;
import cn.dnspod.pojo.constant.DnsPodConstant;
import cn.dnspod.pojo.constant.SysConstant;
import cn.dnspod.pojo.po.ConfigPO;
import cn.dnspod.service.IConfigService;
import cn.dnspod.service.IDnsPodJobService;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @className: DnsPodJobServiceImpl
 * @author: weixuan
 * @date: 2023/5/4 10:20
 **/
@Service
public class DnsPodJobServiceImpl implements IDnsPodJobService {

    private static final Logger logger = LoggerFactory.getLogger(DnsPodJobServiceImpl.class);

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private IConfigService configService;

    private static final String DNS_POD = "DNS_POD";


    @Override
    public void addDnsPodJob() {
        // 判断cron表达式是否正确
        ConfigPO cronConfig = configService.findConfigByKey(DnsPodConstant.DNS_POD_CRON);
        if (!CronExpression.isValidExpression(cronConfig.getConfigValue())) {
            logger.error("Cron表达式格式不正确");
            return;
        }
        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronConfig.getConfigValue());
        // 按新的表达式构建一个新的trigger
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .startNow()
                .withSchedule(scheduleBuilder)
                .build();
        try {
            JobDetail jobDetail = JobBuilder.newJob(DnsPodJobHandler.class)
                    .withIdentity(SysConstant.JOB_NAME, DNS_POD)
                    .build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
            logger.error("成功添加定时任务");
        } catch (SchedulerException e) {
            logger.error("添加定时任务出现错误：{}", e);
        }
    }

    @Override
    public void updateDnsPodJob() {
        JobKey jobKey = new JobKey(SysConstant.JOB_NAME, DNS_POD);
        JobDetail jobDetail = null;
        try {
            jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                logger.error("任务不存在：{} - {}", jobKey.getName(), jobKey.getGroup());
            }
            try {
                scheduler.deleteJob(jobKey);
            } catch (SchedulerException e) {
                logger.error("删除定时任务出错");
            }
        } catch (SchedulerException e) {
            logger.error("获取任务详细信息失败：{}", e);
        }
        addDnsPodJob();
    }


}
