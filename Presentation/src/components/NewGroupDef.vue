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
                    <v-alert
                       v-if="validationerror"
                       name="erroralert"
                       id="erroralert"
                       type="error"
                    >{{ $t(validationerror) }}</v-alert>
                    <v-text-field
                      :label="$t('new-group.name')"
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
                      :label="$t('new-group.membercount')"
                      v-model.number="membercount"
                      name="name"
                      id="name"
                      type="number"
                      prepend-icon="mdi-numeric"
                      :min="minNumberMember"
                      :max="maxNumberMember"
                      :error-messages="$t(membercounterror, {min: minNumberMember, max: maxNumberMember})"
                      v-on:change="checkMemberNumber"
                    />
                    <v-divider></v-divider>
                    <v-checkbox
                    :label="$t('new-group.rerolls')"
                      v-model="checkboxRerolls"
                      name="enableRerolls"
                      id="enableRerolls"
                      prepend-icon="mdi-sync"
                      color="primary"
                    ></v-checkbox>
                    <v-text-field
                      :label="$t('new-group.rerolls-slider')"
                      v-model="rerolls"
                      name="rerolls"
                      id="rerolls"
                      type="number"
                      prepend-icon="mdi-sync"
                      :min="minrerolls"
                      :max="maxrerolls"
                      :error-messages="$t(rerollserror, {max: maxrerolls, min: minrerolls})"
                      :disabled="!checkboxRerolls"
                      v-on:change="checkRerollsNumber"
                    />
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
                      <tr v-for="(member, index) in members" v-bind:key="index">
                        <td>{{ index + 1 }}</td>
                        <td>
                          <v-text-field
                          :label="$t('new-group.member-name')"
                            v-model="member.name"
                            :name="name + index"
                            :id="name + index"
                            type="text"
                            prepend-icon="mdi-account"
                            :counter="maxcharmember"
                            :error-messages="$t(member.error, {max: maxcharmember})"
                            v-on:change="checkmember(member)"
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
                      <tr v-for="(member, index) in group.members" v-bind:key="index">
                        <td>{{ index + 1 }}</td>
                        <td>{{ member.name }}</td>
                        <td v-if="showEmail">{{ member.email }}</td>
                        <td>
                          <v-text-field
                            :value="member.key"
                            :name="key + index"
                            :id="key + index"
                            :type="member.showKey ? 'text' : 'password'"
                            :append-icon="member.showKey ? 'mdi-eye' : 'mdi-eye-off'"
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
            <v-btn color="secondary" name="back" id="back" v-on:click="stepBack" v-if="step < 3">{{ $tc('new-group.back', step)}}</v-btn>
            <v-spacer />
            <v-btn color="primary" name="next" id="next" v-on:click="stepForward">{{ $tc('new-group.next', step)}}</v-btn>
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
    membercount: 0,
    checkboxRerolls: false,
    rerolls: 0,
    
    /* Helper */
    membercountsave: 0,

    /* Result data */
    members: [],
    group: null,
    
    /* Validations */
    validationerror: "",

    maxchargroupname: 15,
    groupnameerror: "",
    
    maxcharmember: 15,

    minNumberMember: 2,
    maxNumberMember: 50,
    membercounterror: "",

    minrerolls: 0,
    maxrerolls: 100,
    rerollserror: "",
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
        this.checkGroupName();
        this.checkMemberNumber();
        this.checkRerollsNumber();
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
          this.step = 1;
        }
      } else if (this.step === 2) {

        let membersValid = true;
        for(let i = 0; i < this.members.length; i++){
          this.checkmember(this.members[i]);
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