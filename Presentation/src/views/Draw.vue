<template>
  <layout-default>
    <DrawDef />
  </layout-default>
</template>

<script>
import DrawDef from "../components/DrawDef";
import LayoutDefault from "../layouts/LayoutDefault.vue";

export default {
  name: "Draw",

  components: {
    DrawDef,
    LayoutDefault
  }
};
</script>
