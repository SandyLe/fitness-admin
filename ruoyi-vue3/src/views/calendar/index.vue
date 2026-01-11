<template>
  <div class="app-container home">
    <h2>{{$t('home.calendar')}}</h2>
    <p>{{$t('home.calendarDesc2')}}</p> 
    <el-row :gutter="40" style="margin-top: 40px;">
      <el-col :sm="24" :lg="18" >
       <el-card class="box" >
        <el-calendar v-model="value"  >
           <template #date-cell="{ data }">
            <div
              class="custom-calendar-cell"
              :class="{ 'highlight': isHighlighted(data.day) ,'disabled': isPastDate(data.day)}"
              @click.stop="handleDateClick(data.day)"
            >
              {{ dayOfMonth(data.day) }}
              <span v-if="isHighlighted(data.day)" class="highlight-text"></span>
            </div>
          </template>
        </el-calendar>
       </el-card>
      </el-col>
      <el-col :sm="24" :lg="6"  class="calendar-form" >
       <el-card class="box" >
          <el-scrollbar style="height: 570px;">
          <template #header>
            <div class="title">
              {{value.getFullYear()}}{{$t('calendar.year')}}{{value.getMonth() + 1}}{{$t('calendar.month')}}{{value.getDate()}}{{$t('calendar.day')}}
            </div>
          </template>
          <div class="inner-card">
            <el-form :model="form" :rules="rules" ref="formRef" label-width="0px">
              <el-form-item label="" prop="title">
                <el-input v-model="form.title" :placeholder="$t('calendar.selectName')" />
              </el-form-item>
              <el-form-item label="" prop="appointmentTime">
                <el-time-select
                  v-model="form.appointmentTime"
                  start="08:00"
                  step="00:30"
                  end="17:30"
                  :placeholder="$t('calendar.selectTime')"
                >
                </el-time-select>
              </el-form-item>
                
              <el-form-item label="" prop="description">
                <el-input v-model="form.description" type="textarea" :placeholder="$t('calendar.selectReason')"  :rows="4"   class="fixed-textarea"
                />
              </el-form-item>
              <el-form-item label="" prop="isReminder">
                <el-checkbox v-model="form.isReminder" :label="$t('calendar.reminder')" false-value="0" true-value="1" />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitForm" style="width: 45%;height: 40px;margin-right: 10px;">{{$t('common.save')}}</el-button>
                <el-button  @click="resetForm" style="width: 45%;height: 40px;">{{$t('common.cancel')}}</el-button>
              </el-form-item>
            </el-form>
          </div>
           
            <ul class="appointment-list">
              <li v-for="(item, index) in appointmentList" :key="index">
                <div class="appointment-item">
                  <span>{{  formatDate(item.reminderTime,'{h}:{i}')}}</span>
                  <el-icon @click="removeAppointment(index,item)"><Close /></el-icon>
                </div>
                <div class="appointment-reason">{{item.description}}</div>
                <el-tag class="tag"  v-if="item.isReminder==1">
                 <el-icon><bell-filled /></el-icon>{{$t('calendar.remindered')}}
                </el-tag>
              </li>
              
            </ul>
            </el-scrollbar>
       </el-card>
      </el-col>
       
    </el-row>
  </div>
</template>

<script setup name="Index">
import { ref, onMounted } from 'vue'
import { calendarEventList, savecalendarevent, delCalendarEvent } from '@/api/ai'
import { formatDate } from '@/utils/index.js'
const { proxy } = getCurrentInstance()

const value = ref(new Date())

