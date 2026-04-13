import { createWebHistory, createRouter } from 'vue-router'
/* Layout */
import Layout from '@/layout'

/**
 * Note: Route configuration items
 *
 * hidden: true                     // When set to true, this route will not appear in the sidebar, such as 401, login pages, or some edit pages like /edit/1
 * alwaysShow: true                 // When you have more than 1 child route declared under a route, it will automatically become a nested mode -- such as component pages
 *                                  // When there is only one, that child route will be displayed as the root route in the sidebar -- such as guide pages
 *                                  // If you want to display your root route regardless of the number of children routes declared
 *                                  // You can set alwaysShow: true, so it will ignore the previously defined rules and always display the root route
 * redirect: noRedirect             // When set to noRedirect, this route cannot be clicked in breadcrumb navigation
 * name:'router-name'               // Set the name of the route, must be filled in otherwise problems will occur when using <keep-alive>
 * query: '{"id": 1, "name": "ry"}' // Default parameters passed when accessing the route
 * roles: ['admin', 'common']       // Role permissions for accessing the route
 * permissions: ['a:a:a', 'b:b:b']  // Menu permissions for accessing the route
 * meta : {
    noCache: true                   // If set to true, it will not be cached by <keep-alive> (default false)
    title: 'title'                  // Set the name displayed in the sidebar and breadcrumbs for this route
    icon: 'svg-name'                // Set the icon for this route, corresponding path src/assets/icons/svg
    breadcrumb: false               // If set to false, it will not be displayed in the breadcrumb
    activeMenu: '/system/user'      // When this property is set on a route, the corresponding sidebar will be highlighted.
  }
 */

// Public routes
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index.vue')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login'),
    hidden: true
  },
  {
    path: '/register',
    component: () => import('@/views/register'),
    hidden: true
  },
  {
    path: "/:pathMatch(.*)*",
    component: () => import('@/views/error/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error/401'),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: '/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/index'),
        name: 'Home',
        meta: { title: (t) => t('home.index'), icon: 'dashboard', affix: true }
      }
    ]
  },
  // {
  //   path: '/online',
  //   component: Layout,
  //   redirect: '/online/index',
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/online/index.vue'),
  //       name: 'OnlineConsult',
  //       meta: { title: '在线咨询', icon: 'online', affix: true }
  //     }
  //   ]
  // },
  // {
  //   path: '/history',
  //   component: Layout,
  //   redirect: '/history/index',
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/history/index.vue'),
  //       name: 'HistoryChat',
  //       meta: { title: '历史对话', icon: 'message', affix: true }
  //     }
  //   ]
  // },
  // {
  //   path: '/calendar',
  //   component: Layout,
  //   redirect: '/calendar/index',
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/calendar/index.vue'),
  //       name: 'ElectronicCalendar',
  //       meta: { title: '电子日历', icon: 'log', affix: true }
  //     }
  //   ]
  // },
  // {
  //   path: '/knowledge',
  //   component: Layout,
  //   redirect: '/knowledge/index',
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/knowledge/index.vue'),
  //       name: 'Knowledge',
  //       meta: { title: '知识库', icon: 'log', affix: true }
  //     }
  //   ]
  // },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile/:activeTab?',
        component: () => import('@/views/system/user/profile/index'),
        name: 'Profile',
        meta: { title: 'Personal Center', icon: 'user' }
      }
    ]
  }
  // ,{
  //   path: '/feedback',
  //   component: Layout,
  //   hidden: true,
  //   redirect: '/feedback/index',
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/feedback/index.vue'),
  //       name: 'Feedback',
  //       meta: { title: 'User Feedback', icon: 'log', affix: true }
  //     }
  //   ]
  // }
]

// Dynamic routes, dynamically loaded based on user permissions
export const dynamicRoutes = [
  {
    path: '/system/user-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:user:edit'],
    children: [
      {
        path: 'role/:userId(\\d+)',
        component: () => import('@/views/system/user/authRole'),
        name: 'AuthRole',
        meta: { title: 'Assign Roles', activeMenu: '/system/user' }
      }
    ]
  },
  {
    path: '/system/role-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:role:edit'],
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/views/system/role/authUser'),
        name: 'AuthUser',
        meta: { title: 'Assign Users', activeMenu: '/system/role' }
      }
    ]
  },
  {
    path: '/system/dict-data',
    component: Layout,
    hidden: true,
    permissions: ['system:dict:list'],
    children: [
      {
        path: 'index/:dictId(\\d+)',
        component: () => import('@/views/system/dict/data'),
        name: 'Data',
        meta: { title: 'Dictionary Data', activeMenu: '/system/dict' }
      }
    ]
  },
  {
    path: '/monitor/job-log',
    component: Layout,
    hidden: true,
    permissions: ['monitor:job:list'],
    children: [
      {
        path: 'index/:jobId(\\d+)',
        component: () => import('@/views/monitor/job/log'),
        name: 'JobLog',
        meta: { title: 'Job Logs', activeMenu: '/monitor/job' }
      }
    ]
  },
  {
    path: '/tool/gen-edit',
    component: Layout,
    hidden: true,
    permissions: ['tool:gen:edit'],
    children: [
      {
        path: 'index/:tableId(\\d+)',
        component: () => import('@/views/tool/gen/editTable'),
        name: 'GenEdit',
        meta: { title: 'Edit Generation Config', activeMenu: '/tool/gen' }
      }
    ]
  }
]

const { VITE_APP_ENV } = import.meta.env

const router = createRouter({
  history: createWebHistory(VITE_APP_ENV === 'production' ? '/enadmin/' : '/'),
  routes: constantRoutes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    }
    return { top: 0 }
  },
})

export default router
