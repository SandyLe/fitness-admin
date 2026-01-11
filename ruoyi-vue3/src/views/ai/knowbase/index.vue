<template>
  <div class="app-container">
    <!-- 知识库使用情况统计 -->
    <KnowledgeUsageStats ref="usageStatsRef"/>
    <!-- 新增或修改知识库对话框及表格 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item :label="$t('knowBase.knowledgeBaseName')" prop="kbName">
        <el-input
          v-model="queryParams.kbName"
          :placeholder="$t('knowBase.knowledgeBaseNameDec')"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('knowBase.owner')" prop="createBy" v-hasRole="['admin']">
        <el-input
          v-model="queryParams.createBy"
          :placeholder="$t('knowBase.ownerDec')"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="RedisSearch索引名" prop="redisIndex">-->
<!--        <el-input-->
<!--          v-model="queryParams.redisIndex"-->
<!--          placeholder="请输入RedisSearch索引名"-->
<!--          clearable-->
<!--          @keyup.enter="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="文档数量" prop="fileCount">-->
<!--        <el-input-->
<!--          v-model="queryParams.fileCount"-->
<!--          placeholder="请输入文档数量"-->
<!--          clearable-->
<!--          @keyup.enter="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
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
          v-hasPermi="['ai:knowbase:add']"
        >{{ $t('common.add') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          :disabled="single"
          @click="handlePush"
          v-hasPermi="['ai:knowdoc:list']"
        >{{ $t('knowBase.addKnowledgeBaseFile') }}</el-button>
      </el-col>  
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['ai:knowbase:edit']"
        >{{ $t('common.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['ai:knowbase:remove']"
        >{{ $t('common.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['ai:knowbase:export']"
        >{{ $t('common.export') }}</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="knowbaseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('knowBase.knowledgeBaseId')" align="center" prop="id" />
      <el-table-column :label="$t('knowBase.knowledgeBaseName')" align="center" prop="kbName" >
        <template #default="scope">
          <router-link :to="'/ai/knowdoc?id=' + scope.row.id" class="link-type">
            <span>{{ scope.row.kbName }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column :label="$t('knowBase.knowledgeBaseDescription')" align="center" prop="kbDescription" />
      <el-table-column :label="$t('knowBase.owner')" align="center" prop="createBy" />
<!--      <el-table-column label="RedisSearch索引名" align="center" prop="redisIndex" />-->
<!--      <el-table-column label="文档数量" align="center" prop="fileCount" />-->
      <el-table-column :label="$t('common.status')" align="center" prop="status" >
        <template #default="scope">
          {{ scope.row.status === 1 ? $t('knowBase.enabled') : $t('knowBase.disabled') }}
        </template>
      </el-table-column>
<!--      <el-table-column label="备注" align="center" prop="remark" />-->
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
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['ai:knowbase:edit']">{{ $t('common.edit') }}</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['ai:knowbase:remove']">{{ $t('common.delete') }}</el-button>
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

    <!-- 添加或修改知识库对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="knowbaseRef" :model="form" :rules="rules" label-width="180px">
        <el-form-item :label="$t('knowBase.knowledgeBaseName')" prop="kbName">
          <el-input v-model="form.kbName" :placeholder="$t('knowBase.knowledgeBaseNameDec')" />
        </el-form-item>
        <el-form-item :label="$t('knowBase.knowledgeBaseDescription')" prop="kbDescription">
          <el-input v-model="form.kbDescription" type="textarea" :placeholder="$t('knowBase.knowledgeBaseDescriptionDec')" />
        </el-form-item>
<!--        <el-form-item label="所属用户ID" prop="userId">-->
<!--          <el-input v-model="form.userId" placeholder="请输入所属用户ID" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="RedisSearch索引名" prop="redisIndex">-->
<!--          <el-input v-model="form.redisIndex" placeholder="请输入RedisSearch索引名" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="文档数量" prop="fileCount">-->
<!--          <el-input v-model="form.fileCount" placeholder="请输入文档数量" />-->
<!--        </el-form-item>-->
        <el-form-item :label="$t('knowBase.knowledgeBaseRemark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :placeholder="$t('knowBase.knowledgeBaseRemarkDec')" />
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

<script setup name="Knowbase">
import { listKnowbase, getKnowbase, delKnowbase, addKnowbase, updateKnowbase } from "@/api/ai/knowbase"
import { useRouter } from "vue-router"
import KnowledgeUsageStats from "@/views/ai/knowbase/KnowledgeUsageStats.vue";
const router = useRouter()
const { proxy } = getCurrentInstance()

const knowbaseList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const usageStatsRef = ref(null);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    kbName: null,
    kbDescription: null,
    userId: null,
    redisIndex: null,
    fileCount: null,
    status: null,
  },
  rules: {
    kbName: [
      { required: true, message: proxy.$t('knowBase.knowledgeBaseNameRequired'), trigger: "blur" }
    ],
    kbDescription: [
      { required: true, message: proxy.$t('knowBase.knowledgeBaseDescriptionRequired'), trigger: "blur" }
    ],
    // redisIndex: [
    //   { required: true, message: "RedisSearch索引名不能为空", trigger: "blur" }
    // ],
    // fileCount: [
    //   { required: true, message: "文档数量不能为空", trigger: "blur" }
    // ],
    // status: [
    //   { required: true, message: "状态(0:禁用 1:启用)不能为空", trigger: "change" }
    // ],
    // createTime: [
    //   { required: true, message: "创建时间不能为空", trigger: "blur" }
    // ],
    // updateTime: [
    //   { required: true, message: "更新时间不能为空", trigger: "blur" }
    // ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询知识库列表 */
function getList() {
  loading.value = true
  listKnowbase(queryParams.value).then(response => {
    knowbaseList.value = response.rows
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
    kbName: null,
    kbDescription: null,
    userId: null,
    redisIndex: null,
    fileCount: null,
    status: null,
    createTime: null,
    updateTime: null,
    createBy: null,
    updateBy: null,
    remark: null
  }
  proxy.resetForm("knowbaseRef")
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
/** 新增按钮操作 */
function handlePush() {
  reset()
  router.push({
    path: '/ai/knowdoc',
    query: {
      id: ids.value[0]
    }
  })
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
  title.value = proxy.$t('knowBase.addKnowledgeBase')
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getKnowbase(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = proxy.$t('knowBase.updateKnowledgeBase')
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["knowbaseRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateKnowbase(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t('common.updateSuccess'))
          open.value = false
          getList()
          refreshUsageStats(); //  刷新配额统计
        })
      } else {
        addKnowbase(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t('common.addSuccess'))
          open.value = false
          getList()
          refreshUsageStats(); //  刷新配额统计
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm(proxy.$t('knowBase.confirmDeleteKnowledgeBase') + _ids + proxy.$t('common.menuName')+'?').then(function() {
    return delKnowbase(_ids)
  }).then(() => {
    refreshUsageStats(); //  刷新配额统计
    getList()
    proxy.$modal.msgSuccess(proxy.$t('common.deleteSuccess'))
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('ai/knowbase/export', {
    ...queryParams.value
  }, `knowbase_${new Date().getTime()}.xlsx`)
}

/**
 * 刷新统计信息
 */
const refreshUsageStats = () => {
  usageStatsRef.value?.refresh?.();
};
getList()
</script>
