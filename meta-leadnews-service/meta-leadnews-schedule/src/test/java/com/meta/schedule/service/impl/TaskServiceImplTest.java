package com.meta.schedule.service.impl;

import com.meta.model.schedule.dtos.Task;
import com.meta.schedule.ScheduleApplication;
import com.meta.schedule.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest(classes = ScheduleApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class TaskServiceImplTest {

    @Autowired
    private TaskService taskService;

    @Test
    public void addTask() {

        for (int i = 0; i < 5; i++) {
            Task task = new Task();
            task.setTaskType(100+i);
            task.setPriority(50);
            task.setParameters("task test".getBytes());
            task.setExecuteTime(new Date().getTime()+500*i);

            long taskId = taskService.addTask(task);
            log.info(Long.toString(taskId));

        }
    }

    @Test
    public void cancelTesk(){
        taskService.cancelTask(1713385004232462338L);
    }


    @Test
    public void testPoll(){
        Task task = taskService.poll(100, 50);
        System.out.println(task);
    }


}