<!-- 信息管理/商品信息管理 -->
<template>
  <div>
    <div>
      <div class="main-body-content">
        <!-- 顶部 -->
        <div class="main-body-search">
          <div class="main-body-header">商品信息管理</div>
          <el-input size="small" class="main-input" placeholder="请输入商品名称" v-model="search.name"></el-input>
					<el-button size="small" type="primary" @click="load()">查询</el-button>
					<el-button size="small" type="primary" @click="add">新增</el-button>
        </div>

        <!-- 中部表格 -->
        <div class="main-table-box">
          <!-- 表格数据来自tableData -->
          <el-table style="width: 100%" :data="tableData" size="small" strip header-cell-class-name="tableHeaderClass" >
            <!-- 商品图片列 -->
						<el-table-column label="商品图片">
							<template v-slot="scope">
								<el-image :src="scope.row.img" :preview-src-list="[scope.row.img]" style="width: 50px; height: 50px; border-radius: 50%"></el-image>
							</template>
						</el-table-column>

            <!-- 商品名称列 -->
						<el-table-column label="商品名称" prop="name"></el-table-column>

            <!-- 商品分类列 -->
						<el-table-column label="商品分类" prop="type"></el-table-column>

            <!-- 商品型号列 -->
						<el-table-column label="商品型号" prop="goodsmodel"></el-table-column>

            <!-- 商品地区列 -->
						<el-table-column label="商品地区" prop="location"></el-table-column>

            <!-- 商品价格列 -->
						<el-table-column label="商品价格" prop="price"></el-table-column>

            <!-- 库存列 -->
						<el-table-column label="库存" prop="goodsnum"></el-table-column>

            <!-- 点赞量列 -->
						<el-table-column label="点赞量" prop="praise"></el-table-column>

            <!-- 是否热门列 -->
						<el-table-column label="是否热门" prop="ishot"></el-table-column>

            <!-- 商品介绍列 -->
						<el-table-column label="商品介绍">
							<template v-slot="scope">
								<el-button size="small" type="primary" @click="initEditorView(scope.row)">查看介绍</el-button>
							</template>
						</el-table-column>

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
        
        <!-- 表格的分页组件，在提供每页的记录数和总记录数后该组件会自动计算页数 -->
        <!-- 点击换页按钮或者某个页码的时候，pageNum就会更新成新的页码，同时调用handleCurrentChange方法 -->
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
        <!-- 商品图片修改 -->
				<el-form-item label="商品图片">
          <!-- action里面指定文件上传的目标地址，在选择文件后通过请求把文件发送到后端保存，后端对应的接口会进行处理 -->
          <!-- ref="jdtp"设置引用名称，用于访问该组件 :on-success="jdtpSuccessUpload"监听文件上传成功的时间并调用该方法 -->
					<el-upload action="http://localhost:8080/files/upload" ref="jdtp" :on-success="jdtpSuccessUpload">
						<el-button size="small" type="success">点击上传</el-button>
					</el-upload>
				</el-form-item>

        <!-- 商品名称修改 -->
				<el-form-item label="商品名称">
					<el-input size="small" v-model="form.name" placeholder="请输入商品名称"></el-input>
				</el-form-item>

        <!-- 商品分类修改 -->
				<el-form-item label="商品分类">
					<el-select size="small" style="width: 100%" v-model="form.typeId">
            <!-- v-for="item in typeData"是遍历typeData数组生成多个选项给下拉菜单选择 -->
            <!-- :label="item.name"是展示在菜单中的选项标签 -->
						<el-option v-for="item in typeData" :value="item.id" :label="item.name" :key="item.id"></el-option>
					</el-select>
				</el-form-item>

        <!-- 是否热门修改 -->
        <el-form-item label="是否热门">
          <el-radio-group size="small" v-model="form.ishot">
            <el-radio label="是"></el-radio>
            <el-radio label="否"></el-radio>
          </el-radio-group>
        </el-form-item>

        <!-- 商品地区修改 -->
				<el-form-item label="商品地区">
					<el-input size="small" v-model="form.location" placeholder="请输入商品地区"></el-input>
				</el-form-item>

        <!-- 商品价格修改 -->
				<el-form-item label="商品价格">
					<el-input size="small" v-model="form.price" placeholder="请输入商品价格"></el-input>
				</el-form-item>

        <!-- 商品型号修改 -->
        <el-form-item label="商品型号">
          <el-input size="small" v-model="form.goodsmodel" placeholder="请输入商品型号"></el-input>
        </el-form-item>

        <!-- 库存修改 -->
        <el-form-item label="库存">
          <el-input size="small" v-model="form.goodsnum" placeholder="请输入商品库存"></el-input>
        </el-form-item>

        <!-- 商品介绍修改 -->
				<el-form-item label="商品介绍">
          <!-- id="wang-editor"是富文本编辑器的id -->
					<div id="wang-editor"></div>
				</el-form-item>
      </el-form>

      <!-- 修改信息后的操作按钮 -->
      <div slot="footer" class="dialog-footer">
          <el-button size="small" @click="dialogVisible = false">取 消</el-button>
          <el-button size="small" type="primary" @click="save">保 存</el-button>
      </div>
    </el-dialog>

    <!-- 商品介绍信息富文本对话框 -->
	  <el-dialog title="商品介绍" :visible.sync="richTextDialogVisible">
      <!-- v-html="form.description"是将form.description的值直接显示在富文本中 -->
		  <div class="w-e-text" v-html="form.description"></div>
	  </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
