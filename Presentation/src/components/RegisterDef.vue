<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title>{{ $t('register.formtitle')}}</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-form>
              <v-text-field
                :label="$t('register.email')"
                name="name"
                prepend-icon="mdi-email"
                type="email"
              />

              <v-text-field
                :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                :type="showPassword ? 'text' : 'password'"
                @click:append="showPassword = !showPassword"
                id="password"
                :label="$t('register.password')"
                name="password"
                prepend-icon="mdi-lock"
              />
              <v-text-field
                :append-icon="showPasswordSec ? 'mdi-eye' : 'mdi-eye-off'"
                :type="showPasswordSec ? 'text' : 'password'"
                @click:append="showPasswordSec = !showPasswordSec"
                id="passwordSec"
                :label="$t('register.passwordsec')"
                name="passwordSed"
                prepend-icon="mdi-lock"
              />
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-btn color="secondary" v-on:click="routeHome">{{ $t('register.cancel')}}</v-btn>
            <v-spacer />
            <v-btn color="primary">{{ $t('register.submit')}}</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  props: {
    source: String
  },
  data: () => ({
    showPassword: false,
    showPasswordSec: false
  }),
  methods: {
    routeHome: function() {
      this.$router.push({ path: "/" });
    }
  }
};
</script>