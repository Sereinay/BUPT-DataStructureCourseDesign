<script setup>
import { ref } from 'vue';
import axios from 'axios';
import dayjs from 'dayjs';

const input = ref('');
const searchType = ref('name');
const orderBy = ref('rating');
const tableData = ref([]);
const showOverlay = ref(false);
const selectedPlace = ref({
  name: '赏味中国',
  windowName: '',
  serious: '这是盐的味道，山的味道，风的味道，阳光的味道，也是时间的味道，人情的味道。\n' +
      '这些味道，已经在漫长的时光中和故土、乡亲、念旧、勤俭、坚忍等等情感和信念混合在一起，\n' +
      '才下舌尖，又上心间，让我们几乎分不清哪一个是滋味，哪一种是情怀。。'
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

    const response = await axios.get('/api/food/findFoodByType', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: {
        name: input.value,
        type: searchType.value,
        orderBy: orderBy.value,
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
        <h1>{{ selectedPlace.name }}</h1>
        <p>{{ selectedPlace.windowName }}:{{ selectedPlace.serious }}</p>
        <button type="button" @click="toRelative">探索</button>
      </div>
      <div class="col">
        <div class="fixed-header" style="background: transparent; display: flex; align-items: center;">
          <el-input @input="showOverlay = true" style="margin-bottom: 5px; margin-top: 30px; width: 30%;" v-model="input"
                    clearable
                    placeholder="想吃点什么？">
            <template #append>
              <el-button style="color: black; width: 40px" @click="searchPlaces" :icon="Search"/>
            </template>
          </el-input>
          <el-radio-group v-model="searchType" style="margin-left: 5px; width: 200px;">
            <el-radio-button label="name">名称</el-radio-button>
            <el-radio-button label="windowName">窗口</el-radio-button>
            <el-radio-button label="serious">菜系</el-radio-button>
          </el-radio-group>
          <el-radio-group v-model="orderBy" style="margin-left: 5px; width: 200px;">
            <el-radio-button label="rating">评分</el-radio-button>
            <el-radio-button label="hot">热度</el-radio-button>
            <el-radio-button label="distance">距离</el-radio-button>
          </el-radio-group>
        </div>
        <div v-if="showOverlay" class="overlay">
          <el-table :data="tableData" stripe style="width: 70%; border-radius: 10px;" @row-click="handleRowClick">
            <el-table-column prop="name" label="名称" width="100"/>
            <el-table-column prop="serious" label="菜系" width="80"/>
            <el-table-column prop="windowName" label="窗口" width="150"/>
            <el-table-column prop="rating" label="评分" width="80"/>
            <el-table-column prop="hot" label="热度" width="80"/>
            <el-table-column prop="distance" label="距离" width="200"/>
          </el-table>
        </div>
        <div class="card card1" @click="toRelative">
          <h5>炸酱面</h5>
          <p>
            北京市的一道传统小吃，属于北京菜，该菜品由菜码、炸酱拌面条而成，流行于北京 、天津、河北等地。</p>
        </div>
        <div class="card card2" @click="toRelative">
          <h5>烤鸭</h5>
          <p>
            烤鸭是北京和南京的一道特色名菜，属于北京菜或金陵菜，该菜品以色泽红艳，肉质细嫩，味道醇厚，肥而不腻的特色，被誉为“天下美味”而驰名中外</p>
        </div>
        <div class="card card3" @click="toRelative">
          <h5>水煮鱼</h5>
          <p>
            水煮鱼又称江水煮江鱼、水煮鱼片，是中国川渝地区的一道特色名菜，属于川菜系，“油而不腻、辣而不燥、麻而不苦、肉质滑嫩”是其特色。</p>
        </div>
        <div class="card card4" @click="toRelative">
          <h5>梅菜扣肉</h5>
          <p>
            梅菜扣肉是汉族传统名菜，走菜时，把肉反扣在盘中。成菜后，肉烂味香，吃起来咸中略带甜味，肥而不腻。 </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { logout } from "@/net/index.js";
import router from "@/router/index.js";
import { Search } from "@element-plus/icons-vue";

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
        localStorage.removeItem("username")
        router.push('/')
      })
    },
    toRelative(){
      router.push('/NodeRelative')
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
  background-image: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url(../assets/food.jpg);
  background-position: center;
  background-size: cover;
  padding-left: 8%;
  padding-right: 8%;
  box-sizing: border-box;
  display: flex;
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
  font-size: 70px;
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
  background-image: url(../assets/mian.jpg);
}

.card2 {
  background-image: url(../assets/ya.jpg);
}

.card3 {
  background-image: url(../assets/yu.jpg);
}

.card4 {
  background-image: url(../assets/rou.jpg);
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
