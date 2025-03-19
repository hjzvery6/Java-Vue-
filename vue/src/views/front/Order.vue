<!-- 商城推荐系统/我的订单 -->
<template>
  <div class="main-content">
    <div  style="margin-top: 40px;padding: 0 200px">
      <div class="main-table-box">
        <div style="height: 50px; line-height: 50px; font-weight: bold; font-size: 20px;margin-bottom: 40px">我的订单</div>

        <!-- 订单信息表格 -->
        <el-table style="width: 100%" :data="orderData" size="small" strip header-cell-class-name="tableHeaderClass" >
          <!-- 商品图片列 -->
          <el-table-column label="商品" width="460px">
            <template v-slot="scope">
              <div style="display: flex; align-items: center; margin-bottom: 5px">
                <!-- 把商品的图片、商品名称以及该商品的购买数量都放在这一列中 -->
                <el-image :src="scope.row.goods.img" :preview-src-list="[scope.row.goods.img]" style="width: 40px; height: 40px; border-radius: 50%"></el-image>
                <div style="flex: 1; font-size: 14px; margin-left: 10px">{{scope.row.goods.name}} x {{scope.row.num}}</div>
              </div>
            </template>
          </el-table-column>

          <!-- 订单编号列 -->
          <el-table-column label="订单编号" prop="orderId" width="150px"></el-table-column>

          <!-- 下单人列 -->
          <el-table-column label="下单人" prop="userName"></el-table-column>

          <!-- 下单时间列 -->
          <el-table-column label="下单时间" prop="time" width="150px"></el-table-column>

          <!-- 订单价格 -->
          <el-table-column label="订单价格" prop="price"></el-table-column>

          <!-- 收货人 -->
          <el-table-column label="收货人" prop="name"></el-table-column>

          <!-- 收货地址 -->
          <el-table-column label="收货地址" prop="address"></el-table-column>

          <!-- 联系方式 -->
          <el-table-column label="联系方式" prop="phone" width="100px"></el-table-column>

          <!-- 订单状态 -->
          <el-table-column label="订单状态" prop="status"></el-table-column>

          <!-- 操作列 -->
          <el-table-column label="操作" fixed="right" width="250">
            <template v-slot="scope">
              <!-- 确认收货按钮，只有当订单状态为已发货时才出现，点击后调用updateStatus方法更新订单状态 -->
              <el-button type="primary" v-if="scope.row.status === '已发货'" @click="updateStatus(scope.row, '已完成')" style="margin-right: 5px">确认收货</el-button>

              <!-- 退款按钮，只有当订单状态为已完成时才出现，点击后调用updateStatus方法更新订单状态 -->
              <el-button type="primary" v-if="scope.row.status === '已完成'" @click="updateStatus(scope.row, '退款申请中')">退款</el-button>

              <!-- 评价按钮，只有当订单状态为已完成时才出现，点击后调用openDialog方法打开评价对话框，并传入商品id作为参数 -->
              <el-button type="primary" v-if="scope.row.status === '已完成'" @click="openDialog(scope.row.goodsId)" style="margin-right: 5px">评价</el-button>
              
              <!-- 删除按钮，确认后调用del方法删除订单 -->
              <el-popconfirm title="确定删除？" @confirm="del(scope.row.id)">
                <el-button size="small" type="danger" slot="reference" style="margin-left: 5px">删除</el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- 评价对话框 -->
    <el-dialog title="请填写评价信息" :visible.sync="dialogVisible" width="40%">
      <el-form label-position="right" label-width="100px" style="padding-right: 40px">
        <!-- 评价文本输入 -->
        <el-form-item label="评价内容">
          <el-input size="small" type="textarea" v-model="content" placeholder="请输入评价内容"></el-input>
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

export default {
  data() {
    return {
      user: JSON.parse(localStorage.getItem("user") || {}),
      orderData: [], // 订单信息数组
      content: '', // 评价
      goodsId: '',
      dialogVisible: false, // 评价对话框是否可见
    }
  },

  // 加载的时候做的事
  mounted() {
    this.load()
  },


  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    // 加载订单信息
    load() {
      request.get("/order").then(res => {
        if (res.code === '0') {
          this.orderData = res.data
        } else {
          this.$notify.error(res.msg)
        }
      })
    },

    // 按id删除订单信息
    del(id) {
      request.delete("/order/" + id).then(res => {
        if (res.code === '0') {
          this.$notify.success("删除成功")
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })
    },

    // 更新订单状态
    updateStatus(data, status) {
      data.status = status
      request.put("/order", data).then(res => {
        if (res.code === '0') {
          this.$notify.success("操作成功")
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })
    },

    // 打开评价对话框
    openDialog(goodsId) {
      this.goodsId = goodsId
      this.dialogVisible = true;
    },

    // 保存评价
    save() {
      if (!this.content) {
        this.$notify.warning("请输入内容")
      }
      let data = {content: this.content, userId: this.user.id, role: this.user.role, parentId: 0, relId: this.goodsId}
      request.post("/comment", data).then(res => {
        if (res.code ==='0') {
          this.$notify.success("评价成功")
          this.dialogVisible = false
          this.content = ""
          this.goodsId = ""
        } else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}
</script>
