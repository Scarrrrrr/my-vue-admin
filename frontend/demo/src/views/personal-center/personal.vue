<template>
  <div class="myinfo">
    <el-descriptions title="个人信息" direction="vertical" :column="4" border>
      <el-descriptions-item label="用户名">{{user.name}}</el-descriptions-item>
      <el-descriptions-item label="性别">{{user.sex}}</el-descriptions-item>
      <el-descriptions-item label="身份" :span="2">{{user.role}}</el-descriptions-item>
      <el-descriptions-item label="备注">
        <el-tag size="small">无</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="电子邮箱">{{user.email}}</el-descriptions-item>
    </el-descriptions>  
  </div>

</template>

<script>
export default {
    name:"personal",
    data(){
        return{
            user:{
              name:"zhangsan",
              sex:"男",
              role:"admin",
              email:""
            }
        }
    },
    mounted(){
      this.user.name = localStorage.getItem('name')
      this.user.sex = localStorage.getItem('sex')
      this.user.role = localStorage.getItem('role')
      this.user.email = localStorage.getItem('email')
    }
}
</script>

<style scoped>
.myinfo{
  padding: .5em 2em;
}
</style>