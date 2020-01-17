<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="12" md="8">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title v-if="step < 3">{{ $t('new-group.formtitle')}}</v-toolbar-title>
            <v-toolbar-title v-if="step == 3">{{ $t('new-group.final-title', groupname)}}</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-stepper v-model="step" alt-labels>
              <v-stepper-header>
                <v-stepper-step key="group" step="1">{{ $t('new-group.group')}}</v-stepper-step>
                <v-divider></v-divider>
                <v-stepper-step key="member" step="2">{{ $t('new-group.member')}}</v-stepper-step>
                <v-divider></v-divider>
                <v-stepper-step key="result" step="3">{{ $t('new-group.result')}}</v-stepper-step>
              </v-stepper-header>
              <v-stepper-items>
                <v-stepper-content key="group" step="1">
                  <v-form>
                    <v-text-field
                      :label="$t('new-group.name')"
                      :error-messages="$t(groupnameerror)"
                      name="name"
                      v-model="groupname"
                      v-on:change="checkGroupName"
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
                      :disabled="!allowEmail"
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
                      name="rerolls"
                      v-model="rerolls"
                      prepend-icon="mdi-sync"
                      :disabled="!checkboxRerolls"
                      type="number"
                    />
                  </v-form>
                </v-stepper-content>
                <v-stepper-content key="member" step="2">
                  <v-simple-table fixed-header v-if="members != null">
                    <thead>
                      <tr>
                        <th class="text-left">{{ $t('new-group.member-number')}}</th>
                        <th class="text-left">{{ $t('new-group.member-name')}}</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="member, index in members">
                        <td>{{ index + 1 }}</td>
                        <td><v-text-field
                            v-model="member.name"
                            :label="$t('new-group.member-name')"
                            name="name"
                            prepend-icon="mdi-account"
                            type="text"
                          /></td>
                      </tr>
                    </tbody>
                  </v-simple-table>
                </v-stepper-content>
                <v-stepper-content key="result" step="3">
                  <v-simple-table fixed-header v-if="group != null">
                    <thead>
                      <tr>
                        <th class="text-left">{{ $t('new-group.member-number')}}</th>
                        <th class="text-left">{{ $t('new-group.member-name')}}</th>
                        <th class="text-left" v-if="showEmail">{{ $t('new-group.member-email')}}</th>
                        <th class="text-left">{{ $t('new-group.member-key')}}</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="member, index in group.members" :key="index">
                        <td>{{ index + 1 }}</td>
                        <td>{{ member.name }}</td>
                        <td v-if="showEmail">{{ member.email }}</td>
                        <td>
                          <v-text-field
                            :value="member.key"
                            :append-icon="member.showKey ? 'mdi-eye' : 'mdi-eye-off'"
                            :type="member.showKey ? 'text' : 'password'"
                            @click:append="member.showKey = !member.showKey"
                          ></v-text-field>
                        </td>
                      </tr>
                    </tbody>
                  </v-simple-table>
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
import api from "./backend-api";

export default {
  props: {
    source: String
  },
  data: () => ({
    groupname: "",
    groupnameerror: "",
    rerolls: 0,
    showPassword: false,
    allowEmail: false,
    min: 0,
    max: 50,
    checkbox: false,
    checkboxRerolls: false,
    step: 1,
    membercount: 0,
    membercountsave: 0,
    members: [],
    group: null
  }),
  methods: {
    stepBack: function() {
      if (this.step === 1) {
        this.$router.push({ path: "/" });
      } else if(this.step === 2) {
        this.step = 1;
      } else if(step === 3){
        this.$router.push({ path: "/" });
      }
    },
    checkGroupName: function() {
      api.checkGroupName(this.groupname).then(response => {
        console.log(response);
        if (response.data == true) {
          this.groupnameerror = "new-group.error-groupname-exists";
        } else {
          this.groupnameerror = "";
        }
      });
    },
    stepForward: function() {
      if (this.step === 1) {
        if (this.membercountsave >= this.membercount) {
          this.members.splice(this.membercount);
          this.membercountsave = this.membercount;
        } else if (this.membercountsave <= this.membercount) {
          let memberstemp = Array.from(
            { length: this.membercount - this.membercountsave },
            () => ({ name: "", email: "" })
          );
          this.members = this.members.concat(memberstemp);
          this.membercountsave = this.membercount;
        }
        this.step = 2;
      } else if (this.step === 2) {
        api
          .createGroup(this.groupname, this.rerolls, this.members)
          .then(response => {
            this.group = response.data;
            let s = this.group.members.length;
            for(let i = 0; i < s; i++){
                this.$set(this.group.members[i], 'showKey', false);
            }
            console.log(this.group);
            this.step = 3;
          })
          .catch(error => {
            if (error.response.status == 409) {
              this.groupnameerror = "new-group.error-groupname-exists";
              this.step = 1;
            }
          });
      } else {
        this.$router.push({ path: "/" });
      }
    }
  }
};
</script>