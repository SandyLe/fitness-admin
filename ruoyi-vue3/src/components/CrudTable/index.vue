<template>
  <div class="excel-crud">
    <div class="header">
      <h3>{{ title }}</h3>
      <button class="btn primary" @click="startAdd">新增</button>
    </div>

    <table>
      <thead>
      <tr>
        <th v-for="col in columns" :key="col.prop">
          {{ col.label }}
        </th>
        <th width="120">操作</th>
      </tr>
      </thead>

      <tbody>
      <!-- 新增行 -->
      <tr v-if="adding" class="adding-row">
        <td v-for="col in columns" :key="col.prop">
          <input v-if="!col.editor || col.editor === 'input'" v-model="editingValue" />
          <select
              v-else-if="col.editor === 'select'"
              v-model="editingValue"
              @change="onSelectChange"
              @blur="onBlurEdit"
          >
            <option
                v-for="opt in optionsMap[col.prop] || []"
                :key="opt.value"
                :value="opt.value"
            >
              {{ opt.label }}
            </option>
          </select>
        </td>
        <td>
          <button class="btn success" @click="saveNew">保存</button>
          <button class="btn" @click="cancelAdd">取消</button>
        </td>
      </tr>

      <!-- 数据行 -->
      <tr v-for="row in list" :key="row[rowKey]">
        <td
            v-for="col in columns"
            :key="col.prop"
            @click="startEdit(row, col)"
        >
          <template v-if="isEditingCell(row, col)">
            <input
                v-if="!col.editor || col.editor === 'input'"
                v-model="editingValue"
                @keydown.esc.prevent="cancelEdit"
                @blur="onBlurEdit"
                autofocus
            />
            <select
                v-else-if="col.editor === 'select'"
                v-model="editingValue"
                @change="onSelectChange"
                @blur="onBlurEdit"
            >
              <option
                  v-for="opt in optionsMap[col.prop] || []"
                  :key="opt.value"
                  :value="opt.value"
              >
                {{ opt.label }}
              </option>
            </select>

          </template>
          <template v-else>
            {{ renderCell(row, col) }}
          </template>
        </td>

        <td>
          <button class="btn success" @click="saveEdit(row)">保存</button>
          <button class="btn danger" @click="remove(row)">删除</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import {isExternal} from "@/utils/validate.js";

/* ================= Props ================= */
const props = defineProps({
  title: String,

  columns: {
    type: Array,
    required: true
  },

  rowKey: {
    type: String,
    default: 'id'
  },

  listRequest: {
    type: Function,
    required: true
  },
  addRequest: {
    type: Function,
    required: true
  },
  updateRequest: {
    type: Function,
    required: true
  },
  deleteRequest: Function,

  hiddenParams: {
    type: Object,
    default: () => ({})
  }
})

/* ================= Data ================= */
const list = ref([])

/* 新增 */
const adding = ref(false)
const newRow = ref({})

/* 编辑（只允许一个单元格） */
const editingCell = ref({
  rowKey: null,
  colProp: null
})
const editingValue = ref('')
// ⭐ 编辑缓存：key = rowKey
const editCache = ref({})
const resetState = () => {
  list.value = []
  adding.value = false
  newRow.value = {}
  cancelEdit()
}
/* ================= Methods ================= */
const fetchList = async () => {
  resetState()
  fetchColumns()
  const res = await props.listRequest({ ...props.hiddenParams })
  list.value = res?.rows?.rows || res?.rows || []
}

/* ===== 新增 ===== */
const startAdd = () => {
  adding.value = true
  newRow.value = {}
}

const saveNew = async () => {
  await props.addRequest({
    ...newRow.value,
    ...props.hiddenParams
  })
  adding.value = false
  fetchList()
}

const cancelAdd = () => {
  adding.value = false
  newRow.value = {}
}

