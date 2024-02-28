<template lang="">
  <div>
    <form @submit.prevent="logout">
      <button>Logout</button>
    </form>
  </div>
</template>
<script setup>
import axios from 'axios';

    const logout = () => {
        axios.post('/api/logout').then().catch();
    }
</script>
<style lang="">
    
</style>