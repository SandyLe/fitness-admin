<template>
   <el-form ref="userRef" :model="form" :rules="rules" label-width="80px">
      <el-form-item :label="$t('user.nickName')" prop="nickName">
         <el-input v-model="form.nickName" maxlength="30" />
      </el-form-item>
      <el-form-item :label="$t('user.phonenumber')" prop="phonenumber">
         <el-input v-model="form.phonenumber" maxlength="11" />
      </el-form-item>
      <el-form-item :label="$t('user.email')" prop="email">
         <el-input v-model="form.email" maxlength="50" />
      </el-form-item>
      <el-form-item :label="$t('user.sex')">
         <el-radio-group v-model="form.sex">
            <el-radio value="0">{{ $t('user.male') }}</el-radio>
            <el-radio value="1">{{ $t('user.female') }}</el-radio>
         </el-radio-group>
      </el-form-item>
      <el-form-item :label="$t('doctor.imgUrl')" prop="imgUrl">
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
      <el-form-item>
      <el-button type="primary" @click="submit">{{ $t('common.save') }}</el-button>
      <el-button type="danger" @click="close">{{ $t('common.cancel') }}</el-button>
      </el-form-item>
   </el-form>
</template>

<script setup>
import { updateUserProfile } from "@/api/system/user"
import {getToken} from "@/utils/auth.js";
import AuthImg from "@/components/AuthImg"
const baseApi = import.meta.env.VITE_APP_BASE_API

const props = defineProps({
  user: {
    type: Object
  }
})

const { proxy } = getCurrentInstance()

const headers = ref({ Authorization: "Bearer " + getToken() })
const form = ref({})
const rules = ref({
  nickName: [{ required: true, message: proxy.$t("user.nickNameEmpty"), trigger: "blur" }],
  email: [{ required: true, message: proxy.$t("user.emailEmpty"), trigger: "blur" }, { type: "email", message: proxy.$t("user.emailInvalid"), trigger: ["blur", "change"] }],
  phonenumber: [{ required: true, message: proxy.$t("user.phonenumberEmpty"), trigger: "blur" }, { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: proxy.$t("user.phonenumberInvalid"), trigger: "blur" }],
})
const uploadUrl = ref(import.meta.env.VITE_APP_BASE_API + "/common/upload") // 上传的文件服务器地址

/** 提交按钮 */
function submit() {
  proxy.$refs.userRef.validate(valid => {
    if (valid) {
      updateUserProfile(form.value).then(response => {
        proxy.$modal.msgSuccess("修改成功")
        props.user.phonenumber = form.value.phonenumber
        props.user.email = form.value.email
        props.user.imgUrl = form.value.imgUrl
      })
    }
  })
}

/** 关闭按钮 */
function close() {
  proxy.$tab.closePage()
}
function importImgUrl(res,file) {
  //上传img文件 获取文件内容赋值给imgUrl
  if (res.code === 200) {
    form.value.imgUrl = res.fileName
  } else {
    proxy.$modal.msgError(res.msg)
    proxy.$refs.imgUrlRef.handleRemove(file)
  }
}
// 回显当前登录用户信息
watch(() => props.user, user => {
  if (user) {
    form.value = { nickName: user.nickName, phonenumber: user.phonenumber, email: user.email, sex: user.sex, imgUrl: user.imgUrl }
  }
},{ immediate: true })
</script>
