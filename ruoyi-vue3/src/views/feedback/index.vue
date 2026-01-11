<template>
  <div class="app-container home">
    <h2>{{$t("feedback.feedbackTitle")}}</h2>
    <p>{{$t("feedback.feedbackDesc")}}</p>
    <el-row  style="margin-top: 40px;">
      
      <el-col :sm="24" :lg="24">
       <el-card class="box">
           <el-form :model="form" :rules="rules" ref="formRef" label-width="120px" label-position="top">
              <el-form-item :label="$t('feedback.title')" prop="feedbackTitle">
                <el-input v-model="form.feedbackTitle" :placeholder="$t('feedback.placeholder')"  maxlength="100"
    show-word-limit/>
              </el-form-item>
             
              <el-form-item :label="$t('feedback.detailContent')" prop="feedbackContent">
                <el-input v-model="form.feedbackContent" type="textarea" :placeholder="$t('feedback.content')"  :rows="4"  maxlength="5000" show-word-limit  class="fixed-textarea"
                />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitForm" style="width: 100%;height: 40px;">{{$t("feedback.submit")}}</el-button>
              </el-form-item>
            </el-form>
       </el-card>
      </el-col>
    </el-row>
    </div>
</template>

<script setup name="Index">
import { ref } from 'vue'
const value = ref(new Date())
const form = ref({
  feedbackTitle: '',
  feedbackContent: ''
})
const { proxy } = getCurrentInstance()
const rules = ref({
  feedbackTitle: [{ required: true, message: proxy.$t("feedback.titleRequired"), trigger: 'blur' }],
  feedbackContent: [{ required: true, message: proxy.$t("feedback.contentRequired"), trigger: 'blur' }],
})
const formRef = ref(null)
function submitForm() {
  formRef.value.validate((valid) => {
    if (valid) {
     
    } else {
      console.log('校验失败')
    }
  })
}


</script>

<style scoped lang="scss">
:deep(.el-card__header) {
  width: 100%;
}
:deep(.el-card__body) {
  padding: 10px 0 !important;
  width: 100%;
}


.home {
  
  h2{
    font-size: 26px;
    font-weight: bold;
    color: #1B1F34;
  }
  p{
    font-size: 16px;
    color: #565E6E;
  }
  
  .box {
    border: none;
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    padding: 10px;
    .fixed-textarea :deep(.el-textarea__inner) {
      height: 200px !important;
      resize: none;
    }
  }
}
</style>

