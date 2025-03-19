<!-- 商城推荐系统/所有商品 -->
<template>
  <div class="main-content">
    <!-- <div class="main-line" style="margin-top: 40px;padding: 40px 260px">
      <el-image :src="carousel_1_2" style="width:100%;height: 400px">
        <div slot="placeholder" class="image-slot">
          加载中<span class="dot">...</span>
        </div>
      </el-image>
    </div> -->

    <!-- 上面是原来的轮播图组件，我这里换成了首页的轮播图 -->
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

    <!-- 商品展示和种类选择 -->
    <div class="main-line" style="margin-top: 40px;padding: 20px 200px">
      <div class="main-line-1">
        <!-- el-tabs创建标签页组件，:tab-position="tabPosition"设置了标签页的位置 -->
        <!-- 点击后调用方法进行处理，el-tabs组件在触发这个事件的时候会自动传递一个tab对象给这个处理方法 -->
        <el-tabs :tab-position="tabPosition" v-model="activeName" @tab-click="handleClick" >
          <!-- 遍历typeData数组 -->
          <el-tab-pane v-for="item in typeData" :label="item.name" :name="item.id + ''" :key="item.id" >
            <el-row >
              <!-- :span="6"是让每个el-col占据一行的四分之一宽度，确保每行显示4个商品 -->
              <el-col :span="6" v-for="obj in goodsData" style="margin-bottom: 10px;"  >
                <el-card class="goods-card" :body-style="{ padding: '0px' }" style="height: 400px;width: 95%;margin: auto" >
                  <!-- 商品图片 -->
                  <img :src="obj.img" alt="" style="width: 100%;height: 300px;" class="image" @click="$router.push('/front/detail?id=' + obj.id)">
                  <div style="padding: 14px; text-align: center" @click="$router.push('/front/detail?id=' + obj.id)">
                    <!-- 图片标题 -->
                    <div class="overflowShow" style="font-weight: 550;font-size: 20px; color:#5b5a5a;letter-spacing: 1px;margin-bottom: 24px">{{obj.name}}</div>
                    <!-- 图片价格 -->
                    <div class="bottom clearfix">
                      <span class="time">￥{{obj.price}}</span>
                    </div>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    return {
			carousel_1_1: [
        // require('@/assets/imgs/lun-1.jpg'),
        // require('@/assets/imgs/lun-2.jpg'),
      ],
      // carousel_1_2: require('@/assets/imgs/goods.jpg'),
      activeName: '',
      typeData: [],
      goodsData: [],
      tabPosition: 'right' // 商品种类标签页的位置
    }
  },

  // 监听路由变化，并在目标路由路径为/front/index的时候重新加载页面
  watch: {
    $route(to, from) {
      if(to.path === '/front/index') {
        console.log("hahah")
        location.reload()
      }
    }
  },

  // 加载页面时做的事情
  mounted() {
    request.get("/type").then(res => {
      if (res.code === '0') {
        this.typeData = res.data; // 种类信息
        this.load(this.typeData[0].id) // 默认加载第一个种类的商品
      } else {
        this.$notify.error(res.msg)
        if (res.code === '401') {
          this.$router.push("/login")
        }
      }
    })
    // 加载轮播图
    this.loadBanner()
  },

  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    // 按id加载商品信息，是某一类商品的全部信息
    load(typeId) {
      this.activeName = typeId + ''
      request.get("/goods/type/" + typeId).then(res => {
        if (res.code === '0') {
          this.goodsData = res.data
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

    // 点击标签页商品种类的处理，tab对象在tabs组件被点击的时候会自动传递给这个处理方法
    handleClick(tab) {
      this.load(tab.name) // 重新加载该种类的商品
    },
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
