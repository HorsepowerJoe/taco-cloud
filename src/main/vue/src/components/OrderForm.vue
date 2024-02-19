<template lang="">
  <form @submit.prevent="onSubmit">
    <h1>Order your taco creations!</h1>
    <img
      src="../assets/logo.png"
      alt="tacoImage"
    >
    <a
      id="another"
      href="/design"
    >Design another taco</a><br>

    <h3>Deliver my taco masterpieces to...</h3>

    <label for="deliveryName">Name: </label>
    <input
      v-model="deliveryName"
      v-bind="deliveryNameAttr"
      type="text"
      name="deliveryName"
    >
    <span>{{ errors.deliveryName ? '이름을 입력해 주세요.':'' }}</span>
    <br>

    <label for="deliveryStreet">Street address: </label>
    <input
      v-model="deliveryStreet"
      v-bind="deliveryStreetAttr"
      type="text"
      name="deliveryStreet"
    >
    <span>{{ errors.deliveryStreet ? '상세 주소를 입력해 주세요.':'' }}</span>
    <br>

    <label for="deliveryCity">City: </label>
    <input
      v-model="deliveryCity"
      v-bind="deliveryCityAttr"
      type="text"
      name="deliveryCity"
    >
    <span>{{ errors.deliveryCity ? '도시를 입력해 주세요.':'' }}</span>
    <br>

    <label for="deliveryState">State: </label>
    <input
      v-model="deliveryState"
      v-bind="deliveryStateAttr"
      type="text"
      name="deliveryState"
    >
    <span>{{ errors.deliveryState ? '지역을 입력해 주세요.':'' }}</span>
    <br>

    <label for="deliveryZip">Zip code: </label>
    <input
      v-model="deliveryZip"
      v-bind="deliveryZipAttr"
      type="text"
      name="deliveryZip"
    >
    <span>{{ errors.deliveryZip ? '우편번호를 입력해 주세요.':'' }}</span>
    <br>

    <h3>Here's how I'll pay..</h3>
    <label for="ccNumber">Credit Card #: </label>
    <input
      v-model="ccNumber"
      v-bind="ccNumberAttr"
      type="text"
      name="ccNumber"
    >
    <span>{{ errors.ccNumber ? '카드번호를 입력해 주세요.' : '' }}</span>
    <br>

    <label for="ccExpiration">Expiration: </label>
    <input
      v-model="ccExpiration"
      v-bind="ccExpirationAttr"
      type="text"
      name="ccExpiration"
    >
    <span>{{ errors.ccExpiration === 'ccExpiration is not valid.' ? "만료기간을 입력해 주세요." : errors.ccExpiration }}</span>
    <br>

    <label for="ccCVV">CVV: </label>
    <input
      v-model="ccCVV"
      v-bind="ccCVVAttr"
      type="text"
      name="ccCVV"
    >
    <span>{{ errors.ccCVV === 'ccCVV is not valid.' ? "CVV를 입력해 주세요." : errors.ccCVV }}</span>
    <br>

    <input
      type="submit"
      value="Submit order"
    >
  </form>
</template>
<script setup>
import axios from 'axios';
import { useForm } from 'vee-validate';
import { onMounted } from 'vue';




const {handleSubmit, errors, defineField } = useForm({
    validationSchema:{
        deliveryName:{required:true},
    deliveryStreet:{required:true},
    deliveryCity: {required:true},
    deliveryState:{required:true},
    deliveryZip: {required:true},
    ccNumber: {required:true, numeric:true, creditCardNumber:true},
    ccExpiration: {required:true, ccExp:true},
    ccCVV: {required:true, numeric:true, ccCVV:3},
    }
});

const [deliveryName, deliveryNameAttr] = defineField('deliveryName');
const [deliveryStreet, deliveryStreetAttr] = defineField('deliveryStreet');
const [deliveryCity, deliveryCityAttr] = defineField('deliveryCity');
const [deliveryState, deliveryStateAttr] = defineField('deliveryState');
const [deliveryZip, deliveryZipAttr] = defineField('deliveryZip');
const [ccNumber, ccNumberAttr] = defineField('ccNumber');
const [ccExpiration, ccExpirationAttr] = defineField('ccExpiration');
const [ccCVV, ccCVVAttr] = defineField('ccCVV');


const onSubmit = handleSubmit((formData)=>{
    if(Object.keys(errors.value).length > 0){
        return;
    }else{
      formData.tacos=
        axios.post('/api/orders', formData).then(resp=>{resp.status===200?location.href='/':alert('서버 통신 실패!\n'+resp.body)}).catch(e=>{alert('서버 통신 실패! \n'+e.response.data)});
    }
})

onMounted(() => {
  console.log(sessionStorage.getItem('newTacoData'))
  if (sessionStorage.getItem('newTacoData'))return;
  alert('주문 정보가 없습니다. 디자인 페이지로 돌아갑니다.');
  window.location.href='/design';
})



</script>
<style lang="">
    
</style>