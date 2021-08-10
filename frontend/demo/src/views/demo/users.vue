<template>
  <div>
      <div v-for="(user,i) in users" :key="i">
        <el-card>
          <h3>
            {{user.name}}
          </h3>
          <p>{{user.sex}}</p>
          <p>{{user.role}}</p>
          <p>{{user.email}}</p>
        </el-card>
      </div>
  </div>
</template>

<script>
export default {
  name:"users",
  data(){
    return{
      users:[]
    }
  },
  mounted(){
    this.$axios.get("/user/allUsers")
      .then(res=>{
        const users = res.data;
        this.users = users;
      })
  }
}
</script>

<style>

</style>