package abc.wuyan.demo.controller;

import abc.wuyan.demo.model.*;
import abc.wuyan.demo.service.ActivityService;
import abc.wuyan.demo.util.Consts;
import org.omg.PortableInterceptor.ACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    //所有活动
    @RequestMapping("/all")
    public List<Activity> findAllactivities(){
        return activityService.findAllActivities();
    }

    //单个活动的详情
    @RequestMapping("/specific")
    public Activity singleActivity(@RequestParam Integer tid){
        return activityService.findById(tid);
    }

    //用户查看自己参加的所有活动
    @RequestMapping("/myactivity")
    public List<Activity> checkActivities(@RequestParam Integer uid){
        return activityService.findPersonalActivityById(uid);
    }

    //用户参加活动
    @RequestMapping(value = "/attend",method = RequestMethod.POST)
    public RespInfo attendActivity(@RequestBody Attend attend){
        String msg = "参加成功";
        if(activityService.findIfAttend(attend.getUid(),attend.getTid())==null){
            activityService.attend(attend);
            return new RespInfo(Consts.SUCCESS_CODE, msg);
        }else{
            return new RespInfo(Consts.ERROR_CODE, msg="已经参加过了");
        }
    }

    //用户退出活动
    @RequestMapping(value = "/quit",method = RequestMethod.POST)
    public RespInfo quitActivity(@RequestBody Attend attend){
        String msg = "退出成功";
        activityService.quit(attend);
        return new RespInfo(Consts.SUCCESS_CODE, msg);
    }

    //管理员添加活动
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RespInfo save(@RequestBody Activity activity) {
        String msg = "添加成功";
        activityService.insert(activity);
        return new RespInfo(Consts.SUCCESS_CODE, activity, msg);
    }

    //管理员编辑活动
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RespInfo update(@RequestBody Activity activity) {
        String msg = "编辑成功";
        activityService.update(activity);
        return new RespInfo(Consts.SUCCESS_CODE, activity, msg);
    }

    //管理员删除活动
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public RespInfo deleteById(@RequestBody MyParam param){
        if(param.getId()!=null){
            activityService.delete(param.getId());
            return new RespInfo(Consts.SUCCESS_CODE, null, "删除成功");
        }else{
            return new RespInfo(Consts.ERROR_CODE, null, "信息不足无法删除");
        }
    }

    //管理员查看活动下的所有用户
    @RequestMapping("/users")
    public List<User> checkUsers(@RequestParam Integer tid){
        return activityService.findActivityUsersById(tid);
    }

    //搜索活动
    @RequestMapping("/searchByTitle")
    public List<Activity> searchByTitle(@RequestParam String title){
        return activityService.findActivityByTitle(title);
    }

    @RequestMapping("/searchByBeginTime")
    public List<Activity> searchByBeginTime(@RequestBody Activity activity){
        return activityService.findActivityByBeginTime(activity.getBeginTimestamp());
    }

    @RequestMapping("/searchByEndTime")
    public List<Activity> searchByEndTime(@RequestBody Activity activity){
        return activityService.findActivityByEndTime(activity.getEndTimestamp());
    }

    @RequestMapping("/present")
    public List<Activity> presentActivity() {
        Date now = new Date();
        long nowstamp = now.getTime();
        return activityService.presentActivity(nowstamp);
    }

    @RequestMapping("/past")
    public List<Activity> pastActivity() {
        Date now = new Date();
        long nowstamp = now.getTime();
        return activityService.pastActivity(nowstamp);
    }

}
