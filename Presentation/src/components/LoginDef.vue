<template>
      <v-container
        class="fill-height"
        fluid
      >
        <v-row
          align="center"
          justify="center"
        >
          <v-col
            cols="12"
            sm="8"
            md="4"
          >
            <v-card class="elevation-12">
              <v-toolbar
                color="primary"
                dark
                flat
              >
                <v-toolbar-title>{{ $t('login.formtitle')}}</v-toolbar-title>
              </v-toolbar>
              <v-card-text>
                <v-form>
                  <v-text-field
                    :label="$t('login.account')"
                    name="name"
                    prepend-icon="mdi-account"
                    type="text"
                  />

                  <v-text-field
                    :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="showPassword ? 'text' : 'password'"
                    @click:append="showPassword = !showPassword"
                    id="key"
                    :label="$t('login.password')"
                    name="password"
                    prepend-icon="mdi-lock"
                  />
                </v-form>
              </v-card-text>
              <v-card-actions>
              <v-btn color="secondary" v-on:click="routeHome">{{ $t('login.back')}}</v-btn>
                <v-spacer />
                <v-btn color="primary">{{ $t('login.submit')}}</v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
</template>

<script>
  export default {
    props: {
      source: String,
    },
    data: () => ({
        showPassword: false
      }),
      methods: {
              routeHome: function() {
                  this.$router.push({path: '/'})
              }
          }
  }
</script>