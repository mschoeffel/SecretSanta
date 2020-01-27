<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="12" md="8">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title name="title" id="title">{{ $t('help.formtitle')}}</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-expansion-panels>
              <v-expansion-panel>
                <v-expansion-panel-header>{{$t('help.firststeps')}}</v-expansion-panel-header>
                <v-expansion-panel-content>{{$t('help.firststeps-content')}}</v-expansion-panel-content>
              </v-expansion-panel>
              <v-expansion-panel>
                <v-expansion-panel-header>{{$t('help.newgroup')}}</v-expansion-panel-header>
                <v-expansion-panel-content>{{$t('help.newgroup-content')}}</v-expansion-panel-content>
              </v-expansion-panel>
              <v-expansion-panel>
                <v-expansion-panel-header>{{$t('help.showgroup')}}</v-expansion-panel-header>
                <v-expansion-panel-content>{{$t('help.showgroup-content')}}</v-expansion-panel-content>
              </v-expansion-panel>
              <v-expansion-panel>
                <v-expansion-panel-header>{{$t('help.drawmember')}}</v-expansion-panel-header>
                <v-expansion-panel-content>{{$t('help.drawmember-content')}}</v-expansion-panel-content>
              </v-expansion-panel>
            </v-expansion-panels>
          </v-card-text>
          <v-card-actions>
            <v-btn
              name="routehome"
              id="routehome"
              color="secondary"
              v-on:click="routeHome"
            >{{ $t('help.home')}}</v-btn>
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
  data: () => ({}),
  methods: {
    routeHome: function() {
      this.$router.push({ path: "/" });
    }
  }
};
</script>