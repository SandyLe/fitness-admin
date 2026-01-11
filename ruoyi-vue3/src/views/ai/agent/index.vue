<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item :label="$t('agent.agentName')" prop="agentName">
        <el-input
          v-model="queryParams.agentName"
          :placeholder="$t('agent.agentNamePlaceholder')"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">
          {{ $t('common.search') }}
        </el-button>
        <el-button icon="Refresh" @click="resetQuery">
          {{ $t('common.reset') }}
        </el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['ai:agent:add']"
        >{{ $t('common.add') }}</el-button>
      </el-col>
        <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          :disabled="single"
          @click="handlePush"
          v-hasPermi="['ai:agent:history']"
        >{{ $t('agent.history') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['ai:agent:edit']"
        >{{ $t('common.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['ai:agent:remove']"
        >{{ $t('common.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['ai:agent:export']"
        >{{ $t('common.export') }}</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="agentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('agent.agentId')" align="center" prop="id" />
      <el-table-column :label="$t('agent.agentName')" align="center" prop="agentName" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('agent.agentDescription')" align="center" prop="agentDescription" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('agent.model')" align="center" prop="modelId" :show-overflow-tooltip="true">
        <template #default="scope">
          {{ modelList.find(item => item.id === scope.row.modelId)?.modelName || '未指定' }}
        </template>
      </el-table-column>
      <el-table-column :label="$t('agent.knowledgeBase')" align="center" prop="knowledgeBaseIds" :show-overflow-tooltip="true">
        <template #default="scope">
            {{ getKnowledgeBaseNames(scope.row.knowledgeBaseIds) }}
        </template>
      </el-table-column>
      <el-table-column :label="$t('agent.status')" align="center" prop="status" >
        <template #default="scope">
          {{ scope.row.status === 1 ? $t('agent.statusYes') : $t('agent.statusNo') }}
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.createTime')" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.updateTime')" align="center" prop="updateTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.operation')" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
           <el-button link type="primary" icon="View" @click="handleVisit(scope.row)" v-hasPermi="['ai:agent:edit']">{{ $t('agent.visitAgent') }}</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['ai:agent:edit']">{{ $t('common.edit') }}</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['ai:agent:remove']">{{ $t('common.delete') }}</el-button>
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

    <!-- 添加或修改智能体对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="agentRef" :model="form" :rules="rules" label-width="180px">
        <el-form-item :label="$t('agent.agentName')" prop="agentName">
          <el-input v-model="form.agentName" :placeholder="$t('agent.agentNamePlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('agent.agentDescription')" prop="agentDescription">
          <el-input v-model="form.agentDescription" type="textarea" :placeholder="$t('agent.agentDescriptionPlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('agent.largeModel')" prop="modelId">
          <el-select v-model="form.modelId" :placeholder="$t('agent.modelPlaceholder')">
            <el-option v-for="item in modelList" :key="item.id" :label="item.modelName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('agent.knowledgeBase')" prop="knowledgeBaseIds">
          <el-select v-model="form.knowledgeBaseIds" multiple :placeholder="$t('agent.knowledgeBasePlaceholder')">
            <el-option v-for="item in knowbaseList" :key="item.id" :label="item.kbName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('agent.systemPrompt')" prop="systemPrompt">
          <el-input v-model="form.systemPrompt" type="textarea" :placeholder="$t('agent.systemPromptPlaceholder')" />
          <el-upload
            ref="systemPromptRef"
            class="avatar-uploader"
            :action="uploadUrl"
            :show-file-list="false"
            :on-success="importSystemPrompt"
            :accept="'.txt'"
          >
            <el-button size="small" type="primary">{{ $t('common.import') }}</el-button>
          </el-upload>
          
        </el-form-item>
<!--        <el-form-item label="温度参数(0-1)" prop="temperature">-->
<!--          <el-input v-model="form.temperature" placeholder="请输入温度参数(0-1)" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="最大token数" prop="maxTokens">-->
<!--          <el-input v-model="form.maxTokens" placeholder="请输入最大token数" />-->
<!--        </el-form-item>-->
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

<script setup name="Agent">
import { listAgent, getAgent, delAgent, addAgent, updateAgent } from "@/api/ai/agent"
import { listModel } from "@/api/ai/model"
import { listKnowbase } from "@/api/ai/knowbase"

const router = useRouter()
const { proxy } = getCurrentInstance()

const agentList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const uploadUrl = ref(import.meta.env.VITE_APP_BASE_API + "/common/upload") // 上传的文件服务器地址

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    agentName: null,
  },
  rules: {
    agentName: [
      { required: true, message: proxy.$t('agent.agentNamePlaceholder'), trigger: "blur" }
    ],
    modelId: [
      { required: true, message: proxy.$t('agent.modelPlaceholder'), trigger: "blur" }
    ],
    // userId: [
    //   { required: true, message: "创建用户ID不能为空", trigger: "blur" }
    // ],
    // status: [
    //   { required: true, message: "状态(0:禁用 1:启用)不能为空", trigger: "change" }
    // ],
    // createdTime: [
    //   { required: true, message: "创建时间不能为空", trigger: "blur" }
    // ],
    // updatedTime: [
    //   { required: true, message: "更新时间不能为空", trigger: "blur" }
    // ]
  }
})
const modelList = ref([])
const knowbaseList = ref([])
const { queryParams, form, rules } = toRefs(data)

/** 访问智能体 */
function handleVisit(row) {
  router.push({
    path: '/onlines',
    query: {
      agentId: row.id
    }
  })
}
/** 新增按钮操作 */
function handlePush() {
  reset()
  router.push({
    path: '/ai/consession',
    query: {
      id: ids.value[0]
    }
  })
}
/** 查询智能体列表 */
function getList() {
  loading.value = true
  listAgent(queryParams.value).then(response => {
    agentList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}
/** 查询知识库列表 */
function getKnowbaseList() {
  listKnowbase().then(response => {
    knowbaseList.value = response.rows
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
    agentName: null,
    agentDescription: null,
    modelId: null,
    knowledgeBaseIds: null,
    userId: null,
    systemPrompt: null,
    temperature: null,
    maxTokens: null,
    status: null,
    createdTime: null,
    updatedTime: null
  }
  proxy.resetForm("agentRef")
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
function getModelList() {
  listModel().then(response => {
    modelList.value = response.rows
  })
}
/** 导入系统提示词 */
function importSystemPrompt(res,file) {
  //上传txt文件 获取文件内容赋值给systemPrompt
  if (res.code === 200) {
    form.value.systemPrompt = res.fileName
  } else {
    proxy.$modal.msgError(res.msg)
    proxy.$refs.systemPromptRef.handleRemove(file)
  }

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
  title.value = proxy.$t('agent.addAgent')
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getAgent(_id).then(response => {
    form.value = response.data
    form.value.knowledgeBaseIds = form.value.knowledgeBaseIds ? form.value.knowledgeBaseIds.split(',').map(Number) : [];
    open.value = true
    title.value = proxy.$t('agent.updateAgent')
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["agentRef"].validate(valid => {
    if (valid) {
      form.value.knowledgeBaseIds = form.value.knowledgeBaseIds ? form.value.knowledgeBaseIds.join(',') : '';
      if (form.value.id != null) {
        updateAgent(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t('agent.updateAgentSuccess'))
          open.value = false
          getList()
        })
      } else {
        addAgent(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t('agent.addAgentSuccess'))
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
  proxy.$modal.confirm(proxy.$t('agent.confirmDelete', _ids)).then(function() {
    return delAgent(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess(proxy.$t('agent.deleteSuccess'))
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('ai/agent/export', {
    ...queryParams.value
  }, `agent_${new Date().getTime()}.xlsx`)
}
getModelList()
getKnowbaseList()
getList()
const getKnowledgeBaseNames = computed(() => (knowledgeBaseIds) => {
  console.log('knowledgeBaseIds',knowledgeBaseIds, knowbaseList.value)
  if (!knowledgeBaseIds) return proxy.$t('agent.noKnowledgeBase');
  return knowledgeBaseIds
    .split(',')
    .map(id => id.trim())
    .map(id => knowbaseList.value.find(item => item.id === Number(id))?.kbName || proxy.$t('agent.unknownKnowledgeBase'))
    .join(', ');
});
</script>
