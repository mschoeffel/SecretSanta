<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="12" md="8">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title v-if="step < 3">{{ $t('new-group.formtitle')}}</v-toolbar-title>
            <v-toolbar-title v-if="step == 3">{{ $t('new-group.final-title', {name: groupname})}}</v-toolbar-title>
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
                    <v-alert
                       v-if="validationerror.length > 0"
                       type="error"
                    >{{ $t(validationerror) }}</v-alert>
                    <v-text-field
                      :label="$t('new-group.name')"
                      :error-messages="$t(groupnameerror, {max: maxchargroupname})"
                      name="name"
                      :counter="maxchargroupname"
                      v-model="groupname"
                      v-on:change="checkGroupName"
                      prepend-icon="mdi-account-group"
                      type="text"
                      required
                    />
                    <v-text-field
                      :label="$t('new-group.membercount')"
                      :error-messages="$t(membercounterror, {min: minNumberMember, max: maxNumberMember})"
                      v-model.number="membercount"
                      name="name"
                      prepend-icon="mdi-numeric"
                      type="number"
                      :min="minNumberMember"
                      :max="maxNumberMember"
                      v-on:change="checkMemberNumber"
                      required
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
                      :error-messages="$t(rerollserror, {max: maxrerolls, min: minrerolls})"
                      v-on:change="checkRerollsNumber"
                      :min="minrerolls"
                      :max="maxrerolls"
                      name="rerolls"
                      v-model="rerolls"
                      prepend-icon="mdi-sync"
                      :disabled="!checkboxRerolls"
                      type="number"
                    />
                  </v-form>
                </v-stepper-content>
                <v-stepper-content key="member" step="2">
                    <v-alert
                       v-if="validationerror.length > 0"
                       type="error"
                    >{{ $t(validationerror) }}</v-alert>
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
                            :error-messages="$t(member.error, {max: maxcharmember})"
                            v-on:change="checkmember(member)"
                            :counter="maxcharmember"
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
            <v-btn color="secondary" v-on:click="stepBack" v-if="step < 3">{{ $tc('new-group.back', step)}}</v-btn>
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
    checkbox: false,
    checkboxRerolls: false,
    step: 1,
    membercount: 0,
    membercountsave: 0,
    members: [],
    group: null,
    minNumberMember: 2,
    maxNumberMember: 50,
    maxchargroupname: 15,
    membercounterror: "",
    minrerolls: 0,
    maxrerolls: 100,
    rerollserror: "",
    validationerror: "",
    maxcharmember: 15
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
      this.groupnameerror = "";
      if(this.groupname.length <= 0){
        this.groupnameerror = "new-group.validation-groupnamerequired";
      } else if(this.groupname.length > this.maxchargroupname){
        this.groupnameerror = "new-group.validation-groupnamelength";
      } else{
        api.checkGroupName(this.groupname).then(response => {
          if (response.data == true) {
            this.groupnameerror = "new-group.error-groupname-exists";
          }
        });
      }

    },
    checkMemberNumber: function(){
      this.membercounterror = "";
      if(this.membercount > this.maxNumberMember || this.membercount < this.minNumberMember){
        this.membercounterror = "new-group.validation-membercountbounds";
      }
    },
    checkRerollsNumber: function() {
      this.rerollserror = "";
      if(this.rerolls > this.maxrerolls || this.rerolls < this.minrerolls){
        this.rerollserror = "new-group.validation-rerollsbounds";
      }
    },
    checkmember: function(member){
      member.error = "";
      if(member.name.length <= 0){
        member.error = "new-group.validation-memberrequired";
      } else if(member.name.length > this.maxcharmember){
        member.error = "new-group.validation-memberlength";
      }
    },
    stepForward: function() {
      if (this.step === 1) {
        this.validationerror = "";
        if(!this.membercounterror && !this.groupnameerror && !this.rerollserror){
          if (this.membercountsave >= this.membercount) {
            this.members.splice(this.membercount);
            this.membercountsave = this.membercount;
          } else if (this.membercountsave <= this.membercount) {
            let memberstemp = Array.from(
              { length: this.membercount - this.membercountsave },
              () => ({ name: "", email: "", error: "" })
            );
            this.members = this.members.concat(memberstemp);
            this.membercountsave = this.membercount;
          }
          this.step = 2;
        } else{
          this.validationerror = "new-group.validationerror";
        }
      } else if (this.step === 2) {

        let membersValid = true;
        for(let i = 0; i < this.members.length; i++){
            if(this.members[i].error.length > 0){
                membersValid = false;
                break;
            }
        }

        if(membersValid){
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
          } else{
            this.validationerror = "new-group.validationerror";
          }
      } else {
        this.$router.push({ path: "/" });
      }
    }
  }
};
</script>