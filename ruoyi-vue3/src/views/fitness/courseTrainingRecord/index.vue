<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item :label="$t('courseTrainingRecord.course')" prop="course">
        <el-input
          v-model="queryParams.course"
          :placeholder="$t('courseTrainingRecord.coursePlaceholder')"
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
<!--      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['ai:courseTrainingRecord:add']"
        >{{ $t('common.add') }}</el-button>
      </el-col>
&lt;!&ndash;        <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          :disabled="single"
          @click="handlePush"
          v-hasPermi="['ai:courseTrainingRecord:history']"
        >{{ $t('courseTrainingRecord.history') }}</el-button>
      </el-col>&ndash;&gt;
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['ai:courseTrainingRecord:edit']"
        >{{ $t('common.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['ai:courseTrainingRecord:remove']"
        >{{ $t('common.delete') }}</el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['ai:courseTrainingRecord:export']"
        >{{ $t('common.export') }}</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="courseTrainingRecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" align="center" prop="index" width="50"/>
      <el-table-column :label="$t('courseTrainingRecord.batchNo')" align="center" prop="batchNo" />
      <el-table-column :label="$t('courseTrainingRecord.course')" align="center" prop="course" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('courseTrainingRecord.user')" align="center" prop="user" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('courseTrainingRecord.source')" align="center" prop="source" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('courseTrainingRecord.analysis')" align="center" prop="analysis" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('courseTrainingRecord.advice')" align="center" prop="advice" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('courseTrainingRecord.startTime')" align="center" prop="startTime" >
        <template #default="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.operation')" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="View" @click="handleVisit(scope.row)" v-hasPermi="['ai:courseTrainingRecord:edit']">{{ $t('courseTrainingRecord.visitcourseTrainingRecord') }}</el-button>
<!--          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['ai:courseTrainingRecord:edit']">{{ $t('common.edit') }}</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['ai:courseTrainingRecord:remove']">{{ $t('common.delete') }}</el-button>-->
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
      <el-form ref="courseTrainingRecordRef" :model="form" :rules="rules" label-width="180px">
        <el-form-item :label="$t('courseTrainingRecord.userName')" prop="userName">
          <el-input v-model="form.userName" :placeholder="$t('courseTrainingRecord.userName')" />
        </el-form-item>
        <el-form-item :label="$t('courseTrainingRecord.nickName')" prop="nickName">
          <el-input v-model="form.nickName" :placeholder="$t('courseTrainingRecord.nickNamePlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('courseTrainingRecord.age')" prop="age">
          <el-input v-model="form.age" :placeholder="$t('courseTrainingRecord.nickNamePlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('courseTrainingRecord.gender')" prop="gender">
          <el-select v-model="form.gender" :placeholder="$t('courseTrainingRecord.modelPlaceholder')">
            <el-option v-for="item in genderList" :key="item.value" :label="item.text" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('courseTrainingRecord.height')" prop="height">
          <el-input v-model="form.height" :placeholder="$t('courseTrainingRecord.heightPlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('courseTrainingRecord.weight')" prop="weight">
          <el-input v-model="form.weight" :placeholder="$t('courseTrainingRecord.weightPlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('courseTrainingRecord.email')" prop="email">
          <el-input v-model="form.email" :placeholder="$t('courseTrainingRecord.emailPlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('courseTrainingRecord.phone')" prop="phone">
          <el-input v-model="form.phone" :placeholder="$t('courseTrainingRecord.phonePlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('courseTrainingRecord.introduce')" prop="introduce">
          <el-input v-model="form.introduce" type="textarea" :placeholder="$t('courseTrainingRecord.introduce')" />
        </el-form-item>
        <el-form-item :label="$t('courseTrainingRecord.fitnessGoal')" prop="fitnessGoal">
          <el-input v-model="form.fitnessGoal" type="textarea" :placeholder="$t('courseTrainingRecord.fitnessGoal')" />
        </el-form-item>
<!--        <el-form-item :label="$t('courseTrainingRecord.knowledgeBase')" prop="knowledgeBaseIds">
          <el-select v-model="form.knowledgeBaseIds" multiple :placeholder="$t('courseTrainingRecord.knowledgeBasePlaceholder')">
            <el-option v-for="item in knowbaseList" :key="item.id" :label="item.kbName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('courseTrainingRecord.systemPrompt')" prop="systemPrompt">
          <el-input v-model="form.systemPrompt" type="textarea" :placeholder="$t('courseTrainingRecord.systemPromptPlaceholder')" />
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
          
        </el-form-item>-->
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

    <!-- 训练记录详细 -->
    <el-dialog :title="title" v-model="openRecordDtlView" width="1400px" append-to-body>
      <CrudTable
          title="训练记录明细"
          ref="recordRef"
          no-editing= true
          :columns="recordDtlColumns"
          :hidden-params="{ courseId, batchNo }"
          row-key = "recordId"
          :list-request="listCourseTrainingRecord"
      />
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="openRecordDtlView = false">{{ $t('common.close') }}</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="courseTrainingRecord">
import { listCourseTrainingRecord, listTrainingRecordData, getCourseTrainingRecord, delCourseTrainingRecord, addCourseTrainingRecord, updateCourseTrainingRecord } from "@/api/fitness/courseTrainingRecord"
import CrudTable from "@/components/CrudTable"

const recordDtlColumns = [{ label: '课程', prop: 'name'},
  { label: '动作指标', prop: 'actionPoints'},
  { label: '训练值', prop: 'actionPointsValue'},
  { label: '组数', prop: 'groupsNum'},
  { label: '动作次数', prop: 'actionsNum'},
  { label: '开始时间', prop: 'startTime'},
  { label: '结束时间', prop: 'endTime'},
  { label: '评论', prop: 'actionCommentDesc'},
  { label: '建议', prop: 'suggestions'}]
const router = useRouter()
const { proxy } = getCurrentInstance()

const courseTrainingRecordList = ref([])
const open = ref(false)
const openRecordDtlView = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const courseId = ref(0)
const batchNo = ref("")
const uploadUrl = ref(import.meta.env.VITE_APP_BASE_API + "/common/upload") // 上传的文件服务器地址
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
      { required: true, message: proxy.$t('courseTrainingRecord.nickNamePlaceholder'), trigger: "blur" }
    ],
    modelId: [
      { required: true, message: proxy.$t('courseTrainingRecord.modelPlaceholder'), trigger: "blur" }
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
const genderList = [{'value':'male', 'text': proxy.$t('courseTrainingRecord.male')}, {'value':'female', 'text': proxy.$t('courseTrainingRecord.female')}]
const knowbaseList = ref([])
const { queryParams, form, rules } = toRefs(data)

/** 查看详情 */
function handleVisit(row) {
  reset()
  courseId.value = row.courseId
  batchNo.value = row.batchNo
  openRecordDtlView.value = true
  title.value = proxy.$t('courseTrainingRecord.viewcourseTrainingRecord')
  recordRef.value?.reload()
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
  listTrainingRecordData(queryParams.value).then(response => {
    courseTrainingRecordList.value = response.rows
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
    nickName: null,
    userName: null,
    phone: null,
    age: null,
    gender: null,
    systemPrompt: null,
    temperature: null,
    maxTokens: null,
    status: null,
    createdTime: null,
    updatedTime: null
  }
  proxy.resetForm("courseTrainingRecordRef")
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
  title.value = proxy.$t('courseTrainingRecord.addcourseTrainingRecord')
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getcourseTrainingRecord(_id).then(response => {
    form.value = response.data
    form.value.knowledgeBaseIds = form.value.knowledgeBaseIds ? form.value.knowledgeBaseIds.split(',').map(Number) : [];
    open.value = true
    title.value = proxy.$t('courseTrainingRecord.updatecourseTrainingRecord')
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["courseTrainingRecordRef"].validate(valid => {
    if (valid) {
      form.value.knowledgeBaseIds = form.value.knowledgeBaseIds ? form.value.knowledgeBaseIds.join(',') : '';
      if (form.value.id != null) {
        updatecourseTrainingRecord(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t('courseTrainingRecord.updatecourseTrainingRecordSuccess'))
          open.value = false
          getList()
        })
      } else {
        addcourseTrainingRecord(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t('courseTrainingRecord.addcourseTrainingRecordSuccess'))
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
  proxy.$modal.confirm(proxy.$t('courseTrainingRecord.confirmDelete', _ids)).then(function() {
    return delcourseTrainingRecord(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess(proxy.$t('courseTrainingRecord.deleteSuccess'))
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('ai/courseTrainingRecord/export', {
    ...queryParams.value
  }, `courseTrainingRecord_${new Date().getTime()}.xlsx`)
}
getList()
const getKnowledgeBaseNames = computed(() => (knowledgeBaseIds) => {
  console.log('knowledgeBaseIds',knowledgeBaseIds, knowbaseList.value)
  if (!knowledgeBaseIds) return proxy.$t('courseTrainingRecord.noKnowledgeBase');
  return knowledgeBaseIds
    .split(',')
    .map(id => id.trim())
    .map(id => knowbaseList.value.find(item => item.id === Number(id))?.kbName || proxy.$t('courseTrainingRecord.unknownKnowledgeBase'))
    .join(', ');
});
</script>
