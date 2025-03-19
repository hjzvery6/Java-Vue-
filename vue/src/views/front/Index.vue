<!-- 商城推荐系统/首页 -->
<template>
  <div class="main-content">
    <!-- 轮播图 -->
		<div class="main-line" style="padding: 0px 20px">
			<div class="main-line-1">
				<div class="main-img">
          <!-- el-carousel创建轮播图 -->
					<el-carousel height="80vh" :interval="2000">
            <!-- v-for遍历数组，点击图片的时候进行跳转将商品id作为查询参数传递给目标页面 -->
						<el-carousel-item v-for="(item,index) in carousel_1_1" :key="index">
							<img :src="item.img" alt=""  style="height: 800px" @click="$router.push('/front/detail?id=' + item.goodsId)">
						</el-carousel-item>
					</el-carousel>
				</div>
			</div>
		</div>

    <!-- 广告展示位 点击可链接到商品详情 -->
    <div class="item-grid"  style="margin-top: 40px;padding: 20px 20px">
      <!-- 这个div是广告的文字，通过循环从advertisingData里面取出广告的数据，然后展示广告的标题和介绍 -->
      <div class="item-grid-item"  v-for="obj in advertisingData" @click="$router.push('/front/detail?id=' + obj.goodsId)">
        <div class="overflowShow" style="width: 70%; font-size: 16px;font-weight:550;letter-spacing:2px;text-align: center; margin: 10px auto">{{obj.title}}</div>
        <div class="overflowShow" style="width: 70%; color:#bbafaf;letter-spacing:1px;text-align: center; margin: 10px auto">{{obj.introducing}}</div>
      </div>
      <!-- 这个div是广告的图片 -->
      <div class="item-grid-item"  :class="{ line: (index + 1) % 5 != 0 }" :key="index" v-for="(obj,index) in advertisingData" @click="$router.push('/front/detail?id=' + obj.goodsId)">
        <div >
          <img :src="obj.img" alt="" style="width: 50%;height: 120px;  ">
        </div>
      </div>
    </div>

    <!-- 热销商品展示 -->
    <div style="padding: 20px 200px">
      <!-- 文字部分 -->
      <div style="margin-top: 60px;margin-bottom: 60px">
        <div style="text-align: center;margin:30px auto">
          <div style="color: #2c2b2b; font-size:28px; font-weight:550; margin-bottom:20px; font-family:'Dubai Medium'">热销产品</div>
        </div>
      </div>
      <!-- 商品展示 -->
      <div>
        <el-row >
          <!-- :span="6"是让每个el-col占据一行的四分之一宽度，确保每行显示4个商品 -->
          <!-- goodsData数组中存储了热销商品的数据 -->
          <el-col :span="6" v-for="obj in goodsData" style="margin-bottom: 10px;" >
            <!-- 每个商品项用el-card封装成一个商品卡片 -->
            <el-card class="goods-card" :body-style="{ padding: '0px' }" style="height: 400px;width: 95%;margin: auto">
              <!-- 商品图片 -->
              <img :src="obj.img" alt="" style="width: 100%;height: 300px;" class="image" @click="$router.push('/front/detail?id=' + obj.id)">
              <div style="padding: 14px; text-align: center" @click="$router.push('/front/detail?id=' + obj.id)">
                <!-- 商品标题 -->
                <div class="overflowShow" style="font-weight: 550;font-size: 16px; color:#5b5a5a;letter-spacing: 1px;margin-bottom: 24px">{{obj.name}}</div>
                <!-- 商品价格 -->
                <div class="bottom clearfix">
                  <span class="time">￥{{obj.price}}</span>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </div>

    <!-- 尾部商城介绍 -->
    <div style="text-align: center;margin:50px auto;" >
      <div style="color: #666666; font-size: 30px;margin-bottom: 30px">海量商品 品质生活</div>
      <div style="color: #999797; font-size: 14px;margin-bottom: 20px">欢迎来到我的商城！这里提供高质量的产品和优质的服务，让您的购物体验更加愉快和舒适</div>
      <div style="color: #999797; font-size: 14px;margin-bottom: 20px">在这里，您可以轻松地浏览各种商品，并享受优惠的价格和促销活动</div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    return {
			carousel_1_1: [], // 轮播图数组
      activeName: '',
      typeData: [],
      goodsData: [], // 热销商品数组
      advertisingData: [], // 广告数组
    }
  },

  // 监听路由变化，并在目标路由路径为/front/index的时候重新加载页面
  watch: {
    $route(to, from) {
      if(to.path === '/front/index') {
        location.reload()
      }
    }
  },

  // 加载页面时做的事情
  mounted() {
    this.load()
    this.loadAdvertising()
    this.loadBanner()
  },

  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    // 加载热门商品
    load() {
      request.get("/goods/hotgoods").then(res => {
        if (res.code === '0') {
          this.goodsData = res.data
        } else {
          this.$notify.error(res.msg)
        }
      })
    },

    // 加载广告位
    loadAdvertising() {
      request.get("/advertising").then(res => {
        if (res.code === '0') {
          this.advertisingData = res.data // 返回请求中的数据赋给广告数组
        } else {
          this.$notify.error(res.msg)
        }
      })
    },

  // 加载轮播图
   loadBanner() {
     request.get("/banner").then(res => {
       if (res.code === '0'){
         this.carousel_1_1 = res.data; // 返回请求中的数据赋给轮播图数组
         console.log(this.carousel_1_1)
       } else {
         this.$notify.error(res.msg);
       }
     })
   },

    // handleClick(tab) {
    //   this.load(tab.name)
    // },
  }
}
</script>

<style scoped>
/* 添加热销商品卡片的鼠标悬停样式 */
.goods-card:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 添加阴影效果 */
  transform: translateY(-5px); /* 稍微向上移动 */
  transition: all 0.3s ease; /* 添加过渡效果 */
}
</style>
