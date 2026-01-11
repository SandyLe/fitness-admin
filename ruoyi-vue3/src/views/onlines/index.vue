<template>
  <div class="online">
    <div class="onlinetitle">
      <img src="@/assets/images/onlineLogo.png" alt="">
      <div class="title-con">
        <h3>{{agentName}}</h3>
        <p class="online-status">{{$t("online.status")}}</p>
      </div>
    </div>
    <div class="message-container"  ref="messaggListRef">

      <div class="message-list">
          <div v-for="(item, index) in historyMessages" :key="index" :class="
              item.type === 'USER' ? 'message user-message' : 'message bot-message'
            ">
             <i
              :class="
               item.type === 'USER'
                  ? 'user message-icon'
                  : 'robot message-icon'
              "
            >
             <span v-if="item.type === 'USER'">{{$t("online.user")}}</span>
             <span v-else>{{$t("online.ai")}}</span>
             </i>
             <div class="message-content">
              <span v-if="item.type === 'USER'" v-html="item.contents[0].text"></span>
              <span v-else v-html="marked.parse(item.text)"></span>
            </div>
          </div>
          <div
            v-for="(message, index) in messages"
            :key="index"
            :class="
              message.isUser ? 'message user-message' : 'message bot-message'
            "
          >
            <!-- 会话图标 -->
            <i
              :class="
                message.isUser
                  ? 'user message-icon'
                  : 'robot message-icon'
              "
            >
             <span v-if="message.isUser">{{$t("online.user")}}</span>
             <span v-else>{{$t("online.ai")}}</span>
          </i>
            <!-- 会话内容 -->
            <div class="message-content">
              <!-- <span v-html="message.content"></span> -->
               <span v-if="message.isUser" v-html="message.content"></span>
              <span v-else v-html="marked.parse(message.content)"></span>
               <p class="tip">{{ $formatDate(message.time, '{h}:{i}') }}</p>
              <!-- loading -->
              <span
                class="loading-dots"
                v-if="message.isThinking || message.isTyping"
              >
                <span class="dot"></span>
                <span class="dot"></span>
              </span>
            </div>
           
          </div>
    </div>
    </div>
    <div class="input-container">
      <div class="input-box">
        <el-input
        type="textarea"
        v-model="inputMessage"
        :placeholder="$t('online.placeholder')"
        @keyup.enter="sendMessage"
      ></el-input>
      <el-button type="primary" icon="Promotion" circle  @click="sendMessage" :disabled="isSending" />
      </div>
      <p>
        {{$t("online.tip")}}
      </p>
    </div>
  </div>
</template>
<script setup>
import { marked } from 'marked';
import { onMounted, ref, watch } from 'vue'
import axios from 'axios'
import { getToken } from '@/utils/auth'
import {historyDetail} from '@/api/ai'
import {getAgent} from "@/api/ai/agent"
import { getLanguage } from '@/utils/language'

import { v4 as uuidv4 } from 'uuid'
const controller = new AbortController();
const historyMessages = ref([])
// 自定义首字节超时：15秒内没收到任何数据就取消
const timeoutId = setTimeout(() => {
  controller.abort();
}, 15000);
// 获取环境配置的API基础地址
const apiBaseUrl = import.meta.env.VITE_APP_BASE_API 
const agentName = ref('')
const messaggListRef = ref()
const isSending = ref(false)
const uuid = ref()
const inputMessage = ref('')
const messages = ref([])
const { proxy } = getCurrentInstance()
onMounted(() => {
  getAgentName()
  // 移除 setInterval，改用手动滚动
  watch(messages, () => scrollToBottom(), { deep: true })
  if(proxy.$route.query.memoryId){
   getHistoryDetail(proxy.$route.query.memoryId)
  }else{
   initUUID()
   hello()
  }
 
})
const getAgentName = (agentId) => {
  agentId = proxy.$route.query.agentId||'2'
  getAgent(agentId).then(res => {
    console.log(res)
    if (res.code === 200) {
      document.title = res.data.agentName;
      agentName.value =  res.data.agentName
    }
  })
}
const scrollToBottom = () => {
  if (messaggListRef.value) {
    messaggListRef.value.scrollTop = messaggListRef.value.scrollHeight
    
  }
}
const getHistoryDetail = (memoryId) => {
  historyDetail(memoryId).then(res => {
    if (res.code === 200) {
      historyMessages.value = JSON.parse(res.data.content).filter(item => item.type === 'USER' || item.type === 'AI')
      // sendRequest('继续')
    }
    
  })
}
const hello = () => {
  sendRequest(proxy.$t('online.hello'))
}

