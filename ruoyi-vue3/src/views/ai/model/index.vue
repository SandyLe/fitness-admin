<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item :label="$t('model.modelName')" prop="modelName">
         <el-select style="width: 150px" v-model="queryParams.modelName" :placeholder="$t('model.modelNameDec')"   @keyup.enter="handleQuery">
          <el-option v-for="dict in ai_mode_name" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('model.owner')" prop="createBy" v-hasRole="['admin']">
        <el-input
            v-model="queryParams.createBy"
            :placeholder="$t('model.ownerDec')"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">{{$t('common.search')}}</el-button>
        <el-button icon="Refresh" @click="resetQuery">{{$t('common.reset')}}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['ai:model:add']"
        >{{$t('common.add')}}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['ai:model:edit']"
        >{{$t('common.edit')}}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['ai:model:remove']"
        >{{$t('common.delete')}}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['ai:model:export']"
        >{{$t('common.export')}}</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="modelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('model.modelId')" align="center" prop="id" />
      <el-table-column :label="$t('model.modelName')" align="center" prop="modelName" :show-overflow-tooltip="true">
        <template #default="scope">
          <dict-tag :options="ai_mode_name" :value="scope.row.modelName" />
        </template>
      </el-table-column>
      <el-table-column :label="$t('model.modelType')" align="center" prop="modelType" />
<!--      <el-table-column label="模型配置参数" align="center" prop="modelConfig" />-->
      <el-table-column :label="$t('model.apiKey')" align="center" prop="apiKey" :show-overflow-tooltip="true" />
      <el-table-column :label="$t('model.baseUrl')" align="center" prop="baseUrl" :show-overflow-tooltip="true" />
      <el-table-column :label="$t('model.status')" align="center" prop="status" >
        <template #default="scope">
          <span>{{ scope.row.status === 0 ? $t('model.disabled') : $t('model.enabled') }}</span>
        </template>
      </el-table-column>
       <el-table-column :label="$t('model.createBy')" align="center" prop="createBy" />
      <el-table-column :label="$t('model.createdTime')" align="center" prop="createdTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('model.updatedTime')" align="center" prop="updatedTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.operation')" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['ai:model:edit']">{{$t('common.edit')}}</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['ai:model:remove']">{{$t('common.delete')}}</el-button>
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

    <!-- 添加或修改AI模型配置对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="modelRef" :model="form" :rules="rules" label-width="180px">
        <el-form-item :label="$t('model.modelName')" prop="modelName">
            <el-select v-model="form.modelName" :placeholder="$t('model.modelNameDec')">
              <el-option v-for="dict in ai_mode_name" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item :label="$t('model.modelType')" prop="modelType">
          <el-select v-model="form.modelType" :placeholder="$t('model.modelTypeDec')">
            <el-option label="OPENAI" value="OPENAI" />
            <el-option label="CHATGLM" value="CHATGLM" />
            <el-option label="ERNIE" value="ERNIE" />
            <el-option label="QWEN" value="QWEN" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('model.apiKey')" prop="apiKey">
          <el-input v-model="form.apiKey" type="textarea" :placeholder="$t('model.apiKeyDec')" />
        </el-form-item>
        <el-form-item :label="$t('model.baseUrl')" prop="baseUrl">
          <el-input v-model="form.baseUrl" type="textarea" :placeholder="$t('model.baseUrlDec')" />
        </el-form-item>
        <el-form-item :label="$t('model.modelConfig')" prop="modelConfig">
          <el-input v-model="form.modelConfig" type="textarea" :placeholder="$t('model.modelConfigDec')" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">{{$t('common.confirm')}}</el-button>
          <el-button @click="cancel">{{$t('common.cancel')}}</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Model">
import { listModel, getModel, delModel, addModel, updateModel } from "@/api/ai/model"

const { proxy } = getCurrentInstance()

const modelList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const {  ai_mode_name } = proxy.useDict( "ai_mode_name")

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    modelName: null,
  },
  rules: {
    modelName: [
      { required: true, message: proxy.$t('model.modelNameRequired'), trigger: "blur" }
    ],
    modelType: [
      { required: true, message: proxy.$t('model.modelTypeRequired'), trigger: "change" }
    ],
    // modelConfig: [
    //   {
    //     required: true,
    //     message: "模型配置参数(JSON格式)不能为空",
    //     trigger: "blur",
    //     validator: (rule, value, callback) => {
    //       try {
    //         if (value) JSON.parse(value);
    //         callback();
    //       } catch (err) {
    //         callback(new Error("模型配置参数必须是有效的JSON格式"));
    //       }
    //     }
    //   }
    // ],
    apiKey: [
      { required: true, message: proxy.$t('model.apiKeyRequired'), trigger: "blur" }
    ],
    baseUrl: [
      { required: true, message: proxy.$t('model.baseUrlRequired'), trigger: "blur" }
    ]
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询AI模型配置列表 */
function getList() {
  loading.value = true
  listModel(queryParams.value).then(response => {
    modelList.value = response.rows
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
    modelName: null,
    modelType: null,
    modelConfig: null,
    apiKey: null,
    baseUrl: null,
    status: null,
    createdBy: null,
    createdTime: null,
    updatedTime: null
  }
  proxy.resetForm("modelRef")
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
  title.value = proxy.$t('model.addModel')
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getModel(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = proxy.$t('model.updateModel')
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["modelRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateModel(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t('common.updateSuccess'))
          open.value = false
          getList()
        })
      } else {
        addModel(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t('common.addSuccess'))
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
  proxy.$modal.confirm(proxy.$t('model.confirmDelete') + '"' + _ids + '"' + proxy.$t('common.menuName')+'?').then(function() {
    return delModel(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess(proxy.$t('common.deleteSuccess'))
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('ai/model/export', {
    ...queryParams.value
  }, `model_${new Date().getTime()}.xlsx`)
}

getList()
</script>
