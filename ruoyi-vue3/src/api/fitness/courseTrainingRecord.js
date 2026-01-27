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

// 查询训练记录
export function listTrainingRecordData(query) {
  return request({
    url: '/fitness/courseTrainingRecord/listTrainingRecordData',
    method: 'get',
    params: query
  })
}
