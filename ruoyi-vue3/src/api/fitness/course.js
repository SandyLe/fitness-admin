import request from '@/utils/request'

// 查询课程列表
export function listCourse(query) {
  return request({
    url: '/fitness/course/list',
    method: 'get',
    params: query
  })
}

// 查询课程详细
export function getCourse(id) {
  return request({
    url: '/fitness/course/' + id,
    method: 'get'
  })
}

// 新增课程
export function addCourse(data) {
  return request({
    url: '/fitness/course',
    method: 'post',
    data: data
  })
}

// 修改课程
export function updateCourse(data) {
  return request({
    url: '/fitness/course',
    method: 'put',
    data: data
  })
}

// 删除课程
export function delCourse(id) {
  return request({
    url: '/fitness/course/' + id,
    method: 'delete'
  })
}

// 查询课程动作指标列表
export function listCourseActionIndicator(query) {
  return request({
    url: '/fitness/courseActionIndicator/list',
    method: 'get',
    params: query
  })
}

// 查询课程详细
export function getCourseActionIndicator(id) {
  return request({
    url: '/fitness/courseActionIndicator/' + id,
    method: 'get'
  })
}

// 新增课程
export function addCourseActionIndicator(data) {
  return request({
    url: '/fitness/courseActionIndicator',
    method: 'post',
    data: data
  })
}

// 修改课程
export function updateCourseActionIndicator(data) {
  return request({
    url: '/fitness/courseActionIndicator',
    method: 'put',
    data: data
  })
}

// 删除课程
export function delCourseActionIndicator(id) {
  return request({
    url: '/fitness/courseActionIndicator/' + id,
    method: 'delete'
  })
}

