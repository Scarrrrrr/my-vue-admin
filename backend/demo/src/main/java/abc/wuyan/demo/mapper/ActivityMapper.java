package abc.wuyan.demo.mapper;

import abc.wuyan.demo.model.Activity;
import abc.wuyan.demo.model.Attend;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface ActivityMapper{
    @Insert("INSERT INTO ACTIVITY (title, description, place, beginTime, endTime, beginTimestamp, endTimestamp) VALUE (#{title}, #{description}, #{place}, #{beginTime}, #{endTime}, #{beginTimestamp}, #{endTimestamp})")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="tid", before=false, resultType=Integer.class)
    public int insert(Activity activity);

    @Select("SELECT * FROM ACTIVITY")
    public List<Activity> selectAll();

    @Select("SELECT * FROM ACTIVITY WHERE TID = #{tid}")
    public Activity findById(Integer tid);

    @Insert("INSERT INTO ATTEND (tid, uid) VALUE(#{tid}, #{uid})")
    public void attend(Attend attend);

    @Delete("DELETE FROM ATTEND WHERE TID=#{tid} AND UID=#{uid}")
    public void quit(Attend attend);

    @Delete("DELETE FROM ACTIVITY WHERE TID=#{tid}")
    public void deleteById(Integer tid);

    @Update("UPDATE ACTIVITY SET TITLE=#{title}, PLACE=#{place}, BEGINTIME=#{beginTime}, ENDTIME=#{endTime}, DESCRIPTION=#{description} WHERE TID=#{tid}")
    public void update(Activity activity);

    @Select("SELECT UID FROM ATTEND WHERE TID = #{tid}")
    public List<Integer> findActivityUsersById(Integer tid);

    @Select("SELECT TID FROM ATTEND WHERE UID = #{uid}")
    public List<Integer> findPersonalActivityById(Integer uid);

    @Select("SELECT * FROM ATTEND WHERE UID=#{uid} AND TID=#{tid}")
    public Attend findIfAttend(Integer uid, Integer tid);

    @Select("SELECT * FROM ACTIVITY WHERE TITLE = #{title}")
    public List<Activity> findActivityByTitle(String title);

    @Select("SELECT * FROM ACTIVITY WHERE BEGINTIMESTAMP = #{beginTimestamp}")
    public List<Activity> findActivityByBeginTime(long beginTimestamp);

    @Select("SELECT * FROM ACTIVITY WHERE ENDTIMESTAMP = #{endTimestamp}")
    public List<Activity> findActivityByEndTime(long endTimestamp);

    @Select("SELECT * FROM ACTIVITY WHERE BEGINTIMESTAMP <= #{nowTimestamp} AND ENDTIMESTAMP >= #{nowTimestamp}")
    public List<Activity> presentActivity(long nowTimestamp);

    @Select("SELECT * FROM ACTIVITY WHERE BEGINTIMESTAMP > #{nowTimestamp} OR ENDTIMESTAMP < #{nowTimestamp}")
    public List<Activity> pastActivity(long nowTimestamp);
}
