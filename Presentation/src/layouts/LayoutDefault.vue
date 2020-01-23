<template>
  <v-app id="inspire">
    <v-navigation-drawer name="navigation" id="navigation" v-model="drawer" app clipped>
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
      </v-list>
      <template v-slot:append>
        <v-list>
          <v-divider></v-divider>
          <v-list-item>
            <v-switch
              v-model="themeSwitch"
              v-on:change="changeTheme"
              :label="$t('global.themeswitch')"
              color="primary"
              class="mt-0"
              hide-details
            ></v-switch>
          </v-list-item>
          <v-divider></v-divider>
          <v-list-item link v-on:click="routeHelp">
            <v-list-item-action>
              <v-icon>mdi-help</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>{{ $t('navigation.help')}}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item link v-on:click="routeGithub">
            <v-list-item-action>
              <v-icon>mdi-github-circle</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>{{ $t('navigation.github')}}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </template>
    </v-navigation-drawer>

    <v-app-bar name="header" id="header" app clipped-left>
      <v-app-bar-nav-icon name="changenav" id="changenav" @click.stop="drawer = !drawer" />
      <v-toolbar-title>{{ $t('global.title')}}</v-toolbar-title>
      <v-spacer></v-spacer>
      <div class="language-width">
        <v-select
          v-model="$i18n.locale"
          name="language"
          id="language"
          :items="langs"
          :label="$t('global.language')"
          outlined
          dense
          hide-details
        ></v-select>
      </div>
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
import store from "./../store";
import { changedarktheme } from "./../store";

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
    routeHome: function() {
      this.$router.push({ path: "/" }).catch(err => {});
    },
    routeNewGroup: function() {
      this.$router.push({ path: "/newgroup" }).catch(err => {});
    },
    changeTheme: function() {
      this.$vuetify.theme.dark = this.themeSwitch;
      store.commit("changedarktheme", this.themeSwitch);
    },
    routeGithub: function() {
      window.location = "https://github.com/mschoeffel/SecretSanta";
    },
    routeHelp: function() {
      this.$router.push({ path: "/help" }).catch(err => {});
    }
  }
};
</script>