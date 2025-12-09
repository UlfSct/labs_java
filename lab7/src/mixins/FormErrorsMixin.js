import { reactive } from 'vue';

function isDict(v) {
  return typeof v === 'object' && v !== null && !(v instanceof Array) && !(v instanceof Date);
}

export function useFormErrors() {
  const state = reactive({
    errors: {
      fields: {},
    },
  });

  const mapErrors = (data) => {
    state.errors.fields = data;
  };

  const hasError = (field) => {
    return getError(field) !== null;
  };

  const getError = (field, errors = undefined) => {
    if (errors === undefined) {
      errors = state.errors.fields;
    }
    for (let val in errors) {
      if (isDict(errors[val])) {
        if (field !== undefined && field !== null && field.includes('.')) {
          return getError(field.split('.').slice(1).join('.'), errors[val]);
        }
      }
      if ((errors[val]).length > 0) {
        if (val === field) {
          return errors[val];
        }
      }
    }
    return null;
  };

  const deleteError = (field, id = undefined) => {
    if (id !== undefined) {
      if (state.errors.fields[id]) {
        delete state.errors.fields[id][field];
        if (Object.keys(state.errors.fields[id]).length === 0) {
          delete state.errors.fields[id];
        }
      }
    } else {
      delete state.errors.fields[field];
    }
  };

  const clearErrors = () => {
    state.errors.fields = {};
  }

  return {
    state,
    mapErrors,
    hasError,
    getError,
    deleteError,
    clearErrors
  };
}
