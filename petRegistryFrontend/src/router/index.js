import { createRouter, createWebHistory } from "vue-router";
import LoginView from "../views/LoginView.vue";
import RegisterView from "../views/RegisterView.vue";
import AnimalListView from "../views/AnimalListView.vue";
import AnimalEditView from "../views/AnimalEditView.vue";
import AnimalCreateView from "../views/AnimalCreateView.vue";
import AnimalDetailsView from "../views/AnimalDetailsView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "login",
      component: LoginView,
    },
    {
      path: "/register",
      name: "register",
      component: RegisterView,
    },
    {
      path: "/animals",
      name: "animals",
      component: AnimalListView,
      meta: {
        isAuthenticated: true,
      },
    },
    {
      path: "/animals/create",
      name: "animal create",
      component: AnimalCreateView,
      meta: {
        isAuthenticated: true,
        isCitizen: true,
        isVet: false,
        isEmployee: false,
      },
    },
    {
      path: "/animals/:id(\\d+)/update",
      name: "animal update",
      component: AnimalEditView,
      meta: {
        isAuthenticated: true,
        isCitizen: true,
        isVet: false,
        isEmployee: false,
      },
      props: true,
    },
    {
      path: "/animals/:id(\\d+)/details",
      name: "animal details",
      component: AnimalDetailsView,
      meta: {
        isAuthenticated: true,
      },
      props: true,
    },
    {
      path: "/:catchAll(.*)*",
      name: "Error",
      component: () => import("../views/ErrorView.vue"),
    },
  ],
});

router.beforeEach((to, from, next) => {
  if (to.matched.some((record) => record.meta.isAuthenticated)) {
    if (localStorage.getItem("user") == null) {
      next({
        path: "/",
        params: { nextUrl: to.fullPath },
      });
    } else {
      let user = JSON.parse(localStorage.getItem("user"));
	console.log(user)
      if (to.matched.some((record) => record.meta.isCitizen)) {
        if (user.role == "ROLE_CITIZEN" || user.role == "ROLE_ADMIN") {
          next();
        } else {
          next({ name: "Error" });
        }
      } else if (to.matched.some((record) => record.meta.isEmployee)) {
        if (user.role == "ROLE_EMPLOYEE" || user.role == "ROLE_ADMIN") {
          next();
        } else {
          next({ name: "Error" });
        }
      } else if (to.matched.some((record) => record.meta.isVet)) {
        if (user.role == "ROLE_VET" || user.role == "ROLE_ADMIN") {
          next();
        } else {
          next({ name: "Error" });
        }
      } else {
        next();
      }
    }
  } else {
    next();
  }
});
export default router;
