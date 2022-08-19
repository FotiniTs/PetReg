<script setup>
import { storeToRefs } from "pinia";
import { useAnimalStore } from "../stores/animal";
import { reactive, ref, watch } from "vue";
import { useRouter } from "vue-router";

const props = defineProps(["id"]);

const animalStore = useAnimalStore();

(async () => {
  animalStore.fetchAnimal(props.id);
})();

const { currentAnimal, updatedAnimal } = storeToRefs(animalStore);

const validateType = (rule, value, callback) => {
  if (!value) {
    return callback(new Error("Please input the type"));
  } else {
    callback();
  }
};
const validateGender = (rule, value, callback) => {
  if (!value) {
    return callback(new Error("Please input the gender"));
  } else {
    callback();
  }
};

const validateMicrochipCode = (rule, value, callback) => {
  if (!value || value <= 0) {
    return callback(new Error("Please input the microchip code"));
  } else {
    callback();
  }
};

const validateBirthDate = (rule, value, callback) => {
  if (!value || value <= 0) {
    return callback(new Error("Please input the birth date"));
  } else {
    callback();
  }
};

const rules = reactive({
  gender: [{ validator: validateGender, trigger: "blur" }],
  type: [{ validator: validateType, trigger: "blur" }],
  microchip_code: [{ validator: validateMicrochipCode, trigger: "blur" }],
  birth_date: [{ type: "date", validator: validateBirthDate, trigger: "blur" }],
});

const editForm = ref(null);

async function submitForm(formEl) {
  if (!formEl) return;
  formEl.validate(async (valid) => {
    if (valid) {
      await animalStore.updateAnimal(props.id, currentAnimal.value);
    } else {
      return false;
    }
  });
}

const router = useRouter();

watch(updatedAnimal, function () {
  router.push("/animals");
});
</script>

<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span>Edit Animal</span>
      </div>
    </template>
    <el-form
      ref="editForm"
      :model="currentAnimal"
      status-icon
      :rules="rules"
      label-position="left"
      size="large"
      class="demo-ruleForm"
    >
      <el-form-item label="Microchip Code" prop="microchip_code">
        <el-input-number v-model="currentAnimal.microchip_code" />
      </el-form-item>
      <el-form-item label="Type" prop="type">
        <el-radio-group v-model="currentAnimal.type">
          <el-radio label="Cat" />
          <el-radio label="Dog" />
        </el-radio-group>
      </el-form-item>
      <el-form-item label="Gender" prop="gender">
        <el-radio-group v-model="currentAnimal.gender">
          <el-radio label="Male" />
          <el-radio label="Female" />
        </el-radio-group>
      </el-form-item>
      <el-form-item prop="birth_date" label="Birth Date">
        <el-date-picker
          v-model="currentAnimal.birth_date"
          type="date"
          placeholder="Pick a day"
          format="DD/MM/YYYY"
          value-format="DD/MM/YYYY"
        />
      </el-form-item>
      <el-form-item label="Is missing" prop="missing">
        <el-switch v-model="currentAnimal.missing" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm(editForm)"
          >Submit</el-button
        >
      </el-form-item>
    </el-form>
  </el-card>
</template>
