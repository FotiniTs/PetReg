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

const validateConfirmPass = (rule, value, callback) => {
  if (!value) {
    return callback(new Error("Please input the confirm password"));
  } else if (value != user.password) {
    return callback(new Error("Passwords not match !"));
  } else {
    callback();
  }
};

const rules = reactive({
  username: [
    {
      required: true,
      message: "Please input the username",
      trigger: "blur",
    },
    {
      min: 5,
      message: "Username must have at least 5 characters",
      trigger: "blur",
    },
  ],
  first_name: [
    {
      required: true,
      message: "Please input the first name",
      trigger: "blur",
    },
    {
      min: 5,
      message: "First name must have at least 5 characters",
      trigger: "blur",
    },
  ],
  last_name: [
    {
      required: true,
      message: "Please input the last name",
      trigger: "blur",
    },
    {
      min: 5,
      message: "Last name must have at least 5 characters",
      trigger: "blur",
    },
  ],
  password: [
    {
      required: true,
      message: "Please input the password",
      trigger: "blur",
    },
    {
      min: 6,
      message: "Password must have at least 6 characters",
      trigger: "blur",
    },
  ],
  confirm_password: [
    {
      validator: validateConfirmPass,
      trigger: "blur",
    },
  ],
  city: [
    {
      required: true,
      message: "Please input the city",
      trigger: "blur",
    },
    {
      min: 3,
      message: "city must have at least 3 characters",
      trigger: "blur",
    },
  ],
  state: [
    {
      required: true,
      message: "Please input the state",
      trigger: "blur",
    },
    {
      min: 3,
      message: "state must have at least 3 characters",
      trigger: "blur",
    },
  ],
});
const loginForm = ref(null);

const user = reactive({
  username: "",
  password: "",
  confirm_password: "",
  first_name: "",
  last_name: "",
  city: "",
  state: "",
});

const { loggedIn } = storeToRefs(authStore);

async function submitForm(formEl) {
  if (!formEl) return;
  formEl.validate(async (valid) => {
    if (valid) {
      await authStore.register(user);
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
          <span>Register</span>
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
        <el-form-item label="First Name" prop="first_name">
          <el-input v-model="user.first_name" />
        </el-form-item>
        <el-form-item label="Last Name" prop="last_name">
          <el-input v-model="user.last_name" />
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input v-model="user.password" type="password" />
        </el-form-item>
        <el-form-item label="Confirm Password" prop="confirm_password">
          <el-input v-model="user.confirm_password" type="password" />
        </el-form-item>
        <el-form-item label="City" prop="city">
          <el-input v-model="user.city" />
        </el-form-item>
        <el-form-item label="State" prop="state">
          <el-input v-model="user.state" />
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
