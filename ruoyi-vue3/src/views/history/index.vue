<template>
  <div class="app-container home">
    <h2>{{$t('home.historyConsult')}}</h2>
    <p>{{$t('home.historyConsultDesc2')}}</p>
    <el-row :gutter="40" style="margin-top: 40px;">
      <el-col :sm="24" :lg="24" >
         <ul class="history-list">
            <li v-for="(item, index) in list" :key="index">
              <div class="history-item">
                <h4 @click="handleClick(item.agentId,item.memoryId)">{{item.sessionTitle}}</h4>
                <span> {{item.updateTime}}</span>
              
              </div>
              <el-tag class="tag" type="info" @click="handleClick(item.agentId,item.memoryId)">
               {{ item.messageCount }} {{$t('common.messageDesc')}}
              </el-tag>
            </li>
            
          </ul>
      </el-col>
      </el-row>
      </div>
</template>

<script setup name="Index">
import { historyList } from '@/api/ai'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
let router = useRouter()

let list = ref([]);

const init = () => {
  historyList().then(res => {
    list.value = res.rows
  })
}

onMounted(() => {
  init()
})



// 点击历史对话
const handleClick = (agentId,memoryId) => {
  router.push({
        path: '/online',
        query: {
          agentId: agentId,
          memoryId: memoryId
        }
      })
}
</script>

<style scoped lang="scss">
.home {
  
  h2{
    font-size: 26px;
    font-weight: bold;
    color: #1B1F34;
  }
  p{
    font-size: 16px;
    color: #565E6E;
  }
  .history-list{
      padding: 0;
      li{
        box-shadow: var(--el-box-shadow-light);
        animation: slideIn 0.3s ease-in-out;
        width: 100%;
        padding: 10px;
        border-radius: 5px;
        background-color: #fff;
        list-style: none;
        margin-bottom: 10px;
        padding: 0 20px;
        box-sizing: border-box;
        text-align: left;
        min-height: 90px;
        cursor: pointer;
        &:hover{
          background-color: #f5f5f5;
        }
        .history-item{
          display: flex;
          justify-content: space-between;
          align-items: center;
          h4{
            font-size: 14px;
            font-weight: bold;
            color: #000;
          }
          span{
            font-size: 12px;
            color: #505969;
          }
        }
       
        span.tag{
          font-size: 12px;
        }
      }
    }
  }

</style>

