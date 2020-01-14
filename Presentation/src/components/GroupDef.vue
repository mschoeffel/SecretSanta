<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title>{{ $t('group.formtitle', { name: group})}}</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-simple-table fixed-header v-on:>
              <thead>
                <tr>
                  <th class="text-left">{{ $t('group.number')}}</th>
                  <th class="text-left">{{ $t('group.name')}}</th>
                  <th class="text-left" v-if="showEmail">{{ $t('group.email')}}</th>
                  <th class="text-left">{{ $t('group.key')}}</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="member, index in members" :key="index">
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
          </v-card-text>
          <v-card-actions>
            <v-spacer />
            <v-btn color="primary" v-on:click="ok">{{ $t('group.finished')}}</v-btn>
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
    group: "Testgruppe",
    members: [
      {
        name: "Test",
        email: "Testemail",
        key: "Testkey",
        showKey: false
      },
      {
        name: "Test2",
        email: "Testemail2",
        key: "Testkey2",
        showKey: false
      }
    ],
    showEmail: false,
    showKey: []
  }),
  methods: {
    ok: function() {
      this.$router.push({ path: "/" });
    }
  }
};
</script>