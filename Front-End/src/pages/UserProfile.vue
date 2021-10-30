<template>
  <div class="content">
    <div class="row">
      <!-- <div class="col-md-8">
        <edit-profile-form :model="model">
        </edit-profile-form>
      </div> -->
      <div class="col-md-12">
        <user-card :user="user"></user-card>
      </div>
    </div>
  </div>
</template>
<script>
// import EditProfileForm from "./Profile/EditProfileForm.vue";
import UserCard from "./Profile/UserCard.vue";
import UserRepository from "../api/user.js";
export default {
  components: {
    // EditProfileForm,
    UserCard,
  },
  data() {
    return {
      model: {
        company: "Creative Code Inc.",
        email: "mike@email.com",
        username: "michael23",
        firstName: "Mike",
        lastName: "Andrew",
        address: "Bld Mihail Kogalniceanu, nr. 8 Bl 1, Sc 1, Ap 09",
        city: "Melbourne",
        country: "Australia",
        about:
          "Lamborghini Mercy, Your chick she so thirsty, I'm in that two seat Lambo.",
      },
      user: {
        fullName: "",
        phoneNumber: "",
        addressDetail: "",
        gender:false
      },
    };
  },
  created() {
    this.getUserProfile();
  },
  methods: {
    getUserProfile() {
      const username = this.$cookies.get("username");
      UserRepository.getUserByUsername(username).then((res) => {
        let userProfile = res.data.data;
        this.user.fullName = userProfile.fullName;
        this.user.phoneNumber = userProfile.phoneNumber;
        this.user.addressDetail = userProfile.addressDetail;
        this.user.gender = userProfile.gender;
      });
    },
  },
};
</script>
<style>
</style>
