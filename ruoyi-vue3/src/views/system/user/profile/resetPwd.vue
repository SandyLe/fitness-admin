<template>
   <el-form ref="pwdRef" :model="user" :rules="rules" label-width="80px">
      <el-form-item :label="$t('user.oldPassword')" prop="oldPassword">
         <el-input v-model="user.oldPassword" :placeholder="$t('user.oldPasswordDesc')" type="password" show-password />
      </el-form-item>
      <el-form-item :label="$t('user.newPassword')" prop="newPassword">
         <el-input v-model="user.newPassword" :placeholder="$t('user.newPasswordDesc')" type="password" show-password />
      </el-form-item>
      <el-form-item :label="$t('user.confirmPassword')" prop="confirmPassword">
         <el-input v-model="user.confirmPassword" :placeholder="$t('user.confirmPasswordDesc')" type="password" show-password/>
      </el-form-item>
      <el-form-item>
      <el-button type="primary" @click="submit">{{$t("common.save")}}</el-button>
      <el-button type="danger" @click="close">{{$t("common.close")}}</el-button>
      </el-form-item>
   </el-form>
</template>

<script setup>
import { updateUserPwd } from "@/api/system/user"

const { proxy } = getCurrentInstance()

const user = reactive({
  oldPassword: undefined,
  newPassword: undefined,
  confirmPassword: undefined
})

const equalToPassword = (rule, value, callback) => {
  if (user.newPassword !== value) {
    callback(new Error(proxy.$t("user.confirmPasswordError")))
  } else {
    callback()
  }
}

const rules = ref({
  oldPassword: [{ required: true, message: proxy.$t("user.oldPasswordDesc"), trigger: "blur" }],
  newPassword: [{ required: true, message: proxy.$t("user.newPasswordDesc"), trigger: "blur" }, { min: 6, max: 20, message: proxy.$t("user.newPasswordLength"), trigger: "blur" }, { pattern: /^[^<>"'|\\]+$/, message: proxy.$t("user.illegalCharsDesc") + "< > \" ' \\\ |", trigger: "blur" }],
  confirmPassword: [{ required: true, message: proxy.$t("user.confirmPasswordDesc"), trigger: "blur" }, { required: true, validator: equalToPassword, trigger: "blur" }]
})

/** 提交按钮 */
function submit() {
  proxy.$refs.pwdRef.validate(valid => {
    if (valid) {
      updateUserPwd(user.oldPassword, user.newPassword).then(response => {
        proxy.$modal.msgSuccess(proxy.$t("user.resetSuccess") + user.newPassword)
      })
    }
  })
}

/** 关闭按钮 */
function close() {
  proxy.$tab.closePage()
}
</script>
