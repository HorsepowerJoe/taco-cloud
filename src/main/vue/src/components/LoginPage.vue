<template lang="">
  <h1>Login</h1>
  <img
    src="../assets/logo.png"
    alt=""
  >
  <br>
  <br>
  <q>New here? Click <router-link to="/register">here</router-link></q>
  <form @submit.prevent="onSubmit">
    <label for="username">Username: </label>
    <input
      v-model="loginForm.username"
      type="text"
      name="username"
    ><br>
    <label for="password">Password: </label>
    <input
      v-model="loginForm.password"
      type="text"
      name="password"
    ><br>
    <button>Login</button>
  </form>
</template>

<script setup>
import axios from 'axios';
import { ref } from 'vue';
const loginForm = ref({
    username:'',
    password:''
});

const onSubmit = () => {
    axios.post('/api/login',loginForm.value).then(resp=>{console.log(resp)}).catch(e=>{alert(e.response.data)});
}
 
</script>
<style lang="">
    
</style>