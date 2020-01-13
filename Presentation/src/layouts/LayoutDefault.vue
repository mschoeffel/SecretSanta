<template>
  <v-app id="inspire">
    <v-navigation-drawer
      v-model="drawer"
      app
      clipped
    >
      <v-list dense>
        <v-list-item link>
          <v-list-item-action>
            <v-icon>mdi-view-dashboard</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Dashboard</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link>
          <v-list-item-action>
            <v-icon>mdi-settings</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Settings</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar
      app
      clipped-left
    >
      <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
      <v-toolbar-title>{{ $t('global.title')}}</v-toolbar-title>
      <v-spacer></v-spacer>
      <div class
      <div class="language-width">
      <v-select
                v-model="$i18n.locale"
                :items="langs"
                :label="$t('global.language')"
                outlined
                dense
                hide-details
      ></v-select>
      </div>
      <v-btn v-on:click="routeLogin" icon>
        <v-icon>mdi-login</v-icon>
      </v-btn>

    </v-app-bar>

    <v-content>
     <slot/>
    </v-content>

    <v-footer app>
      <span>&copy; 2019</span>
    </v-footer>
  </v-app>
</template>

<style scoped>
.language-width {
    width: 100px;
}
</style>

<script>
  export default {
    name: 'LayoutDefault',
    props: {
      source: String,
    },
    data: () => ({
      drawer: false,
      langs:['en', 'de'],
    }),
    methods: {
        routeLogin: function() {
            this.$router.push({path: '/login'})
        }
    }
  }
</script>