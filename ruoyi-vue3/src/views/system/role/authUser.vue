
<template>
   <div class="app-container">
      <el-form :model="queryParams" ref="queryRef" v-show="showSearch" :inline="true">
         <el-form-item :label="$t(user.username)" prop="userName">
            <el-input
               v-model="queryParams.userName"
               :placeholder="$t(user.usernameDesc)"
               clearable
               style="width: 240px"
               @keyup.enter="handleQuery"
            />
         </el-form-item>
         <el-form-item :label="$t(user.mobile)" prop="phonenumber">
            <el-input
               v-model="queryParams.phonenumber"
               :placeholder="$t(user.mobileDesc)"
               clearable
               style="width: 240px"
               @keyup.enter="handleQuery"
            />
         </el-form-item>
         <el-form-item>
            <el-button type="primary" icon="Search" @click="handleQuery">{{$t(common.search)}}</el-button>
            <el-button icon="Refresh" @click="resetQuery">{{$t(common.reset)}}</el-button>
         </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
         <el-col :span="1.5">
            <el-button
               type="primary"
               plain
               icon="Plus"
               @click="openSelectUser"
               v-hasPermi="['system:role:add']"
            >{{$t(user.addUser)}}</el-button>
         </el-col>
         <el-col :span="1.5">
            <el-button
               type="danger"
               plain
               icon="CircleClose"
               :disabled="multiple"
               @click="cancelAuthUserAll"
               v-hasPermi="['system:role:remove']"
            >{{$t(user.batchCancelAuth)}}</el-button>
         </el-col>
         <el-col :span="1.5">
            <el-button 
               type="warning" 
               plain 
               icon="Close"
               @click="handleClose"
            >{{$t(common.close)}}</el-button>
         </el-col>
         <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
         <el-table-column type="selection" width="55" align="center" />
         <el-table-column :label="$t(user.username)" prop="userName" :show-overflow-tooltip="true" />
         <el-table-column :label="$t(user.nickName)" prop="nickName" :show-overflow-tooltip="true" />
         <el-table-column :label="$t(user.email)" prop="email" :show-overflow-tooltip="true" />
         <el-table-column :label="$t(user.mobile)" prop="phonenumber" :show-overflow-tooltip="true" />
         <el-table-column :label="$t(user.status)" align="center" prop="status">
            <template #default="scope">
               <dict-tag :options="sys_normal_disable" :value="scope.row.status" />
            </template>
         </el-table-column>
         <el-table-column :label="$t(user.createTime)" align="center" prop="createTime" width="180">
            <template #default="scope">
               <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
         </el-table-column>
         <el-table-column :label="$t(common.operation)" align="center" class-name="small-padding fixed-width">
            <template #default="scope">
               <el-button link type="primary" icon="CircleClose" @click="cancelAuthUser(scope.row)" v-hasPermi="['system:role:remove']">{{$t(user.cancelAuth)}}</el-button>
            </template>
         </el-table-column>
      </el-table>

      <pagination
         v-show="total > 0"
         :total="total"
         v-model:page="queryParams.pageNum"
         v-model:limit="queryParams.pageSize"
         @pagination="getList"
      />
      <select-user ref="selectRef" :roleId="queryParams.roleId" @ok="handleQuery" />
   </div>
</template>

<script setup name="AuthUser">
import selectUser from "./selectUser"
import { allocatedUserList, authUserCancel, authUserCancelAll } from "@/api/system/role"

const route = useRoute()
const { proxy } = getCurrentInstance()
const { sys_normal_disable } = proxy.useDict("sys_normal_disable")

const userList = ref([])
const loading = ref(true)
const showSearch = ref(true)
const multiple = ref(true)
const total = ref(0)
const userIds = ref([])

const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  roleId: route.params.roleId,
  userName: undefined,
  phonenumber: undefined,
})

/** 查询授权用户列表 */
function getList() {
  loading.value = true
  allocatedUserList(queryParams).then(response => {
    userList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 返回按钮 */
function handleClose() {
  const obj = { path: "/system/role" }
  proxy.$tab.closeOpenPage(obj)
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  userIds.value = selection.map(item => item.userId)
  multiple.value = !selection.length
}

/** 打开授权用户表弹窗 */
function openSelectUser() {
  proxy.$refs["selectRef"].show()
}

/** 取消授权按钮操作 */
function cancelAuthUser(row) {
  proxy.$modal.confirm(proxy.$t(user.confirmCancelAuth) + '"' + row.userName + '"' + proxy.$t(user.role) + '"' + queryParams.roleId + '"' + proxy.$t(user.confirmCancelAuth)).then(function () {
    return authUserCancel({ userId: row.userId, roleId: queryParams.roleId })
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess(proxy.$t(user.cancelAuthSuccess))
  }).catch(() => {})
}

/** 批量取消授权按钮操作 */
function cancelAuthUserAll(row) {
  const roleId = queryParams.roleId
  const uIds = userIds.value.join(",")
  proxy.$modal.confirm(proxy.$t(user.confirmCancelAuthAll)).then(function () {
    return authUserCancelAll({ roleId: roleId, userIds: uIds })
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess(proxy.$t(user.cancelAuthSuccess))
  }).catch(() => {})
}

getList()
</script>
