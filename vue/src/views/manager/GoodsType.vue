<!-- 信息管理/商品分类 -->
<template>
  <div>
    <div>
      <div class="main-body-content">
        <!-- 顶部 -->
        <div class="main-body-search">
          <div class="main-body-header">商品分类管理</div>
          <el-input size="small" class="main-input" placeholder="请输入分类名称" v-model="search.name"></el-input>
					<el-button size="small" type="primary" @click="load()">查询</el-button>
					<el-button size="small" type="primary" @click="add">新增</el-button>
        </div>

        <!-- 中部表格 -->
        <div class="main-table-box">
          <el-table style="width: 100%" :data="tableData" size="small" strip header-cell-class-name="tableHeaderClass" >
            <!-- 分类名称列 -->
						<el-table-column label="分类名称" prop="name"></el-table-column>
						
            <!-- 分类描述列 -->
            <el-table-column label="分类描述" prop="description"></el-table-column>
            
            <!-- 操作列 -->
            <el-table-column label="操作">
              <template v-slot="scope">
								<el-button size="small" type="primary" @click="edit(scope.row)">编辑</el-button>
								<el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
									<el-button size="small" type="danger" slot="reference">删除</el-button>
								</el-popconfirm>
              </template>
            </el-table-column>
          </el-table>
        </div>

        <!-- 表格的分页，在提供每页的记录数和总记录数后该组件会自动计算页数 -->
        <!-- current-page会记录这个分页组件现在的页码 -->
        <!-- 在换页的时候就会触发current-change事件，然后调用对应的方法 -->
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

    <!-- 种类信息编辑对话框 -->
    <el-dialog title="请填写信息" :visible.sync="dialogVisible" width="40%">
      <el-form :model="form" label-position="right" label-width="100px" style="padding-right: 40px">
        <!-- 分类名称修改 -->
				<el-form-item label="分类名称">
					<el-input size="small" v-model="form.name" placeholder="请输入分类名称"></el-input>
				</el-form-item>

        <!-- 分类描述修改 -->
				<el-form-item label="分类描述">
					<el-input size="small" v-model="form.description" placeholder="请输入分类描述"></el-input>
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

// 商品分类模块接口请求统一前缀，往后端发送请求的时候加上这个前缀
let baseUrl = "/type/"

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

    // 编辑初始化模态框
    edit(row) {
      this.form = JSON.parse(JSON.stringify(row));

      this.dialogVisible = true;
    },

    // 编辑数据保存
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

    // 换页的时候更改页码，然后重新加载数据
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load();
    },
  }
}
</script>
