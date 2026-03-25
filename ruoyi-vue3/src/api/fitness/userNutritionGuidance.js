import request from '@/utils/request'

// 查询课程列表
export function listUserNutritionGuidance(query) {
  return request({
    url: '/fitness/userNutritionGuidance/list',
    method: 'get',
    params: query
  })
}

// 查询课程详细
export function getUserNutritionGuidance(id) {
  return request({
    url: '/fitness/userNutritionGuidance/' + id,
    method: 'get'
  })
}

// 新增课程
export function addUserNutritionGuidance(data) {
  return request({
    url: '/fitness/userNutritionGuidance',
    method: 'post',
    data: data
  })
}

// 修改课程
export function updateUserNutritionGuidance(data) {
  return request({
    url: '/fitness/userNutritionGuidance',
    method: 'put',
    data: data
  })
}

// 删除课程
export function delUserNutritionGuidance(id) {
  return request({
    url: '/fitness/userNutritionGuidance/' + id,
    method: 'delete'
  })
}

