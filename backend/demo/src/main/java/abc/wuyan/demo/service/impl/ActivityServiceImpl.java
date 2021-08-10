package abc.wuyan.demo.service.impl;

import abc.wuyan.demo.mapper.ActivityMapper;
import abc.wuyan.demo.mapper.UserMapper;
import abc.wuyan.demo.model.Activity;
import abc.wuyan.demo.model.Attend;
import abc.wuyan.demo.model.User;
import abc.wuyan.demo.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Activity> findAllActivities() {
        return activityMapper.selectAll();
    }

    @Override
    public Activity findById(Integer id) {
        return activityMapper.findById(id);
    }

    @Override
    public void insert(Activity activity) {
        activityMapper.insert(activity);
    }

    @Override
    public void delete(Integer tid) {
        activityMapper.deleteById(tid);
    }

    @Override
    public void quit(Attend attend) {
        activityMapper.quit(attend);
    }

    @Override
    public void update(Activity activity) {
        activityMapper.update(activity);
    }

    @Override
    public void attend(Attend attend) {
        activityMapper.attend(attend);
    }

    @Override
    public List<User> findActivityUsersById(Integer tid) {
        List<Integer> uids = activityMapper.findActivityUsersById(tid);
        ArrayList<User> users = new ArrayList<User>();
        for(int i=0;i<uids.size();i++){
            User u = userMapper.findUserById(uids.get(i));
            u.setPassword("");
            users.add(u);
        }
        return users;
    }

    @Override
    public List<Activity> findPersonalActivityById(Integer uid) {
        List<Integer> tids = activityMapper.findPersonalActivityById(uid);
        ArrayList<Activity> activities = new ArrayList<Activity>();
        for(int i=0;i<tids.size();i++){
            Activity activity = activityMapper.findById(tids.get(i));
            activities.add(activity);
        }
        return activities;
    }

    @Override
    public Attend findIfAttend(Integer uid, Integer tid) {
        return activityMapper.findIfAttend(uid, tid);
    }

    @Override
    public List<Activity> findActivityByTitle(String title) {
        return activityMapper.findActivityByTitle(title);
    }

    @Override
    public List<Activity> findActivityByBeginTime(long beginTimestamp) {
        return activityMapper.findActivityByBeginTime(beginTimestamp);
    }

    @Override
    public List<Activity> findPersonalPastActivityById(long nowTimestamp, Integer uid) {
        return null;
    }

    @Override
    public List<Activity> findActivityByEndTime(long endTimestamp) {
        return activityMapper.findActivityByEndTime(endTimestamp);
    }

    @Override
    public List<Activity> presentActivity(long nowTimestamp) {
        return activityMapper.presentActivity(nowTimestamp);
    }

    @Override
    public List<Activity> pastActivity(long nowTimestamp) {
        return activityMapper.pastActivity(nowTimestamp);
    }

}
