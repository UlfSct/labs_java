<script setup lang="js">
import {computed, onMounted, ref, watch} from "vue";
import ApplyDialog from "@/components/ApplyDialog.vue";
import {useFormErrors} from "@/mixins/FormErrorsMixin.js";
import ScrollCenterDialog from "@/components/ScrollCenterDialog.vue";
import {useBooksStore} from "@/stores/books.js";
import {useAuthorsStore} from "@/stores/authors.js";

const store = useBooksStore()
const authorsStore = useAuthorsStore()
const { getError, hasError, mapErrors, clearErrors } = useFormErrors()

const headers = [
  {
    title: 'Название',
    key: 'title',
    align: 'center',
    sortable: false,
    width: '20%'
  },
  {
    title: 'ISBN',
    key: 'isbn',
    align: 'center',
    sortable: false,
    width: '20%'
  },
  {
    title: 'Автор',
    key: 'authorFullName',
    align: 'center',
    sortable: false,
    width: '20%'
  },
  {
    title: 'Год выпуска',
    key: 'year',
    align: 'center',
    sortable: false,
    width: '30%'
  },
  {
    title: 'Действия',
    key: 'actions',
    align: 'center',
    sortable: false,
    width: '10%'
  }
]
const defaultFormData = {
  title: '',
  authorId: null,
  year: null,
  isbn: ''
}
let requestData = {};

const createDialogOpened = ref(false)
const editDialogOpened = ref(false)
const deleteDialogOpened = ref(false)
const dialogItem = ref(null)
const search = ref('')
const itemsPerPage = ref(10)
const formData = ref(Object.assign({}, defaultFormData))

const items = computed(() => store.getItems)
const loading = computed(() => store.isLoadingList)
const loadingDelete = computed(() => store.isLoadingDelete)
const loadingCreate = computed(() => store.isLoadingCreate)
const loadingUpdate = computed(() => store.isLoadingUpdate)
const pagination = computed(() => store.getPagination)
const authors = computed(() => authorsStore.getSelectorItems)

const openCreateDialog = () => {
  clearErrors()
  formData.value = Object.assign({}, defaultFormData)
  createDialogOpened.value = true
}

const openEditDialog = (item) => {
  clearErrors()
  dialogItem.value = item
  formData.value = Object.assign({}, item)
  editDialogOpened.value = true
}

const openDeleteDialog = (item) => {
  dialogItem.value = item
  deleteDialogOpened.value = true
}

const closeCreateEditDialog = (confirmed) => {
  if (createDialogOpened.value) closeCreateDialog(confirmed)
  else closeEditDialog(confirmed)
}

const closeCreateDialog = async (confirmed) => {
  if (!confirmed) {
    createDialogOpened.value = false
    return
  }

  try {
    await store.createItem(prepareCreateData())
    createDialogOpened.value = false
    loadData()
  } catch (e) {
    mapErrors(e)
  }
}

const closeEditDialog = async (confirmed) => {
  if (!confirmed) {
    editDialogOpened.value = false
    return
  }

  try {
    await store.updateItem(dialogItem.value.id, prepareEditData())
    editDialogOpened.value = false
    loadData()
  } catch (e) {
    mapErrors(e)
  }
}

const closeDeleteDialog = async (confirmed) => {
  if (!confirmed) {
    deleteDialogOpened.value = false
    return
  }

  try {
    await store.deleteItem(dialogItem.value.id)
    deleteDialogOpened.value = false
    loadData()
  } catch (e) {
    mapErrors(e)
  }
}

const prepareCreateData = () => {
  requestData = formData.value;
  if (!requestData.authorId) delete requestData.authorId;
  if (!requestData.title) delete requestData.title;
  if (!requestData.year) delete requestData.year;
  if (!requestData.isbn) delete requestData.isbn;
  return requestData;
}

const prepareEditData = () => {
  requestData = formData.value;
  if (!requestData.authorId) delete requestData.authorId;
  if (!requestData.title) delete requestData.title;
  if (!requestData.year) delete requestData.year;
  if (!requestData.isbn) delete requestData.isbn;
  return requestData;
}

const loadData = () => {
  store.loadItems()
}

const goToPreviousPage = () => {
  store.setPreviousPage()
  loadData()
}

const goToNextPage = () => {
  store.setNextPage()
  loadData()
}

const searchItems = () => {
  if (pagination.value.search === search.value) return
  store.setSearch(search.value)
  loadData()
}

