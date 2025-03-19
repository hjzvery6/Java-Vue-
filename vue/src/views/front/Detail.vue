<!-- 商城推荐系统/点击某商品后的详情页面 -->
<template>
  <div class="detail-container">
    <div class="detail-title">详情信息</div>

    <div class="detail-content">
      <div class="detail-left-img-fields">
        <!-- 商品图片 -->
        <div style="flex: 1;"><img :src="goods.img" style="width: 100%; height: 440px; margin-top: 10px; border-radius: 5px; border: #e3e3e3 solid 1px"></div>

        <div style="flex: 2; margin-left: 50px">
          <!-- 商品名 -->
          <div class="detail-fields-item overflowShow" style="font-size: 18px;color:#000000;font-weight: 550;">{{goods.name}}</div>
          
          <!-- 商品价格(就是有现价和原价的那一格) -->
          <div class="detail-fields-item overflowShow" style="font-size: 18px;color:#000000;font-weight: 550;background-color:#365ec2;color:white;width: 500px;height: 100px;
          text-align: left;line-height: 100px;padding: 0 20px">
            <div style="width: 500px;height: 100px;display:flex;text-align: left;align-items: center;">
              <span style="font-size: 20px">￥</span>
              <span style="font-size: 40px">{{goods.price}}</span>
              <span style="font-size: 20px;margin-left: 10px;font-weight: lighter;text-decoration: line-through">￥{{goods.originalprice}}</span>
            </div>
          </div>

          <!-- 商品地区 -->
          <div class="detail-fields-item overflowShow">
            <span style="font-size: 12px;color: #8c8a8a; margin-right: 10px">商品地区</span>
            <span style="font-size: 13px;color: #2f2e2e">{{goods.location}}</span>
          </div>
          <!-- 商品型号 -->
          <div class="detail-fields-item overflowShow">
            <span style="font-size: 12px;color: #8c8a8a; margin-right: 10px">商品型号</span>
            <span style="font-size: 13px;color: #2f2e2e">{{goods.goodsmodel}}</span>
          </div>
          <!-- 商品运费 -->
          <div class="detail-fields-item overflowShow">
            <span style="font-size: 12px;color: #8c8a8a; margin-right: 10px">商品运费</span>
            <span style="font-size: 13px;color: #2f2e2e">新用户专享首单满38元免邮（部分商品不可用）</span>
          </div>
          <!-- 点个赞吧 -->
          <div class="detail-fields-item overflowShow" style="display: flex;">
            <div style="font-size: 12px;color: #8c8a8a; margin-right: 10px;text-align: center;line-height: 20px">点个赞吧</div>
            <img src="@/assets/imgs/点赞-active.png" v-if="praiseCheck" alt="" style="margin-right: 10px; width: 20px" @click="praise()">
            <img src="@/assets/imgs/点赞.png" v-else alt="" style="margin-right: 10px; width: 20px" @click="praise">
            <div style="font-size: 13px;color: #8c8a8a;text-align: center;line-height: 20px">{{goods.praise}}</div>
          </div>
          <!-- 购买数量选择 -->
          <div class="detail-fields-item overflowShow">
            <span style="font-size: 12px;color: #8c8a8a;  margin-right: 10px">购买数量</span>
            <el-input-number :value="1" size="small" style="width: 100px;" v-model="num"></el-input-number>
          </div>
          <!-- 加入购物车和支付 -->
          <div class="detail-fields-item overflowShow" style="margin-top: 50px">
            <el-button style="margin-left: 5px; width: 200px;height: 60px;background-color: #7ab3f5;color: white;margin-right: 0;border:none;border-radius: 0;" @click="addCart">
              <span style="font-size:16px;">加入购物车</span>
            </el-button>
            <el-button style="width: 200px;height: 60px;background-color: #365ec2;color: white;margin-left: -2px; border:none;border-radius: 0;" @click="buy">
              <span style="font-size:16px;">立即支付</span>
            </el-button>
          </div>
        </div>
      </div>

      <!-- 商品详情下面的广告部分   -->
      <div class="detail-richtext">
        <!-- 商品名称 -->
        <div class="detail-richtext-title">{{goods.name}}</div>
        <!-- 商品描述 -->
        <div class="detail-richtext-content w-e-text" v-html="goods.description"></div>
      </div>
    </div>

    <!-- 评论信息 -->
    <div class="detail-title">评论信息</div>
    <div class="detail-comment">
      <div>
        <el-row v-for="item in comments" style="margin-bottom: 10px">
          <el-col :span="4" style="display: flex; align-items: center;">
            <!-- 评论人头像 -->
            <img :src="item.avatar" style="width: 50px; height: 50px; border-radius: 50%;">
            <!-- 评论人用户名 -->
            <div style="margin-left: 10px; flex: 1" class="overflowShow">{{item.userName}}</div>
          </el-col>
          <el-col :span="20">
            <el-row>
              <!-- 评论内容 -->
              <el-col :span="20" style="height: 50px; line-height: 50px">{{item.content}}</el-col>
              <!-- 评论时间 -->
              <el-col :span="4" style="height: 50px; line-height: 50px; text-align: right">{{item.time}}</el-col>
            </el-row>
            <!-- 这条评论的子评论 -->
            <el-row :span="3" style="margin-top: 20px" v-for="child in item.children">
              <el-col :span="4" style="display: flex; align-items: center;">
                <!-- 子评论人用户名 -->
                <img :src="child.avatar" style="width: 50px; height: 50px; border-radius: 50%;">
                <!-- 子评论人头像 -->
                <div style="margin-left: 10px; flex: 1" class="overflowShow">{{child.userName}} 回复：</div>
              </el-col>
              <!-- 子评论内容 -->
              <el-col :span="16" style="height: 50px; line-height: 50px">{{child.content}}</el-col>
              <!-- 子评论时间 -->
              <el-col :span="4" style="height: 50px; line-height: 50px; text-align: right">{{child.time}}</el-col>
            </el-row>
            <!-- 回复框 -->
            <el-row :span="3" style="margin-top: 20px">
              <el-col :span="15">
                <!-- 输入评论 -->
                <el-input v-model="item.reply"></el-input>
              </el-col>
              <el-col :span="4" style="margin-left: 10px">
                <!-- 回复按钮 -->
                <el-button type="primary" @click="submit(item.id, item.reply)">回复</el-button>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
      </div>
    </div>

    <!-- 支付对话框 -->
    <el-dialog title="请选择收货地址" :visible.sync="dialogVisible" width="40%">
      <el-form :model="goods" label-position="right" label-width="100px" style="padding-right: 40px">
        <!-- 收货人 -->
        <el-form-item>
          <span style="font-size: 12px;color: #8c8a8a;  margin-right: 10px">收货人</span>
          <el-select size="small" style="width: 80%" placeholder="请选择收货人" v-model="addressId">
            <el-option v-for="item in addressList" :value="item.id" :label="item.username" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
        <!-- 收货地址 -->
        <el-form-item>
          <span style="font-size: 12px;color: #8c8a8a;  margin-right: 10px">收货地址</span>
          <el-select size="small" style="width: 80%" placeholder="请选择收货地址" v-model="addressId">
            <el-option v-for="item in addressList" :value="item.id" :label="item.address" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <!-- 取消和支付按钮 -->
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small"  @click="save" style="background-color: #365ec2; color: white;">确 定 支 付</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    let goodsId = this.$route.query.id // 获取当前商品的id
    return {
      user: JSON.parse(localStorage.getItem("user") || {}),
      goods: {}, // 存储商品的详细信息
      goodsId: goodsId,
      // buyNum: 1, // 初始化购买数量
      comments: [], // 评论列表
      addressList: [], // 地址列表
      dialogVisible: false, // 对话框是否可见
      num: 1, // 购买数量
      content: '', // 评论内容
      praiseCheck: false, // 是否点赞
      addressId: null, // 地址id
      // carousel_1_1: [
      //     require("@/assets/imgs/dapai.png")
      // ],
    }
  },

  // 加载的时候做的事情
  mounted() {
    this.load()
    this.loadComment()
    this.checkPraise()
    this.loadAddress()
  },

  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    // 加载商品信息
    load() {
      request.get('/goods/' + this.goodsId).then(res => {
        this.goods = res.data
      })
    },

    // 加载该商品的所有评论信息
    loadComment() {
      request.get("/comment/" + this.goodsId).then(res => {
        if (res.code === '0') {
          this.comments = res.data
        } else {
          this.$notify.error(res.msg)
        }
      })
    },

    // 加载地址信息
    loadAddress(){
      request.get("/address").then(res => {
        if (res.code === '0') {
          this.addressList = res.data
        } else {
          this.$notify.error(res.msg)
        }
      })
    },

    // 点击立即支付后对话框可见
    buy() {
      this.dialogVisible = true;
    },

    // 确定支付后调用
    save() {
      // 先判断当前情况下是否可以支付
      if (!this.user && !this.user.username) {
        this.$notify.error("登录失效，请重新登陆")
        this.$router.push("/login")
        return;
      }
      if (this.user.role === 1) {
        this.$notify.warning("您是管理员，不支持该操作")
        return;
      }
      if (!this.addressId) {
        this.$notify.warning("请选择收货地址,如没有地址，请到地址管理设置地址")
        return
      }
      // 可以的话设置商品的信息
      this.goods.userId = this.user.id;
      this.goods.goodsId = this.goods.id;
      this.goods.addressId = this.addressId;
      this.goods.num = this.num;
      this.goods.id = null;
      request.post("/order/buy/one", this.goods).then(res => {
        if (res.code === '0') {
          this.$notify.success("购买成功")
          this.$router.push("/front/order")
        } else {
          this.$notify.error(res.msg)
        }
      })
    },

    // 加入购物车
    addCart() {
      if (!this.user && !this.user.username) {
        this.$notify.error("登录失效，请重新登陆")
        this.$router.push("/login")
        return;
      }
      if (this.user.role === 1) {
        this.$notify.warning("您是管理员，不支持该操作")
        return;
      }
      let data = {
        userId: this.user.id,
        goodsId: this.goods.id,
        num: this.num,
      }
      request.post("/cart", data).then(res => {
        if (res.code === '0') {
          this.$notify.success("加入成功")
          this.$router.push("/front/cart")
        } else {
          this.$notify.error(res.msg)
        }
      })
    },
    
    // 提交评论
    submit(parentId, content) {
      if (!content) {
        this.$notify.warning("请输入内容")
      }
      let data = {content: content, userId: this.user.id, role: this.user.role, parentId: parentId, relId: this.goodsId}
      request.post("/comment", data).then(res => { // 这里发送的请求同样是后端的Comment控制器来处理，和Comment.vue一样
        if (res.code ==='0') {
          this.$notify.success("评论成功")
          this.loadComment()
          this.content = ""
        } else {
          this.$notify.error(res.msg)
        }
      })
    },

    // 检查是否已经点赞
    checkPraise() {
      request.get("/goods/praise/check/" + this.goodsId).then(res => {
        if (res.code === '0') {
          this.praiseCheck = res.data
        } else {
          this.$notify.error(res.msg)
        }
      })
    },

    // 点赞
    praise() {
      request.get("/goods/praise/" + this.goodsId).then(res => {
        if (res.code === '0') {
          this.checkPraise();
          this.load(); // 点赞后重新加载商品信息
        } else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}
</script>
