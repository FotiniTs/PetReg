<script setup>
import { storeToRefs } from "pinia";
import { useAnimalStore } from "../stores/animal";
import { useMedicalHistoryStore } from "../stores/medical_history";

import { ref } from "vue";
import { useRouter } from "vue-router";

const props = defineProps(["id"]);

const animalStore = useAnimalStore();

const medicalHistoryStore = useMedicalHistoryStore();

(async () => {
  await animalStore.fetchAnimal(props.id);
  await medicalHistoryStore.initMedicalCases();
})();

const { currentAnimal } = storeToRefs(animalStore);

const { medicalCases } = storeToRefs(medicalHistoryStore);

const dialogCreateVisible = ref(false);

const dialogEditVisible = ref(false);

const activeName = ref("first");

const handleClick = (tab, event) => {
  console.log(tab, event);
};

const formLabelWidth = "140px";

const medical_case = ref({
  title: "",
  description: "",
});

const medical_case_to_update = ref({
  id: 0,
  title: "",
  description: "",
  timestamp: "",
});

const rules = ref({
  title: [
    { required: true, message: "Please input the title", trigger: "blur" },
    {
      min: 3,
      message: "Length should be at least 3 characters",
      trigger: "blur",
    },
  ],
  description: [
    {
      required: true,
      message: "Please input the description",
      trigger: "blur",
    },
    {
      min: 3,
      message: "Length should be at least 3 characters",
      trigger: "blur",
    },
  ],
});

const router = useRouter();

const editAnimal = () => {
  router.push({
    name: "animal update",
    params: { id: currentAnimal.value.id },
  });
};
const deleteAnimal = async () => {
  await animalStore.deleteAnimal(currentAnimal.value.id);
  router.push({
    name: "animals",
  });
};

const createMedicalCase = async () => {
  medical_case.value.animal_id = currentAnimal.value.id;
  await medicalHistoryStore.saveMedicalCase(medical_case.value);
  dialogCreateVisible.value = false;
};

const openEditMedicalCaseDialog = (medical_case) => {
  medical_case_to_update.value = medical_case;
  dialogEditVisible.value = true;
};
const updateMedicalCase = async () => {
  await medicalHistoryStore.updateMedicalCase(
    medical_case_to_update.value.id,
    medical_case_to_update.value
  );
  dialogEditVisible.value = false;
};

const deleteMedicalCase = async (medical_case) => {
  await medicalHistoryStore.deleteMedicalCase(medical_case.id);
};
</script>

<template>
  <el-dialog v-model="dialogCreateVisible" title="Add medical case">
    <el-form :model="medical_case" :rules="rules">
      <el-form-item label="Title" prop="title" :label-width="formLabelWidth">
        <el-input v-model="medical_case.title" />
      </el-form-item>
      <el-form-item
        label="Description"
        prop="description"
        :label-width="formLabelWidth"
      >
        <el-input v-model="medical_case.description" type="textarea" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogCreateVisible = false">Cancel</el-button>
        <el-button type="primary" @click="createMedicalCase()"
          >Confirm</el-button
        >
      </span>
    </template>
  </el-dialog>
  <el-dialog v-model="dialogEditVisible" title="Edit medical case">
    <el-form :model="medical_case_to_update" :rules="rules">
      <el-form-item label="Title" prop="title" :label-width="formLabelWidth">
        <el-input v-model="medical_case_to_update.title" />
      </el-form-item>
      <el-form-item
        label="Description"
        prop="description"
        :label-width="formLabelWidth"
      >
        <el-input
          v-model="medical_case_to_update.description"
          type="textarea"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogEditVisible = false">Cancel</el-button>
        <el-button type="primary" @click="updateMedicalCase()"
          >Confirm</el-button
        >
      </span>
    </template>
  </el-dialog>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span>Animal Details</span>
      </div>
    </template>
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
      <el-tab-pane label="Animal" name="first">
        <el-descriptions
          title="Animal Information"
          :column="4"
          size="32px"
          direction="vertical"
          :style="blockMargin"
        >
          <el-descriptions-item v-if="currentAnimal.citizen" label="Owner">
            {{ currentAnimal.citizen.full_name }}
          </el-descriptions-item>
          <el-descriptions-item label="Microchip Code">{{
            currentAnimal.microchip_code
          }}</el-descriptions-item>
          <el-descriptions-item label="Gender">{{
            currentAnimal.gender
          }}</el-descriptions-item>
          <el-descriptions-item label="Type">{{
            currentAnimal.type
          }}</el-descriptions-item>
          <el-descriptions-item label="Birth Date">
            {{ currentAnimal.birth_date }}
          </el-descriptions-item>
          <el-descriptions-item label="Validated">
            <el-tag size="small" v-if="currentAnimal.validated">YES</el-tag>
            <el-tag size="small" v-else>NO</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="Is Missing ?">
            <el-tag size="small" v-if="currentAnimal.missing">YES</el-tag>
            <el-tag size="small" v-else>NO</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="Actions">
            <el-button
              v-if="animalStore.canEditAnimal(currentAnimal)"
              size="small"
              @click="editAnimal()"
              >Edit</el-button
            >
            <el-button
              size="small"
              type="primary"
              v-if="animalStore.canDeleteAnimal(currentAnimal)"
              @click="deleteAnimal(currentAnimal)"
              >Delete</el-button
            >
            <el-button
              size="small"
              type="primary"
              v-if="animalStore.canFindAnimal(currentAnimal)"
              @click="animalStore.findAnimal(currentAnimal)"
              >Found</el-button
            >
            <el-button
              size="small"
              type="primary"
              v-if="animalStore.canValidateAnimal(currentAnimal)"
              @click="animalStore.validateAnimal(currentAnimal)"
              >Validate</el-button
            >
          </el-descriptions-item>
        </el-descriptions>
      </el-tab-pane>
      <el-tab-pane label="Medical History" name="second">
        <el-button
          v-if="medicalHistoryStore.canEditMedicalCase()"
          size="small"
          @click="dialogCreateVisible = true"
          >Create</el-button
        >
        <el-alert
          v-if="medicalCases?.length == 0"
          title="No medical history found !"
          type="info"
          effect="dark"
          :closable="false"
        />
        <div v-else>
          <el-descriptions
            v-for="(medical_case, index) in medicalCases"
            :key="medical_case.id"
            :title="index + 1 + '. Medical Case'"
            :column="4"
            size="32px"
            direction="vertical"
            :style="blockMargin"
          >
            <el-descriptions-item label="Title">
              {{ medical_case.title }}
            </el-descriptions-item>
            <el-descriptions-item label=" Description">{{
              medical_case.description
            }}</el-descriptions-item>
            <el-descriptions-item label="Timestamp">{{
              medical_case.timestamp
            }}</el-descriptions-item>
            <el-descriptions-item
              v-if="medicalHistoryStore.canEditMedicalCase()"
              label="Actions"
            >
              <el-button
                size="small"
                @click="openEditMedicalCaseDialog(medical_case)"
                >Edit</el-button
              >
              <el-button
                size="small"
                type="primary"
                @click="deleteMedicalCase(medical_case)"
                >Delete</el-button
              >
            </el-descriptions-item>
          </el-descriptions>
        </div>
      </el-tab-pane>
    </el-tabs>
  </el-card>
</template>

<style>
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}
</style>