watch(() => itemsPerPage.value, (nVal) => {
  store.setNewSize(nVal)
  loadData()
})

onMounted(() => {
  loadData()
  authorsStore.loadAuthorsSelector()
})
</script>

<template>
  <div class="items-container">
    <div class="title-section">
      <h1 class="page-title">Книги</h1>
    </div>
    <v-card class="items-card" elevation="4">
      <div class="card-header">
        <div class="header-content">
          <v-text-field
            v-model="search"
            variant="outlined"
            color="primary"
            bg-color="grey-lighten-4"
            placeholder="Поиск по названию"
            clear-icon="mdi-close"
            density="compact"
            class="mr-2"
            clearable
            hide-details
            max-width="300"
            @click:clear="searchItems"
            @keyup.enter="searchItems"
          />
          <v-btn
            color="primary"
            variant="flat"
            icon="mdi-plus"
            @click="openCreateDialog"
            class="add-btn"
            size="small"
            rounded="lg"
            elevation="2"
          >
          </v-btn>
        </div>
      </div>

      <div class="table-container">
        <v-data-table
          :items="items"
          :headers="headers"
          :loading="loading"
          :hide-default-footer="true"
          no-data-text="Нет данных для отображения"
          class="items-table"
          density="compact"
          hover
          :items-per-page="-1"
        >
          <template v-slot:loading>
            <div class="loading-overlay">
              <v-progress-circular indeterminate color="primary" size="64"></v-progress-circular>
              <p class="loading-text">Загрузка . . .</p>
            </div>
          </template>

          <template v-slot:item.isbn="{ item }">
            {{ item.bookInfo ? item.bookInfo.isbn : '-' }}
          </template>

          <template v-slot:item.actions="{ item }">
            <v-tooltip text="Редактировать" location="top">
              <template v-slot:activator="{ props }">
                <v-btn
                  v-bind="props"
                  icon
                  variant="text"
                  color="primary"
                  size="30"
                  @click="openEditDialog(item)"
                  class="action-btn"
                >
                  <v-icon icon="mdi-pencil" size="20"></v-icon>
                </v-btn>
              </template>
            </v-tooltip>
            <v-tooltip text="Удалить" location="top">
              <template v-slot:activator="{ props }">
                <v-btn
                  v-bind="props"
                  icon
                  variant="text"
                  color="error"
                  size="30"
                  @click="openDeleteDialog(item)"
                  class="action-btn"
                >
                  <v-icon icon="mdi-delete" size="20"></v-icon>
                </v-btn>
              </template>
            </v-tooltip>
          </template>

          <template v-slot:bottom>
            <div class="custom-footer">
              <div class="footer-controls">
                <v-btn
                  class="footer-btn"
                  :disabled="pagination.first || loading"
                  @click="goToPreviousPage"
                  size="30"
                  variant="text"
                >
                  <v-icon>mdi-chevron-left</v-icon>
                </v-btn>
                <v-btn
                  class="footer-btn"
                  :disabled="pagination.last || loading"
                  @click="goToNextPage"
                  size="30"
                  variant="text"
                >
                  <v-icon>mdi-chevron-right</v-icon>
                </v-btn>

                <div class="items-per-page-selector">
                  <span class="selector-label">Показать:</span>
                  <v-select
                    v-model="itemsPerPage"
                    :items="[1, 10, 20]"
                    :readonly="loading"
                    density="compact"
                    variant="outlined"
                    hide-details
                    class="page-select"
                  />
                </div>
              </div>
            </div>
          </template>
        </v-data-table>
      </div>
    </v-card>

    <apply-dialog
      v-if="deleteDialogOpened"
      :opened="deleteDialogOpened"
      :get-text="() => 'Вы уверены, что хотите удалить данную книгу?'"
      :loading="loadingDelete"
      @close="closeDeleteDialog"
    />

    <scroll-center-dialog
      v-if="createDialogOpened || editDialogOpened"
      :value="createDialogOpened || editDialogOpened"
      @close="closeCreateEditDialog(false)"
      @apply="closeCreateEditDialog(true)"
      :loading="loadingCreate || loadingUpdate"
      :title="createDialogOpened ? 'Добавить новую книгу' : 'Редактировать книгу'"
    >
      <template v-slot:icon>
        <v-icon :icon="createDialogOpened ? 'mdi-account-plus' : 'mdi-account-edit'" color="primary" size="28"></v-icon>
      </template>

      <div class="form-content mt-4">
        <v-text-field
          v-model="formData.title"
          variant="outlined"
          color="primary"
          bg-color="grey-lighten-4"
          class="form-field"
          :error="hasError('title')"
          :error-messages="getError('title')"
          density="comfortable"
          clearable
        >
          <template v-slot:label>
            <span class="field-label">Название<span class="required-star">*</span></span>
          </template>
        </v-text-field>

        <v-select
          v-model="formData.authorId"
          :items="authors"
          variant="outlined"
          color="primary"
          bg-color="grey-lighten-4"
          class="form-field"
          :error="hasError('authorId')"
          :error-messages="getError('authorId')"
          item-value="id"
          item-title="fullName"
          density="comfortable"
          clearable
        >
          <template v-slot:label>
            <span class="field-label">Автор<span class="required-star">*</span></span>
          </template>
        </v-select>

        <v-number-input
          v-model="formData.year"
          variant="outlined"
          color="primary"
          bg-color="grey-lighten-4"
          class="form-field"
          :error="hasError('year')"
          :error-messages="getError('year')"
          density="comfortable"
          clearable
        >
          <template v-slot:label>
            <span class="field-label">Год выпуска<span class="required-star">*</span></span>
          </template>
        </v-number-input>

        <v-text-field
          v-model="formData.isbn"
          variant="outlined"
          color="primary"
          bg-color="grey-lighten-4"
          class="form-field"
          :error="hasError('isbn')"
          :error-messages="getError('isbn')"
          density="comfortable"
          clearable
        >
          <template v-slot:label>
            <span class="field-label">ISBN</span>
          </template>
        </v-text-field>
      </div>
    </scroll-center-dialog>
  </div>
