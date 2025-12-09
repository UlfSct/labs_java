export const urls = {
  AUTHORS: {
    LIST: {
      path: '/authors',
      method: 'GET'
    },
    DETAIL: {
      path: '/authors/{id}',
      method: 'GET'
    },
    CREATE: {
      path: '/authors',
      method: 'POST'
    },
    UPDATE: {
      path: '/authors/{id}',
      method: 'PUT'
    },
    DELETE: {
      path: '/authors/{id}',
      method: 'DELETE'
    },
  },
  BOOKS: {
    LIST: {
      path: '/books',
      method: 'GET'
    },
    DETAIL: {
      path: '/books/{id}',
      method: 'GET'
    },
    CREATE: {
      path: '/books',
      method: 'POST'
    },
    UPDATE: {
      path: '/books/{id}',
      method: 'PUT'
    },
    DELETE: {
      path: '/books/{id}',
      method: 'DELETE'
    },
  },
  ORDERS: {
    LIST: {
      path: '/orders',
      method: 'GET'
    },
    DETAIL: {
      path: '/orders/{id}',
      method: 'GET'
    },
    CREATE: {
      path: '/orders',
      method: 'POST'
    },
    UPDATE: {
      path: '/orders/{id}',
      method: 'PUT'
    },
    DELETE: {
      path: '/orders/{id}',
      method: 'DELETE'
    },
  },
  SELECTORS: {
    AUTHORS: {
      path: '/selectors/authors',
      method: 'GET'
    },
    BOOKS: {
      path: '/selectors/books',
      method: 'GET'
    },
  }
}
