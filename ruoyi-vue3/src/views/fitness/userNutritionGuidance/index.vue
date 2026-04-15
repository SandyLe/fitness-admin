<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="188px">
      <el-form-item :label="$t('userNutritionGuidance.guidanceName')" prop="guidanceName">
        <el-input
          v-model="queryParams.course"
          :placeholder="$t('userNutritionGuidance.guidanceNamePlaceholder')"
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
          v-hasPermi="['fitness:userNutritionGuidance:add']"
        >{{ $t('common.add') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['fitness:userNutritionGuidance:edit']"
        >{{ $t('common.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['fitness:userNutritionGuidance:remove']"
        >{{ $t('common.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['ai:userNutritionGuidance:export']"
        >{{ $t('common.export') }}</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="userNutritionGuidanceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Index" type="index" align="center" prop="index" width="50"/>
      <el-table-column :label="$t('userNutritionGuidance.createTime')" align="center" prop="createTime" />
      <el-table-column :label="$t('userNutritionGuidance.nickName')" align="center" prop="nickName" />
      <el-table-column :label="$t('userNutritionGuidance.guidanceName')" align="center" prop="guidanceName" />
      <el-table-column :label="$t('userNutritionGuidance.templateCode')" align="center" prop="templateCode" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('userNutritionGuidance.themeId')" align="center" prop="themeId" :show-overflow-tooltip="true">
        <template #default="scope">
          {{ themeList.find(item => item.id === scope.row.themeId)?.themeName || 'Not specified' }}
        </template>
      </el-table-column>
      <el-table-column :label="$t('userNutritionGuidance.guidanceDesc')" align="center" prop="guidanceDesc" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('common.operation')" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="View" @click="handleVisit(scope.row)" v-hasPermi="['fitness:userNutritionGuidance:view']">{{ $t('userNutritionGuidance.visitUserNutritionGuidance') }}</el-button>
          <el-button link type="primary" icon="Edit" @click="handleguidanceDtl(scope.row)" v-hasPermi="['fitness:userNutritionGuidance:guidanceDtl']">{{ $t('userNutritionGuidance.guidanceDtl') }}</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['fitness:userNutritionGuidance:edit']">{{ $t('common.edit') }}</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['fitness:userNutritionGuidance:remove']">{{ $t('common.delete') }}</el-button>
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

    <!-- 添加或修改营养指导对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="userNutritionGuidanceRef" :model="form" :rules="rules" label-width="180px">
        <el-form-item :label="$t('userNutritionGuidance.template')" v-if="addFlag" prop="template">
          <el-select v-model="form.template" :placeholder="$t('userNutritionGuidance.templatePlaceholder')" @change="changeTemplate">
            <el-option v-for="item in templateList" :key="item.id" :label="item.guidanceName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('userNutritionGuidance.userId')" v-if="addFlag" prop="userId">
          <el-select v-model="form.userId" :placeholder="$t('userNutritionGuidance.userIdPlaceholder')">
            <el-option v-for="item in userList" :key="item.id" :label="item.nickName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('userNutritionGuidance.guidanceName')"  prop="guidanceName">
          <el-input v-model="form.guidanceName" :placeholder="$t('userNutritionGuidance.guidanceNamePlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('userNutritionGuidance.templateCode')" prop="templateCode">
          <el-input v-model="form.templateCode" :placeholder="$t('userNutritionGuidance.templateCodePlaceholder')" readonly />
        </el-form-item>
        <el-form-item :label="$t('userNutritionGuidance.themeId')" prop="themeId">
          <el-select v-model="form.themeId" :placeholder="$t('userNutritionGuidance.themeIdPlaceholder')">
            <el-option v-for="item in themeList" :key="item.id" :label="item.themeName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('userNutritionGuidance.guidanceDesc')" prop="guidanceDesc">
          <el-input v-model="form.guidanceDesc" type="textarea" :placeholder="$t('userNutritionGuidance.guidanceDescPlaceholder')" />
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
          title="Nutrition guidance details"
          ref="guidanceDtlRef"
          :columns="recordDtlColumns"
          :hidden-params="{ nutritionGuidanceId, dictType }"
          row-key = "id"
          :list-request="listUserNutritionGuidanceDetail"
          :add-request="addUserNutritionGuidanceDetail"
          :update-request="updateUserNutritionGuidanceDetail"
          :delete-request="delUserNutritionGuidanceDetail"
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
            <el-form-item :label="$t('userNutritionGuidance.guidanceName')">{{ form.guidanceName }}</el-form-item>
            <el-form-item :label="$t('userNutritionGuidance.themeId')">
              <div>{{ themeList.find(item => item.id === form.themeId)?.themeName || 'Not specified' }}</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('userNutritionGuidance.templateCode')">{{ form.templateCode }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item :label="$t('userNutritionGuidance.guidanceDesc')">{{ form.guidanceDesc }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <CrudTable
          title="Nutrition guidance details"
          ref="recordRef"
          no-editing= true
          :columns="recordDtlColumns"
          :hidden-params="{ nutritionGuidanceId, dictType }"
          row-key = "id"
          :list-request="listUserNutritionGuidanceDetail"
      />
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="openRecordDtlView = false">{{ $t('common.close') }}</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="userNutritionGuidance">
import {
  listUserNutritionGuidance,
  getUserNutritionGuidance,
  delUserNutritionGuidance,
  addUserNutritionGuidance,
  updateUserNutritionGuidance
} from "@/api/fitness/userNutritionGuidance"
import CrudTable from "@/components/CrudTable"
import {listCourseTheme} from "@/api/fitness/courseTheme.js";
import {
  addUserNutritionGuidanceDetail,
  delUserNutritionGuidanceDetail,
  listUserNutritionGuidanceDetail,
  updateUserNutritionGuidanceDetail
} from "@/api/fitness/userNutritionGuidanceDetail.js";
import {getDicts} from "@/api/system/dict/data.js";
import {listNutritionGuidanceTemplate, getNutritionGuidanceTemplate} from "@/api/fitness/nutritionGuidanceTemplate.js";
import { listUser} from "@/api/fitness/user"

const recordDtlColumns = [{ label: 'Type', prop: 'typeCode', editable: true, editor: 'select',
  optionsRequest: getDicts, optionsFormatter: (item) => ({
    label: item.dictLabel,
    value: item.dictValue
  })},
  { label: 'Item', prop: 'itemName', editable: true },
  { label: 'Value', prop: 'itemValue', editable: true }]
const { proxy } = getCurrentInstance()

const userNutritionGuidanceList = ref([])
const themeList = ref([])
const templateList = ref([])
const userList = ref([])
const open = ref(false)
const addFlag = ref(false)
const openRecordDtlView = ref(false)
const dtlDialog = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const nutritionGuidanceId = ref(0)
const dictType = ref("")
const guidanceDtlRef = ref(null)
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
      { required: true, message: proxy.$t('userNutritionGuidance.nickNamePlaceholder'), trigger: "blur" }
    ],
    modelId: [
      { required: true, message: proxy.$t('userNutritionGuidance.modelPlaceholder'), trigger: "blur" }
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
  nutritionGuidanceId.value = row.id
  const _id = row.id || ids.value
  recordRef.value?.reload()
  getUserNutritionGuidance(_id).then(response => {
    form.value = response.data
    openRecordDtlView.value = true
    dictType.value = 'nutrition_guidance_type'
    title.value = proxy.$t('userNutritionGuidance.viewUserNutritionGuidance')
  })
}
/** 查询营养指导列表 */
function getList() {
  loading.value = true
  listUserNutritionGuidance(queryParams.value).then(response => {
    userNutritionGuidanceList.value = response.rows
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
    guidanceDesc: null,
    status: null,
    createdTime: null,
    updatedTime: null,
    userId: null
  }
  proxy.resetForm("userNutritionGuidanceRef")
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
  addFlag.value = true
  listUser().then(response => {
    userList.value = response.rows
  })
  if (proxy.$route.query.userId) {
    form.value.userId = Number(proxy.$route.query.userId)
  }

  title.value = proxy.$t('userNutritionGuidance.addUserNutritionGuidance')
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getUserNutritionGuidance(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = proxy.$t('userNutritionGuidance.updateUserNutritionGuidance')
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["userNutritionGuidanceRef"].validate(valid => {
    if (valid) {
      form.value.knowledgeBaseIds = form.value.knowledgeBaseIds ? form.value.knowledgeBaseIds.join(',') : '';
      if (form.value.id != null) {
        updateUserNutritionGuidance(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t('userNutritionGuidance.updateUserNutritionGuidanceSuccess'))
          open.value = false
          getList()
        })
      } else {
        addUserNutritionGuidance(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t('userNutritionGuidance.addUserNutritionGuidanceSuccess'))
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

function getGuidanceTemplateList() {
  listNutritionGuidanceTemplate().then(response => {
    templateList.value = response.rows
  })
}
/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm(proxy.$t('userNutritionGuidance.confirmDelete', _ids)).then(function() {
    return delUserNutritionGuidance(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess(proxy.$t('userNutritionGuidance.deleteSuccess'))
  }).catch(() => {})
}

function handleguidanceDtl(row) {
  reset()
  const _id = row.id || ids.value
  nutritionGuidanceId.value = _id
  getUserNutritionGuidance(_id).then(response => {
    form.value = response.data
    dtlDialog.value = true
    dictType.value = 'nutrition_guidance_type'
    title.value = proxy.$t('userNutritionGuidance.guidanceDtlManage')
    guidanceDtlRef.value?.reload()
  })
}
/** 导出按钮操作 */
function handleExport() {
  proxy.download('ai/userNutritionGuidance/export', {
    ...queryParams.value
  }, `userNutritionGuidance_${new Date().getTime()}.xlsx`)
}

function changeTemplate(id) {
  getNutritionGuidanceTemplate(id).then(response => {
    form.value.remark = response.data.remark
    form.value.themeId = response.data.themeId
    form.value.templateCode = response.data.templateCode
    form.value.guidanceName = response.data.guidanceName
    form.value.guidanceDesc = response.data.templateDesc
    form.value.template = id
    form.value.id = null
  })

}

onMounted(() => {
  if (proxy.$route.query.userId) {
    queryParams.value.userId = Number(proxy.$route.query.userId)
  }
  handleQuery()
})
getCourseThemeList();
getGuidanceTemplateList();
</script>
