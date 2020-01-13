<template>
  <layout-default>
    <NewGroupDef />
  </layout-default>
</template>

<script>
import NewGroupDef from "../components/NewGroupDef";
import LayoutDefault from "../layouts/LayoutDefault.vue";

export default {
  name: "NewGroup",

  components: {
    NewGroupDef,
    LayoutDefault
  }
};
</script>
