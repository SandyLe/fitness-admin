<template>
   <div class="app-container">
      <el-form :model="queryParams" ref="queryRef" :inline="true">
         <el-form-item :label="$t('online.onlineUrl')" prop="ipaddr">
            <el-input
               v-model="queryParams.ipaddr"
               :placeholder="$t('online.onlineUrlPlaceholder')"
               clearable
               style="width: 200px"
               @keyup.enter="handleQuery"
            />
         </el-form-item>
         <el-form-item :label="$t('online.userName')" prop="userName">
            <el-input
               v-model="queryParams.userName"
               :placeholder="$t('online.userNamePlaceholder')"
               clearable
               style="width: 200px"
               @keyup.enter="handleQuery"
            />
         </el-form-item>
         <el-form-item>
            <el-button type="primary" icon="Search" @click="handleQuery">{{ $t('common.search') }}</el-button>
            <el-button icon="Refresh" @click="resetQuery">{{ $t('common.reset') }}</el-button>
         </el-form-item>
      </el-form>
      <el-table
         v-loading="loading"
         :data="onlineList.slice((pageNum - 1) * pageSize, pageNum * pageSize)"
         style="width: 100%;"
      >
         <el-table-column label="序号" width="50" type="index" align="center">
            <template #default="scope">
               <span>{{ (pageNum - 1) * pageSize + scope.$index + 1 }}</span>
            </template>
         </el-table-column>
         <el-table-column :label="$t('online.sessionId')" align="center" prop="tokenId" :show-overflow-tooltip="true" />
         <el-table-column :label="$t('online.loginName')" align="center" prop="userName" :show-overflow-tooltip="true" />
         <el-table-column :label="$t('online.deptName')" align="center" prop="deptName" :show-overflow-tooltip="true" />
         <el-table-column :label="$t('online.ipaddr')" align="center" prop="ipaddr" :show-overflow-tooltip="true" />
         <el-table-column :label="$t('online.loginLocation')" align="center" prop="loginLocation" :show-overflow-tooltip="true" />
         <el-table-column :label="$t('online.os')" align="center" prop="os" :show-overflow-tooltip="true" />
         <el-table-column :label="$t('online.browser')" align="center" prop="browser" :show-overflow-tooltip="true" />
         <el-table-column :label="$t('online.loginTime')" align="center" prop="loginTime" width="180">
            <template #default="scope">
               <span>{{ parseTime(scope.row.loginTime) }}</span>
            </template>
         </el-table-column>
         <el-table-column :label="$t('common.operation')" align="center" class-name="small-padding fixed-width">
            <template #default="scope">
               <el-button link type="primary" icon="Delete" @click="handleForceLogout(scope.row)" v-hasPermi="['monitor:online:forceLogout']">{{ $t('online.kickOut') }}</el-button>
            </template>
         </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" v-model:page="pageNum" v-model:limit="pageSize" />
   </div>
</template>

<script setup name="Online">
import { forceLogout, list as initData } from "@/api/monitor/online"

const { proxy } = getCurrentInstance()

const onlineList = ref([])
const loading = ref(true)
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)

const queryParams = ref({
  ipaddr: undefined,
  userName: undefined
})

/** 查询登录日志列表 */
function getList() {
  loading.value = true
  initData(queryParams.value).then(response => {
    onlineList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 搜索按钮操作 */
function handleQuery() {
  pageNum.value = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

/** 强退按钮操作 */
function handleForceLogout(row) {
    proxy.$modal.confirm($t('online.confirmKickOut') + '"' + row.userName + '"?').then(function () {
  return forceLogout(row.tokenId)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess($t('common.operationSuccess'))
  }).catch(() => {})
}

getList()
</script>
