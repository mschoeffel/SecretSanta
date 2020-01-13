<template>
  <layout-default>
    <LoginDef />
  </layout-default>
</template>

<script>
import LoginDef from "../components/LoginDef";
import LayoutDefault from "../layouts/LayoutDefault.vue";

export default {
  name: "Login",

  components: {
    LoginDef,
    LayoutDefault
  }
};
</script>
