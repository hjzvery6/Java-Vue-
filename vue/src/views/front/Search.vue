<!-- 前台商城/搜素栏搜索后的展示页面 -->
<template>
  <div class="main-content">
    <div class="item-grid">
      <div class="item-grid-item" v-for="obj in goodsData" @click="$router.push('/front/detail?id=' + obj.id)">
        <img :src="obj.img" alt="" style="width: 100%; height: 25vh">
        <div class="overflowShow" style="width: 70%; text-align: center; margin: 10px auto">{{obj.name}}</div>
        <div class="overflowShow" style="width: 70%; text-align: center; margin: 10px auto">价格：￥{{obj.price}}</div>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {

  data() {
    return {
      name: '',
      goodsData: [],
    }
  },
  mounted() {
    this.searchGoods()
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    searchGoods() {
      let name = this.name || this.$route.query.name
      if (!name){
        request.get("/goods").then(res => {
          if (res.code === '0') {
            this.goodsData = res.data
          } else {
            this.$notify.error(res.msg)
          }
        })
      } else {
        request.get("/goods/search/" + name).then(res => {
          if (res.code === '0') {
            this.goodsData = res.data
          } else {
            this.$notify.error(res.msg)
          }
        })
      }
    },
    handleClick(tab) {
      this.load(tab.name)
    }
  }
}
</script>
