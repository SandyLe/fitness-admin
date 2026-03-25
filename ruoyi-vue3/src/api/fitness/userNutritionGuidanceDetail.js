import request from '@/utils/request'

// 查询课程列表
export function listUserNutritionGuidanceDetail(query) {
  return request({
    url: '/fitness/userNutritionGuidanceDetail/list',
    method: 'get',
    params: query
  })
}

// 查询课程详细
export function getUserNutritionGuidanceDetail(id) {
  return request({
    url: '/fitness/userNutritionGuidanceDetail/' + id,
    method: 'get'
  })
}

// 新增课程
export function addUserNutritionGuidanceDetail(data) {
  return request({
    url: '/fitness/userNutritionGuidanceDetail',
    method: 'post',
    data: data
  })
}

// 修改课程
export function updateUserNutritionGuidanceDetail(data) {
  return request({
    url: '/fitness/userNutritionGuidanceDetail',
    method: 'put',
    data: data
  })
}

// 删除课程
export function delUserNutritionGuidanceDetail(id) {
  return request({
    url: '/fitness/userNutritionGuidanceDetail/' + id,
    method: 'delete'
  })
}

