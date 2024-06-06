<script setup>
import {ref} from 'vue';
import axios from 'axios';
import dayjs from 'dayjs';

const placeType = ref('All');
const nodeRelativesPlaceType = ref('All');
const tableData = ref([]);
const nodeRelativesData = ref([]);
const selectedNode = ref(null);
const centerText = ref('');
const leftPlaceType = ref('All');

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
    return localExpire.isBefore(sessionExpire) ? sessionToken.token : localToken.token;
  }

  if (localToken) {
    return localToken.token;
  }

  if (sessionToken) {
    return sessionToken.token;
  }

  return null;
};

const fetchNodesByPlaceType = async () => {
  try {
    const token = getToken();
    if (!token) {
      console.error("Token not found");
      return;
    }
    const response = await axios.get('/api/navigate/placeType', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: {
        placeType: placeType.value,
      },
    });

    if (response.data && response.data.data) {
      tableData.value = response.data.data;
      console.log(tableData.value)
    } else {
      console.error("No data returned from the API");
    }
  } catch (error) {
    console.error("Error fetching nodes:", error);
  }
};

const handleRowClick = async (row) => {
  selectedNode.value = row;
  const formattedNodeId = row.nodeId < 10 ? `0${row.nodeId}` : row.nodeId;
  centerText.value = `${row.placeType} - ${formattedNodeId}`;
  try {
    const token = getToken();
    if (!token) {
      console.error("Token not found");
      return;
    }

    const response = await axios.get('/api/navigate/nodeRelatives', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: {
        placeTypeAndNodeId: `${row.placeType}${formattedNodeId}`,
        placeType: leftPlaceType.value,
      },
    });

    if (response.data && response.data.data) {
      nodeRelativesData.value = response.data.data;
    } else {
      console.error("No data returned from the API");
    }
  } catch (error) {
    console.error("Error fetching node relatives:", error);
  }
};
</script>

<template>
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
  <div class="main-container">
    <div class="left-side">
      <div class="left-top">
        <el-radio-group v-model="leftPlaceType" @change="handleRowClick(selectedNode)">
          <el-radio label="All" style="padding: 10px">All</el-radio>
          <el-radio label="Service" style="padding: 10px">Service</el-radio>
          <el-radio label="Building" style="padding: 10px">Building</el-radio>
          <el-radio label="Intersection" style="padding: 10px">Intersection</el-radio>
        </el-radio-group>
        <h2>{{ centerText }}</h2>
      </div>
      <div class="node-items-container">
        <div v-for="node in nodeRelativesData" :key="node.nodeId" class="node-item">
          {{ node.placeType }} - {{ node.nodeId }}<br>
          Length: {{ node.length }}
        </div>
      </div>
      <div class="left-bottom">
        <img src="../assets/map.jpg" alt="Map" class="map-image">
      </div>
    </div>
    <div class="right-side">
      <div class="top-right">
        <el-radio-group v-model="placeType">
          <el-radio label="All" style="padding: 10px">All</el-radio>
          <el-radio label="Service" style="padding: 10px">Service</el-radio>
          <el-radio label="Building" style="padding: 10px">Building</el-radio>
          <el-radio label="Intersection" style="padding: 10px">Intersection</el-radio>
        </el-radio-group>
        <el-button @click="fetchNodesByPlaceType" style="width: 150px">查询</el-button>
      </div>
      <div class="bottom-right">
        <el-table :data="tableData" @row-click="handleRowClick" height="590" style="overflow: auto;border-radius: 7px"
                  stripe border>
          <el-table-column prop="nodeId" label="Node ID" width="150"/>
          <el-table-column prop="placeType" label="Place Type" width="150"/>
          <el-table-column prop="xposition" label="X Position" width="150"/>
          <el-table-column prop="yposition" label="Y Position" width="150"/>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import {logout} from "@/net/index.js";
import router from "@/router/index.js";

export default {
  name: 'TravelLanding',
  methods: {
    logout() {
      logout(() => {
        localStorage.removeItem("username")
        router.push('/')
      })
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,200;1,100&display=swap');

* {
  margin: 0;
  padding: 0;
  font-family: 'Poppins', sans-serif;
}

header {
  z-index: 999;
  display: flex;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  justify-content: space-between;
  padding: 20px 120px;
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
  margin-left: 50px;
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

.main-container {
  display: flex;
  flex-direction: row;
  height: 100vh;
  padding-top: 80px; /* Adjusted for header height */
  width: 100%;
  background-position: center;
  background-size: cover;
  padding-left: 8%;
  padding-right: 8%;
  box-sizing: border-box;
  background-image: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url(../assets/img.png);
}

.left-side {
  flex: 1;
  padding: 20px;
  background: transparent;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.left-top {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center; /* Center align the content */
}

.node-items-container {
  padding: 30px;
  display: flex;
  flex-wrap: wrap;
  width: 100%;
}

.node-item {
  background: rgba(255, 255, 255, 0.9);
  padding: 5px;
  margin: 5px;
  border-radius: 5px;
  width: calc(20% - 10px); /* Adjust width to fit 5 items per row */
  font-size: 12px;
  text-align: center;
}

.left-bottom {
  width: 100%;
  display: flex;
  justify-content: center;
}

.map-image {
  width: 80%; /* Scale down to 80% of the original size */
  height: auto;
}

.right-side {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 20px;
}

.top-right {
  flex: 0 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: rgba(255, 255, 255, 0.7);
  padding: 10px;
  border-radius: 5px;
}

.bottom-right {
  flex: 1;
  padding-top: 20px;
}

</style>
