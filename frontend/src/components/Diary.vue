<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <a href="#" class="brand">Prometheus</a>
        <div class="menu-btn"></div>
        <div class="navigation">
          <div class="navigation-items">
            <a href="/WelcomeHost">主页</a>
            <a href="/TravelLanding">景点预览</a>
            <a href="/Diary">日记管理</a>
            <a href="/InnerNavigate">内部导航</a>
            <a @click="logout">退出登录</a>
          </div>
        </div>
      </el-header>
      <el-container>
        <el-aside style="display: block; position: absolute; left: 0; top: 60px; bottom: 0; width: 200px">
          <el-menu>
            <el-menu-item @click="fetchOwnDiaries">查看自己的日记</el-menu-item>
            <el-menu-item @click="fetchPublishedDiaries">查看所有已发布日记</el-menu-item>
          </el-menu>
        </el-aside>
        <el-main style="position: absolute; left: 200px; right: 0; top: 60px; bottom: 0; overflow-y: scroll;">
          <el-button v-if="viewOwnDiaries" type="primary" @click="createDialogVisible = true">创建日记</el-button>
          <el-input v-model="searchQuery" placeholder="搜索日记" clearable @clear="fetchDiaries"></el-input>
          <el-button type="primary" @click="searchDiaries">搜索</el-button>
          <el-table :data="diaries" style="width: 100%">
            <el-table-column prop="title" label="标题"></el-table-column>
            <el-table-column prop="siteName" label="景点名称"></el-table-column>
            <el-table-column prop="createTime" label="创建时间"></el-table-column>
            <el-table-column v-if="viewOwnDiaries" label="操作">
              <template v-slot:default="scope">
                <el-button size="small" @click="viewDiary(scope.row.id)">查看</el-button>
                <el-button size="small" @click="editDiary(scope.row.id)">编辑</el-button>
                <el-button size="small" type="danger" @click="deleteDiary(scope.row.id)">删除</el-button>
              </template>
            </el-table-column>
            <el-table-column v-else label="操作">
              <template v-slot:default="scope">
                <el-button size="small" @click="viewDiary(scope.row.id)">查看</el-button>
                <el-button size="small" type="success" @click="rateDiary(scope.row.id)">评分</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-main>
      </el-container>
    </el-container>

    <el-dialog title="创建日记" v-model="createDialogVisible">
      <el-form :model="newDiary">
        <el-form-item label="标题">
          <el-input v-model="newDiary.title"></el-input>
        </el-form-item>
        <el-form-item label="景点名称">
          <el-input v-model="newDiary.siteName"></el-input>
        </el-form-item>
        <el-form-item label="日记内容">
          <el-input type="textarea" v-model="newDiary.content"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="createDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveDiary('DRAFT')">保存为草稿</el-button>
        <el-button type="success" @click="saveDiary('PUBLISHED')">发布</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';
import {logout} from "@/net/index.js";
import router from "@/router/index.js";
import dayjs from "dayjs";

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
  data() {
    return {
      diaries: [],
      searchQuery: '',
      viewOwnDiaries: true,
      createDialogVisible: false,
      newDiary: {
        title: '',
        siteName: '',
        content: '',
        status: '',
        createTime: '',
        updateTime: '',
        studentName: localStorage.getItem("username"),
        rating: '',
        popularity: '',
        code: '',
        rateNum: '',
        padding: ''
      }
    };
  },
  methods: {
    logout() {
      logout(() => {
        localStorage.removeItem("username")
        router.push('/')
      });
    },
    fetchOwnDiaries() {
      this.viewOwnDiaries = true;
      this.fetchDiaries(`/api/diary/student?studentName=${this.getStudentName()}`);
    },
    fetchPublishedDiaries() {
      this.viewOwnDiaries = false;
      this.fetchDiaries('/api/diary/search');
    },
    fetchDiaries(url) {
      try {
        const token = getToken();
        if (!token) {
          console.error("Token not found");
          return;
        }
        axios.get(url, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
        )
            .then(response => {
              this.diaries = response.data.data; // assuming response structure contains data
            })
            .catch(error => {
              console.error('Failed to fetch diaries:', error);
            });
      } catch (error) {
        console.error(error);
      }
    },
    searchDiaries() {
      const url = this.viewOwnDiaries ? `/api/diary/student?studentName=${this.getStudentName()}` : '/api/diary/search';
      axios.get(url, {
        params: {
          title: this.searchQuery,
          siteName: '',
          sortOption: '',
          page: 1,
          size: 10
        }
      })
          .then(response => {
            this.diaries = response.data.data; // assuming response structure contains data
          })
          .catch(error => {
            console.error('Failed to search diaries:', error);
          });
    },
    saveDiary(status) {
      const token = getToken();
      if (!token) {
        console.error("Token not found");
        return;
      }
      const diary = { ...this.newDiary, status };
      axios.post('/api/diary/create', {diary}, {
        headers: {
          Authorization: `Bearer ${token}`,
        }})
          .then(() => {
            this.createDialogVisible = false;
            this.fetchDiaries(this.viewOwnDiaries ? `/api/diary/student?studentName=${this.getStudentName()}` : '/api/diaries/search');
          })
          .catch(error => {
            console.error('Failed to save diary:', error);
          });
    },
    viewDiary(id) {
      this.$router.push({ path: `/Diary/View/${id}` });
    },
    editDiary(id) {
      this.$router.push({ path: `/Diary/Edit/${id}` });
    },
    deleteDiary(id) {
      axios.delete(`/api/diary/${id}`)
          .then(() => {
            this.fetchDiaries(this.viewOwnDiaries ? `/api/diary/student?studentName=${this.getStudentName()}` : '/api/diaries/search');
          })
          .catch(error => {
            console.error('Failed to delete diary:', error);
          });
    },
    rateDiary(id) {
      this.$router.push({ path: `/Diary/Rate/${id}` });
    },
    getStudentName() {
      // 获取当前登录学生的ID
      return localStorage.getItem("username"); // 示例ID
    }
  },
  mounted() {
    this.fetchOwnDiaries();
  }
};
</script>

<style scoped>
.common-layout {
  height: 100%;
  display: flex;
  flex-direction: column;
}
.brand {
  font-size: 24px;
  font-weight: bold;
}
.menu-btn {
  display: none;
}
.navigation {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.navigation-items a {
  margin-left: 20px;
  text-decoration: none;
  color: #000;
}
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
  bottom: 0;
  left: 0;
  background-color: #fff;
  width: 0;
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
