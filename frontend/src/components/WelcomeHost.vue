<template>
  <div>
    <header>
      <a href="#" class="brand">Prometheus</a>
      <div class="menu-btn"></div>
      <div class="navigation">
        <div class="navigation-items">
          <a href="#">主页</a>
          <a href="/TravelLanding">景点预览</a>
          <a href="/Diary">日记管理</a>
          <a href="/InnerNavigate">内部导航</a>
          <a @click="logout">退出登录</a>
        </div>
      </div>
    </header>
    <section class="home">
      <video class="video-slide active" src="../assets/demo02.mp4" autoplay muted loop></video>
      <video class="video-slide" src="../assets/demo01.mp4" autoplay muted loop></video>

      <div class="content active">
        <h1>朝.<br><span>游碧海而暮苍梧</span></h1>
        <p>
          古人之观于天地、山川、草木、虫鱼、鸟兽，往往有得，以其求思之深而无不在也。夫夷以近，则游者众；险以远，则至者少。而世之奇伟、瑰怪，非常之观，常在于险远，而人之所罕至焉，故非有志者不能至也。</p>
        <a href="/FoodRecommend">探索无限</a>
      </div>

      <div class="content">
        <h1>平生.<br><span>只负云小梦，一步能登天下山</span></h1>
        <p>世界是客栈，死亡是旅行的终点。</p>
        <a href="#">未来可期</a>
      </div>

      <div class="media-icons">
        <a href="#"><i class="fab fa-facebook-f"></i></a>
        <a href="#"><i class="fab fa-instagram"></i></a>
        <a href="#"><i class="fab fa-twitter"></i></a>
      </div>

      <div class="slider-navigation">
        <div class="nav-btn active"></div>
        <div class="nav-btn"></div>
      </div>
    </section>
  </div>
</template>

<script>
import {logout} from "@/net/index.js";
import router from "@/router/index.js";
export default {
  name: 'WelcomeHost',
  methods:{
    logout(){
      logout(()=>{localStorage.removeItem("username")
        router.push('/')
      })
    }
  },
  mounted() {
    const menuBtn = document.querySelector(".menu-btn");
    const navigation = document.querySelector(".navigation");
    const btns = document.querySelectorAll(".nav-btn");
    const slides = document.querySelectorAll(".video-slide");
    const contents = document.querySelectorAll(".content");

    menuBtn.addEventListener("click", () => {
      menuBtn.classList.toggle("active");
      navigation.classList.toggle("active");
    });

    const sliderNav = function (manual) {
      btns.forEach((btn) => {
        btn.classList.remove("active");
      });
      slides.forEach((slide) => {
        slide.classList.remove("active");
      });
      contents.forEach((content) => {
        content.classList.remove("active");
      });

      btns[manual].classList.add("active");
      slides[manual].classList.add("active");
      contents[manual].classList.add("active");
    }

    btns.forEach((btn, i) => {
      btn.addEventListener("click", () => {
        sliderNav(i);
      });
    });
  }
}
</script>

<style scoped>
* {
  margin: 0px;
  padding: 0px;
  box-sizing: border-box;
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


.home {
  position: relative;
  width: 100%;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  flex-direction: column;
  background-color: #2696E9;
}

.home video {
  z-index: 000;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.home:before {
  z-index: 777;
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  background: rgba(3, 96, 251, 0.3);
  width: 100%;
  height: 100%;
}

.home .content {
  z-index: 888;
  color: #fff;
  width: 70%;
  margin-top: 50px;
  display: none;
}

.home .content.active {
  display: block;
}

.home .content h1 {
  font-size: 4rem;
  font-weight: 900;
  text-transform: uppercase;
  letter-spacing: 5px;
  line-height: 75px;
  margin-bottom: 40px;
}

.home .content h1 span {
  font-size: 1.2em;
  font-weight: 600;
}

.home .content p {
  margin-bottom: 65px;
}

.home .content a {
  background: #fff;
  padding: 15px 35px;
  color: #1680ac;
  font-size: 1.1em;
  font-weight: 500;
  text-decoration: none;
  border-radius: 2px;
}

.home .media-icons {
  z-index: 888;
  position: absolute;
  display: flex;
  flex-direction: column;
  right: 30px;
  transition: 0.5s ease;
  justify-content: space-between;
  gap: 20px
}

.home .media-icons a {
  color: #fff;
  font-size: 1.6em;
  transition: 0.3s ease;
}

.home .media-icons a:hover {
  transform: scale(1.2);
}


.slider-navigation {
  z-index: 888;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  transform: translateY(80px);
  margin-bottom: 12px;
  gap: 20px

}

.slider-navigation .nav-btn {
  width: 12px;
  height: 12px;
  background: #fff;
  border-radius: 50%;
  cursor: pointer;
  box-shadow: 0 0 2px rgba(255, 255, 255, 0.5);
}

.slider-navigation .nav-btn.active {
  background: #2696E9;
}

.slider-navigation .nav-btn:hover {
  transform: scale(1.2)
}


.video-slide {
  position: absolute;
  width: 100%;
  clip-path: circle(2.9% at 0 50%);
}

.video-slide.active {
  clip-path: circle(150.0% at 0 50%);
  transition: 0.5s ease;
}


@media (max-width: 1040px) {
  header {
    padding: 12px 20px;
  }

  section {
    padding: 100px 20px;
  }

  .home .media-icons {
    right: 15px;

  }

  header .navigation {
    display: none;
  }

  header .navigation.active {
    position: fixed;
    width: 100%;
    height: 100vh;
    top: 0;
    left: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    background: rgba(1, 1, 1, 0.5);
  }

  header .navigation .navigation-items a::before {
    background-color: rgb(0, 0, 0);
    height: 5px;
  }


  header .navigation.active .navigation-items {
    background: #fff;
    width: 600px;
    max-width: 600px;
    margin: 20px;
    padding: 40px;
    display: flex;
    flex-direction: column;
    align-items: center;
    border-radius: 5px;
  }


  header .navigation .navigation-items a {
    color: #000;
    font-size: 1.2em;
    margin: 20px;
  }

  .menu-btn {
    background-size: 30px;
    background-position: center;
    width: 40px;
    height: 40px;
    cursor: pointer;
  }

  .menu-btn.active {
    z-index: 999;
    background-size: 25px;
    background-position: center;

  }
}
</style>
