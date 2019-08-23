import Vue from 'vue'
import Vuex from 'vuex'
import { stat } from 'fs';
Vue.use(Vuex)


export default new Vuex.Store({
    state:{
        showHeader:true,
        user:{
            id:window.localStorage.getItem('user') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).id,
        },
        keywords:'',
        currentPage:1,
        blogIndex:'0',
        pageSize:6
    },
    getters:{
        showHeader:state=>state.showHeader,
        user(state){
            return state.user
        },
        keywords:state=>state.keywords,
        currentPage:state=>state.currentPage,
        blogIndex:state=>state.blogIndex,
        pageSize:state=>state.pageSize
    },
    mutations:{
        updateShowHeader(state,newShow){
            if(newShow){
                
                state.showHeader=newShow;
            }else{
                state.showHeader=!state.showHeader;
            }
            
        },
        updateCurrentPage(state,newPage){
            state.currentPage = newPage;
            state.blogIndex = state.pageSize*(state.currentPage-1);
        },
        login(state,user){
            state.user=user;
            window.localStorage.setItem("user",JSON.stringify(user));
        },
        logout(){
            window.localStorage.removeItem('user');
        },
        setKeyWords(state,keywords){
            state.keywords = keywords;
        }
    },
})