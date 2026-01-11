<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item :label="$t('userInfo.nickName')" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          :placeholder="$t('userInfo.nickNamePlaceholder')"
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
          v-hasPermi="['ai:userInfo:add']"
        >{{ $t('common.add') }}</el-button>
      </el-col>
<!--        <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          :disabled="single"
          @click="handlePush"
          v-hasPermi="['ai:userInfo:history']"
        >{{ $t('userInfo.history') }}</el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['ai:userInfo:edit']"
        >{{ $t('common.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['ai:userInfo:remove']"
        >{{ $t('common.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['ai:userInfo:export']"
        >{{ $t('common.export') }}</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="userInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" align="center" prop="index" width="50"/>
      <el-table-column :label="$t('userInfo.id')" align="center" prop="id" />
      <el-table-column :label="$t('userInfo.nickName')" align="center" prop="nickName" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('userInfo.userName')" align="center" prop="userName" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('userInfo.gender')" align="center" prop="gender" :show-overflow-tooltip="true">
        <template #default="scope">
          {{  scope.row.gender === 'male'? $t('userInfo.male') : $t('userInfo.female') }}
        </template>
      </el-table-column>
      <el-table-column :label="$t('userInfo.age')" align="center" prop="age" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('userInfo.phone')" align="center" prop="phone" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('userInfo.email')" align="center" prop="email" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('userInfo.status')" align="center" prop="status" >
        <template #default="scope">
          {{ scope.row.status === 1 ? $t('userInfo.statusYes') : $t('userInfo.statusNo') }}
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.createdTime')" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createdTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.updatedTime')" align="center" prop="updateTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updatedTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.operation')" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
           <el-button link type="primary" icon="View" @click="handleVisit(scope.row)" v-hasPermi="['ai:userInfo:edit']">{{ $t('userInfo.visitUserInfo') }}</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['ai:userInfo:edit']">{{ $t('common.edit') }}</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['ai:userInfo:remove']">{{ $t('common.delete') }}</el-button>
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
      <el-form ref="userInfoRef" :model="form" :rules="rules" label-width="180px">
        <el-form-item :label="$t('userInfo.userName')" prop="userName">
          <el-input v-model="form.userName" :placeholder="$t('userInfo.userName')" />
        </el-form-item>
        <el-form-item :label="$t('userInfo.nickName')" prop="nickName">
          <el-input v-model="form.nickName" :placeholder="$t('userInfo.nickNamePlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('userInfo.age')" prop="age">
          <el-input v-model="form.age" :placeholder="$t('userInfo.nickNamePlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('userInfo.gender')" prop="gender">
          <el-select v-model="form.gender" :placeholder="$t('userInfo.modelPlaceholder')">
            <el-option v-for="item in genderList" :key="item.value" :label="item.text" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('userInfo.height')" prop="height">
          <el-input v-model="form.height" :placeholder="$t('userInfo.heightPlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('userInfo.weight')" prop="weight">
          <el-input v-model="form.weight" :placeholder="$t('userInfo.weightPlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('userInfo.email')" prop="email">
          <el-input v-model="form.email" :placeholder="$t('userInfo.emailPlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('userInfo.phone')" prop="phone">
          <el-input v-model="form.phone" :placeholder="$t('userInfo.phonePlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('userInfo.introduce')" prop="introduce">
          <el-input v-model="form.introduce" type="textarea" :placeholder="$t('userInfo.introduce')" />
        </el-form-item>
        <el-form-item :label="$t('userInfo.fitnessGoal')" prop="fitnessGoal">
          <el-input v-model="form.fitnessGoal" type="textarea" :placeholder="$t('userInfo.fitnessGoal')" />
        </el-form-item>
<!--        <el-form-item :label="$t('userInfo.knowledgeBase')" prop="knowledgeBaseIds">
          <el-select v-model="form.knowledgeBaseIds" multiple :placeholder="$t('userInfo.knowledgeBasePlaceholder')">
            <el-option v-for="item in knowbaseList" :key="item.id" :label="item.kbName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('userInfo.systemPrompt')" prop="systemPrompt">
          <el-input v-model="form.systemPrompt" type="textarea" :placeholder="$t('userInfo.systemPromptPlaceholder')" />
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

    <!-- 任务日志详细 -->
    <el-dialog :title="title" v-model="openView" width="700px" append-to-body>
      <el-form :model="form" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('userInfo.userName')">{{ form.userName }}</el-form-item>
            <el-form-item :label="$t('userInfo.gender')">
              <div v-if="form.gender == 'male'">{{ $t('userInfo.male') }}</div>
              <div v-else-if="form.gender == 'female'">{{ $t('userInfo.female') }}</div>
            </el-form-item>
            <el-form-item :label="$t('userInfo.height')">{{ form.height }}</el-form-item>
            <el-form-item :label="$t('userInfo.email')">{{ form.email }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('userInfo.nickName')">{{ form.nickName }}</el-form-item>
            <el-form-item :label="$t('userInfo.age')">{{ form.age }}</el-form-item>
            <el-form-item :label="$t('userInfo.weight')">{{ form.weight }}</el-form-item>
            <el-form-item :label="$t('userInfo.phone')">{{ form.phone }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item :label="$t('userInfo.introduce')">{{ form.introduce }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item :label="$t('userInfo.fitnessGoal')">{{ form.fitnessGoal }}</el-form-item>
          </el-col>
          <!--          <el-col :span="12">
                      <el-form-item :label="$t('userInfo.cronExpression')">{{ form.cronExpression }}</el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item :label="$t('userInfo.nextValidTime')">{{ parseTime(form.nextValidTime) }}</el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-form-item :label="$t('userInfo.invokeTarget')">{{ form.invokeTarget }}</el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item :label="$t('userInfo.status')">
                        <div v-if="form.status == 0">{{ $t('userInfo.normal') }}</div>
                        <div v-else-if="form.status == 1">{{ $t('userInfo.paused') }}</div>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item :label="$t('userInfo.concurrent')">
                        <div v-if="form.concurrent == 0">{{ $t('userInfo.allowConcurrent') }}</div>
                        <div v-else-if="form.concurrent == 1">{{ $t('userInfo.disallowConcurrent') }}</div>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item :label="$t('job.misfirePolicy')">
                        <div v-if="form.misfirePolicy == 0">{{ $t('userInfo.defaultPolicy') }}</div>
                        <div v-else-if="form.misfirePolicy == 1">{{ $t('userInfo.immediateExecution') }}</div>
                        <div v-else-if="form.misfirePolicy == 2">{{ $t('userInfo.executeOnce') }}</div>
                        <div v-else-if="form.misfirePolicy == 3">{{ $t('userInfo.giveUpExecution') }}</div>
                      </el-form-item>
                    </el-col>-->
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="openView = false">{{ $t('common.close') }}</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="userInfo">
import { listUser, getUserInfo, delUserInfo, addUserInfo, updateUserInfo } from "@/api/fitness/user"
import { listModel } from "@/api/ai/model"
import { listKnowbase } from "@/api/ai/knowbase"

const router = useRouter()
const { proxy } = getCurrentInstance()

const userInfoList = ref([])
const open = ref(false)
const openView = ref(false)
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
    nickName: null,
  },
  rules: {
    nickName: [
      { required: true, message: proxy.$t('userInfo.nickNamePlaceholder'), trigger: "blur" }
    ],
    modelId: [
      { required: true, message: proxy.$t('userInfo.modelPlaceholder'), trigger: "blur" }
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
const genderList = [{'value':'male', 'text': proxy.$t('userInfo.male')}, {'value':'female', 'text': proxy.$t('userInfo.female')}]
const knowbaseList = ref([])
const { queryParams, form, rules } = toRefs(data)

/** 查看详情 */
function handleVisit(row) {
  reset()
  const _id = row.id || ids.value
  getUserInfo(_id).then(response => {
    form.value = response.data
    openView.value = true
    title.value = proxy.$t('userInfo.viewUserInfo')
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
  listUser(queryParams.value).then(response => {
    userInfoList.value = response.rows
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
  proxy.resetForm("userInfoRef")
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
  title.value = proxy.$t('userInfo.addUserInfo')
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getUserInfo(_id).then(response => {
    form.value = response.data
    form.value.knowledgeBaseIds = form.value.knowledgeBaseIds ? form.value.knowledgeBaseIds.split(',').map(Number) : [];
    open.value = true
    title.value = proxy.$t('userInfo.updateUserInfo')
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["userInfoRef"].validate(valid => {
    if (valid) {
      form.value.knowledgeBaseIds = form.value.knowledgeBaseIds ? form.value.knowledgeBaseIds.join(',') : '';
      if (form.value.id != null) {
        updateUserInfo(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t('userInfo.updateUserInfoSuccess'))
          open.value = false
          getList()
        })
      } else {
        addUserInfo(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t('userInfo.addUserInfoSuccess'))
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
  proxy.$modal.confirm(proxy.$t('userInfo.confirmDelete', _ids)).then(function() {
    return delUserInfo(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess(proxy.$t('userInfo.deleteSuccess'))
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('ai/userInfo/export', {
    ...queryParams.value
  }, `userInfo_${new Date().getTime()}.xlsx`)
}
getList()
const getKnowledgeBaseNames = computed(() => (knowledgeBaseIds) => {
  console.log('knowledgeBaseIds',knowledgeBaseIds, knowbaseList.value)
  if (!knowledgeBaseIds) return proxy.$t('userInfo.noKnowledgeBase');
  return knowledgeBaseIds
    .split(',')
    .map(id => id.trim())
    .map(id => knowbaseList.value.find(item => item.id === Number(id))?.kbName || proxy.$t('userInfo.unknownKnowledgeBase'))
    .join(', ');
});
</script>
