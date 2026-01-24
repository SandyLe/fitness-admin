<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item :label="$t('course.name')" prop="name">
        <el-input
            v-model="queryParams.name"
            :placeholder="$t('course.namePlaceholder')"
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
            v-hasPermi="['fitness:course:add']"
        >{{ $t('common.add') }}</el-button>
      </el-col>
      <!--        <el-col :span="1.5">
              <el-button
                type="primary"
                plain
                :disabled="single"
                @click="handlePush"
                v-hasPermi="['fitness:course:history']"
              >{{ $t('course.history') }}</el-button>
            </el-col>-->
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['fitness:course:edit']"
        >{{ $t('common.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['fitness:course:remove']"
        >{{ $t('common.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['fitness:course:export']"
        >{{ $t('common.export') }}</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="courseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" align="center" prop="index" width="50"/>
      <el-table-column :label="$t('course.code')" align="center" prop="code" :show-overflow-tooltip="true"/>
      <!--      <el-table-column :label="$t('course.courseId')" align="center" prop="courseId" />-->
      <el-table-column :label="$t('course.name')" align="center" prop="name" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('course.theme')" align="center" prop="themeId" :show-overflow-tooltip="true">
        <template #default="scope">
          {{ themeList.find(item => item.id === scope.row.themeId)?.themeName || '未指定' }}
        </template>
      </el-table-column>
      <el-table-column :label="$t('course.brifeIntroduction')" align="center" prop="brifeIntroduction" :show-overflow-tooltip="true">
        <template #default="scope">
          <div v-html="scope.row.brifeIntroduction.replace(/\n/g, '<br>')"></div>
        </template>
      </el-table-column>
      <!--      <el-table-column :label="$t('course.imgUrl')" align="center" prop="imgUrl" :show-overflow-tooltip="true">
              <template #default="scope">
                <img :src="scope.row.imgUrl" alt="" style="width:50px"/>
              </template>
            </el-table-column>-->
      <el-table-column :label="$t('course.duration')" align="center" prop="duration" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('course.level')" align="center" prop="level" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('course.courseAction')" align="center" prop="courseAction" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('course.groupsCount')" align="center" prop="groupsCount" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('course.actionsCount')" align="center" prop="actionsCount" :show-overflow-tooltip="true"/>
      <el-table-column :label="$t('course.courseDesc')" align="center" prop="courseDesc" >
        <template #default="scope">
          <div v-html="scope.row.courseDesc"></div>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.createdTime')" align="center" prop="createTime">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createdTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.updatedTime')" align="center" prop="updateTime">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updatedTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.updatedTime')" align="center" prop="updateTime">
        <template #default="scope">
          <AuthImg
              :src="baseApi + '/common/download/resource?resource=' + scope.row.imgUrl"
              :token="headers.Authorization"
              header-key="Authorization"
              width="100"
              height="100"
              token-prefix=""
          />

        </template>
      </el-table-column>
      <el-table-column :label="$t('common.operation')" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="View" @click="handleVisit(scope.row)" v-hasPermi="['fitness:course:edit']">{{ $t('course.visitCourse') }}</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['fitness:course:edit']">{{ $t('common.edit') }}</el-button>
          <el-button link type="primary" icon="Edit" @click="handleActionIndicator(scope.row)" v-hasPermi="['fitness:course:actionIndicator']">{{ $t('course.actionIndicator') }}</el-button>
          <el-button link type="primary" icon="Edit" @click="handleActionComment(scope.row)" v-hasPermi="['fitness:course:actionComment']">{{ $t('course.actionComment') }}</el-button>
          <el-button link type="primary" icon="Edit" @click="handleActionPoints(scope.row)" v-hasPermi="['fitness:course:actionPoints']">{{ $t('course.actionPoints') }}</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['fitness:course:remove']">{{ $t('common.delete') }}</el-button>
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

    <!-- 添加或修改课程对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="courseRef" :model="form" :rules="rules" label-width="180px">
        <el-form-item :label="$t('course.name')" prop="name">
          <el-input v-model="form.name" :placeholder="$t('course.namePlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('course.code')" prop="code">
          <el-input v-model="form.code" :placeholder="$t('course.codePlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('course.theme')" prop="themeId">
          <el-select v-model="form.themeId" :placeholder="$t('course.themePlaceholder')">
            <el-option v-for="item in themeList" :key="item.id" :label="item.themeName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('course.brifeIntroduction')" prop="brifeIntroduction">
          <el-input v-model="form.brifeIntroduction" type="textarea" :placeholder="$t('course.brifeIntroductionPlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('course.courseDesc')" prop="courseDesc">
          <el-input v-model="form.courseDesc" type="textarea" :placeholder="$t('course.courseDescPlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('course.isShowIndex')" prop="isShowIndex">
          <el-switch v-model="form.isShowIndex" class="drawer-switch" :placeholder="$t('course.isShowIndexPlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('course.level')" prop="level">
          <el-select v-model="form.level" :placeholder="$t('course.levelPlaceholder')">
            <el-option v-for="item in levelList" :key="item.id" :label="item.text" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('course.courseAction')" prop="courseAction">
          <el-input v-model="form.courseAction" :placeholder="$t('course.courseActionPlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('course.groupsCount')" prop="groupsCount">
          <el-input v-model="form.groupsCount" :placeholder="$t('course.groupsCountPlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('course.actionsCount')" prop="actionsCount">
          <el-input v-model="form.actionsCount" :placeholder="$t('course.fitnessGoalPlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('course.duration')" prop="duration">
          <el-input v-model="form.duration" :placeholder="$t('course.durationPlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('course.imgUrl')" prop="imgUrl">
          <el-upload
              ref="imgUrlRef"
              class="avatar-uploader"
              :action="uploadUrl"
              :show-file-list="false"
              :on-success="importImgUrl"
              :accept="'.png,.jpg,.jpeg'"
              :headers="headers"
          >
            <el-button size="small" type="primary">{{ $t('common.import') }}</el-button>
          </el-upload>
          <AuthImg
              :src="baseApi + '/common/download/resource?resource=' + form.imgUrl"
              :token="headers.Authorization"
              header-key="Authorization"
              width="20"
              height="20"
              fit="cover"
              token-prefix=""
          />
        </el-form-item>
        <el-form-item :label="$t('course.videoUrl')" prop="videoUrl">
          <el-input v-model="form.systemPrompt" type="hidden" :placeholder="$t('course.videoUrlPlaceholder')" />
          <el-upload
              ref="videoUrlRef"
              class="avatar-uploader"
              :action="uploadUrl"
              :show-file-list="false"
              :on-success="importVideoUrl"
              :accept="'.mp4,.avi,.rmv,rmvb,3gp'"
              :headers="headers"
          >
            <el-button size="small" type="primary">{{ $t('common.import') }}</el-button>
          </el-upload>
          <AuthVideo
              :src="baseApi + '/common/download/resource?resource=' + form.videoUrl"
              :poster="baseApi + '/common/download/resource?resource=' + form.imgUrl"
              :token="headers.Authorization"
              width="240"
              height="135"
              autoplay
              muted
              loop
          />
        </el-form-item>
        <!--        <el-form-item :label="$t('course.knowledgeBase')" prop="knowledgeBaseIds">
                  <el-select v-model="form.knowledgeBaseIds" multiple :placeholder="$t('course.knowledgeBasePlaceholder')">
                    <el-option v-for="item in knowbaseList" :key="item.id" :label="item.kbName" :value="item.id" />
                  </el-select>
                </el-form-item>
                -->
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

    <!-- 课程详细 -->
    <el-dialog :title="title" v-model="openView" width="700px" append-to-body>
      <el-form :model="form" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('course.name')">{{ form.name }}</el-form-item>
            <el-form-item :label="$t('course.theme')">
              <div>{{ themeList.find(item => item.id === form.themeId)?.themeName || '未指定' }}</div>
            </el-form-item>
            <el-form-item :label="$t('course.level')">{{ form.level }}</el-form-item>
            <el-form-item :label="$t('course.groupsCount')">{{ form.groupsCount }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('course.code')">{{ form.code }}</el-form-item>
            <el-form-item :label="$t('course.isShowIndex')">
              <div v-if="form.isShowIndex == '1'">{{ $t('common.yes') }}</div>
              <div v-else-if="form.isShowIndex == '0'">{{ $t('common.no') }}</div>
            </el-form-item>
            <el-form-item :label="$t('course.courseAction')">{{ form.courseAction }}</el-form-item>
            <el-form-item :label="$t('course.actionsCount')">{{ form.actionsCount }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item :label="$t('course.brifeIntroduction')">{{ form.brifeIntroduction }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item :label="$t('course.courseDesc')">{{ form.courseDesc }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="openView = false">{{ $t('common.close') }}</el-button>
        </div>
      </template>
    </el-dialog>
    <!-- 课程动作标准 -->
    <el-dialog :title="title" v-model="indicatorDialog" width="1024px" append-to-body>
      <CrudTable
          title="课课动作指标"
          ref="indicatorRef"
          :columns="indicatorColumns"
          :hidden-params="{ courseId }"
          row-key = "actionIndicatorId"
          :list-request="listCourseActionIndicator"
          :add-request="addCourseActionIndicator"
          :update-request="updateCourseActionIndicator"
          :delete-request="delCourseActionIndicator"
      />
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="indicatorDialog = false">{{ $t('common.close') }}</el-button>
        </div>
      </template>
    </el-dialog>
    <!-- 课程动作评估 -->
    <el-dialog :title="title" v-model="commentDialog" width="1024px" append-to-body>
      <CrudTable
          title="课课动作评估"
          ref="commentRef"
          :columns="commentColumns"
          :hidden-params="{ courseId }"
          row-key = "actionCommentId"
          :list-request="listCourseActionComment"
          :add-request="addCourseActionComment"
          :update-request="updateCourseActionComment"
          :delete-request="delCourseActionComment"
      />
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="commentDialog = false">{{ $t('common.close') }}</el-button>
        </div>
      </template>
    </el-dialog>
    <!-- 课程动作要点 -->
    <el-dialog :title="title" v-model="pointDialog" width="1024px" append-to-body>
      <CrudTable
          title="课课动作要点"
          ref="pointRef"
          :columns="pointColumns"
          :hidden-params="{ courseId }"
          row-key = "actionPointsId"
          :list-request="listCourseActionPoints"
          :add-request="addCourseActionPoints"
          :update-request="updateCourseActionPoints"
          :delete-request="delCourseActionPoints"
      />
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="pointDialog = false">{{ $t('common.close') }}</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="course">
import { getToken } from "@/utils/auth"
import {
  listCourse,
  getCourse,
  delCourse,
  addCourse,
  updateCourse,
  listCourseActionIndicator,
  addCourseActionIndicator,
  updateCourseActionIndicator,
  delCourseActionIndicator,
  getCourseActionIndicator,
  getCourseActionComment,
  listCourseActionComment,
  addCourseActionComment,
  updateCourseActionComment,
  delCourseActionComment,
  getCourseActionPoints,
  listCourseActionPoints,
  addCourseActionPoints,
  updateCourseActionPoints,
  delCourseActionPoints
} from "@/api/fitness/course"
import { listCourseTheme } from "@/api/fitness/courseTheme"
import AuthImg from "@/components/AuthImg"
import AuthVideo from "@/components/AuthVideo"
import CrudTable from "@/components/CrudTable"

const headers = ref({ Authorization: "Bearer " + getToken() })
const router = useRouter()
const { proxy } = getCurrentInstance()
const baseApi = import.meta.env.VITE_APP_BASE_API

const courseList = ref([])
const list = ref([])
const themeList = ref([])
const levelList = [{"id":"初级","text":"初级"},{"id":"中级","text":"中级"},{"id":"高级","text":"高级"}]
const open = ref(false)
const openView = ref(false)
const indicatorDialog = ref(false)
const commentDialog = ref(false)
const pointDialog = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const uploadUrl = ref(import.meta.env.VITE_APP_BASE_API + "/common/upload") // 上传的文件服务器地址
const courseId = ref("")
const indicatorRef = ref(null)
const commentRef = ref(null)
const pointRef = ref(null)

const indicatorColumns = [{ label: '动作指标名称', prop: 'actionPoints', editable: true },
  { label: '动作指标编码', prop: 'actionPointsCode', editable: true },
  { label: '点位1', prop: 'point1', editable: true },
  { label: '点位2', prop: 'point2', editable: true },
  { label: '点位3', prop: 'point3', editable: true },
  { label: '标准值', prop: 'standardValue', editable: true },
  { label: '开始值', prop: 'startValue', editable: true },
  { label: '结束值', prop: 'endValue', editable: true }]
const commentColumns = [{ label: '动作指标', prop: 'indicatorId', editable: true, editor: 'select',
  optionsRequest: listCourseActionIndicator, optionsFormatter: (item) => ({
    label: item.actionPoints,
    value: item.actionIndicatorId
  })},
  { label: '大于或小于指标', prop: 'lessOrMore', editable: true },
  { label: '指标值', prop: 'standardValue', editable: true },
  { label: '动作指标评估名称', prop: 'actionCommentTitle', editable: true },
  { label: '动作指标评价', prop: 'actionCommentDesc', editable: true },
  { label: '动作指标建议', prop: 'suggestions', editable: true }]
const pointColumns = [{ label: '动作要点', prop: 'actionPoints', editable: true }]
const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    nickName: null,
  },
  rules: {
    nickName: [
      { required: true, message: proxy.$t('course.namePlaceholder'), trigger: "blur" }
    ],
    modelId: [
      { required: true, message: proxy.$t('course.modelPlaceholder'), trigger: "blur" }
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
  const _id = row.courseId || ids.value
  getCourse(_id).then(response => {
    form.value = response.data
    openView.value = true
    title.value = proxy.$t('course.viewCourse')
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
/** 查询课程列表 */
function getList() {
  loading.value = true
  listCourse(queryParams.value).then(response => {
    courseList.value = response.rows
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
    courseId: null,
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
  proxy.resetForm("courseRef")
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
function getCourseThemeList() {
  listCourseTheme().then(response => {
    themeList.value = response.rows
  })
}
/** 导入课程封面 */
function importImgUrl(res,file) {
  //上传img文件 获取文件内容赋值给imgUrl
  if (res.code === 200) {
    form.value.imgUrl = res.fileName
  } else {
    proxy.$modal.msgError(res.msg)
    proxy.$refs.imgUrlRef.handleRemove(file)
  }
}
/** 导入课程教学视频 */
function importVideoUrl(res,file) {
  //上传video文件 获取文件内容赋值给videoUrl
  if (res.code === 200) {
    form.value.videoUrl = res.fileName
  } else {
    proxy.$modal.msgError(res.msg)
    proxy.$refs.videoUrlRef.handleRemove(file)
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
  title.value = proxy.$t('course.addCourse')
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.courseId || ids.value
  getCourse(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = proxy.$t('course.updateCourse')
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["courseRef"].validate(valid => {
    if (valid) {
      form.value.knowledgeBaseIds = form.value.knowledgeBaseIds ? form.value.knowledgeBaseIds.join(',') : '';
      if (form.value.courseId != null) {
        updateCourse(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t('course.updateCourseSuccess'))
          open.value = false
          getList()
        })
      } else {
        addCourse(form.value).then(response => {
          proxy.$modal.msgSuccess(proxy.$t('course.addCourseSuccess'))
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.courseId || ids.value
  proxy.$modal.confirm(proxy.$t('course.confirmDelete', _ids)).then(function() {
    return delCourse(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess(proxy.$t('course.deleteSuccess'))
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('ai/course/export', {
    ...queryParams.value
  }, `course${new Date().getTime()}.xlsx`)
}

function handleActionIndicator(row) {
  reset()
  const _id = row.courseId || ids.value
  courseId.value = _id
  getCourse(_id).then(response => {
    form.value = response.data
    indicatorDialog.value = true
    title.value = proxy.$t('course.actionIndicator')
    indicatorRef.value?.reload()
  })
}

function handleActionComment(row) {
  reset()
  const _id = row.courseId || ids.value
  courseId.value = _id
  getCourse(_id).then(response => {
    form.value = response.data
    commentDialog.value = true
    title.value = proxy.$t('course.actionComment')
    commentRef.value?.reload()
  })
}


function handleActionPoints(row) {
  reset()
  const _id = row.courseId || ids.value
  courseId.value = _id
  getCourse(_id).then(response => {
    form.value = response.data
    pointDialog.value = true
    title.value = proxy.$t('course.actionPoints')
    pointRef.value?.reload()
  })
}

function getCourseIndicators(params){
  return listCourse(queryParams.value).then(response => {
    list.value = response.rows
  })
}

function addCourseIndicator(params){

}
function updateCourseIndicator(params){
    console.log("i99999999999999")
}
function deleteCourseIndicator(params){

}

getCourseThemeList()
getList();
</script>
