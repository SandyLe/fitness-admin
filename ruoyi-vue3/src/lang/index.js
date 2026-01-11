import { createI18n } from 'vue-i18n'
import zhCN from './zh-cn.json'   
import enUS from './en.json'      
import idID from './id-ID.json'  

// 注意：这里只包含你自己的业务翻译，不包含 Element Plus 的！
const messages = {
  'zh-CN': zhCN,
  'en-US': enUS,
  'id-ID': idID
}

const i18n = createI18n({
  legacy: false,            
  globalInjection: true,    
  locale: localStorage.getItem('language') || 'zh-CN',
  messages
})

export default i18n