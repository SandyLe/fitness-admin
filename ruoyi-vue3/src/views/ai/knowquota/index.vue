<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px" @submit.prevent> <!--  submit.prevent 阻止回车刷新页面 -->
      <el-form-item :label="$t('knowQuota.userName')" prop="userId">
        <el-input
            v-model="queryParams.createBy"
            :placeholder="$t('knowQuota.userNameDec')"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">{{ $t('common.search') }}</el-button>
        <el-button icon="Refresh" @click="resetQuery">{{ $t('common.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--            type="primary"-->
<!--            plain-->
<!--            icon="Plus"-->
<!--            @click="handleAdd"-->
<!--            v-hasPermi="['ai:knowquota:add']"-->
<!--        >新增</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['ai:knowquota:edit']"
        >{{ $t('common.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['ai:knowquota:remove']"
        >{{ $t('common.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['ai:knowquota:export']"
        >{{ $t('common.export') }}</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="knowquotaList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('knowQuota.quotaId')" align="center" prop="quotaId" />
      <el-table-column :label="$t('knowQuota.owner')" align="center" prop="createBy" />
<!--      <el-table-column label="最大存储限额" align="center" prop="maxStorageBytes" />-->
<!--      <el-table-column label="最多可创建知识库数量，默认10个" align="center" prop="maxKbCount" />-->
      <el-table-column :label="$t('knowQuota.knowledgeBaseSize')" align="center" prop="usedStorageBytes" >
        <template #default="scope">
          <el-tag>{{ formatBytes(scope.row.usedStorageBytes) }} / {{ formatBytes(scope.row.maxStorageBytes) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('knowQuota.knowledgeBaseCount')" align="center" prop="usedKbCount" >
        <template #default="scope">
          <el-tag>{{ scope.row.usedKbCount }} / {{ scope.row.maxKbCount }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.operation')" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['ai:knowquota:edit']">{{ $t('common.edit') }}</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['ai:knowquota:remove']">{{ $t('common.delete') }}</el-button>
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

    <!-- 添加或修改用户知识库配额对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="knowquotaRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item :label="$t('knowQuota.owner')" prop="createBy">
          <el-input v-model="form.createBy"  readonly/>
        </el-form-item>
        <el-form-item :label="$t('knowQuota.knowledgeBaseSize')" prop="maxStorageBytes">
          <el-input v-model="form.maxStorageBytes" :placeholder="$t('knowQuota.knowledgeBaseSizeDesc')" />
        </el-form-item>
        <el-form-item :label="$t('knowQuota.knowledgeBaseCount')" prop="maxKbCount">
          <el-input v-model="form.maxKbCount" :placeholder="$t('knowQuota.knowledgeBaseCountDesc')" />
        </el-form-item>
<!--        <el-form-item label="已用存储" prop="usedStorageBytes">-->
<!--          <el-input v-model="form.usedStorageBytes" placeholder="请输入已用存储" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="已创建知识库数量" prop="usedKbCount">-->
<!--          <el-input v-model="form.usedKbCount" placeholder="请输入已创建知识库数量" />-->
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

<script setup name="Knowquota">
import { listKnowquota, getKnowquota, delKnowquota, addKnowquota, updateKnowquota } from "@/api/ai/knowledgeQuota"

const { proxy } = getCurrentInstance()

const knowquotaList = ref([])
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
    userId: null,
    maxStorageBytes: null,
    maxKbCount: null,
    usedStorageBytes: null,
    usedKbCount: null,
  },
  rules: {
    // userId: [
    //   { required: true, message: "用户ID不能为空", trigger: "blur" }
    // ],
    // maxStorageBytes: [
    //   { required: true, message: "最大存储限额不能为空", trigger: "blur" }
    // ],
    // maxKbCount: [
    //   { required: true, message: "最多可创建知识库数量，默认10个不能为空", trigger: "blur" }
    // ],
    // usedStorageBytes: [
    //   { required: true, message: "已用存储不能为空", trigger: "blur" }
    // ],
    // usedKbCount: [
    //   { required: true, message: "已创建知识库数量不能为空", trigger: "blur" }
    // ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询用户知识库配额列表 */
function getList() {
  loading.value = true
  listKnowquota(queryParams.value).then(response => {
    knowquotaList.value = response.rows
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
    quotaId: null,
    userId: null,
    maxStorageBytes: null,
    maxKbCount: null,
    usedStorageBytes: null,
    usedKbCount: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null
  }
  proxy.resetForm("knowquotaRef")
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
  ids.value = selection.map(item => item.quotaId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = proxy.$t("knowQuota.add")
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _quotaId = row.quotaId || ids.value
  getKnowquota(_quotaId).then(response => {
    form.value = response.data
    open.value = true
    title.value = proxy.$t("knowQuota.update")
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["knowquotaRef"].validate(valid => {
    if (valid) {
      if (form.value.quotaId != null) {
        updateKnowquota(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t("common.updateSuccess"))
          open.value = false
          getList()
        })
      } else {
        addKnowquota(form.value).then(response => {
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
  const _quotaIds = row.quotaId || ids.value
  proxy.$modal.confirm(proxy.$t("knowQuota.confirmDelete") + '"' + _quotaIds + '"').then(function() {
    return delKnowquota(_quotaIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess(proxy.$t("common.deleteSuccess"))
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('ai/knowquota/export', {
    ...queryParams.value
  }, `knowquota_${new Date().getTime()}.xlsx`)
}

/** 文件大小单位转换 */
function formatBytes(bytes) {
  if (bytes === null || bytes === undefined || bytes === 0) return '0 B';
  const k = 1024;
  const sizes = ['B', 'KB', 'MB', 'GB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  return parseFloat((bytes / Math.pow(k, i)).toFixed(i > 1 ? 1 : 0)) + ' ' + sizes[i];
}

getList()
</script>
