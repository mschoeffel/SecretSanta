import Vue from 'vue'
import Vuex from 'vuex'
import api from './components/backend-api'

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        darktheme: false
    },
    mutations: {
        changedarktheme(state, newdarktheme){
            state.darktheme = newdarktheme;
        }
    },
    getters: {
        getdarktheme: state => state.darktheme
    }
})