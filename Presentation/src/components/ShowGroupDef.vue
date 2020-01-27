<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="12" md="8">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title v-if="step < 3">{{ $t('show-group.formtitle')}}</v-toolbar-title>
            <v-toolbar-title v-if="step == 3">{{ $t('show-group.final-title', {name: groupname})}}</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn icon v-on:click="routeHelp">
              <v-icon>mdi-help</v-icon>
            </v-btn>
          </v-toolbar>
          <v-card-text>
            <v-stepper v-model="step" alt-labels>
              <v-stepper-header>
                <v-stepper-step key="input" step="1">{{ $t('show-group.input')}}</v-stepper-step>
                <v-divider></v-divider>
                <v-stepper-step key="result" step="2">{{ $t('show-group.result')}}</v-stepper-step>
              </v-stepper-header>
              <v-stepper-items>
                <v-stepper-content key="input" step="1">
                  <v-alert
                    v-if="deletesuccess"
                    name="deletesuccess"
                    id="deletesuccess"
                    type="success"
                  >{{ $t(deletesuccess)}}</v-alert>
                  <v-alert
                    name="info"
                    id="info"
                    type="info"
                    outlined
                    dismissible
                  >{{ $t('show-group.info')}}</v-alert>
                  <v-alert
                    v-if="validationerror"
                    name="erroralert"
                    id="erroralert"
                    type="error"
                  >{{ $t(validationerror) }}</v-alert>
                  <v-text-field
                    :label="$t('show-group.name')"
                    v-model="groupname"
                    name="groupname"
                    id="groupname"
                    type="text"
                    prepend-icon="mdi-account-group"
                    :counter="maxchargroupname"
                    :error-messages="$t(groupnameerror, {max: maxchargroupname})"
                    v-on:change="checkGroupName"
                  />
                  <v-text-field
                    :label="$t('show-group.key')"
                    v-model="grouptoken"
                    name="grouptoken"
                    id="grouptoken"
                    :type="showtoken ? 'text' : 'password'"
                    :append-icon="showtoken ? 'mdi-eye' : 'mdi-eye-off'"
                    @click:append="showtoken = !showtoken"
                    prepend-icon="mdi-key"
                    :error-messages="$t(grouptokenerror)"
                  />
                </v-stepper-content>
                <v-stepper-content key="result" step="2">
                  <v-alert
                    v-if="deleteerror"
                    name="deleteerror"
                    id="deleteerror"
                    type="error"
                  >{{ $t(deleteerror)}}</v-alert>
                  <v-alert
                    name="info"
                    id="info"
                    type="info"
                    outlined
                    dismissible
                  >{{ $t('show-group.info2')}}</v-alert>
                  <v-text-field
                    v-if="group != null"
                    :label="$t('show-group.token')"
                    :value="group.token"
                    name="token"
                    id="token"
                    :type="showGroupKey ? 'text' : 'password'"
                    prepend-icon="mdi-key"
                    readonly
                  >
                    <template slot="append">
                      <v-icon
                        class="mr-3"
                        v-on:click="showGroupKey = !showGroupKey"
                      >{{showGroupKey ? 'mdi-eye' : 'mdi-eye-off'}}</v-icon>
                      <v-icon v-on:click="simpleCopyToClipboard('token')">mdi-content-copy</v-icon>
                    </template>
                  </v-text-field>
                  <v-alert
                    name="info"
                    id="info"
                    type="info"
                    outlined
                    dismissible
                  >{{ $t('show-group.info3')}}</v-alert>
                  <v-simple-table fixed-header v-if="group != null">
                    <thead>
                      <tr>
                        <th class="text-left">{{ $t('show-group.member-number')}}</th>
                        <th class="text-left">{{ $t('show-group.member-name')}}</th>
                        <th class="text-left">{{ $t('show-group.member-partner')}}</th>
                        <th class="text-left">{{ $t('show-group.rerolls')}}</th>
                        <th class="text-left">{{ $t('show-group.draw-accepted')}}</th>
                        <th class="text-left">{{ $t('show-group.member-key')}}</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="(member, index) in group.members" v-bind:key="index">
                        <td>{{ index + 1 }}</td>
                        <td>{{ member.name }}</td>
                        <td>
                          <v-text-field
                            :value="member.partner"
                            :name="'partner-' + index"
                            :id="'partner-' + index"
                            :type="member.showPartner ? 'text' : 'password'"
                            :append-icon="member.showPartner ? 'mdi-eye' : 'mdi-eye-off'"
                            @click:append="member.showPartner = !member.showPartner"
                            readonly
                          />
                        </td>
                        <td>{{ member.rerolls }}</td>
                        <td>{{ member.drawAccepted }}</td>
                        <td>
                          <v-text-field
                            :value="member.key"
                            :name="'key-' + index"
                            :id="'key-' + index"
                            :type="member.showKey ? 'text' : 'password'"
                            readonly
                          >
                            <template slot="append">
                              <v-icon
                                class="mr-3"
                                v-on:click="member.showKey = !member.showKey"
                              >{{member.showKey ? 'mdi-eye' : 'mdi-eye-off'}}</v-icon>
                              <v-icon
                                v-on:click="copyKeyToClipboard('key-' + index, member)"
                              >mdi-content-copy</v-icon>
                            </template>
                          </v-text-field>
                        </td>
                      </tr>
                    </tbody>
                  </v-simple-table>
                </v-stepper-content>
              </v-stepper-items>
            </v-stepper>
          </v-card-text>
          <v-card-actions>
            <v-btn
              color="secondary"
              name="back"
              id="back"
              v-on:click="stepBack"
            >{{ $tc('show-group.back', step)}}</v-btn>
            <v-spacer />
            <v-btn
              color="primary"
              name="next"
              id="next"
              v-on:click="stepForward"
              v-if="step < 2"
            >{{ $t('show-group.next')}}</v-btn>
            <v-dialog v-model="dialog" max-width="350" v-if="step == 2">
              <template v-slot:activator="{ on }">
                <v-btn
                  color="error"
                  name="delete"
                  id="delete"
                  v-on="on"
                >{{ $t('show-group.delete')}}</v-btn>
              </template>
              <v-card>
                <v-card-title class="headline">{{ $t('show-group.delete-title')}}</v-card-title>
                <v-card-text>{{ $t('show-group.delete-text')}}</v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                    color="secondary"
                    @click="dialog = false"
                  >{{ $t('show-group.delete-cancel')}}</v-btn>
                  <v-btn color="error" @click="deleteGroup">{{ $t('show-group.delete-accept')}}</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
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

    /* Input models*/
    groupname: "",
    grouptoken: "",
    showtoken: false,

    /* Result data */
    group: null,

    /* Validations */
    validationerror: "",

    maxchargroupname: 15,
    groupnameerror: "",
    grouptokenerror: "",

    deleteerror: "",
    deletesuccess: "",

    /* Dialog */
    dialog: false,

    /* Result */
    showGroupKey: false
  }),
  methods: {
    stepBack: function() {
      if (this.step === 1) {
        this.$router.push({ path: "/" });
      } else if (this.step === 2) {
        this.step = 1;
      }
    },
    checkGroupName: function() {
      this.groupnameerror = "";
      if (this.groupname.length <= 0) {
        this.groupnameerror = "show-group.validation-groupnamerequired";
      } else if (this.groupname.length > this.maxchargroupname) {
        this.groupnameerror = "show-group.validation-groupnamelength";
      }
    },
    stepForward: function() {
      if (this.step === 1) {
        this.validationerror = "";
        this.checkGroupName();
        if (!this.membercounterror) {
          api
            .getGroupDetail(this.groupname, this.grouptoken)
            .then(response => {
              this.group = response.data;
              let s = this.group.members.length;
              for (let i = 0; i < s; i++) {
                this.$set(this.group.members[i], "showKey", false);
                this.$set(this.group.members[i], "showPartner", false);
              }
              this.step = 2;
            })
            .catch(error => {
              if (error.response.status == 404) {
                this.groupnameerror =
                  "show-group.validation-invalid-credentials";
                this.grouptokenerror =
                  "show-group.validation-invalid-credentials";
                this.step = 1;
              }
            });
        } else {
          this.validationerror = "show-group.validation-invalid-input";
        }
      }
    },
    routeHelp: function() {
      this.$router.push({ path: "/help" });
    },
    copyKeyToClipboard: function(id, member) {
      let changed = false;
      let input = document.getElementById(id);
      if (!member.showKey) {
        member.showKey = true;
        input.setAttribute("type", "text");
        changed = true;
      }
      input.select();
      document.execCommand("copy");
      if (changed) {
        member.showKey = false;
        input.setAttribute("type", "password");
      }
    },
    simpleCopyToClipboard: function(id) {
      let changed = false;
      let input = document.getElementById(id);
      if (!this.showGroupKey) {
        this.showGroupKey = true;
        input.setAttribute("type", "text");
        changed = true;
      }
      input.select();
      document.execCommand("copy");
      if (changed) {
        this.showGroupKey = false;
        input.setAttribute("type", "password");
      }
    },
    deleteGroup: function() {
      api
        .deleteGroup(this.groupname, this.grouptoken)
        .then(response => {
          this.deletesuccess = "show-group.delete-successfull";
          this.step = 1;
        })
        .catch(error => {
          if (error.response.status == 404) {
            this.deleteerror = "show-group.delete-error";
          }
        });
    }
  }
};
</script>