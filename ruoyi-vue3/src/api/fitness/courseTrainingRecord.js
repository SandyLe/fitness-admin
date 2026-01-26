import request from '@/utils/request'

// 查询课程列表
export function listCourseTrainingRecord(query) {
  return request({
    url: '/fitness/courseTrainingRecord/list',
    method: 'get',
    params: query
  })
}

// 查询课程详细
export function getCourseTrainingRecord(id) {
  return request({
    url: '/fitness/courseTrainingRecord/' + id,
    method: 'get'
  })
}

// 新增课程
export function addCourseTrainingRecord(data) {
  return request({
    url: '/fitness/courseTrainingRecord',
    method: 'post',
    data: data
  })
}

// 修改课程
export function updateCourseTrainingRecord(data) {
  return request({
    url: '/fitness/courseTrainingRecord',
    method: 'put',
    data: data
  })
}

// 删除课程
export function delCourseTrainingRecord(id) {
  return request({
    url: '/fitness/courseTrainingRecord/' + id,
    method: 'delete'
  })
}

// 查询课程动作指标列表
export function listCourseTrainingRecordActionIndicator(query) {
  return request({
    url: '/fitness/courseTrainingRecordActionIndicator/list',
    method: 'get',
    params: query
  })
}

// 查询课程动作指标详细
export function getCourseTrainingRecordActionIndicator(id) {
  return request({
    url: '/fitness/courseTrainingRecordActionIndicator/' + id,
    method: 'get'
  })
}

// 新增课程动作指标
export function addCourseTrainingRecordActionIndicator(data) {
  return request({
    url: '/fitness/courseTrainingRecordActionIndicator',
    method: 'post',
    data: data
  })
}

// 修改课程动作指标
export function updateCourseTrainingRecordActionIndicator(data) {
  return request({
    url: '/fitness/courseTrainingRecordActionIndicator',
    method: 'put',
    data: data
  })
}

// 删除课程动作指标
export function delCourseTrainingRecordActionIndicator(id) {
  return request({
    url: '/fitness/courseTrainingRecordActionIndicator/' + id,
    method: 'delete'
  })
}


// 查询课程动作评价列表
export function listCourseTrainingRecordActionComment(query) {
  return request({
    url: '/fitness/courseTrainingRecordActionComment/list',
    method: 'get',
    params: query
  })
}

// 查询课程动作评价详细
export function getCourseTrainingRecordActionComment(id) {
  return request({
    url: '/fitness/courseTrainingRecordActionComment/' + id,
    method: 'get'
  })
}

// 新增课程评价指标评价
export function addCourseTrainingRecordActionComment(data) {
  return request({
    url: '/fitness/courseTrainingRecordActionComment',
    method: 'post',
    data: data
  })
}

// 修改课程动作指标评价
export function updateCourseTrainingRecordActionComment(data) {
  return request({
    url: '/fitness/courseTrainingRecordActionComment',
    method: 'put',
    data: data
  })
}

// 删除课程动作指标评价
export function delCourseTrainingRecordActionComment(id) {
  return request({
    url: '/fitness/courseTrainingRecordActionComment/' + id,
    method: 'delete'
  })
}

// 查询课程动作要点列表
export function listCourseTrainingRecordActionPoints(query) {
  return request({
    url: '/fitness/courseTrainingRecordActionPoints/list',
    method: 'get',
    params: query
  })
}

// 查询课程动作要点详细
export function getCourseTrainingRecordActionPoints(id) {
  return request({
    url: '/fitness/courseTrainingRecordActionPoints/' + id,
    method: 'get'
  })
}

// 新增课程评价要点
export function addCourseTrainingRecordActionPoints(data) {
  return request({
    url: '/fitness/courseTrainingRecordActionPoints',
    method: 'post',
    data: data
  })
}

// 修改课程动作要点
export function updateCourseTrainingRecordActionPoints(data) {
  return request({
    url: '/fitness/courseTrainingRecordActionPoints',
    method: 'put',
    data: data
  })
}

// 删除课程动作要点
export function delCourseTrainingRecordActionPoints(id) {
  return request({
    url: '/fitness/courseTrainingRecordActionPoints/' + id,
    method: 'delete'
  })
}