const sendMessage = () => {
  if (inputMessage.value.trim()) {
    sendRequest(inputMessage.value.trim())
    inputMessage.value = ''
    
  }
}

const sendRequest = (message) => {
  isSending.value = true
  const userMsg = {
    isUser: true,
    content: message,
    isTyping: false,
    isThinking: false,
  }
  //第一条默认发送的用户消息”你好“不放入会话列表
  if(messages.value.length > 0){
    messages.value.push(userMsg)
  }


  // 添加机器人加载消息
  const botMsg = {
    isUser: false,
    content: '', // 增量填充
    isTyping: true, // 显示加载动画
    isThinking: false,
  }
  messages.value.push(botMsg)
  const lastMsg = messages.value[messages.value.length - 1]
  scrollToBottom()
axios
    .post(
      // apiBaseUrl+ '/ai/xiaozhi/chat' ,
      apiBaseUrl+ '/ai/chat/stream' ,
      { memoryId:  proxy.$route.query.memoryId|| uuid.value, message,agentId: proxy.$route.query.agentId||'2'},
      {
        headers: {
          'Authorization': `Bearer ${getToken()}`,
          'Accept-Language': getCurrentLanguageTag()
        },
        responseType: 'stream', // 必须为合法值 "text"
        onDownloadProgress: (e) => {
          const fullText = e.event.target.responseText // 累积的完整文本
          let newText = fullText.substring(lastMsg.content.length)
          lastMsg.content += newText //增量更新
          console.log(lastMsg)
          scrollToBottom() // 实时滚动
        },
      }
    )
    .then(() => {
      console.log('流结束')
      // 流结束后隐藏加载动画
      messages.value.at(-1).isTyping = false
      isSending.value = false
    })
    .catch((error) => {
      console.error('流式错误:', error)
      messages.value.at(-1).content = proxy.$t('online.requestFailed')
      messages.value.at(-1).isTyping = false
      isSending.value = false
    })
  // chat(
  //   { memoryId: uuid.value, message: message },
  //   {
  //      timeout: 0,
  //     responseType: 'text', // 浏览器中必须用 'text'，不是 'stream'
  //     onDownloadProgress: (progressEvent) => {
  //       clearTimeout(timeoutId); // 收到数据后清除超时
  //        const reader = progressEvent.target?.response;
  //         if (typeof reader === 'string') {
  //           const fullText = reader;
  //           const delta = fullText.substring(lastMsg.content.length);
  //           if (delta) {
  //             lastMsg.content += delta;
  //             scrollToBottom();
  //           }
  //         }
       
  //     }
  //   }
  // )
  //   .then(() => {
  //     // 流结束后隐藏加载动画
  //     messages.value.at(-1).isTyping = false
  //     isSending.value = false
  //   })
  //   .catch((error) => {
  //     console.error('流式错误:', error)
  //     messages.value.at(-1).content = '请求失败，请重试'
  //     messages.value.at(-1).isTyping = false
  //     isSending.value = false
  //   })
}

// 辅助函数：获取标准语言标签
function getCurrentLanguageTag() {
  // 优先使用用户设置（如果有）
  const userLang = getLanguage(); // 示例
  // 映射为标准格式
  const langMap = {
    'zh': 'zh-CN',
    'en': 'en-US',
    'fr': 'fr-FR',
    'es': 'es-ES'
  };
  return langMap[userLang] || userLang || navigator.language || 'zh-CN';
}

// 初始化 UUID
const initUUID = () => {
  // let storedUUID = localStorage.getItem('user_uuid')
  // if (!storedUUID) {
    // storedUUID = uuidToNumber(uuidv4())
    // localStorage.setItem('user_uuid', storedUUID)
  // }
  uuid.value = uuidToNumber(uuidv4())
}

const uuidToNumber = (uuid) => {
  let number = 0
  for (let i = 0; i < uuid.length && i < 6; i++) {
    const hexValue = uuid[i]
    number = number * 16 + (parseInt(hexValue, 16) || 0)
  }
  return number % 1000000
}