/* ===== 编辑 ===== */
const startEdit = async (row, col) => {
  if (!col.editable) return

  const key = row[props.rowKey]

  editingCell.value = {
    rowKey: key,
    colProp: col.prop
  }

  // 初始化行缓存
  if (!editCache.value[key]) {
    editCache.value[key] = { ...row }
  }

  editingValue.value = editCache.value[key][col.prop]

  // ⭐ 如果是下拉框 & 还没加载过选项
  /*if (
      col.editor === 'select' &&
      col.optionsRequest &&
      !optionsMap.value[col.prop]
  ) {
    const res = await col.optionsRequest()

    const raw = res?.rows?.rows || res?.rows || []

    // ⭐ 如果有格式化函数，就转换
    if (col.optionsFormatter) {
      optionsMap.value[col.prop] = raw.map(col.optionsFormatter)
    } else {
      // 默认兜底：尝试常见字段名
      optionsMap.value[col.prop] = raw.map(item => ({
        label: item.label ?? item.name ?? item.dictName,
        value: item.value ?? item.id ?? item.dictCode
      }))
    }
  }*/

}

const onBlurEdit = () => {
  const { rowKey, colProp } = editingCell.value
  if (!rowKey || !colProp) return

  // ⭐ 写入编辑缓存
  editCache.value[rowKey][colProp] = editingValue.value
  // 2️⃣ 同步更新显示值（关键）
  const row = list.value.find(
      item => item[props.rowKey] === rowKey
  )
  if (row) {
    row[colProp] = editingValue.value
  }
}
const isEditingRow = (row) => {
  return editingCell.value.rowKey === row[props.rowKey]
}

const isEditingCell = (row, col) => {
  return (
      editingCell.value.rowKey === row[props.rowKey] &&
      editingCell.value.colProp === col.prop
  )
}

const saveEdit = async (row, col) => {
  const key = row[props.rowKey]
  const cache = editCache.value[key]
  if (!cache) return

  await props.updateRequest({
    ...cache,
    ...props.hiddenParams
  })

  cancelEdit()
  fetchList()
}

const cancelEdit = () => {
  editingCell.value = { rowKey: null, colProp: null }
  editingValue.value = ''
  delete editCache.value[props.rowKey]
}

/* ===== 删除 ===== */
const remove = async (row) => {
  if (!confirm('确认删除？')) return
  debugger
  /*await props.deleteRequest({
    [props.rowKey]: row[props.rowKey],
    ...props.hiddenParams
  })*/
  await props.deleteRequest(row[props.rowKey])
  fetchList()
}
const renderCell = (row, col) => {
  if (
      col.editor === 'select' &&
      optionsMap.value[col.prop]
  ) {
    const opt = optionsMap.value[col.prop].find(
        o => o.value === row[col.prop]
    )
    return opt ? opt.label : row[col.prop]
  }
  return row[col.prop]
}

const onSelectChange = () => {
  onBlurEdit()
}
const optionsMap = ref({})
const resetColumn = () => {
  optionsMap.value =[]
}

const fetchColumns = async () => {
  resetColumn()
  props.columns.forEach(col => {
    if (
        col.editor === 'select' &&
        col.optionsRequest &&
        !optionsMap.value[col.prop]
    ) {
      try {
        const res = col.optionsRequest({ ...props.hiddenParams }).then(res=>{
          const raw = res?.rows?.rows || res?.rows || []
          // ⭐ 如果有格式化函数，就转换
          if (col.optionsFormatter) {
            optionsMap.value[col.prop] = raw.map(col.optionsFormatter)
          } else {
            // 默认兜底：尝试常见字段名
            optionsMap.value[col.prop] = raw.map(item => ({
              label: item.label ?? item.name ?? item.dictName,
              value: item.value ?? item.id ?? item.dictCode
            }))
          }
        })
      }catch (e) {
        console.log(e)
      }
    }
  })
}
onMounted( ()=>{
  fetchList()
  fetchColumns()
})
defineExpose({
  reload: fetchList
})

</script>

<style scoped>
.excel-crud {
  font-size: 14px;
}

.header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  border: 1px solid #ddd;
  padding: 6px;
}

td {
  cursor: pointer;
}

input {
  width: 100%;
  border: none;
  outline: none;
  padding: 4px;
}

/* ===== Buttons ===== */
.btn {
  padding: 4px 10px;
  border: none;
  background: #e6f2ff;
  color: #1677ff;
  cursor: pointer;
  margin-right: 4px;
}

.btn.primary {
  background: #e6f2ff;
}

.btn.success {
  background: #f6ffed;
  color: #52c41a;
}

.btn.danger {
  background: #fff1f0;
  color: #ff4d4f;
}

.adding-row {
  background: #f0f8ff;
}
</style>
