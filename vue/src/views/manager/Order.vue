<!-- 信息管理/订单管理 -->
<template>
  <div>
    <div>
      <div class="main-body-content">
        <!-- 顶部 -->
        <div class="main-body-search">
          <div class="main-body-header">订单管理</div>
          <el-input size="small" class="main-input" placeholder="请输入订单编号" v-model="search.orderId"></el-input>
					<el-button size="small" type="primary" @click="load()">查询</el-button>
        </div>

        <!-- 中部表格 -->
        <div class="main-table-box">
          <el-table style="width: 100%" :data="tableData" size="small" strip header-cell-class-name="tableHeaderClass" >
            <el-table-column label="商品图片">
							<template v-slot="scope">
								<el-image :src="scope.row.goods.img" :preview-src-list="[scope.row.goods.img]" style="width: 50px; height: 50px; border-radius: 50%"></el-image>
							</template>
						</el-table-column>
            <el-table-column label="商品名称" prop="goods.name"></el-table-column>
            <el-table-column label="购买数量" prop="num"></el-table-column>

            <!-- 订单编号列 -->
            <el-table-column label="订单编号" prop="orderId"></el-table-column>

            <!-- 下单人列 -->
            <el-table-column label="下单人" prop="userName"></el-table-column>

            <!-- 下单时间列 -->
            <el-table-column label="下单时间" prop="time"></el-table-column>

            <!-- 订单价格列 -->
            <el-table-column label="订单价格" prop="price"></el-table-column>

            <!-- 收货人列 -->
            <el-table-column label="收货人" prop="name"></el-table-column>

            <!-- 收货地址 -->
            <el-table-column label="收货地址" prop="address"></el-table-column>

            <!-- 联系方式 -->
            <el-table-column label="联系方式" prop="phone"></el-table-column>

            <!-- 订单状态 -->
            <el-table-column label="订单状态" prop="status"></el-table-column>

            <!-- 操作列 -->
            <el-table-column label="操作" >
              <template v-slot="scope">
                <!-- v-if是判断按钮出现的条件，点击按钮后调用updateStatus方法更新订单状态 -->
                <el-button type="primary" v-if="scope.row.status === '待发货'" @click="updateStatus(scope.row, '已发货')">发货</el-button>
                <el-button type="warning" v-if="scope.row.status === '退款申请中'" @click="updateStatus(scope.row, '退款完成')">退款</el-button>
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

    <!-- 商品介绍富文本框 -->
    <el-dialog title="商品介绍" :visible.sync="richTextDialogVisible">
      <div class="w-e-text" v-html="form.jdjs"></div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

// 商品管理模块接口请求统一前缀
let baseUrl = "/order/"

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
			richTextDialogVisible: false,
    }
  },

  // 页面加载的时候，做一些事情，在created里面
  mounted() {
		// 加载订单信息
		this.load();
  },

  // 定义一些页面上控件出发的事件调用的方法
  methods: {
    operationSpace() {
      let width = 0
      let _this = this
      setTimeout(() => {
        let rows = document.getElementsByClassName('el-table__row');
        if (rows && rows.length) {
          for (const row of rows) {
            let tmpWidth = 0
            let tds = row.getElementsByTagName('td')
            let btns = tds[tds.length - 1].getElementsByTagName('button')
            for (let i = 0; i < btns.length; i++) {
              tmpWidth += btns[i].offsetWidth + 5
            }
            if (tmpWidth > width) {
              width = tmpWidth
            }
          }
        }
        _this.colWidth = width
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

    // 处理表格分页
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load();
    },
    
    // 订单状态更新
    updateStatus(data, status) {
      data.status = status
      request.put(baseUrl, data).then(res => {
        if (res.code === '0') {
          this.$notify.success("操作成功")
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}
</script>
