<template>
    <div>
      <div class="myhead">
        <el-form :inline="true" size="small">
            <el-form-item label="活动名称：">
                <el-input v-model="form.title"></el-input>
            </el-form-item>
            <el-form-item label="开始时间：">
                <el-date-picker
                v-model="form.beginTime"
                type="datetime"
                placeholder="选择日期时间">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="结束时间：" >
                <el-date-picker
                v-model="form.endTime"
                type="datetime"
                placeholder="选择日期时间">
                </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="search" icon="el-icon-search">
                  搜索
              </el-button>
            </el-form-item>
          </el-form>
      </div>
      <el-table v-loading="listLoading" :data="list" element-loading-text="Loading..." border fit highlight-current-row>
        <el-table-column prop="tid" :label="header[0]" align="center" sortable>
        </el-table-column>
        <el-table-column prop="title" :label="header[1]" align="center">
        </el-table-column>
        <el-table-column prop="place" :label="header[2]" align="center">
        </el-table-column>
        <el-table-column prop="description" :label="header[3]" align="center">
        </el-table-column>
        <el-table-column prop="beginTime" :label="header[4]" align="center" sortable>
        </el-table-column>
        <el-table-column prop="endTime" :label="header[5]" align="center" sortable>
        </el-table-column>
      </el-table>
    </div>
</template>

  <script>
  import { parseTime } from '@/utils/util'
  import { axios } from '@/utils/request'

  export default {
    name: 'search',
    data() {
      return {
        form:{
            title:'',
            beginTime:'',
            endTime:'',
        },
        filter:{
            researchGroup: null,
            enrollmentTime: null,
        },
        researchGroups: null,
        header:['活动id','活动标题','活动地点','活动描述','开始时间','结束时间'],
        list: [],
        listLoading: false,
        downloadLoading: false,
        filename: '',
        autoWidth: true,
        bookType: 'xlsx'
      }
    },
    methods: {
      search() {
        this.listLoading = true
        this.list = []
        if(this.form.title&&!this.form.beginTime&&!this.form.endTime){
          axios({
              url: `/activity/searchByTitle?title=${this.form.title}`
          }).then(res => {
              res.forEach(element=>{
                this.list.push(element)
              })
          }).catch(err=>{ this.$message({message:err,type:"error"}) })       
        }
        if(this.form.beginTime&&!this.form.title&&!this.form.endTime){
          axios({
              url:`/activity/searchByBeginTime`,
              method:'post',
              data:{
                beginTimestamp:this.form.beginTime.getTime()
              }
          }).then(res => {
              res.forEach(element=>{
                this.list.push(element)
              })
          }).catch(err=>{ this.$message({message:err,type:"error"}) })  
        }
        if(this.form.endTime&&!this.form.beginTime&&this.form.title){
          axios({
              url:`/activity/searchByEndTime`,
              method:'post',
              data:{
                endTimestamp:this.form.endTime.getTime()
              }
          }).then(res => {
              res.forEach(element=>{
                this.list.push(element)
              })
          }).catch(err=>{ this.$message({message:err,type:"error"}) })  
        }

        this.listLoading = false
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => {
          if (j === 'timestamp') {
            return parseTime(v[j])
          } else {
            return v[j]
          }
        }))
      },
    }
  }
  </script>
  
  <style scoped>
    .myhead{
        padding: .3em 2em;
    }
    .el-col {
        border-radius: 4px;
    }
    .grid-content {
        border-radius: 4px;
        min-height: 36px;
    }
  </style>