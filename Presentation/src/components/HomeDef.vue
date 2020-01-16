<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title>{{ $t('home.formtitle')}}</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-form>
              <v-alert v-if="displayerror.length > 0" type="error">
                  I'm an error alert.
              </v-alert>
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
          </v-card-text>
          <v-card-actions>
            <v-btn color="secondary" v-on:click="routeNewGroup">{{ $t('home.create-group')}}</v-btn>
            <v-spacer />
            <v-btn color="primary" v-on:click="drawPartner">{{ $t('home.draw')}}</v-btn>
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
    showKey: false,
    groupname: "",
    name: "",
    key: "",
    displayerror: "",
    credentialserror: ""
  }),
  methods: {
    routeNewGroup: function() {
      this.$router.push({ path: "/newgroup" });
    },
    drawPartner: function(){
      api.drawPartner(this.groupname, this.name, this.key)
        .then(response => {
             console.log(response.data);
        })
        .catch(
            error => {
            console.log(error.response);
            if(error.response.status == 404){
                this.credentialserror = "home.invalid-credentials";
            } else if(error.response.status == 422){
                this.displayerror = "ERROR";   
            }
        });
    }
  }
};
</script>