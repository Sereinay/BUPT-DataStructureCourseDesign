<script setup>

</script>

<template>
  <div style="width: 100vw;height: 100vh;overflow: hidden;display: flex">
    <div style="flex: 1;background-color: black">
      <el-image style="width: 100%;height: 100%" fit="cover"
                src="https://pic2.zhimg.com/v2-696b347aa5b02a943706d5de13dc6ec1_r.jpg"/>
    </div>
    <div class="welcome-title">
      <div style="font-size: 30px;font-weight: bold">“因为山就在那里。”</div>
      <div style="margin-top: 10px;margin-left: 60px"> ————George Herbert Leigh Mallory</div>
      <div style="margin-top: 5px"></div>
    </div>
    <div class="right-card">
      <router-view v-slot="{ Component }">
        <transition name="el-fade-in-linear" mode="out-in">
          <component :is="Component"/>
        </transition>
      </router-view>
    </div>

  </div>
</template>

<style scoped>
.right-card {
  width: 400px;
  z-index: 1;
  background-color: aliceblue;
}

.welcome-title {
  position: absolute;
  bottom: 30px;
  left: 30px;
  color: #d5ffe8;
}
</style>