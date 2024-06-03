<script setup>
import {ref} from 'vue';
import axios from 'axios';
import dayjs from 'dayjs';

const input = ref('');
const searchType = ref('name');
const tableData = ref([]);
const showOverlay = ref(false);
const selectedPlace = ref({
  placeName: '北京',
  information: '故宫的守望者，长城的摆渡人，千年帝都，文化之根深脉络，历史之风韵犹存。见证了无数王朝的兴衰更迭，承载着中华五千年的文明史，是历史与现代交响的永恒乐章。'
});

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

const searchPlaces = async () => {
  try {
    const token = getToken();
    if (!token) {
      console.error("Token not found");
      return;
    }

    const response = await axios.get('/api/place/search', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: {
        type: searchType.value,
        query: input.value,
      },
    });

    if (response.data && response.data.data) {
      tableData.value = response.data.data;
    } else {
      console.error("No data returned from the API");
    }
  } catch (error) {
    console.error("Error searching places:", error);
  }
};

const handleRowClick = (row) => {
  selectedPlace.value = row;
  showOverlay.value = false;
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
  <div class="container">
    <div class="row">
      <div class="col">
        <h1>{{ selectedPlace.placeName }}</h1>
        <p>{{ selectedPlace.information }}</p>
        <button type="button">探索</button>
      </div>
      <div class="col">
        <div class="fixed-header" style="background: transparent">
          <el-input @input="showOverlay = true" style="margin-bottom: 5px;margin-top: 30px;width: 70%;" v-model="input" clearable
                    placeholder="您要去哪里">
            <template #append>
              <el-button style="color: black;width: 60px" @click="searchPlaces" :icon="Search"/>
            </template>
          </el-input>
          <el-select v-model="searchType" placeholder="选择搜索类型" style="width: 20%; margin-top: 30px;margin-left: 10px;">
            <el-option label="名称" value="name"></el-option>
            <el-option label="信息" value="info"></el-option>
          </el-select>
        </div>
        <div v-if="showOverlay" class="overlay">
          <el-table :data="tableData" stripe style="width: 70%;border-radius: 10px;" @row-click="handleRowClick">
            <el-table-column prop="placeName" label="名称" width="80"/>
            <el-table-column prop="rating" label="评分" width="60"/>
            <el-table-column prop="type" label="类型" width="80"/>
            <el-table-column prop="information" label="简介"/>
          </el-table>
        </div>
        <div class="card card1">
          <h5>长城</h5>
          <p>
            长城是中国古代建筑的杰作，是世界上最著名的防御工程之一，也是中国的标志性建筑之一，以其雄伟壮丽的气势吸引着无数游客和历史爱好者。</p>
        </div>
        <div class="card card2">
          <h5>天坛</h5>
          <p>
            天坛位于北京市，是明清两代帝王祭祀天地之所，是中国古代建筑艺术的典范之一，也是世界文化遗产。其建筑规划、设计精巧，</p>
        </div>
        <div class="card card3">
          <h5>故宫</h5>
          <p>
            是中国明清两代的皇家宫殿，也是世界上最大、保存最完整的古代木质结构建筑群之一，其建筑风格庄严典雅，是中国古代建筑的杰作，吸引着大量游客和历史爱好者前来参观。</p>
        </div>
        <div class="card card4">
          <h5>国贸</h5>
          <p>
            是北京市最重要的商务和金融中心之一。包括中国尊、国贸大厦等高层建筑，是北京市标志性建筑之一，也是国内外企业总部和高端商务办公区的集聚地。</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {logout} from "@/net/index.js";
import router from "@/router/index.js";
import {Search} from "@element-plus/icons-vue";

export default {
  name: 'TravelLanding',
  data() {
    return {
      searchKeyword: '', // 用户输入的搜索关键词
      showOverlay: false // 控制覆盖层显示状态
    };
  },
  computed: {
    Search() {
      return Search
    }
  },
  methods: {
    logout() {
      logout(() => {
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
  width: 70%;
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
  margin-left: 40px;
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


.container {
  width: 100%;
  height: 100vh;
  background-image: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url(../assets/img.png);
  background-position: center;
  background-size: cover;
  padding-left: 8%;
  padding-right: 8%;
  box-sizing: border-box;
}

nav {
  flex: 1;
  text-align: right;
}

nav ul li {
  list-style: none;
  display: inline-block;
  margin-left: 60px;
}

nav ul li a {
  text-decoration: none;
  color: #fff;
  font-size: 13px;
}

.row {
  display: flex;
  height: 88%;
  align-items: center;
}

.col {
  flex-basis: 50%;
}

.fixed-header {
  position: sticky;
  top: 0;
  background: rgba(255, 255, 255, 0.9);
  z-index: 1000;
  padding: 10px 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.overlay {
  position: absolute;
  padding-top: 10px;
  width: 60%;
  max-width: 60%;
  max-height: 60%;
  overflow-y: auto;
  background: transparent;
  border-radius: 10px;
  box-shadow: 0 0px 0px rgba(0, 0, 0, 0.1);
}


h1 {
  color: #fff;
  font-size: 100px;
}

p {
  color: #fff;
  font-size: 11px;
  line-height: 15px;
}

button {
  width: 180px;
  color: #000;
  font-size: 12px;
  padding: 12px 0;
  background: #fff;
  border: 0;
  border-radius: 20px;
  outline: none;
  margin-top: 30px;
  cursor: pointer;
}


.card {
  width: 240px;
  height: 230px;
  display: inline-block;
  border-radius: 10px;
  padding: 15px 25px;
  box-sizing: border-box;
  cursor: pointer;
  margin: 10px 15px;
  background-position: center;
  background-size: cover;
  transition: transform 0.5s;
}

.card1 {
  background-image: url(../assets/theGreatWall.jpg);
}

.card2 {
  background-image: url(../assets/tianTan.jpg);
}

.card3 {
  background-image: url(../assets/guGong.jpg);
}

.card4 {
  background-image: url(../assets/guoMao.jpeg);
}

.card:hover {
  transform: translateY(-10px);
}

h5 {
  color: #fff;
  text-shadow: 0 0 5px #999;
}

.card p {
  text-shadow: 0 0 5px #000;
  font-size: 8px;
}
</style>
