<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title>{{ $t('new-group.formtitle')}}</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-form>
              <v-text-field
                :label="$t('new-group.name')"
                name="name"
                prepend-icon="mdi-account-group"
                type="text"
              />
              <v-subheader>{{ $t('new-group.membercount')}}</v-subheader>
              <v-slider
                v-model="slider"
                class="align-center"
                :max="max"
                :min="min"
                hide-details
                prepend-icon="mdi-numeric"
              >
                <template v-slot:append>
                  <v-text-field
                    v-model="slider"
                    class="mt-0 pt-0"
                    hide-details
                    single-line
                    type="number"
                    style="width: 60px"
                  ></v-text-field>
                </template>
              </v-slider>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-btn color="secondary" v-on:click="routeHome">{{ $t('new-group.cancel')}}</v-btn>
            <v-spacer />
            <v-btn color="primary">{{ $t('new-group.next')}}</v-btn>
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
    min: 0,
    max: 50,
    slider: 10
  }),
  methods: {
    routeHome: function() {
      this.$router.push({ path: "/" });
    }
  }
};
</script>