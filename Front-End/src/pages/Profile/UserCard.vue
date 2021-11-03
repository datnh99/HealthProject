<template>
  <card type="user">
    <div class="author">
      <div class="block block-one"></div>
      <div class="block block-two"></div>
      <div class="block block-three"></div>
      <div class="block block-four"></div>
      <a href="#">
        <img
          v-if="!user.gender"
          class="avatar"
          src="../../assets/img/anime6.png"
          alt="..."
        />
        <img
          v-else
          class="avatar"
          src="../../assets/img/anime3.png"
          alt="..."
        />

        <h5 class="title">{{ user.fullName }}</h5>
      </a>
      <p class="description">
        {{ user.phoneNumber }}
      </p>
    </div>
    <p class="card-description">
      {{ user.addressDetail }}
    </p>
    <div slot="footer" class="row button-container">
      <div class="col-4">
        <base-button type="info" icon size="lg" class="btn-facebook">
          <i class="fas fa-qrcode fa-lg"></i>
        </base-button>
        <b>Quản lý QR</b>
      </div>
      <div class="col-4">
        <base-button type="success" @click="goToReport()" icon size="lg">
          <a-icon style type="file-add" />
        </base-button>
        <b>Khai báo</b>
      </div>
      <div class="col-4">
        <base-button type="warning" icon size="lg">
          <a-icon type="message" theme="filled" />
        </base-button>
        <b> Feedback</b>
      </div>
    </div>
  </card>
</template>
<script>
import { Card } from "@/components/index";
import BaseButton from "@/components/BaseButton";

export default {
  components: {
    Card,
    BaseButton,
  },
  props: {
    user: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  methods: {
    goToReport() {
      this.$router.push("declare");
    },
  },
};
</script>
<style scoped lang="scss" >
.anticon {
  // margin-right: 6px;
  font-size: 22px;
}
</style>
