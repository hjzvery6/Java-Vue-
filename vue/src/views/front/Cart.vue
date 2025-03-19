<!-- 商城推荐系统/购物车 -->
<template>
  <div class="main-content">
    <div style="margin-top: 40px;padding: 0 260px">
      <div class="main-table-box">
        <div style="height: 50px; line-height: 50px; font-weight: bold; font-size: 20px;margin-bottom: 40px">我的购物车</div>
        <!-- 购物车表格 -->
        <el-table style="width: 100%" :data="cartData" size="small" strip header-cell-class-name="tableHeaderClass" >
          <!-- 商品图片列 -->
          <el-table-column label="商品图片" width="100px">
            <template v-slot="scope">
              <el-image :src="scope.row.img" :preview-src-list="[scope.row.img]" style="width: 60px; height: 60px; border-radius: 50%"></el-image>
            </template>
          </el-table-column>

          <!-- 商品名称列 -->
          <el-table-column label="商品名称" prop="goodsName" width="400px"></el-table-column>

          <!-- 价格列 -->
          <el-table-column label="价格" prop="price"></el-table-column>

          <!-- 数量列 -->
          <el-table-column label="数量" prop="num"></el-table-column>

          <!-- 操作列 -->
          <el-table-column label="操作">
            <template v-slot="scope">
              <el-popconfirm title="确定删除？" @confirm="del(scope.row.id)">
                <el-button size="small" type="danger" slot="reference">删除</el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
        
        <!-- 购买按钮 -->
        <div style="margin-top: 100px; text-align: right; padding: 20px 60px">
          <div class="detail-fields-item overflowShow" style="margin-top: 20px">
            <!-- 监听到点击按钮，调用buy方法 -->
            <el-button style="margin-left: 5px;width: 200px;height: 60px;background-color: #365ec2;color: white" @click="buy">
              <span style="font-size:16px;">购买</span>
            </el-button>
          </div>
        </div>
      </div>
    </div>

    <!-- 购买对话框 -->
    <el-dialog title="请选择收货地址" :visible.sync="dialogVisible" width="40%">
      <el-form :model="goods" label-position="right" label-width="100px" style="padding-right: 40px">
        <el-form-item>
          <span style="font-size: 12px;color: #8c8a8a;  margin-right: 10px">收货人</span>
          <el-select size="small" style="width: 80%" placeholder="请选择收货人" v-model="addressId">
            <el-option v-for="item in addressList" :value="item.id" :label="item.username" :key="item.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <span style="font-size: 12px;color: #8c8a8a;  margin-right: 10px">配送地址</span>
          <el-select size="small" style="width: 80%" placeholder="请选择收货地址" v-model="addressId">
            <el-option v-for="item in addressList" :value="item.id" :label="item.address" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <!-- 操作按钮 -->
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <!-- 监听到点击支付按钮，调用save方法处理 -->
        <el-button size="small"  @click="save" style="background-color: #365ec2; color: white;">支 付</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    let goodsId = this.$route.query.id // 从当前路由的查询参数中获取id并赋值
    return {
      user: JSON.parse(localStorage.getItem("user") || {}),
      cartData: [], // 购物车表格数据
      goods: {},
      goodsId: goodsId,
      addressList: [],
      dialogVisible: false, // 对话框是否可见
      addressId: null, // 收获地址
    }
  },

  // 加载时做的事情
  mounted() {
    this.load()
    this.loadAddress()

  },

  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    // 加载数据
    load() {
      request.get("/cart").then(res => {
        if (res.code === '0') {
          this.cartData = res.data
        } else {
          this.$notify.error(res.msg)
        }
      })
    },

    // 根据id删除
    del(id) {
      request.delete("/cart/" + id).then(res => {
        if (res.code === '0') {
          this.$notify.success("删除成功")
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })
    },

    // 加载收货地址
    loadAddress(){
      request.get("/address").then(res => {
        if (res.code === '0') {
          this.addressList = res.data
        } else {
          this.$notify.error(res.msg)
        }
      })
    },

    // 点击购买的时候调用的方法
    buy() {
      this.dialogVisible = true; // 购买对话框可见
    },

    // 在对话框中支付后调用的方法
    save() {
      // 如果购物车里没有订单
      if (this.cartData.length === 0) {
        this.$notify.warning("您的购物车空空如也")
        return
      }
      // 如果收货地址为空
      if (!this.addressId) {
        this.$notify.warning("请选择收货地址,如没有地址，请到地址管理设置地址")
        return
      }
      let data = {list: this.cartData, addressId: this.addressId}
      request.post("/order/buy", data).then(res => {
        if (res.code === '0') {
          this.$notify.success("购买成功")
          this.$router.push("/front/order")
        } else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}
</script>
