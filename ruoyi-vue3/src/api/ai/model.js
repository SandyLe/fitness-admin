import request from '@/utils/request'

// 查询AI模型配置列表
export function listModel(query) {
  return request({
    url: '/ai/model/list',
    method: 'get',
    params: query
  })
}

// 查询AI模型配置详细
export function getModel(id) {
  return request({
    url: '/ai/model/' + id,
    method: 'get'
  })
}

// 新增AI模型配置
export function addModel(data) {
  return request({
    url: '/ai/model',
    method: 'post',
    data: data
  })
}

// 修改AI模型配置
export function updateModel(data) {
  return request({
    url: '/ai/model',
    method: 'put',
    data: data
  })
}

// 删除AI模型配置
export function delModel(id) {
  return request({
    url: '/ai/model/' + id,
    method: 'delete'
  })
}
