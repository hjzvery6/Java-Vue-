<!-- 系统首页/数据分析 -->
<template>
  <div>
    <div class="main-body-header">商品数据分析</div>
    <div class="main-body-content">
      <div>
        <!-- <el-row :gutter="20"> -->
        <el-row>
          <!-- 饼图，id是后续用ECharts初始化并渲染 -->
          <el-col :span="24">
            <div id="bie" style="height: 600px"></div>
          </el-col>
          
          <!-- 柱状图，id是后续用ECharts初始化并渲染 -->
          <el-col :span="24">
            <div id="bar" style="height: 400px"></div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import * as echarts from "echarts";

export default {
  name: 'IndexView',

  data() {
    return {
      activeName: '',
      notices: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },

  mounted() {
    this.initEcharts();
  },

  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    initEcharts() {
      // 向后端发出请求
      request.get("/order/echarts/bie").then(res => {
        if (res.code === '0') {
          // 开始去渲染饼图数据啦
          this.initBie(res.data)
        } else {
          this.$message.error(res.msg)
        }
      })
      request.get("/order/echarts/bar").then(res => {
        if (res.code === '0') {
          let map = res.data;
          // 开始去渲染柱状图数据啦
          this.initBar(map.xAxis, map.yAxis)
        } else {
          this.$message.error(res.msg)
        }
      })
    },

    // 饼图初始化
    initBie(data) {
      let chartDom = document.getElementById('bie');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        title: {
          text: '商品总销售额（饼图）',
          subtext: '统计维度：商品',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '商品',
            type: 'pie',
            radius: '50%',
            data: data,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };

      option && myChart.setOption(option);
    },

    // 柱状图初始化
    initBar(xAxis, yAxis) {
      let chartDom = document.getElementById('bar');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        title: {
          text: '订单分类总销量（柱状图）',
          subtext: '统计维度：商品分类',
          left: 'center'
        },
        // x轴为类别轴
        xAxis: {
          type: 'category',
          data: xAxis
        },
        // y轴为数值轴
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: yAxis,
            type: 'bar',
            showBackground: true,
            backgroundStyle: {
              color: 'rgba(180, 180, 180, 0.2)'
            }
          }
        ]
      };

      option && myChart.setOption(option);
    },
  }
}
</script>
