<template>
  <div :style="wrapperStyle">
    <img
        v-if="imgUrl"
        :src="imgUrl"
        :style="imgStyle"
        v-bind="$attrs"
    />
    <div v-else-if="loading" class="placeholder">加载中...</div>
    <div v-else class="placeholder">加载失败</div>
  </div>
</template>

<script setup>
import { ref, watch, onBeforeUnmount, computed } from 'vue'

const props = defineProps({
  src: { type: String, required: true },
  token: { type: String, default: () => localStorage.getItem('token') || '' },

  width: [String, Number],
  height: [String, Number],
  fit: { type: String, default: 'cover' }
})

const imgUrl = ref('')
const loading = ref(false)
let objectUrl = null

const normalize = v =>
    typeof v === 'number' ? `${v}px` : v

/* 🔥 外层容器：真正控制尺寸 */
const wrapperStyle = computed(() => ({
  width: normalize(props.width),
  height: normalize(props.height),
  overflow: 'hidden',
  display: 'inline-block',
  flexShrink: 0
}))

/* 🔥 img：填满容器 */
const imgStyle = computed(() => ({
  width: normalize(props.width)+'%',
  height: normalize(props.height)+'%',
  objectFit: props.fit,
  display: 'block'
}))

const loadImage = async () => {
  loading.value = true
  imgUrl.value = ''

  try {
    const res = await fetch(props.src, {
      headers: { Authorization: `Bearer ${props.token}` }
    })
    const blob = await res.blob()

    if (objectUrl) URL.revokeObjectURL(objectUrl)
    objectUrl = URL.createObjectURL(blob)
    imgUrl.value = objectUrl
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

watch(() => [props.src, props.token], loadImage, { immediate: true })

onBeforeUnmount(() => objectUrl && URL.revokeObjectURL(objectUrl))
</script>

<style scoped>
.placeholder {
  width: 100%;
  height: 100%;
  background: #f5f5f5;
  color: #999;
  font-size: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
