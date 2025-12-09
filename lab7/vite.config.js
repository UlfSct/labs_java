import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'node:url'

export default defineConfig(({ mode }) => {
  // Используем текущую директорию через import.meta.url
  const currentDir = fileURLToPath(new URL('.', import.meta.url))

  // Загрузка env переменных
  const env = loadEnv(mode, currentDir, 'VUE_APP_')
  // Создаем объект для define
  const defineEnv = {}
  Object.keys(env).forEach(key => {
    defineEnv[`import.meta.env.${key}`] = JSON.stringify(env[key])
  })

  return {
    plugins: [vue()],

    resolve: {
      alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url))
      }
    },

    define: {
      ...defineEnv,
      __APP_ENV__: JSON.stringify(mode)
    },

    server: {
      port: 3000,
      host: true
    }
  }
})
