<template>
  <div>
    <el-tabs v-model="tabPosition" type="border-card">
      <el-tab-pane label="正在参加" name="line">
        <div>
          <el-table
            :data="nowData"
            style="width: 100%"
          >
            <el-table-column
              label="活动id"
              width="100"
            >
              <template slot-scope="scope">
                {{ scope.row.tid }}
              </template>
            </el-table-column>
            <el-table-column
              label="活动标题"
              width="130"
            >
              <template slot-scope="scope">
                <el-tag>{{ scope.row.title }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column
              label="活动描述"
              width="180"
            >
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.description }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="开始时间"
              width="260"
            >
              <template slot-scope="scope">
                <i class="el-icon-time" />
                <span style="margin-left: 10px">{{ scope.row.beginTime }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="结束时间"
              width="300"
            >
              <template slot-scope="scope">
                <i class="el-icon-time" />
                <span style="margin-left: 10px">{{ scope.row.endTime }}</span>
              </template>
            </el-table-column>
            
            <el-table-column label="退出操作">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="danger"
                  @click="deleteVisible = true;deleteId=scope.row.tid"
                >
                  退出
                </el-button>
                <el-dialog
                  title="确认退出活动吗"
                  :visible.sync="deleteVisible"
                  width="30%"
                >
                  <p>退出后操作将不可恢复！</p>
                  <span slot="footer" class="dialog-footer">
                    <el-button @click="deleteVisible = false">取 消</el-button>
                    <el-button type="danger" @click="handleDelete()">退 出</el-button>
                  </span>
                </el-dialog>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
      <el-tab-pane label="历史参加" name="bar">
        <div>
          <el-table
            :data="pastData"
            style="width: 100%"
          >
            <el-table-column
              label="活动id"
              width="100"
            >
              <template slot-scope="scope">
                {{ scope.row.tid }}
              </template>
            </el-table-column>
            <el-table-column
              label="活动标题"
              width="130"
            >
              <template slot-scope="scope">
                <el-tag>{{ scope.row.title }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column
              label="活动描述"
              width="180"
            >
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.description }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="开始时间"
              width="260"
            >
              <template slot-scope="scope">
                <i class="el-icon-time" />
                <span style="margin-left: 10px">{{ scope.row.beginTime }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="结束时间"
              width="300"
            >
              <template slot-scope="scope">
                <i class="el-icon-time" />
                <span style="margin-left: 10px">{{ scope.row.endTime }}</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
    name:"attend",
    data(){
        return{
            tabPosition: "line",
            tableData:[],
            pastData:[],
            nowData:[],
            deleteVisible:false,
        }
    },
    mounted(){
        const uid = localStorage.getItem('id');
        const now = new Date().getTime();
        this.$axios.get("/activity/myactivity?uid="+uid)
          .then(res=>{
            res.data.forEach(element=>{
              if(element){
                this.tableData.push(element)
                if(element.endTimestamp < now){
                  this.pastData.push(element)
                }else{
                  this.nowData.push(element)
                }
              }
            })
          })
    },methods:{
      handleDelete(){
        this.deleteVisible = false
        const deleteId = this.deleteId
        this.$axios({
          url:'/activity/quit',
          method: 'post',
          data:{
            uid:localStorage.getItem('id'),
            tid:deleteId
          }
        }).then(res => {
        if (res.data.status === -1) {
          this.$message({ message: "退出失败！", type: 'error' })          
        } else {
          this.$message({ message: "成功退出！", type: 'success' })
          this.$router.go(0)
        }
      }).catch(err => { this.$message({ message: err, type: 'error' }) })
      }
    }
}
</script>

<style>

</style>