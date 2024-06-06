import {createRouter, createWebHistory} from "vue-router";
import {unauthorized} from "@/net/index.js";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'welcome-',
            component: () => import('@/views/WelcomeView.vue'),
            children: [
                {
                    path: '',
                    name: 'welcome-login',
                    component: () => import('@/views/welcome/LoginPage.vue')
                },
                {
                    path: 'register',
                    name: 'welcome-register',
                    component: () => import('@/views/welcome/RegisterPage.vue')
                }
            ]
        },
        {
            path: '/index',
            name: 'index',
            component: () => import('@/views/IndexView.vue')
        },
        {
            path: '/InnerNavigate',
            name: 'inner-navigate',
            component: () => import('@/components/InnerNavigate.vue')
        },
        {
            path: '/TravelLanding',
            name: 'travel-landing',
            component: () => import('@/components/TravelLanding.vue')
        },
        {
            path: '/WelcomeHost',
            name: 'welcome-host',
            component: () => import('@/components/WelcomeHost.vue')
        },
        {
            path: '/NodeRelative',
            name: 'Node-Relative',
            component: () => import('@/components/NodeRelative.vue')
        },{
            path: '/FoodRecommend',
            name: 'Food',
            component: () => import('@/components/FoodRecommend.vue')
        }
    ]
})

router.beforeEach((to, from, next) => {
    const isUnauthorized = unauthorized()
    if (to.name.startsWith(('welcome-') && !isUnauthorized)) {
        next('/index')
    } else if (to.fullPath.startsWith('/index') && isUnauthorized) {
        next('/')
    } else {
        next()
    }
})
export default router