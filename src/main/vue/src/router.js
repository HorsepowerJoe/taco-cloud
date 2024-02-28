import { createRouter, createWebHistory } from 'vue-router';
import Design from './components/Design.vue';
import Home from './components/Home.vue';
import OrderForm from './components/OrderForm.vue';
import LoginPage from './components/LoginPage.vue';
import Registration from './components/Registration.vue';


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
    path: '/orders/current',
    name: 'OrderForm',
    component: OrderForm
  },
  {
    path: '/register',
    name: 'Registration',
    component: Registration
  },
  {
    path: '/login',
    name: 'login',
    component: LoginPage
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;