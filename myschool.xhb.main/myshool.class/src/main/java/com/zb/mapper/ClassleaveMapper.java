package com.zb.mapper;

import com.zb.pojo.Class_leave;
import com.zb.pojo.Leave_job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassleaveMapper {
    //获取请假的信息
    List<Leave_job> findleaveBy();
    //根据id查询某一条请假的信息
    Leave_job getleaveBy(@Param("id")String id);
    //添加请假条例
    int addleavejob(Leave_job leaveJob);
    //添加请假列表
    int addclassleave(Class_leave classLeave);
    //是否批准 同意
    int updataleaveAgree(@Param("id") String id);
    //批准拒绝
    int updataleaverefuse(@Param("id") String id);
    //根据同意或拒绝删除任务表中的信息
    int deleteleave(@Param("id") String id);
    //根据班级班号获取这个班级的请假信息
    List<Leave_job> findleavenumber(@Param("class_number") Integer class_number);
}
