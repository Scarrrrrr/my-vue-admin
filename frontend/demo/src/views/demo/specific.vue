<template>
  <div class="myinfo">
    <h2>活动详情</h2>
    <el-descriptions style="margin-top:1em" direction="vertical" :column="2" border>
      <el-descriptions-item label="活动名称">{{activity.title}}</el-descriptions-item>
      <el-descriptions-item label="举办地点">{{activity.place}}</el-descriptions-item>
      <el-descriptions-item label="活动描述">{{activity.description}}</el-descriptions-item>
      <el-descriptions-item label="参加人数">{{users.length}}</el-descriptions-item>
      <el-descriptions-item label="开始时间">
        <i class="el-icon-time" />
        <span style="margin-left: 10px">{{ activity.beginTime }}</span>
      </el-descriptions-item>
      <el-descriptions-item label="结束时间">
        <i class="el-icon-time" />
        <span style="margin-left: 10px">{{ activity.endTime }}</span>
      </el-descriptions-item>
    </el-descriptions>
    <div class="inusers" v-if="admin">
      <h2>参与人员</h2>
      <el-table
        :data="users"
        style="width: 100%"
      >
        <el-table-column
          label="人员id"
          width="120"
        >
          <template slot-scope="scope">
            {{ scope.row.id }}
          </template>
        </el-table-column>
        <el-table-column
          label="用户名"
          width="200"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="性别"
          width="120"
        >
          <template slot-scope="scope">
            <span >{{ scope.row.sex }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="电子邮箱"
          width="250"
        >
          <template slot-scope="scope">
            <el-input size="small" v-model="scope.row.place" v-show="scope.row.editable"></el-input>
            <span>{{ scope.row.email }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="角色"
          width="220"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.role=="admin"?"管理员":"普通用户" }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="bottom">
      <el-button class="mybutton" size="medium" type="success" @click="back">返回</el-button>
    </div>
  </div>
</template>

<script>
export default {
    data(){
        return{
            tid: this.$route.params.id,
            isLogin:false,
            admin:false,
            users:[],
            activity:{
                title:"",
                place:"",
                description:"",
                beginTime:"",
                endTime:"",
            }
        }
    },
    mounted(){
      this.isLogin = localStorage.getItem('id')?true:false;
      if(this.isLogin){
        this.admin = localStorage.getItem('role')=="admin"?true:false
      }
      const tid = this.tid
      this.$axios({
          url:`/activity/specific?tid=${tid}`
      }).then(res=>{
          this.activity = res.data
      })
      this.getUsers(tid)
    },
    methods:{
        back(){
          this.$router.go(-1)
        },
        getUsers(tid){
          this.$axios.get('/activity/users?tid='+tid)
            .then(res=>{
              this.users = res.data
            })
        }
    }
}
</script>

<style scoped>
.myinfo{
  padding: .5em 2em;
}
.myinfo::after{
  clear: both;
}
.bottom{
  position: relative;
  height: 100px;
}
.mybutton{
  position: absolute;
  bottom: 2em;
  right: 0;
}
.inusers{
  margin-top: 2em;
  margin-bottom: 2em;
}
</style>