import request from '@/utils/request'

// 查询知识库列表
export function listKnowbase(query) {
  return request({
    url: '/ai/knowbase/list',
    method: 'get',
    params: query
  })
}

// 查询知识库详细
export function getKnowbase(id) {
  return request({
    url: '/ai/knowbase/' + id,
    method: 'get'
  })
}

// 新增知识库
export function addKnowbase(data) {
  return request({
    url: '/ai/knowbase',
    method: 'post',
    data: data
  })
}

// 修改知识库
export function updateKnowbase(data) {
  return request({
    url: '/ai/knowbase',
    method: 'put',
    data: data
  })
}

// 删除知识库
export function delKnowbase(id) {
  return request({
    url: '/ai/knowbase/' + id,
    method: 'delete'
  })
}
