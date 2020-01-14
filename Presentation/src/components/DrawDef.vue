<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title>{{ $t('draw.title')}}</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-form>
              <p class="font-weight-medium text-center">{{ $t('draw.message')}}</p>
              <br />
              <p class="font-weight-bold text-center">TEST</p>
              <br />
              <p>{{ $tc('draw.autoaccept', 5)}}</p>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-tooltip bottom>
              <template v-slot:activator="{ on }">     
                <v-badge color="green" content="6" bordered overlap>
                <v-btn color="secondary" dark v-on="on">{{ $t('draw.reroll')}}</v-btn>
              </template>
              <span>{{ $tc('draw.reroll-tooltip', 1)}}</span>
            </v-tooltip>
            <v-spacer />
            <v-btn color="primary">{{ $t('draw.accept')}}</v-btn>
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