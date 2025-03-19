<!-- 用户管理/普通用户，很多地方和Admin.vue类似 -->
<template>
  <div>
    <div>
      <div class="main-body-content">
        <!-- 顶部 -->
        <div class="main-body-search">
          <div class="main-body-header">普通用户</div>
          <el-input size="small" class="main-input" placeholder="请输入用户名" v-model="search.username"></el-input>
					<el-button size="small" type="primary" @click="load()">查询</el-button>
					<el-button size="small" type="primary" @click="add">新增</el-button>
        </div>

        <!-- 中部表格 -->
        <div class="main-table-box">
          <!-- el-table这个组件会自动把tableData所有数据遍历并展示，而对里面要自定义渲染的列就用v-slot="scope" -->
          <el-table style="width: 100%" :data="tableData" size="small" strip header-cell-class-name="tableHeaderClass">
            <!-- 用户名列 -->
						<el-table-column label="用户名" prop="username"></el-table-column>

            <!-- 头像列 -->
						<el-table-column label="头像">
							<template v-slot="scope">
								<el-image :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]" style="width: 50px; height: 50px; border-radius: 50%"></el-image>
							</template>
						</el-table-column>

            <!-- 真实姓名列 -->
            <el-table-column label="真实姓名" prop="realname"></el-table-column>

            <!-- 手机号列 -->
            <el-table-column label="手机号" prop="phone"></el-table-column>

            <!-- 性别列 -->
						<el-table-column label="性别" prop="sex"></el-table-column>

            <!-- 年龄列 -->
						<el-table-column label="年龄" prop="age"></el-table-column>

            <!-- 操作列 -->
            <el-table-column label="操作">
              <template v-slot="scope">
								<el-button size="small" type="primary" @click="edit(scope.row)">编辑</el-button>
								<el-popconfirm title="确定删除？" @confirm="del(scope.row.id)">
									<el-button size="small" type="danger" slot="reference">删除</el-button>
								</el-popconfirm>
              </template>
            </el-table-column>
          </el-table>
        </div>

        <!-- 表格的分页，在提供每页的记录数和总记录数后该组件会自动计算页数 -->
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

    <!-- 信息修改对话框 -->
    <el-dialog title="请填写信息" :visible.sync="dialogVisible" width="40%">
      <el-form :model="form" label-position="right" label-width="100px" style="padding-right: 40px">
        <!-- 用户名修改 -->
				<el-form-item label="用户名">
					<el-input size="small" v-model="form.username" placeholder="请输入用户名"></el-input>
				</el-form-item>

        <!-- 真实姓名修改 -->
        <el-form-item label="真实姓名">
          <el-input size="small" v-model="form.realname" placeholder="请输入真实姓名"></el-input>
        </el-form-item>

        <!-- 头像上传 -->
				<el-form-item label="头像">
					<el-upload action="http://localhost:8080/files/upload" ref="tx" :on-success="txSuccessUpload">
						<el-button size="small" type="success">点击上传</el-button>
					</el-upload>
				</el-form-item>

        <!-- 手机号修改 -->
				<el-form-item label="手机号">
					<el-input size="small" v-model="form.phone" placeholder="请输入手机号"></el-input>
				</el-form-item>

        <!-- 性别修改 -->
				<el-form-item label="性别">
					<el-radio-group size="small" v-model="form.sex">
						<el-radio label="男"></el-radio>
						<el-radio label="女"></el-radio>
					</el-radio-group>
				</el-form-item>

        <!-- 年龄修改 -->
				<el-form-item label="年龄">
					<el-input size="small" v-model="form.age" placeholder="请输入年龄"></el-input>
				</el-form-item>
      </el-form>

      <!-- 修改信息后的操作按钮 -->
      <div slot="footer" class="dialog-footer">
          <el-button size="small" @click="dialogVisible = false">取 消</el-button>
          <el-button size="small" type="primary" @click="save">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

// 用户模块接口请求统一前缀，往后端发送请求的时候加上这个前缀
let baseUrl = "/user/"

export default {
  data() {
    return {
      user: {},
      tableData: [], // 这是管理员记录数据
      pageNum: 1, // 这是页码
      dialogVisible: false, // 这是管理员信息修改这个对话框的显示状态
      search: {}, // 这是搜索框的数据
      form: {}, // 这是对话框表单的数据
      total: 0, // 这是总记录数
    }
  },

  // 页面加载的时候，做一些事情，在created里面
  mounted() {
    this.load()
  },

  // 定义一些页面上控件出发的事件调用的方法
  methods: {
    // 计算表格操作列的总宽度，并存储在colWidth属性中，用于设置表格操作列的宽度
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

    // 分页加载表格数据，这个在mount里面就调用过一次，也就是说打开这个页面就会加载一次数据库的记录到页面上，而且在用户增删改查后也会加载一次
    load() {
      request.post(baseUrl + "page?pageNum=" + this.pageNum, this.search).then(res => {
        if (res.code === '0') {
          this.tableData = res.data.list;
          this.total = res.data.total;
          this.operationSpace();
          // console.log(this.tableData)
        } else {
          this.$notify.error(res.msg);
        }
      });
    },

    // 新增初始化对话框
    add() {
      this.form = {};
			this.$nextTick(() => {
				this.$refs.tx.clearFiles()
			});

      this.dialogVisible = true;
    },

    // 编辑初始化对话框
    edit(row) {
      this.form = JSON.parse(JSON.stringify(row));
			this.$nextTick(() => {
				this.$refs.tx.clearFiles()
			});
      this.dialogVisible = true;
    },

    // 编辑的数据保存
    save() {
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

    // 将后端返回的图像的URL赋值给form.avatar
		txSuccessUpload(res) {
			this.form.avatar = res.data;
		},

    // 换页的时候更改页码，然后重新加载数据
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load();
    },
  }
}
</script>
