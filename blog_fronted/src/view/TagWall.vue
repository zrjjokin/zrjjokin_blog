<template>
    <div class="tags-wall">
        <div class="title">标签墙</div>
        <div class="content">
            <el-tag 
                class="dot-tag"
                v-for="(value,key) in tagList"
                :key="key"
                type="success"
                effect="light"
                style="margin-left:8px;margin-top:5px;"
            >
                {{key}}&nbsp;&nbsp;&nbsp;&nbsp;[{{value}}]
            </el-tag>
        </div>
    </div>
</template>

<script>
export default {
    data(){
        return {
            tagList:{}
        }
    },
    created(){
        this.initData();
    },
    methods:{
        initData(){
            var _this = this;
            this.$http.get(
                '/api/tags'
            ).then(resp=>{
                console.log(resp.data);
                _this.tagList = resp.data;
            })
        }
    }

}
</script>

<style scoped>

    .tags-wall{
        margin-top: 40px;
        margin-left: 40px;
    }
    .dot-tag{
        display: inline-block;
        float: left;
        width: 100px;
        text-align: left;
    }
</style>