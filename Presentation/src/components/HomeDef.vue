<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="12" md="8">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title>{{ $t('home.formtitle')}}</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-stepper v-model="step" alt-labels>
              <v-stepper-header>
                <v-stepper-step key="input" step="1">{{ $t('home.input')}}</v-stepper-step>
                <v-divider></v-divider>
                <v-stepper-step key="result" step="2">{{ $t('home.result')}}</v-stepper-step>
              </v-stepper-header>
              <v-stepper-items>
                <v-stepper-content key="input" step="1">
                  <v-form>
                    <v-alert
                      v-if="displayerror.length > 0"
                      type="error"
                      dismissible
                    >{{ displayerror }}</v-alert>
                    <v-text-field
                      :label="$t('home.group')"
                      :error-messages="$t(credentialserror)"
                      name="groupname"
                      v-model="groupname"
                      prepend-icon="mdi-account-group"
                      type="text"
                    />

                    <v-text-field
                      :label="$t('home.name')"
                      :error-messages="$t(credentialserror)"
                      name="name"
                      v-model="name"
                      prepend-icon="mdi-account"
                      type="text"
                    />

                    <v-text-field
                      :append-icon="showKey ? 'mdi-eye' : 'mdi-eye-off'"
                      :type="showKey ? 'text' : 'password'"
                      @click:append="showKey = !showKey"
                      id="key"
                      :label="$t('home.key')"
                      :error-messages="$t(credentialserror)"
                      name="key"
                      v-model="key"
                      prepend-icon="mdi-key"
                    />
                  </v-form>
                </v-stepper-content>
                <v-stepper-content key="result" step="2">
                  <v-alert v-if="displayinfo.length > 0" type="info" dismissible>{{ displayinfo }}</v-alert>
                  <v-form>
                    <p class="font-weight-medium text-center">{{ $t('home.message')}}</p>
                    <br />
                    <p class="font-weight-bold text-center">{{ partner }}</p>
                    <div v-if="!accepted">
                      <br />
                      <p>{{ $tc('home.autoaccept', 5)}}</p>
                    </div>
                  </v-form>
                </v-stepper-content>
              </v-stepper-items>
            </v-stepper>
          </v-card-text>
          <v-card-actions>
            <v-btn
              v-if="step == 1"
              color="secondary"
              v-on:click="routeNewGroup"
            >{{ $t('home.create-group')}}</v-btn>
            <v-btn v-if="step == 2" color="secondary" v-on:click="stepBack">{{ $t('home.back')}}</v-btn>
            <v-spacer />
            <v-btn v-if="step == 1" color="primary" v-on:click="getPartner">{{ $t('home.draw')}}</v-btn>
            <v-tooltip bottom v-if="step == 2">
              <template v-slot:activator="{ on }">
                <v-badge :content="rerolls" bordered overlap>
                  <v-btn
                    color="success"
                    :disabled="accepted || rerolls <= 0"
                    v-on="on"
                    v-on:click="drawPartner"
                  >{{ $t('home.reroll')}}</v-btn>
                </v-badge>
              </template>
              <span>{{ $tc('home.reroll-tooltip', rerolls)}}</span>
            </v-tooltip>
            <v-spacer v-if="step == 2" />
            <v-btn v-if="step == 2" :disabled="accepted" color="primary" v-on:click="acceptPartner">{{ $t('home.accept')}}</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import api from "./backend-api";

export default {
  props: {
    source: String
  },
  data: () => ({
    step: 1,
    showKey: false,
    groupname: "",
    name: "",
    key: "",
    displayerror: "",
    displayinfo: "",
    credentialserror: "",
    rerolls: 0,
    partner: "",
    accepted: false
  }),
  methods: {
    routeNewGroup: function() {
      this.$router.push({ path: "/newgroup" });
    },
    stepBack: function() {
      this.step = 1;
    },
    acceptPartner: function(){
        api
        .acceptPartner(this.groupname, this.name, this.key)
        .then(response => {
            this.partner = response.data.partner.name;
            this.accepted = response.data.drawAccepted;
            this.rerolls = response.data.rerolls;

            this.step = 2;
        })
        .catch(error => {
          console.log(error);
          if (error.response.status == 404) {
            this.credentialserror = "home.invalid-credentials";
            step = 1;
          }
          console.log(error.response);
        });
    },
    getPartner: function() {
      api
        .getMember(this.groupname, this.name, this.key)
        .then(response => {
          if (response.data.partner != null) {
            this.partner = response.data.partner.name;
            this.accepted = response.data.drawAccepted;
            this.rerolls = response.data.rerolls;

            this.step = 2;
          } else {
            this.drawPartner();
          }
        })
        .catch(error => {
          console.log(error);
          if (error.response.status == 404) {
            this.credentialserror = "home.invalid-credentials";
            step = 1;
          }
          console.log(error.response);
        });
    },
    drawPartner: function() {
      api
        .drawPartner(this.groupname, this.name, this.key)
        .then(response => {
          console.log(response.data);
          this.partner = response.data.partner.name;
          this.accepted = response.data.drawAccepted;
          this.rerolls = response.data.rerolls;

          this.step = 2;
        })
        .catch(error => {
          console.log(error);
          if (error.response.status == 404) {
            this.credentialserror = "home.invalid-credentials";
            step = 1;
          } else if (error.response.status == 422) {
            this.displayinfo = error.response.data.message;
            this.credentialserror = "";
          } else {
            this.displayerror = error.response.data.message;
            this.credentialserror = "";
          }
        });
    }
  }
};
</script>