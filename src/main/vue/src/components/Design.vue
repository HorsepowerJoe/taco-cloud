<!-- eslint-disable vue/multi-word-component-names -->
<template lang="">
  <h1>Design your taco!</h1>
  <img
    src="../assets/logo.png"
    alt=""
  >
  <form @submit.prevent="submitHandle">
    <!-- 원문의 타임리프 밸리데이션 구문 해석해서 기입.. 뷰리데이트 사용으로 기입x  -->
    <div class="grid">
      <div
        id="wraps"
        class="ingredient-group"
      >
        <h3>Designate your wrap:</h3>
        <div
          v-for="(item, index) in tacoDatas.wraps"
          :key="index"
        >
          <input
            v-model="newTacoData.wrap"
            name="ingredients"
            type="checkbox"
            :value="item.id"
          >
          <span>{{ item.name }}</span>
        </div>
      </div>

      <div
        id="proteins"
        class="ingredient-group"
      >
        <h3>Pick your protein:</h3>
        <div
          v-for="(item, index) in tacoDatas.proteins"
          :key="index"
        >
          <input
            v-model="newTacoData.protein"
            name="ingredients"
            type="checkbox"
            :value="item.id"
          >
          <span>{{ item.name }}</span>
        </div>
      </div>

      <div
        id="cheeses"
        class="ingredient-group"
      >
        <h3>Choose your cheese:</h3>
        <div
          v-for="(item, index) in tacoDatas.cheeses"
          :key="index"
        >
          <input
            v-model="newTacoData.cheese"
            name="ingredients"
            type="checkbox"
            :value="item.id"
          >
          <span>{{ item.name }}</span>
        </div>
      </div>

      <div
        id="veggies"
        class="ingredient-group"
      >
        <h3>Choose your veggies:</h3>
        <div
          v-for="(item, index) in tacoDatas.veggies"
          :key="index"
        >
          <input
            v-model="newTacoData.veggies"
            name="ingredients"
            type="checkbox"
            :value="item.id"
          >
          <span>{{ item.name }}</span>
        </div>
      </div>

      <div
        id="sauces"
        class="ingredient-group"
      >
        <h3>Choose your sauces:</h3>
        <div
          v-for="(item, index) in tacoDatas.sauces"
          :key="index"
        >
          <input
            v-model="newTacoData.sauces"
            name="ingredients"
            type="checkbox"
            :value="item.id"
          >
          <span>{{ item.name }}</span>
        </div>
      </div>
    </div>

    <div>
      <h3>Name your taco creation:</h3>
      <Field
        v-model="newTacoData.name"
        type="text"
        name="name"
        :rules="{required: true ,max: 15, min: 3}"
      />
    </div>
    <button type="submit">
      Submit
    </button>
  </form>
</template>
<script setup>

import axios from "axios";

import { validate, Field } from 'vee-validate';
import { ref, onMounted } from "vue";

const tacoDatas = ref([{}]);
const newTacoData = ref({
    wrap: "",
    protein: "",
    cheese: "",
    veggies: "",
    sauces: "",
    name: ""
});



onMounted(() => {
    axios.get("/api/design").then((resp) => {
        if (resp.status === 200) {
            tacoDatas.value = resp.data;
        } else {
            alert(resp.data);
        }
    });
});





const submitHandle = () => {
    validate(newTacoData.value.name, { required: true, max: 15, min: 3 }).then((v) => {
        v.valid ? axios.post('/api/sendTaco', newTacoData)
            : alert('이름은 필수 기입이며, 3~15자까지 가능합니다.');
    });
};
</script>
<style lang=""></style>
