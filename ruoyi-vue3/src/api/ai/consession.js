import request from '@/utils/request'

// 查询对话会话列表
export function listConsession(query) {
  return request({
    url: '/ai/consession/list',
    method: 'get',
    params: query
  })
}

// 查询对话会话详细
export function getConsession(id) {
  return request({
    url: '/ai/consession/' + id,
    method: 'get'
  })
}

// 新增对话会话
export function addConsession(data) {
  return request({
    url: '/ai/consession',
    method: 'post',
    data: data
  })
}

// 修改对话会话
export function updateConsession(data) {
  return request({
    url: '/ai/consession',
    method: 'put',
    data: data
  })
}

// 删除对话会话
export function delConsession(id) {
  return request({
    url: '/ai/consession/' + id,
    method: 'delete'
  })
}
