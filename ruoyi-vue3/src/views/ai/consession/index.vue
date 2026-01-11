<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">{{ $t('common.search') }}</el-button>
        <el-button icon="Refresh" @click="resetQuery">{{ $t('common.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['ai:consession:add']"
        >{{ $t('common.add') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['ai:consession:edit']"
        >{{ $t('common.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['ai:consession:remove']"
        >{{ $t('common.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['ai:consession:export']"
        >{{ $t('common.export') }}</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="consessionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('comsession.sessionId')" align="center" prop="id" />
      <el-table-column :label="$t('comsession.sessionIdDesc')" align="center" prop="sessionId" />
      <el-table-column :label="$t('comsession.memoryIdDesc')" align="center" prop="memoryId" />
      <el-table-column :label="$t('comsession.agentIdDesc')" align="center" prop="agentId" />
      <el-table-column :label="$t('comsession.userIdDesc')" align="center" prop="userId" />
      <el-table-column :label="$t('comsession.sessionTitleDesc')" align="center" prop="sessionTitle" />
      <el-table-column :label="$t('comsession.totalTokensDesc')" align="center" prop="totalTokens" />
      <el-table-column :label="$t('comsession.messageCountDesc')" align="center" prop="messageCount" />
      <el-table-column :label="$t('comsession.startTimeDesc')" align="center" prop="startTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('comsession.endTimeDesc')" align="center" prop="endTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
       <el-table-column :label="$t('comsession.statusDesc')" align="center" prop="status" >
        <template #default="scope">
          <span v-if="scope.row.status == 0">{{ $t('comsession.statusDescStart') }}</span>
          <span v-else>{{ $t('comsession.statusDescEnd') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.createTime')" align="center" prop="createdTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createdTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.updateTime')" align="center" prop="updatedTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updatedTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.operation')" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['ai:consession:edit']">{{ $t('common.edit') }}</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['ai:consession:remove']">{{ $t('common.delete') }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改对话会话对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="consessionRef" :model="form" :rules="rules" label-width="180px">
        <el-form-item :label="$t('comsession.sessionId')" prop="sessionId">
          <el-input v-model="form.sessionId" :placeholder="$t('comsession.sessionIdDesc')" />
        </el-form-item>
        <el-form-item :label="$t('comsession.memoryIdDesc')" prop="memoryId">
          <el-input v-model="form.memoryId" :placeholder="$t('comsession.memoryIdDesc')" />
        </el-form-item>
        <el-form-item :label="$t('comsession.agentIdDesc')" prop="agentId">
          <el-input v-model="form.agentId" :placeholder="$t('comsession.agentIdDesc')" disabled />
        </el-form-item>
        <el-form-item :label="$t('comsession.userIdDesc')" prop="userId"> 
          <el-input v-model="form.userId" :placeholder="$t('comsession.userIdDesc')" />
        </el-form-item>
        <el-form-item :label="$t('comsession.sessionTitleDesc')" prop="sessionTitle">
          <el-input v-model="form.sessionTitle" :placeholder="$t('comsession.sessionTitleDesc')" />
        </el-form-item>
        <el-form-item :label="$t('comsession.totalTokensDesc')" prop="totalTokens">
          <el-input v-model="form.totalTokens" :placeholder="$t('comsession.totalTokensDesc')" />
        </el-form-item>
        <el-form-item :label="$t('comsession.messageCountDesc')" prop="messageCount">
          <el-input v-model="form.messageCount" :placeholder="$t('comsession.messageCountDesc')" />
        </el-form-item>
        <el-form-item :label="$t('comsession.startTimeDesc')" prop="startTime">
          <el-date-picker clearable
            v-model="form.startTime"
            type="date"
            value-format="YYYY-MM-DD"
            :placeholder="$t('comsession.startTimeDesc')">
          </el-date-picker>
        </el-form-item>
        <el-form-item :label="$t('comsession.endTimeDesc')" prop="endTime">   
          <el-date-picker clearable
            v-model="form.endTime"
            type="date"
            value-format="YYYY-MM-DD"
            :placeholder="$t('comsession.endTimeDesc')">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">{{ $t('common.confirm') }}</el-button>
          <el-button @click="cancel">{{ $t('common.cancel') }}</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Consession">
import { listConsession, getConsession, delConsession, addConsession, updateConsession } from "@/api/ai/consession"

const { proxy } = getCurrentInstance()

const consessionList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
  },
  rules: {
    sessionId: [
      { required: true, message: proxy.$t("comsession.sessionIdRequired"), trigger: "blur" }
    ],
    memoryId: [
      { required: true, message: proxy.$t("comsession.memoryIdRequired"), trigger: "blur" }
    ],
  
    userId: [
      { required: true, message: proxy.$t("comsession.userIdRequired"), trigger: "blur" }
    ],
    startTime: [
      { required: true, message: proxy.$t("comsession.startTimeRequired"), trigger: "blur" }
    ],
    status: [
      { required: true, message: proxy.$t("comsession.statusRequired"), trigger: "change" }
    ],
    createdTime: [
      { required: true, message: proxy.$t("comsession.createdTimeRequired"), trigger: "blur" }
    ],
    updatedTime: [
      { required: true, message: proxy.$t("comsession.updatedTimeRequired"), trigger: "blur" }
    ]
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询对话会话列表 */
function getList() {
  loading.value = true
  listConsession(queryParams.value).then(response => {
    consessionList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    sessionId: null,
    memoryId: null,
    agentId: proxy.$route.query.id,
    userId: null,
    sessionTitle: null,
    totalTokens: null,
    messageCount: null,
    startTime: null,
    endTime: null,
    status: null,
    createdTime: null,
    updatedTime: null
  }
  proxy.resetForm("consessionRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = proxy.$t("comsession.addSession")
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getConsession(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = proxy.$t("comsession.updateSession")
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["consessionRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateConsession(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t("common.updateSuccess"))
          open.value = false
          getList()
        })
      } else {
        addConsession(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t("common.addSuccess"))
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm(proxy.$t("comsession.confirmDeleteSession") + _ids + proxy.$t("common.menuName")+'"？').then(function() {
    return delConsession(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess(proxy.$t("common.deleteSuccess"))
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('ai/consession/export', {
    ...queryParams.value
  }, `consession_${new Date().getTime()}.xlsx`)
}

getList()
</script>
