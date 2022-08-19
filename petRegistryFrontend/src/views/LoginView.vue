<script setup>
import { reactive, ref, watch } from "vue";
import { useAuthStore } from "../stores/auth";
import { computed } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";

const router = useRouter();

const authStore = useAuthStore();

const isLoggedIn = computed(() => authStore.loggedIn);

(async () => {
  if (isLoggedIn.value) {
    router.push("/about");
  }
})();

const validateUsername = (rule, value, callback) => {
  if (!value) {
    return callback(new Error("Please input the username"));
  } else {
    callback();
  }
};
const validatePass = (rule, value, callback) => {
  if (!value) {
    return callback(new Error("Please input the password"));
  } else {
    callback();
  }
};
const rules = reactive({
  username: [{ validator: validateUsername, trigger: "blur" }],
  password: [{ validator: validatePass, trigger: "blur" }],
});
const loginForm = ref(null);

const user = reactive({
  username: "",
  password: "",
});

const { loggedIn } = storeToRefs(authStore);

async function submitForm(formEl) {
  if (!formEl) return;
  formEl.validate(async (valid) => {
    if (valid) {
      await authStore.login(user);
    } else {
      return false;
    }
  });
}

watch(loggedIn, function () {
  router.push("/animals");
});
</script>

<template>
  <el-space fill wrap direction="vertical" :fill-ratio="30" style="width: 100%">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>Login</span>
        </div>
      </template>
      <el-form
        ref="loginForm"
        :model="user"
        status-icon
        :rules="rules"
        label-position="top"
        size="large"
        class="demo-ruleForm"
      >
        <el-form-item label="Username" prop="username">
          <el-input v-model="user.username" />
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input v-model="user.password" type="password" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm(loginForm)"
            >Submit</el-button
          >
        </el-form-item>
      </el-form>
    </el-card>
  </el-space>
</template>

<style scoped>
.box-card {
  text-align: center;
}
</style>
