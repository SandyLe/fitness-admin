<template>
  <div class="avatar-container" :class="{'collapse': collapse}" >
      <div class="avatar">
        <img
          v-if="img"
          :src="img"
          class="avatar-image"
        />
        <span v-else class="avatar-initial">我</span>
      </div>
      <h2 class="avatar-name">{{name}}</h2>
  
  </div>
</template>

<script setup>

defineProps({
  name: {
    type: String,
    required: true
  },
  collapse: {
    type: Boolean,
    default: false
  }
})

</script>

<style lang="scss" scoped>


.avatar-container {
  display: flex;
  align-items: center;
  padding-left: 15px;
  width: 100%;
  height: 50px;
  line-height: 50px;
  border-top: 1px solid #383B4C;
  color: #fff;
  font-size: 24px;
  font-weight: 600;
  &.collapse{
    .avatar-name{
     display: none;
    }
  }
   .avatar{
    width: 30px;
    height: 30px;
    border-radius: 50%;
    margin-right: 10px;
    background-color: #2457D7;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: center;
        img{
            width: 100%;
            height: 100%;
            border-radius: 50%;
        }
        .avatar-initial{
            line-height: 30px;
            color: #fff;
            font-size: 16px;
            font-weight: 600;
            vertical-align: middle;
        }
   } 
   .avatar-name{
        line-height: 50px;
        color: #fff;
        font-size:  16px;
        font-weight: 600;
   }
}
</style>