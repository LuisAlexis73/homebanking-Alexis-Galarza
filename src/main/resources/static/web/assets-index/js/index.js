const app = Vue.createApp({
  data() {
    return {
      email: "",
      pwd: "",
      firstName: "",
      lastName: "",
    };
  },
  created() {},
  methods: {
    login() {
      axios
        .post("/api/login", `email=${this.email}&pwd=${this.pwd}`, {
          headers: { "content-type": "application/x-www-form-urlencoded" },
        })
        .then((response) => (location.href = "/web/accounts.html"))
        .catch((response) =>
          Swal.fire({
            icon: "error",
            title: "Oops! some is wrong",
            text: "Please, check the fileds.",
          })
        );
    },

    register() {
      axios
        .post(
          "/api/clients",
          `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.pwd}`,
          { headers: { "content-type": "application/x-www-form-urlencoded" } }
        )
        .then((response) => this.login())
        .catch(function (error) {
          console.log(error);
        });
    },
  },
}).mount("#app");
