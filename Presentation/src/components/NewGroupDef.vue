<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title>{{ $t('new-group.formtitle')}}</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-stepper v-model="step" non-linear>
              <v-stepper-header>
                <v-stepper-step key="group" step="1">{{ $t('new-group.group')}}</v-stepper-step>
                <v-divider></v-divider>
                <v-stepper-step key="member" step="2">{{ $t('new-group.member')}}</v-stepper-step>
              </v-stepper-header>
              <v-stepper-items>
                <v-stepper-content key="group" step="1">
                  <v-form>
                    <v-text-field
                      :label="$t('new-group.name')"
                      name="name"
                      prepend-icon="mdi-account-group"
                      type="text"
                    />
                    <v-text-field
                      :label="$t('new-group.membercount')"
                      v-model.number="membercount"
                      name="name"
                      prepend-icon="mdi-numeric"
                      type="number"
                    />
                    <v-divider></v-divider>
                    <v-checkbox
                      v-model="checkbox"
                      name="email"
                      color="primary"
                      :label="$t('new-group.email')"
                      prepend-icon="mdi-email-lock"
                    ></v-checkbox>
                    <v-divider></v-divider>
                    <v-checkbox
                      v-model="checkboxRerolls"
                      color="primary"
                      :label="$t('new-group.rerolls')"
                      prepend-icon="mdi-sync"
                    ></v-checkbox>
                    <v-text-field
                      :label="$t('new-group.rerolls-slider')"
                      name="name"
                      prepend-icon="mdi-sync"
                      :disabled="!checkboxRerolls"
                      type="number"
                    />
                  </v-form>
                </v-stepper-content>
                <v-stepper-content key="member" step="2">
                  <v-list>
                    <div v-for="n in membercount">
                      <v-subheader>{{n}}</v-subheader>
                      <v-list-item-group color="primary">
                        <v-list-item dense>
                          <v-list-item-content>
                            <v-text-field
                              :label="$t('new-group.member-name')"
                              name="name"
                              prepend-icon="mdi-account"
                              type="text"
                            />
                          </v-list-item-content>
                        </v-list-item>
                        <v-list-item dense v-if="checkbox">
                          <v-list-item-content>
                            <v-text-field
                              :label="$t('new-group.member-email')"
                              name="name"
                              prepend-icon="mdi-email"
                              type="text"
                            />
                          </v-list-item-content>
                        </v-list-item>
                      </v-list-item-group>
                      <v-divider v-if="n < membercount"></v-divider>
                    </div>
                  </v-list>
                </v-stepper-content>
              </v-stepper-items>
            </v-stepper>
          </v-card-text>
          <v-card-actions>
            <v-btn color="secondary" v-on:click="stepBack">{{ $tc('new-group.back', step)}}</v-btn>
            <v-spacer />
            <v-btn color="primary" v-on:click="stepForward">{{ $tc('new-group.next', step)}}</v-btn>
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
    slider: 10,
    checkbox: false,
    checkboxRerolls: false,
    sliderRerolls: 0,
    minRerolls: 0,
    maxRerolls: 5,
    step: 1,
    membercount: 0
  }),
  methods: {
    stepBack: function() {
      if (this.step === 1) {
        this.$router.push({ path: "/" });
      } else {
        this.step -= 1;
      }
    },
    stepForward: function() {
      if (this.step === 2) {
        alert("Absenden");
      } else {
        this.step += 1;
      }
    }
  }
};
</script>