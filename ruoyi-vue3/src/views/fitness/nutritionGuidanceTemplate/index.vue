<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item :label="$t('nutritionGuidanceTemplate.guidanceName')" prop="guidanceName">
        <el-input
          v-model="queryParams.course"
          :placeholder="$t('nutritionGuidanceTemplate.guidanceNamePlaceholder')"
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
          v-hasPermi="['ai:nutritionGuidanceTemplate:add']"
        >{{ $t('common.add') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['ai:nutritionGuidanceTemplate:edit']"
        >{{ $t('common.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['ai:nutritionGuidanceTemplate:remove']"
        >{{ $t('common.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['ai:nutritionGuidanceTemplate:export']"
        >{{ $t('common.export') }}</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="nutritionGuidanceTemplateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" align="center" prop="index" width="50"/>
      <el-table-column :label="$t('nutritionGuidanceTemplate.guidanceName')" align="center" prop="guidanceName" />
      <el-table-column :label="$t('nutritionGuidanceTemplate.templateCode')" align="center" prop="templateCode" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('nutritionGuidanceTemplate.themeId')" align="center" prop="themeId" :show-overflow-tooltip="true">
        <template #default="scope">
          {{ themeList.find(item => item.id === scope.row.themeId)?.themeName || '未指定' }}
        </template>
      </el-table-column>
      <el-table-column :label="$t('nutritionGuidanceTemplate.templateDesc')" align="center" prop="templateDesc" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('common.operation')" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="View" @click="handleVisit(scope.row)" v-hasPermi="['ai:nutritionGuidanceTemplate:edit']">{{ $t('nutritionGuidanceTemplate.visitnutritionGuidanceTemplate') }}</el-button>
          <el-button link type="primary" icon="Edit" @click="handleTemplateDtl(scope.row)" v-hasPermi="['fitness:nutritionGuidanceTemplate:templateDtl']">{{ $t('nutritionGuidanceTemplate.templateDtl') }}</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['ai:nutritionGuidanceTemplate:edit']">{{ $t('common.edit') }}</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['ai:nutritionGuidanceTemplate:remove']">{{ $t('common.delete') }}</el-button>
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
      <el-form ref="nutritionGuidanceTemplateRef" :model="form" :rules="rules" label-width="180px">
        <el-form-item :label="$t('nutritionGuidanceTemplate.guidanceName')" prop="guidanceName">
          <el-input v-model="form.guidanceName" :placeholder="$t('nutritionGuidanceTemplate.guidanceNamePlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('nutritionGuidanceTemplate.templateCode')" prop="templateCode">
          <el-input v-model="form.templateCode" :placeholder="$t('nutritionGuidanceTemplate.templateCodePlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('nutritionGuidanceTemplate.themeId')" prop="themeId">
          <el-select v-model="form.themeId" :placeholder="$t('nutritionGuidanceTemplate.themeIdPlaceholder')">
            <el-option v-for="item in themeList" :key="item.id" :label="item.themeName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('nutritionGuidanceTemplate.templateDesc')" prop="templateDesc">
          <el-input v-model="form.templateDesc" type="textarea" :placeholder="$t('nutritionGuidanceTemplate.templateDescPlaceholder')" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">{{ $t('common.confirm') }}</el-button>
          <el-button @click="cancel">{{ $t('common.cancel') }}</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 营养指导模板明细 -->
    <el-dialog :title="title" v-model="dtlDialog" width="1024px" append-to-body>
      <CrudTable
          title="营养指导模板明细"
          ref="templateDtlRef"
          :columns="recordDtlColumns"
          :hidden-params="{ templateId, dictType }"
          row-key = "id"
          :list-request="listNutritionGuidanceDetail"
          :add-request="addNutritionGuidanceDetail"
          :update-request="updateNutritionGuidanceDetail"
          :delete-request="delNutritionGuidanceDetail"
      />
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dtlDialog = false">{{ $t('common.close') }}</el-button>
        </div>
      </template>
    </el-dialog>


    <!-- 训练记录详细 -->
    <el-dialog :title="title" v-model="openRecordDtlView" width="1400px" append-to-body>
      <el-form :model="form" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('nutritionGuidanceTemplate.guidanceName')">{{ form.guidanceName }}</el-form-item>
            <el-form-item :label="$t('nutritionGuidanceTemplate.themeId')">
              <div>{{ themeList.find(item => item.id === form.themeId)?.themeName || '未指定' }}</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('nutritionGuidanceTemplate.templateCode')">{{ form.templateCode }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item :label="$t('nutritionGuidanceTemplate.templateDesc')">{{ form.templateDesc }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <CrudTable
          title="营养指导明细"
          ref="recordRef"
          no-editing= true
          :columns="recordDtlColumns"
          :hidden-params="{ templateId, dictType }"
          row-key = "id"
          :list-request="listNutritionGuidanceDetail"
      />
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="openRecordDtlView = false">{{ $t('common.close') }}</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="nutritionGuidanceTemplate">
import {
  listNutritionGuidanceTemplate,
  getNutritionGuidanceTemplate,
  delNutritionGuidanceTemplate,
  addNutritionGuidanceTemplate,
  updateNutritionGuidanceTemplate
} from "@/api/fitness/nutritionGuidanceTemplate"
import CrudTable from "@/components/CrudTable"
import {listCourseTheme} from "@/api/fitness/courseTheme.js";
import {
  addNutritionGuidanceDetail,
  delNutritionGuidanceDetail,
  listNutritionGuidanceDetail,
  updateNutritionGuidanceDetail
} from "@/api/fitness/nutritionGuidanceDetail.js";
import {getDicts} from "@/api/system/dict/data.js";

const recordDtlColumns = [{ label: '类型', prop: 'typeCode', editable: true, editor: 'select',
  optionsRequest: getDicts, optionsFormatter: (item) => ({
    label: item.dictLabel,
    value: item.dictValue
  })},
  { label: '项目', prop: 'itemName', editable: true },
  { label: '值', prop: 'itemValue', editable: true }]
const { proxy } = getCurrentInstance()

const nutritionGuidanceTemplateList = ref([])
const themeList = ref([])
const open = ref(false)
const openRecordDtlView = ref(false)
const dtlDialog = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const templateId = ref(0)
const dictType = ref("")
const templateDtlRef = ref(null)
const recordRef = ref(null)

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    nickName: null,
  },
  rules: {
    nickName: [
      { required: true, message: proxy.$t('nutritionGuidanceTemplate.nickNamePlaceholder'), trigger: "blur" }
    ],
    modelId: [
      { required: true, message: proxy.$t('nutritionGuidanceTemplate.modelPlaceholder'), trigger: "blur" }
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
const { queryParams, form, rules } = toRefs(data)

/** 查看详情 */
function handleVisit(row) {
  reset()
  templateId.value = row.id
  const _id = row.id || ids.value
  recordRef.value?.reload()
  getNutritionGuidanceTemplate(_id).then(response => {
    form.value = response.data
    openRecordDtlView.value = true
    dictType.value = 'nutrition_guidance_type'
    title.value = proxy.$t('nutritionGuidanceTemplate.viewnutritionGuidanceTemplate')
    recordRef.value?.reload()
  })
}
/** 查询智能体列表 */
function getList() {
  loading.value = true
  listNutritionGuidanceTemplate(queryParams.value).then(response => {
    nutritionGuidanceTemplateList.value = response.rows
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
    templateCode: null,
    guidanceName: null,
    themeId: null,
    templateDesc: null,
    status: null,
    createdTime: null,
    updatedTime: null
  }
  proxy.resetForm("nutritionGuidanceTemplateRef")
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
  title.value = proxy.$t('nutritionGuidanceTemplate.addNutritionGuidanceTemplate')
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getNutritionGuidanceTemplate(_id).then(response => {
    form.value = response.data
    form.value.knowledgeBaseIds = form.value.knowledgeBaseIds ? form.value.knowledgeBaseIds.split(',').map(Number) : [];
    open.value = true
    title.value = proxy.$t('nutritionGuidanceTemplate.updateNutritionGuidanceTemplate')
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["nutritionGuidanceTemplateRef"].validate(valid => {
    if (valid) {
      form.value.knowledgeBaseIds = form.value.knowledgeBaseIds ? form.value.knowledgeBaseIds.join(',') : '';
      if (form.value.id != null) {
        updateNutritionGuidanceTemplate(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t('nutritionGuidanceTemplate.updateNutritionGuidanceTemplateSuccess'))
          open.value = false
          getList()
        })
      } else {
        addNutritionGuidanceTemplate(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t('nutritionGuidanceTemplate.addNutritionGuidanceTemplateSuccess'))
          open.value = false
          getList()
        })
      }
    }
  })
}

function getCourseThemeList() {
  listCourseTheme().then(response => {
    themeList.value = response.rows
  })
}
/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm(proxy.$t('nutritionGuidanceTemplate.confirmDelete', _ids)).then(function() {
    return delNutritionGuidanceTemplate(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess(proxy.$t('nutritionGuidanceTemplate.deleteSuccess'))
  }).catch(() => {})
}

function handleTemplateDtl(row) {
  reset()
  const _id = row.id || ids.value
  templateId.value = _id
  getNutritionGuidanceTemplate(_id).then(response => {
    form.value = response.data
    dtlDialog.value = true
    dictType.value = 'nutrition_guidance_type'
    title.value = proxy.$t('nutritionGuidanceTemplate.templateDtlManage')
    templateDtlRef.value?.reload()
  })
}
/** 导出按钮操作 */
function handleExport() {
  proxy.download('ai/nutritionGuidanceTemplate/export', {
    ...queryParams.value
  }, `nutritionGuidanceTemplate_${new Date().getTime()}.xlsx`)
}
getList()
getCourseThemeList();
</script>