</template>

<style scoped>
.items-container {
  padding: 24px;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  min-height: calc(100vh - 64px);
  overflow: auto;
}

.items-card {
  border-radius: 16px;
  overflow: hidden;
  background: white;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.08);
}

.card-header {
  background: linear-gradient(135deg, #1976d2 0%, #0d47a1 100%);
  color: white;
  padding: 10px 20px;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title-section {
  display: flex;
  align-items: center;
}

.page-title {
  font-size: 2rem;
  margin-bottom: 12px;
  margin-left: 12px;
  font-weight: 600;
  color: #1976d2;
}

.add-btn {
  background: rgba(255, 255, 255, 0.15);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
  transition: all 0.3s ease;
}

.add-btn:hover {
  background: rgba(255, 255, 255, 0.25);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.table-container {
  padding: 0;
}

.items-table {
  border-radius: 0;
}

.action-btn {
  transition: all 0.2s ease;
}

.action-btn:hover {
  transform: scale(1.1);
}

.loading-overlay {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 80px 0;
  background: rgba(255, 255, 255, 0.8);
}

.loading-text {
  margin-top: 20px;
  color: #666;
  font-size: 1.1rem;
}

.form-content {
  padding: 8px;
}

.form-field {
  margin-bottom: 20px;
}

.field-label {
  font-weight: 500;
  color: #424242;
}

.required-star {
  color: #f44336;
  margin-left: 2px;
}

@media (max-width: 960px) {
  .items-container {
    padding: 16px;
  }

  .page-title {
    font-size: 1.75rem;
  }
}

@media (max-width: 600px) {
  .items-container {
    padding: 12px;
  }

  .card-header {
    padding: 20px;
  }

  .page-title {
    font-size: 1.5rem;
  }

}

.custom-footer {
  display: flex;
  justify-content: end;
  align-items: center;
  padding: 8px 16px;
  border-top: 1px solid rgba(128, 128, 128, 0.3);
  border-radius: 0 0 4px 4px;
}

.footer-controls {
  display: flex;
  justify-items: end;
  justify-content: flex-end;
  align-items: center;
  gap: 16px;
}

.footer-btn {
  min-width: 36px;
  color: #1976d2;
  border: 1px solid #1976d2;
  background-color: #ffffff;
}

.footer-btn:hover:not(:disabled) {
  background-color: #bbdefb;
}

.footer-btn:disabled {
  color: #90caf9;
  border-color: #e3f2fd;
}

.items-per-page-selector {
  display: flex;
  align-items: center;
  gap: 8px;
}

.selector-label {
  font-size: 14px;
  color: #1565c0;
}

.page-select {
  width: 100px;
}

</style>
