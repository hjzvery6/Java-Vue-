<!-- 信息管理/前台广告位管理 -->
<template>
  <div>
    <div>
      <div class="main-body-content">
        <!-- 顶部 -->
        <div class="main-body-search">
          <div class="main-body-header">前台广告位管理</div>
          <el-input size="small" class="main-input" placeholder="请输入标题" v-model="search.title"></el-input>
					<el-button size="small" type="primary" @click="load()">查询</el-button>
					<el-button size="small" type="primary" @click="add">新增</el-button>
        </div>

        <!-- 中部表格 -->
        <div class="main-table-box">
          <el-table style="width: 100%" :data="tableData" size="small" strip header-cell-class-name="tableHeaderClass" >
						<el-table-column label="广告位封面图片">
							<template v-slot="scope">
								<el-image :src="scope.row.img" :preview-src-list="[scope.row.img]" style="width: 50px; height: 50px; border-radius: 50%"></el-image>
							</template>
						</el-table-column>
            <el-table-column label="广告主标题" prop="title"></el-table-column>
            <el-table-column label="广告副标题" prop="introducing"></el-table-column>
						<el-table-column label="广告商品名" prop="goodName"></el-table-column>
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

        <!-- 表格分页器 -->
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
        <!-- 下拉菜单会把所有商品的名称放在这里选择 -->
        <el-form-item label="商品">
          <el-select size="small" style="width: 100%" v-model="form.goodsId">
            <el-option v-for="item in goodsData" :value="item.id" :label="item.name" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
				<el-form-item label="广告位图片">
					<el-upload action="http://localhost:8080/files/upload" ref="jdtp" :on-success="jdtpSuccessUpload">
						<el-button size="small" type="success">点击上传</el-button>
					</el-upload>
				</el-form-item>
				<el-form-item label="主标题">
					<el-input size="small" v-model="form.title" placeholder="请输入主标题"></el-input>
				</el-form-item>
				<el-form-item label="副标题">
					<el-input size="small" v-model="form.introducing" placeholder="请输入副标题"></el-input>
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
// import wangEditor from "wangeditor";

// 商品管理模块接口请求统一前缀
let baseUrl = "/advertising/"

export default {
  data() {
    return {
      user: {},
      tableData: [],
      pageNum: 1,
      dialogVisible: false,
      search: {},
      form: {},
      total: 0,
      goodsData: [], // 这个商品数据用来制作对话框里的下拉菜单
    }
  },

  // 页面加载的时候，做一些事情，在created里面
  mounted() {
		// 加载商品
		this.loadGoods();
    this.load()
  },

  // 定义一些页面上控件出发的事件调用的方法
  methods: {
    // 计算表格列宽度
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

    // 编辑初始化对话框
    edit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
    },

    // 修改信息保存
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

    // 商品图片更新
		jdtpSuccessUpload(res) {
			this.form.img = res.data;
		},

		// 加载商品给对话框中选商品用
    loadGoods() {
			request.get("/goods").then(res => {
				if (res.code === '0') {
					this.goodsData = res.data;
				} else {
					this.$notify.error(res.msg);
				}
			})
		},

    // 换页处理
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load();
    },
  }
}
</script>
