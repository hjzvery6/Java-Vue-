<!-- 信息管理/地址管理，注意这个只有用户看得到 -->
<template>
  <div>
    <div>
      <div class="main-body-content">
        <!-- 顶部 -->
        <div class="main-body-search">
          <div class="main-body-header">地址管理</div>
          <el-input size="small" class="main-input" placeholder="请输入收货地址" v-model="search.address"></el-input>
					<el-button size="small" type="primary" @click="load()">查询</el-button>
					<el-button size="small" type="primary" @click="add()">新增</el-button>
        </div>

        <!-- 中部表格 -->
        <div class="main-table-box">
          <el-table style="width: 100%" :data="tableData" size="small" strip header-cell-class-name="tableHeaderClass" >
						<el-table-column label="收货人" prop="username"></el-table-column>
						<el-table-column label="收货地址" prop="address"></el-table-column>
						<el-table-column label="联系方式" prop="phone"></el-table-column>
            <el-table-column label="操作">
              <template v-slot="scope">
								<el-popconfirm title="确定删除？" @confirm="del(scope.row.id)">
									<el-button size="small" type="danger" slot="reference">删除</el-button>
								</el-popconfirm>
              </template>
            </el-table-column>
          </el-table>
        </div>

        <!-- 尾部分页器 -->
        <div style="margin: 20px 0">
          <el-pagination
              background
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-size="5"
              layout="total, prev, pager, next"
              :total="total">
          </el-pagination>
        </div>
      </div>
    </div>

    <!-- 修改信息对话框 -->
    <el-dialog title="请填写信息" :visible.sync="dialogVisible" width="40%">
      <el-form :model="form" label-position="right" label-width="100px" style="padding-right: 40px">
        <el-form-item label="收货人">
          <el-input size="small" v-model="form.username" placeholder="请输入收货人"></el-input>
        </el-form-item>
        <el-form-item label="收货地址">
          <el-input size="small" v-model="form.address" placeholder="请输入收货地址"></el-input>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input size="small" v-model="form.phone" placeholder="请输入联系方式"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="save">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

// 地址模块接口请求统一前缀，和后端的控制器对应
let baseUrl = "/address/"

export default {
  data() {
    return {
      user: JSON.parse(localStorage.getItem("user") || {}), // 获取登录用户的个人信息，如果没有就设为空对象
      tableData: [],
      pageNum: 1,
      dialogVisible: false,
      search: {},
      form: {},
      total: 0,
    }
  },

  // 页面加载的时候，做一些事情，在created里面
  mounted() {
    this.load()
  },

  // 定义一些页面上控件出发的事件调用的方法
  methods: {
    // 计算表格列宽
    operationSpace() {
      let width = 0
      let _this = this
      setTimeout(() => {
        let rows = document.getElementsByClassName('el-table__row');
        if (rows && rows.length) {
          let tds = rows[0].getElementsByTagName('td')
          let btns = tds[tds.length - 1].getElementsByTagName('button')
          for (let i = 0; i <btns.length; i++) {
            width += btns[i].offsetWidth + 5
          }
          _this.colWidth = width
        }
      }, 0)
    },

    // 分页加载表格数据
    load() {
      // 将search也加入请求中是因为无论要不要搜索，对结果都是要进行分页的，如果search为空的时候就是全部记录
      request.post(baseUrl + "page?pageNum=" + this.pageNum, this.search).then(res => {
        if (res.code === '0') {
          this.tableData = res.data.list;
          this.total = res.data.total;
          this.operationSpace();
        } else {
          this.$notify.error(res.msg);
        }
      });
    },

    // 新增初始化对话框
    add() {
      this.form = {};
      this.dialogVisible = true;
    },

    // 修改信息保存
    save() {
      this.form.userId = this.user.id
      this.form.role = this.user.role
      if (!this.form.id) {
        // 没有ID，说明是新增操作
        request.post(baseUrl, this.form).then(res => {
          if (res.code === '0') {
            this.$notify.success("添加成功");
            this.dialogVisible = false;

            this.load();
          } else {
            this.$notify.error(res.msg);
          }
        })
      } else {
        // 有ID，说明是更新操作
        request.put(baseUrl, this.form).then(res => {
          if (res.code === '0') {
            this.$notify.success('更新成功');
            this.dialogVisible = false;
            this.load();
          } else {
            this.$notify.error(res.msg);
          }
        })
      }
    },

    // 根据ID删除某条数据
    del(id) {
      request.delete(baseUrl + id).then(res => {
        if (res.code === '0') {
          this.$notify.success('删除成功');
          this.load();
        } else {
          this.$notify.error(res.msg);
        }
      });
    },

    // 换页处理
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load();
    },
  }
}
</script>
