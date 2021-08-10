package abc.wuyan.demo.service;

import abc.wuyan.demo.model.Activity;
import abc.wuyan.demo.model.Attend;
import abc.wuyan.demo.model.User;

import java.util.Date;
import java.util.List;

public interface ActivityService {
    public List<Activity> findAllActivities();
    public Activity findById(Integer id);
    public void insert(Activity activity);
    public void delete(Integer tid);
    public void quit(Attend attend);
    public void update(Activity activity);
    public void attend(Attend attend);
    public List<User> findActivityUsersById(Integer tid);
    public List<Activity> findPersonalActivityById(Integer uid);
    public Attend findIfAttend(Integer uid, Integer tid);
    public List<Activity> findActivityByTitle(String title);
    public List<Activity> findActivityByBeginTime(long beginTimestamp);
    public List<Activity> findPersonalPastActivityById(long nowTimestamp, Integer uid);
    public List<Activity> findActivityByEndTime(long endTimestamp);
    public List<Activity> presentActivity(long nowTimestamp);
    public List<Activity> pastActivity(long nowTimestamp);

}
