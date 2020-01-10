<template>
    <layout-default>
        <HelloWorld />
    </layout-default>
</template>

<script>
import HelloWorld from '../components/HelloWorld';
import LayoutDefault from '../layouts/LayoutDefault.vue';


export default {
  name: 'Home',

  components: {
    HelloWorld,
    LayoutDefault,
  },
};
</script>
