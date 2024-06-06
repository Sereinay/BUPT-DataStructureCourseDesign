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
          <div class="search-bar">
            <el-input v-model="searchQuery" placeholder="搜索日记" clearable @clear="fetchDiaries"></el-input>
            <el-autocomplete
                v-model="searchPlace"
                :fetch-suggestions="querySearch"
                placeholder="请输入地点">
            </el-autocomplete>
            <el-radio-group v-model="sortOption">
              <el-radio label="POPULARITY" @click="sortOption = 'POPULARITY'">按热度</el-radio>
              <el-radio label="RATING" @click="sortOption = 'RATING'">按评分</el-radio>
            </el-radio-group>
            <el-button type="primary" @click="searchDiaries(searchQuery, searchPlace, sortOption)">搜索</el-button>
          </div>
          <el-button v-if="viewOwnDiaries" type="primary" @click="createDialogVisible = true">创建日记</el-button>
          <el-table :data="diaries" style="width: 100%">
            <el-table-column prop="title" label="标题"></el-table-column>
            <el-table-column prop="siteName" label="景点名称"></el-table-column>
            <el-table-column prop="createTime" label="创建时间"></el-table-column>
            <el-table-column v-if="viewOwnDiaries" label="操作">
              <template v-slot:default="scope">
                <el-button size="small" @click="viewDiary(scope.row.id)">查看</el-button>
                <el-button size="small" @click="openEditDiary(scope.row.id)">编辑</el-button>
                <el-button size="small" type="danger" @click="deleteDiary(scope.row.id)">删除</el-button>
              </template>
            </el-table-column>
            <el-table-column v-else label="操作">
              <template v-slot:default="scope">
                <el-button size="small" @click="viewDiary(scope.row.id)">查看</el-button>
                <el-rate v-model="scope.row.rating" @change="rateDiary(scope.row.id, scope.row.rating)"></el-rate>
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
          <el-autocomplete
              v-model="newDiary.siteName"
              :fetch-suggestions="querySearch"
              placeholder="请输入地点">
          </el-autocomplete>
        </el-form-item>
        <el-form-item label="日记内容">
          <el-input type="textarea" v-model="newDiary.content"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="createDialogVisible = false;">取消</el-button>
        <el-button type="primary" @click="saveDiary('DRAFT')">保存为草稿</el-button>
        <el-button type="success" @click="saveDiary('PUBLISHED')">发布</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改日记" v-model="changeDialogVisible">
      <el-form :model="viewDiaryData">
        <el-form-item label="标题">
          <el-input v-model="viewDiaryData.title"></el-input>
        </el-form-item>
        <el-form-item label="景点名称">
          <el-input v-model="viewDiaryData.siteName"></el-input>
        </el-form-item>
        <el-form-item label="日记内容">
          <el-input type="textarea" v-model="viewDiaryData.content"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="createDialogVisible = false;">取消</el-button>
        <el-button type="primary" @click="editDiary(viewDiaryData.id, 'DRAFT')">保存为草稿</el-button>
        <el-button type="success" @click="editDiary(viewDiaryData.id, 'DRAFT')">发布</el-button>
      </div>
    </el-dialog>

    <el-dialog title="查看日记" v-model="viewDialogVisible">
      <el-form :model="viewDiaryData">
        <el-form-item label="标题">
          <el-input v-model="viewDiaryData.title" readonly></el-input>
        </el-form-item>
        <el-form-item label="日记内容">
          <el-input type="textarea" v-model="viewDiaryData.content" readonly></el-input>
        </el-form-item>
        <el-form-item label="地点">
          <el-input v-model="viewDiaryData.siteName" readonly></el-input>
        </el-form-item>
        <el-form-item label="创作者">
          <el-input v-model="viewDiaryData.studentName" readonly></el-input>
        </el-form-item>
        <el-form-item label="评分">
          <el-input v-model="viewDiaryData.score" readonly></el-input>
        </el-form-item>
        <el-form-item label="热度">
          <el-input v-model="viewDiaryData.popularity" readonly></el-input>
        </el-form-item>
        <el-form-item label="修改时间">
          <el-input v-model="viewDiaryData.modifyTime" readonly></el-input>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';