const form = ref({
 
})
const highlightDates = [
  '2025-12-20',
  '2025-12-25',
  '2025-12-09'
]
const dayOfMonth = (dateStr) => {
  return dateStr.split('-')[2]
}
// 判断是否是过去日期（不含今天）
const isPastDate = (date) => {
  const d = new Date(date)
  d.setHours(0, 0, 0, 0)
  return d < new Date().setHours(0, 0, 0, 0)
}
const handleDateClick = (date) => {
  if (isPastDate(date)) {
    // 不响应过去日期的点击
    return
  }
  value.value = date
  getAppointmentList(formatDate(value.value,'{y}-{m}-{d}'))
}
// 判断是否高亮
const isHighlighted = (date) => {
  return highlightDates.includes(date)
}
const appointmentList = ref([]) 
const rules = ref({
  title: [{ required: true, message: proxy.$t('calendar.selectName'), trigger: 'blur' }],
  appointmentTime: [{ required: true, message: proxy.$t('calendar.selectTime'), trigger: 'blur' }],
  description: [{ required: true, message: proxy.$t('calendar.selectReason'), trigger: 'blur' }],
})
const formRef = ref(null)
function submitForm() {
  formRef.value.validate((valid) => {
    if (valid) {
      
      form.value.reminderTime =  formatDate(value.value,'{y}-{m}-{d}') + ' ' + form.value.appointmentTime + ':00'
      savecalendarevent(form.value).then(res => {
        if (res.code === 200) {
          proxy.$modal.msgSuccess(proxy.$t("common.saveSuccess"))
          resetForm()
          getAppointmentList(formatDate(value.value,'{y}-{m}-{d}'))
        } else {
        }
      })
      
    } else {
      console.log('校验失败')
    }
  })
}
function resetForm() {
  form.value = {
    id: null,
    title: null,
    appointmentTime: null,
    description: null,
    isReminder: null,
  }
  proxy.resetForm("formRef")
}
function removeAppointment(index,item) {
  delCalendarEvent(item.eventId).then(res => {
    if (res.code === 200) {
      appointmentList.value.splice(index, 1)
      proxy.$modal.msgSuccess(proxy.$t("common.deleteSuccess"))
    } else {
      ElMessage.error(res.msg || proxy.$t("calendar.deleteFailed"))
    }
  })
}
function getAppointmentList(curDate) {
  calendarEventList({params:{curDate:curDate}}).then(res => {
    if (res.code === 200) {
      appointmentList.value = res.rows || []
    } else {
      ElMessage.error(res.msg || proxy.$t("calendar.getFailed"))
    }
  })
}
onMounted(() => {
  getAppointmentList(formatDate(value.value,'{y}-{m}-{d}'))
})

</script>

<style scoped lang="scss">
:deep(.el-card__header) {
  width: 100%;
}
:deep(.el-card__body) {
  padding: 10px 0 !important;
  width: 100%;
}


:deep(.custom-calendar-cell){
  height: 100%;
  padding-top: 5px;
}
:deep(.el-calendar-table .el-calendar-day){
  padding: 0px !important;
}
:deep(.custom-calendar-cell.disabled)
{
   color: #a8abb2;
}
.highlight {
  position: relative;
  background-color: #2151C5;
  color: #fff;
  width: 100%;
  height: 100%;
  .highlight-text{
    position: absolute;
    bottom: 5px;
    left: 50%;
    transform: translateX(-50%);
    width: 5px;
    height: 5px;
    border-radius: 50%;
    background-color: #fff;
    display: block;
    z-index: 100;
  }
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
  .calendar-form{
    animation: slideIn 0.3s ease-in-out;
   
  }
  @keyframes slideIn {
    0% {
      transform:translateX(-50%);
    }
    100% {
      transform:translateX(0);
    }
  }
  .box {
    border: none;
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    padding: 10px;
    .title{
      display: flex;
      width: 100%;
      font-size: 14px;
      font-weight: bold;
      color: #1B1F34;
    }
    .inner-card{
      width: 100%;
      padding: 10px;
      border-radius: 5px;
      background-color: #F7F9FC;
      margin-bottom: 10px;
    }
    .fixed-textarea :deep(.el-textarea__inner) {
      height: 100px !important;
      resize: none;
    }
    .appointment-list{
      padding: 0;
      li{
        animation: slideIn 0.3s ease-in-out;
        width: 100%;
        padding: 10px;
        border-radius: 5px;
        background-color: #F7F9FC;
        list-style: none;
        margin-bottom: 10px;
        padding-left: 10px;
        box-sizing: border-box;
        text-align: left;
        border-left: 4px solid #2B64F6;
        min-height: 90px;
        .appointment-item{
          display: flex;
          justify-content: space-between;
          align-items: center;
          span{
            font-size: 12px;
            font-weight: bold;
            color: #2B64F6;
          }
          .el-icon{
            cursor: pointer;
          }
        }
        .appointment-reason{
          width: 95%;
          font-size: 12px;
          line-height: 20px;
          margin: 5px 0;
          color: #000;
          text-align: left;
          font-weight: 400;
        }
        span.tag{
          font-size: 12px;
          background-color: rgba(43, 100, 246, 0.1);
          color: #2B64F6;

        }
      }
    }
  }
}
</style>

