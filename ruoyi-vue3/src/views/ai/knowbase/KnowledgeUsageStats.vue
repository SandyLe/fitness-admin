<!-- src/components/KnowledgeUsageStats.vue -->
<template>
  <div class="usage-stats-container">
    <!-- 标题 -->
    <div class="header">
      <el-icon style="margin-right: 8px; color: #409EFF">
        <Document />
      </el-icon>
      <span class="title">{{ $t('knowBase.knowledgeBaseUsageStats') }}</span>
    </div>

    <!-- 统计卡片 -->
    <div class="stats-grid">
      <!-- 知识库数量 -->
      <div class="stat-item">
        <div class="label">
          <el-icon><FolderOpened /></el-icon>
          <span>{{ $t('knowBase.knowledgeBaseCount') }}</span>
        </div>
        <div class="value">{{ usedKbCount }} / {{ maxKbCount }}</div>
        <el-progress
            :percentage="kbPercentage"
            :stroke-width="6"
            :color="progressColor(kbPercentage)"
            :show-text="false"
            class="progress"
        />
        <div class="hint">{{ $t('knowBase.more') }} {{ maxKbCount }} {{ $t('knowBase.knowledgeBaseCountDesc') }}</div>
      </div>

      <!-- 存储空间 -->
      <div class="stat-item">
        <div class="label">
          <el-icon><DataLine /></el-icon>
          <span>{{ $t('knowBase.storageSpace') }}</span>
        </div>
        <div class="value">
          {{ formatBytes(usedStorageBytes) }} / {{ formatBytes(maxStorageBytes) }}
        </div>
        <el-progress
            :percentage="storagePercentage"
            :stroke-width="6"
            :color="progressColor(storagePercentage)"
            :show-text="false"
            class="progress"
        />
        <div class="hint">{{ $t('knowBase.totalStorageQuota') }} {{ formatBytes(maxStorageBytes) }}</div>
      </div>
    </div>

    <!-- 分隔条：位于统计区和表格之间 -->
    <div class="divider"></div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { getKnowquotaByUser } from '@/api/ai/knowledgeQuota';
import { Document, FolderOpened, DataLine } from '@element-plus/icons-vue';

const usedKbCount = ref(0);
const maxKbCount = ref(10);
const usedStorageBytes = ref(0);
const maxStorageBytes = ref(200 * 1024 * 1024);

// 计算百分比
const kbPercentage = computed(() => {
  if (maxKbCount.value === 0) return 0;
  const p = (usedKbCount.value / maxKbCount.value) * 100;
  return Math.min(100, Math.max(0, p));
});

const storagePercentage = computed(() => {
  if (maxStorageBytes.value === 0) return 0;
  const p = (usedStorageBytes.value / maxStorageBytes.value) * 100;
  return Math.min(100, Math.max(0, p));
});

function progressColor(percentage) {
  if (percentage < 70) return '#67C23A'; // 绿
  if (percentage < 90) return '#E6A23C'; // 黄
  return '#F56C6C'; // 红
}

function formatBytes(bytes) {
  if (bytes === null || bytes === undefined || bytes === 0) return '0 B';
  const k = 1024;
  const sizes = ['B', 'KB', 'MB', 'GB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  return parseFloat((bytes / Math.pow(k, i)).toFixed(i > 1 ? 1 : 0)) + ' ' + sizes[i];
}

// 抽取数据加载逻辑为独立函数
async function loadQuotaData() {
  try {
    const res = await getKnowquotaByUser();
    if (res?.code === 200 && res.data) {
      const q = res.data;
      usedKbCount.value = q.usedKbCount ?? 0;
      maxKbCount.value = q.maxKbCount ?? 10;
      usedStorageBytes.value = q.usedStorageBytes ?? 0;
      maxStorageBytes.value = q.maxStorageBytes ?? 200 * 1024 * 1024;
    }
  } catch (error) {
    console.warn('加载知识库配额失败:', error);
  }
}

// 初始加载
onMounted(async () => {
  await loadQuotaData();
});

// 暴露给父组件
defineExpose({
  refresh: loadQuotaData
});
</script>

<style scoped>
.usage-stats-container {
  margin-bottom: 20px;
  padding: 0;
  background: transparent; /* 不加背景色 */
}

.header {
  display: flex;
  align-items: center;
  margin-bottom: 16px;
  font-size: 16px;
  font-weight: 600;
  color: #333;
}

.stats-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
  margin-bottom: 12px;
}

.stat-item {
  padding: 16px;
  border: 1px solid #ebeef5;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.05);
  background: white; /* 内部白色，外部透明 */
  transition: box-shadow 0.3s;
}

.stat-item:hover {
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.label {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #666;
  margin-bottom: 8px;
}

.label .el-icon {
  margin-right: 6px;
  color: #409eff;
}

.value {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin: 8px 0;
}

.progress {
  margin: 10px 0;
}

.hint {
  font-size: 12px;
  color: #999;
}

/* 分隔条：位于统计区和表格之间 */
.divider {
  height: 2px; /* 增加高度，使其更明显 */
  background-color: #f0f2f5; /* 使用较浅的颜色，确保有足够的对比度 */
  margin-top: 16px;
  margin-bottom: 16px;
}
</style>