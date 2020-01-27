<template>
  <layout-default>
    <ShowGroupDef />
  </layout-default>
</template>

<script>
import ShowGroupDef from "../components/ShowGroupDef";
import LayoutDefault from "../layouts/LayoutDefault.vue";

export default {
  name: "ShowGroup",

  components: {
    ShowGroupDef,
    LayoutDefault
  }
};
</script>
