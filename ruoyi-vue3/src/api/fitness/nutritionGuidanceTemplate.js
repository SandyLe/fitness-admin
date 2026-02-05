import request from '@/utils/request'

// 查询课程列表
export function listNutritionGuidanceTemplate(query) {
  return request({
    url: '/fitness/nutritionGuidanceTemplate/list',
    method: 'get',
    params: query
  })
}

// 查询课程详细
export function getNutritionGuidanceTemplate(id) {
  return request({
    url: '/fitness/nutritionGuidanceTemplate/' + id,
    method: 'get'
  })
}

// 新增课程
export function addNutritionGuidanceTemplate(data) {
  return request({
    url: '/fitness/nutritionGuidanceTemplate',
    method: 'post',
    data: data
  })
}

// 修改课程
export function updateNutritionGuidanceTemplate(data) {
  return request({
    url: '/fitness/nutritionGuidanceTemplate',
    method: 'put',
    data: data
  })
}

// 删除课程
export function delNutritionGuidanceTemplate(id) {
  return request({
    url: '/fitness/nutritionGuidanceTemplate/' + id,
    method: 'delete'
  })
}

