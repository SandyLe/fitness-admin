<template>
  <div class="app-container home">
    <h2>{{ $t('home.quickFunction') }}</h2>
    <p>{{ $t('home.selectFunction') }}</p>
    
    <el-row :gutter="40" style="margin-bottom: 40px;">
      <el-col :sm="24" :lg="6" >
        <el-card class="update-log" @click="goTarget('/onlines')">
          <img src="@/assets/images/zaixianzixun.png" :alt="$t('home.onlineConsult')" />
          <h4>{{ $t('home.onlineConsult') }}</h4>
          <p>{{ $t('home.onlineConsultDesc') }}</p>
       </el-card>
      </el-col>
      <el-col :sm="24" :lg="6" >
         <el-card class="update-log" @click="goTarget('/history')">
          <img src="@/assets/images/lishiduihua.png" :alt="$t('home.historyConsult')" />
          <h4>{{ $t('home.historyConsult') }}</h4>
          <p>{{ $t('home.historyConsultDesc') }}</p>
       </el-card>
      </el-col>
       <el-col :sm="24" :lg="6" >
         <el-card class="update-log" @click="goTargetHref('https://sh.91160.com/')" >
          <img src="@/assets/images/yuyueguahao.png" :alt="$t('home.registerConsult')" />
          <h4>{{ $t('home.registerConsult') }}</h4>
          <p>{{ $t('home.registerConsultDesc') }}</p>
       </el-card>
      </el-col>
       <el-col :sm="24" :lg="6" >
         <el-card class="update-log" @click="goTargetHref('https://zwdt.sh.gov.cn/govPortals/municipalDepartments/SHYBSH')" >
          <img src="@/assets/images/yiliaobaozhang.png" :alt="$t('home.medicalInsurance')" />
          <h4>{{ $t('home.medicalInsurance') }}</h4>
          <p>{{ $t('home.medicalInsuranceDesc') }}</p>
       </el-card>
      </el-col>
      </el-row>
      <el-row :gutter="40">
      <el-col :sm="24" :lg="6" >
        <el-card class="update-log" @click="goTarget('/calendar')"    >
          <img src="@/assets/images/dianzirili.png" :alt="$t('home.calendar')" />
          <h4>{{ $t('home.calendar') }}</h4>
          <p>{{ $t('home.calendarDesc') }}</p>
       </el-card>
      </el-col>
      <el-col :sm="24" :lg="6" >
         <el-card class="update-log" @click="goTarget('/user/profile')">
          <img src="@/assets/images/gerenxinxi.png" :alt="$t('home.personalInfo')" />
          <h4>{{ $t('home.personalInfo') }}</h4>
          <p>{{ $t('home.personalInfoDesc') }}</p>
       </el-card>
      </el-col>
       <el-col :sm="24" :lg="6" >
         <el-card class="update-log" @click="goTarget('/feedback')">
          <img src="@/assets/images/wentifankui.png" :alt="$t('home.feedback')" />
          <h4>{{ $t('home.feedback') }}</h4>
          <p>{{ $t('home.feedbackDesc') }}</p>
       </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup name="Index">
import router from '@/router'
const version = ref('3.9.0')

function goTarget(url) {
 router.push(url)
}
function goTargetHref(url) {
  window.open(url, '__blank')
}
</script>

<style scoped lang="scss">
.home {
  padding: 40px;
  h2{
    font-size: 26px;
    font-weight: bold;
    color: #1B1F34;
  }
  p{
    font-size: 16px;
    color: #565E6E;
  }

  .update-log {
    border: none;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
    height: 400px;
    cursor: pointer;
    h4{
      font-size: 24px;
      font-weight: bold;
      color: #1B1F34;
    }
    p{
       font-size: 20px;
       color: #565E6E;
    }
  }
}
</style>

