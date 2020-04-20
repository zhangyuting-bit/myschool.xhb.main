package com.zb.mapper;

import com.zb.pojo.Habit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HabitMapper {

    //新增习惯养成
    int addHabit(Habit habit);
    //查看某天的习惯养成成果
    List<Habit> listHabitsSomeday(@Param("joinDate") String joinDate,
                                  @Param("subjectId") String subjectId);
    //查看全部习惯成果
    List<Habit> listAllHabits(@Param("subjectId") String subjectId);
    //查看某个用户的习惯养成成果
    List<Habit> listSomeoneHabits(@Param("userId") String userId,
                                  @Param("subjectId") String subjectId);
    //撤销习惯养成成果
    int deleteHabit(@Param("habitId") String habitId);
    //某用户某天是否参加了习惯养成
    int isJoinTheHabitSomeDay(@Param("userId") String userId,
                              @Param("subjectId") String subjectId,
                              @Param("joinDate") String joinDate);

}
