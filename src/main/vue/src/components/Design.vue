<template lang="">
    <h1>Design your taco!</h1>
    <img src="../assets/logo.png" alt="">
    <form @submit.prevent="submitHandle">
      
  <button type="submit">Submit</button>
    </form>
    
    </template>
<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const tacoDatas = ref([{}]);


onMounted(()=>{
    axios.get('/api/design').then(resp=>{
        if(resp.status === 200){
            tacoDatas.value = resp.data;
        }else{
            alert(resp.data);
        }
    })
})

const submitHandle = (e) => {
    console.log(e);
    console.log(tacoDatas.value[0]);
}

</script>
<style lang="">
     
 </style>
