import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    // 登录和注册的路由
	{ path: '/login', component: () => import("@/views/Login") },
	{ path: '/register', component: () => import("@/views/Register") },

	// 后台管理的路由
    { path: '/end', component: () => import("@/views/ManagerLayout"),
        children: [
            { path: 'dataAnalysis', component: () => import("@/views/manager/DataAnalysis") }, // 数据分析
			{ path: 'admin', component: () => import("@/views/manager/Admin") }, // 管理员
			{ path: 'user', component: () => import("@/views/manager/User") }, // 普通用户
			{ path: 'goodsType', component: () => import("@/views/manager/GoodsType") }, // 商品分类
			{ path: 'goodsInfo', component: () => import("@/views/manager/GoodsInfo") }, // 商品信息管理
			{ path: 'hotGoods', component: () => import("@/views/manager/HotGoods") }, // 热销管理
			{ path: 'pAdmin', component: () => import("@/views/manager/PAdmin")  }, // 管理员个人信息
			{ path: 'pUser', component: () => import("@/views/manager/PUser")  }, // 普通用户个人信息
			{ path: 'order', component: () => import("@/views/manager/Order")  }, // 订单管理
			{ path: 'comment', component: () => import("@/views/manager/Comment")  }, // 评论管理
			// { path: 'notice', component: () => import("@/views/manager/Notice")  },
			{ path: 'address', component: () => import("@/views/manager/Address")  }, // 地址管理
			{ path: 'advertising', component: () => import("@/views/manager/Advertising")  }, // 前台的广告位管理
			{ path: 'banner', component: () => import("@/views/manager/Banner")  }, // 前台的轮播图管理
            { path: 'password', component: () => import("@/views/manager/Password") }, // 修改密码
        ]
    },

	// 前台商城的路由
	{ path: '/front', redirect: 'front/index', component: () => import("@/views/FrontLayout"),
		children: [
			{ path: 'index', component: () => import("@/views/front/Index") },
			{ path: 'detail', component: () => import("@/views/front/Detail") },
			{ path: 'cart', component: () => import("@/views/front/Cart") },
			{ path: 'order', component: () => import("@/views/front/Order") },
			{ path: 'search', component: () => import("@/views/front/Search") },
			{ path: 'goods', component: () => import("@/views/front/Goods") },
			{ path: 'chatPage', component: () => import("@/views/front/ChatPage") },
		]
	},

]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

// 路由守卫
router.beforeEach((to ,from, next) => {
    if (to.path === '/') {
        return next("/login");
    }
    let user = localStorage.getItem("user");
    if (!user && to.path !== '/login' && to.path !== '/register' && to.path !== '/front/index') {
        return next("/login");
    }
    next();
})

export default router
