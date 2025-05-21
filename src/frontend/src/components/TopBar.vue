<template>
  <div class="top-bar">
    <div class="actions">
      <DarkModeToggle></DarkModeToggle>
      <UserIcon></UserIcon>
    </div>
  </div>
</template>

<script>
import UserIcon from '@/components/UserIcon.vue';
import DarkModeToggle from "@/components/DarkModeToggle.vue";

export default {
  name: 'TopBar',
  components: {
    DarkModeToggle,
    UserIcon
  },
  data() {
    return {};
  }
};
</script>

<style scoped>
.top-bar {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  background-color: #007bff;
  padding: 10px 20px;
  color: white;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.actions {
  display: flex;
  align-items: center;
}

button {
  background: none;
  border: none;
  cursor: pointer;
  font-weight: bold;
  margin: 0 10px;
}

button:hover {
  text-decoration: underline;
}

.dark .top-bar {
  background-color: #333;
}
</style>