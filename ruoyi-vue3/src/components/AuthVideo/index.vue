<template>
  <div :style="wrapperStyle">
    <video
        v-if="videoUrl"
        :src="videoUrl"
        :style="videoStyle"
        v-bind="$attrs"
    />
    <div v-else-if="loading" class="placeholder">视频加载中...</div>
    <div v-else class="placeholder">无 token 或加载失败</div>
  </div>
</template>

<script setup>
import { ref, watch, onBeforeUnmount, computed } from 'vue'

const props = defineProps({
  src: { type: String, required: true },

  /** 🔥 强制由外部传入 token */
  token: {
    type: String,
    required: true
  },

  headerKey: {
    type: String,
    default: 'Authorization'
  },
  tokenPrefix: {
    type: String,
    default: 'Bearer '
  },

  width: [String, Number],
  height: [String, Number]
})

const videoUrl = ref('')
const loading = ref(false)
let objectUrl = null

const normalize = v => (typeof v === 'number' ? `${v}px` : v)

const wrapperStyle = computed(() => ({
  width: normalize(props.width),
  height: normalize(props.height),
  overflow: 'hidden',
  background: '#000',
  display: 'inline-block'
}))

const videoStyle = {
  width: '100%',
  height: '100%',
  display: 'block'
}

const loadVideo = async () => {
  if (!props.src || !props.token) {
    console.warn('[AuthVideo] token 为空，已中断请求')
    return
  }

  loading.value = true
  videoUrl.value = ''

  try {
    const res = await fetch(props.src, {
      headers: {
        [props.headerKey]: props.tokenPrefix + props.token
      }
    })

    if (!res.ok) throw new Error('fetch failed')

    const blob = await res.blob()

    if (objectUrl) URL.revokeObjectURL(objectUrl)
    objectUrl = URL.createObjectURL(blob)
    videoUrl.value = objectUrl
  } catch (e) {
    console.error('[AuthVideo]', e)
  } finally {
    loading.value = false
  }
}

watch(
    () => [props.src, props.token],
    loadVideo,
    { immediate: true }
)

onBeforeUnmount(() => {
  if (objectUrl) URL.revokeObjectURL(objectUrl)
})
</script>

<style scoped>
.placeholder {
  width: 100%;
  height: 100%;
  background: #000;
  color: #aaa;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
}
</style>
