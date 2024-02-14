import { defineRule } from 'vee-validate';

import { required,max,min } from '@vee-validate/rules';
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';

defineRule('required', required);
defineRule('max', max);
defineRule('min', min);




createApp(App).use(router).mount('#app')
