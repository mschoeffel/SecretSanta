<template>
  <layout-default>
    <GroupDef />
  </layout-default>
</template>

<script>
import GroupDef from "../components/GroupDef";
import LayoutDefault from "../layouts/LayoutDefault.vue";

export default {
  name: "Group",

  components: {
    GroupDef,
    LayoutDefault
  }
};
</script>
