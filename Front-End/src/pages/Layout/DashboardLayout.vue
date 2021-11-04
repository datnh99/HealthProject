<template>
  <div class="wrapper">
    <!-- <div class="wrapper" :class="{'nav-open': $sidebar.showSidebar}"> -->

    <side-bar :background-color="backgroundColor" v-if="checkToken">
      <!-- <mobile-menu slot="content"></mobile-menu> -->
      <sidebar-link to="/home-page">
        <i class="tim-icons icon-single-02"></i>
        <template v-if="!isRTL">
          <p>Trang chủ</p>
        </template>
        <template v-else>
          <p>ملف تعريفي للمستخدم</p>
        </template>
      </sidebar-link>
      <sidebar-link to="/dashboard">
        <i class="tim-icons icon-chart-pie-36"></i>
        <template v-if="!isRTL">
          <p>Dashboard</p>
        </template>
        <template v-else>
          <p>لوحة القيادة</p>
        </template>
      </sidebar-link>
      <sidebar-link to="/class-management">
        <i class="tim-icons icon-bank"></i>
        <template v-if="!isRTL">
          <p>Quản lý lớp học</p>
        </template>
        <template v-else>
          <p>لوحة القيادة</p>
        </template>
      </sidebar-link>
      <sidebar-link to="/user-management">
        <i class="tim-icons icon-single-02"></i>
        <template v-if="!isRTL">
          <p>Quản lý học sinh</p>
        </template>
        <template v-else>
          <p>لوحة القيادة</p>
        </template>
      </sidebar-link>
      <sidebar-link to="/teacher-management">
        <i class="tim-icons icon-single-02"></i>
        <template v-if="!isRTL">
          <p>Quản lý giáo viên</p>
        </template>
        <template v-else>
          <p>لوحة القيادة</p>
        </template>
      </sidebar-link>
    </side-bar>
    <sidebar-share :background-color.sync="backgroundColor"></sidebar-share>

    <div class="main-panel" :data="backgroundColor">
      <top-navbar></top-navbar>

      <dashboard-content @click.native="toggleSidebar"></dashboard-content>

      <content-footer></content-footer>
    </div>
  </div>
</template>
<style lang="scss"></style>

<script>
import TopNavbar from "./TopNavbar.vue";
import ContentFooter from "./ContentFooter.vue";
import DashboardContent from "./DashboardContent.vue";
import SidebarShare from "./SidebarSharePlugin.vue";
import SideBar from "@/components/SidebarPlugin/SideBar.vue";
import SidebarLink from "@/components/SidebarPlugin/SidebarLink.vue";

export default {
  components: {
    TopNavbar,
    DashboardContent,
    ContentFooter,
    SideBar,
    SidebarLink,
    SidebarShare
  },
  data() {
    return {
      backgroundColor: "blue"
    };
  },
  computed: {
    isRTL() {
      return this.$rtl.isRTL;
    },
    checkToken() {
      if (this.$cookies.get("accessToken")) {
        return true;
      }
      return false;
    }
  },
  methods: {
    toggleSidebar() {
      if (this.$sidebar.showSidebar) {
        this.$sidebar.displaySidebar(false);
      }
    }
  }
};
</script>
<style scoped>
</style>
