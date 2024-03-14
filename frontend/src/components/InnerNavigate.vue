<template>
  <div class="blurred-wrapper">
    <header class="main-header container">
      <a href="index.html" class="logo">Prometheus</a>
      <a href="/WelcomeHost" class="btn sign-up">返回</a>
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
    </section>
  </div>
</template>

<script>

import {logout} from "@/net/index.js";
import router from "@/router/index.js";

export default {
  name: 'InnerNavigate',
  methods:{
    logout(){
      logout(()=>{
        router.push('/')
      })
    },
}}
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

.main-header {
  height: 12rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.container {
  max-width: 120rem;
  margin: 0 auto;
  padding: 0 1.5rem;
}

.logo {
  color: #fff;
  text-transform: uppercase;
  font-size: 2.5rem;
  font-weight: bold;
}

.btn {
  padding: 1rem 2.5rem;
  border: 2px solid rgba(255, 255, 255, 0.5);
  border-radius: 2.5rem;
  color: #fff;
}

.hero {
  display: flex;
  align-items: center;
  height: calc(100vh - 12rem);
}

.content-wrapper {
  max-width: 40rem;
  transform: translateY(-4rem);
}

.tagline {
  text-transform: uppercase;
  font-size: 1rem;
  font-weight: 100;
  margin-bottom: 1rem;
  color: rgb(128, 128, 128);
}

.title {
  font-size: 3rem;
  margin-bottom: 2rem;
  color: darkkhaki;
}

.message {
  font-size: 1.4rem;
  margin-bottom: 3rem;
  color: grey;
}

.cta {
  background-color: #000;
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
