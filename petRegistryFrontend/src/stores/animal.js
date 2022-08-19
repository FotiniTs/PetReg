import { defineStore } from "pinia";
import { useAuthStore } from "../stores/auth";
import axios from "axios";
import { useMessageStore } from "../stores/message";

export const useAnimalStore = defineStore({
  id: "animal",
  state: () => ({
    createdAnimal: {},
    updatedAnimal: {},
    deletedAnimal: {},
    currentAnimal: {},
    animals: [],
    error: "",
    success: "",
  }),
  getters: {
    doubleCount: (state) => state.user * 2,
  },
  actions: {
    async saveAnimal(animal) {
      const authStore = useAuthStore();
      const messageStore = useMessageStore();
      try {
        const res = await axios.post(
          import.meta.env.VITE_API_URL + "animals",
          animal,
          {
            headers: {
              Authorization: "Bearer " + authStore.user.token,
            },
          }
        );
        this.createdAnimal = res.data;
        messageStore.success = "Animal data saved !";
      } catch (error) {
        messageStore.error = error.response.data.message;
      }
    },
    async updateAnimal(id, animal) {
      const authStore = useAuthStore();
      const messageStore = useMessageStore();
      try {
        const res = await axios.put(
          import.meta.env.VITE_API_URL + "animals/" + id,
          animal,
          {
            headers: {
              Authorization: "Bearer " + authStore.user.token,
            },
          }
        );
        this.updatedAnimal = res.data;
        messageStore.success = "Animal data updated !";
      } catch (error) {
        messageStore.error = error.response.data.message;
      }
    },
    async deleteAnimal(id) {
      const authStore = useAuthStore();
      const messageStore = useMessageStore();
      try {
        const res = await axios.delete(
          import.meta.env.VITE_API_URL + "animals/" + id,
          {
            headers: {
              Authorization: "Bearer " + authStore.user.token,
            },
          }
        );
        this.deletedAnimal = { id: res.data };
        this.animals = this.animals.filter((animal) => animal.id != id);
        messageStore.success = "Animal data deleted !";
      } catch (error) {
        messageStore.error = error.response.data.message;
      }
    },
    async fetchAnimals() {
      const authStore = useAuthStore();
      const messageStore = useMessageStore();
      try {
        const res = await axios.get(import.meta.env.VITE_API_URL + "animals", {
          headers: {
            Authorization: "Bearer " + authStore.user.token,
          },
        });
        this.animals = res.data;
      } catch (error) {
        messageStore.error = error.response.data.message;
      }
    },
    async fetchAnimal(id) {
      const authStore = useAuthStore();
      const messageStore = useMessageStore();
      try {
        const res = await axios.get(
          import.meta.env.VITE_API_URL + "animals/" + id,
          {
            headers: {
              Authorization: "Bearer " + authStore.user.token,
            },
          }
        );
        this.currentAnimal = res.data;
      } catch (error) {
	console.log(error);
        messageStore.error = error.response.data.message;
      }
    },
    async validateAnimal(animal) {
      animal.validated = true;
      await this.updateAnimal(animal.id, animal);
    },
    async findAnimal(animal) {
      animal.missing = false;
      await this.updateAnimal(animal.id, animal);
    },
    canCreateAnimal() {
      const authStore = useAuthStore();
      if (
        authStore.user.role == "ROLE_ADMIN" ||
        authStore.user.role == "ROLE_CITIZEN"
      ) {
        return true;
      }
      return false;
    },
    canEditAnimal(animal) {
      const authStore = useAuthStore();
      if (authStore.user.role == "ROLE_ADMIN") {
        return true;
      } else if (authStore.user.role == "ROLE_CITIZEN") {
        if (animal.citizen) {
          let citizen = animal.citizen;
          if (citizen.username == authStore.user.username) return true;
        }
      } else {
        return false;
      }
    },
    canDeleteAnimal(animal) {
      const authStore = useAuthStore();
      if (authStore.user.role == "ROLE_ADMIN") {
        return true;
      } else if (authStore.user.role == "ROLE_CITIZEN") {
        if (animal.citizen) {
          let citizen = animal.citizen;
          if (citizen.username == authStore.user.username) return true;
        }
      } else {
        return false;
      }
    },
    canValidateAnimal(animal) {
      const authStore = useAuthStore();
      if (
        (authStore.user.role == "ROLE_ADMIN" ||
          authStore.user.role == "ROLE_VET") &&
        animal.validated == false
      ) {
        return true;
      }
      return false;
    },
    canFindAnimal(animal) {
      const authStore = useAuthStore();
      if (
        (authStore.user.role == "ROLE_ADMIN" ||
          authStore.user.role == "ROLE_EMPLOYEE") &&
        animal.missing == true
      ) {
        return true;
      }
      return false;
    },
  },
});
