import { createRouter, createWebHistory } from 'vue-router';
import Design from './components/Design.vue';
import Home from './components/Home.vue';
import OrderForm from './components/OrderForm.vue';


const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/design',
    name: 'Design',
    component: Design
  },
  {
    path: '/current',
    name: 'OrderForm',
    component: OrderForm
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;