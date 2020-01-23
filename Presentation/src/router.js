import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

const Home = () => import(`./views/Home.vue`);
const NewGroup = () => import(`./views/NewGroup.vue`);
const Help = () => import(`./views/Help.vue`);


const router = new Router({
    mode: 'history', // uris without hashes #, see https://router.vuejs.org/guide/essentials/history-mode.html#html5-history-mode
    routes: [
        { path: '/', component: Home },
        { path: '/newgroup', component: NewGroup },
        { path: '/help', component: Help },
        // otherwise redirect to home
        { path: '*', redirect: '/' }
    ]
});

export default router;