// 转换特殊字符
const convertStreamOutput = (output) => {
  return output
    .replace(/\n/g, '<br>')
    .replace(/\t/g, '&nbsp;&nbsp;&nbsp;&nbsp;')
    .replace(/&/g, '&amp;') // 新增转义，避免 HTML 注入
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
}

const newChat = () => {
  // 这里添加新会话的逻辑
  console.log('开始新会话')
  localStorage.removeItem('user_uuid')
  window.location.reload()
}

</script>
<style scoped lang="scss">
.online {
  height: calc(100vh - 85px);
  display: flex;
  flex-direction: column;
  .onlinetitle {
    width: 100%;
    height: 85px;
    background-color: #fff;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    padding-left: 20px;
    box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
   
    img {
      width: 60px;
      height: 60px;
      margin-right: 20px;
      border-radius: 50%;
    }
    .title-con {
      h3 {
        font-size: 16px;
        line-height: 16px;
        font-weight: bold;
        color: #303133;
      }
      p {
        font-size: 12px;
        line-height: 12px;
        font-weight: normal;
        color: #606266;
        &.online-status {
          &::before {
            content: '';
            display: inline-block;
            width: 8px;
            height: 8px;
            border-radius: 50%;
            background-color: #409eff;
            margin-right: 4px;
          }
        }
      }
    }
  }
  .message-container {
    flex: 1;
    background-color: #F7F9FC;
    padding: 20px;
    box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
    overflow: auto;
      display: flex;
      flex-direction: column;
      .message-list {
        display: flex;
        flex-direction: column;
      
      .message {
        margin-bottom: 15px;
       
        border-radius: 4px;
        display: flex;
       flex: 1;
        .message-content {
          flex:1;
          position: relative;
          box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
          color: #000;
          .tip {
            width: 100px;
            position: absolute;
            bottom: -30px;
            font-size: 12px;
            line-height: 12px;
            padding: 0;
            font-weight: normal;
            color: #606266;
          }
        }
      }
      .user-message {
        max-width: 70%;
       
        align-self: flex-end;
        flex-direction: row-reverse;
        .message-content {
          background-color: #e1f5fe;
           padding: 10px;
           border-radius: 10px;
        }
        .tip {
          text-align: right;
          right: 5px;
        }
      }

      .bot-message {
        max-width: 100%;
        align-items: center;
        align-self: flex-start;
         .message-content {
          background-color: #fff;
           padding: 10px;
             border-radius: 10px;
        }
        .tip {
          left: 5px;
        }
      }

      .message-icon {
        margin: 0 10px;
        font-size: 1.2em;
        width: 30px;
        height: 30px;
        border-radius: 50%;
        color: #fff;
        text-align: center;
        font-size: 14px;
        line-height: 30px;
        font-weight: bold;
        font-style: normal;
      }
      .user {
        background-color: #2457d7;
       
      }
      .robot {
        width: 30px;
        height: 30px;
        border-radius: 50%;
        background-color: #5CBA57;
        // background: url('@/assets/images/onlineLogo.png') no-repeat center center;
        // background-size: cover; 
      }
      .loading-dots {
        padding-left: 5px;
      }

      .dot {
        display: inline-block;
        margin-left: 5px;
        width: 8px;
        height: 8px;
        background-color: #000000;
        border-radius: 50%;
        animation: pulse 1.2s infinite ease-in-out both;
      }

      .dot:nth-child(2) {
        animation-delay: -0.6s;
      }

      @keyframes pulse {
        0%,
        100% {
          transform: scale(0.6);
          opacity: 0.4;
        }

        50% {
          transform: scale(1);
          opacity: 1;
        }
      }
    }
  }
  .input-container {
    box-sizing: border-box;
    width: 100%;
    height: 120px;
    background-color: #fff;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 0 20px;
    box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
    margin-top: auto;
    .input-box {
      width: 100%;
      height: 80px;
      display: flex;
      justify-content: flex-start;
      align-items: center;
      background-color: #F1F4F8;
      color: #333;
      border-radius: 10px;
    }
    .el-textarea__inner{
      color: #333;
    }
    p{
      height: 12px;
      font-size: 12px;
      line-height: 12px;
      font-weight: normal;
      color: #606266;
      margin: 5px 0 0 0;
    }
    :deep(.el-textarea__inner) {
      width: 100%;
      height: 80px !important; 
      background-color: #F1F4F8;
      border: none;
      resize: none;
      box-shadow: none;
    }
    .el-button {
      position: absolute;
      right: 50px;
    }
  }
}

</style>