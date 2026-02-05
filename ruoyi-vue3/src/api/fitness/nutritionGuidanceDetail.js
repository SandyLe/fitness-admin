import request from '@/utils/request'

// 查询课程列表
export function listNutritionGuidanceDetail(query) {
  return request({
    url: '/fitness/nutritionGuidanceDetail/list',
    method: 'get',
    params: query
  })
}

// 查询课程详细
export function getNutritionGuidanceDetail(id) {
  return request({
    url: '/fitness/nutritionGuidanceDetail/' + id,
    method: 'get'
  })
}

// 新增课程
export function addNutritionGuidanceDetail(data) {
  return request({
    url: '/fitness/nutritionGuidanceDetail',
    method: 'post',
    data: data
  })
}

// 修改课程
export function updateNutritionGuidanceDetail(data) {
  return request({
    url: '/fitness/nutritionGuidanceDetail',
    method: 'put',
    data: data
  })
}

// 删除课程
export function delNutritionGuidanceDetail(id) {
  return request({
    url: '/fitness/nutritionGuidanceDetail/' + id,
    method: 'delete'
  })
}

