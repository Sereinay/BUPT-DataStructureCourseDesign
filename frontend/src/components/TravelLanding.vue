<template>
  <div class="container">
    <div class="navbar">
      <img src="../assets/logo.png" class="logo" />
      <nav>
        <ul>
          <li><a href="/WelcomeHost">首页</a></li>
          <li><a @click="logout">退出</a></li>
        </ul>
      </nav>
      <img src="../assets/menu.png" class="menu-icon" />
    </div>
    <div class="row">
      <div class="col">
        <h1>北京</h1>
        <p>
          故宫的守望者，长城的摆渡人，千年帝都，文化之根深脉络，历史之风韵犹存。见证了无数王朝的兴衰更迭，承载着中华五千年的文明史，是历史与现代交响的永恒乐章。
        </p>
        <button type="button">探索</button>
      </div>
      <div class="col">
        <div class="card card1">
          <h5>故宫</h5>
          <p>联合国的其他成员国从未正式承认</p>
        </div>
        <div class="card card2">
          <h5>天坛</h5>
          <p>联合国的其他成员国从未正式承认</p>
        </div>
        <div class="card card3">
          <h5>北海公园</h5>
          <p>联合国的其他成员国从未正式承认</p>
        </div>
        <div class="card card4">
          <h5>颐和园</h5>
          <p>联合国的其他成员国从未正式承认</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import {logout} from "@/net/index.js";
import router from "@/router/index.js";

export default {
  name: 'TravelLanding',
  methods:{
    logout(){
      logout(()=>{
        router.push('/')
      })
    }
}}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,200;1,100&display=swap');

* {
  margin: 0;
  padding: 0;
  font-family: 'Poppins', sans-serif;
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

.navbar {
  height: 12%;
  display: flex;
  align-items: center;
}

.logo {
  width: 50px;
  cursor: pointer;
}

.menu-icon {
  width: 30px;
  cursor: pointer;
  margin-left: 40px;
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
  background-image: url(../assets/pic-1.png);
}

.card2 {
  background-image: url(../assets/pic-2.png);
}

.card3 {
  background-image: url(../assets/pic-3.png);
}

.card4 {
  background-image: url(../assets/pic-4.png);
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
