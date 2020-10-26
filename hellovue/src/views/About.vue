<template>
  <div class="about">
    <h1>This is an about page</h1>
    <el-button @click="startHacking">Button Demo</el-button>

  </div>
<!--
  <div>
   </div>
-->
</template>

<script>
    export default {
        methods: {
            startHacking () {
                this.$notify({
                    title: 'It works!',
                    type: 'success',
                    message: 'We\'ve laid the ground work for you. It\'s time for you to build something epic!',
                    duration: 5000
                })
            }
        }
    }
</script>
