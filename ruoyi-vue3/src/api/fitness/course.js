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

// 查询课程动作指标详细
export function getCourseActionIndicator(id) {
  return request({
    url: '/fitness/courseActionIndicator/' + id,
    method: 'get'
  })
}

// 新增课程动作指标
export function addCourseActionIndicator(data) {
  return request({
    url: '/fitness/courseActionIndicator',
    method: 'post',
    data: data
  })
}

// 修改课程动作指标
export function updateCourseActionIndicator(data) {
  return request({
    url: '/fitness/courseActionIndicator',
    method: 'put',
    data: data
  })
}

// 删除课程动作指标
export function delCourseActionIndicator(id) {
  return request({
    url: '/fitness/courseActionIndicator/' + id,
    method: 'delete'
  })
}


// 查询课程动作评价列表
export function listCourseActionComment(query) {
  return request({
    url: '/fitness/courseActionComment/list',
    method: 'get',
    params: query
  })
}

// 查询课程动作评价详细
export function getCourseActionComment(id) {
  return request({
    url: '/fitness/courseActionComment/' + id,
    method: 'get'
  })
}

// 新增课程评价指标评价
export function addCourseActionComment(data) {
  return request({
    url: '/fitness/courseActionComment',
    method: 'post',
    data: data
  })
}

// 修改课程动作指标评价
export function updateCourseActionComment(data) {
  return request({
    url: '/fitness/courseActionComment',
    method: 'put',
    data: data
  })
}

// 删除课程动作指标评价
export function delCourseActionComment(id) {
  return request({
    url: '/fitness/courseActionComment/' + id,
    method: 'delete'
  })
}

// 查询课程动作要点列表
export function listCourseActionPoints(query) {
  return request({
    url: '/fitness/courseActionPoints/list',
    method: 'get',
    params: query
  })
}

// 查询课程动作要点详细
export function getCourseActionPoints(id) {
  return request({
    url: '/fitness/courseActionPoints/' + id,
    method: 'get'
  })
}

// 新增课程评价要点
export function addCourseActionPoints(data) {
  return request({
    url: '/fitness/courseActionPoints',
    method: 'post',
    data: data
  })
}

// 修改课程动作要点
export function updateCourseActionPoints(data) {
  return request({
    url: '/fitness/courseActionPoints',
    method: 'put',
    data: data
  })
}

// 删除课程动作要点
export function delCourseActionPoints(id) {
  return request({
    url: '/fitness/courseActionPoints/' + id,
    method: 'delete'
  })
}