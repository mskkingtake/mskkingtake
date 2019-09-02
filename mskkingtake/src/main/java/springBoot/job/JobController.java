package jee.boot.module.job;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jee.boot.common.basic.CrudController;
import jee.boot.common.basic.result.ResponseMessage;
import jee.boot.common.basic.result.Result;
import jee.boot.module.businessdata.entity.Report;
import jee.boot.module.businessdata.service.RemindDetailsService;
import jee.boot.module.businessdata.service.ReportService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 报告 RestController
 * @zhanghc
 * @version 2019-07-09
 */
@Api(tags="定时任务")
@RestController
@RequestMapping(value = "/businessdata/job")
public class JobController extends CrudController<ReportService, Report> implements SchedulingConfigurer {

    @Autowired
    RemindDetailsService remindDetailsService;

    /**
     * 方案一：注解定时任务
     * @throws Exception
     */
    @Scheduled(cron = "0 0/1 * * * ?") // 每分钟执行一次
    public void work() throws Exception {
        System.out.println("执行注解定时任务：" + new Date());

        // 调用发送逻辑
        this.remindDetailsService.sendRemindInfo();
    }

    /**
     * 方案二：动态读取cron执行定时任务
     * 执行定时任务.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(

            //1.添加任务内容(Runnable)
            () -> {
                System.out.println("执行动态定时任务: " + LocalDateTime.now().toLocalTime());
                try {
                    this.remindDetailsService.sendRemindInfo();
                } catch(Exception e) {
                    e.printStackTrace();
                }

                System.out.println("执行完成");
            },

            //2.设置执行周期(Trigger)
            triggerContext -> {
                //2.1 从数据库获取执行周期
//                  String cron = cronMapper.getCron();
                String cron = "30 * * * * ?";

                //2.2 合法性校验.
                if (StringUtils.isBlank(cron)) {
                    // Omitted Code ..
                }
                //2.3 返回执行周期(Date)
                return new CronTrigger(cron).nextExecutionTime(triggerContext);
            }
        );
    }
}