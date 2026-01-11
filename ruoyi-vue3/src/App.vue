<template>
    <el-config-provider :locale="elLocale">
        <router-view />
    </el-config-provider>
</template>

<script setup>
import useSettingsStore from '@/store/modules/settings'
import { handleThemeStyle } from '@/utils/theme'
import { computed } from 'vue'
import { useI18n } from 'vue-i18n'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import en from 'element-plus/es/locale/lang/en'
import id from 'element-plus/es/locale/lang/id'
import { calendarEventList } from '@/api/ai'
import { formatDate } from '@/utils/index.js'
import { ElNotification } from 'element-plus'

const { locale } = useI18n()

const elLocale = computed(() => {
  switch (locale.value) {
    case 'en-US': return en
    case 'id-ID': return id
    default: return zhCn // 包括 'zh-CN'
  }
})
onMounted(() => {
  nextTick(() => {
    // 初始化主题样式
    handleThemeStyle(useSettingsStore().theme)
    // // 初始化日历事件列表
    // calendarEventList({params:{curDate:formatDate(new Date(),'{y}-{m}-{d}')}}).then(res => {
    //   if (res.code === 200) {
    //   //  console.log(res.rows)
    //     res.rows.forEach(item => {
    //       const now = new Date().getTime();
    //       const diff = Math.abs(now - new Date(item.reminderTime).getTime());
    //       const tenMinutes = 10 * 60 * 1000; // 十分钟毫秒数
    //       if (item.isReminder == 1 && diff <= tenMinutes) {      
    //         const remindedKey = 'reminded_' + item.eventId;
    //         if (!localStorage.getItem(remindedKey)) {
    //           ElNotification({
    //             title: '预约提醒',
    //             message: h('i', { style: 'color: #2b6ef6' }, '您有一个预约，请确认'),
    //             onClose: () => {
    //               localStorage.setItem(remindedKey, 'true');
    //             }
    //           });
    //         }
    //       }
    //       item.reminderTime = new Date(item.reminderTime)
    //     })
    //   }
    // })
  })
})
</script>
