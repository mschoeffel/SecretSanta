<template>
  <layout-default>
    <RegisterDef />
  </layout-default>
</template>

<script>
import RegisterDef from "../components/RegisterDef";
import LayoutDefault from "../layouts/LayoutDefault.vue";

export default {
  name: "Register",

  components: {
    RegisterDef,
    LayoutDefault
  }
};
</script>
