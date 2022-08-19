<script setup>
import { storeToRefs } from "pinia";
import { useAuthStore } from "./stores/auth";
import { useMessageStore } from "./stores/message";
import { computed, watch } from "vue";
import { ElNotification } from "element-plus";
import { useRouter } from "vue-router";

const authStore = useAuthStore();

const messageStore = useMessageStore();

const loggedIn = computed(() => authStore.loggedIn);

const { success, error } = storeToRefs(messageStore);

const router = useRouter();

const logout = () => {
  authStore.logout();
  router.push({ name: "login" });
};
watch(success, function () {
  if (success.value != "") {
    ElNotification({
      title: "Success",
      message: success.value,
      type: "success",
    });
  }
  success.value = "";
});

watch(error, function () {
  if (error.value != "") {
    ElNotification({
      title: "Error",
      message: error.value,
      type: "error",
    });
  }
  error.value = "";
});
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <el-menu
          class="el-menu-demo"
          mode="horizontal"
          :ellipsis="false"
          :router="true"
        >
          <el-menu-item>Pet Registry</el-menu-item>
          <div class="flex-grow" />
          <el-menu-item v-if="!loggedIn" index="/">Login</el-menu-item>
          <el-menu-item v-if="!loggedIn" index="/register"
            >Register</el-menu-item
          >
          <el-menu-item v-if="loggedIn" index="/animals">
            Animals
          </el-menu-item>
          <el-menu-item v-if="loggedIn">
            <a @click="logout()">
              <span>Logout</span>
            </a></el-menu-item
          >
        </el-menu>
      </el-header>
      <el-main>
        <div style="padding-top: 30px">
          <el-space
            fill
            wrap
            direction="vertical"
            :fill-ratio="90"
            style="width: 100%"
          >
            <RouterView />
          </el-space>
        </div>
      </el-main>
    </el-container>
  </div>
</template>
<style>
.flex-grow {
  flex-grow: 2;
}
</style>
