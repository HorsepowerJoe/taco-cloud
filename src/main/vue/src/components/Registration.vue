<!-- eslint-disable vue/multi-word-component-names -->
<template lang="">
  <div>
    <form @submit.prevent="onSubmit">
      <label for="username">Username: </label>
      <input
        v-model="something"
        type="text"
        name="username"
      >
        
      <label for="password">password: </label>
      <input
        v-model="something"
        type="text"
        name="password"
      >
        
      <label for="confirm">confirm password: </label>
      <input
        v-model="something"
        type="text"
        name="confirm"
      >
        
      <label for="fullname">Fullname: </label>
      <input
        v-model="something"
        type="text"
        name="fullname"
      >
        
      <label for="street">Street: </label>
      <input
        v-model="something"
        type="text"
        name="street"
      >
        
      <label for="city">City: </label>
      <input
        v-model="something"
        type="text"
        name="city"
      >
        
      <label for="state">State: </label>
      <input
        v-model="something"
        type="text"
        name="state"
      >
        
      <label for="zip">Zip: </label>
      <input
        v-model="something"
        type="text"
        name="zip"
      >
        
      <label for="phone">Phone: </label>
      <input
        v-model="something"
        type="text"
        name="phone"
      >

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
    fullname: '',
    street: '',
    city: '',
    state: '',
    zip: '',
    phone: ''
});

const onSubmit = () => {
    axios.post('/api/registration', registrationForm).then().catch();
}
</script>
<style lang="">
    
</style>