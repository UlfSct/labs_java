<template>
  <v-dialog persistent :model-value="opened" width="500" :disabled="loading">
    <v-card>
      <v-card-title style="word-break: break-word!important;">{{ titleText }}</v-card-title>
      <v-divider></v-divider>
      <v-card-text class="pt-5 pb-1">
        <span v-html="getText()" v-if="getText !== undefined && opened"></span>
        <slot v-else></slot>
        <v-alert v-if="error && error.length > 0" type="error" class="mb-0 mt-2">
          {{ error }}
        </v-alert>
      </v-card-text>
      <v-card-actions>
          <v-btn
              id="apply_btn"
              @click="applyDialog"
              :loading="loading"
              color="warning"
              text
          >
            {{ applyText }}
          </v-btn>
          <v-btn
              id="close_btn"
              :loading="loading"
              @click="closeDialog"
              text
          >
            {{ denyText }}
          </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "ApplyDialog",
  props: {
    getText: {
      type: Function,
      default: undefined
    },
    opened: Boolean,
    error: {
      type: String,
      default: '',
      required: false
    },
    titleText: {
      type: String,
      required: false,
      default: "Подтвердите изменение"
    },
    applyText: {
      type: String,
      required: false,
      default: "Подтвердить"
    },
    denyText: {
      type: String,
      required: false,
      default: "Отменить"
    },
    loading: {
      type: Boolean,
      required: false,
      default: false,
    }
  },
  data: function () {
    return {
      // opened: true
    }
  },
  methods: {
    closeDialog: function () {
      this.$emit("close", false)
    },
    applyDialog: function () {
      this.$emit("close", true)
    }

  }
}
</script>

<style scoped>

</style>
