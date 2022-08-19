import { defineStore } from "pinia";
import { useAuthStore } from "../stores/auth";
import axios from "axios";
import { useMessageStore } from "../stores/message";
import { useAnimalStore } from "./animal";

export const useMedicalHistoryStore = defineStore({
  id: "medical_history",
  state: () => ({
    createdMedicalCase: {},
    updatedMedicalCase: {},
    deletedMedicalCase: {},
    medicalCases: [],
  }),
  getters: {},
  actions: {
    async saveMedicalCase(medical_case) {
      const authStore = useAuthStore();
      const messageStore = useMessageStore();
      try {
        const res = await axios.post(
          import.meta.env.VITE_API_URL + "medical_cases",
          medical_case,
          {
            headers: {
              Authorization: "Bearer " + authStore.user.token,
            },
          }
        );
        this.createdMedicalCase = res.data;
        this.medicalCases.push(this.createdMedicalCase);
        messageStore.success = "Medical case saved !";
      } catch (error) {
        messageStore.error = error.response.data.message;
      }
    },
    async updateMedicalCase(id, medical_case) {
      const authStore = useAuthStore();
      const messageStore = useMessageStore();
      try {
        const res = await axios.put(
          import.meta.env.VITE_API_URL + "medical_cases/" + id,
          medical_case,
          {
            headers: {
              Authorization: "Bearer " + authStore.user.token,
            },
          }
        );
        this.updatedMedicalCase = res.data;
        this.medicalCases = this.medicalCases.filter(
          (medical_case) => medical_case.id != id
        );
        this.medicalCases.push(this.updatedMedicalCase);
        messageStore.success = "Medical case updated !";
      } catch (error) {
        messageStore.error = error.response.data.message;
      }
    },
    async deleteMedicalCase(id) {
      const authStore = useAuthStore();
      const messageStore = useMessageStore();
      try {
        const res = await axios.delete(
          import.meta.env.VITE_API_URL + "medical_cases/" + id,
          {
            headers: {
              Authorization: "Bearer " + authStore.user.token,
            },
          }
        );
        this.deletedMedicalCase = { id: res.data };
        this.medicalCases = this.medicalCases.filter(
          (medical_case) => medical_case.id != id
        );
        messageStore.success = "Medical case deleted !";
      } catch (error) {
        messageStore.error = error.response.data.message;
      }
    },
    async initMedicalCases() {
      const animalStore = useAnimalStore();
      if (animalStore.currentAnimal != {}) {
        console.log(animalStore.currentAnimal);
        this.medicalCases = animalStore.currentAnimal.medical_cases;
      }
    },
    canEditMedicalCase() {
      const authStore = useAuthStore();
      if (
        authStore.user.role == "ROLE_ADMIN" ||
        authStore.user.role == "ROLE_VET"
      ) {
        return true;
      }
      return false;
    },
  },
});
