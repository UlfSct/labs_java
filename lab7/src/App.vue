<script setup lang="js">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { names } from '@/router/index.js'

const router = useRouter()

const menuItems = ref([
  {
    name: names.ORDERS,
    title: 'Заказы',
    path: '/'
  },
  {
    name: names.BOOKS,
    title: 'Книги',
    path: '/books'
  },
  {
    name: names.AUTHORS,
    title: 'Авторы',
    path: '/authors'
  }
])


</script>

<template>
  <v-app>
    <v-app-bar
      color="#0d47a1"
      dark
      flat
    >
      <v-app-bar-title>
        <router-link to="/" class="text-white text-decoration-none">
          Библиотека
        </router-link>
      </v-app-bar-title>

      <div class="d-none d-md-flex">
        <v-btn
          v-for="item in menuItems"
          :key="item.name"
          :to="item.path"
          variant="text"
          color="white"
          class="mx-1"
        >
          {{ item.title }}
        </v-btn>
      </div>
      <v-spacer class="d-md-none"></v-spacer>
      <v-menu class="d-md-none">
        <template v-slot:activator="{ props }">
          <v-btn
            icon
            v-bind="props"
            color="white"
          >
            <v-icon>mdi-menu</v-icon>
          </v-btn>
        </template>
        <v-list nav bg-color="blue-accent-2">
          <v-list-item
            v-for="item in menuItems"
            :key="item.name"
            :value="item.name"
            :to="item.path"
            active-class="blue-darken-2"
          >
            <v-list-item-title class="text-white">
              {{ item.title }}
            </v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>
    <div class="mt-16">
      <router-view/>
    </div>
  </v-app>
</template>

<style scoped></style>
