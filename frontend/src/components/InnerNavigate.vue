<template>
  <div class="blurred-wrapper">
    <header>
      <a href="#" class="brand">Prometheus</a>
      <div class="menu-btn"></div>
      <div class="navigation">
        <div class="navigation-items">
          <a href="/WelcomeHost">主页</a>
          <a href="/TravelLanding">景点预览</a>
          <a href="#">日记管理</a>
          <a href="/InnerNavigate">内部导航</a>
          <a @click="logout">退出登录</a>
        </div>
      </div>
    </header>

    <section class="hero container">
      <div class="content-wrapper">
        <h1 class="title">
          前路就在眼前<span>.</span>
        </h1>
        <p class="message">
          路，只在脚下
        </p>
      </div>
      <div class="input-wrapper">
        <div class="input-button-row">
          <el-input
              v-model="input"
              style="width: 400px"
              placeholder="您要去的地方"
              clearable
          />
          <el-button type="primary" @click="getPath" style="width: 100px">查询</el-button>
        </div>
        <div class="radio-groups-row">
          <el-radio-group v-model="pathType" style="margin-right: 100px">
            <el-radio-button label="onePath">两点</el-radio-button>
            <el-radio-button label="somePath">多点</el-radio-button>
          </el-radio-group>
          <el-radio-group v-model="travelType" style="padding-right: 14px">
            <el-radio-button label="short">最短路径</el-radio-button>
            <el-radio-button label="time">最短时间</el-radio-button>
          </el-radio-group>
        </div>
        <div class="tags-container">
          <el-tag v-for="node in nodes" :key="node.nodeId" effect="dark" round class="tag-item"
                  style="width: 70px;margin-top: 10px">
            {{ node.placeType + node.nodeId }}
          </el-tag>
        </div>
        <div id="chart" class="chart"></div> <!-- 将图表移到标签下方 -->
      </div>
    </section>
  </div>
</template>

<script>
import { logout } from "@/net/index.js";
import router from "@/router/index.js";
import axios from "axios";
import dayjs from "dayjs";
import * as d3 from "d3";

const getToken = () => {
  const localStorageTokenData = localStorage.getItem('access_token');
  const sessionStorageTokenData = sessionStorage.getItem('access_token');

  let localToken = null;
  let sessionToken = null;

  try {
    if (localStorageTokenData) {
      localToken = JSON.parse(localStorageTokenData);
    }

    if (sessionStorageTokenData) {
      sessionToken = JSON.parse(sessionStorageTokenData);
    }
  } catch (error) {
    console.error("Error parsing token data:", error);
    return null;
  }

  if (localToken && sessionToken) {
    const localExpire = dayjs(localToken.expire);
    const sessionExpire = dayjs(sessionToken.expire);

    return localExpire.isAfter(sessionExpire) ? localToken.token : sessionToken.token;
  }

  if (localToken) {
    return localToken.token;
  }

  if (sessionToken) {
    return sessionToken.token;
  }

  return null;
};

