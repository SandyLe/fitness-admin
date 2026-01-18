<template>
  <div class="excel-crud">
    <h3>{{ title }}</h3>

    <button @click="addRow">新增</button>

    <table>
      <thead>
      <tr>
        <th v-for="col in columns" :key="col.prop">
          {{ col.label }}
        </th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <!-- 新增行 -->
      <tr
          v-if="adding"
          class="editing new-row"
      >
        <td
            v-for="col in columns"
            :key="col.prop"
            @click="startAddCell(col)"
        >
          <input
              v-model="newRow[col.prop]"
              @keydown.enter.prevent="saveNewRow"
              @keydown.esc.prevent="cancelAdd"
              autofocus
          />
        </td>
        <td>
          <button @click="saveNewRow">保存</button>
          <button @click="cancelAdd">取消</button>
        </td>
      </tr>

      <!-- 已有行 -->
      <tr
          v-for="row in list"
          :key="row[rowKey]"
          :class="{ editing: editingRowKey === row[rowKey] }"
      >
        <td
            v-for="col in columns"
            :key="col.prop"
            @click="startCellEdit(row, col)"
        >
          <template v-if="isEditingCell(row, col)">
            <input
                v-model="editRow[col.prop]"
                @keydown.enter.prevent="saveEditRow"
                @keydown.esc.prevent="cancelEdit"
                @blur="autoSave && saveEditRow()"
                autofocus
            />
          </template>
          <template v-else>
            {{ row[col.prop] }}
          </template>
        </td>

        <td>
          <button @click="remove(row)">删除</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

/* ================= Props ================= */
const props = defineProps({
  title: String,
  columns: { type: Array, required: true },
  rowKey: { type: String, default: 'id' },

  listRequest: { type: Function, required: true },
  addRequest: { type: Function, required: true },
  updateRequest: { type: Function, required: true },
  deleteRequest: Function,

  hiddenParams: {
    type: Object,
    default: () => ({})
  },

  autoSave: {
    type: Boolean,
    default: true
  }
})

/* ================= State ================= */
const list = ref([])

/* 编辑已有行 */
const editingRowKey = ref(null)
const editingColProp = ref(null)
const editRow = ref({})

/* 新增行 */
const adding = ref(false)
const newRow = ref({})

/* ================= List ================= */
const fetchList = async () => {
  const res = await props.listRequest({
    ...props.hiddenParams
  })
  list.value = res?.rows?.rows || res?.rows || []
}

/* ================= 新增 ================= */
const addRow = () => {
  adding.value = true
  newRow.value = {}
}

const saveNewRow = async () => {
  if (!adding.value) return

  await props.addRequest({
    ...newRow.value,
    ...props.hiddenParams
  })

  adding.value = false
  newRow.value = {}
  fetchList()
}

const cancelAdd = () => {
  adding.value = false
  newRow.value = {}
}

/* ================= 编辑 ================= */
const startCellEdit = (row, col) => {
  if (!col.editable || adding.value) return

  editingRowKey.value = row[props.rowKey]
  editingColProp.value = col.prop
  editRow.value = { ...row }
}

const isEditingCell = (row, col) =>
    editingRowKey.value === row[props.rowKey] &&
    editingColProp.value === col.prop

const saveEditRow = async () => {
  if (!editingRowKey.value) return

  await props.updateRequest({
    ...editRow.value,
    ...props.hiddenParams
  })

  cancelEdit()
  fetchList()
}

const cancelEdit = () => {
  editingRowKey.value = null
  editingColProp.value = null
  editRow.value = {}
}

/* ================= 删除 ================= */
const remove = async (row) => {
  if (!confirm('确认删除？')) return
  if (!props.deleteRequest) return

  await props.deleteRequest({
    [props.rowKey]: row[props.rowKey],
    ...props.hiddenParams
  })

  fetchList()
}

onMounted(fetchList)
</script>

<style scoped>
.excel-crud table {
  width: 100%;
  border-collapse: collapse;
}
.excel-crud th,
.excel-crud td {
  border: 1px solid #ddd;
  padding: 6px;
  min-width: 80px;
}

.excel-crud tr.editing {
  background: #f0f8ff;
}

.excel-crud tr.new-row {
  background: #f6ffed;
}

.excel-crud input {
  width: 100%;
  border: none;
  outline: none;
  padding: 4px;
}
.excel-crud button {
  background-color: #e6f4ff;
  color: #1677ff;
  border: 1px solid #91caff;
  padding: 4px 10px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 13px;
  margin-right: 6px;
  transition: all 0.2s;
}

.excel-crud button:hover {
  background-color: #bae0ff;
  border-color: #69b1ff;
}

.excel-crud button:active {
  background-color: #91caff;
}

</style>
