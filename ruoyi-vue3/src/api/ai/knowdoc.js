import request from '@/utils/request'

// 查询知识库文档列表
export function listknowdoc(query) {
  return request({
    url: '/ai/knowdoc/list',
    method: 'get',
    params: query
  })
}

// 查询知识库文档详细
export function getknowdoc(id) {
  return request({
    url: '/ai/knowdoc/' + id,
    method: 'get'
  })
}

// 新增知识库文档
export function addknowdoc(data) {
  return request({
    url: '/ai/knowdoc',
    method: 'post',
    data: data
  })
}

// 修改知识库文档
export function updateknowdoc(data) {
  return request({
    url: '/ai/knowdoc',
    method: 'put',
    data: data
  })
}

// 删除知识库文档
export function delknowdoc(id) {
  return request({
    url: '/ai/knowdoc/' + id,
    method: 'delete'
  })
}