import { logout } from "@/net/index.js";
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
      sortOption: 'RATING',
      searchPlace: '',
      viewOwnDiaries: true,
      changeDialogVisible: false,
      createDialogVisible: false,
      viewDialogVisible: false,
      newDiary: {
        title: '',
        content: '',
        studentName: localStorage.getItem("username"),
        siteName: '',
        status: '',
      },
      viewDiaryData: {
        id: '',
        title: ' ',
        content: ' ',
        siteName: ' ',
        studentName: ' ',
        score: '1',
        popularity: '1',
        modifyTime: '1',
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
        })
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
    searchDiaries(title, siteName, sortOption) {
      const studentName = localStorage.getItem("username");
      const isPublished = !this.viewOwnDiaries;
      const token = getToken();
      if (!token) {
        console.error("Token not found");
        return;
      }
      axios.get('/api/diary/search', {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
        params: {
          title,
          siteName,
          studentName,
          sortOption,
          page: 1,
          size: 10,
          isPublished,
        }
      })
          .then(response => {
            console.log(response.data.data);
            this.diaries = response.data.data.records; // assuming response structure contains data
          })
          .catch(error => {
            console.error('Failed to search diaries:', error);
          });
    },
    openEditDiary(id) {
      this.changeDialogVisible = true;
      const token = getToken();
      if (!token) {
        console.error("Token not found");
        return;
      }
      axios.get(`/api/diary`, {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
        params: {
          id: id
        }
      }).then(response => {
        this.viewDiaryData.id = response.data.data.id;
        this.viewDiaryData.title = response.data.data.title;
        this.viewDiaryData.popularity = response.data.data.popularity;
        this.viewDiaryData.content = response.data.data.content;
        this.viewDiaryData.siteName = response.data.data.siteName;
        this.viewDiaryData.studentName = response.data.data.studentName;
        this.viewDiaryData.score = response.data.data.rating;
        this.viewDiaryData.modifyTime = response.data.data.updateTime;
      }).catch(error => {
        console.error('Failed to view diary:', error);
      });
    },
    querySearch(queryString, cb) {
      const token = getToken();
      if (!token) {
        console.error("Token not found");
        return;
      }

      axios.get('/api/place/search', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          type: 'name',
          query: queryString
        }
      })
          .then(response => {
            console.log(response.data.data);
            const suggestions = response.data.data.map(item => ({ value: item.placeName }));
            cb(suggestions);
          })
          .catch(error => {
            console.error('Failed to fetch suggestions:', error);
          });
    },
    saveDiary(status) {
      const token = getToken();
      if (!token) {
        console.error("Token not found");
        return;
      }
      const params = new URLSearchParams();
      params.append('title', this.newDiary.title);
      params.append('content', this.newDiary.content);
      params.append('studentName', this.getStudentName());
      params.append('siteName', this.newDiary.siteName);
      params.append('status', status);

      axios.post('/api/diary/create', params, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded', // 确保正确的 Content-Type
          'Authorization': `Bearer ${token}`,
        }
      })
          .then(() => {
            this.createDialogVisible = false;
            this.fetchDiaries(this.viewOwnDiaries ? `/api/diary/student?studentName=${this.getStudentName()}` : '/api/diaries/search');
          })
          .catch(error => {
            console.error('Failed to save diary:', error);
          });
    },
    viewDiary(id) {
      this.viewDialogVisible = true;
      const token = getToken();
      if (!token) {
        console.error("Token not found");
        return;
      }
      axios.get(`/api/diary`, {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
        params: {
          id: id
        }
      }).then(response => {
        this.viewDiaryData.title = response.data.data.title;
        this.viewDiaryData.popularity = response.data.data.popularity;
        this.viewDiaryData.content = response.data.data.content;
        this.viewDiaryData.siteName = response.data.data.siteName;
        this.viewDiaryData.studentName = response.data.data.studentName;
        this.viewDiaryData.score = response.data.data.rating;
        this.viewDiaryData.modifyTime = response.data.data.updateTime;
      }).catch(error => {
        console.error('Failed to view diary:', error);
      });
    },
    editDiary(id, status) {
      this.changeDialogVisible = false;
      const token = getToken();
      if (!token) {
        console.error("Token not found");
        return;
      }

      const params = new URLSearchParams();
      params.append('title', this.viewDiaryData.title);
      params.append('content', this.viewDiaryData.content);
      params.append('studentName', this.getStudentName());
      params.append('id', id);
      params.append('status', status);

      axios.put('/api/diary/update', params, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded', // 确保正确的 Content-Type
          'Authorization': `Bearer ${token}`,
        }
      })
          .then(() => {
            this.createDialogVisible = false;
            this.fetchDiaries(this.viewOwnDiaries ? `/api/diary/student?studentName=${this.getStudentName()}` : '/api/diaries/search');
          })
          .catch(error => {
            console.error('Failed to save diary:', error);
          });
    },
    deleteDiary(id) {
      const token = getToken();
      if (!token) {
        console.error("Token not found");
        return;
      }
      axios.delete(`/api/diary/delete`, {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
        params: {
          id: id,
          studentName: this.getStudentName()
        }
      })
          .then(() => {
            this.fetchDiaries(this.viewOwnDiaries ? `/api/diary/student?studentName=${this.getStudentName()}` : '/api/diary/search');
          })
          .catch(error => {
            console.error('Failed to delete diary:', error);
          });
    },
    rateDiary(id, rating) {
      const token = getToken();
      if (!token) {
        console.error("Token not found");
        return;
      }
      const params = new URLSearchParams();
      params.append('score', rating);
      params.append('id', id);

      axios.put('/api/diary/rate', params, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded', // 确保正确的 Content-Type
          'Authorization': `Bearer ${token}`,
        }
      })
          .then(() => {
            this.createDialogVisible = false;
            this.fetchDiaries(this.viewOwnDiaries ? `/api/diary/student?studentName=${this.getStudentName()}` : '/api/diaries/search');
          })
          .catch(error => {
            console.error('Failed to save diary:', error);
          });
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
  background-color: #dcdcdc;
}

.brand {
  font-size: 24px;
  font-weight: bold;
  color: #333;
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

html, body, #app {
  height: 100%;
}

body {
  font-size: 1.6rem;
  font-family: sans-serif;
  color: #333;
  background-color: #dcdcdc;
}

a {
  text-decoration: none;
  display: inline-block;
}

@keyframes hue {
  from {
    filter: hue-rotate(0);
  }
  to {
    filter: hue-rotate(360deg);
  }
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
  background-color: #f8f8f8;
}

.brand {
  color: #333;
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
  color: #333;
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
  background-color: #333;
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

.search-bar {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.search-bar .el-input,
.search-bar .el-autocomplete,
.search-bar .el-radio-group,
.search-bar .el-button {
  margin-right: 10px;
}

.el-table th,
.el-table td {
  color: #333;
}

.el-dialog__header,
.el-dialog__body,
.el-dialog__footer {
  background-color: #f8f8f8;
}
</style>
