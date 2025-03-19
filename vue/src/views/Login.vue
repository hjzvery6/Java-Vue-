<!-- 登录页面 -->
<template>
  <div class="login-container">
    <div style="display: flex">
      <el-form>
        <el-form-item class="form-box" style="padding-top: 40px;border-radius: 10px">
          <!-- 用户名输入 -->
          <el-form-item >
            <el-input style="margin-bottom: 15px;" size="large" prefix-icon="el-icon-s-custom" placeholder="请输入用户名" v-model="form.username"></el-input>
          </el-form-item>

          <!-- 密码输入 -->
          <el-form-item >
            <el-input style="margin-bottom: 15px" size="large" show-password prefix-icon="el-icon-key" placeholder="请输入密码" v-model="form.password"></el-input>
          </el-form-item>

          <!-- 登录用户选择 -->
          <el-form-item >
            <el-select style="width: 100%;margin-bottom: 15px" size="large"  placeholder="请选择角色" v-model="form.role">
              <!-- 这里的value会绑定到form.role作为身份验证 -->
              <el-option value="1" label="管理员"></el-option>
              <el-option value="2" label="用户"></el-option>
            </el-select>
          </el-form-item>

          <!-- 登录按钮 -->
          <el-form-item>
            <!-- 点击登录按钮的时候调用login方法对表单数据进行验证 -->
            <el-button style="width: 100%;height: 50px;background-color: #282b33;border-color: #282b33;color:white" size="large"  @click="login()">登录</el-button>
          </el-form-item>
        </el-form-item>

        <!-- 跳转注册 -->
        <el-form-item style="background-color: #ffffff; border-radius: 10px; display: flex; justify-content: center;align-items: center">
          <el-form-item class="regBox">
            <!-- 点击注册的时候调用navRegister方法跳转到注册页面 -->
            <a href="javascript:void(0)" @click="navRegister()" style="text-indent: 12px;">注册新账号</a><span class="el-icon-right"></span>
          </el-form-item>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
// request里面是进行前后端通信的依赖
import request from "@/utils/request";

export default {
  name: "LoginView",
  data() {
    return {
      form: {}
    }
  },

  // 页面加载的时候，做一些事情，在created里面
  mounted() {
  },


  // 定义一些页面上控件出发的事件调用的方法
  methods: {
    // 登录按钮点击事件入口
    login() {
      // 这是前端对表单内容的验证，防止输入为空的情况
      if (!this.form.username) {
        this.$notify.error("请输入用户名");
        return;
      }
      if (!this.form.password) {
        this.$notify.error("请输入密码");
        return;
      }
      if (!this.form.role) {
        this.$notify.error("请选择角色");
        return;
      }

      // 调用后台登录接口
      request.post('/login', this.form).then(res => {
        if(res.code === '0') {
          // 将用户信息保存一份到浏览器缓存里
          let user = res.data.user;
          let token = res.data.token;

          // 登录成功后用户信息会存储在localStorage里，方便其他页面获取用户信息
          localStorage.setItem('user', JSON.stringify(user));
          localStorage.setItem('token', token);
          this.$notify.success("登录成功");

          // 登录成功后不同的用户跳转到对应的页面，管理员到后台页面，普通用户到商城首页
          if (user.role === 1) {
            location.href = "/end/pAdmin"
          }
          if (user.role === 2) {
            location.href = "/front/index"
          }
        } else {
          // 否则就是登录失败了
          this.$notify.error(res.msg);
        }
      });
    },
    // 注册按钮点击事件入口
    navRegister() {
      this.$router.push("/register")
    }
  }
}
</script>

<!-- scoped保证这里的样式只在这个组件使用 -->
<style scoped>
.login-container {
  height: 100vh;
  overflow: hidden;
  background-image: url("@/assets/imgs/login_bg.jpg");
  background-size: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.form-box {
  width: 500px;
  padding: 20px 40px;
  border-radius: 2px;
  background-color: #ffffff;
  opacity: 1;
}

::deep .el-form-item {
  margin-bottom: 5px;
}
::deep .el-input__inner {
  border-color: #ccc !important;
  border-radius: 2px;
  background-color: #ffffff !important;
  color: #333;
}
::deep .el-input__icon {
  color: #666;
}

a {
  text-decoration: none;
  color: #0c0c0c;
  font-weight: bold;
}
.regBox {
  font-size: 28px;
  margin-bottom: 10px;
  margin-top: 10px;
  text-align: right;
}
</style>