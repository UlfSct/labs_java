import {defineStore} from "pinia";
import {sendRequest} from "@/utils/requests.js";
import {urls} from "@/utils/urls.js";

export const useOrdersStore = defineStore('orders', {
  state: () => ({
    items: [],
    detail: null,
    loading: {
      list: false,
      detail: false,
      create: false,
      update: false,
      delete: false
    },
    pagination: {
      page: 1,
      size: 10,
      first: true,
      last: true,
      count: 0,
      search: null
    }
  }),
  getters: {
    getItems: (state) => state.items,
    getDetail: state => state.detail,
    isLoadingList: (state) => state.loading.list,
    isLoadingDetail: (state) => state.loading.detail,
    isLoadingDelete: (state) => state.loading.delete,
    isLoadingUpdate: (state) => state.loading.edit,
    isLoadingCreate: (state) => state.loading.create,
    getPagination: (state) => state.pagination,
  },
  actions: {
    setNextPage() {
      if (this.pagination.last) return
      this.pagination.page++
    },
    setPreviousPage() {
      if (this.pagination.first) return
      this.pagination.page--
    },
    setNewSize(size) {
      this.pagination.page = 1
      this.pagination.size = size
    },
    setSearch(search) {
      this.pagination.search = search ? search : null
      this.pagination.page = 1
    },
    async loadItems() {
      this.loading.list = true
      let response = await sendRequest(urls.ORDERS.LIST, {}, {}, {
        page: this.pagination.page,
        size: this.pagination.size,
        search: this.pagination.search
      })
      this.items = response.content
      this.pagination.page = response.number + 1
      this.pagination.size = response.size
      this.pagination.first = response.first
      this.pagination.last = response.last
      this.pagination.count = response.totalElements
      setTimeout(() => {
        this.loading.list = false
      }, 1000)
    },
    async loadDetail(id) {
      this.loading.detail = true
      this.detail = await sendRequest(urls.ORDERS.DETAIL, {}, {id})
      setTimeout(() => {
        this.loading.detail = false
      }, 1000)
    },
    async updateItem(id, data) {
      this.loading.update = true
      try {
        await sendRequest(urls.ORDERS.UPDATE, data, {id})
      } catch (e) {
        throw e
      } finally {
        setTimeout(() => {
          this.loading.update = false
        }, 1000)
      }
    },
    async createItem(data) {
      this.loading.create = true
      try {
        await sendRequest(urls.ORDERS.CREATE, data)
      } catch (e) {
        throw e
      } finally {
        setTimeout(() => {
          this.loading.create = false
        }, 1000)
      }
    },
    async deleteItem(id) {
      this.loading.delete = true
      try {
        await sendRequest(urls.ORDERS.DELETE, {}, {id})
      } catch (e) {
        throw e
      } finally {
        setTimeout(() => {
          this.loading.delete = false
        }, 1000)
      }
    }
  }
})
