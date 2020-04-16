package com.zb.service.impl;

import com.zb.mapper.HabitMapper;
import com.zb.pojo.Habit;
import com.zb.service.HabitService;
import com.zb.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitServiceImpl implements HabitService {

    @Autowired(required = false)
    HabitMapper habitMapper;

    @Override
    public int addHabit(Habit habit) {
        habit.setHabitId(IdWorker.getId());
        return habitMapper.addHabit(habit);
    }

    @Override
    public List<Habit> listHabitsSomeday(String joinDate, String subjectId) {
        return habitMapper.listHabitsSomeday(joinDate,subjectId);
    }

    @Override
    public List<Habit> listAllHabits(String subjectId) {
        return habitMapper.listAllHabits(subjectId);
    }

    @Override
    public List<Habit> listSomeoneHabits(String userId, String subjectId) {
        return habitMapper.listSomeoneHabits(userId,subjectId);
    }

    @Override
    public int deleteHabit(String habitId) {
        return habitMapper.deleteHabit(habitId);
    }

    @Override
    public int isJoinTheHabitSomeDay(String userId, String subjectId, String joinDate) {
        return habitMapper.isJoinTheHabitSomeDay(userId,subjectId,joinDate);
    }
}
