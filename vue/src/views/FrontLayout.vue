<!-- 用户的商城页面，相关的子页面写在front文件夹下 -->
<template>
  <div>
    <!-- 顶部 -->
    <div class="header">
      <div class="header-left">
        <img src="@/assets/imgs/logo2.png" alt="">
        <div class="title" @click="$router.push('/front/index')">商品推荐系统</div>
      </div>

      <!-- 页面选择 -->
      <div class="header-center">
        <div class="header-nav">
          <!-- :default-active="$route.path"是绑定当前激活的菜单项为当前路由 -->
          <!-- mode="horizontal"设置菜单为水平模式 -->
          <!-- router是启动路由模式，点击菜单项会导航到对应路由 -->
          <el-menu :default-active="$route.path" mode="horizontal" router>
						<el-menu-item index="/front/index">首页</el-menu-item>
						<el-menu-item index="/front/goods">所有商品</el-menu-item>
            <!-- 下面三个页面只有普通用户看的到 -->
						<el-menu-item v-if="user.role !== 1" index="/front/cart">我的购物车</el-menu-item>
						<el-menu-item v-if="user.role !== 1" index="/front/order">我的订单</el-menu-item>
            <el-menu-item v-if="user.role !== 1" index="/front/ChatPage">对话推荐（还没做完）</el-menu-item>
          </el-menu>
        </div>
      </div>

      <!-- 搜索栏 -->
      <div style="width: 300px">
        <el-input size="medium" style="width: 200px" v-model="name" clearable placeholder="请输入商品名称搜索"></el-input>
        <!-- 点击搜索的时候调用search方法 -->
        <el-button size="medium" @click="search" style="margin-left: 5px">搜索</el-button>
      </div>

      <!-- 用户头像及下拉菜单 -->
      <div class="header-right">
        <!-- 没登陆的时候显示登录和注册按钮 -->
        <div v-if="!user.username">
          <el-button @click="$router.push('/login')">登录</el-button>
          <el-button @click="$router.push('/register')">注册</el-button>
        </div>

        <!-- 登录之后显示头像及下拉菜单 -->
        <div v-else>
          <el-dropdown>
            <div class="header-dropdown">
              <!-- 显示登录用户的头像，是从user.avatar中获取 -->
              <img :src="user.avatar" alt="">
              <div style="margin-left: 10px">
                <!-- 显示登录用户名，user.username -->
                <span>{{ user.username }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
              </div>
            </div>
            <el-dropdown-menu slot="dropdown">
              <!-- 点击个人中心的时候调用goToPerson方法 -->
              <el-dropdown-item><div @click="goToPerson()">个人中心</div></el-dropdown-item>
              <!-- 点击退出登录的时候调用logout方法 -->
              <el-dropdown-item><div style="text-decoration: none" @click="logout">退出</div></el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </div>

    <!-- 顶部下面所有的页面内容 -->
    <div class="main-body">
      <!-- 渲染路由匹配到的组件，ref="child"用于在现在这个组件中调用子路由的方法和数据 -->
      <router-view ref="child" />
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "FrontLayout",

  // data是一个函数，返回一个对象，包含这个组件需要的数据
  data () {
    return {
      top: '',
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      name: ''
    }
  },

  // 页面加载时候做的事情
  mounted() {
  },

  methods: {
    // 根据不同用户身份跳转到对应的个人信息页面
    goToPerson() {
			if (this.user.role === 1) {
				this.$router.push("/end/pAdmin")
			}
			if (this.user.role === 2) {
				this.$router.push("/end/pUser")
			}
    },

    // 清除本地存储的用户信息并跳转到登录页面
    logout() {
      localStorage.removeItem("user");
      this.$router.push("/login");
    },

    // 点击搜索按钮后进行搜索
    search() {
      // 导航到搜索的页面
      this.$router.replace('/front/search?name=' + this.name + '&random' + Math.random())
      // 调用子组件的方法searchGoods，这个方法在Search.vue里面
      this.$refs.child.searchGoods()
    }
  }
}
</script>

<!-- 引用front.css里面的css样式 -->
<style scoped>
  @import "@/assets/css/front.css";
</style>