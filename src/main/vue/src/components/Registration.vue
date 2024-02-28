<!-- eslint-disable vue/multi-word-component-names -->
<template lang="">
  <div>
    <p>Registartion</p>
    <form @submit.prevent="onSubmit">
      <label for="username">Username: </label>
      <input
        v-model="registrationForm.username"
        type="text"
        name="username"
      ><br>
        
      <label for="password">password: </label>
      <input
        v-model="registrationForm.password"
        type="text"
        name="password"
      ><br>
        
      <label for="confirm">confirm password: </label>
      <input
        v-model="registrationForm.confirm"
        type="text"
        name="confirm"
      ><br>
        
      <label for="fullname">Fullname: </label>
      <input
        v-model="registrationForm.fullname"
        type="text"
        name="fullname"
      ><br>
        
      <label for="street">Street: </label>
      <input
        v-model="registrationForm.street"
        type="text"
        name="street"
      ><br>
        
      <label for="city">City: </label>
      <input
        v-model="registrationForm.city"
        type="text"
        name="city"
      ><br>
        
      <label for="state">State: </label>
      <input
        v-model="registrationForm.state"
        type="text"
        name="state"
      ><br>
        
      <label for="zip">Zip: </label>
      <input
        v-model="registrationForm.zip"
        type="text"
        name="zip"
      ><br>
        
      <label for="phone">Phone: </label>
      <input
        v-model="registrationForm.phone"
        type="text"
        name="phone"
      ><br>

      <button>Register</button>
    </form>    
  </div>
</template>
<script setup>
import axios from 'axios';
import { ref } from 'vue';

const registrationForm = ref({
    username: '',
    password: '',
    confirm: '',
    fullname: '',
    street: '',
    city: '',
    state: '',
    zip: '',
    phone: ''
});

const onSubmit = () => {
  console.log(registrationForm.value)
    axios.post('/api/register', registrationForm.value).then(resp=>{if(resp.status == 200){alert('가입 성공!'); location.href='/design';}}).catch(e=>alert(e.response.data));
}
</script>
<style lang="">
    
</style>