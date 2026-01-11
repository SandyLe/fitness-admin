import request from '@/utils/request'

// 查询用户知识库配额列表
export function listKnowquota(query) {
    return request({
        url: '/ai/knowquota/list',
        method: 'get',
        params: query
    })
}

// 查询当前用户的配额信息
export function getKnowquotaByUser() {
    return request({
        url: '/ai/knowquota/getInfoByUser',
        method: 'get'
    })
}

// 查询用户知识库配额详细
export function getKnowquota(quotaId) {
    return request({
        url: '/ai/knowquota/' + quotaId,
        method: 'get'
    })
}

// 新增用户知识库配额
export function addKnowquota(data) {
    return request({
        url: '/ai/knowquota',
        method: 'post',
        data: data
    })
}

// 修改用户知识库配额
export function updateKnowquota(data) {
    return request({
        url: '/ai/knowquota',
        method: 'put',
        data: data
    })
}

// 删除用户知识库配额
export function delKnowquota(quotaId) {
    return request({
        url: '/ai/knowquota/' + quotaId,
        method: 'delete'
    })
}
