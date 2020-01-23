<template>
  <layout-default>
    <HelpDef />
  </layout-default>
</template>

<script>
import HelpDef from "../components/HelpDef";
import LayoutDefault from "../layouts/LayoutDefault.vue";

export default {
  name: "Help",

  components: {
    HelpDef,
    LayoutDefault
  }
};
</script>
