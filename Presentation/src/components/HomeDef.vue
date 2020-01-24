<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="12" md="8">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title name="title" id="title">{{ $t('home.formtitle')}}</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn icon v-on:click="routeHelp">
              <v-icon>mdi-help</v-icon>
            </v-btn>
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
                  <v-alert
                    name="info"
                    id="info"
                    type="info"
                    outlined
                    dismissible
                  >{{ $t('home.info')}}</v-alert>
                  <v-alert
                    name="erroralert"
                    id="erroralert"
                    v-if="displayerror"
                    type="error"
                  >{{ $t(displayerror) }}</v-alert>
                  <v-text-field
                    :label="$t('home.group')"
                    v-model="groupname"
                    name="groupname"
                    id="groupname"
                    type="text"
                    prepend-icon="mdi-account-group"
                    :counter="maxchargroupname"
                    :error-messages="$t(groupnameerror, {max: maxchargroupname})"
                    v-on:change="checkgroupname"
                  />
                  <v-text-field
                    :label="$t('home.name')"
                    v-model="name"
                    name="name"
                    id="name"
                    type="text"
                    prepend-icon="mdi-account"
                    :counter="maxcharmember"
                    :error-messages="$t(membernameerror, {max: maxcharmember})"
                    v-on:change="checkmembername"
                  />
                  <v-text-field
                    :label="$t('home.key')"
                    v-model="key"
                    name="key"
                    id="key"
                    :type="showKey ? 'text' : 'password'"
                    prepend-icon="mdi-key"
                    :append-icon="showKey ? 'mdi-eye' : 'mdi-eye-off'"
                    @click:append="showKey = !showKey"
                    :error-messages="$t(keyerror)"
                    v-on:change="checkkey"
                  />
                </v-stepper-content>
                <v-stepper-content key="result" step="2">
                  <v-alert v-if="displayinfo" type="info" dismissible>{{ displayinfo }}</v-alert>
                  <p class="font-weight-medium text-center">{{ $t('home.message')}}</p>
                  <br />
                  <p class="font-weight-bold text-center">{{ partner }}</p>
                  <div v-if="!accepted">
                    <br />
                    <p>{{ $tc('home.autoaccept', 5)}}</p>
                  </div>
                </v-stepper-content>
              </v-stepper-items>
            </v-stepper>
          </v-card-text>
          <v-card-actions>
            <v-btn
              v-if="step == 1"
              name="routecreategroup"
              id="routecreategroup"
              color="secondary"
              v-on:click="routeNewGroup"
            >{{ $t('home.create-group')}}</v-btn>
            <v-btn
              v-if="step == 2"
              name="back"
              id="back"
              color="secondary"
              v-on:click="stepBack"
            >{{ $t('home.back')}}</v-btn>
            <v-spacer />
            <v-btn
              v-if="step == 1"
              name="next"
              id="next"
              color="primary"
              v-on:click="getPartner"
            >{{ $t('home.draw')}}</v-btn>
            <v-tooltip bottom v-if="step == 2">
              <template v-slot:activator="{ on }">
                <v-badge :content="rerolls" bordered overlap>
                  <v-btn
                    name="reroll"
                    id="reroll"
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
            <v-btn
              v-if="step == 2"
              name="accept"
              id="accept"
              :disabled="accepted"
              color="primary"
              v-on:click="acceptPartner"
            >{{ $t('home.accept')}}</v-btn>
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
    rerolls: 0,
    partner: "",
    maxcharmember: 15,
    maxchargroupname: 15,
    groupnameerror: "",
    membernameerror: "",
    keyerror: "",
    accepted: false
  }),
  methods: {
    routeNewGroup: function() {
      this.$router.push({ path: "/newgroup" });
    },
    stepBack: function() {
      this.step = 1;
    },
    checkgroupname: function() {
      this.groupnameerror = "";
      if (this.groupname.length <= 0) {
        this.groupnameerror = "home.validation-groupnamerequired";
      } else if (this.groupname.length > this.maxchargroupname) {
        this.groupnameerror = "home.validation-groupnamelength";
      }
    },
    checkmembername: function() {
      this.membernameerror = "";
      if (this.name.length <= 0) {
        this.membernameerror = "home.validation-membernamerequired";
      } else if (this.name.length > this.maxcharmember) {
        this.membernameerror = "home.validation-membernamelength";
      }
    },
    checkkey: function() {
      this.keyerror = "";
      if (this.key.length <= 0) {
        this.keyerror = "home.validation-keyrequired";
      }
    },
    acceptPartner: function() {
      api
        .acceptPartner(this.groupname, this.name, this.key)
        .then(response => {
          this.partner = response.data.partner;
          this.accepted = response.data.drawAccepted;
          this.rerolls = response.data.rerolls;

          this.step = 2;
        })
        .catch(error => {
          console.log(error);
          if (error.response.status == 404) {
            this.groupnameerror = "home.invalid-credentials";
            this.membernameerror = "home.invalid-credentials";
            this.keyerror = "home.invalid-credentials";
            this.step = 1;
          }
        });
    },
    getPartner: function() {
      this.displayerror = "";
      this.checkgroupname();
      this.checkmembername();
      this.checkkey();
      if (!this.keyerror && !this.membernameerror && !this.groupnameerror) {
        api
          .getMember(this.groupname, this.name, this.key)
          .then(response => {
            if (response.data.partner != null) {
              this.partner = response.data.partner;
              this.accepted = response.data.drawAccepted;
              this.rerolls = response.data.rerolls;

              this.step = 2;
            } else {
              this.drawPartner();
            }
          })
          .catch(error => {
            if (error.response.status == 404) {
              this.groupnameerror = "home.invalid-credentials";
              this.membernameerror = "home.invalid-credentials";
              this.keyerror = "home.invalid-credentials";
              this.step = 1;
            }
          });
      } else {
        this.displayerror = "home.validationerror";
      }
    },
    drawPartner: function() {
      api
        .drawPartner(this.groupname, this.name, this.key)
        .then(response => {
          console.log(response.data);
          this.partner = response.data.partner;
          this.accepted = response.data.drawAccepted;
          this.rerolls = response.data.rerolls;

          this.step = 2;
        })
        .catch(error => {
          console.log(error);
          if (error.response.status == 404) {
            this.groupnameerror = "home.invalid-credentials";
            this.membernameerror = "home.invalid-credentials";
            this.keyerror = "home.invalid-credentials";
            this.step = 1;
          } else if (error.response.status == 422) {
            this.displayinfo = error.response.data.message;
            this.groupnameerror = "";
            this.membernameerror = "";
            this.keyerror = "";
          } else {
            this.displayerror = error.response.data.message;
            this.groupnameerror = "";
            this.membernameerror = "";
            this.keyerror = "";
          }
        });
    },
    routeHelp: function() {
      this.$router.push({ path: "/help" });
    }
  }
};
</script>