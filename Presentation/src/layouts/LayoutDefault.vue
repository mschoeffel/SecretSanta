<template>
  <v-app id="inspire">
    <v-navigation-drawer v-model="drawer" app clipped>
      <v-list dense>
        <v-list-item link v-on:click="routeHome">
          <v-list-item-action>
            <v-icon>mdi-home</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>{{ $t('navigation.home')}}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link v-on:click="routeNewGroup">
          <v-list-item-action>
            <v-icon>mdi-account-group</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>{{ $t('navigation.create-group')}}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-divider></v-divider>
        <v-list-item link v-on:click="routeLogin">
          <v-list-item-action>
            <v-icon>mdi-login</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>{{ $t('navigation.login')}}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link v-on:click="routeRegister">
          <v-list-item-action>
            <v-icon>mdi-account-plus</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>{{ $t('navigation.register')}}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-divider></v-divider>
        <v-list-item>
          <v-switch v-model="themeSwitch" v-on:change="changeTheme" :label="$t('global.themeswitch')" color="primary" hide-details></v-switch>
        </v-list-item>
        <v-list-item link v-on:click="routeTest">
          <v-list-item-action>
            <v-icon>mdi-help</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>{{ $t('navigation.help')}}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link>
          <v-list-item-action>
            <v-icon>mdi-github-circle</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>{{ $t('navigation.github')}}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app clipped-left>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
      <v-toolbar-title>{{ $t('global.title')}}</v-toolbar-title>
      <v-spacer></v-spacer>
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
      <slot />
    </v-content>

    <v-footer app>
      <span>&copy; 2019 {{ $t('global.versionnumber')}}</span>
    </v-footer>
  </v-app>
</template>

<style scoped>
.language-width {
  width: 100px;
}
</style>

<script>
import store from './../store'
import { changedarktheme } from './../store'

export default {
  name: "LayoutDefault",
  props: {
    source: String
  },
  data: () => ({
    drawer: false,
    langs: ["en", "de"],
    themeSwitch: store.getters.getdarktheme
  }),
  methods: {
    routeLogin: function() {
      this.$router.push({ path: "/login" });
    },
    routeHome: function() {
      this.$router.push({ path: "/" });
    },
    routeTest: function() {
      this.$router.push({ path: "/draw" });
    },
    routeRegister: function() {
      this.$router.push({ path: "/register" });
    },
    routeNewGroup: function() {
      this.$router.push({ path: "/newgroup" });
    },
    changeTheme: function() {
      this.$vuetify.theme.dark = this.themeSwitch;
      store.commit('changedarktheme', this.themeSwitch);
    }
  }
};
</script>