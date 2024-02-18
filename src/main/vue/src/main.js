import { defineRule } from 'vee-validate';

import { required,max,min, numeric, email  } from '@vee-validate/rules';
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';

defineRule('required', required);
defineRule('max', max);
defineRule('min', min);
defineRule('numeric', numeric);
defineRule('email', email);
defineRule('ccCVV', (value) => {
    return /^[0-9]{3}$/.test(value) || '입력값은 3자리여야 합니다.';
  });
defineRule('creditCardNumber', (value)=>{
    const regex = /^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\d{3})\d{11})$/;
    return regex.test(value) || '유효하지 않은 신용카드 번호입니다.';
})
defineRule('ccExp', (value)=>{
    const regex = /^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$/;
    return regex.test(value) || '날짜 형식이 잘못되었습니다. (MM/YY 형식이어야 합니다)';
})





createApp(App).use(router).mount('#app')
