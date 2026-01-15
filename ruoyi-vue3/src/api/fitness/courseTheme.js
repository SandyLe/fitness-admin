import request from '@/utils/request'

// 查询课程列表
export function listCourseTheme(query) {
  return request({
    url: '/fitness/courseTheme/list',
    method: 'get',
    params: query
  })
}

// 查询课程详细
export function getCourseTheme(id) {
  return request({
    url: '/fitness/courseTheme/' + id,
    method: 'get'
  })
}

// 新增课程
export function addCourseTheme(data) {
  return request({
    url: '/fitness/courseTheme',
    method: 'post',
    data: data
  })
}

// 修改课程
export function updateCourseTheme(data) {
  return request({
    url: '/fitness/courseTheme',
    method: 'put',
    data: data
  })
}

// 删除课程
export function delCourseTheme(id) {
  return request({
    url: '/fitness/courseTheme/' + id,
    method: 'delete'
  })
}
