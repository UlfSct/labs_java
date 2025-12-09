import { config } from '@/utils/config.js'

const getDefaultHeaders = () => {
  return {
    'Content-Type': 'application/json'
  }
}

const constructUrl = (path, params = {}, gets = null) => {
  let parts = path.split('/')
  for (let i = 0; i < parts.length; i++) {
    if (parts[i][0] !== '{') continue
    let paramValueFound = false
    let paramName = parts[i].slice(1, parts[i].length - 1)
    for (let param in params) {
      if (param !== paramName) continue
      paramValueFound = true
      parts[i] = params[paramName]
    }
    if (!paramValueFound) {
      console.error(`Параметр ${paramName} не задан при отправке запроса`)
      return null
    }
  }

  let url = `${config.api}${parts.join('/')}`
  if (gets) return addGetParameters(url, gets)
  return url
}

const addGetParameters = (url, parameters) => {
  const urlObject = new URL(url)
  Object.keys(parameters).forEach((key) => {
    if (parameters[key] === undefined || parameters[key] === null) {
      urlObject.searchParams.delete(key)
    } else {
      urlObject.searchParams.set(key, String(parameters[key]))
    }
  })
  return urlObject.toString()
}

export const sendRequest = async (
  urlObject,
  data = {},
  params = {},
  gets = null,
  headers = getDefaultHeaders()
) => {
  let url = constructUrl(urlObject.path, params, gets)
  if (!url) return
  if (!headers) return

  const options = {
    method: urlObject.method,
    headers: headers,
    mode: 'cors'
  }

  if (urlObject.method !== 'GET') {
    options.body = JSON.stringify(data)
  }

  let response = await fetch(url, options)
  if (!response.ok) {
    throw await response.json()
  }
  return response.json()
}