export default {
  name: 'InnerNavigate',
  data() {
    return {
      input: '',
      pathType: 'onePath',
      travelType: 'short',
      nodes: []
    };
  },
  methods: {
    logout() {
      logout(() => {
        localStorage.removeItem("username")
        router.push('/')
      });
    },
    async getPath() {
      try {
        const token = getToken();
        if (!token) {
          console.error("Token not found");
          return;
        }

        const pathMethod = this.pathType === 'onePath' ? 'shortest-path-with-two' : 'shortest-path-with-some';
        let params = {
          type: this.travelType
        };

        if (pathMethod === 'shortest-path-with-two') {
          const inputValues = this.input.split(' ');
          if (inputValues.length === 2) {
            params.from = inputValues[0];
            params.to = inputValues[1];
          } else {
            console.error("Invalid input format for 'shortest-path-with-two'. Expected two values separated by space.");
            return;
          }
        } else if (pathMethod === 'shortest-path-with-some') {
          params.placeList = this.input;
        }

        const response = await axios.get(`/api/navigate/${pathMethod}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
          params: params
        });
        this.nodes = response.data.data;
        this.drawChart();
      } catch (error) {
        console.error(error);
      }
    },
    drawChart() {
      const width = 500;
      const height = 500;
      const margin = 20;

      d3.select("#chart").selectAll("*").remove();

      const svg = d3.select("#chart")
          .append("svg")
          .attr("width", width)
          .attr("height", height)
          .style("border", "1px solid black");

      const xScale = d3.scaleLinear()
          .domain([0, d3.max(this.nodes, d => d.xposition)])
          .range([margin, width - margin]);

      const yScale = d3.scaleLinear()
          .domain([0, d3.max(this.nodes, d => d.yposition)])
          .range([height - margin, margin]);

      svg.selectAll("circle")
          .data(this.nodes)
          .enter()
          .append("circle")
          .attr("cx", d => xScale(d.xposition))
          .attr("cy", d => yScale(d.yposition))
          .attr("r", 5)
          .attr("fill", "blue");

      svg.selectAll("text")
          .data(this.nodes)
          .enter()
          .append("text")
          .attr("x", d => xScale(d.xposition) + 5)
          .attr("y", d => yScale(d.yposition) - 5)
          .text(d => d.placeType + d.nodeId)
          .attr("font-size", "12px")
          .attr("fill", "black");

      const lineGenerator = d3.line()
          .x(d => xScale(d.xposition))
          .y(d => yScale(d.yposition));

      svg.append("path")
          .datum(this.nodes)
          .attr("d", lineGenerator)
          .attr("fill", "none")
          .attr("stroke", "black");
    }
  }
}
</script>

<style scoped>
/*
⚡ MAKE SURE TO SUBSCRIBE PROGRAMMER CLOUD ⚡
*/

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html {
  font-size: 62.5%;
}

body {
  font-size: 1.6rem;
  font-family: sans-serif;
  color: #fff;
}

a {
  text-decoration: none;
  display: inline-block;
}

.tags-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px; /* 标签之间的间隔 */
  max-width: 500px;
}

.tag-item {
  /* 每行显示四个标签 */
  width: calc(100% / 4 - (10px * 3 / 4));
}

/* 确保最后一个标签在新的行中 */
.tag-item:nth-child(4n) {
  margin-right: 0;
}

.input-wrapper {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
}

.input-button-row {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.radio-groups-row {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.blurred-wrapper {
  height: 100vh;
  background: url(../assets/background.jfif) center/cover;
  position: relative;
  z-index: 1;
  overflow: hidden;
  animation: hue 10s infinite alternate;
}

.blurred-wrapper::after {
  content: "";
  width: 100%;
  height: 100%;
  background: inherit;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
  filter: blur(2rem);
  transform: scale(1.15);
}

@keyframes hue {
  from {
    filter: hue-rotate(0);
  }
  to {
    filter: hue-rotate(360deg);
  }
}

.container {
  max-width: 120rem;
  margin: 0 auto;
  padding: 0 1.5rem;
}

header {
  z-index: 999;
  display: flex;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  justify-content: space-between;
  padding: 15px 200px;
  transition: 0.5s ease;
  align-items: center;
}

.brand {
  color: #fff;
  font-size: 1.5rem;
  font-weight: 700;
  text-transform: uppercase;
  text-decoration: none;
}

header .navigation {
  position: relative;
}

header .navigation .navigation-items a {
  position: relative;
  color: #fff;
  font-size: 1rem;
  font-weight: 500;
  text-decoration: none;
  margin-left: 30px;
  transition: 0.3s ease;
}

header .navigation .navigation-items a::before {
  content: "";
  position: absolute;
  bottom: 0px;
  left: 0px;
  background-color: #fff;
  width: 0%;
  height: 3px;
  transition: 0.3s ease;
}

header .navigation .navigation-items a:hover:before {
  width: 100%;
}

section {
  padding: 100px 200px;
}

.hero {
  margin-top: 200px;
  padding: 15px 200px;
  display: flex;
  align-items: center;
  height: calc(100vh - 12rem);
}

.content-wrapper {
  max-width: 40rem;
  transform: translateY(-4rem);
}

.title {
  font-size: 70px;
  margin-bottom: 2rem;
  color: darkkhaki;
}

.message {
  font-size: 30px;
  margin-bottom: 3rem;
  color: grey;
}

.input-wrapper {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
}

#chart {
  margin-top: 20px;
}

@media screen and (min-width: 768px) {
  .btn {
    padding: 1.5rem 3rem;
  }

  .content-wrapper {
    max-width: 60rem;
  }

  .tagline {
    font-size: 1.6rem;
  }

  .title {
    font-size: 6rem;
  }

  .message {
    font-size: 2rem;
  }
}
</style>
