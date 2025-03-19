<!-- 用户的后台页面，菜单中的子页面写在manager文件夹下 -->
<template>
  <div>
    <!-- 头部 -->
    <div class="manager-header" id="header">
      <a href="">
        <div class="manager-header-left">
          <img src="@/assets/imgs/logo2.png" alt="">
          <div class="title">商品推荐系统</div>
        </div>
      </a>

      <div class="manager-header-center">欢迎您，{{ user.username }}</div>

      <!-- 用户头像的下拉菜单 -->
      <div class="manager-header-right">
        <el-dropdown placement="bottom">
          <!-- 显示登录用户的头像，是从user.avatar中获取 -->
          <div class="avatar-box"><img :src="user.avatar"></div>
          <el-dropdown-menu slot="dropdown">
            <!-- 点击个人信息的时候调用goToPerson方法 -->
            <div @click="goToPerson()"><el-dropdown-item>个人信息</el-dropdown-item></div>
            <a href="/front/index"><div><el-dropdown-item>前往前台</el-dropdown-item></div></a>
            <!-- 点击退出登录的时候调用logout方法 -->
            <div @click="logout()"><el-dropdown-item>退出登录</el-dropdown-item></div>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>

    <!-- 左侧的菜单以及展开的子页面 -->
    <div style="display: flex">
      <!-- 菜单 -->
      <div id="main-menu">
        <div class="main-nav">
          <!-- :default-active="$route.path"是绑定当前激活的菜单项为当前路由 -->
          <!-- router是启动路由模式，点击菜单项会导航到对应路由 -->
          <!-- 过程：例如点击系统首页下的数据分析的时候，index="/end/index"触发导航，Vue Router会将浏览器地址栏的路径进行更改，通过router-view进行渲染 -->
          <el-menu :default-active="$route.path" router class="main-nav-menu">
            <!-- 这个菜单栏只有管理员能看到 -->
						<el-submenu index="1" v-if="user.role === 1">
							<template slot="title"><i class="el-icon-house"></i><span>系统首页</span></template>
							<el-menu-item index="/end/dataAnalysis">商品数据分析</el-menu-item>
						</el-submenu>

            <!-- 这个菜单栏只有管理员能看到 -->
						<el-submenu index="2" v-if="user.role === 1">
							<template slot="title"><i class="el-icon-user"></i><span>用户管理</span></template>
							<el-menu-item index="/end/admin">管理员</el-menu-item>
							<el-menu-item index="/end/user">普通用户</el-menu-item>
						</el-submenu>
            
            <!-- index是菜单栏中每个子菜单的唯一表示 -->
						<el-submenu index="3">
							<template slot="title"><i class="el-icon-folder-opened"></i><span>信息管理</span></template>
              <!-- user.role === 1的是只有管理员能看到，user.role !== 1的是只有普通用户能看到，没写的就是都看得到 -->
							<!-- <el-menu-item index="/end/notice" v-if="user.role === 1">系统公告</el-menu-item> -->
              <el-menu-item index="/end/goodsType" v-if="user.role === 1">商品分类管理</el-menu-item>
              <el-menu-item index="/end/goodsInfo" v-if="user.role === 1">商品信息管理</el-menu-item>
              <el-menu-item index="/end/hotGoods" v-if="user.role === 1">热销商品管理</el-menu-item>
              <el-menu-item index="/end/advertising" v-if="user.role === 1">前台广告位管理</el-menu-item>
              <el-menu-item index="/end/banner" v-if="user.role === 1">前台轮播图管理</el-menu-item>
              <el-menu-item index="/end/order" v-if="user.role === 1">订单管理</el-menu-item>
							<el-menu-item index="/end/comment">评论管理</el-menu-item>
							<el-menu-item index="/end/address" v-if="user.role !== 1">地址管理</el-menu-item>
						</el-submenu>

						<el-submenu index="4">
							<template slot="title"><i class="el-icon-setting"></i><span>系统管理</span></template>
							<el-menu-item index="/end/password">修改密码</el-menu-item>
              <!-- 点击退出登录的时候调用logout方法 -->
							<el-menu-item index="/end/logout" @click="logout()">退出登录</el-menu-item>
						</el-submenu>
          </el-menu>
        </div>
      </div>

      <!-- 对应的子页面 -->
      <div class="main-body">
        <div>
          <!-- 用户在菜单栏选择切换的时候，会渲染对应的子页面 -->
          <!-- 过程：例如点击系统首页下的数据分析的时候，index="/end/index"触发导航，Vue Router会将浏览器地址栏的路径进行更改，通过router-view进行渲染 -->
          <router-view />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "ManagerLayout",
  data () {
    return {
      // 获取当前登录用户的信息
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },

  // 监听到更新头像的事件，更新头像
  mounted() {
    this.$bus.$on('updateTx', (tx) => {
      this.user.tx = tx
    })
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

    // navToFront() {
    // },

    // 清除本地存储的用户信息并跳转到登录页面
    logout() {
      localStorage.removeItem("user");
      this.$router.push("/login");
    }
  }
}
</script>

<!-- 引用manager.css里面设置的css样式 -->
<style scoped>
  @import "@/assets/css/manager.css";
</style>