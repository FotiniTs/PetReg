<script setup>
import { computed, ref } from "vue";
import { useAnimalStore } from "../stores/animal";
import { useRouter } from "vue-router";

const animalStore = useAnimalStore();

const search = ref("");

(async () => {
  animalStore.fetchAnimals();
})();

const filterTableData = computed(() =>
  animalStore.animals.filter(
    (data) => !search.value || data.microchip_code == search.value
  )
);

const router = useRouter();

const viewAnimal = (row) => {
  router.push({
    name: "animal details",
    params: { id: row.id },
  });
};

const createAnimal = () => {
  router.push({
    name: "animal create",
  });
};

const handleEdit = (index, row) => {
  router.push({
    name: "animal update",
    params: { id: row.id },
  });
};
const handleDelete = async (index, row) => {
  await animalStore.deleteAnimal(row.id);
};
</script>

<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span>Animals</span>
        <el-button
          class="button"
          v-if="animalStore.canCreateAnimal()"
          @click="createAnimal()"
          text
          >Create Animal</el-button
        >
      </div>
    </template>
    <el-table @row-click="viewAnimal" :data="filterTableData">
      <el-table-column type="expand">
        <template #default="props">
          <div m="4">
            <p m="t-0 b-2">Owner: {{ props.row.citizen.full_name }}</p>
            <p m="t-0 b-2">Validated: {{ props.row.validated }}</p>
            <p m="t-0 b-2">Missing: {{ props.row.missing }}</p>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="Microchip Code" prop="microchip_code" />
      <el-table-column label="Type" prop="type" />
      <el-table-column label="Gender" prop="gender" />
      <el-table-column label="Birth Date" prop="birth_date" />
      <el-table-column align="right">
        <template #header>
          <el-input
            v-model="search"
            size="small"
            placeholder="Type to search"
          />
        </template>
        <template #default="scope">
          <el-button
            v-if="animalStore.canEditAnimal(scope.row)"
            size="small"
            @click="handleEdit(scope.$index, scope.row)"
            >Edit</el-button
          >
          <el-button
            size="small"
            type="primary"
            v-if="animalStore.canDeleteAnimal(scope.row)"
            @click="handleDelete(scope.$index, scope.row)"
            >Delete</el-button
          >
          <el-button
            size="small"
            type="primary"
            v-if="animalStore.canFindAnimal(scope.row)"
            @click="animalStore.findAnimal(scope.row)"
            >Found</el-button
          >
          <el-button
            size="small"
            type="primary"
            v-if="animalStore.canValidateAnimal(scope.row)"
            @click="animalStore.validateAnimal(scope.row)"
            >Validate</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<style>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}
</style>