import wangEditor from "wangeditor";

// 商品信息管理模块接口请求统一前缀,，往后端发送请求的时候加上这个前缀
let baseUrl = "/goods/"

// 富文本
let editor

// 初始化富文本编辑器
function initWangEditor(content) {	setTimeout(() => {
		if (!editor) {
			editor = new wangEditor('#wang-editor') // 通过传入选择器#wang-editor，将富文本编辑器绑定到页面中id=wang-editor的DOM元素上
			editor.config.placeholder = '请输入商品介绍'
			editor.config.uploadFileName = 'file'
			editor.config.uploadImgServer = 'http://localhost:8080/files/wang/upload' // 图片上传的服务器地址
			editor.create()
		}
		editor.txt.html(content) // 将传入的content参数作为初始内容
	}, 0)
}

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
      typeData: [],
			richTextDialogVisible: false, // 富文本编辑器可见参数
    }
  },

  // 页面加载的时候，做一些事情，在created里面
  mounted() {
		this.loadType();
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

 		// 加载商品分类
     loadType() {
			request.get("/type").then(res => {
				if (res.code === '0') {
          console.log(res);
					this.typeData = res.data;
				} else {
					this.$notify.error(res.msg);
				}
			})
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
			initWangEditor(''); // 里面还有一个富文本编辑器要初始化
			this.$nextTick(() => { // 清除上传组件中之前选择的文件
				this.$refs.jdtp.clearFiles()
			});
      // 对话框可见
      this.dialogVisible = true;
    },

    // 编辑初始化对话框
    edit(row) {
      this.form = JSON.parse(JSON.stringify(row));
			initWangEditor(row.description); // 里面还有一个富文本编辑器要初始化
      this.$nextTick(() => { // 清除上传组件中之前选择的文件
				this.$refs.jdtp.clearFiles()
			});
      // 对话框可见
      this.dialogVisible = true;
    },

    // 在查看商品介绍时，商品介绍的富文本对话框
		initEditorView(o) {
			this.form = JSON.parse(JSON.stringify(o));
      // 富文本对话框可见
			this.richTextDialogVisible = true;
		},

    // 编辑信息保存
    save() {
			this.form.description = editor.txt.html();
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

    // 换页处理
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load();
    },
  }
}
</script>
