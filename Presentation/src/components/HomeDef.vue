<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title>{{ $t('home.formtitle')}}</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-form>
              <v-text-field
                :label="$t('home.group')"
                name="group"
                prepend-icon="mdi-account-group"
                type="text"
              />

              <v-text-field
                :label="$t('home.name')"
                name="name"
                prepend-icon="mdi-account"
                type="text"
              />

              <v-text-field
                :append-icon="showKey ? 'mdi-eye' : 'mdi-eye-off'"
                :type="showKey ? 'text' : 'password'"
                @click:append="showKey = !showKey"
                id="key"
                :label="$t('home.key')"
                name="key"
                prepend-icon="mdi-key"
              />
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-btn color="secondary">{{ $t('home.create-group')}}</v-btn>
            <v-spacer />
            <v-btn color="primary">{{ $t('home.draw')}}</v-btn>
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
    showKey: false
  })
};
</script>