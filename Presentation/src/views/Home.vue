<template>
    <layout-default>
        <HomeDef />
    </layout-default>
</template>

<script>
import HomeDef from '../components/HomeDef';
import LayoutDefault from '../layouts/LayoutDefault.vue';


export default {
  name: 'Home',

  components: {
    HomeDef,
    LayoutDefault,
  },
};
</script>
