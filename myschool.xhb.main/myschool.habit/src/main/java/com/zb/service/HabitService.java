package com.zb.service;

import com.zb.pojo.Habit;

import java.util.List;

public interface HabitService {

    //新增习惯养成
    int addHabit(Habit habit);
    //查看某天的习惯养成成果
    List<Habit> listHabitsSomeday(String joinDate,String subjectId);
    //查看全部习惯成果
    List<Habit> listAllHabits(String subjectId);
    //查看某个用户的习惯养成成果
    List<Habit> listSomeoneHabits(String userId,String subjectId);
    //撤销习惯养成成果
    int deleteHabit(String habitId);
    //某用户某天是否参加了习惯养成
    int isJoinTheHabitSomeDay(String userId,String subjectId,String joinDate);

}
