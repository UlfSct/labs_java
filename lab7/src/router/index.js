import { createRouter, createWebHistory } from 'vue-router'
import Orders from "@/modules/Orders.vue";
import Books from "@/modules/Books.vue";
import Authors from "@/modules/Authors.vue";

export const names = {
  ORDERS: 'Orders',
  BOOKS: 'Books',
  AUTHORS: 'Authors'
}

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: names.ORDERS,
      component: Orders
    },
    {
      path: '/books',
      name: names.BOOKS,
      component: Books
    },
    {
      path: '/authors',
      name: names.AUTHORS,
      component: Authors
    }
  ]
})

export default router
