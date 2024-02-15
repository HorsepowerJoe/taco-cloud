import { defineRule } from 'vee-validate';

import { required,max,min, numeric, email,  } from '@vee-validate/rules';
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';

defineRule('required', required);
defineRule('max', max);
defineRule('min', min);
defineRule('numeric', numeric);
defineRule('email', email);





createApp(App).use(router).mount('#app')
