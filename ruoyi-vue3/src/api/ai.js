import request from '@/utils/request'
export function upload(data) {
  return request({
    url: '/ai/documents/upload',
    headers: {
      isToken: true
    },
    method: 'post',
    data: data
  })
}

// 智能体聊天
export function chat(data, config={}) {
  return request({
    url: '/ai/xiaozhi/chat',
    headers: {
      isToken: true
    },
    method: 'post',
    data: data,
    stream: true,
    ...config
  })
}
// 历史对话列表
export function historyList(params) {
  return request({
    url: '/ai/chat/history/list',
    headers: {
      isToken: true
    },
    method: 'get',
    params: params
  })
}
// 历史对话详情
export function historyDetail(memoryId) {
  return request({
    url: '/ai/chat/history/' + memoryId,
    headers: {
      isToken: true
    },
    method: 'get'
  })
}
// 删除日历事件
export function delCalendarEvent(eventId) {
  return request({
    url: '/ai/calendarevent/' + eventId,
    method: 'delete'
  })
}
// 日历事件列表
export function calendarEventList(params) {
  return request({
    url: '/ai/calendarevent/list',
    headers: {
      isToken: true
    },
    method: 'get',
    params: params
  })
}

// 日历事件保存
export function savecalendarevent(data) {
  return request({
    url: '/ai/calendarevent',
    headers: {
      isToken: true
    },
    method: 'post',
    data: data
  })
}
