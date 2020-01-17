<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title>{{ $t('new-group.formtitle')}}</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-stepper v-model="step" alt-labels>
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
                  <v-list>
                    <div v-for="member, index in members">
                      <v-subheader>{{index + 1}}</v-subheader>
                      <v-list-item inactive="true" selectable="false">
                        <v-list-item-content>
                          <v-text-field
                            v-model="member.name"
                            :label="$t('new-group.member-name')"
                            name="name"
                            prepend-icon="mdi-account"
                            type="text"
                          />
                        </v-list-item-content>
                      </v-list-item>
                      <v-list-item v-if="checkbox" inactive="true" selectable="false">
                        <v-list-item-content>
                          <v-text-field
                            v-model="member.email"
                            :label="$t('new-group.member-email')"
                            name="name"
                            prepend-icon="mdi-email"
                            type="text"
                          />
                        </v-list-item-content>
                      </v-list-item>
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
    slider: 10,
    checkbox: false,
    checkboxRerolls: false,
    sliderRerolls: 0,
    minRerolls: 0,
    maxRerolls: 5,
    step: 1,
    membercount: 0,
    membercountsave: 0,
    members: []
  }),
  methods: {
    stepBack: function() {
      if (this.step === 1) {
        this.$router.push({ path: "/" });
      } else {
        this.step -= 1;
      }
    },
    checkGroupName: function(){
        api.checkGroupName(this.groupname)
        .then(response => {
            console.log(response);
            if(response.data == true){
                this.groupnameerror = "new-group.error-groupname-exists";
            } else{
                this.groupnameerror = "";
            }
        })
    },
    stepForward: function() {
      if (this.step === 2) {
        api.createGroup(this.groupname, this.rerolls, this.members)
        .then(response => {
             console.log(response);
             this.$router.push({ path: "/group" });
        })
        .catch(
            error => {
            console.log(error.response);
            if(error.response.status == 409){
                this.groupnameerror = "new-group.error-groupname-exists";
                this.step = 1;
            }
        });

      } else {
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
        this.step += 1;
      }
    }
  }
};
</script>