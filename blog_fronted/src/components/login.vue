<template>
    <el-form label-position="left" class="login-container" :rules="rules" v-loading="loading">
        <h3>个人博客</h3>
        <el-form-item prop="account">
            <el-input type="text" placeholder="账号" v-model="loginForm.username"></el-input>
        </el-form-item>
        <el-form-item prop="checkPass">
            <el-input type="password" placeholder="密码" v-model="loginForm.password"></el-input>
        </el-form-item>
        <el-form-item>
            <el-checkbox v-model="checked">记住密码</el-checkbox>
        </el-form-item>
        <el-form-item style="width:100%">
            <el-button type="primary" style="width:100%" @click="submitForm">登录</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
export default {
    data(){
        return {
            rules:{
                account:[{required:true,message:"请输入用户名",trigger:"blur"}],
                checkPass:[{required:true,message:"请输入密码",trriger:"blur"}]
            },
            loginForm:{
                username:'admin',
                password:'123'
            },
            checked:true,
            loading:false
        }
    },
    methods:{
        submitForm(){
            var _this=this;
            this.loading=true;
            this.$http.post('/api/login',{
                username:this.loginForm.username,
                password:this.loginForm.password
            },
            {
                withCredentials: true,
            }
            
            ).then(resp=>{
                _this.loading=false;
                console.log("resp",resp);
                if(resp&&resp.status==200){
                    var data=resp.data;
                    //console.log(data);
                    _this.$store.commit('login',data.user);
                    _this.$router.replace({path:'/index'});
                }
            })
        }
    },

}
</script>
<style scoped>
.login-container{
    margin:180px auto;
    border-radius: 15px;
    width:350px;
    padding: 30px 20px 15px 20px;
    background: #fff;
    background-clip: padding-box;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;

}

</style>