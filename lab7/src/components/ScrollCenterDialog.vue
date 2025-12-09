<template>
  <v-dialog
    v-model="open"
    :width="width"
    :fullscreen="fullscreen"
    persistent
    scrollable
  >
    <v-card rounded="xl">
      <v-toolbar density="default" :class="leftToolbarButtonEnabled ? 'px-2' : 'pr-2'" :color="topColor">
        <slot name="toolbar-left-button"></slot>
        <v-spacer v-if="leftToolbarButtonEnabled"></v-spacer>
        <slot name="title">
          <span :class="getTitleClasses()" class="color--corporate-main">
            {{ title }}
          </span>
        </slot>
        <v-spacer v-if="spacedTitle"></v-spacer>
        <slot name="beforeCloseButton"></slot>
      </v-toolbar>
      <v-card-text
        ref="dialog-scroll-container"
        :class="fabButtonEnabled && showFabButton() ? 'mb-n10' : itemClass"
        class="overflow-y-auto scrolled-style py-0 px-4 px-md-6"
        @scroll="scrollHandler"
        :style="getHeightStyle()"
      >
        <template v-if="alertsEnabled">
          <v-row no-gutters style="position: absolute; z-index: 100; width: calc(100% - 12px);">
            <v-col cols="12" v-for="(alert, i) in alerts" :key="`alert_${i}`" class="mt-2">
              <v-card class="mr-8 elevation-8 pa-2 rounded-lg">
                <v-alert
                  class="ma-0 rounded-0 pa-2"
                  style="background: white!important; color: #0D0D0D!important;"
                  color="warning"
                  dismissible
                  outlined
                >
                  {{ alert }}
                  <template v-slot:close>
                    <v-btn
                      color="warning"
                      icon
                      @click="$emit('update:alerts', getUpdatedAlertsArray(i))"
                    >
                      <v-icon>cancel</v-icon>
                    </v-btn>
                  </template>
                </v-alert>
              </v-card>
            </v-col>
          </v-row>
        </template>
        <slot></slot>
      </v-card-text>
      <v-card-actions class="px-5" v-if="bottomPanelEnabled">
        <slot name="actions">
          <slot name="prepend-actions"></slot>
          <v-spacer v-if="left"></v-spacer>
          <v-btn
            v-if="dennyble"
            :color="dennyColor"
            variant="text"
            rounded="xl"
            class="text-h6 text-uppercase"
            :loading="loading"
            :readonly="loading"
            @click="closeDialog(false)"
          >
            {{ dennyText }}
          </v-btn>
          <v-spacer v-if="between"></v-spacer>
          <v-btn
            v-if="applyable"
            :color="applyColor"
            variant="text"
            rounded="xl"
            class="text-h6 text-uppercase"
            :loading="loading"
            :readonly="loading"
            @click="closeDialog(true)"
          >
            {{ applyText }}
          </v-btn>
          <v-spacer v-if="right"></v-spacer>
          <slot name="append-actions"></slot>
        </slot>
      </v-card-actions>
      <v-fab
        v-if="fabButtonEnabled && showFabButton()"
        color="primary"
        style="color: white!important;"
        class="elevation-6 mr-10 mb-10 rounded-xl"
        absolute
        :icon="fabButtonIcon"
        :loading="fabButtonLoading()"
        @click="$emit('fabButtonClicked')"
      ></v-fab>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "ScrollCenterDialog",
  props: {
    value: Boolean,
    title: String,
    scrolled: {
      default: true,
      type: Boolean
    },
    itemClass: {
      default: '',
    },
    fullscreen: {
      default: false,
      type: Boolean
    },
    closed: {
      default: true,
      type: Boolean
    },
    spacedTitle: {
      default: true,
      type: Boolean,
    },
    between: {
      default: false,
      type: Boolean,
    },
    left: {
      default: true,
      type: Boolean,
    },
    right: {
      default: false,
      type: Boolean,
    },
    loading: {
      default: false,
      type: Boolean,
    },
    dennyText: {
      default: 'Отмена',
      type: String,
    },
    dennyColor: {
      default: 'corporate_main',
      type: String,
    },
    applyable: {
      default: true,
      type: Boolean,
    },
    dennyble: {
      default: true,
      type: Boolean,
    },
    applyText: {
      default: 'Сохранить',
      type: String,
    },
    applyColor: {
      default: 'corporate_main',
      type: String,
    },
    topColor: {
      default: 'blue_shades_focus',
      type: String
    },
    width: {
      default: '600px',
      type: String,
    },
    contentHeight: {
      default: undefined,
      type: String
    },
    bottomPanelEnabled: {
      default: true,
      type: Boolean,
    },
    needScrollHandler: {
      default: false,
      type: Boolean
    },
    leftToolbarButtonEnabled: {
      default: false,
      type: Boolean,
    },
    fabButtonEnabled: {
      default: false,
      required: false,
    },
    showFabButton: {
      default: () => false,
      type: Function
    },
    fabButtonIcon: {
      default: 'edit',
      type: String,
    },
    fabButtonLoading: {
      default: () => false,
      type: Function
    },
    alertsEnabled: {
      default: false,
      type: Boolean
    },
    alerts: {
      default: () => [],
      required: false,
    }
  },
  data () {
    return {
      open: false,
    }
  },
  watch: {
    value: {
      immediate: true,
      handler () {
        this.open = this.value
      }
    },
    open: {
      immediate: true,
      handler () {
        this.$emit('input', this.open)
      }
    }
  },
  methods: {
    closeDialog (emit_apply = false) {
      if (emit_apply) {
        this.$emit('apply')
      } else {
        this.$emit('close')
      }
    },
    getHeightStyle(){
      if (this.$vuetify.display.smAndDown)
        return {height: 'calc(100% - ' + !this.bottomPanelEnabled ? 108 : 56 + 'px)'}
      else
        return {height: this.contentHeight ? this.contentHeight: undefined}
    },
    scrollHandler() {
      if (this.needScrollHandler) {
        let scroll_px_left = this.$refs["dialog-scroll-container"].$el.scrollHeight -
          (this.$refs["dialog-scroll-container"].$el.clientHeight + this.$refs["dialog-scroll-container"].$el.scrollTop)
        this.$emit('updateFullScrolled', scroll_px_left <= 480)
      }
    },
    getUpdatedAlertsArray(i) {
      let array = this.alerts
      array.splice(i, 1)
      return array
    },
    getTitleClasses() {
      let classes = 'text-h6'
      if (this.leftToolbarButtonEnabled) return classes
      return classes + (this.$vuetify.display.mdAndUp ? ' pl-6' : ' pl-5')
    }
  }
}
</script>

<style scoped>
.scrolled-style::-webkit-scrollbar {
  width: 5px;
}

.scrolled-style::-webkit-scrollbar-track {
}

.scrolled-style::-webkit-scrollbar-thumb {
  background: gray;
  border-bottom-left-radius: 10px;
  border-top-left-radius: 10px;
}
</style>
