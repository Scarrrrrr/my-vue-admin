<template>
  <div class="layout-test">
    <el-tabs v-model="tabPosition" type="border-card">
      <el-tab-pane label="所有活动" name="all">
        <div class="activity-card" v-for="(activity,i) in activities" :key="i">
          <el-card>
            <h3>
              {{activity.title}}
            </h3>
            <p>{{activity.beginTime}}-{{activity.endTime}}</p>
            <p>{{activity.description}}</p>
            <p>{{activity.place}}</p>
            <el-button @click="jumpToSpecific(activity.tid)" type="success" class="absolute-right-down">查看详情</el-button> 
          </el-card>
        </div>
      </el-tab-pane>
      <el-tab-pane label="正在进行的活动" name="present">
        <div class="activity-card" v-for="(activity,i) in presents" :key="i">
          <el-card>
            <h3>
              {{activity.title}}
            </h3>
            <p>{{activity.beginTime}}-{{activity.endTime}}</p>
            <p>{{activity.description}}</p>
            <p>{{activity.place}}</p>
            <el-button @click="attend(activity.tid)" type="danger" class="absolute-right">立即参加</el-button> 
            <el-button @click="jumpToSpecific(activity.tid)" type="success" class="absolute-right-down">查看详情</el-button> 
          </el-card>
        </div>
      </el-tab-pane>
      <el-tab-pane label="已结束的活动" name="over">
        <div class="activity-card" v-for="(activity,i) in past" :key="i">
          <el-card>
            <h3>
              {{activity.title}}
            </h3>
            <p>{{activity.beginTime}}-{{activity.endTime}}</p>
            <p>{{activity.description}}</p>
            <p>{{activity.place}}</p>
            <el-button @click="jumpToSpecific(activity.tid)" type="success" class="absolute-right-down">查看详情</el-button> 
          </el-card>
        </div>
      </el-tab-pane>
    </el-tabs>

  </div>
</template>

<script>
export default {
  name: "test",
  components: {},
  data() {
    return {
      tabPosition:"all",
      activities:[],
      presents:[],
      past:[]
    };
  },
  props: {},
  mounted(){
    // this.isLogin=localStorage.getItem('id')?true:false;
    this.$axios.get("/activity/all")
      .then(res=>{
        this.activities = res.data;
      })
    this.getPresents()
    this.getPast()
  },
  methods:{
    attend(tid){
      const id = localStorage.getItem('id');
      if(id){
        this.$axios({
          url:'/activity/attend',
          method:'post',
          data:{
            uid:id,
            tid:tid
          }
        }).then(res=>{
          if(res.data.status == 0){
            this.$message({message:"参加成功",type:'success'})
          }else if(res.data.status == -1){
            this.$message({message:"已经参加过了",type:'error'})
          }
        }).catch(err=>{
          this.$message({message:err,type:'error'})
        })        
      }else{
        this.$message({message:"请先登录！",type:'error'})
      }
    },
    jumpToSpecific(id){
      this.$router.push(`/home/specific/${id}`)
    },
    getPresents(){
      this.$axios.get('/activity/present')
        .then(res=>{
          this.presents = res.data
        })
    },
    getPast(){
      this.$axios.get('/activity/past')
        .then(res=>{
          this.past = res.data
        })
    }
  }
};
</script>

<style lang="less" scoped>
.layout-test {
  text-align: left;
  padding: 16px;
  span {
    font-size: 18px;
  }
  .activity-card{
    position: relative;
    .absolute-right {
      position: absolute; /*固定作用*/
      right:5em;
      top:50%;
    }
    .absolute-right-down{
      position: absolute;
      right: 5em;
      top:30%
    }
  }
}
</style>