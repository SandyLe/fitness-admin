import router from './router'
import { ElMessage } from 'element-plus'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken } from '@/utils/auth'
import { isHttp, isPathMatch } from '@/utils/validate'
import { isRelogin } from '@/utils/request'
import useUserStore from '@/store/modules/user'
import useSettingsStore from '@/store/modules/settings'
import usePermissionStore from '@/store/modules/permission'
import { calendarEventList } from '@/api/ai.js'
import { formatDate } from '@/utils/index.js'
import { ElNotification } from 'element-plus'
NProgress.configure({ showSpinner: false })

const whiteList = ['/login', '/register']

const isWhiteList = (path) => {
  return whiteList.some(pattern => isPathMatch(pattern, path))
}

router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    to.meta.title && useSettingsStore().setTitle(to.meta.title)
    /* has token*/
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else if (isWhiteList(to.path)) {
      next()
    } else {
      if (useUserStore().roles.length === 0) {
        isRelogin.show = true
        // 判断当前用户是否已拉取完user_info信息
        useUserStore().getInfo().then(() => {
          isRelogin.show = false
          usePermissionStore().generateRoutes().then(accessRoutes => {
            // 根据roles权限生成可访问的路由表
            accessRoutes.forEach(route => {
              if (!isHttp(route.path)) {
                router.addRoute(route) // 动态添加可访问路由表
              }
            })
          
            next({ ...to, replace: true }) // hack方法 确保addRoutes已完成
          })
        }).catch(err => {
          useUserStore().logOut().then(() => {
            ElMessage.error(err)
            next({ path: '/' })
          })
        })
      } else {
         // 初始化日历事件列表
    calendarEventList({params:{curDate:formatDate(new Date(),'{y}-{m}-{d}')}}).then(res => {
      if (res.code === 200) {
      //  console.log(res.rows)
        res.rows.forEach(item => {
          const now = new Date().getTime();
          const diff = Math.abs(now - new Date(item.reminderTime).getTime());
          const tenMinutes = 10 * 60 * 1000; // 十分钟毫秒数
          if (item.isReminder == 1 && diff <= tenMinutes) {      
            const remindedKey = 'reminded_' + item.eventId;
            if (!localStorage.getItem(remindedKey)) {
              ElNotification({
                title: '预约提醒',
                message: h('i', { style: 'color: #2b6ef6' }, '您有一个预约，请确认'),
                onClose: () => {
                  localStorage.setItem(remindedKey, 'true');
                }
              });
            }
          }
          item.reminderTime = new Date(item.reminderTime)
        })
      }
    })
        next()
      }
    }
  } else {
    // 没有token
    if (isWhiteList(to.path)) {
      // 在免登录白名单，直接进入
      next()
    } else {
      next(`/login?redirect=${to.fullPath}`) // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
