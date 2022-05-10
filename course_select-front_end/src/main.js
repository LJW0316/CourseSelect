import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'

import '@/assets/css/global.css'

createApp(App).use(store).use(router).use(ElementPlus,{locale: zhCn, }).mount('#app')

//路由拦截，防止未登录用户进入主界面
router.beforeEach((to, from, next) => {
    if (to.meta.requireAuth) {
        let userJson = sessionStorage.getItem("user");
        if (userJson) {
            next();
        } else {
            next({
                path: '/login',
                query: {redirect: to.fullPath}
            })
        }
    } else {
        next();
    }
})