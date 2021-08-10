<template>
  <div class="manage">
    <h2 class="mheader">活动管理</h2>
    <el-button @click="addVisible=true" size="medium" type="success">添加活动</el-button>
    <el-dialog
      title="添加活动"
      :visible.sync="addVisible"
      width="30%"
    >
      <el-form>
        <el-form-item label="活动标题">
          <el-input v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item label="活动描述">
          <el-input v-model="form.description"></el-input>
        </el-form-item>
        <el-form-item label="活动地点">
          <el-input v-model="form.place"></el-input>
        </el-form-item>
        <el-form-item label="开始时间">
          <el-date-picker 
            type="datetime" 
            v-model="form.beginTime"
            placeholder="选择日期时间"
            >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker 
            type="datetime" 
            v-model="form.endTime"
            placeholder="选择日期时间"
            >
          </el-date-picker>
        </el-form-item> 
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="success" @click="handleUpdate(form)">添 加</el-button>
        <el-button @click="addVisible = false">取 消</el-button>
      </span>
    </el-dialog>
    <div>
      <el-table
        :data="tableData"
        @row-click="jumpToSpecific"
        style="width: 100%"
      >
        <el-table-column
          label="活动id"
          width="80"
        >
          <template slot-scope="scope">
            {{ scope.row.tid }}
          </template>
        </el-table-column>
        <el-table-column
          label="活动标题"
          width="100"
        >
          <template slot-scope="scope">
            <el-input size="small" v-model="scope.row.title" v-show="scope.row.editable"></el-input>
            <span v-show="!scope.row.editable" style="margin-left: 10px">{{ scope.row.title }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="活动描述"
          width="150"
        >
          <template slot-scope="scope">
            <el-input size="small" v-model="scope.row.description" v-show="scope.row.editable"></el-input>
            <span v-show="!scope.row.editable" style="margin-left: 10px">{{ scope.row.description }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="地点"
          width="100"
        >
          <template slot-scope="scope">
            <el-input size="small" v-model="scope.row.place" v-show="scope.row.editable"></el-input>
            <span v-show="!scope.row.editable" style="margin-left: 10px">{{ scope.row.place }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="开始时间"
          width="220"
        >
          <template slot-scope="scope">
            <i v-show="!scope.row.editable" class="el-icon-time" />
            <span v-show="!scope.row.editable" style="margin-left: 10px">{{ scope.row.beginTime }}</span>
            <el-date-picker
              v-show="scope.row.editable"
              v-model="scope.row.beginTime"
              type="datetime"
              placeholder="选择日期">
            </el-date-picker>
          </template>
        </el-table-column>
        <el-table-column
          label="结束时间"
          width="220"
        >
          <template slot-scope="scope">
            <i v-show="!scope.row.editable" class="el-icon-time" />
            <span v-show="!scope.row.editable" style="margin-left: 10px">{{ scope.row.endTime }}</span>
            <el-date-picker
              v-show="scope.row.editable"
              v-model="scope.row.beginTime"
              type="datetime"
              placeholder="选择日期">
            </el-date-picker>
          </template>
        </el-table-column>
        <el-table-column label="编辑">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click.stop="handle(scope.row)"
            >
              {{scope.row.editable ? "保存" : "编辑"}}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column label="删除">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="danger"
              @click.stop="deleteVisible = true;deleteId=scope.row.tid;deleteIndex=scope.$index"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog
        title="确认删除帖子吗"
        :visible.sync="deleteVisible"
        width="30%"
      >
        <p>删除后操作将不可恢复！</p>
        <span slot="footer" class="dialog-footer">
          <el-button @click.stop="deleteVisible = false">取 消</el-button>
          <el-button type="danger" @click.stop="handleDelete(deleteIndex, tableData)">删 除</el-button>
        </span>
      </el-dialog>
    </div>
    <el-pagination
      background
      layout="total, prev, pager, next"
      :total="tableData.length"
      :page-size="page.size"
      :current-page.sync="page.current"
    />
  </div>
</template>

<script>
export default {
  name:'activities',
  data() {
    return {
      currentPage: 1,
      deleteVisible: false,
      deleteIndex: -1,
      deleteId: -1,
      addVisible: false,
      tableData: [],
      form:{
        title:"",
        description:"",
        place:"",
        beginTime:"",
        beginTimestamp:"",
        endTime:"",
        endTimestamp:"",
      },
      page: {
        current: 1,
        pages: 1,
        size: 10,
        total: 100
      }
    }
  },
  mounted() {
    this.getAllPosts()
  },
  methods: {
    getAllPosts() {
      this.tableData = []
      this.$axios({
        url: '/activity/all',
        method: 'get'
      }).then(res => {
        this.tableData = res.data.map(row =>{
          return Object.assign(row, {editable:false})
        });
      })

    },
    handleDelete(index, rows) {
      this.deleteVisible = false
      const deleteId = this.deleteId
      this.$axios({
        url:"/activity/delete",
        method:'post',
        data:{
          id:deleteId
        }
      }).then(res => {
        if (res.data.status === -1) {
          this.$message({ message: res.data.message, type: 'error' })          
        } else {
          this.$message({ message: res.data.message, type: 'success' })
          rows.splice(index, 1)
        }
      }).catch(err => { this.$message({ message: err, type: 'error' }) })
    },
    handleUpdate() {
      this.addVisible = false
      const param = this.form;
      param.beginTimestamp = param.beginTime.getTime()
      param.endTimestamp = param.endTime.getTime()
      this.$axios({
        url:"/activity/save",
        method:'post',
        data: param
      }).then(res => {
        if (res.data.status === -1) {
          this.$message({ message: res.data.message, type: 'error' })          
        } else {
          this.$message({ message: res.data.message, type: 'success' })
          this.$router.go(0)
        }
      }).catch(err => { this.$message({ message: err, type: 'error' }) })
    },
    handle(row) {
      // 保存
      if (row.editable) {
        this.$axios({
            url: '/activity/update',
            method: 'post',
            data: row
        }).then(res => {
          if(res.data.status==0){
            this.$message({
              message: '修改成功',
              type: 'success'
            });
          }else{
            this.$message({
              message: '修改失败',
              type: 'error'
            });
          }
          row.editable = false
        })
        // 编辑
        } else {
            row.editable = true
        }
    },
    jumpToSpecific(row){
      this.$router.push('/home/specific/'+row.tid)
    }
  }
}
</script>

<style scoped>
.manage{
  padding:.5em 2em;
}
</style>