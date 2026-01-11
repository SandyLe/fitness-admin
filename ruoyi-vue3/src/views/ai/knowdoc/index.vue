<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="文档名称" prop="docName">
        <el-input
          v-model="queryParams.docName"
          placeholder="请输入文档名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="Plus"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['ai:knowdoc:add']"-->
<!--        >新增</el-button>-->
<!--      </el-col>-->
       
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['ai:knowdoc:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['ai:knowdoc:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['ai:knowdoc:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-upload v-hasPermi="['ai:knowdoc:add']"
          class="upload-demo"
          :action="apiBaseUrl + '/common/upload'"
          :headers="headers"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
          :on-success="handleUploadSuccess"
          :before-upload="beforeUpload"
          multiple
          :limit="3"
          :on-exceed="handleExceed"
          :show-file-list="false"
          accept=".txt,.pdf,.doc,.docx,.ppt,.pptx,.xls,.xlsx,.html,.htm,.xml,.md,.rtf,.odt,.ods,.odp"
        >
          <el-button type="primary">上传文档</el-button>
        </el-upload>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="knowdocList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="文档ID" align="center" prop="id" />
      <el-table-column label="知识库ID" align="center" prop="kbId" />
      <el-table-column label="文档名称" align="center" prop="docName" />
      <el-table-column label="文档类型" align="center" prop="docType" />
      <el-table-column label="文件存储路径" align="center" prop="filePath" />
      <el-table-column label="文件大小(字节)" align="center" prop="fileSize" />
      <el-table-column label="状态" align="center" prop="status" >
        <template #default="scope">
          <span v-if="scope.row.status === 0">处理中</span>
          <span v-else-if="scope.row.status === 1">完成</span>
          <span v-else>失败</span>
        </template>
      </el-table-column>
    
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['ai:knowdoc:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['ai:knowdoc:remove']">删除</el-button>
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

    <!-- 添加或修改知识库文档对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="knowdocRef" :model="form" :rules="rules" label-width="180px">
        <el-form-item label="知识库ID" prop="kbId">
          <el-input v-model="form.kbId" placeholder="请输入知识库ID" disabled />
        </el-form-item>
        <el-form-item label="文档名称" prop="docName">
          <el-input v-model="form.docName" placeholder="请输入文档名称" />
        </el-form-item>
        <el-form-item label="文档类型" prop="docType">
          <el-select v-model="form.docType" placeholder="请选择文档类型">
            <el-option label="txt" value="txt" />
            <el-option label="pdf" value="pdf" />
            <el-option label="docx" value="docx" />
          </el-select>
        </el-form-item>
        <el-form-item label="文件存储路径" prop="filePath">
          <el-input v-model="form.filePath" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="文件大小(字节)" prop="fileSize">
          <el-input v-model="form.fileSize" placeholder="请输入文件大小(字节)" />
        </el-form-item>
     
     
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="knowdoc">
import { ElLoading, ElMessage } from 'element-plus'
import { listknowdoc, getknowdoc, delknowdoc, addknowdoc, updateknowdoc } from "@/api/ai/knowdoc"
const apiBaseUrl = import.meta.env.VITE_APP_BASE_API 
import { getToken } from '@/utils/auth'
const { proxy } = getCurrentInstance()
const headers = ref({ Authorization: "Bearer " + getToken() })
const knowdocList = ref([])
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
    docName: null,
  },
  rules: {

    docName: [
      { required: true, message: "文档名称不能为空", trigger: "blur" }
    ],
    docType: [
      { required: true, message: "文档类型(txt/pdf/docx等)不能为空", trigger: "change" }
    ],
    fileSize: [
      { required: true, message: "文件大小(字节)不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "状态(0:处理中 1:完成 2:失败)不能为空", trigger: "change" }
    ],
    createTime: [
      { required: true, message: "创建时间不能为空", trigger: "blur" }
    ],
    updateTime: [
      { required: true, message: "更新时间不能为空", trigger: "blur" }
    ]
  }
})

const { queryParams, form, rules } = toRefs(data)
function handleRemove(file, fileList) {
      console.log(file, fileList);
}
function handlePreview(file) {
  console.log(file);
}
function handleExceed(files, fileList) {
  ElMessage.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
}
function beforeUpload(file) {
   const maxSize = 5 * 1024 * 1024; // 5MB
  if (file.size > maxSize) {
    ElMessage.error(`文件 ${file.name} 超过 5MB 限制`);
    return false; // 阻止上传
  }
  const allowedTypes = [
    'text/plain',
    'application/pdf',
    'application/msword',
    'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
    'application/vnd.ms-powerpoint',
    'application/vnd.openxmlformats-officedocument.presentationml.presentation',
    'application/vnd.ms-excel',
    'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
    'text/html',
    'application/xml',
    'text/markdown',
    'text/x-markdown',
    'application/rtf',
    'application/vnd.oasis.opendocument.text',
    'application/vnd.oasis.opendocument.spreadsheet',
    'application/vnd.oasis.opendocument.presentation'
  ];

  if (!allowedTypes.includes(file.type)) {
    ElMessage.error(`不支持的文件类型：${file.name}`);
    return false;
  }
  return true; 
}
function beforeRemove(file, fileList) {
  return this.$confirm(`确定移除 ${ file.name }？`);
}
function handleUploadSuccess(data, file) {
  if(data.code != 200){
    ElMessage.error(data.msg)
    return
  }
  console.log(data, file);
  let form = {
    kbId: proxy.$route.query.id,
    docName: data.newFileName,
    docType: data.newFileName.split(".")[1],
    filePath:  data.fileLocalPath,
    fileSize: file.fileSize
  }
   addknowdoc(form).then(response => {
    proxy.$modal.msgSuccess("新增成功")
    open.value = false
    getList()
  })
}
/** 查询知识库文档列表 */
function getList() {
  loading.value = true
  queryParams.value.kbId = proxy.$route.query.id
  listknowdoc(queryParams.value).then(response => {
    knowdocList.value = response.rows
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
    kbId: proxy.$route.query.id,
    docName: null,
    docType: null,
    filePath: null,
    fileSize: null,
    chunkCount: null,
    embeddingSize: null,
    maxChunkSize: null,
    status: null,
    processResult: null,
    createTime: null,
    updateTime: null
  }
  proxy.resetForm("knowdocRef")
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
  form.value.kbId = proxy.$route.query.id
  open.value = true
  title.value = "添加知识库文档"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getknowdoc(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改知识库文档"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["knowdocRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateknowdoc(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addknowdoc(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
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
  proxy.$modal.confirm('是否确认删除知识库文档编号为"' + _ids + '"的数据项？').then(function() {
    return delknowdoc(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('ai/knowdoc/export', {
    ...queryParams.value
  }, `knowdoc_${new Date().getTime()}.xlsx`)
}

getList()
</script>
